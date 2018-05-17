package gui;

import domein.DomeinController;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Wedstrijd_opslaanController extends BorderPane {

    @FXML
    private Label lblMessage;
    @FXML
    private Button btnVolgende;
    @FXML
    private Label lblOpslaan;
    @FXML
    private TextField txfNaam;
    @FXML
    private Label lblWedstrijdNaam;

    private DomeinController domeinController;
    private Scene set;
    private ResourceBundle gekozentaal;
    private Scene menu;

    public Wedstrijd_opslaanController(DomeinController domeinController, Scene set, Scene menu, ResourceBundle gekozentaal) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("wedstrijd_opslaan.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.domeinController = domeinController;
        this.set = set;
        this.gekozentaal = gekozentaal;
        this.menu = menu;
        lblOpslaan.setText(gekozentaal.getString("opslaan"));
        lblWedstrijdNaam.setText(gekozentaal.getString("wedstrijdNaam"));
        btnVolgende.setText(gekozentaal.getString("volgende"));
    }

    @FXML
    private void btnVolgendeOnAction(ActionEvent event) {
        if (!txfNaam.getText().isEmpty()) {
            try {
                if (!domeinController.isWedstrijdUniek(txfNaam.getText())) {
                    domeinController.slaWedstrijdOp(txfNaam.getText());
                    Alert opslaanAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    opslaanAlert.setHeaderText(gekozentaal.getString("verderSpelen?"));
                    opslaanAlert.setTitle(gekozentaal.getString("verderSpelen?"));
                    ButtonType buttonYes = new ButtonType(gekozentaal.getString("ja"));
                    ButtonType buttonNo = new ButtonType(gekozentaal.getString("neen"));
                    opslaanAlert.getButtonTypes().setAll(buttonYes, buttonNo);

                    Optional<ButtonType> result = opslaanAlert.showAndWait();
                    if (result.get() == buttonYes) {
                        Stage primaryStage = (Stage) this.getScene().getWindow();
                        primaryStage.setScene(set);
                        primaryStage.show();
                    } else {
                        Stage primaryStage = (Stage) this.getScene().getWindow();
                        primaryStage.setScene(menu);
                        primaryStage.show();
                    }
                } else {
                    lblMessage.setText(gekozentaal.getString("nietUniek"));
                }
            } catch (IllegalArgumentException iae) {
                lblMessage.setText(gekozentaal.getString(iae.getMessage()));
            }
        } else {
            lblMessage.setText(gekozentaal.getString("naamLeeg"));
        }
    }

}
