# Name Practice
The Practice screen is central to the application and most likely what the user will spend the most time on. The practice
scene allows the user to hear, record and compare their attempts to selected names from the database. The names available 
in the practice screen are selected previously in the upload/ search or view screens.

## Basic Features

- **Name List** - shown on the left is the list of names to be practised in this current session. The names will be played in the order that 
they are shown in this list. Entries in the Name List can either be an individual name or a group of names that are to 
be practised together. Names are not selected to be practised from this list, the user should use the next and previous buttons instead.

- **Current Name** - shown largely in the centre of the page is the name that is currently being practised. The current name
will update when the user progresses through the names in the Name List.

- **Play Button** - the play button, shown in the centre of the buttons at the bottom of the page is used to play the current name.
    - If **Listen Only** is selected, then the current name will be played to the user, so that they can hear the sound without recording.
    - If **Listen Only** is not selected, then the user will be taken through the recording process. Firstly, the name will be played outloud
    as before. Then the user will be prompted that the recording will start when they are ready. After recording, the original name 
    followed by the user's attempt will be played for comparison. The user is then presented with the opportunity to save or to discard
    this attempt.  
    - The progress bar located directly below the current name displays information about the state of the recording process
    as well as displaying how long the user has left to record their attempt.   
    
- **Navigation Buttons** - the previous and next buttons are used to navigate through the list of names that are being 
practised. When a user is comfortable with practising the current name, they can use the next button to attempt the next name. 
To return to a previous name, the user can use the previous button. A user can remain as long as desired on a name, and can 
navigate through the name list at their own pace.

- **Return Button** - the return button, located in the bottom right of the screen, will end this practice session and will
return the user to the home menu where they can perform other actions or exit the application.

## Advanced Features
- **Volume Slider** - the volume slider is located on the far right of the screen. The volume slider controls the volume at which
the practised names are played back to the user. The slider ranges between 0 (muted) and 100 (maximum volume). The default setting
of the slider is 100. To adjust the playback audio, the user should move the slider knob to the desired value before playing
a name.

- **Test Microphone** - the option for the user to test their microphone level is located in the bottom left corner of the screen.
This button will cause a popup with a live detection of the volume level captured by the microphone. The higher the blue bar, the
greater the volume captured by the microphone.

- **Bad Recording** - when practising names from the database, the user may encounter names of poor quality. To flag this name to 
the database administrator, the user can select the bad recording button. This button will prompt the user to select which of the 
individual names was of bad quality, catering for several names being played together. Furthermore, badly rated names are will not 
be played when there are names of better quality present.

- **Stop Recording** - if the user has finished recording their attempt in less than the 5 seconds provided, they can select the
stop recording button displayed in red. This button will skip over the remaining recording time and proceed to comparing the 
attempt against the original. The user should take care not to press this button before finishing their attempt, as it will
not record anything after it is pressed.

- **Repeat Count** - the repeat count dropdown selection is located on the left side of the screen. This option determines 
how many times the comparison between the user recording and the database recording will be played. The default value is 1, 
however the user can choose for the comparison to be looped over up to 5 times.