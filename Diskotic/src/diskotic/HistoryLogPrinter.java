/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskotic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Ferdian
 */
public class HistoryLogPrinter {

    private static HistoryLogPrinter instance;
    private FileWriter fileWriter;
    private File file;
    private boolean lastCommandIsReset;

    private HistoryLogPrinter() throws IOException {
        file = new File("console_log.txt");

        file.createNewFile();

        fileWriter = new FileWriter(file, true);
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

    public void addGraph(String firstNode, String secondNode, long weight) {
        try {
            fileWriter.write("added " + firstNode + " to " + secondNode + " with weight " + weight + "\n");
        } catch (IOException ex) {

        }
        try {
            fileWriter.close();
        } catch (IOException ex) {

        }
        lastCommandIsReset = false;
    }

    public void reset() {
        if(lastCommandIsReset)return;
        try {
            fileWriter.write("Deleted all nodes and reset\n");
            fileWriter.close();
        } catch (IOException ex) {
            
        }
        lastCommandIsReset = true ;

    }

}
