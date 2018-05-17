package gui;

import domein.DomeinController;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuSchermController extends BorderPane {

    private DomeinController domeinController;
    private ResourceBundle gekozentaal;
    @FXML
    private Button btnNieuweSpeler;
    @FXML
    private Button btnNieuweWedstrijd;
    @FXML
    private Button btnLaadWedstrijd;

    public MenuSchermController(DomeinController dc, ResourceBundle gekozentaal) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("menuScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        domeinController = dc;
        this.gekozentaal = gekozentaal;

        btnNieuweSpeler.setText(gekozentaal.getString("menuOptie1"));
        btnNieuweWedstrijd.setText(gekozentaal.getString("menuOptie2"));
        btnLaadWedstrijd.setText(gekozentaal.getString("menuOptie3"));
    }

    @FXML
    private void btnNieuweSpelerOnAction(ActionEvent event) {
        laadOptie(new Scene(new MaakNieuweSpelerController(domeinController, this.getScene(), gekozentaal)));
    }

    @FXML
    private void btnNieuweWedstrijdOnAction(ActionEvent event) {
        laadOptie(new Scene(new SelecteerSpelerController(domeinController, this.getScene(), gekozentaal)));
    }

    @FXML
    private void btnLaadWedstrijdOnAction(ActionEvent event) {
        if (domeinController.bewaardeWedstrijden().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(gekozentaal.getString("laden"));
            alert.setHeaderText("");
            alert.setContentText(gekozentaal.getString("geenWedstrijdenBeschikbaar"));
            alert.showAndWait();
        } else {
            laadOptie(new Scene(new Bestaande_wedstrijd_ladenController(domeinController, this.getScene(), gekozentaal)));
        }
    }

    private void laadOptie(Scene scene) {
        Stage primaryStage = (Stage) this.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
