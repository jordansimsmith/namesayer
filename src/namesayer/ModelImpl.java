package namesayer;

import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ModelImpl implements Model {

    private TreeView tree;

    public ModelImpl() {
        generateTreeView();
    }

    @Override
    public TreeView getTreeView() {
        return tree;
    }

    private void generateTreeView() {
        // declare folder to search for files
        File folder = new File("names/");

        // get all names in the database
        File[] files = folder.listFiles();

        Name rootName = new Name("Names List");

        // create name objects for each file
        for (File file: files) {

            // extract name
            String rawName = file.getName();
            String parsedName = rawName.substring(rawName.lastIndexOf("_") + 1, rawName.lastIndexOf("."));
            // capitalise first letter
            parsedName = parsedName.substring(0,1).toUpperCase() + parsedName.substring(1);

            // create name version object
            NameVersion name = new NameVersion(parsedName, file);

            // do other versions of this name already exist?
            if (rootName.containName(parsedName)){
                Name nameGroup = rootName.getNameByString(parsedName);
                nameGroup.addName(name);
            } else {
                // first occurrence of this name
                Name nameGroup = new Name(parsedName);
                nameGroup.addName(name);

                rootName.addName(nameGroup);
            }

        }

        // generate tree root
        CheckBoxTreeItem<Name> root = new CheckBoxTreeItem<>(rootName);
        root.setExpanded(true);

        // iterate over all name groups
        for (Name nameGroup: rootName.getNames()){
            CheckBoxTreeItem<Name> groupItem = new CheckBoxTreeItem<>(nameGroup);
            root.getChildren().add(groupItem);

            // iterate over all name versions
            for (Name nameVersion: nameGroup.getNames()) {
                groupItem.getChildren().add(new CheckBoxTreeItem<>(nameVersion));
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

        try {
            FileWriter writer = new FileWriter("badnames.txt", true);
            PrintWriter printer = new PrintWriter(writer);

            printer.printf("%s" + "%n", fileName);
            printer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
