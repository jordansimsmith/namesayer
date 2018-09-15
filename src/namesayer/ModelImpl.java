package namesayer;

import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelImpl implements Model {

    @Override
    public TreeView getTreeView() {

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

            // do other versions of this name already exist
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
        TreeItem<Name> root = new TreeItem<>(rootName);
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

        return tree;
    }
}
