/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author JASONYEHEZKIEL
 */
public class ModelEdge {
    private final DoubleProperty weight;
    private final ObjectProperty<Node> src, dest;
    
    public ModelEdge(double weight, Node src, Node dest){
        this.weight = new SimpleDoubleProperty(weight);
        this.src = new SimpleObjectProperty<>(src);
        this.dest = new SimpleObjectProperty<>(dest);
    }

    public double getWeight() {
        return weight.getValue();
    }

    public String getSrc() {
        return src.getValue().getName();
    }

    public String getDest() {
        return dest.getValue().getName();
    }
}
