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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.Edge;
import model.ErrorAlertDisplayer;
import model.ErrorLogPrinter;
import model.Graph;
import model.HistoryLogPrinter;
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
    private ArrayList<Edge> edges;
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
        synchronizeAll();
    }

    @FXML
    public void addWCodeButtonOnClick(ActionEvent event) {
        synchronizeAll();
        showDialogAddWCode();
        synchronizeAll();
    }

    @FXML
    public void addWNameButtonOnClick(ActionEvent event) {
        synchronizeAll();
        showDialogAddWName();
        synchronizeAll();
    }

    @FXML
    public void removeButtonOnClick(ActionEvent event) {
        int idx = edgeTable.getSelectionModel().getSelectedIndex();
        if (idx < 0) {
            return;
        }
        showDialogRemove();
    }

    @FXML
    public void updateButtonOnClick(ActionEvent event) {
        int idx = edgeTable.getSelectionModel().getSelectedIndex();
        if (idx < 0) {
            return;
        }
        showDialogUpdate();
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
            try {
                int src = Integer.parseInt(nameRes[0]);
                int dst = Integer.parseInt(nameRes[1]);
                double weight = Double.parseDouble(nameRes[2]);

                if (g.addEdges(src, dst, weight)) {
                    edgeList.add(new ModelEdge(weight, nodes.get(src), nodes.get(dst)));
                    String res = "Success to add node!!\nAdded edge from " + nodes.get(src).getName() + " to " + nodes.get(src).getName() + " with weight " + weight + "!";
                    text.setValue(res);
                    HistoryLogPrinter.getInstance().addEdge(nameRes[0], nameRes[1], weight);
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                text.setValue("Failed to add edge!!\nNode code is not valid!");
                ErrorLogPrinter.getInstance().display("\nFailed to add edge!!\nNode code is not valid!\n" + e.getMessage() + "\n");
                String message = "";
                for (int i = 0; i < 5; i++) {
                    message += e.getStackTrace()[i].toString() + "\n";
                }
                ErrorAlertDisplayer.getInstance().display("Error!", "Failed to add edge!!\nNode code is not valid!", message);
            }
        });
    }

    private void showDialogAddWName() {
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
        Label destT = new Label("Destination :");
        Label weightT = new Label("Weight :");
        TextField weightI = new TextField();
        weightI.setPromptText("Weight");
        ChoiceBox<String> sourceI = new ChoiceBox<>(FXCollections.observableArrayList(nodeList));
        sourceI.getSelectionModel().selectFirst();
        ChoiceBox<String> destI = new ChoiceBox<>(FXCollections.observableArrayList(nodeList));
        sourceI.getSelectionModel().selectFirst();

        UnaryOperator<Change> integerFilter = change -> {
            String input = change.getText();
            if (input.matches("[0-9]*")) {
                return change;
            }
            return null;
        };

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
                return new String[]{Integer.toString(sourceI.getSelectionModel().getSelectedIndex()), Integer.toString(destI.getSelectionModel().getSelectedIndex()), weightI.getText()};
            }
            return null;
        });

        Optional<String[]> result = dialog.showAndWait();

        result.ifPresent(nameRes -> {
            try {
                int src = Integer.parseInt(nameRes[0]);
                int dst = Integer.parseInt(nameRes[1]);
                double weight = Double.parseDouble(nameRes[2]);
                
//                if (src == dst) 

                if (g.addEdges(src, dst, weight)) {
                    edgeList.add(new ModelEdge(weight, nodes.get(src), nodes.get(dst)));
                    String res = "Success to add node!!\nAdded edge from " + nodes.get(src).getName() + " to " + nodes.get(src).getName() + " with weight " + weight + "!";
                    text.setValue(res);
                    HistoryLogPrinter.getInstance().addEdge(nameRes[0], nameRes[1], weight);
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                text.setValue("Failed to add edge!!\nNode code is not valid!");
                ErrorLogPrinter.getInstance().display("\nFailed to add edge!!\nNode code is not valid!\n" + e.getMessage() + "\n");
                String message = "";
                for (int i = 0; i < 5; i++) {
                    message += e.getStackTrace()[i].toString() + "\n";
                }
                ErrorAlertDisplayer.getInstance().display("Error!", "Failed to add edge!!\nNode code is not valid!", message);
            }
        });
    }
    
    private void showDialogRemove() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Edge");
        alert.setHeaderText("Are you sure to delete this selected edge?");
        alert.setContentText(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int idx = edgeTable.getSelectionModel().getSelectedIndex();
            if (idx >= 0 && g.removeEdge(idx)) {
                text.setValue("Edge " + idx + "("
                        + nodeList.get(edges.get(idx).getSrc().getNumber()) + " -> " + nodeList.get(edges.get(idx).getDest().getNumber()) + " : " + edgeList.get(idx).getWeight() + ") successfully removed!");
                HistoryLogPrinter.getInstance().removeEdge(nodeList.get(edges.get(idx).getSrc().getNumber()), nodeList.get(edges.get(idx).getDest().getNumber()), edgeList.get(idx).getWeight());
                synchronizeAll();
            } else {
                text.setValue("Edge " + idx + "("
                        + nodeList.get(edges.get(idx).getSrc().getNumber()) + " -> " + nodeList.get(edges.get(idx).getDest().getNumber()) + " : " + edgeList.get(idx).getWeight() + ") failed to remove!");
            }
        } else {
            alert.close();
        }
    }

    private void showDialogUpdate() {
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Update Edge");

        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 10, 10));

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

        weightI.setTextFormatter(new TextFormatter<>(integerFilter));

        gridPane.add(weightT, 0, 0);
        gridPane.add(weightI, 1, 0);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> weightI.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return weightI.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(nameRes -> {
            int idx = edgeTable.getSelectionModel().getSelectedIndex();
            g.updateEdgeWeight(idx, Double.parseDouble(nameRes));
            text.setValue("Edge " + idx + "("
                    + nodeList.get(edges.get(idx).getSrc().getNumber()) + " -> " + nodeList.get(edges.get(idx).getDest().getNumber()) + " : " + edgeList.get(idx).getWeight() + ") weight updated to " + Double.parseDouble(nameRes));
            HistoryLogPrinter.getInstance().updateEdge(nodeList.get(edges.get(idx).getSrc().getNumber()), nodeList.get(edges.get(idx).getDest().getNumber()), edgeList.get(idx).getWeight(), Double.parseDouble(nameRes));
            synchronizeAll();
        });
    }

    private void synchronizeAll() {
        this.nodeList = g.getNodeListAsString();
        this.nodes = g.getNodes();
        this.edges = g.getEdges();
        this.edgeList = g.getModelEdgeAsObservableList();
        this.edgeTable.setItems(edgeList);
    }
}
