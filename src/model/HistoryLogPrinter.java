/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ferdian
 */
public class HistoryLogPrinter {

    private static HistoryLogPrinter instance;
    private BufferedWriter fileWriter;
    private File file;
    private boolean lastCommandIsReset;
    private String value;

    private HistoryLogPrinter() throws IOException {
        file = new File("console_log.txt");

        fileWriter = new BufferedWriter(new FileWriter(file));
        value = "";
    }

    public static HistoryLogPrinter getInstance() {
        if (instance == null) {
            try {
                instance = new HistoryLogPrinter();
            } catch (IOException ex) {

            }
        }
        return instance;
    }

    public void addNode(int kode, String name) {
        try {
            value += "Added node : " + kode + " with name " + name + "\n";
            fileWriter.append("Added node : " + kode + " with name " + name + "\n");
            System.out.println("add yes");
        } catch (IOException ex) {
            System.out.println("add no");
        }
        try {
            fileWriter.flush();
        } catch (IOException ex) {

        }
    }

    public void removeNode(int kode, String name) {
        try {
            value += "Removed node : " + kode + " with name " + name + "\n";
            fileWriter.append("Removed node : " + kode + " with name " + name + "\n");
        } catch (IOException ex) {

        }
        try {
            fileWriter.flush();
        } catch (IOException ex) {

        }
    }

    public void updateNode(int kode, String oldName, String newName) {
        try {
            value += "Updated node : " + kode + " with name " + oldName + " to " + newName + "\n";
            fileWriter.append("Updated node : " + kode + " with name " + oldName + " to " + newName + "\n");
        } catch (IOException ex) {

        }
        try {
            fileWriter.flush();
        } catch (IOException ex) {

        }
    }

    public void addEdge(String firstNode, String secondNode, double weight) {
        try {
            value += "Added Edge : " + firstNode + " to " + secondNode + " with weight " + weight + "\n";
            fileWriter.append("Added Edge : " + firstNode + " to " + secondNode + " with weight " + weight + "\n");
        } catch (IOException ex) {

        }
        try {
            fileWriter.flush();
        } catch (IOException ex) {

        }
        lastCommandIsReset = false;
    }

    public void removeEdge(String firstNode, String secondNode, double weight) {
        try {
            value += "Removed Edge : " + firstNode + " to " + secondNode + " with weight " + weight + "\n";
            fileWriter.append("Removed Edge : " + firstNode + " to " + secondNode + " with weight " + weight + "\n");
        } catch (IOException ex) {

        }
        try {
            fileWriter.flush();
        } catch (IOException ex) {

        }
        lastCommandIsReset = false;
    }

    public void updateEdge(String firstNode, String secondNode, double oldWeight, double newWeight) {
        try {
            value += "Updated Edge : " + firstNode + " to " + secondNode + " with weight " + oldWeight + " change to " + newWeight + "\n";
            fileWriter.append("Updated Edge : " + firstNode + " to " + secondNode + " with weight " + oldWeight + " change to " + newWeight + "\n");
        } catch (IOException ex) {

        }
        try {
            fileWriter.flush();
        } catch (IOException ex) {

        }
        lastCommandIsReset = false;
    }

    public void addMSTResult(String res) {
        try {
            if (!value.contains("FINISH")) {
                value += res;
                value += "FINISH";
                fileWriter.append(res);
                fileWriter.append("FINISH");
            }
        } catch (IOException ex) {

        }
        try {
            fileWriter.flush();
        } catch (IOException ex) {

        }
    }

    public String getValue() {
        if (value.contains("FINISH")) {
            return value;
        } else {
            return null;
        }
    }

}
