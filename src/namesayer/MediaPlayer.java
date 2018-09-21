package namesayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MediaPlayer implements Initializable {

    private List<Name> names;
    private Model model;

    private NameVersion currentName;
    private int currentIndex;

    public MediaPlayer(List<Name> names, Model model) {
        this.names = names;
        this.model = model;
    }

    @FXML
    private ProgressBar recordPB;

    @FXML
    private MediaView mediaViewer;

    @FXML
    private ListView playList;

    @FXML
    private CheckBox handleMode;

    @FXML
    private Text infoPlay;

    @FXML
    public void handleNext(ActionEvent event) {

        if (currentIndex < names.size() - 1) {
            setCurrentName(currentIndex + 1);
        } else {
            setCurrentName(0);
        }
    }

    @FXML
    public void handlePrevious(ActionEvent event) {

        if (currentIndex > 0) {
            setCurrentName(currentIndex - 1);
        } else {
            setCurrentName(names.size() - 1);
        }
    }

    @FXML
    public void handlePlay(ActionEvent event) {
        System.out.println("Playing " + currentName);
    }

    @FXML
    protected void handleMicTest(ActionEvent event) throws IOException {

        // new stage
        Stage window = new Stage();

        // construct controller
        micTestController controller = new micTestController();

        // set on close action
        window.setOnCloseRequest(event1 -> controller.cleanUp());

        // set controller and fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("micTest.fxml"));
        loader.setController(controller);
        Parent root = loader.load();

        window.setTitle("Mic Test");
        window.setScene(new Scene(root, 300, 100));
        window.setMinWidth(300);
        window.setMinHeight(100);
        
        final boolean resizable = window.isResizable();
        window.setResizable(!resizable);
        window.setResizable(resizable);
        window.show();

    }

    @FXML
    public void handleBadQuality(ActionEvent event) {

        model.lowQualityName(currentName);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("Bad name successfully recorded.");
        alert.setContentText("Thank you for your input");
        alert.showAndWait();
    }


    @FXML
    protected void handleHome(ActionEvent event) throws IOException {

        Parent viewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene viewScene = new Scene(viewParent);

        // get stage info
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // change scene
        window.setScene(viewScene);
        window.setTitle("Name Sayer");
        window.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // set play list
        ObservableList<Name> nameList = FXCollections.observableList(names);
        playList.setItems(nameList);

        if (nameList.size() > 0) {
            setCurrentName(0);
        }

    }

    private void setCurrentName(int index) {

        Name name = names.get(index);

        // verify name
        if (!(name instanceof NameVersion)) {
            return;
        }

        // safe to cast
        this.currentName = (NameVersion) name;

        // set text
        infoPlay.setText(currentName.getName());

        // set current index
        this.currentIndex = index;

    }
}
