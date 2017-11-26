/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import model.ErrorAlertDisplayer;
import model.ErrorLogPrinter;
import model.Graph;

/**
 * FXML Controller class
 *
 * @author i16072
 */
public class MainScreenController implements Initializable {

    @FXML
    private RadioButton addNodeRadio;

    @FXML
    private RadioButton addEdgeRadio;

    @FXML
    private TextArea consoleText;

    @FXML
    private AnchorPane contentPane;

    private Graph g;
    private ReadOnlyStringWrapper consoleTextProperty;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        consoleTextProperty = new ReadOnlyStringWrapper();
        consoleText.textProperty().bind(consoleTextProperty);
        this.g = new Graph();
    }

    @FXML
    public void addNodeRadioOnClick(ActionEvent event) throws IOException {
        addEdgeRadio.setSelected(false);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/NodeLayout.fxml"));
        AnchorPane frame = fxmlLoader.load();

        NodeLayoutController c = (NodeLayoutController) fxmlLoader.getController();

        c.initVariable(g);

        consoleTextProperty.bindBidirectional(c.getText());

        consoleTextProperty.setValue("Adding a node to the graph.");

        contentPane.getChildren().setAll(frame);
    }

    @FXML
    public void addEdgeRadioOnClick(ActionEvent event) throws IOException {
        addNodeRadio.setSelected(false);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/EdgeLayout.fxml"));
        AnchorPane frame = fxmlLoader.load();

        EdgeLayoutController c = (EdgeLayoutController) fxmlLoader.getController();

        c.initVariable(g);

        consoleTextProperty.bindBidirectional(c.getText());

        consoleTextProperty.setValue("Adding an edge to the graph.");

        contentPane.getChildren().setAll(frame);
    }

    @FXML
    public void executeButtonOnClick(ActionEvent event) throws IOException {
        try {
            g.kruskalMST();
        } catch (Exception ex) {
            consoleTextProperty.setValue("\nCannot perform Kruskal's algorithm!\n See error_log for more details." + ex.getMessage() + "\n");
            String message = "";
            for (int i = 0; i < 5; i++) {
                message += ex.getStackTrace()[i].toString() + "\n";
            }
            ErrorAlertDisplayer.getInstance().display("Error!", ex.getMessage(), message);
            for (int i = 5; i < ex.getStackTrace().length; i++) {
                message += ex.getStackTrace()[i].toString() + "\n";
            }
            ErrorLogPrinter.getInstance().display("\n" + ex.getMessage() + "\n" + message + "\n");
            return;
        }

        consoleTextProperty.setValue("Executed!");

        addNodeRadio.setSelected(false);
        addEdgeRadio.setSelected(false);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/ResultLayout.fxml"));
        ScrollPane frame = fxmlLoader.load();

        contentPane.getChildren().setAll(frame);
    }

    @FXML
    public void clearAllButtonOnClick(ActionEvent event) {
        g.reset();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/NodeLayout.fxml"));
            NodeLayoutController c = (NodeLayoutController) fxmlLoader.getController();
            c.reset(g);

            fxmlLoader.setLocation(getClass().getResource("/view/EdgeLayout.fxml"));
            EdgeLayoutController c1 = (EdgeLayoutController) fxmlLoader.getController();
            c1.reset(g);
        } catch (Exception ignored) {
        }

        consoleTextProperty.setValue("All Clear!! :)");
    }

}
