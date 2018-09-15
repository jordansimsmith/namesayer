package namesayer;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {

    @Override
    public TreeView getTreeView() {

        // declare folder to search for files
        File folder = new File("names/");

        // get all names in the database
        File[] files = folder.listFiles();

        List<NameVersion> names = new ArrayList<NameVersion>();

        // create name objects for each file
        for (File file: files) {

            // extract name
            String rawName = file.getName();
            String parsedName = rawName.substring(rawName.lastIndexOf("_") + 1, rawName.lastIndexOf("."));
            // capitalise first letter
            parsedName = parsedName.substring(0,1).toUpperCase() + parsedName.substring(1);

            NameVersion name = new NameVersion(parsedName, file);
            names.add(name);
        }

        // generate tree root
        TreeItem<NameVersion> root = new TreeItem<NameVersion>(new NameVersion("root", null));
        root.setExpanded(true);

        // add names as children of the root
        for (NameVersion name: names) {
            TreeItem<NameVersion> item = new TreeItem<NameVersion>(name);
            root.getChildren().add(item);
        }

        // add the root to the tree
        TreeView<NameVersion> tree = new TreeView<NameVersion>(root);

        return tree;
    }
}
