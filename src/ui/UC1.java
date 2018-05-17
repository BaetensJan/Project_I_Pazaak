package ui;

import domein.DomeinController;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UC1 {

    private static DomeinController domeinController = new DomeinController();
    private static ResourceBundle gekozentaal = ResourceBundle.getBundle("talen.taal", new Locale("nl", "BE"));

    public static void main(String[] args) throws SQLException {
        boolean isNietCorrect = true;
        Scanner s = new Scanner(System.in);
        do {
            try {
                System.out.printf(gekozentaal.getString("naam"));
                String naam = s.next();
                System.out.printf("%n%s", gekozentaal.getString("geboortejaar"));
                int geboortejaar = s.nextInt();
                domeinController.maakNieuweSpeler(naam, geboortejaar);
                isNietCorrect = false;
            } catch (IllegalArgumentException iae) {
                System.err.println(iae.getMessage());
            } catch (InputMismatchException e) {
                System.err.println(gekozentaal.getString("inputMismatchErrorGeboortejaar"));
                s.nextLine();
            }
        } while (isNietCorrect);
    }
}
