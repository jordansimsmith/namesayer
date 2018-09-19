package namesayer;

import javafx.scene.control.TreeView;

import java.util.List;

public interface Model {

    /**
     * This method explores the names/ folder and retrieves all name versions in the database. These
     * name versions are organised into a tree structure by their group name (non versioned). Each node of the
     * tree is a Name object. Each name group (non versioned) contains all of its versions. The root of the tree contains
     * all name groups.
     *
     * @return a TreeView of type Name of the current name file database.
     */
    TreeView getTreeView();

    /**
     * This method records a name as having bad quality. It is written to a file in the project folder.
     *
     * @param name: the Name object that is of low quality.
     * @throws IllegalArgumentException: When the input is a name group not a name version.
     */
    void lowQualityName(Name name) throws IllegalArgumentException;

    /**
     * This method returns all currently selected name versions.
     *
     * @return a list of NameVersion objects that are currently selected.
     */
    List<NameVersion> getCheckedNames();

    /**
     * This method returns a RecordWorker (task) object that can be bound to a progress indicator and executed on a new
     * thread to record audio in the background.
     *
     * @param name: NameVersion object that is being recorded against.
     * @return a RecordWorker object for concurrent execution.
     * @throws IllegalArgumentException: The input must be a NameVersion object, not a Name object.
     */
    RecordWorker getRecordWorker(Name name) throws IllegalArgumentException;

    /**
     * This method searches the database for all user practice recordings for a specific name version and returns them
     * in a list. Note that the input must be of type nameVersion, not name.
     *
     * @param name: NameVersion object that user recordings are being searched against.
     * @return A list of NameVersion objects representing the user practice recordings for that specific name.
     * @throws IllegalArgumentException: The input must be a NameVersion object, not a Name object.
     */
    List<NameVersion> getUserCreations(Name name) throws IllegalArgumentException;
}
