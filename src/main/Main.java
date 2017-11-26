/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 *
 * @author JASONYEHEZKIEL
 */
public class Main extends Application {

    private Stage stage;
    private Pane basePane;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        stage.setOnCloseRequest((WindowEvent event) -> {
            //mengeluarkan dialog
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Are you sure to exit?");
            alert.setHeaderText(null);
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Platform.exit();
                System.exit(0);
            } else {
                alert.close();
                event.consume();
            }
        });

        initLayout();
        showSplash();
    }

    private void initLayout() {
        try {
            basePane = (Pane) FXMLLoader.load(Main.class.getResource("/view/MainLayout.fxml"));

            Scene scene = new Scene(basePane);

            stage.setScene(scene);
            stage.setTitle("Minimum Spanning Tree - Kruskal's Algorithm");
            stage.getIcons().add(new Image("/src/icon.png"));
            stage.sizeToScene();
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            System.out.println("CANNOT LOAD ROOT LAYOUT");
            System.out.println(ex.getMessage());
        }
    }

    /**
     * menampilkan splash screen saat pertama dibuka
     */
    private void showSplash() {
        try {
            AnchorPane paneSplash = FXMLLoader.load(Main.class.getResource("/view/Splash.fxml"));
            basePane.getChildren().setAll(paneSplash);
            stage.sizeToScene();

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), paneSplash);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            TranslateTransition moveOut = new TranslateTransition(Duration.millis(700));
            moveOut.setNode(paneSplash);
            moveOut.setDelay(Duration.millis(150));
            moveOut.setInterpolator(Interpolator.EASE_BOTH);
            moveOut.setToX(2000);

            paneSplash.setCache(true);
            paneSplash.setCacheHint(CacheHint.SPEED);

            fadeIn.play();

            fadeIn.setOnFinished((ActionEvent event) -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                moveOut.play();
            });

            moveOut.setOnFinished((ActionEvent event) -> {
                try {
                    BorderPane appPane = FXMLLoader.load(Main.class.getResource("/view/MainScreen.fxml"));
                    basePane.getChildren().setAll(appPane);
                    stage.sizeToScene();
//                    basePane.getStylesheets().add(Main.class.getResource("/src/CatchTheTargetStyleSheet.css").toExternalForm());
                } catch (IOException ex) {
                    System.out.println("CANNOT LOAD APP LAYOUT");
                    System.out.println(ex.getMessage());
                }
            });

        } catch (IOException ex) {
            System.out.println("CANNOT LOAD SPLASH LAYOUT");
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
