package main;

import domein.DomeinController;
import gui.StartSchermController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PazaakFX extends Application {

    @Override
    public void start(Stage primaryStage) {

        DomeinController domeinController = new DomeinController();

        StartSchermController ssc = new StartSchermController(domeinController);

        Scene scene = new Scene(ssc, 1024, 720);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
