/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author JASONYEHEZKIEL
 */
public class Node {
    public final int number;
    private String name;
    
    public Node(int number, String name){
        this.number = number;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
