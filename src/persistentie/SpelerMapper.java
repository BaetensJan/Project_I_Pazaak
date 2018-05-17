package persistentie;

import domein.Kaart;
import domein.Speler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpelerMapper {

    public SpelerMapper() {

    }

    public void bewaarNieuweSpeler(Speler speler) throws SQLException {

        try (Connection con = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO ID222177_g02.Speler(naam, geboortejaar, krediet)" + "VALUES(?,?,?)");
            ps.setString(1, speler.getNaam());
            ps.setInt(2, speler.getGeboortejaar());
            ps.setDouble(3, speler.getKrediet());
            ps.executeUpdate();
            bewaarSpelerKaart(speler, "+", "2");
            bewaarSpelerKaart(speler, "+", "4");
            bewaarSpelerKaart(speler, "+", "5");
            bewaarSpelerKaart(speler, "+", "6");
            bewaarSpelerKaart(speler, "+/-", "1");
            bewaarSpelerKaart(speler, "+/-", "3");
            bewaarSpelerKaart(speler, "-", "1");
            bewaarSpelerKaart(speler, "-", "2");
            bewaarSpelerKaart(speler, "-", "3");
            bewaarSpelerKaart(speler, "-", "5");
        } catch (SQLException ex) {
            Logger.getLogger(SpelerMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void bewaarSpelerKaart(Speler speler, String type, String waarde) {

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ID222177_g02.SpelerKaart(naam,type,waarde)" + "VALUES(?,?,?)");
            ps.setString(1, speler.getNaam());
            ps.setString(2, type);
            ps.setString(3, waarde);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SpelerMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Speler> getSpelers() {

        List<Speler> spelers = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement ps = conn.prepareStatement("SELECT naam, geboortejaar, krediet FROM ID222177_g02.Speler;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String naam = rs.getString("naam");
                int geboortejaar = rs.getInt("geboortejaar");
                double krediet = rs.getDouble("krediet");
                spelers.add(new Speler(naam, geboortejaar, krediet));
            }
        } catch (SQLException sqle) {
            Logger.getLogger(SpelerMapper.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return spelers;
    }

    public int getNaamLengte() {
        return 255;
    }

    public void koopKaart(Kaart kaart, Speler speler) {
        double krediet = speler.getKrediet() - kaart.getPrijs();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement ps = conn.prepareStatement("UPDATE `ID222177_g02`.`Speler` SET `krediet`='" + krediet + "' WHERE `naam`='" + speler.getNaam() + "';");
            ps.executeUpdate();
        } catch (SQLException sqle) {
            Logger.getLogger(SpelerMapper.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }

    public void geefGewonnenKrediet(Speler speler) {
        double krediet = speler.getKrediet();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement ps = conn.prepareStatement("UPDATE `ID222177_g02`.`Speler` SET `krediet`='" + krediet + "' WHERE `naam`='" + speler.getNaam() + "';");
            ps.executeUpdate();
        } catch (SQLException sqle) {
            Logger.getLogger(SpelerMapper.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }
}
