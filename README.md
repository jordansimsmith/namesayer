# NameSayer
Final iteration of the NameSayer application. This application allows a user to practise the pronounciation of names from a database. 

This application is developed using the Model View Controller (MVC) design pattern with JavaFX. Scene builder has been used to create the UI layout.

## Target Audience
The target user for this project is the Dean of Engineering. The Dean will use this application to practice students' names before
the graduation ceremony. 

## How to run
1. Place the names database in a folder called "names/" on the same level as the jar.
2. Run `java -jar namesayer-part03.jar` on Terminal.

## User Instructions

### Search Database
1. Users can enter a string of Names they would like to listen/practise with.
2. Once the user is satisfied with their search they can click the **Search** button to commence name search.
3. The application will return the names that exist in the database. Name Sayer will also show names not found in red.
4. If the user is content with the search results, they can click the **Practice** button to go to the Practice Scene.

### Upload
1. Users can select a text file with pre-selected names using the **Upload*** button.
2. Name Sayer will show the all of names found in the database on a list on the left. Similarly, names not found will be shown on a list by the right hand side of the window.
3. If the user wish add more names, they can do so using the **Upload** button again.
4. If the user is ready to practise, they can do so using the **Play** button.
5. To clear the both lists, users can select the **Clear** button.
6. To return to the main menu, select the **Home** button.

### View Database
1. On application startup the left most list will be populated with all available names from the database. The names are listed in alphabetical order for easier naviagtion. Similar names are placed under the same branch to reduce cluster.
2. Select desired name/s to practise by clicking on the corresponding checkbox/es. 
3. Click the **Select** button to update the playlist.
4. To add previously saved user attempts, highlight any given name on the database. The corresponding user practices will populate the far right list. An empty list indicates that user has yet to save any practice recordings.  
*OPTIONAL: User may chose to randomise the order in which the recordings are played by checking the **Shuffle** checkbox.*  
5. When satisfied with the playlist, the user may start the playback using the **Playback** button.
6. The practice scene contains a list on the left containing all of the selected names in order that they
will be practised.
7. **Previous** and **Next** buttons can be used to navigate through the list of names.
8. The **Practice Mode** checkbox can be selected so that the names can be listened to without being 
attempted by the user.
9. The **Play** button plays the selected name, and if **Listen only** is unselected, then it will prompt the 
user to record their attempt at the name. A comparision between the recording and the user attempt will
be played after. The user can then choose to save this creation for future access or to discard it.
10. The volume slider on the right allows users to select their preferrable playback volume.
11. The **Bad Recording** button allows the user to indicate that a database recording is of low quality.
The user should select which of the names is of bad quality.
These ratings are stored in a file called *badnames.txt* in the same location as the runnable jar.
12. The **Test Mic** button can be used to test the volume of the user's microphone.
13. The **Return** button can be used to return to the previous screen where the user can select more names to 
practice or replay their past attempts.

### View Existing Attempts
1. The user can view all existing attempts. They will be listed alphabetically on the ListView.
2. The user can select a name and click **Play** to listen to their previous attempts.
3. The user can return to home screen via the **Close** button.
 
### Exiting Name Sayer
1. Users can exit Name Sayer by selecting the **Quit** button on the home screen.
