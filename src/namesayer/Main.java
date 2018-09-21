package namesayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("NameSayer");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();

        primaryStage.setMinHeight(720);
        primaryStage.setMinWidth(1280);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
