package gui;

import domein.DomeinController;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class WedstrijdstapelController extends BorderPane {

    @FXML
    private Button btnVolgende;
    @FXML
    private Label lblSpeler;
    @FXML
    private Label lblNogTeKiezenKaarten;
    @FXML
    private ListView<ImageView> lvBeschikbareKaarten;
    @FXML
    private ListView<ImageView> lvGeselecteerdeKaarten;
    @FXML
    private TextArea txaInfo;
    @FXML
    private Button btnVoegToe;
    @FXML
    private Button btnVerwijder;
    @FXML
    private Label lblKaartInfo;
    @FXML
    private Label lblBeschikbareKaarten;
    @FXML
    private Label lblMessage;

    private DomeinController domeinController;
    private Kies_speler_WedstrijdStapel_makenController wedstrijdstapelSpelers;
    private ResourceBundle gekozentaal;
    private String speler;
    private List<String> kaarten, geselecteerdeKaarten;
    private int plaats;

    WedstrijdstapelController(DomeinController domeinController, Kies_speler_WedstrijdStapel_makenController wedstrijdstapelSpelers, ResourceBundle gekozentaal, String speler) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("wedstrijdstapel.fxml"));
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
        plaats = -1;
        kaarten = new ArrayList();
        for (String kaart : domeinController.getKaartenSpeler(speler)) {
            kaarten.add(kaart);
        }

        lblBeschikbareKaarten.setText(gekozentaal.getString("beschikbareKaarten"));
        lblKaartInfo.setText(gekozentaal.getString("kaartInfo"));
        btnVerwijder.setText(gekozentaal.getString("verwijder"));
        btnVoegToe.setText(gekozentaal.getString("voegToe"));
        lblSpeler.setText(speler);
        btnVolgende.setText(gekozentaal.getString("volgende"));
        geselecteerdeKaarten = new ArrayList();

        updateGUI();
        lvBeschikbareKaarten.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                if (lvBeschikbareKaarten.getSelectionModel().getSelectedItem() != null) {
                    String kaart = kaarten.get(lvBeschikbareKaarten.getSelectionModel().getSelectedIndex());
                    txaInfo.setText(kaart);
                }
            }
        });
    }

    @FXML
    private void btnVolgendeOnAction(ActionEvent event) {
        try {
            domeinController.maakWedstrijdstapel(speler, geselecteerdeKaarten);
            Stage primaryStage = (Stage) this.getScene().getWindow();
            primaryStage.setScene(wedstrijdstapelSpelers.getScene());
            primaryStage.show();
            wedstrijdstapelSpelers.updateGUI();
        } catch (IllegalArgumentException iae) {
            lblMessage.setText(gekozentaal.getString(iae.getMessage()));
        }
    }

    @FXML
    private void btnVoegToeOnAction(ActionEvent event) {
        if (lvBeschikbareKaarten.getSelectionModel().getSelectedItem() != null) {
            int index = lvBeschikbareKaarten.getSelectionModel().getSelectedIndex();
            geselecteerdeKaarten.add(kaarten.get(index));
            kaarten.remove(index);
            updateGUI();
        }
    }

    @FXML
    private void btnVerwijderOnAction(ActionEvent event) {
        if (lvGeselecteerdeKaarten.getSelectionModel().getSelectedItem() != null) {
            int index = lvGeselecteerdeKaarten.getSelectionModel().getSelectedIndex();
            kaarten.add(geselecteerdeKaarten.get(index));
            geselecteerdeKaarten.remove(index);
            updateGUI();
        }
    }

    private void updateGUI() {
        if (geselecteerdeKaarten.size() < 6) {
            btnVolgende.setDisable(true);
        } else {
            btnVolgende.setDisable(false);
        }
        if (geselecteerdeKaarten.isEmpty()) {
            btnVerwijder.setDisable(true);
        } else {
            btnVerwijder.setDisable(false);
        }
        if (geselecteerdeKaarten.size() == 6) {
            btnVoegToe.setDisable(true);
        } else {
            btnVoegToe.setDisable(false);
        }
        lblNogTeKiezenKaarten.setText(gekozentaal.getString("nogTeKiezenKaarten") + (6 - geselecteerdeKaarten.size()));
        lvBeschikbareKaarten.setItems(FXCollections.observableArrayList());
        lvGeselecteerdeKaarten.setItems(FXCollections.observableArrayList());
        String url = "/resources/";
        String omschrijving;
        BufferedImage bufferedImage;
        Image image = null;
        for (String kaart : kaarten) {
            omschrijving = kaart;
            omschrijving = omschrijving.replaceAll("/", "_");
            try {
                bufferedImage = ImageIO.read(this.getClass().getResource(url + omschrijving + ".png"));
                image = SwingFXUtils.toFXImage(bufferedImage, null);

                lvBeschikbareKaarten.getItems().add(new ImageView(image));
            } catch (IOException ex) {
                Logger.getLogger(Kaart_kopenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (String kaart : geselecteerdeKaarten) {
            omschrijving = kaart;
            omschrijving = omschrijving.replaceAll("/", "_");
            try {
                bufferedImage = ImageIO.read(this.getClass().getResource(url + omschrijving + ".png"));
                image = SwingFXUtils.toFXImage(bufferedImage, null);

                lvGeselecteerdeKaarten.getItems().add(new ImageView(image));
            } catch (IOException ex) {
                Logger.getLogger(Kaart_kopenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
