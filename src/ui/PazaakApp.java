package ui;

import domein.DomeinController;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PazaakApp {

    private DomeinController domeinController;
    private ResourceBundle gekozentaal;

    public PazaakApp(DomeinController domeinController) {
        this.domeinController = domeinController;

    }

    public void start() throws SQLException {
        boolean isNietCorrect = true;
        int menukeuze = 1;
        Scanner s = new Scanner(System.in);
        selecteerTaal();
        do {
            do {
                try {
                    System.out.println("Menu");
                    System.out.println("1) " + gekozentaal.getString("menuOptie1"));
                    System.out.println("2) " + gekozentaal.getString("menuOptie2"));
                    System.out.println("3) " + gekozentaal.getString("menuOptie3"));
                    System.out.println("4) " + gekozentaal.getString("menuOptie4"));
                    System.out.print(gekozentaal.getString("kiesKeuze"));
                    menukeuze = s.nextInt();
                    if (menukeuze < 1 || menukeuze > 4) {
                        throw new IllegalArgumentException(gekozentaal.getString("menukeuzeIllegalArgumentException"));
                    }
                    isNietCorrect = false;
                } catch (IllegalArgumentException iae) {
                    System.err.println(iae.getMessage());
                } catch (InputMismatchException ime) {
                    System.err.println(gekozentaal.getString("inputMismatchError"));
                    s.nextLine();
                }
            } while (isNietCorrect);
            switch (menukeuze) {
                case 1:
                    maakSpeler();
                    break;
                case 2:
                    maakWedstrijd();
                    break;
                case 3:
                    laadWedstrijd();
                    break;
            }
        } while (menukeuze != 4);
    }

    private void selecteerTaal() {
        boolean isNietCorrect = true;
        int taal = 1;
        Scanner s = new Scanner(System.in);
        do {
            try {
                System.out.printf("Kies uw taal%nChoisissez votre langue%nChoose your language%n");
                System.out.println("1) Nederlands");
                System.out.println("2) Français");
                System.out.println("3) English");
                System.out.print("Keuze / Choix / Choice: ");
                taal = s.nextInt();
                if (taal < 1 || taal > 3) {
                    throw new IllegalArgumentException("Gelieve een nummer tussen 1 en 3 te kiezen / Choisissez une numéro entre 1 et 3 s'il vous plaît / Please choose a number between 1 and 3");
                }
                isNietCorrect = false;
            } catch (InputMismatchException ime) {
                System.err.println("Gelieve een nummer in te geven! / Entrer un numéro s'il vous plaît! / Please enter a number!");
                s.nextLine();
            } catch (IllegalArgumentException iae) {
                System.err.println(iae.getMessage());
            }
        } while (isNietCorrect);
        if (taal == 1) {
            gekozentaal = ResourceBundle.getBundle("talen.taal", new Locale("nl", "BE"));

        }

        if (taal == 2) {
            gekozentaal = ResourceBundle.getBundle("talen.taal", new Locale("fr", "FR"));

        }

        if (taal == 3) {
            gekozentaal = ResourceBundle.getBundle("talen.taal", new Locale("en", "GB"));

        }
        System.out.println(gekozentaal.getString("gekozentaal"));
    }

    private void maakSpeler() throws SQLException {
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

    private void maakWedstrijd() {
        Scanner s = new Scanner(System.in);
        boolean spelersNodig = true, genoegSpelers = true;
        List<String> geselecteerdeSpelers = new ArrayList();
        List<String> deSpelers = new ArrayList();
        int keuze = 0, naam = 0;

        do {
            try {
                if (domeinController.geefAantalSpelers() < 2) {
                    genoegSpelers = false;
                    spelersNodig = false;
                    throw new IllegalArgumentException(gekozentaal.getString("nietGenoegSpelers"));
                }
                System.out.println(gekozentaal.getString("lijstSpelers"));
                List<String> lijstNamen = domeinController.toonSpelers();
                for (int i = 0; i < lijstNamen.size(); i++) {
                    System.out.printf("%d%s%n", i + 1, ") " + lijstNamen.get(i));
                }
                System.out.println(gekozentaal.getString("selecteerSpeler"));
                naam = (s.nextInt() - 1);

                if (geselecteerdeSpelers.contains(lijstNamen.get(naam))) {
                    throw new IllegalArgumentException(gekozentaal.getString("naamAlInGeselecteerdeLijst"));
                }

                if (naam < 0 || naam > domeinController.geefAantalSpelers() + 1) {
                    throw new IndexOutOfBoundsException();
                }
                geselecteerdeSpelers.add(lijstNamen.get(naam)); // Systeem registreert de speler

                if (geselecteerdeSpelers.size() == 2) {
                    spelersNodig = false;
                }

            } catch (InputMismatchException ime) {
                System.err.println(gekozentaal.getString("inputMismatchError"));
                s.nextLine();
            } catch (IllegalArgumentException iae) {
                System.err.println(iae.getMessage());
            } catch (IndexOutOfBoundsException ioobe) {
                System.err.println(gekozentaal.getString("indexOutOfBoundsException"));
            }

        } while (spelersNodig);
        boolean confirmed = false;
        if (genoegSpelers) {
            System.out.println(gekozentaal.getString("gekozenSpelers"));
            for (String spelerNaam : geselecteerdeSpelers) {
                deSpelers.add(spelerNaam);
                System.out.println(spelerNaam);
            }
            do {
                try {
                    System.out.println(gekozentaal.getString("bevestigMakenWedstrijd"));
                    System.out.println("1) " + gekozentaal.getString("ja"));
                    System.out.println("2) " + gekozentaal.getString("neen"));
                    keuze = s.nextInt();
                    if (keuze > 2 || keuze < 1) {
                        throw new IllegalArgumentException(gekozentaal.getString("booleanKeuzeIllegalArgumentException"));
                    }
                    switch (keuze) {
                        case 1:
                            System.out.println(gekozentaal.getString("wedstrijdBevestigd"));
                            confirmed = true;
                            domeinController.maakWedstrijd(deSpelers);
                            break;
                        case 2:
                            System.out.println(gekozentaal.getString("wedstrijdNietBevestigd"));
                            confirmed = false;
                            break;
                    }
                } catch (InputMismatchException ime) {
                    System.err.println(gekozentaal.getString("inputMismatchError"));
                    s.nextLine();
                } catch (IllegalArgumentException iae) {
                    System.err.println(iae.getMessage());
                }
            } while (!confirmed);
            boolean nogKaarten = true, correct = false;
            naam = 0;
            int aantalKaarten = 0, j = 0, aantalSpelers = 0;
            List<String> geselecteerdeKaarten = new ArrayList();
            List<String> spelerKaarten = new ArrayList();
            List<String> spelers = new ArrayList();
            for (String speler : domeinController.geefSpelers()) {
                spelers.add(speler);
            }
            String speler = spelers.get(0);
            do {
                correct = false;
                do {
                    try {
                        System.out.println(gekozentaal.getString("spelersZonderStapel"));
                        List<String> spelersZonderStapel = domeinController.toonSpelersZonderWedstrijdstapel();
                        for (int i = 1; i <= spelersZonderStapel.size(); i++) {
                            System.out.println(i + ") " + spelersZonderStapel.get(i - 1));
                        }
                        System.out.println(gekozentaal.getString("kiesWedstrijdstapel"));
                        naam = s.nextInt() - 1;
                        if (naam < 0 || naam > spelers.size() - 1) {
                            throw new IllegalArgumentException(gekozentaal.getString("indexOutOfBoundsException"));
                        }
                        speler = spelers.get(naam);
                        spelers.remove(speler);
                        spelerKaarten = domeinController.getKaartenSpeler(speler);
                        geselecteerdeKaarten = new ArrayList();
                        nogKaarten = true;
                        aantalKaarten = 0;
                        correct = true;
                    } catch (InputMismatchException ime) {
                        System.err.println(gekozentaal.getString("inputMismatchError"));
                        s.nextLine();
                    } catch (IllegalArgumentException iae) {
                        System.err.println(iae.getMessage());
                    } catch (IndexOutOfBoundsException aioobe) {
                        System.err.println(gekozentaal.getString("indexOutOfBoundsException"));
                    }
                } while (!correct);
                int menukeuze = 0;
                do {
                    try {
                        System.out.printf("%s%n1) %s%n2) %s%n",
                                gekozentaal.getString("koopKaartMenu"),
                                gekozentaal.getString("koopKaartMenuSelecteerKaart"),
                                gekozentaal.getString("koopKaartMenuKoopKaart"));
                        menukeuze = s.nextInt();
                        if (menukeuze < 1 || menukeuze > 2) {
                            throw new IllegalArgumentException("indexOutOfBoundsException");
                        }
                    } catch (InputMismatchException ime) {
                        System.err.println(gekozentaal.getString("inputMismatchError"));
                        s.nextLine();
                    } catch (IllegalArgumentException iae) {
                        System.err.println(gekozentaal.getString(iae.getMessage()));
                    } catch (IndexOutOfBoundsException aioobe) {
                        System.err.println(gekozentaal.getString("indexOutOfBoundsException"));
                    }
                    if (menukeuze == 2) {
                        do {
                            try {
                                System.out.printf("%s%.0f%n", gekozentaal.getString("uwKrediet"), domeinController.geefSpelerKrediet(speler));
                                List<String> teKopenkaarten = domeinController.getKaartenTeKopen(speler);
                                for (String teKopenKaart : teKopenkaarten) {
                                    System.out.printf("%d) %s, %s%.0f%n", teKopenkaarten.indexOf(teKopenKaart) + 1, teKopenKaart, gekozentaal.getString("kostprijs"), domeinController.geefKaartPrijs(teKopenKaart));
                                }
                                System.out.printf("%d) %s%n", teKopenkaarten.size() + 1, gekozentaal.getString("keerTerug"));
                                keuze = (s.nextInt() - 1);
                                if (keuze < 0 || keuze > teKopenkaarten.size()) {
                                    throw new IllegalArgumentException("indexOutOfBoundsException");
                                }
                                if (keuze != teKopenkaarten.size()) {
                                    domeinController.koopKaart(teKopenkaarten.get(keuze), speler);
                                    spelerKaarten.add(teKopenkaarten.get(keuze));
                                    System.out.printf("%s%s%n", gekozentaal.getString("bevestigingKoop"), teKopenkaarten.get(keuze));
                                }
                            } catch (InputMismatchException ime) {
                                System.err.println(gekozentaal.getString("inputMismatchError"));
                                s.nextLine();
                            } catch (IllegalArgumentException iae) {
                                System.err.println(gekozentaal.getString(iae.getMessage()));
                            } catch (IndexOutOfBoundsException aioobe) {
                                System.err.println(gekozentaal.getString("indexOutOfBoundsException"));
                            }
                        } while (keuze != domeinController.getKaartenTeKopen(speler).size());
                    } else if (menukeuze == 1) {
                        do {
                            try {
                                System.out.println(gekozentaal.getString("selecteerKaart"));
                                for (String kaart : spelerKaarten) {
                                    System.out.printf("%d) %s%n", spelerKaarten.indexOf(kaart) + 1, kaart);
                                }
                                System.out.print(gekozentaal.getString("selecteerNummerKaart"));
                                keuze = (s.nextInt() - 1);
                                if (keuze < 0 || keuze > spelerKaarten.size() - 1) {
                                    throw new IllegalArgumentException("indexOutOfBoundsException");
                                } else {
                                    geselecteerdeKaarten.add(spelerKaarten.get(keuze));
                                    spelerKaarten.remove(keuze);
                                    aantalKaarten++;
                                }
                                if (aantalKaarten < 6) {
                                    System.out.printf("%s%d%n", gekozentaal.getString("nogTeKiezenKaarten"), (6 - aantalKaarten));
                                }
                                if (aantalKaarten >= 6) {
                                    nogKaarten = false;
                                }
                            } catch (InputMismatchException ime) {
                                System.err.println(gekozentaal.getString("inputMismatchError"));
                                s.nextLine();
                            } catch (IllegalArgumentException iae) {
                                System.err.println(gekozentaal.getString(iae.getMessage()));
                            } catch (IndexOutOfBoundsException aioobe) {
                                System.err.println(gekozentaal.getString("indexOutOfBoundsException"));
                            }
                        } while (nogKaarten);
                    }
                } while (nogKaarten);
                domeinController.maakWedstrijdstapel(speler, geselecteerdeKaarten);
                aantalSpelers++;
            } while (aantalSpelers < 2);
        }
        speelWedstrijd();
    }

    private void speelWedstrijd() {
        boolean verderSpelen = true;
        do {
            speelSet();
            if (domeinController.controlleerWedstrijdGewonnen() == null) {
                verderSpelen = slaOp();
            }
        } while (domeinController.controlleerWedstrijdGewonnen() == null && verderSpelen);
        if (verderSpelen) {
            System.out.printf("%s %s%n", gekozentaal.getString("wedstrijdGewonnen"), domeinController.controlleerWedstrijdGewonnen());
        }
    }

    private boolean slaOp() {
        boolean isJuist, isOpgeslagen;
        Scanner s = new Scanner(System.in);
        isJuist = false;
        isOpgeslagen = false;
        do {
            try {
                System.out.printf("%s%n%s%n%s%n", gekozentaal.getString("opslaan?"), "1)" + gekozentaal.getString("ja"), "2)" + gekozentaal.getString("neen"));
                int input = s.nextInt();
                if (input < 1 || input > 2) {
                    throw new IllegalArgumentException("indexOutOfBoundsException");
                }
                if (input == 1) {
                    do {
                        try {
                            System.out.printf("%s%n", gekozentaal.getString("wedstrijdNaam"));
                            String naamWedstrijd = s.next();
                            if (domeinController.isWedstrijdUniek(naamWedstrijd)) {
                                throw new IllegalArgumentException("nietUniek");
                            }
                            domeinController.slaWedstrijdOp(naamWedstrijd);
                            System.out.printf("%s%n%s%n%s%n", gekozentaal.getString("verderSpelen?"), "1) " + gekozentaal.getString("ja"), "2) " + gekozentaal.getString("neen"));
                            input = s.nextInt();
                            if (input < 1 || input > 2) {
                                throw new IllegalArgumentException("indexOutOfBoundsException");
                            }
                            if (input == 2) {
                                return false;
                            }
                            isOpgeslagen = true;
                        } catch (InputMismatchException ime) {
                            System.err.println(gekozentaal.getString("inputMismatchError"));
                            s.nextLine();
                        } catch (IllegalArgumentException iae) {
                            System.err.println(gekozentaal.getString(iae.getMessage()));
                        }
                    } while (!isOpgeslagen);
                }
                isJuist = true;
            } catch (InputMismatchException ime) {
                System.err.println(gekozentaal.getString("inputMismatchError"));
                s.nextLine();
            } catch (IllegalArgumentException iae) {
                System.err.println(gekozentaal.getString(iae.getMessage()));
            }
        } while (!isJuist);
        return true;
    }

    private void speelSet() {
        boolean isGedaan = false;
        Scanner s = new Scanner(System.in);

        domeinController.speelNieuweSet();
        do {
            try {
                toonSituatie();
                System.out.printf("%s%n1) %s%n2) %s%n3) %s%n",
                        gekozentaal.getString("kiesEenKeuze"),
                        gekozentaal.getString("beurtBeëindigen"),
                        gekozentaal.getString("wedstrijdkaartGebruiken"),
                        gekozentaal.getString("spelbordBevriezen"));
                int input = s.nextInt();
                if (input < 1 || input > 3) {
                    throw new IllegalArgumentException("menukeuzeIllegalArgumentException");
                }
                switch (input) {
                    case 1:
                        domeinController.beeindigSpeler();
                        break;
                    case 2:
                        gebruikKaart();
                        break;
                    case 3:
                        domeinController.bevriesSpeler();
                        break;
                }
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
                }
            } catch (IllegalArgumentException iae) {
                System.out.println(gekozentaal.getString(iae.getMessage()));
            } catch (InputMismatchException ime) {
                System.out.println(gekozentaal.getString("inputMismatchError"));
                s.nextLine();
            }
        } while (!isGedaan);
        String gewonnen = domeinController.controlleerSetGewonnen();
        if (gewonnen != null) {
            System.out.printf("%s %s%n", gekozentaal.getString("deWinnaarIs"), gewonnen);
        } else {
            System.out.printf("%s%n", gekozentaal.getString("gelijkSpel"));
        }
    }

    private void toonSituatie() {

        System.out.printf("%s%n%s%n", gekozentaal.getString("wedstrijdsituatie"), gekozentaal.getString("spelbord"));
        System.out.printf("%s %s%n", gekozentaal.getString("spelerAanZet"), domeinController.getSpelerAanBeurt());
        System.out.printf("%s%n%s : %d%n%s : %d%n", gekozentaal.getString("AantalGewonnenSets"), domeinController.geefSpelers().get(0), domeinController.geefAantalGewonnenSets().get(0), domeinController.geefSpelers().get(1), domeinController.geefAantalGewonnenSets().get(1));
        for (String kaart : domeinController.geefGespeeldeKaarten()) {
            System.out.printf("%d) %s%n", domeinController.geefGespeeldeKaarten().indexOf(kaart) + 1, kaart);
        }
        if (domeinController.getSpelerAanBeurt().equals(domeinController.geefSpelers().get(0))) {
            System.out.printf("%s %d%n", gekozentaal.getString("score"), domeinController.geefScore());
            System.out.printf("%s %d%n", gekozentaal.getString("tegenstandersScore"), domeinController.geefScores().get(1));
        } else {
            System.out.printf("%s %d%n", gekozentaal.getString("score"), domeinController.geefScore());
            System.out.printf("%s %d%n", gekozentaal.getString("tegenstandersScore"), domeinController.geefScores().get(0));
        }
        System.out.printf("%s %n", gekozentaal.getString("uwKaarten"));
        for (String kaart : domeinController.geefWedstrijdKaarten()) {
            System.out.printf("%d) %s%n", domeinController.geefWedstrijdKaarten().indexOf(kaart) + 1, kaart);
        }
    }

    private void gebruikKaart() {
        Scanner s = new Scanner(System.in);
        boolean isJuist = false;

        do {
            System.out.printf("%s %n", gekozentaal.getString("uwKaarten"));
            for (String kaart : domeinController.geefWedstrijdKaarten()) {
                System.out.printf("%d) %s%n", domeinController.geefWedstrijdKaarten().indexOf(kaart) + 1, kaart);
            }
            try {
                System.out.printf("%s ", gekozentaal.getString("kiesEenKaart"));
                int input = s.nextInt();
                String kaartOmschrijving = domeinController.geefWedstrijdKaarten().get(input - 1);
                String kaartType = domeinController.geefKaartType(kaartOmschrijving);
                if (kaartType.equals("+/-")) {
                    System.out.printf("%s%n%s%n%s%n", gekozentaal.getString("kiesType"), "1) -", "2) +");
                    input = s.nextInt();
                    if (input < 1 || input > 2) {
                        throw new IllegalArgumentException("indexOutOfBoundsException");
                    }
                    if (input == 1) {
                        domeinController.speelKaart(kaartOmschrijving, "-");
                    } else if (input == 2) {
                        domeinController.speelKaart(kaartOmschrijving, "+");
                    }
                } else if (kaartType.equals("x+/-y")) {
                    String kaartWaarde = domeinController.geefKaartWaarde(kaartOmschrijving);
                    System.out.printf("%s%n%s%n%s%n%s%n%s%n", gekozentaal.getString("kiesType"), "1) +" + kaartWaarde.charAt(0), "2) -" + kaartWaarde.charAt(0), "3) +" + kaartWaarde, "4) -" + kaartWaarde.charAt(4));
                    input = s.nextInt();
                    switch (input) {
                        case 1:
                            domeinController.speelKaart(kaartOmschrijving, "+x");
                            break;
                        case 2:
                            domeinController.speelKaart(kaartOmschrijving, "-x");
                            break;
                        case 3:
                            domeinController.speelKaart(kaartOmschrijving, "+y");
                            break;
                        case 4:
                            domeinController.speelKaart(kaartOmschrijving, "-y");
                            break;
                    }
                } else {
                    domeinController.speelKaart(kaartOmschrijving);
                }
                isJuist = true;
            } catch (IllegalArgumentException iae) {
                System.out.println(gekozentaal.getString(iae.getMessage()));
            } catch (InputMismatchException ime) {
                System.out.println(gekozentaal.getString("inputMismatchError"));
                s.nextLine();
            }
        } while (!isJuist);

    }

    private void laadWedstrijd() {
        Scanner s = new Scanner(System.in);
        boolean isJuist = false;
        List<String> wedstrijdLijst = domeinController.bewaardeWedstrijden();
        do {
            try {
                if (!wedstrijdLijst.isEmpty()) {
                    System.out.println(gekozentaal.getString("kiesWedstrijd"));
                    for (int i = 1; i <= wedstrijdLijst.size(); i++) {
                        System.out.println(i + ") " + wedstrijdLijst.get(i - 1));
                    }
                    int input = s.nextInt();
                    if (input < 1 || input > wedstrijdLijst.size()) {
                        throw new IllegalArgumentException("indexOutOfBoundsException");
                    }
                    domeinController.laadWedstrijd(wedstrijdLijst.get(input - 1));
                    speelWedstrijd();
                } else {
                    System.out.println(gekozentaal.getString("geenWedstrijdenBeschikbaar"));
                }
                isJuist = true;
            } catch (IllegalArgumentException iae) {
                System.err.println(iae.getMessage());
            } catch (InputMismatchException ime) {
                System.err.println(gekozentaal.getString("inputMismatchError"));
                s.nextLine();
            }
        } while (!isJuist);

    }
}
