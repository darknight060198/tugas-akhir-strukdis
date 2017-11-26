/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author DarKnight98
 */
public class ModelNode {
    private final IntegerProperty number;
    private final StringProperty name;

    public ModelNode(int number, String name) {
        this.number = new SimpleIntegerProperty(number);
        this.name = new SimpleStringProperty(name);
    }

    public int getNumber() {
        return number.getValue();
    }

    public String getName() {
        return name.getValue();
    }
}
