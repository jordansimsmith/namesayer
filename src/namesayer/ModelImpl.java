package namesayer;

import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.*;
import java.util.*;

public class ModelImpl implements Model {

    private TreeView<Name> tree;
    private List<NameVersion> checked = new ArrayList<>();
    private Map<String, Name> map = new HashMap<>();

    public ModelImpl() {
        generateTreeView();
        generateMap();
    }

    @Override
    public TreeView getTreeView() {
        return tree;
    }

    @Override
    public Map getMap() {
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
            }
        }
    }

    private void generateTreeView() {
        // declare folder to search for files
        File folder = new File("names/");

        // get all names in the database
        File[] files = folder.listFiles();

        // name database not present
        if (files == null) {
            throw new RuntimeException("Names database not found. Please populate the names/ folder.");
        }

        Name rootName = new Name("Names List");

        // create name objects for each file
        for (File file : files) {

            // parse name
            String parsedName = parseFileName(file);

            // create name version object
            NameVersion name = new NameVersion(parsedName, file);

            // do other versions of this name already exist?
            if (rootName.containName(parsedName)) {
                Name nameGroup = rootName.getNameByString(parsedName);
                nameGroup.addName(name);
            } else {
                // first occurrence of this name
                Name nameGroup = new Name(parsedName);
                nameGroup.addName(name);

                rootName.addName(nameGroup);
            }

        }

        // sort files
        Collections.sort(rootName.getNames(), (o1, o2) -> o1.toString().compareTo(o2.toString()));

        // generate tree root
        CheckBoxTreeItem<Name> root = new CheckBoxTreeItem<>(rootName);
        root.setExpanded(true);

        // iterate over all name groups
        for (Name nameGroup : rootName.getNames()) {
            CheckBoxTreeItem<Name> groupItem = new CheckBoxTreeItem<>(nameGroup);
            root.getChildren().add(groupItem);

            // iterate over all name versions
            for (Name nameVersion : nameGroup.getNames()) {

                CheckBoxTreeItem<Name> checkBoxTreeItem = new CheckBoxTreeItem<>(nameVersion);

                // add checked selection listener
                checkBoxTreeItem.selectedProperty().addListener((obs, oldVal, newVal) -> {

                    // deselected
                    if (oldVal && !newVal) {
                        checked.remove(checkBoxTreeItem.getValue());
                    }

                    // selected
                    if (!oldVal && newVal) {
                        checked.add((NameVersion) checkBoxTreeItem.getValue());
                    }

                });

                groupItem.getChildren().add(checkBoxTreeItem);
            }
        }


        // add the root to the tree
        TreeView<Name> tree = new TreeView<>(root);

        tree.setCellFactory(CheckBoxTreeCell.forTreeView());

        // set field
        this.tree = tree;
    }

    @Override
    public void lowQualityName(Name name) throws IllegalArgumentException {

        // assert input is a name version not a name group
        if (!(name instanceof NameVersion)) {
            throw new IllegalArgumentException("Argument must be a name version not a name group");
        }

        // get file name
        String fileName = ((NameVersion) name).getFile().getName();

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
    public List<NameVersion> getCheckedNames() {

        return checked;
    }

    @Override
    public PracticeWorker getPracticeWorker(Name name, boolean practiceMode) throws IllegalArgumentException {

        // verify the input type
        if (!(name instanceof NameVersion)) {
            throw new IllegalArgumentException("Argument must be a name version not a name group");
        }

        // safe to cast
        return new PracticeWorker((NameVersion) name, practiceMode, this);
    }

    @Override
    public List<NameVersion> getUserCreations(Name name) throws IllegalArgumentException {

        List<NameVersion> creations = new ArrayList<>();

        // verify the input type
        if (!(name instanceof NameVersion)) {
            throw new IllegalArgumentException("Argument must be a name version not a name group");
        }

        // safe to cast
        NameVersion nameVersion = (NameVersion) name;

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
    public Process playAudio(List<Name> names) throws IllegalArgumentException {

        StringBuilder files = new StringBuilder();

        // iterate through all provided names
        for (Name name : names) {
            // verify the input types
            if (!(name instanceof NameVersion)) {
                throw new IllegalArgumentException("Arguments must be of type name version not name group");
            }

            // safe to cast
            files.append(" ").append(((NameVersion) name).getFile().getPath());
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
}
