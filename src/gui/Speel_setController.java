package gui;

import domein.DomeinController;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class Speel_setController extends BorderPane {

    @FXML
    private Label lblSpeler2Info;
    @FXML
    private Label lblSpeler1Info;
    @FXML
    private Button btnEindeBeurt;
    @FXML
    private Label lblsetsGewonnen1;
    @FXML
    private Label lblsetsGewonnen2;
    @FXML
    private Button btnSpeelKaart;
    @FXML
    private Button btnBevries;
    @FXML
    private ImageView imgView21;
    @FXML
    private ImageView imgView22;
    @FXML
    private ImageView imgView23;
    @FXML
    private ComboBox<String> cmbKeuze;
    @FXML
    private ImageView imgView26;
    @FXML
    private ImageView imgView25;
    @FXML
    private ImageView imgView24;
    @FXML
    private ImageView imgView27;
    @FXML
    private ImageView imgView28;
    @FXML
    private ImageView imgView29;
    @FXML
    private ImageView imgView19;
    @FXML
    private ImageView imgView18;
    @FXML
    private ImageView imgView17;
    @FXML
    private ImageView imgView14;
    @FXML
    private ImageView imgView15;
    @FXML
    private ImageView imgView16;
    @FXML
    private ImageView imgView13;
    @FXML
    private ImageView imgView12;
    @FXML
    private ImageView imgView11;
    @FXML
    private Label lblSpelerScore1;
    @FXML
    private Label lblSpelerScore2;
    @FXML
    private ListView<ImageView> lvKaartenSpeler1;
    @FXML
    private ListView<ImageView> lvKaartenSpeler2;

    private DomeinController domeinController;
    private ResourceBundle gekozentaal;
    private Scene menu;
    private List<String> deSpelers;
    private List<ImageView> spelbord1, spelbord2;
    private List<List<String>> stapels;

    public Speel_setController(DomeinController domeinController, ResourceBundle gekozentaal, Scene menu) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("speel_set.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        this.domeinController = domeinController;
        this.gekozentaal = gekozentaal;
        this.menu = menu;
        lblsetsGewonnen1.setText(gekozentaal.getString("setsGewonnen"));
        lblsetsGewonnen2.setText(gekozentaal.getString("setsGewonnen"));
        btnBevries.setText(gekozentaal.getString("bevries"));
        btnSpeelKaart.setText(gekozentaal.getString("speelKaart"));
        btnEindeBeurt.setText(gekozentaal.getString("volgende"));
        domeinController.speelNieuweSet();
        deSpelers = domeinController.geefSpelers();
        spelbord1 = new ArrayList();
        spelbord2 = new ArrayList();
        spelbord1.add(imgView11);
        spelbord1.add(imgView12);
        spelbord1.add(imgView13);
        spelbord1.add(imgView14);
        spelbord1.add(imgView15);
        spelbord1.add(imgView16);
        spelbord1.add(imgView17);
        spelbord1.add(imgView18);
        spelbord1.add(imgView19);
        spelbord2.add(imgView21);
        spelbord2.add(imgView22);
        spelbord2.add(imgView23);
        spelbord2.add(imgView24);
        spelbord2.add(imgView25);
        spelbord2.add(imgView26);
        spelbord2.add(imgView27);
        spelbord2.add(imgView28);
        spelbord2.add(imgView29);

        updateGUI();

        lvKaartenSpeler1.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                cmbKeuze.setItems(FXCollections.observableArrayList());
                int index = lvKaartenSpeler1.getSelectionModel().getSelectedIndex();
                String kaartOmshrijving = stapels.get(0).get(index);
                String type = domeinController.geefKaartType(kaartOmshrijving);
                cmbKeuze.setDisable(false);
                if (type.equals("+/-")) {
                    cmbKeuze.getItems().add("+");
                    cmbKeuze.getItems().add("-");
                    btnSpeelKaart.setDisable(true);
                } else if (type.equals("x+/-y")) {
                    cmbKeuze.getItems().add("+x");
                    cmbKeuze.getItems().add("-x");
                    cmbKeuze.getItems().add("+y");
                    cmbKeuze.getItems().add("-y");
                    btnSpeelKaart.setDisable(true);
                } else {
                    cmbKeuze.setDisable(true);
                    btnSpeelKaart.setDisable(false);
                }
            }
        });

        lvKaartenSpeler2.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                cmbKeuze.setItems(FXCollections.observableArrayList());
                int index = lvKaartenSpeler2.getSelectionModel().getSelectedIndex();
                String kaartOmshrijving = stapels.get(1).get(index);
                String type = domeinController.geefKaartType(kaartOmshrijving);
                cmbKeuze.setDisable(false);
                System.out.println(type);
                if (type.equals("+/-")) {
                    cmbKeuze.getItems().add("+");
                    cmbKeuze.getItems().add("-");
                    btnSpeelKaart.setDisable(true);
                } else if (type.equals("x+/-y")) {
                    cmbKeuze.getItems().add("+x");
                    cmbKeuze.getItems().add("-x");
                    cmbKeuze.getItems().add("+y");
                    cmbKeuze.getItems().add("-y");
                    btnSpeelKaart.setDisable(true);
                } else {
                    cmbKeuze.setDisable(true);
                    btnSpeelKaart.setDisable(false);
                }
            }
        });
    }

    private void emptyImages() {
        for (ImageView iv : spelbord1) {
            iv.setImage(null);
        }
        for (ImageView iv : spelbord2) {
            iv.setImage(null);
        }
    }

    private void updateGUI() {
        cmbKeuze.setDisable(true);
        emptyImages();
        List<Integer> gewonnenSets = domeinController.geefAantalGewonnenSets();
        lblSpelerScore1.setText("" + gewonnenSets.get(0));
        lblSpelerScore2.setText("" + gewonnenSets.get(1));
        List<Integer> scores = domeinController.geefScores();
        lblSpeler1Info.setText(deSpelers.get(0) + " " + gekozentaal.getString("score") + scores.get(0));
        lblSpeler2Info.setText(deSpelers.get(1) + " " + gekozentaal.getString("score") + scores.get(1));
        List<List<String>> kaartenSpelbord;
        stapels = domeinController.geefBeideStapels();
        kaartenSpelbord = domeinController.geefBeideWedstrijdkaarten();
        String huidigeSpeler = domeinController.getSpelerAanBeurt();
        if (kaartenSpelbord.get(deSpelers.indexOf(huidigeSpeler)).size() >= 9) {
            btnEindeBeurt.setDisable(true);
            domeinController.bevriesSpeler();
        }
        List<ImageView> spelbord;
        ListView<ImageView> hand;
        lvKaartenSpeler1.setItems(FXCollections.observableArrayList());
        lvKaartenSpeler2.setItems(FXCollections.observableArrayList());
        String url = "/resources/";
        String omschrijving;
        BufferedImage bufferedImage;
        Image image = null;
        for (List<String> lijst : kaartenSpelbord) {
            if (kaartenSpelbord.indexOf(lijst) == 0) {
                spelbord = spelbord1;
            } else {
                spelbord = spelbord2;
            }
            for (int i = 0; i < lijst.size(); i++) {
                omschrijving = lijst.get(i);
                omschrijving = omschrijving.replaceAll("/", "_");
                image = null;
                try {
                    bufferedImage = ImageIO.read(this.getClass().getResource(url + omschrijving + ".png"));
                    image = SwingFXUtils.toFXImage(bufferedImage, null);
                    spelbord.get(i).setImage(image);
                } catch (IOException ex) {
                    Logger.getLogger(Kaart_kopenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        for (List<String> lijst : stapels) {
            if (stapels.indexOf(lijst) == 0) {
                hand = lvKaartenSpeler1;
            } else {
                hand = lvKaartenSpeler2;
            }
            for (int i = 0; i < lijst.size(); i++) {
                omschrijving = lijst.get(i);
                omschrijving = omschrijving.replaceAll("/", "_");
                image = null;
                try {
                    bufferedImage = ImageIO.read(this.getClass().getResource(url + omschrijving + ".png"));
                    image = SwingFXUtils.toFXImage(bufferedImage, null);
                    ImageView imgv = new ImageView(image);
                    imgv.setPreserveRatio(true);
                    imgv.setFitWidth(70);
                    hand.getItems().add(imgv);
                } catch (IOException ex) {
                    Logger.getLogger(Kaart_kopenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (deSpelers.indexOf(huidigeSpeler) == 0) {
            lvKaartenSpeler2.setDisable(true);
            lvKaartenSpeler1.setDisable(false);
        } else {
            lvKaartenSpeler2.setDisable(false);
            lvKaartenSpeler1.setDisable(true);
        }
        if (isSetGedaan()) {
            eindeSet();
        }
    }

    @FXML
    private void btnEindeBeurtOnAction(ActionEvent event) {
        domeinController.beeindigSpeler();
        updateGUI();
    }

    @FXML
    private void btnSpeelKaartOnAction(ActionEvent event) {
        String huidigeSpeler = domeinController.getSpelerAanBeurt();
        int index = deSpelers.indexOf(huidigeSpeler);
        List<String> stapel = stapels.get(index);
        ListView<ImageView> lijst;
        if (index == 0) {
            lijst = lvKaartenSpeler1;
        } else {
            lijst = lvKaartenSpeler2;
        }
        String kaart = stapel.get(lijst.getSelectionModel().getSelectedIndex());
        if (cmbKeuze.isDisabled()) {
            domeinController.speelKaart(kaart);
        } else {
            domeinController.speelKaart(kaart, cmbKeuze.getSelectionModel().getSelectedItem());
        }
        updateGUI();
    }

    @FXML
    private void btnBevriesOnAction(ActionEvent event) {
        domeinController.bevriesSpeler();
        updateGUI();
    }

    @FXML
    private void cmbKeuzeOnAction(ActionEvent event) {
        if (cmbKeuze.getSelectionModel().getSelectedItem() != null) {
            btnSpeelKaart.setDisable(false);
        } else {
            btnSpeelKaart.setDisable(true);
        }
    }

    private boolean isSetGedaan() {
        boolean isGedaan;
        if (domeinController.geefScores().get(0) > 20) {
            isGedaan = true;
        } else if (domeinController.geefScores().get(1) > 20) {
            isGedaan = true;
        } else if (domeinController.geefAantalKaarten().get(0) > 9) {
            isGedaan = true;
        } else if (domeinController.geefAantalKaarten().get(1) > 9) {
            isGedaan = true;
        } else if (domeinController.geefIsBevroren().get(0) == true && domeinController.geefIsBevroren().get(1) == true) {
            isGedaan = true;
        } else {
            isGedaan = false;
        }
        return isGedaan;
    }

    private void eindeSet() {
        String gewonnen = domeinController.controlleerSetGewonnen();
        Alert alert = new Alert(AlertType.INFORMATION);
        if (gewonnen != null) {
            alert.setHeaderText(gekozentaal.getString("deWinnaarIs"));
            alert.setContentText(gekozentaal.getString("deWinnaarIs") + gewonnen);
        } else {
            alert.setHeaderText(gekozentaal.getString("deWinnaarIs"));
            alert.setContentText(gekozentaal.getString("gelijkSpel"));
        }
        alert.showAndWait();
        String winnaar = domeinController.controlleerWedstrijdGewonnen();
        if (winnaar == null) {
            Alert opslaanAlert = new Alert(AlertType.CONFIRMATION);
            opslaanAlert.setHeaderText(gekozentaal.getString("opslaan?"));
            opslaanAlert.setTitle(gekozentaal.getString("opslaan?"));
            ButtonType buttonYes = new ButtonType(gekozentaal.getString("ja"));
            ButtonType buttonNo = new ButtonType(gekozentaal.getString("neen"));
            opslaanAlert.getButtonTypes().setAll(buttonYes, buttonNo);

            Optional<ButtonType> result = opslaanAlert.showAndWait();
            if (result.get() == buttonYes) {
                Scene scene = new Scene(new Wedstrijd_opslaanController(domeinController, this.getScene(), menu, gekozentaal));
                Stage primaryStage = (Stage) this.getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.show();
            }
            domeinController.speelNieuweSet();
            updateGUI();
        } else {
            alert.setHeaderText(gekozentaal.getString("deWinnaarIs"));
            alert.setContentText(String.format("%s%n%s%n%s%n", (gekozentaal.getString("deWinnaarIs") + winnaar), deSpelers.get(0) + ":" + domeinController.geefAantalGewonnenSets().get(0),
                    deSpelers.get(1) + ":" + domeinController.geefAantalGewonnenSets().get(1)));
            alert.showAndWait();
            Stage primaryStage = (Stage) this.getScene().getWindow();
            primaryStage.setScene(menu);
            primaryStage.show();
        }
    }

}
