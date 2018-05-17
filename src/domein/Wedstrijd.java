package domein;

import java.util.*;

/**
 * Wedstrijd Klasse
 *
 * @author Baetens Jan
 */
public class Wedstrijd {

    private List<Speler> deSpelers;
    private List<List<WedstrijdKaart>> wedstrijdstapels, gespeeldeKaarten;
    private List<WedstrijdKaart> setKaarten;
    private List<Integer> scores;
    private Integer huidigeAanBeurt;
    private Speler winnaar;
    private List<Integer> setsGewonnen;
    private List<Boolean> isBevroren;
    private Speler begonnen;
    private String naam;
    private boolean isGedaan;

    /**
     * Constructor voor volledig nieuwe wedstrijd
     *
     */
    public Wedstrijd() {
        setsGewonnen = new ArrayList();
        deSpelers = new ArrayList();
        wedstrijdstapels = new ArrayList();
        wedstrijdstapels.add(new ArrayList());
        wedstrijdstapels.add(new ArrayList());
        this.isGedaan = false;
    }

    /**
     * Constructor voor het laden van een bestaande wedstrijd uit de databank
     *
     * @param naam
     * @param begonnen
     * @param deSpelers
     * @param setsGewonnen
     * @param wedstrijdstapels
     */
    public Wedstrijd(String naam, Speler begonnen, List<Speler> deSpelers, List<Integer> setsGewonnen, List<List<WedstrijdKaart>> wedstrijdstapels) {
        this.setsGewonnen = setsGewonnen;
        this.begonnen = begonnen;
        this.deSpelers = deSpelers;
        this.wedstrijdstapels = wedstrijdstapels;
        this.naam = naam;
        this.isGedaan = false;
    }

    /**
     *
     * @return naam van de wedstrijd
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Sets naam van de wedstrijd
     *
     * @param naam
     */
    public void setNaam(String naam) {
        this.naam = naam;
    }

    /**
     * Voegt speler toe bij de wedstrijd
     *
     * @param geselecteerdeSpeler
     */
    public void voegSpelerToe(Speler geselecteerdeSpeler) {
        deSpelers.add(geselecteerdeSpeler);
        setsGewonnen.add(0);
    }

    private void geefWinnaarKrediet() {
        winnaar.setKrediet(winnaar.getKrediet() + 5);
    }

    /**
     *
     * @return lijst van spelers zonder wedstrijdstapel
     */
    public List<Speler> toonSpelersZonderWedstrijdStapel() {

        List<Speler> lijstNamen = new ArrayList();
        for (Speler speler : deSpelers) {
            if (wedstrijdstapels.get(deSpelers.indexOf(speler)).isEmpty()) {
                lijstNamen.add(speler);
            }
        }
        return lijstNamen;
    }

    /**
     * Maakt een wedstrijdstapel aan voor de meegegeven speler met de meegegeven
     * kaarten, 4 van de 6 meegegeven kaarten worden geselecteerd
     *
     * @param speler
     * @param kaarten
     */
    public void maakWedstrijdstapel(Speler speler, List<Kaart> kaarten) {
        Collections.shuffle(kaarten);
        List<Kaart> randomKaarten = kaarten.subList(0, 4);
        List<WedstrijdKaart> wedstrijdKaarten = new ArrayList();
        for (Kaart kaart : randomKaarten) {
            wedstrijdKaarten.add(new WedstrijdKaart(kaart));
        }
        if (deSpelers.indexOf(speler) == 0) {
            wedstrijdstapels.set(0, wedstrijdKaarten);
        } else {
            wedstrijdstapels.set(1, wedstrijdKaarten);
        }

    }

