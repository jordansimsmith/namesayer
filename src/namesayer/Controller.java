package namesayer;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TreeView<String> treeView;





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
