package namesayer.model;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;

public class PracticeWorker extends Task<Void> {

    private NameList names;
    private boolean practiceMode;
    private int repeatCount;
    private Model model;

    private boolean stop = false;

    public PracticeWorker(NameList names, boolean practiceMode, int repeatCount) {
        this.names = names;
        this.practiceMode = practiceMode;
        this.repeatCount = repeatCount;

        this.model = ModelImpl.getInstance();
    }

    @Override
    protected Void call() throws Exception {

        updateProgress(0, 0);
        updateMessage("Playing original");

        // play original name
        play(names, null);

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

        updateMessage("Recording");

        // record user name
        stop = false;
        NameVersion recording = record();

        // reset progress
        updateProgress(0, 0);

        updateMessage("Comparing names");

        // play both names as many times as the user requests
        for (int i = 0; i < repeatCount; i++) {
            play(names, recording);
        }


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

    private void play(NameList names, NameVersion recording) {

        // start process and wait
        model.playAudio(names, recording);
    }

    private NameVersion record() throws InterruptedException, IOException {

        String namesListName = names.generateFileName();

        // make directory for recording
        new ProcessBuilder("/bin/bash", "-c", "mkdir recordings").start().waitFor();

        // remove any past attempts
        new ProcessBuilder("/bin/bash", "-c", "rm recordings/*_" + namesListName + ".wav").start().waitFor();

        // get current time
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(Calendar.getInstance().getTime());

        // capture audio from microphone
        String filename = "user_" + timeStamp + "_" + namesListName + ".wav";
        String command = "ffmpeg -f alsa -i pulse -t 5 recordings/" + filename;
        ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", command);
        Process process = builder.start();

        // periodically update task progress
        for (int i = 1; i <= 50; i++) {

            // check for user cancel
            if (stop) {
                process.destroy();
                break;
            }

            updateProgress(i, 50);
            Thread.sleep(100);
        }

        // read newly created file
        File file = new File("recordings/" + filename);

        return new NameVersion(names.toString(), file);
    }

    public void stopRecording() {
        stop = true;
    }
}
