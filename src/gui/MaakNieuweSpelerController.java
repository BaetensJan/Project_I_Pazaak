/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author baete
 */
public class MaakNieuweSpelerController extends BorderPane {

    @FXML
    private TextArea txfNaam;
    @FXML
    private TextArea txfGeboortejaar;
    @FXML
    private Button btnCreate;
    @FXML
    private Label lblMessage;
    @FXML
    private Label lblNieuweSpeler;
    @FXML
    private Label lblNaam;
    @FXML
    private Label lblGeboortejaar;
    private DomeinController domeinController;
    private Scene menu;
    private ResourceBundle gekozentaal;

    public MaakNieuweSpelerController(DomeinController domeinController, Scene menu, ResourceBundle gekozentaal) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("maakNieuweSpeler.fxml"));
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
        lblNaam.setText(gekozentaal.getString("spelerToString2"));
        lblGeboortejaar.setText(gekozentaal.getString("spelerToString3"));
        btnCreate.setText(gekozentaal.getString("volgende"));
        lblNieuweSpeler.setText(gekozentaal.getString("nieuweSpeler"));
        lblMessage.setText("");
    }

    @FXML
    private void btnCreateOnAction(ActionEvent event) throws SQLException {
        try {
            if (!Pattern.matches("[0-9]{4}", txfGeboortejaar.getText())) {
                throw new IllegalArgumentException("inputMismatchErrorGeboortejaar");
            }
            String naam = txfNaam.getText();
            int geboortejaar = Integer.parseInt(txfGeboortejaar.getText());
            lblMessage.setText(gekozentaal.getString("spelerAanHetMaken"));
            domeinController.maakNieuweSpeler(naam, geboortejaar);
            Stage primaryStage = (Stage) this.getScene().getWindow();
            primaryStage.setScene(menu);
            primaryStage.show();
        } catch (IllegalArgumentException iae) {
            lblMessage.setText(gekozentaal.getString(iae.getMessage()));
        }
    }
}
