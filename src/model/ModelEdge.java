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
        this.src = new SimpleObjectProperty<Node>(src);
        this.dest = new SimpleObjectProperty<Node>(dest);
    }

    public DoubleProperty getWeight() {
        return weight;
    }

    public ObjectProperty<Node> getSrc() {
        return src;
    }

    public ObjectProperty<Node> getDest() {
        return dest;
    }
}
