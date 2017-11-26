/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Ferdian
 */
public class ErrorAlertDisplayer {

    private static ErrorAlertDisplayer instance;
    private Alert alert;

    private ErrorAlertDisplayer() {
        alert = new Alert(AlertType.INFORMATION);

    }

    public static ErrorAlertDisplayer getInstance() {
        if (instance == null) {
            instance = new ErrorAlertDisplayer();
        }
        return instance;
    }

    public void display(String message, String error, String des) {
        alert.setTitle(message);
        alert.setHeaderText(error);
        alert.setContentText(des);
        alert.showAndWait();
    }

}