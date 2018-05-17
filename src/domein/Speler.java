package domein;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Speler Klasse
 *
 * @author Baetens Jan
 */
public class Speler {

    private String naam;
    private int geboortejaar;
    private double krediet;

    /**
     * Constructor voor bestaande spelers uit mappers
     *
     * @param naam
     * @param geboortejaar
     * @param krediet
     */
    public Speler(String naam, int geboortejaar, double krediet) {
        controleerNaam(naam);
        this.naam = naam;
        controleerGeboortejaar(geboortejaar);
        this.geboortejaar = geboortejaar;
        this.krediet = krediet;
    }

    /**
     * Constructor voor volledig nieuwe spelers
     *
     * @param naam
     * @param geboortejaar
     */
    public Speler(String naam, int geboortejaar) {
        this(naam, geboortejaar, 0.0);
    }

    private void controleerNaam(String naam) {
        if (Pattern.matches("^[a-zA-Z]", String.format("%s", naam.charAt(0))) == false) {
            throw new IllegalArgumentException("naamLetterException");
        }
        if (Pattern.matches("[a-zA-Z0-9]{3,}", naam) == false) {
            throw new IllegalArgumentException("naamInputMismatch");
        }
    }

    private int controleerGeboortejaar(int geboortejaar) {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        Date currentTime = localCalendar.getTime();
        int jaar = localCalendar.get(Calendar.YEAR);
        if (jaar - geboortejaar < 6 || jaar - geboortejaar > 99) {
            throw new IllegalArgumentException("geboorteJaarException");
        }
        return geboortejaar;
    }

    /**
     *
     * @return naam van speler
     */
    public String getNaam() {
        return naam;
    }

    /**
     *
     * @return geboortejaar van speler
     */
    public int getGeboortejaar() {
        return geboortejaar;
    }

    /**
     *
     * @return krediet van speler
     */
    public double getKrediet() {
        return krediet;
    }

    /**
     * Past Krediet aan van de speler
     *
     * @param krediet
     */
    public void setKrediet(double krediet) {
        this.krediet = krediet;
    }
}
