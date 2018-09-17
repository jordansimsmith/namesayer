package namesayer;

import javafx.concurrent.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RecordWorker extends Task<Name> {

    private NameVersion name;

    public RecordWorker(NameVersion name) {
        this.name = name;
    }

    @Override
    protected Name call() throws Exception {

        System.out.println("Record worker is starting");


        // make directory for recording
        String command = "mkdir -p recordings/" + name.getFile().getName();
        ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", command);
        Process process = builder.start();
        process.waitFor();

        // capture audio from microphone
        String timeStamp = new SimpleDateFormat("dd-mm-yyyy_HH-mm-ss").format(Calendar.getInstance().getTime());

        command = "ffmpeg -f alsa -i pulse -t 5 recordings/" + name.getFile().getName() + "/" + timeStamp + ".wav";
        builder = new ProcessBuilder("/bin/bash", "-c", command);
        builder.start();


        return null;
    }
}
