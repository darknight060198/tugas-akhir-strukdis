/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.Graph;
import model.ModelEdge;
import model.ModelNode;
import model.Node;

/**
 * FXML Controller class
 *
 * @author DarKnight98
 */
public class EdgeLayoutController implements Initializable {

    @FXML
    private TableView<ModelEdge> edgeTable;

    private Graph g;
    private StringProperty text;
    private ObservableList<ModelEdge> edgeList;
    private ArrayList<Node> nodes;
    private ArrayList<String> nodeList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        text = new SimpleStringProperty();

        edgeTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("src"));
        edgeTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("dest"));
        edgeTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("weight"));
    }

    public void initVariable(Graph g) {
        this.g = g;
        edgeList = g.getModelEdgeAsObservableList();
        nodes = g.getNodes();
        edgeTable.setItems(edgeList);
        synchronizeNodeList();
    }

    @FXML
    public void addWCodeButtonOnClick(ActionEvent event) {
        showDialogAddWCode();
    }

    @FXML
    public void addWNameButtonOnClick(ActionEvent event) {
        synchronizeNodeList();
//        showDialogAdd();
    }

    @FXML
    public void removeButtonOnClick(ActionEvent event) {

    }

    @FXML
    public void updateButtonOnClick(ActionEvent event) {

    }

    public StringProperty getText() {
        return text;
    }

    private void showDialogAddWCode() {
        // Create the custom dialog.
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Add Edge");

        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 10, 10));

        Label sourceT = new Label("Source :");
        TextField sourceI = new TextField();
        sourceI.setPromptText("Source");
        Label destT = new Label("Destination :");
        TextField destI = new TextField();
        destI.setPromptText("Destination");
        Label weightT = new Label("Weight :");
        TextField weightI = new TextField();
        weightI.setPromptText("Weight");

        UnaryOperator<Change> integerFilter = change -> {
            String input = change.getText();
            if (input.matches("[0-9]*")) {
                return change;
            }
            return null;
        };

        sourceI.setTextFormatter(new TextFormatter<>(integerFilter));
        destI.setTextFormatter(new TextFormatter<>(integerFilter));
        weightI.setTextFormatter(new TextFormatter<>(integerFilter));

        gridPane.add(sourceT, 0, 0);
        gridPane.add(sourceI, 1, 0);
        gridPane.add(destT, 0, 1);
        gridPane.add(destI, 1, 1);
        gridPane.add(weightT, 0, 2);
        gridPane.add(weightI, 1, 2);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> sourceI.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new String[]{sourceI.getText(), destI.getText(), weightI.getText()};
            }
            return null;
        });

        Optional<String[]> result = dialog.showAndWait();

        result.ifPresent(nameRes -> {
            int src = Integer.parseInt(nameRes[0]);
            int dst = Integer.parseInt(nameRes[1]);
            double weight = Double.parseDouble(nameRes[2]);

            if (g.addEdges(src, dst, weight)) {
                edgeList.add(new ModelEdge(weight, nodes.get(src), nodes.get(dst)));
                String res = "Success to add node!!\nAdded edge from " + src + " to " + dst + "with weight" + weight + ".";
                text.setValue(res);
            } else {
                text.setValue("Failed to add edge!!");
            }
        });
    }

    /*
    private void showDialogAdd() {
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add Edge");

        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
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
                nodeList.add(new ModelNode(temp.number, temp.getName()));
                text.setValue("Success to add node!!");
                g.printNode();
            } else {
                text.setValue("Failed to add node!!");
            }
        });
    }*/
    private void synchronizeNodeList() {
        this.nodeList = g.getNodeListAsString();
    }
}