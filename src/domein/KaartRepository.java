package domein;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import persistentie.KaartMapper;

/**
 * KaartRepository Klasse
 *
 * @author Baetens Jan
 */
public class KaartRepository {

    private List<Kaart> kaarten;
    private KaartMapper kaartMapper;

    /**
     * Constructor
     *
     */
    public KaartRepository() {
        kaartMapper = new KaartMapper();
        kaarten = kaartMapper.getKaarten();
    }

    /**
     * Maakt kaart aan met de meegegeven parameters en voegt deze toe aan de
     * kaarten lijst
     *
     * @param type
     * @param waarde
     * @param omschrijving
     */
    public void maakKaart(String type, String waarde, String omschrijving) {
        try {
            kaarten.add(new Kaart(type, waarde, omschrijving));
        } catch (InputMismatchException ime) {
            throw new InputMismatchException("waardeKaartInputMismatchException");
        }
    }

    /**
     * wordt gebruikt door de mappers om kaarten te terug te geven
     *
     * @param type
     * @param waarde
     * @return Kaart met meegegeven type en waarde
     */
    public Kaart geefKaart(String type, String waarde) {
        int i = 0;
        boolean isGevonden = false;

        while (i < kaarten.size() && isGevonden == false) {
            if (kaarten.get(i).getType().equals(type) && kaarten.get(i).getWaarde().equals(waarde)) {
                isGevonden = true;
            } else {
                i++;
            }
        }
        return kaarten.get(i);
    }

    /**
     *
     * @param speler
     * @return Lijst van kaarten die de speler bezit
     */
    public List<Kaart> getKaartenSpeler(Speler speler) {
        return kaartMapper.getSpelerKaarten(speler);
    }

    /**
     *
     * @param speler
     * @return Lijst van kaarten die de speler nog kan kopen
     */
    public List<Kaart> getKaartenTeKopen(Speler speler) {
        List<Kaart> alleKaarten = kaartMapper.getKaarten(), spelerKaarten = kaartMapper.getSpelerKaarten(speler), teKopenKaarten = new ArrayList();
        boolean isGevonden = false;
        for (Kaart kaart : alleKaarten) {
            isGevonden = false;
            for (Kaart spelerKaart : spelerKaarten) {
                if (spelerKaart.getOmschrijving().equals(kaart.getOmschrijving())) {
                    isGevonden = true;
                }
            }
            if (!isGevonden) {
                teKopenKaarten.add(kaart);
            }
        }
        return teKopenKaarten;
    }

    /**
     * Koopt meegegeven kaart voor de meegegeven speler, Roept mapper aan
     *
     * @param kaart
     * @param speler
     */
    public void koopKaart(Kaart kaart, Speler speler) {
        kaartMapper.koopKaart(kaart, speler);

    }

    /**
     *
     * @param omschrijving
     * @return kaart met meegegeven omschrijving
     */
    public Kaart geefKaart(String omschrijving) {
        int i = 0;
        boolean isGevonden = false;
        do {
            if (kaarten.get(i).getOmschrijving().equals(omschrijving)) {
                isGevonden = true;
                return kaarten.get(i);
            } else if (i == kaarten.size()) {
                isGevonden = true;
            } else {
                i++;
            }
        } while (!isGevonden);
        return null;
    }
}
