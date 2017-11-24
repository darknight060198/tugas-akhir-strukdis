/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
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
        
        c.setG(g);
        c.initVariable();
        
        consoleTextProperty.bindBidirectional(c.getText());
        
        consoleTextProperty.setValue("Menambahkan sebuah node ke dalam graph.");
        
        contentPane.getChildren().setAll(frame);
    }

    @FXML
    public void addEdgeRadioOnClick(ActionEvent event) throws IOException {
        addNodeRadio.setSelected(false);
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/EdgeLayout.fxml"));
        AnchorPane frame = fxmlLoader.load();
        
        EdgeLayoutController c = (EdgeLayoutController) fxmlLoader.getController();
        
//        c.setG(g);
//        
//        consoleTextProperty.bindBidirectional(c.getText());
//        
        consoleTextProperty.setValue("Menambahkan sebuah edge ke dalam graph.");
    }

    @FXML
    public void executeButtonOnClick(ActionEvent event) {

    }

}
