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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import model.Graph;
import model.ModelNode;
import model.Node;

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
    private ObservableList<ModelNode> nodeList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        text = new SimpleStringProperty();

        nodeTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("number"));
        nodeTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("name"));
    }

    public void initVariable(Graph g) {
        this.g = g;
        nodeList = g.getModelNodesAsObservableList();
        nodeTable.setItems(nodeList);
    }

    @FXML
    public void addButtonOnClick(ActionEvent event) {
        showDialogAdd();
    }

    @FXML
    public void removeButtonOnClick(ActionEvent event) {
        int idx = nodeTable.getSelectionModel().getSelectedIndex();
        if (idx < 0) return;
        showDialogRemove();
    }

    @FXML
    public void updateButtonOnClick(ActionEvent event) {
        int idx = nodeTable.getSelectionModel().getSelectedIndex();
        if (idx < 0) return;
        showDialogUpdate();
    }

    private void showDialogAdd() {
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add Node");

        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 10, 10));

        Label keterangan = new Label("Insert Node Name :");
        TextField name = new TextField();
        name.setPromptText("Name");

        gridPane.add(keterangan, 0, 0);
        gridPane.add(name, 1, 0);

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
            Node temp = new Node(nodeList.size(), nameRes);
            if (g.addNode(temp)) {
                nodeList.add(new ModelNode(temp.getNumber(), temp.getName()));
                String res = "Success to add node!!\nAdded node number " + temp.getNumber() + " called " + temp.getName() + ".";
                text.setValue(res);
            } else {
                text.setValue("Failed to add node!!");
            }
        });
    }

    private void showDialogRemove() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Node");
        alert.setHeaderText("Are you sure to delete this selected node?");
        alert.setContentText(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int idx = nodeTable.getSelectionModel().getSelectedIndex();
            if (idx >= 0 && g.removeNode(idx)) {
                text.setValue("Node " + idx + "("
                        + nodeList.get(idx).getName() + ") successfully removed!");
                g.synchronizedNumberNode();
                nodeList = g.getModelNodesAsObservableList();
                nodeTable.setItems(nodeList);
            } else {
                text.setValue("Node " + idx + "("
                        + nodeList.get(idx).getName() + ") failed to remove!");
            }
        } else {
            alert.close();
        }
    }
    
    private void showDialogUpdate() {
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Update Node");

        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 10, 10));

        Label keterangan = new Label("Insert Node Name :");
        TextField name = new TextField();
        name.setPromptText("Name");

        gridPane.add(keterangan, 0, 0);
        gridPane.add(name, 1, 0);

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
            int idx = nodeTable.getSelectionModel().getSelectedIndex();
            g.updateNodeName(idx, nameRes);
            text.setValue("Node " + idx + "("
                        + nodeList.get(idx).getName() + ") succesfully updated to " + nameRes + "!");
            nodeList = g.getModelNodesAsObservableList();
            nodeTable.setItems(nodeList);
        });
    }

    public StringProperty getText() {
        return text;
    }

}
