package namesayer;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.util.*;

public class ModelImpl implements Model {

    private static Model instance = null;

    private Map<String, Name> map = new HashMap<>();
    private List<Name> list = new ArrayList<>();

    private int volume = 100;

    private static final double TARGET_VOLUME = -20.0d;

    private ModelImpl() {
        generateMap();
    }

    /**
     * Returns the singleton instance of the Model.
     */
    public static Model getInstance() {

        if (instance == null) {
            instance = new ModelImpl();
        }

        return instance;
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
        for (Name name : names.getNames()) {
            ButtonType button = new ButtonType(name.toString());
            buttons.add(button);
            map.put(button, name);
        }

        // construct and show alert box asking for which name was bad
        Alert alert = new Alert(Alert.AlertType.NONE, "Please select the name that you wish to report.", buttons.toArray(new ButtonType[]{}));
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


        return new PracticeWorker(name, practiceMode);
    }

    @Override
    public void playAudio(NameList nameList, NameVersion recording) {

        StringBuilder files = new StringBuilder();

        List<File> filesToAdjust = new ArrayList<>();

        // get all files to be played
        for (Name name : nameList.getNames()) {
            filesToAdjust.add(name.pickVersion().getFile());
        }

        // audio manipulation
        try {
            // trim silence and equalize volume
            List<File> filesToConcatenate = adjustAudio(filesToAdjust);

            // concatenate
            File fileToPlay = concatenateAudio(filesToConcatenate);

            // add concatenated file to playlist
            files.append(fileToPlay.getPath()).append(" ");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // add recording if applicable
        if (recording != null) {
            files.append(recording.getFile().getPath());
            files.append(" ");
        }

        // execute ffplay command
        String command = "for f in " + files.toString() + "; do ffplay -volume "+ volume +" -autoexit -nodisp \"$f\"; done";
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", command);
        try {
            processBuilder.start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // delete temp folder
        try {
            new ProcessBuilder("/bin/bash", "-c", "rm -rf temp").start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method manipulates the audio tracks for playing.
     * Involves trimming silence and volume equalisation.
     *
     * @param files The list of files to be adjusted.
     * @return a list of adjusted file objects.
     */
    private List<File> adjustAudio(List<File> files) throws IOException, InterruptedException {

        // list of files to return
        List<File> filteredFiles = new ArrayList<>();

        // make temp directory to work from
        new ProcessBuilder("/bin/bash", "-c", "mkdir temp").start().waitFor();

        // iterate through each file in the list
        for (File file : files) {

            // trim the string
            String trim = "ffmpeg -y -i " + file.getPath() + " -af silenceremove=1:0:-35dB:1:5:-50dB temp/" + file.getName() + "_trim.wav";
            new ProcessBuilder("/bin/bash", "-c", trim).start().waitFor();

            // read the volume
            String read = "ffmpeg -y -i " + file.getPath() + " -filter:a volumedetect -f null /dev/null |& grep mean_volume:";
            Process process = new ProcessBuilder("/bin/bash", "-c", read).start();
            process.waitFor();

            InputStream stdout = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
            String line = br.readLine();
            line = line.substring(line.lastIndexOf(":") + 1, line.lastIndexOf(" "));
            double meanVol = Double.parseDouble(line);

            // equalize volume
            double deltaVol = TARGET_VOLUME - meanVol;
            String equalize = String.format("ffmpeg -y -i temp/%s -filter:a \"volume=%.2fdB\" temp/%s_eq.wav", file.getName() + "_trim.wav", deltaVol, file.getName());
            new ProcessBuilder("/bin/bash", "-c", equalize).start().waitFor();

            // add adjusted file to the return list
            filteredFiles.add(new File("temp/" + file.getName() + "_eq.wav"));
        }

        return filteredFiles;
    }

    /**
     * This method concatenates a list of file objects for playing.
     *
     * @param files The list of files to be concatenated.
     * @return the concatenated audio file.
     */
    private File concatenateAudio(List<File> files) throws IOException, InterruptedException {

        StringBuilder command = new StringBuilder();

        command.append("ffmpeg");

        // add all files as inputs to the command
        for (File file : files) {
            command.append(" -i ");
            command.append(file.getPath());
        }

        command.append(" -filter_complex '");

        // add all channels to the command
        for (int i = 0; i < files.size(); i++) {
            command.append("[").append(i).append(":0]");
        }

        command.append("concat=n=");
        command.append(files.size());
        command.append(":v=0:a=1[out]' -map '[out]' ");

        // generate unique output name
        String name = UUID.randomUUID().toString();
        command.append("temp/").append(name).append(".wav");

        // start process
        new ProcessBuilder("/bin/bash", "-c", command.toString()).start().waitFor();

        return new File("temp/" + name + ".wav");
    }

    @Override
    public NameList nameSearch(String names) {

        List<Name> list = new ArrayList<>();

        // split string
        String[] strings = names.split("[-\\s]");

        // iterate through all provided names
        for (String string : strings) {

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

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
