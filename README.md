# NameSayer
Final iteration of the NameSayer application. This application allows a user to practise the pronounciation of names from a database. 

This application is developed using the Model View Controller (MVC) design pattern with JavaFX. Scene builder has been used to create the UI layout.

## How to run
1. Place the names database in a folder called "names/" on the same level as the jar.
2. Run `java -jar namesayer-part03.jar` on Terminal.

## User Instructions

### View Scene
1. On application startup the left most list will be populated with all available names from the database. The names are listed in alphabetical order for easier naviagtion. Similar names are placed under the same branch to reduce cluster.
2. Select desired name/s to practise by clicking on the corresponding checkbox/es. 
3. Click the **Select** button to update the playlist.
4. To add previously saved user attempts, highlight any given name on the database. The corresponding user practices will populate the far right list. An empty list indicates that user has yet to save any practice recordings.  
OPTIONAL: User may chose to randomise the order in which the recordings are played by checking the **Shuffle** checkbox.  
5. When satisfied with the playlist, the user may start the playback using the **Playback** button.

### Practice Scene
1. The practice scene contains a list on the left containing all of the selected names in order that they
will be practised.
2. **Previous** and **Next** buttons can be used to navigate through the list of names.
3. The **Listen only** checkbox can be selected so that the names can be listened to without being 
attempted by the user.
4. The **Play** button plays the selected name, and if **Listen only** is unselected, then it will prompt the 
user to record their attempt at the name. A comparision between the recording and the user attempt will
be played after. The user can then choose to save this creation for future access or to discard it.
5. The **Bad Recording** button allows the user to indicate that a database recording is of low quality.
These ratings are stored in a file called *badnames.txt* in the same location as the runnable jar.
6. The **Test Mic** button can be used to test the volume of the user's microphone.
7. The **Return** button can be used to return to the previous screen where the user can select more names to 
practice or replay their past attempts.
