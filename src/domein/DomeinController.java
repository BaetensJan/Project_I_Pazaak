package domein;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import persistentie.WedstrijdMapper;

/**
 * DomeinController classe
 *
 * @author Baetens Jan
 */
public class DomeinController {

    private final SpelerRepository spelerRepository;
    private Wedstrijd wedstrijd;
    private WedstrijdMapper wedstrijdMapper;

    /**
     *
     * Constructor
     */
    public DomeinController() {
        spelerRepository = new SpelerRepository();
        wedstrijdMapper = new WedstrijdMapper(spelerRepository);

    }

    /**
     * Maakt nieuwe speler aan in repo + mapper
     *
     * @param naam
     * @param geboortejaar
     * @throws SQLException
     */
    public void maakNieuweSpeler(String naam, int geboortejaar) throws SQLException {
        spelerRepository.maakNieuweSpeler(naam, geboortejaar);
    }

    /**
     *
     * @return spelerLijst
     */
    public List<String> toonSpelers() {
        return spelerRepository.toonSpelers();
    }

    /**
     *
     * @return aantal spelers
     */
    public int geefAantalSpelers() {
        return spelerRepository.geefAantalSpelers();
    }

    /**
     *
     * @return spelers zonder een wedstrijdstapel in de huidige wedstrijd
     */
    public List<String> toonSpelersZonderWedstrijdstapel() {
        List<String> lijstNamen = new ArrayList();
        for (Speler speler : wedstrijd.toonSpelersZonderWedstrijdStapel()) {
            lijstNamen.add(speler.getNaam());
        }
        return lijstNamen;
    }

    /**
     * Maakt een wedstrijd aan met de meegegeven spelers
     *
     * @param geselecteerdeSpelers
     */
    public void maakWedstrijd(List<String> geselecteerdeSpelers) {
        wedstrijd = new Wedstrijd();
        for (String speler : geselecteerdeSpelers) {
            wedstrijd.voegSpelerToe(spelerRepository.geefSpeler(speler));
        }
    }

    /**
     *
     * @param spelerNaam
     * @return lijst van de kaarten van de meegegeven speler
     */
    public List<String> getKaartenSpeler(String spelerNaam) {
        List<String> kaartOmschrijvingen = new ArrayList();
        Speler speler = spelerRepository.geefSpeler(spelerNaam);
        for (Kaart kaart : spelerRepository.getKaartenSpeler(speler)) {
            kaartOmschrijvingen.add(kaart.getOmschrijving());
        }
        return kaartOmschrijvingen;
    }

    /**
     * Maakt wedstrijdstapel aan voor de meegegeven speler met de meegegeven
     * kaarten
     *
     * @param spelerNaam
     * @param kaartOmschrijvingen
     */
    public void maakWedstrijdstapel(String spelerNaam, List<String> kaartOmschrijvingen) {
        Speler speler = spelerRepository.geefSpeler(spelerNaam);
        List<Kaart> selectie = new ArrayList();
        for (String kaartOmschrijving : kaartOmschrijvingen) {
            selectie.add(spelerRepository.geefKaart(kaartOmschrijving));
        }
        wedstrijd.maakWedstrijdstapel(speler, selectie);
    }

    /**
     * Geeft speelNieuweSet() door naar de wedstrijd
     *
     */
    public void speelNieuweSet() {
        wedstrijd.speelNieuweSet();
    }

    /**
     * Geeft speelKaart() door naar de wedstrijd bij kaarten zonder een wissel
     *
     * @param kaartOmschrijving
     */
    public void speelKaart(String kaartOmschrijving) {
        String kaartType = this.geefKaartType(kaartOmschrijving);
        speelKaart(kaartOmschrijving, kaartType);
    }

    /**
     * Geeft speelKaart() door naar de wedstrijd bij kaarten met een keuze van
     * teken
     *
     * @param kaartOmschrijving
     * @param type
     */
    public void speelKaart(String kaartOmschrijving, String type) {
        Kaart kaart = spelerRepository.geefKaart(kaartOmschrijving);
        wedstrijd.speelKaart(kaart, type);
    }

