package namesayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    }

    @FXML private CheckBox shuffleBox;

    @FXML
    protected void playAction(ActionEvent event) throws IOException {

        // switch view
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent viewParent = FXMLLoader.load(getClass().getResource("mediaPlayer.fxml"));
        Scene viewScene = new Scene(viewParent);
        window.setTitle("Practise Mode");
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
