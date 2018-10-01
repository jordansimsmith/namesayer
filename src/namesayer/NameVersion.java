package namesayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class NameVersion  {

    private File file;
    private Date date;
    private String name;
    private boolean badRating;

    public NameVersion(String name, File file) {
        this.name = name;
        this.file = file;

        setDate();
        updateRating();
    }

    private void setDate() {

        String fileName = file.getName();

        String dateString = fileName.substring(fileName.indexOf("_") + 1, fileName.lastIndexOf("_"));

        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy_HH-mm-ss");


        try {
            this.date = sdf.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void updateRating() {

        badRating = false;

        String fileName = file.getName();

        // check if name has a bad badRating
        try {
            Scanner scanner = new Scanner(new File("badnames.txt"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // exit function if the name already has been given a bad badRating
                if (line.equals(fileName)) {
                    badRating = true;
                }
            }
        } catch (FileNotFoundException e) {
            // file not found, cannot have a bad badRating
            badRating = false;
        }
    }

    public Boolean isBadName() {

        updateRating();
        return badRating;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return name + " - " + date;
    }
}

