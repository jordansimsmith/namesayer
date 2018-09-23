package namesayer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class PracticeWorker extends Task<Void> {

    private NameVersion name;
    private boolean practiceMode;
    private Model model;

    public PracticeWorker(NameVersion name, boolean practiceMode, Model model) {
        this.name = name;
        this.practiceMode = practiceMode;
        this.model = model;
    }

    @Override
    protected Void call() throws Exception {

        // play original name
        List<Name> originalName = new ArrayList<>();
        originalName.add(name);
        play(originalName);

        if (practiceMode) {
            // user just wants to listen to the names
            return null;
        }

        // ask user if they are ready to record
        final CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Recording");
            alert.setHeaderText("Are you ready to record?");
            alert.setContentText("Press OK to start recording");
            alert.showAndWait();
            latch.countDown();
        });

        // wait for record confirmation
        latch.await();

        // record user name
        NameVersion recording = record();

        // reset progress
        updateProgress(0, 0);

        // play both names
        List<Name> bothNames = new ArrayList<>();
        bothNames.add(name);
        bothNames.add(recording);
        play(bothNames);

        // ask user if they want to keep the name
        Platform.runLater(() -> {

            // display confirmation box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save recording");
            alert.setHeaderText("Do you want to save this recording?");
            alert.setContentText("Press yes to save the recording.");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                // save the recording (do nothing)
            } else {
                // delete the recording
                String command = "rm " + recording.getFile().getPath();
                ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", command);
                try {
                    processBuilder.start().waitFor();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }


        });

        return null;
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
        for (int i = 1; i <= 50; i++) {

            updateProgress(i, 50);
            Thread.sleep(100);
        }

        process.waitFor();

        // read newly created file
        File file = new File("recordings/" + name.getFile().getName() + "/" + filename);

        return new NameVersion(name.getName(), file);
    }
}
