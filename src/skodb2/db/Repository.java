package skodb2.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

    private static Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try {
            properties.load(new FileInputStream("settings.properties"));
        } catch (IOException e) {
            System.out.println("Kunde inte ladda properties-fil.");
            e.printStackTrace();
        }
    }

    public static List<Kund> getAllCustomers() {
        List<Kund> kundList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = con.prepareStatement("select * from kund");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Kund temp = new Kund();
                temp.setId(rs.getInt("id"));
                temp.setNamn(rs.getString("namn"));
                temp.setOrtid(rs.getInt("ortid"));
                temp.setAnvändarnamn(rs.getString("användarnamn"));
                temp.setLösenord(rs.getString("lösenord"));

                kundList.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kundList;
    }

    public static List<Märke> getAllBrands() {
        List<Märke> out = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = con.prepareStatement("select * from märke");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Märke temp = new Märke();
                temp.setId(rs.getInt("id"));
                temp.setNamn(rs.getString("namn"));
                temp.setCreated(rs.getDate("created"));
                temp.setLastUpdated(rs.getDate("lastUpdated"));
                out.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static List<Sko> getAllShoes() {
        List<Sko> out = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = con.prepareStatement("select * from sko");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Sko temp = new Sko();
                temp.setId(rs.getInt("id"));
                temp.setNamn(rs.getString("namn"));
                temp.setPris(rs.getInt("pris"));
                temp.setStorlek(rs.getInt("storlek"));
                temp.setFärg(rs.getString("färg"));
                temp.setLagerstatus(rs.getInt("lagerstatus"));
                temp.setCreated(rs.getDate("created"));
                temp.setLastUpdated(rs.getDate("lastUpdated"));
                out.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static List<Sko> getAllActiveShoes() {
        List<Sko> out = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = con.prepareStatement("select * from sko where lagerstatus is not null");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Sko temp = new Sko();
                temp.setId(rs.getInt("id"));
                temp.setNamn(rs.getString("namn"));
                temp.setPris(rs.getInt("pris"));
                temp.setStorlek(rs.getInt("storlek"));
                temp.setFärg(rs.getString("färg"));
                temp.setLagerstatus(rs.getInt("lagerstatus"));
                temp.setCreated(rs.getDate("created"));
                temp.setLastUpdated(rs.getDate("lastUpdated"));
                out.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static int getShoeId(String name, String color, int size) {

        int out = -1;

        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = con.prepareStatement("""
                    select *
                    from sko
                    where namn like ? && färg like ? && storlek = ?;
                    """);

            pstmt.setString(1, name);
            pstmt.setString(2, color);
            pstmt.setInt(3, size);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                out = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static int getActiveOrderId(Kund k){
        int out = -1;
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = con.prepareStatement("select * from beställning where kundid=?");
            pstmt.setInt(1, k.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                out = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static Beställning getActiveOrder(Kund k){
        Beställning out = new Beställning();
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = con.prepareStatement("select * from beställning where kundid=?");
            pstmt.setInt(1, k.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Beställning temp = new Beställning();
                temp.setId(rs.getInt("id"));
                temp.setKundid(k.getId());
                temp.setSkoList(getOrderProducts(temp));
                temp.setAvslutad(rs.getBoolean("avslutad"));
                out = temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static List<Sko> getOrderProducts(Beställning b) {
        List<Sko> out = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = con.prepareStatement("select * from beställningsmap " +
                    "join sko on sko.id=skoid where beställningsid=?");
            pstmt.setInt(1, b.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Sko temp = new Sko();
                temp.setId(rs.getInt("id"));
                temp.setNamn(rs.getString("namn"));
                temp.setPris(rs.getInt("pris"));
                temp.setStorlek(rs.getInt("storlek"));
                temp.setFärg(rs.getString("färg"));
                temp.setLagerstatus(rs.getInt("lagerstatus"));
                temp.setCreated(rs.getDate("created"));
                temp.setLastUpdated(rs.getDate("lastUpdated"));
                out.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static void finalizeOrder(int beställningsid){
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement pstmt = con.prepareStatement("update beställning set avslutad = true where id=?");
            pstmt.setInt(1, beställningsid);
            int i = pstmt.executeUpdate();
            System.out.println(i + " rader påverkade");
            System.out.println("Beställning färdigställd");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void addToCart(int kundid, int beställningsid, int skoid){
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            CallableStatement pstmt = con.prepareCall("call addToCart(?, ?, ?)");
            pstmt.setInt(1, kundid);
            pstmt.setInt(2, beställningsid);
            pstmt.setInt(3, skoid);
            pstmt.execute();
            System.out.println("Sko tillagd i beställningen");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void rateShoe(int skoid, int kundid, int betyg, String kommentar){
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            CallableStatement pstmt = con.prepareCall("call rate(?, ?, ?, ?)");
            pstmt.setInt(1, skoid);
            pstmt.setInt(2, kundid);
            pstmt.setInt(3, betyg);
            pstmt.setString(4, kommentar);
            pstmt.execute();
            System.out.println("Sko Betygsatt");
        }catch(Exception e){
            e.printStackTrace();
        }
    }



}
