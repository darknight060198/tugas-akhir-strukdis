/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import model.Graph;
import model.ModelNode;

/**
 * FXML Controller class
 *
 * @author DarKnight98
 */
public class NodeLayoutController implements Initializable {

    @FXML
    private TableView<ModelNode> nodeTable;

    private Graph g;
    private StringProperty text;
    ObservableList<ModelNode> nodeList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        text = new SimpleStringProperty();

        nodeTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("number"));
        nodeTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
    }
    
    public void initVariable() {
        
        nodeList = g.getNodesAsObservableModel();

        nodeTable.setItems(nodeList);
    }

    @FXML
    public void addButtonOnClick(ActionEvent event) {
        showDialogAdd();
    }

    @FXML
    public void removeButtonOnClick(ActionEvent event) {

    }

    @FXML
    public void updateButtonOnClick(ActionEvent event) {

    }

    private void showDialogAdd() {
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("TestName");

        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Name");

        gridPane.add(name, 0, 0);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> name.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return name.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nameRes -> {
            System.out.println("From=" + nameRes);
        });
    }

    public void setG(Graph g) {
        this.g = g;
    }

    public StringProperty getText() {
        return text;
    }

}
