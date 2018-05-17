package gui;

import domein.DomeinController;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Kies_speler_WedstrijdStapel_makenController extends BorderPane {

    @FXML
    private Label lblMessage;
    @FXML
    private ListView<String> lvSpelers;
    @FXML
    private Button btnCreate;
    @FXML
    private Label lblNieuweSpeler;
    private DomeinController domeinController;
    private ResourceBundle gekozentaal;
    private Scene menu;

    public Kies_speler_WedstrijdStapel_makenController(DomeinController domeinController, Scene menu, ResourceBundle gekozentaal) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("kies_speler_WedstrijdStapel_maken.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.domeinController = domeinController;
        this.menu = menu;
        this.gekozentaal = gekozentaal;
        lblNieuweSpeler.setText(gekozentaal.getString("wedstrijdstapelMaken"));
        btnCreate.setText(gekozentaal.getString("volgende"));
        updateGUI();
    }

    @FXML
    private void btnCreateOnAction(ActionEvent event) {
        String geselecteerdeSpeler = lvSpelers.getSelectionModel().getSelectedItem();
        Scene scene = new Scene(new NieuweWedstrijdStartenController(domeinController, this, gekozentaal, geselecteerdeSpeler));
        Stage primaryStage = (Stage) this.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    protected void updateGUI() {
        List<String> spelerLijst = domeinController.toonSpelersZonderWedstrijdstapel();
        lvSpelers.setItems(FXCollections.observableArrayList(spelerLijst));
        lvSpelers.getSelectionModel().select(0);
        if (spelerLijst.isEmpty()) {
            Scene scene = new Scene(new Speel_setController(domeinController, gekozentaal, menu));
            Stage primaryStage = (Stage) this.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }
}
