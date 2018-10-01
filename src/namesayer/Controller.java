package namesayer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Model model;

    @FXML
    private ListView<Name> namesList;

    @FXML
    private ListView<Name> selectedList;

    @FXML
    private Label nameBuilder;

    private List<Name> currentSelection = new ArrayList<>();

    @FXML
    public void handleAdd(ActionEvent e) {
        //TODO: add selected names to a practice queue
    }

    @FXML
    private CheckBox shuffleBox;

    @FXML
    protected void playAction(ActionEvent event) throws IOException {

        // get items selected for practise
        ObservableList<Name> selection = selectedList.getItems();

        if (selection.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot play an empty list");
            alert.setContentText("Please select one or more names to practise");
            alert.showAndWait();
            return;
        }

        if (shuffleBox.isSelected()) {
            Collections.shuffle(selection);
        }

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // construct controller
        MediaPlayer mpController = new MediaPlayer(selection, model);

        // set controller and fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mediaPlayer.fxml"));
        loader.setController(mpController);
        Parent viewParent = loader.load();

        Scene viewScene = new Scene(viewParent);
        window.setTitle("Practice Mode");
        window.setScene(viewScene);

        final boolean resizable = window.isResizable();
        window.setResizable(!resizable);
        window.setResizable(resizable);

        window.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // initialise model
        model = new ModelImpl();

        // initialise names database list
        ObservableList<Name> names = FXCollections.observableList(model.getNamesList());

        // set up listener for checkboxes
        namesList.setCellFactory(CheckBoxListCell.forListView(name -> {
            BooleanProperty observable = new SimpleBooleanProperty();

            observable.addListener((obs, wasSelected, isNowSelected) -> {

                if (wasSelected) {
                    currentSelection.remove(name);
                }

                if (isNowSelected) {
                    currentSelection.add(name);
                }

                nameBuilder.setText(currentSelection.toString());

            });

            return observable;
        }));

        namesList.setItems(names);

    }


}
