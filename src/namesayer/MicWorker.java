package namesayer;

import javafx.concurrent.Task;

import javax.sound.sampled.*;

public class MicWorker extends Task<Void> {

    @Override
    protected Void call() throws Exception {

        // initialise line in
        AudioFormat format = new AudioFormat(44100,16, 2, true, true);

        DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);

        TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
        targetLine.open(format);
        targetLine.start();

        // declare temporary buffer
        byte[] buffer = new byte[6000];

        // sample microphone
        int level = 0;
        boolean stop = false;

        while (!stop) {
            if (targetLine.read(buffer, 0, buffer.length) > 0) {
                level = calculateRMS(buffer);
                System.out.println(level);
            }
        }

        // close line
        targetLine.close();




        return null;
    }

    /**
     * This function returns the Root Mean Square (RMS) level for an array of bytes captured from the microphone.
     * @param data: array of bytes for the audio capture.
     * @return the RMS level.
     */
    private int calculateRMS(byte[] data) {

        long sum = 0;
        for (byte point: data) {
            sum += point;
        }

        double average = sum/(double)data.length;
        double sumMS = 0d;

        for (byte point: data) {
            sumMS += Math.pow(point - average, 2d);
        }

        double avgMS = sumMS/data.length;

        return (int)(Math.pow(avgMS, 0.5d));
    }
}
