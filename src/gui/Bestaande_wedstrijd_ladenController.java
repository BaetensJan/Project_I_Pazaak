package gui;

import domein.DomeinController;
import java.io.IOException;
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

public class Bestaande_wedstrijd_ladenController extends BorderPane {

    @FXML
    private Label lblMessage;
    @FXML
    private Button btnVolgende;
    @FXML
    private Label lblSpelLaden;
    @FXML
    private ListView<String> lvWedstrijden;
    @FXML
    private Label lblBeschikbareWedstrijden;
    private DomeinController domeinConroller;
    private Scene menu;
    private ResourceBundle gekozentaal;

    public Bestaande_wedstrijd_ladenController(DomeinController domeinConroller, Scene menu, ResourceBundle gekozentaal) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("bestaande_wedstrijd_laden.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        this.domeinConroller = domeinConroller;
        this.menu = menu;
        this.gekozentaal = gekozentaal;
        lvWedstrijden.setItems(FXCollections.observableArrayList(domeinConroller.bewaardeWedstrijden()));
        lvWedstrijden.getSelectionModel().select(0);
        lblBeschikbareWedstrijden.setText(gekozentaal.getString("kiesWedstrijd"));
        lblSpelLaden.setText(gekozentaal.getString("laden"));
        btnVolgende.setText(gekozentaal.getString("volgende"));
    }

    @FXML
    private void btnVolgendeOnAction(ActionEvent event) {
        domeinConroller.laadWedstrijd(lvWedstrijden.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(new Speel_setController(domeinConroller, gekozentaal, menu));
        Stage primaryStage = (Stage) this.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
