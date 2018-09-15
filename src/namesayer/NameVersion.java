package namesayer;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NameVersion extends Name{

    private File file;
    private Date date;

    public NameVersion(String name, File file) {
        super(name);

        this.file = file;

        setDate();
    }

    private void setDate() {

        String fileName = file.getName();

        String dateString = fileName.substring(fileName.indexOf("_") + 1, fileName.lastIndexOf("_"));

        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy_HH-mm-ss");


        try {
            Date parsedDate = sdf.parse(dateString);
            this.date = parsedDate;

        } catch (ParseException e){
            e.printStackTrace();
        }


    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return name + " - " + date;
    }
}

