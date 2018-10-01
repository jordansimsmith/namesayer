package namesayer;

import java.util.List;
import java.util.Map;

public interface Model {

    /**
     * This method records a name as having bad quality. It is written to a file in the project folder.
     *
     * @param name: the Name object that is of low quality.
     * @throws IllegalArgumentException: When the input is a name group not a name version.
     */
    void lowQualityName(NameVersion name);

    /**
     * This method returns a PracticeWorker (task) object that can be bound to a progress indicator and executed on a new
     * thread to record audio in the background.
     *
     * @param name: NameVersion object that is being recorded against.
     * @return a PracticeWorker object for concurrent execution.
     * @throws IllegalArgumentException: The input must be a NameVersion object, not a Name object.
     */
    PracticeWorker getPracticeWorker(NameVersion name, boolean practiceMode);

    /**
     * This method searches the database for all user practice recordings for a specific name version and returns them
     * in a list. Note that the input must be of type nameVersion, not name.
     *
     * @param name: NameVersion object that user recordings are being searched against.
     * @return A list of NameVersion objects representing the user practice recordings for that specific name.
     * @throws IllegalArgumentException: The input must be a NameVersion object, not a Name object.
     */
    List<NameVersion> getUserCreations(NameVersion name);

    /**
     * This method utilises the ffplay command to play one or more recordings consecutively. The names must be of type
     * NameVersion, not of type Name.
     *
     * @param names: List of NameVersion objects that should be played.
     * @return the process of the audio playback so it can be cancelled or the process state can be queried.
     * @throws IllegalArgumentException: The input must be a list of NameVersions not Names.
     */
    Process playAudio(List<NameVersion> names);

    /**
     * This method explores the names/ folder and retrieves all name versions in the database. These
     * name versions are organised into a map structure by their group name (non versioned). Each value of the
     * map is a Name object. Each name group (non versioned) contains all of its versions.
     *
     * @return a Map of type String, Name of the current name file database.
     */
    Map<String, Name> getMap();

    /**
     * This method returns the list of (non versioned) names object from the offical names database. Each name object
     * contains its versions.
     *
     * @return a List of Name objects from the database.
     */
    List<Name> getNamesList();
}
