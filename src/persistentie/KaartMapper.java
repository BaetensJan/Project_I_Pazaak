package persistentie;

import domein.Kaart;
import domein.Speler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KaartMapper {

    public KaartMapper() {

    }

    public List<Kaart> getKaarten() {
        List<Kaart> kaarten = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(Connectie.JDBC_URL)) {
            Statement st = con.createStatement();
            String sql = ("SELECT type, waarde, omschrijving, prijs FROM ID222177_g02.Kaart;");
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String type = rs.getString("type");
                String waarde = rs.getString("waarde");
                String omschrijving = rs.getString("omschrijving");
                double prijs = rs.getDouble("prijs");
                kaarten.add(new Kaart(type, waarde, omschrijving, prijs));
            }
        } catch (SQLException sqle) {
            Logger.getLogger(KaartMapper.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return kaarten;
    }

    public List<Kaart> getSpelerKaarten(Speler speler) {
        List<Kaart> kaarten = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(Connectie.JDBC_URL)) {
            Statement st = con.createStatement();
            String sql = ("SELECT ID222177_g02.SpelerKaart.type, ID222177_g02.SpelerKaart.waarde, ID222177_g02.Kaart.omschrijving, ID222177_g02.Kaart.prijs FROM ID222177_g02.SpelerKaart "
                    + "LEFT OUTER JOIN ID222177_g02.Kaart "
                    + "ON ID222177_g02.SpelerKaart.type=ID222177_g02.Kaart.type AND ID222177_g02.SpelerKaart.waarde=ID222177_g02.Kaart.waarde "
                    + "WHERE ID222177_g02.SpelerKaart.naam='" + speler.getNaam() + "';");
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String type = rs.getString("type");
                String waarde = rs.getString("waarde");
                String omschrijving = rs.getString("omschrijving");
                double prijs = rs.getDouble("prijs");
                kaarten.add(new Kaart(type, waarde, omschrijving, prijs));
            }
        } catch (SQLException sqle) {
            Logger.getLogger(KaartMapper.class.getName()).log(Level.SEVERE, null, sqle);
        }
        return kaarten;
    }

    public void koopKaart(Kaart kaart, Speler speler) {
        try (Connection con = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO ID222177_g02.SpelerKaart(naam,type,waarde)" + "VALUES(?,?,?)");
            ps.setString(1, speler.getNaam());
            ps.setString(2, kaart.getType());
            ps.setString(3, kaart.getWaarde());
            ps.executeUpdate();

        } catch (SQLException sqle) {
            Logger.getLogger(KaartMapper.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }
}
