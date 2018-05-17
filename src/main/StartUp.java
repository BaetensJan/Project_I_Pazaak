package main;

import domein.DomeinController;
import java.sql.SQLException;
import ui.PazaakApp;

public class StartUp {

    public static void main(String[] args) throws SQLException {
        DomeinController dc = new DomeinController();
        PazaakApp pazaak = new PazaakApp(dc);
        pazaak.start();
    }

}
