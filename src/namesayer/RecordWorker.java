package namesayer;

import javafx.concurrent.Task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RecordWorker extends Task<Void> {

    private NameVersion name;

    public RecordWorker(NameVersion name) {
        this.name = name;
    }

    @Override
    protected Void call() throws Exception {

        System.out.println("Record worker is starting");


        // make directory for recording
        String command = "mkdir -p recordings/" + name.getFile().getName();
        ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", command);
        Process process = builder.start();
        process.waitFor();

        // get current time
        String timeStamp = new SimpleDateFormat("dd-mm-yyyy_HH-mm-ss").format(Calendar.getInstance().getTime());

        // capture audio from microphone
        command = "ffmpeg -f alsa -i pulse -t 5 recordings/" + name.getFile().getName() + "/" + timeStamp + ".wav";
        builder = new ProcessBuilder("/bin/bash", "-c", command);
        process = builder.start();

        // periodically update task progress
        for (int i = 1; i <= 5; i++) {

            updateProgress(i,5);
            Thread.sleep(1200);
        }

        process.waitFor();

        // read newly created file
        File file = new File("recordings/" + name.getFile().getName() + "/" + timeStamp + ".wav");

        return null;
    }
}
