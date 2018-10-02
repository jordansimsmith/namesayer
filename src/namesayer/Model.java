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
    void lowQualityName(NameList name);

    /**
     * This method returns a PracticeWorker (task) object that can be bound to a progress indicator and executed on a new
     * thread to record audio in the background.
     *
     * @param names: NameVersion object that is being recorded against.
     * @return a PracticeWorker object for concurrent execution.
     * @throws IllegalArgumentException: The input must be a NameVersion object, not a Name object.
     */
    PracticeWorker getPracticeWorker(NameList names, boolean practiceMode);

    /**
     * This method utilises the ffplay command to play one or more recordings consecutively.
     *
     * @param names: List of Name objects that should be played.
     * @param recording: Optional user recording to be played after the names. Pass null if not required.
     * @return the process of the audio playback so it can be cancelled or the process state can be queried.
     * @throws IllegalArgumentException: The input must be a list of NameVersions not Names.
     */
    Process playAudio(NameList names, NameVersion recording);

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

    /**
     * This method searches the database for the specified name.
     *
     * @return the name object that was searched for. Will return null if the name doesnt exist in the database.
     */
    NameList nameSearch(String names);
}
