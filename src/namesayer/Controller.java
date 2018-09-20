package namesayer;

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
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Model model;

    @FXML
    private TreeView<Name> treeView;

    @FXML
    private ListView<Name> practiceList;

    @FXML
    private ListView<Name> playList;

    @FXML
    public void handleAdd(ActionEvent actionEvent) {
    }

    @FXML
    public void handleRecordingPlay (ActionEvent event)
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

        treeView.setOnMouseClicked(event -> {
            // TODO update user creation list on mouse click
        });
    }


}
