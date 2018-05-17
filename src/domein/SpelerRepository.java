package domein;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import persistentie.SpelerMapper;

/**
 * SpelerRepository Klasse
 *
 * @author Baetens Jan
 */
public class SpelerRepository {

    private List<Speler> spelers;
    private SpelerMapper spelerMapper;
    private KaartRepository kaartRepository;

    /**
     * Constructor
     *
     */
    public SpelerRepository() {
        spelerMapper = new SpelerMapper();
        kaartRepository = new KaartRepository();
        spelers = spelerMapper.getSpelers();
    }

    /**
     * Maakt nieuwe speler aan, en voegt deze toe aan de spelers lijst Mapper
     * wordt opgeroepen voor het opslaan in de databank
     *
     * @param naam
     * @param geboortejaar
     * @throws SQLException
     */
    public void maakNieuweSpeler(String naam, int geboortejaar) throws SQLException {

        if (bestaatSpeler(naam)) {
            throw new IllegalArgumentException("nietUniek");
        }
        if (naam.length() > spelerMapper.getNaamLengte()) {
            throw new IllegalArgumentException("naamTeLang");
        }
        Speler speler = new Speler(naam, geboortejaar);
        spelerMapper.bewaarNieuweSpeler(speler);
        spelers.add(speler);
        System.out.printf("%s", speler.toString());
    }

    /**
     *
     * @return lijst van alle spelers
     */
    public List<String> toonSpelers() {

        List<String> lijstNamen = new ArrayList();
        for (Speler speler : spelers) {

            lijstNamen.add(speler.getNaam());
        }
        return lijstNamen;
    }

    /**
     *
     * @param naam
     * @return bestaat speler al
     */
    public boolean bestaatSpeler(String naam) {

        int i = 0;
        boolean isGevonden = false;

        while (i < spelers.size() && isGevonden == false) {
            if (spelers.get(i).getNaam().equals(naam)) {
                isGevonden = true;
            } else {
                i++;
            }
        }
        return isGevonden;
    }

    /**
     *
     * @param naam
     * @return speler met meegegeven naam
     */
    public Speler geefSpeler(String naam) {
        int i = 0;
        boolean isGevonden = false;

        while (i < spelers.size() && isGevonden == false) {
            if (spelers.get(i).getNaam().equals(naam)) {
                isGevonden = true;
            } else {
                i++;
            }
        }
        return spelers.get(i);
    }

    /**
     *
     * @return aantal spelers
     */
    public int geefAantalSpelers() {
        return spelers.size();
    }

    /**
     *
     * @param speler
     * @return kaarten van de meegegeven speler
     */
    public List<Kaart> getKaartenSpeler(Speler speler) {
        return kaartRepository.getKaartenSpeler(speler);
    }

    /**
     *
     * @param speler
     * @return kaarten die de meegegeven speler kan kopen
     */
    public List<Kaart> getKaartenTeKopen(Speler speler) {
        return kaartRepository.getKaartenTeKopen(speler);
    }

    /**
     * Koopt meegegeven kaart als de meegegeven speler genoeg krediet heeft
     * Mapper wordt aangeroepen voor de aanpassing in de databank
     *
     * @param kaart
     * @param speler
     */
    public void koopKaart(Kaart kaart, Speler speler) {
        if (speler.getKrediet() >= kaart.getPrijs()) {
            kaartRepository.koopKaart(kaart, speler);
            spelerMapper.koopKaart(kaart, speler);
            speler.setKrediet(speler.getKrediet() - kaart.getPrijs());
        } else {
            throw new IllegalArgumentException("teWeinigKrediet");
        }
    }

    /**
     *
     * @param omschrijving
     * @return kaart met meegegeven omschrijving
     */
    public Kaart geefKaart(String omschrijving) {
        return kaartRepository.geefKaart(omschrijving);
    }

    /**
     * methode wordt gebruikt door mapper om kaart aan te maken met type en
     * waarde
     *
     * @param type
     * @param waarde
     * @return kaart met meegegeven type en waarde
     */
    public Kaart geefKaart(String type, String waarde) {
        return kaartRepository.geefKaart(type, waarde);
    }

    /**
     * Geeft de gewonnen speler krediet
     *
     * @param speler
     */
    public void geefGewonnenKrediet(Speler speler) {
        spelerMapper.geefGewonnenKrediet(speler);
    }
}
