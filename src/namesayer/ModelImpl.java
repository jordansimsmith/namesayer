package namesayer;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.util.*;

public class ModelImpl implements Model {

    private Map<String, Name> map = new HashMap<>();
    private List<Name> list = new ArrayList<>();

    public ModelImpl() {
        generateMap();
    }

    @Override
    public List<Name> getNamesList() {
        return list;
    }

    @Override
    public Map<String, Name> getMap() {
        return map;
    }

    private String parseFileName(File file) {

        // extract name
        String rawName = file.getName();
        String parsedName = rawName.substring(rawName.lastIndexOf("_") + 1, rawName.lastIndexOf("."));

        // capitalise first letter
        parsedName = parsedName.substring(0, 1).toUpperCase() + parsedName.substring(1);

        return parsedName;
    }

    private void generateMap() {

        // declare folder to search for files
        File folder = new File("names/");

        // get all names in the database
        File[] files = folder.listFiles();

        // name database not present
        if (files == null) {
            throw new RuntimeException("Names database not found. Please populate the names/ folder.");
        }

        // iterate through database files and generate hashmap
        for (File file : files) {

            // parse name
            String parsedName = parseFileName(file);

            // create name version object
            NameVersion nameVersion = new NameVersion(parsedName, file);

            // query whether a version of this name has already been added to the map
            if (map.containsKey(parsedName)) {

                // contains key
                // add name version to the name group
                map.get(parsedName).addName(nameVersion);
            } else {

                // doesn't contain key
                // create new name and add the name version to it
                Name name = new Name(parsedName);
                name.addName(nameVersion);
                map.put(parsedName, name);

                // add Name to a list for displaying
                list.add(name);
            }
        }
    }

    @Override
    public void lowQualityName(NameList names) {

        List<ButtonType> buttons = new ArrayList<>();
        Map<ButtonType, Name> map = new HashMap<>();

        // create buttons for each name
        for (Name name: names.getNames()) {
            ButtonType button = new ButtonType(name.toString());
            buttons.add(button);
            map.put(button, name);
        }

        // construct and show alert box asking for which name was bad
        Alert alert = new Alert(Alert.AlertType.NONE,"Please select the name that you wish to report.",buttons.toArray(new ButtonType[]{}));
        alert.setHeaderText("Which name would you like to report?");
        alert.setTitle("Bad Recording");
        alert.showAndWait();

        // get the name the user picked
        NameVersion badName = map.get(alert.getResult()).getLastPlayed();

        // if the user chooses to rate a name before playing it
        if (badName == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Cannot rate a name that hasn't been played.");
            error.setContentText("Please play a name first before rating it.");
            error.showAndWait();

            return;
        }

        // get file name
        String fileName = badName.getFile().getName();

        // check if name already has a bad rating
        try {
            Scanner scanner = new Scanner(new File("badnames.txt"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // exit function if the name already has been given a bad rating
                if (line.equals(fileName)) {

                    // show success message
                    Alert success = new Alert(Alert.AlertType.CONFIRMATION);
                    success.setTitle("Success");
                    success.setHeaderText("Successfully rated " + badName.getName());
                    success.setContentText("Thank you for your input.");
                    success.showAndWait();

                    return;
                }
            }
        } catch (FileNotFoundException e) {
            // file not found, continue to create badnames.txt
        }

        // otherwise write the filename to badnames.txt
        try {
            FileWriter writer = new FileWriter("badnames.txt", true);
            PrintWriter printer = new PrintWriter(writer);

            printer.printf("%s" + "%n", fileName);
            printer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // show success message
        Alert success = new Alert(Alert.AlertType.CONFIRMATION);
        success.setTitle("Success");
        success.setHeaderText("Successfully rated " + badName.getName());
        success.setContentText("Thank you for your input.");
        success.showAndWait();

    }

    @Override
    public PracticeWorker getPracticeWorker(NameList name, boolean practiceMode) {


        return new PracticeWorker( name, practiceMode, this);
    }

    @Override
    public Process playAudio(NameList nameList, NameVersion recording) {

        StringBuilder files = new StringBuilder();

        // TODO: concatenate, equalize and trim files before playing

        // iterate through all provided names
        for (Name name: nameList.getNames()) {
            files.append(name.pickVersion().getFile().getPath());
            files.append(" ");
        }

        // add recording if applicable
        if (recording != null) {
            files.append(recording.getFile().getPath());
            files.append(" ");
        }

        // execute ffplay command
        String command = "for f in " + files.toString() + "; do ffplay -autoexit -nodisp \"$f\"; done";
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", command);
        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return process;
    }

    @Override
    public NameList nameSearch(String names){

        List<Name> list = new ArrayList<>();

        // split string
        String[] strings = names.split("[-\\s]");

        // iterate through all provided names
        for (String string: strings) {

            // get name
            Name name = map.get(string);

            // if name is found, add it to the list
            if (name != null) {
                list.add(name);
            }
        }

        // if no names are found
        if (list.isEmpty()) {
            return null;
        }

        // return NameList instance
        return new NameList(list);

    }

    @Override
    public List<NameList> parseFile(File file) {

        // list to return
        List<NameList> names = new ArrayList<>();

        // scan through the text file
        try {
            Scanner scanner = new Scanner(file);

            // read all lines
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // parse the line
                NameList nameList = nameSearch(line);

                names.add(nameList);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return names;
    }
}
