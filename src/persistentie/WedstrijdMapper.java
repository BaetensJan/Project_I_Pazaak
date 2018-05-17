package persistentie;

import domein.Kaart;
import domein.Speler;
import domein.SpelerRepository;
import domein.Wedstrijd;
import domein.WedstrijdKaart;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WedstrijdMapper {

    private SpelerRepository spelerRepo;

    public WedstrijdMapper(SpelerRepository spelerRepo) {
        this.spelerRepo = spelerRepo;
    }

    public List<String> bewaardeWedstrijden() {

        List<String> opgeslagen = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g02.Wedstrijd");
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                String wedstrijdnaam = rs.getString("naamWedstrijd");
                opgeslagen.add(wedstrijdnaam);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return opgeslagen;
    }

    public void bewaarWedstrijd(Wedstrijd deWedstrijd) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {

            PreparedStatement queryWedstrijd = conn.prepareStatement("INSERT INTO ID222177_g02.Wedstrijd (naamWedstrijd)" + "VALUES (?)");
            queryWedstrijd.setString(1, deWedstrijd.getNaam());
            queryWedstrijd.executeUpdate();

            PreparedStatement querySpelerWedstrijd;
            PreparedStatement querySpelerWedstrijdKaart;
            for (Speler speler : deWedstrijd.geefSpelers()) {

                querySpelerWedstrijd = conn.prepareStatement("INSERT INTO ID222177_g02.SpelerWedstrijd (naamWedstrijd, naamSpeler, score, begonnen)"
                        + "VALUES (?, ?, ?, ?)");
                querySpelerWedstrijd.setString(1, deWedstrijd.getNaam());
                querySpelerWedstrijd.setString(2, speler.getNaam());
                querySpelerWedstrijd.setInt(3, deWedstrijd.geefAantalGewonnenSets().get(deWedstrijd.geefSpelers().indexOf(speler)));
                if (speler.equals(deWedstrijd.getBegonnen())) {
                    querySpelerWedstrijd.setInt(4, 1);
                } else {
                    querySpelerWedstrijd.setInt(4, 0);
                }
                querySpelerWedstrijd.executeUpdate();

                for (WedstrijdKaart wk : deWedstrijd.geefBeideStapels().get(deWedstrijd.geefSpelers().indexOf(speler))) {
                    Kaart kaart = wk.getKaart();
                    querySpelerWedstrijdKaart = conn.prepareStatement("INSERT INTO ID222177_g02.SpelerWedstrijdKaart (naamSpeler, naamWedstrijd, type, waarde)"
                            + "VALUES ('" + speler.getNaam() + "', '" + deWedstrijd.getNaam() + "', '" + kaart.getType() + "', '" + kaart.getWaarde() + "');");
                    querySpelerWedstrijdKaart.executeUpdate();
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Wedstrijd geefWedstrijd(String naamWedstrijd) {
        List<Integer> aantalGewonnenSets = new ArrayList();
        List<Speler> deSpelers = new ArrayList();
        List<List<WedstrijdKaart>> wedstrijdstapels = new ArrayList();
        Speler begonnenSpeler = null;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement ps = conn.prepareStatement("SELECT naamSpeler, score, begonnen FROM ID222177_g02.SpelerWedstrijd WHERE naamWedstrijd = '" + naamWedstrijd + "';");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String naam = rs.getString("naamSpeler");
                int score = rs.getInt("score");
                int begonnen = rs.getInt("begonnen");
                aantalGewonnenSets.add(score);
                Speler speler = spelerRepo.geefSpeler(naam);
                deSpelers.add(speler);
                wedstrijdstapels.add(new ArrayList());
                if (begonnen == 1) {
                    begonnenSpeler = speler;
                }
            }

            for (Speler speler : deSpelers) {
                int spelerIndex = deSpelers.indexOf(speler);
                ps = conn.prepareStatement("SELECT type, waarde FROM ID222177_g02.SpelerWedstrijdKaart WHERE naamWedstrijd = '" + naamWedstrijd + "' AND naamSpeler = '" + speler.getNaam() + "';");
                rs = ps.executeQuery();
                while (rs.next()) {
                    String type = rs.getString("type");
                    String waarde = rs.getString("waarde");
                    wedstrijdstapels.get(spelerIndex).add(new WedstrijdKaart(spelerRepo.geefKaart(type, waarde)));
                }
            }
            return new Wedstrijd(naamWedstrijd, begonnenSpeler, deSpelers, aantalGewonnenSets, wedstrijdstapels);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean isWedstrijdUniek(String naamWedstrijd) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ID222177_g02.Wedstrijd WHERE naamWedstrijd = ?");
            ps.setString(1, naamWedstrijd);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
