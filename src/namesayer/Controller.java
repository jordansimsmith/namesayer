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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Model model;

    @FXML
    private TreeView<Name> treeView;

    @FXML
    private ListView<Name> selectedList;

    @FXML
    private ListView<Name> recordingsList;

    @FXML
    public void handleAdd(ActionEvent e) {
        ObservableList<Name> checkedItems = FXCollections.observableArrayList(model.getCheckedNames());
        selectedList.setItems(checkedItems);
    }

    @FXML
    public void handleRecordingPlay (ActionEvent e)
    {
        // get current selection
        Name selected = recordingsList.getSelectionModel().getSelectedItem();

        // play audio
        List<Name> list = new ArrayList<>();
        list.add(selected);
        model.playAudio(list);
    }

    @FXML private CheckBox shuffleBox;

    @FXML
    protected void playAction(ActionEvent event) throws IOException {

        // switch view
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent viewParent = FXMLLoader.load(getClass().getResource("mediaPlayer.fxml"));
        Scene viewScene = new Scene(viewParent);
        window.setTitle("Practice Mode");
        window.setScene(viewScene);
        window.show();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // initialise model
        model = new ModelImpl();

        // set tree view
        treeView.setRoot(model.getTreeView().getRoot());
        treeView.setCellFactory(CheckBoxTreeCell.forTreeView());

        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            // only consider name versions
            if (!(newValue.getValue() instanceof NameVersion)){
                return;
            }

            // get user creations
            List<NameVersion> userCreations = model.getUserCreations(newValue.getValue());

            ObservableList<Name> recordings = FXCollections.observableArrayList(userCreations);

            recordingsList.setItems(recordings);
        });

    }


}
