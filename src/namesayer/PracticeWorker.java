package namesayer;

import javafx.concurrent.Task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PracticeWorker extends Task<Name> {

    private NameVersion name;
    private boolean practiceMode;

    public PracticeWorker(NameVersion name, boolean practiceMode) {
        this.name = name;
        this.practiceMode = practiceMode;
    }

    @Override
    protected Name call() throws Exception {

        // make directory for recording
        String command = "mkdir -p recordings/" + name.getFile().getName();
        ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", command);
        Process process = builder.start();
        process.waitFor();

        // get current time
        String timeStamp = new SimpleDateFormat("dd-mm-yyyy_HH-mm-ss").format(Calendar.getInstance().getTime());

        // capture audio from microphone
        String filename = "user_" + timeStamp + "_" + name.getName() + ".wav";
        command = "ffmpeg -f alsa -i pulse -t 5 recordings/" + name.getFile().getName() + "/" + filename;
        builder = new ProcessBuilder("/bin/bash", "-c", command);
        process = builder.start();

        // periodically update task progress
        for (int i = 1; i <= 5; i++) {

            updateProgress(i, 5);
            Thread.sleep(1200);
        }

        process.waitFor();

        // read newly created file
        File file = new File("recordings/" + name.getFile().getName() + "/" + filename);

        return new NameVersion(name.getName(), file);
    }
}
