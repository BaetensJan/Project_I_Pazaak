package gui;

import domein.DomeinController;
import java.io.IOException;
import java.util.ArrayList;
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

public class SelecteerSpelerController extends BorderPane {

    @FXML
    private Label lblNogTeKiezenSpelers;
    @FXML
    private ListView<String> lvAlleSpelers;
    @FXML
    private ListView<String> lvGeselecteerdeSpelers;
    @FXML
    private Button btnVerwijder;
    @FXML
    private Button btnVoegToe;
    @FXML
    private Label lblBeschikbareSpelers;
    @FXML
    private Label lblGeselecteerdeSpelers;
    @FXML
    private Button btnVolgende;
    @FXML
    private Label lblMessage;
    @FXML
    private Label lblSpelerSelecteren;
    private DomeinController domeinController;
    private Scene menu;
    private ResourceBundle gekozentaal;

    public SelecteerSpelerController(DomeinController domeinController, Scene menu, ResourceBundle gekozentaal) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SelecteerSpeler.fxml"));
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

        List<String> namenLijst = domeinController.toonSpelers();
        lvAlleSpelers.setItems(FXCollections.observableArrayList(namenLijst));
        lvAlleSpelers.getSelectionModel().select(0);
        lblBeschikbareSpelers.setText(gekozentaal.getString("lijstSpelers"));
        btnVolgende.setText(gekozentaal.getString("volgende"));
        lblGeselecteerdeSpelers.setText(gekozentaal.getString("geselecteerdeSpelers"));
        lblSpelerSelecteren.setText(gekozentaal.getString("selecteerSpelers"));
        controlleerAantalSpelers();
    }

    @FXML
    private void btnVerwijderOnAction(ActionEvent event) {
        if (lvGeselecteerdeSpelers.getSelectionModel().getSelectedItem() != null) {
            String item = lvGeselecteerdeSpelers.getSelectionModel().getSelectedItem();
            lvGeselecteerdeSpelers.getItems().remove(item);
            lvAlleSpelers.getItems().add(item);
            controlleerAantalSpelers();
        }
    }

    @FXML
    private void btnVoegToeOnAction(ActionEvent event) {
        if (lvAlleSpelers.getSelectionModel().getSelectedItem() != null) {
            String item = lvAlleSpelers.getSelectionModel().getSelectedItem();
            lvAlleSpelers.getItems().remove(item);
            lvGeselecteerdeSpelers.getItems().add(item);
            controlleerAantalSpelers();
        }
    }

    @FXML
    private void btnVolgendeOnAction(ActionEvent event) {
        List<String> geselecteerdeSpelers = new ArrayList(), spelers = lvGeselecteerdeSpelers.getItems();
        for (String speler : spelers) {
            geselecteerdeSpelers.add(speler);
        }
        domeinController.maakWedstrijd(geselecteerdeSpelers);
        Scene scene = new Scene(new Kies_speler_WedstrijdStapel_makenController(domeinController, menu, gekozentaal));
        Stage primaryStage = (Stage) this.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void controlleerAantalSpelers() {
        if (lvGeselecteerdeSpelers.getItems().size() <= 0) {
            btnVerwijder.setDisable(true);
        } else {
            btnVerwijder.setDisable(false);
        }
        if (lvGeselecteerdeSpelers.getItems().size() >= 2) {
            btnVoegToe.setDisable(true);
            btnVolgende.setDisable(false);
        } else {
            btnVoegToe.setDisable(false);
            btnVolgende.setDisable(true);
        }
        lblNogTeKiezenSpelers.setText(gekozentaal.getString("nogTeSelecterenSpelers") + " " + (2 - lvGeselecteerdeSpelers.getItems().size()));
    }

}
