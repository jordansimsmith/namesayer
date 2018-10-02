package namesayer;

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
    public void lowQualityName(NameVersion name) {

        // get file name
        String fileName = name.getFile().getName();

        // check if name already has a bad rating
        try {
            Scanner scanner = new Scanner(new File("badnames.txt"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // exit function if the name already has been given a bad rating
                if (line.equals(fileName)) {
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

    }

    @Override
    public PracticeWorker getPracticeWorker(NameList name, boolean practiceMode) {


        return new PracticeWorker( name, practiceMode, this);
    }

    @Override
    public List<NameVersion> getUserCreations(NameVersion nameVersion){

        List<NameVersion> creations = new ArrayList<>();

        // define folder to search for user creations
        File folder = new File("recordings/" + nameVersion.getFile().getName());

        // read directory
        File[] files = folder.listFiles();

        // return if there are no files
        if (files == null) {
            return creations;
        }

        for (File file : files) {

            // parse name
            String parsedName = parseFileName(file);

            // create name version object
            creations.add(new NameVersion(parsedName, file));
        }

        return creations;
    }

    @Override
    public Process playAudio(NameList names, NameVersion recording) {

        StringBuilder files = new StringBuilder();

        // iterate through all provided names
//        for (NameList name : names) {
//
//            // safe to cast
//            files.append(" ").append(name.getFile().getPath());
//        }
//
//        // execute ffplay command
//        String command = "for f in " + files.toString() + "; do ffplay -autoexit -nodisp \"$f\"; done";
//        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", command);
//        Process process = null;
//        try {
//            process = processBuilder.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return process;

        return null;
    }
}
