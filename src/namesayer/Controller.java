package namesayer;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;


import java.io.File;
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
    protected void newPrac(ActionEvent event) {

    }

    @FXML
    protected void delPrac(ActionEvent event) {

    }

    @FXML private CheckBox handleShuffle;

    @FXML
    protected void playAction(ActionEvent event) {

        ObservableList<TreeItem<String>> selectedItems =  treeView.getSelectionModel().getSelectedItems();

        if(handleShuffle.isSelected()){
            /**
             * Shuffle the stack
             */
            Collections.shuffle(selectedItems);
        }
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // System.out.println("You are initialising");
        updateList();

        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        treeView.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                ObservableList<TreeItem<String>> selectedItems =  treeView.getSelectionModel().getSelectedItems();

                for(TreeItem<String> s : selectedItems){
                    System.out.println("selected item " + s);
                }

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
