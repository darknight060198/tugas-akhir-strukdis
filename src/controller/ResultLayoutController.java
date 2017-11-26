/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.HistoryLogPrinter;

/**
 * FXML Controller class
 *
 * @author DarKnight98
 */
public class ResultLayoutController implements Initializable {

    @FXML
    private TextArea resLabel;
    
    private String hasilTemp;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String res = HistoryLogPrinter.getInstance().getValue();
        if (res != null) {
            hasilTemp = res;
            resLabel.setText(res);
        }
        else resLabel.setText(hasilTemp);
    }
}
