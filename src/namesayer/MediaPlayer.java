package namesayer;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MediaPlayer implements Initializable {
    @FXML
    private MediaView mediaViewer;

    private javafx.scene.media.MediaPlayer mp;

//    @FXML
//    private Slider volumeSlider;

    @FXML
    private ListView playList;

    private Media media;

    @FXML
    private CheckBox handleMode;

    @FXML
    private Text infoPlay;

    @FXML
    public void handleNext(ActionEvent event){

    }


    @FXML
    public void handleReplay(ActionEvent event){

    }

    @FXML
    protected void handleMicTest(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("micTest.fxml"));
        Stage window = new Stage();
        window.setTitle("Mic Test");
        window.setScene(new Scene(root, 300, 100));
        window.setMinWidth(300);
        window.setMinHeight(100);
        window.show();
    }

    @FXML
    public void handleBadQuality(javafx.event.ActionEvent event){
        /**
         * Bad quality recording
         */
    }


    @FXML
    protected void handleHome(javafx.event.ActionEvent event) throws IOException {

        Parent viewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene viewScene = new Scene(viewParent);

        // This line gets the stage info
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(viewScene);
        window.setTitle("Name Sayer");
        window.show();
    }
    private String name = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("./names/" + "se206-" + name + "wav.mp4");
        String path = file.getAbsolutePath();

        infoPlay.setText("");

        if (file.exists()) {
            media = new Media(new File(path).toURI().toString());
            mp = new javafx.scene.media.MediaPlayer(media);

            mediaViewer.setMediaPlayer(mp);
            mp.setAutoPlay(true);

//            // Volume controls
//            volumeSlider.setValue(mp.getVolume() * 100);
//            volumeSlider.valueProperty().addListener(new InvalidationListener() {
//                public void invalidated(Observable ov) {
//                    if (volumeSlider.isValueChanging()) {
//                        mp.setVolume(volumeSlider.getValue() / 100.0);
//                    }
//                }
//            });

            mp.setOnEndOfMedia(new Runnable() {
                public void run() {
                    //Code to run
                    mediaViewer.setMediaPlayer(null);

                }
            });



        }
        else {

        }
    }
}
