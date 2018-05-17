/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author baete
 */
public class Kaart_kopenController extends BorderPane {

    @FXML
    private Button btnVolgende;
    @FXML
    private Label lblPrijs;
    @FXML
    private Label lblKrediet;
    @FXML
    private Button btnKoopKaart;
    @FXML
    private TextArea txaInfo;
    @FXML
    private ListView<ImageView> lvKaarten;

    @FXML
    private Label lblKaartInfo;
    @FXML
    private Label lblBeschikbareKaarten;
    @FXML
    private Label lblMessage;
    private DomeinController domeinController;
    private ResourceBundle gekozentaal;
    private Scene kaartKeuze;
    private String speler;
    List<String> kaarten;

    public Kaart_kopenController(DomeinController domeinController, Scene kaartKeuze, ResourceBundle gekozentaal, String speler) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("kaart_kopen.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        lvKaarten.setItems(FXCollections.observableArrayList());

        this.domeinController = domeinController;
        this.gekozentaal = gekozentaal;
        this.kaartKeuze = kaartKeuze;
        this.speler = speler;
        lblBeschikbareKaarten.setText(gekozentaal.getString("beschikbareKaarten"));
        lblKaartInfo.setText(gekozentaal.getString("kaartInfo"));
        btnVolgende.setText(gekozentaal.getString("volgende"));
        updateGUI();
        lvKaarten.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                if (lvKaarten.getSelectionModel().getSelectedItem() != null) {
                    btnKoopKaart.setDisable(false);
                    String kaart = kaarten.get(lvKaarten.getSelectionModel().getSelectedIndex());
                    if (domeinController.geefKaartPrijs(kaart) > domeinController.geefSpelerKrediet(speler)) {
                        btnKoopKaart.setDisable(true);
                    }
                    txaInfo.setText(kaart);
                    lblPrijs.setText(gekozentaal.getString("kostprijs") + String.format("%.0f", domeinController.geefKaartPrijs(kaart)));
                }
            }
        });
    }

    @FXML
    private void btnVolgendeOnAction(ActionEvent event) {
        Stage primaryStage = (Stage) this.getScene().getWindow();
        primaryStage.setScene(kaartKeuze);
        primaryStage.show();
    }

    @FXML
    private void btnKoopKaartOnAction(ActionEvent event) {
        try {
            domeinController.koopKaart(kaarten.get(lvKaarten.getSelectionModel().getSelectedIndex()), speler);
            updateGUI();
        } catch (IllegalArgumentException iae) {
            lblMessage.setText(gekozentaal.getString(iae.getMessage()));
        }
    }

    private void updateGUI() {
        btnKoopKaart.setDisable(true);
        lblKrediet.setText(gekozentaal.getString("spelerToString4") + String.format("%.0f", domeinController.geefSpelerKrediet(speler)));
        lblPrijs.setText("");
        kaarten = domeinController.getKaartenTeKopen(speler);
        lvKaarten.setItems(FXCollections.observableArrayList());
        String url = "/resources/";
        String omschrijving;
        BufferedImage bufferedImage;
        Image image = null;
        for (String kaart : kaarten) {
            omschrijving = kaart;
            omschrijving = omschrijving.replaceAll("/", "_");
            try {
                System.out.println(omschrijving);
                bufferedImage = ImageIO.read(this.getClass().getResource(url + omschrijving + ".png"));
                image = SwingFXUtils.toFXImage(bufferedImage, null);
                lvKaarten.getItems().add(new ImageView(image));
            } catch (IOException ex) {
                Logger.getLogger(Kaart_kopenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
