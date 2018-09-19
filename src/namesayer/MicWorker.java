package namesayer;

import javafx.concurrent.Task;

public class MicWorker extends Task<Void> {

    @Override
    protected Void call() throws Exception {
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
