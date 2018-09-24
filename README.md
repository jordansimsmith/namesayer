# NameSayer Part Three
Third iteration of the NameSayer application. This application allows a user to practise the pronounciation of names from a database. 

This application is developed using the Model View Controller (MVC) design pattern with JavaFX. Scene builder has been used to create the UI layout.

## How to run
1. Place the names database in a folder called "names/" on the same level as the jar.
2. Run `java -jar namesayer-part03.jar` on Terminal.

## User Instructions
1. On application startup the left most list will be populated with all available names from the database. The names are listed in alphabetical order for easier naviagtion. Similar names are placed under the same branch to reduce cluster.
2. Select desired name/s to practise by clicking on the corresponding checkbox/es. 
3. Click the "Select" button to update the playlist.
4. To add previously saved user attempts, highlight any given name on the database. The corresponding user practices will populate the far right list. An empty list indicates that user has yet to save any practice recordings.
OPTIONAL: User may chose to randomise the order in which the recordings are played by checking the "Shuffle" checkbox.  
5. When satisfied with the playlist, the user may start the playback using the "Playback" button.
