package domein;

/**
 * Kaart Klasse
 *
 * @author Baetens Jan
 */
public class Kaart {

    private String type;
    private String waarde;
    private String omschrijving;
    private double prijs;

    private DomeinController dc;

    /**
     * Constructor
     *
     * @param type
     * @param waarde
     * @param omschrijving
     */
    public Kaart(String type, String waarde, String omschrijving) {
        this.type = contoleerType(type);
        this.waarde = waarde;
        this.omschrijving = omschrijving;
    }

    /**
     * Constructor met prijs
     *
     * @param type
     * @param waarde
     * @param omschrijving
     * @param prijs
     */
    public Kaart(String type, String waarde, String omschrijving, double prijs) {
        this.type = contoleerType(type);
        this.waarde = waarde;
        this.omschrijving = omschrijving;
        this.prijs = prijs;
    }

    private String contoleerType(String type) {
        if (!"+".equals(type) && !"-".equals(type) && !"+/-".equals(type) && !"".equals(type) && !"xT".equals(type) && !"D".equals(type) && !"x&y".equals(type) && !"x+/-y".equals(type)) {
            throw new IllegalArgumentException("kaartTypeException");
        } else {
            return type;
        }
    }

    /**
     *
     * @return type van kaart
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return waarde van kaart
     */
    public String getWaarde() {
        return waarde;
    }

    /**
     *
     * @return omschrijving van kaart
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     *
     * @return prijs van kaart
     */
    public double getPrijs() {
        return prijs;
    }
}
