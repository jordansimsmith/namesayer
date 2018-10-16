package namesayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import namesayer.model.ModelImpl;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("controller/home.fxml"));
        primaryStage.setTitle("NameSayer");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();

        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1280);

        streak();


    }


    public static void main(String[] args) {

        // initialise instance
        ModelImpl.getInstance();

        launch(args);

    }

    private void streak() {
        int streak = 1;

        // Get current date
        LocalDate today = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Date written to file
        LocalDate streakDate = null;

        // determine date parsing format
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        // read text file for streaks
        try {
            Scanner scanner = new Scanner(new File("streak.txt"));
            String line = scanner.nextLine();

            // parse streak date from file
            streakDate = format.parse(line).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // parse streak length from file
            streak = Integer.parseInt(line.substring(line.lastIndexOf(" ") + 1));


        } catch (FileNotFoundException e) {
            // file does not exist
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (streakDate != null) {
            // same day
            if (streakDate.getDayOfYear() == today.getDayOfYear()) {
                // do nothing

                // yesterday
            } else if (streakDate.getDayOfYear() == today.getDayOfYear() - 1) {
                // increment streak
                streak++;
            } else {
                streak = 1;
            }
        }

        // pop up for streaks
        if (streak > 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulations");
            alert.setHeaderText("Streak!");
//            Label label = new Label("You have been practising names for " + streak + " days in a row!");
//            label.setWrapText(true);
//            alert.getDialogPane().setContent(label);
            alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            alert.setContentText("You have been practising names for " + streak + " days in a row!");
            alert.showAndWait();
        }


        // Create or overwrite streak file
        try {
            PrintWriter writer = new PrintWriter("streak.txt", "UTF-8");
            writer.println(today + " " + streak);
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