    /**
     * Nieuwe set wordt gestart
     */
    public void speelNieuweSet() {
        /*Reset nog doen*/
        setKaarten = new ArrayList();
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 11; j++) {
                setKaarten.add(new WedstrijdKaart(new Kaart("", "" + j, String.format("%d", j))));
            }
        }
        Collections.shuffle(setKaarten);
        scores = new ArrayList();
        gespeeldeKaarten = new ArrayList();
        isBevroren = new ArrayList();
        for (Speler s : deSpelers) {
            gespeeldeKaarten.add(new ArrayList());
            isBevroren.add(false);
            scores.add(0);
        }
        if (huidigeAanBeurt == null) {
            if (deSpelers.get(0).getGeboortejaar() > deSpelers.get(1).getGeboortejaar()) {
                huidigeAanBeurt = 0;
            } else if (deSpelers.get(0).getGeboortejaar() < deSpelers.get(1).getGeboortejaar()) {
                huidigeAanBeurt = 0;
            } else {
                if (deSpelers.get(0).getNaam().compareTo(deSpelers.get(1).getNaam()) > 0) {
                    huidigeAanBeurt = 0;
                } else {
                    huidigeAanBeurt = 1;
                }
            }
        } else {
            if (deSpelers.indexOf(begonnen) == 0) {
                huidigeAanBeurt = 1;
            } else {
                huidigeAanBeurt = 0;
            }
        }
        begonnen = deSpelers.get(huidigeAanBeurt);
        WedstrijdKaart remove = setKaarten.get(0);
        setKaarten.remove(0);
        gespeeldeKaarten.get(huidigeAanBeurt).add(remove);
        int newScore = scores.get(huidigeAanBeurt) + Integer.parseInt(remove.getKaart().getWaarde());
        scores.set(huidigeAanBeurt, newScore);
    }

    /**
     * Speelt een kaart voor de huidige speler, met meegegeven keuze (vb: -, +,
     * +x, -y, ...)
     *
     * @param kaart
     * @param type
     */
    public void speelKaart(Kaart kaart, String type) {
        int newScore = scores.get(huidigeAanBeurt), i = 0;
        WedstrijdKaart wedstrijdkaart = new WedstrijdKaart(kaart);
        boolean isGevonden = false;
        List<WedstrijdKaart> lijst = wedstrijdstapels.get(huidigeAanBeurt);
        do {
            if (lijst.get(i).getKaart().equals(kaart)) {
                wedstrijdkaart = lijst.get(i);
                wedstrijdkaart.setTeken(type);
                isGevonden = true;
            } else {
                i++;
            }
        } while (!isGevonden);
        gespeeldeKaarten.get(huidigeAanBeurt).add(wedstrijdkaart);
        if (wedstrijdkaart.getTeken().equals("-")) {
            newScore -= Integer.parseInt(kaart.getWaarde());
        } else if (wedstrijdkaart.getTeken().equals("+")) {
            newScore += Integer.parseInt(kaart.getWaarde());
        } else if (wedstrijdkaart.getTeken().equals("xT")) {
            newScore += Integer.parseInt(wedstrijdkaart.getKaart().getWaarde());
            bevriesSpeler();
        } else if (wedstrijdkaart.getTeken().equals("D")) {
            newScore += Integer.parseInt(gespeeldeKaarten.get(huidigeAanBeurt).get(gespeeldeKaarten.get(huidigeAanBeurt).size() - 2).getKaart().getWaarde());
        } else if (wedstrijdkaart.getTeken().equals("x&y")) {
            String waarde1 = "" + wedstrijdkaart.getTeken().charAt(0);
            String waarde2 = "" + wedstrijdkaart.getTeken().charAt(2);
            for (WedstrijdKaart wk : gespeeldeKaarten.get(huidigeAanBeurt)) {
                if (wk.getKaart().getWaarde().equals(waarde1) || wk.getKaart().getWaarde().equals(waarde2)) {
                    if (wk.getTeken().equals("+")) {
                        wk.setTeken("-");
                        newScore -= Integer.parseInt(wk.getKaart().getWaarde()) - Integer.parseInt(wk.getKaart().getWaarde());
                    } else {
                        wk.setTeken("+");
                        newScore += Integer.parseInt(wk.getKaart().getWaarde()) + Integer.parseInt(wk.getKaart().getWaarde());
                    }
                }
            }
        } else if (wedstrijdkaart.getKaart().getType().equals("x+/-y")) {
            if (wedstrijdkaart.getTeken().equals("+x")) {
                newScore += Integer.parseInt("" + wedstrijdkaart.getKaart().getWaarde().charAt(0));
            } else if (wedstrijdkaart.getTeken().equals("-x")) {
                newScore -= Integer.parseInt("" + wedstrijdkaart.getKaart().getWaarde().charAt(0));
            } else if (wedstrijdkaart.getTeken().equals("+y")) {
                newScore += Integer.parseInt("" + wedstrijdkaart.getKaart().getWaarde().charAt(4));
            } else {
                newScore -= Integer.parseInt("" + wedstrijdkaart.getKaart().getWaarde().charAt(4));
            }
        }
        scores.set(huidigeAanBeurt, newScore);
        wedstrijdstapels.get(huidigeAanBeurt).remove(wedstrijdkaart);

    }

    /**
     * Bevriest de huidige speler
     *
     */
    public void bevriesSpeler() {
        isBevroren.set(huidigeAanBeurt, true);
        beeindigSpeler();
    }

    /**
     * Beeindigt de huidige speler zijn beurt
     */
    public void beeindigSpeler() {
        if (huidigeAanBeurt == 0) {
            if (!isBevroren.get(1)) {
                huidigeAanBeurt = 1;
            }
        } else {
            if (!isBevroren.get(0)) {
                huidigeAanBeurt = 0;
            }
        }
        if (!isBevroren.get(0) || !isBevroren.get(1)) {
            WedstrijdKaart remove = setKaarten.get(0);
            setKaarten.remove(0);
            gespeeldeKaarten.get(huidigeAanBeurt).add(remove);
            int newScore = scores.get(huidigeAanBeurt) + Integer.parseInt(remove.getKaart().getWaarde());
            scores.set(huidigeAanBeurt, newScore);
        }
    }

    /**
     *
     * @return aantal gewonnen sets voor beide spelers
     */
    public List<Integer> geefAantalGewonnenSets() {
        return setsGewonnen;
    }

    /**
     *
     * @return winnaar van de wedstrijd indien er één is, ander null
     */
    public Speler getWinnaar() {
        return this.winnaar;
    }

    /**
     *
     * @return spelbord van huidge speler
     */
    public List<WedstrijdKaart> geefGespeeldeKaarten() {
        return gespeeldeKaarten.get(huidigeAanBeurt);
    }

    /**
     *
     * @return wedstrijdstapel van huidige speler
     */
    public List<WedstrijdKaart> geefWedstrijdKaarten() {
        return wedstrijdstapels.get(huidigeAanBeurt);
    }

    /**
     *
     * @return de speler aan beurt
     */
    public Speler getSpelerAanBeurt() {
        return deSpelers.get(huidigeAanBeurt);
    }

    /**
     *
     * @return het aantal kaarten van beide spelers
     */
    public List<Integer> geefAantalKaarten() {
        List<Integer> aantal = new ArrayList();
        aantal.add(gespeeldeKaarten.get(0).size());
        aantal.add(gespeeldeKaarten.get(1).size());
        return aantal;
    }

    /**
     *
     * @return de scores van de huidige set van beide spelers
     */
    public List<Integer> geefScores() {
        return scores;
    }

    /**
     *
     * @return de score van de huidige speler van de huidige set
     */
    public int geefScore() {
        return scores.get(huidigeAanBeurt);
    }

    /**
     *
     * @return zijn de spelers bevroren
     */
    public List<Boolean> geefIsBevroren() {
        return isBevroren;
    }

    /**
     *
     * @return de spelers van de wedstrijd
     */
    public List<Speler> geefSpelers() {
        List<Speler> nieuw = deSpelers;
        return nieuw;
    }

    /**
     *
     * @return beide spelbordens
     */
    public List<List<WedstrijdKaart>> geefBeideWedstrijdkaarten() {
        return gespeeldeKaarten;
    }

    /**
     *
     * @return de naam van de speler die de wedstrijd gewonnen heeft of null als
     * de wedstrijd nog niet gedaan is
     */
    public Speler controlleerWedstrijdGewonnen() {
        if (setsGewonnen.get(0) >= 3) {
            winnaar = deSpelers.get(0);
            if (!isGedaan) {
                winnaar.setKrediet(winnaar.getKrediet() + 5);
                isGedaan = true;
            }
        } else if (setsGewonnen.get(1) >= 3) {
            winnaar = deSpelers.get(1);
            if (!isGedaan) {
                winnaar.setKrediet(winnaar.getKrediet() + 5);
                isGedaan = true;
            }
        }
        return winnaar;
    }

    /**
     *
     * @return de naam van de speler die de set gewonnen heeft of null als het
     * gelijkspel is
     */
    public Speler controlleerSetGewonnen() {

        Speler gewonnen = null;

        if (scores.get(0) <= 20 && scores.get(1) <= 20) {
            if (scores.get(0) == 20 && scores.get(1) == 20) {
                if (gespeeldeKaarten.get(0).get(gespeeldeKaarten.get(0).size() - 1).getKaart().getType().equals("xT")) {
                    gewonnen = deSpelers.get(0);
                } else if (gespeeldeKaarten.get(1).get(gespeeldeKaarten.get(1).size() - 1).getKaart().getType().equals("xT")) {
                    gewonnen = deSpelers.get(1);
                }
            } else if (gespeeldeKaarten.get(0).size() == 9 && scores.get(0) > scores.get(1)) {
                gewonnen = deSpelers.get(0);
            } else if (gespeeldeKaarten.get(1).size() == 9 && scores.get(0) < scores.get(1)) {
                gewonnen = deSpelers.get(1);
            } else if (scores.get(0) > scores.get(1)) {
                gewonnen = deSpelers.get(0);
            } else if (scores.get(0) < scores.get(1)) {
                gewonnen = deSpelers.get(1);
            }
        } else {
            if (scores.get(0) > 20) {
                gewonnen = deSpelers.get(1);
            } else if (scores.get(1) > 20) {
                gewonnen = deSpelers.get(0);
            }
        }

        int aantalGewonnen = setsGewonnen.get(deSpelers.indexOf(gewonnen));
        aantalGewonnen++;
        setsGewonnen.set(deSpelers.indexOf(gewonnen), aantalGewonnen);

        return gewonnen;
    }

    /**
     *
     * @return de speler die begonnen is bij de vorige set
     */
    public Speler getBegonnen() {
        return begonnen;
    }

    /**
     *
     * @return beide wedstrijdstapels
     */
    public List<List<WedstrijdKaart>> geefBeideStapels() {
        return wedstrijdstapels;
    }
}