    /**
     * Geeft bevriesSpeler() door aan de wedstrijd
     *
     */
    public void bevriesSpeler() {
        wedstrijd.bevriesSpeler();
    }

    /**
     * Geeft beeindigSpeler() door aan de wedstrijd
     *
     */
    public void beeindigSpeler() {
        wedstrijd.beeindigSpeler();
    }

    /**
     *
     * @return set Score van de speler die aan zet is
     */
    public int geefScore() {
        return wedstrijd.geefScore();
    }

    /**
     *
     * @return spelbord kaarten van de speler die aan zet is
     */
    public List<String> geefGespeeldeKaarten() {
        List<String> kaarten = new ArrayList();
        for (WedstrijdKaart kaart : wedstrijd.geefGespeeldeKaarten()) {
            kaarten.add(kaart.getKaart().getOmschrijving());
        }
        return kaarten;
    }

    /**
     *
     * @return wedstrijdstapel van de speler die aan zet is
     */
    public List<String> geefWedstrijdKaarten() {
        List<String> kaarten = new ArrayList();
        for (WedstrijdKaart kaart : wedstrijd.geefWedstrijdKaarten()) {
            kaarten.add(kaart.getKaart().getOmschrijving());
        }
        return kaarten;
    }

    /**
     *
     * @return beide spelborden
     */
    public List<List<String>> geefBeideWedstrijdkaarten() {
        List<List<String>> wedstrijdkaarten = new ArrayList();
        wedstrijdkaarten.add(new ArrayList());
        wedstrijdkaarten.add(new ArrayList());
        int getal = 0;
        for (List<WedstrijdKaart> lwk : wedstrijd.geefBeideWedstrijdkaarten()) {
            for (WedstrijdKaart wk : lwk) {
                wedstrijdkaarten.get(getal).add(wk.getKaart().getOmschrijving());
            }
            getal++;
        }
        return wedstrijdkaarten;
    }

    /**
     *
     * @return speler die aan zet is
     */
    public String getSpelerAanBeurt() {
        Speler speler = wedstrijd.getSpelerAanBeurt();
        return speler.getNaam();
    }

    /**
     *
     * @return size() van beide spelborden
     */
    public List<Integer> geefAantalKaarten() {
        return wedstrijd.geefAantalKaarten();
    }

    /**
     *
     * @return scores van beide spelers
     */
    public List<Integer> geefScores() {
        return wedstrijd.geefScores();
    }

    /**
     *
     * @return zijn de spelers bevroren
     */
    public List<Boolean> geefIsBevroren() {
        return wedstrijd.geefIsBevroren();
    }

    /**
     *
     * @return beide spelers van huidige wedstrijd
     */
    public List<String> geefSpelers() {
        List<String> spelers = new ArrayList();
        for (Speler speler : wedstrijd.geefSpelers()) {
            spelers.add(speler.getNaam());
        }
        return spelers;
    }

    /**
     *
     * @return aantal sets die beide spelers gewonnen hebben
     */
    public List<Integer> geefAantalGewonnenSets() {
        return wedstrijd.geefAantalGewonnenSets();
    }

    /**
     *
     * @param spelerNaam
     * @return lijst van de kaarten die de speler nog kan kopen
     */
    public List<String> getKaartenTeKopen(String spelerNaam) {
        Speler speler = spelerRepository.geefSpeler(spelerNaam);
        List<String> kaartOmschrijving = new ArrayList();
        for (Kaart kaart : spelerRepository.getKaartenTeKopen(speler)) {
            kaartOmschrijving.add(kaart.getOmschrijving());
        }
        return kaartOmschrijving;
    }

