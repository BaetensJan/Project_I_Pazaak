package domein;

/**
 * WedstrijdKaart Klasse
 *
 * @author Baetens Jan
 */
public class WedstrijdKaart {

    private Kaart kaart;
    private String teken;

    /**
     * Constructor
     *
     * @param kaart
     */
    public WedstrijdKaart(Kaart kaart) {
        this.kaart = kaart;
        if (!kaart.getType().equals("+/-")) {
            teken = kaart.getType();
        }
    }

    /**
     *
     * @return kaart van de WedstrijdKaart
     */
    public Kaart getKaart() {
        return kaart;
    }

    /**
     * Sets het teken van de WedstrijdKaart
     *
     * @param teken
     */
    public void setTeken(String teken) {
        this.teken = teken;
    }

    /**
     *
     * @return het teken van de WedstrijdKaart
     */
    public String getTeken() {
        return teken;
    }
}
