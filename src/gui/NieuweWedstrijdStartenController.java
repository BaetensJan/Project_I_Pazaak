package gui;

import domein.DomeinController;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NieuweWedstrijdStartenController extends BorderPane {

    @FXML
    private Label lblNieuweWedstrijd;
    @FXML
    private Label lblSpeler;
    private DomeinController domeinController;
    private Kies_speler_WedstrijdStapel_makenController wedstrijdstapelSpelers;
    private ResourceBundle gekozentaal;
    private String speler;
    @FXML
    private Button btnKaartKopen;
    @FXML
    private Button btnSelecteerKaarten;

    /*Speler meegeven*/
    public NieuweWedstrijdStartenController(DomeinController domeinController, Kies_speler_WedstrijdStapel_makenController wedstrijdstapelSpelers, ResourceBundle gekozentaal, String speler) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("nieuweWedstrijdStarten.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        this.domeinController = domeinController;
        this.wedstrijdstapelSpelers = wedstrijdstapelSpelers;
        this.gekozentaal = gekozentaal;
        this.speler = speler;
        lblSpeler.setText(speler);
        lblNieuweWedstrijd.setText(gekozentaal.getString("wedstrijdstapelMaken"));
        btnKaartKopen.setText(gekozentaal.getString("koopKaartMenuKoopKaart"));
        btnSelecteerKaarten.setText(gekozentaal.getString("koopKaartMenuSelecteerKaart"));
    }

    @FXML
    private void btnKaartKopenOnAction(ActionEvent event) {
        Scene scene = new Scene(new Kaart_kopenController(domeinController, this.getScene(), gekozentaal, speler));
        Stage primaryStage = (Stage) this.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void btnSelecteerKaartenOnAction(ActionEvent event) {
        Scene scene = new Scene(new WedstrijdstapelController(domeinController, wedstrijdstapelSpelers, gekozentaal, speler));
        Stage primaryStage = (Stage) this.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
