/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskotic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ferdian
 */
public class ErrorLogPrinter {

    private static ErrorLogPrinter instance;
    private FileWriter fileWriter;
    private final File file;
    private final Calendar calendar;

    private ErrorLogPrinter() throws IOException {
        file = new File("error_log.txt");
        fileWriter = new FileWriter(file, true);
        calendar = Calendar.getInstance();
    }

    public static ErrorLogPrinter getInstance() {
        if (instance == null) {
            try {
                instance = new ErrorLogPrinter();
            } catch (IOException ex) {

            }
        }
        return instance;
    }

    public void display(String message) {
        calendar.setTime(new Date(System.currentTimeMillis()));
        try {
            fileWriter.write("Error " + message + " " + calendar.getTime() + "\n");
        } catch (IOException ex) {

        }
    }

}
