package namesayer;

import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PracticeWorker extends Task<Name> {

    private NameVersion name;
    private boolean practiceMode;
    private Model model;

    public PracticeWorker(NameVersion name, boolean practiceMode, Model model) {
        this.name = name;
        this.practiceMode = practiceMode;
        this.model = model;
    }

    @Override
    protected Name call() throws Exception {

        // play original name
        List<Name> originalName = new ArrayList<>();
        originalName.add(name);
        play(originalName);

        // record user name
        NameVersion recording = record();

        // play both names
        List<Name> bothNames = new ArrayList<>();
        bothNames.add(name);
        bothNames.add(recording);
        play(bothNames);

        return recording;
    }

    private void play(List<Name> names) throws InterruptedException {

        // start process and wait
        model.playAudio(names).waitFor();
    }

    private NameVersion record() throws InterruptedException, IOException {

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
