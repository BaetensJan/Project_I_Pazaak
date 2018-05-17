package gui;

import domein.DomeinController;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StartSchermController extends BorderPane {

    private final DomeinController domeinController;
    private ResourceBundle gekozentaal;

    @FXML
    private BorderPane pane;
    @FXML
    private ImageView imgNLFlag;
    @FXML
    private ImageView imgFRFlag;
    @FXML
    private ImageView imgENFlag;

    public StartSchermController(DomeinController dc) {
        this.domeinController = dc;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("startScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    private void imgNLFlagOnClick(MouseEvent event) {
        gekozentaal = ResourceBundle.getBundle("talen.taal", new Locale("nl", "BE"));
        startMenuScherm();
    }

    @FXML
    private void imgFRFlagOnClick(MouseEvent event) {
        gekozentaal = ResourceBundle.getBundle("talen.taal", new Locale("fr", "FR"));
        startMenuScherm();
    }

    @FXML
    private void imgENFlagOnClick(MouseEvent event) {
        gekozentaal = ResourceBundle.getBundle("talen.taal", new Locale("en", "GB"));
        startMenuScherm();
    }

    private void startMenuScherm() {
        Scene menu = new Scene(new MenuSchermController(domeinController, gekozentaal));
        Stage primaryStage = (Stage) this.getScene().getWindow();
        primaryStage.setScene(menu);
        primaryStage.show();
    }

}