    /**
     *
     * @param omschrijving
     * @return prijs van de meegegeven kaart
     */
    public double geefKaartPrijs(String omschrijving) {
        Kaart kaart = spelerRepository.geefKaart(omschrijving);
        return kaart.getPrijs();
    }

    /**
     * Geeft koopKaart() door aan de spelerRepository
     *
     * @param kaartOmSchrijving
     * @param spelerNaam
     */
    public void koopKaart(String kaartOmSchrijving, String spelerNaam) {
        Kaart kaart = spelerRepository.geefKaart(kaartOmSchrijving);
        Speler speler = spelerRepository.geefSpeler(spelerNaam);
        spelerRepository.koopKaart(kaart, speler);
    }

    /**
     *
     * @param spelerNaam
     * @return krediet van meegegeven speler
     */
    public double geefSpelerKrediet(String spelerNaam) {
        Speler speler = spelerRepository.geefSpeler(spelerNaam);
        return speler.getKrediet();
    }

    /**
     *
     * @param kaartOmschrijving
     * @return type van meegegeven kaart
     */
    public String geefKaartType(String kaartOmschrijving) {
        Kaart kaart = spelerRepository.geefKaart(kaartOmschrijving);
        return kaart.getType();
    }

    /**
     *
     * @param kaartOmschrijving
     * @return waarde van meegegeven kaart
     */
    public String geefKaartWaarde(String kaartOmschrijving) {
        Kaart kaart = spelerRepository.geefKaart(kaartOmschrijving);
        return kaart.getWaarde();
    }

    /**
     *
     * @return de naam van de speler die de set gewonnen heeft of null als het
     * gelijkspel is
     */
    public String controlleerSetGewonnen() {
        Speler speler = wedstrijd.controlleerSetGewonnen();
        if (speler != null) {
            return speler.getNaam();
        }
        return null;
    }

    /**
     *
     * @return de naam van de speler die de wedstrijd gewonnen heeft of null als
     * de wedstrijd nog niet gedaan is
     */
    public String controlleerWedstrijdGewonnen() {
        Speler winnaar = wedstrijd.controlleerWedstrijdGewonnen();
        if (winnaar != null) {
            spelerRepository.geefGewonnenKrediet(winnaar);
            return winnaar.getNaam();
        }
        return null;
    }

    /**
     * Geeft de wedstrijd een naam en geeft deze door aan de wedstrijdmapper
     *
     * @param naamWedstrijd
     */
    public void slaWedstrijdOp(String naamWedstrijd) {
        wedstrijd.setNaam(naamWedstrijd);
        wedstrijdMapper.bewaarWedstrijd(wedstrijd);
    }

    /**
     * Vraagt wedstrijd op in de Mapper met meegegeven wedstrijdnaam
     *
     * @param naamWedstrijd
     */
    public void laadWedstrijd(String naamWedstrijd) {
        wedstrijd = wedstrijdMapper.geefWedstrijd(naamWedstrijd);
    }

    /**
     *
     * @return alle wedstrijden in de mapper
     */
    public List<String> bewaardeWedstrijden() {
        return wedstrijdMapper.bewaardeWedstrijden();
    }

    /**
     *
     * @param naamWedstrijd
     * @return is de wedstrijd uniek
     */
    public boolean isWedstrijdUniek(String naamWedstrijd) {
        return wedstrijdMapper.isWedstrijdUniek(naamWedstrijd);
    }

    /**
     *
     * @return beide wedstrijdstapels
     */
    public List<List<String>> geefBeideStapels() {
        List<List<String>> wedstrijdkaarten = new ArrayList();
        wedstrijdkaarten.add(new ArrayList());
        wedstrijdkaarten.add(new ArrayList());
        int getal = 0;
        for (List<WedstrijdKaart> lwk : wedstrijd.geefBeideStapels()) {
            for (WedstrijdKaart wk : lwk) {
                wedstrijdkaarten.get(getal).add(wk.getKaart().getOmschrijving());
            }
            getal++;
        }
        return wedstrijdkaarten;
    }

}
