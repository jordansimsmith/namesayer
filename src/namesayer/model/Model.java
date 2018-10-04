package namesayer.model;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface Model {

    /**
     * This method records a name as having bad quality. It is written to a file in the project folder.
     *
     * @param name: the Name object that is of low quality.
     */
    void lowQualityName(NameList name);

    /**
     * This method returns a PracticeWorker (task) object that can be bound to a progress indicator and executed on a new
     * thread to record audio in the background.
     *
     * @param names: NameVersion object that is being recorded against.
     * @return a PracticeWorker object for concurrent execution.
     */
    PracticeWorker getPracticeWorker(NameList names, boolean practiceMode);

    /**
     * This method utilises the ffplay command to play one or more recordings consecutively.
     *
     * @param names:     List of Name objects that should be played.
     * @param recording: Optional user recording to be played after the names. Pass null if not required.
     * @return the process of the audio playback so it can be cancelled or the process state can be queried.
     */
    void playAudio(NameList names, NameVersion recording);

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
     * @param names: a string of names to be practised together, separated by a space or hypen.
     * @return the name object that was searched for. Will return null if the name doesnt exist in the database.
     */
    SearchResult nameSearch(String names);

    /**
     * This method parses an uploaded text file of names. Names to be practised together should be on the same
     * line separated by spaces or hyphens. Separate names should be on new lines.
     *
     * @param file: the text file to parse.
     * @return a list of NameList objects to be played.
     */
    List<SearchResult> parseFile(File file);

    /**
     * Sets the current volume that the audio should be played by
     *
     * @param volume integer between 0 (min) and 100 (max)
     */
    void setVolume(double volume);

    /**
     * Gets the list of the most recent attempts that the user has saved.
     *
     * @return a list of the most recent attempt for each name permutation attempted.
     */
    List<NameVersion> getAttempts();
}
