package namesayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import namesayer.model.ModelImpl;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("controller/home.fxml"));
        primaryStage.setTitle("NameSayer");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();

        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1280);

        final boolean resizable = primaryStage.isResizable();
        primaryStage.setResizable(!resizable);
        primaryStage.setResizable(resizable);

        int streak = 0;

        // Get current date
        Date today = new Date();


        // Check text file for streaks
        try {
            Scanner scanner = new Scanner(new File("streak.txt"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String string = line.substring(0,29);
                streak = Integer.parseInt(line.substring(30));
                DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                Date dateFromFile = null;
                try {
                    dateFromFile = format.parse(string);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

//                System.out.println(dateFromFile);
//                System.out.println(streak);


                if ((dateFromFile.getYear() == today.getYear() && (dateFromFile.getMonth() == today.getMonth())) && (today.getDay() == dateFromFile.getDay()+1)) {
                    streak++;
                } else if ((dateFromFile.getYear() == today.getYear() && (dateFromFile.getMonth() == today.getMonth())) && (today.getDay() == dateFromFile.getDay())) {
                    // same day
                } else {
                    // Clear streak
                    streak = 0;
                }

            }

        } catch (FileNotFoundException e) {
            // File does not exist
        }

        // Create or overwrite streak file
        try {
            PrintWriter writer = new PrintWriter("streak.txt", "UTF-8");
            writer.println(today + " " + streak);
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        // Pop up for streaks
        if (streak >= 3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulations");
            alert.setHeaderText("Streak!");
            alert.setContentText("You have been practising names for " + streak + " days in a row!");
            alert.showAndWait();
        }

    }


    public static void main(String[] args) {

        // initialise instance
        ModelImpl.getInstance();

        launch(args);



    }
}
