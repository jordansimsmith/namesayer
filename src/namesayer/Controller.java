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
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TreeView<String> treeView;

    @FXML
    private ListView<String> practiceList;

    @FXML
    private ListView<String> playList;


    @FXML
    public void handleDataName (ActionEvent event)
    {

    }

    @FXML
    public void handlePracName (ActionEvent event)
    {

    }

//    @FXML
//    protected void newPrac(javafx.event.ActionEvent event) {
//
//    }
//
//    @FXML
//    protected void delPrac(ActionEvent event) {
//
//    }

    @FXML private CheckBox handleShuffle;

    @FXML
    protected void playAction(ActionEvent event) throws IOException {

        ObservableList<TreeItem<String>> selectedItems =  treeView.getSelectionModel().getSelectedItems();

        if(handleShuffle.isSelected()){
            /**
             * Shuffle the stack
             */
            Collections.shuffle(selectedItems);
        }

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent viewParent = FXMLLoader.load(getClass().getResource("mediaPlayer.fxml"));
        Scene viewScene = new Scene(viewParent);

        window.setScene(viewScene);
        window.show();

    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // System.out.println("You are initialising");
        updateList();

        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        treeView.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
//                ObservableList<TreeItem<String>> selectedItems =  treeView.getSelectionModel().getSelectedItems();
//
//                for(TreeItem<String> s : selectedItems){
//                    System.out.println("selected item " + s);
//                }

            }

        });
    }

    @FXML
    protected void updateList() {
        /**
         * Populate the treeview
         */

    }
}
