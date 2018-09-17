package namesayer;

import javafx.scene.control.TreeView;

import java.util.List;

public interface Model {

    /**
     * This method explores the names/ folder and retrieves all name versions in the database. These
     * name versions are organised into a tree structure by their group name (non versioned). Each node of the
     * tree is a Name object. Each name group (non versioned) contains all of its versions. The root of the tree contains
     * all name groups.
     * @return a TreeView of type Name of the current name file database.
     */
    TreeView getTreeView();

    /**
     * This method records a name as having bad quality. It is written to a file in the project folder.
     * @param name: the Name object that is of low quality.
     * @throws IllegalArgumentException: When the input is a name group not a name version.
     */
    void lowQualityName(Name name) throws IllegalArgumentException;

    /**
     * This method returns all currently selected name versions.
     * @return a list of NameVersion objects that are currently selected.
     */
    List<NameVersion> getCheckedNames();
}
