# Set Up Instructions
This page describes the system requirements to run NameSayer, and will guide the user through system setup and the launch of the 
NameSayer application.

## Required Software
NameSayer has been designed to run on a Linux based Operating System, relying on bash system calls. To run NameSayer, please
ensure that you are running a Linux distribution with a Graphical User Interface.

NameSayer also requires a **Java Runtime Environment** to run in and the **ffmpeg** program used for audio manipulation.

Using a terminal, Java 8 can be installed as follows:
```
$ sudo add-apt-repository ppa:webupd8team/java
$ sudo apt update
$ sudo apt install oracle-java8-installer
```

Similarly, ffmpeg can be installed as follows:
```
$ sudo apt update
$ sudo apt install ffmpeg
```

## Required Hardware
The NameSayer application involves the recording and playback of audio. Therefore, NameSayer requires that the user's
computer has either an internal or external microphone that is turned on. The user should ensure that their computer
has audio playback capability (speakers) and that they are turned on.

## Launching NameSayer
Before launching NameSayer, the names database folder must be placed in a folder called `names` in the same directory as 
the executable jar.

```
NameSayer
├── names
├── namesayer.jar
├── README.md
```

NameSayer must be run from the command line. The user must first navigate into the NameSayer folder. Then the application
can be launched as follows:

```
$ java -jar namesayer.jar
```