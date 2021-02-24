package skodb2.db;

import jdk.jfr.Category;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Repository {

    private static Properties properties = new Properties();

    static {
        loadProperties();
        Märke.allBrands = getAllBrands();
        Kategori.allCategories = getAllCategories();
        SkoKategoriMap.allShoeCategoryMappings = getAllShoeCategoryMappings();
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
                temp.setOrt(getOrtFromDB(temp.getId()));
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

    public static List<Kategori> getAllCategories() {
        List<Kategori> out = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = con.prepareStatement("select * from kategori");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Kategori temp = new Kategori();
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

    public static int getActiveOrderId(Kund k) {
        int out = -1;
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = con.prepareStatement("select * from beställning where kundid=? and avslutad=false");
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

    public static Beställning getActiveOrder(Kund k) {
        Beställning out = new Beställning();
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = con.prepareStatement("select * from beställning where kundid=? and avslutad=false");
            pstmt.setInt(1, k.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Beställning temp = new Beställning();
                temp.setId(rs.getInt("id"));
                temp.setKund(k);
                temp.setSkoList(getOrderProducts(temp));
                temp.setAvslutad(rs.getBoolean("avslutad"));
                out = temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

//    public static void parseShoeCategories(Sko s) {
//        try (Connection con = DriverManager.getConnection(
//                properties.getProperty("dbString"),
//                properties.getProperty("username"),
//                properties.getProperty("password"))) {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            PreparedStatement pstmt = con.prepareStatement("select * from skokategorimap where skoid = ?");
//            pstmt.setInt(1, s.getId());
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                int temp = rs.getInt("kategoriId");
//                for (Kategori k : Kategori.allCategories) {
//                    if (k.getId() == temp)
//                        s.getKategoriList().add(k);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static List<Beställning> getAllOrders(Kund k) {
        List<Beställning> out = new ArrayList<>();
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
                temp.setKund(k);
                temp.setSkoList(getOrderProducts(temp));
                temp.setAvslutad(rs.getBoolean("avslutad"));
                out.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static List<SkoKategoriMap> getAllShoeCategoryMappings() {
        List<SkoKategoriMap> out = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = con.prepareStatement("select * from skokategorimap");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                SkoKategoriMap temp = new SkoKategoriMap();
                temp.setId(rs.getInt("id"));
                temp.setSkoId(rs.getInt("skoid"));
                temp.setKategoriId(rs.getInt("kategoriid"));
                out.add(temp);
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
                int märkeid = rs.getInt("märkeid");
                temp.setMärke(Märke.allBrands.stream().filter(n -> n.id == märkeid).collect(Collectors.toList()).get(0));
                temp.setStorlek(rs.getInt("storlek"));
                temp.setFärg(rs.getString("färg"));
                temp.setLagerstatus(rs.getInt("lagerstatus"));
                temp.setCreated(rs.getDate("created"));
                temp.setLastUpdated(rs.getDate("lastUpdated"));
                fillShoeCategoriesList(temp);

                System.out.println("* * * * * * * * * * * * * HEJ * * * * * * * * * * * * *");
                System.out.println(temp.getKategoriList());

//                parseShoeCategories(temp);
                out.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static void fillShoeCategoriesList(Sko s) {
        List<Integer> kategoriIds = SkoKategoriMap.allShoeCategoryMappings.stream()
                .filter(k -> k.getSkoId() == s.getId())
                .map(SkoKategoriMap::getKategoriId).collect(Collectors.toList());

        for (Kategori k : Kategori.allCategories) {
            for (Integer i : kategoriIds) {
                if (i == k.getId()) {
                    s.getKategoriList().add(k);
                }
            }
        }
    }

    public static void finalizeOrder(int beställningsid) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addToCart(int kundid, int beställningsid, int skoid) {
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            boolean inStock = isInStock(skoid);
            CallableStatement pstmt = con.prepareCall("call addToCart(?, ?, ?)");
            pstmt.setInt(1, kundid);
            pstmt.setInt(2, beställningsid);
            pstmt.setInt(3, skoid);
            pstmt.execute();
            System.out.println(inStock ? "Sko tillagd i beställningen" : "Sko tillagd men finns inte i lager.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    public static boolean isInStock(int skoid) {
        int i = 0;
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement stmt = con.prepareStatement("select lagerstatus from sko where id = ?");
            stmt.setInt(1, skoid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.wasNull()) {
                    break;
                }
                i = rs.getInt("lagerstatus");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }
        return i != 0;
    }

    public static void rateShoe(int skoid, int kundid, int betyg, String kommentar) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Ort getOrtFromDB(int kundid) {
        Ort temp = null;
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement stmt = con.prepareStatement("select * from ort join kund on kund.ortid=ort.id where kund.id=?");
            stmt.setInt(1, kundid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                temp = new Ort(rs.getInt("id"), rs.getString("namn"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static void printAvgRatingAndComments(int shoeid) {
        System.out.println("Genomsnittligt betyg : " + getAvgRating(shoeid));
        System.out.println("Kommentarer:");
        getComments(shoeid).forEach(System.out::println);

    }

    public static double getAvgRating(int shoeid) {
        if (shoeid < 0) {
            throw new IllegalStateException("Nåt gick fel");
        }
        double out = -1;
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            CallableStatement pstmt = con.prepareCall("call getProdAvg(?, ?)");

            pstmt.registerOutParameter(2, Types.DOUBLE);
            pstmt.setInt(1, shoeid);
            pstmt.execute();
            out = pstmt.getDouble(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static List<String> getComments(int shoeid) {
        if (shoeid < 0) {
            throw new IllegalStateException("Nåt gick fel");
        }
        List<String> out = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = con.prepareStatement("select kommentar from betygsättning where skoid like ? and kommentar is not null");
            pstmt.setInt(1, shoeid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                out.add(rs.getString("kommentar"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static List<Slutilager> getOutOfStock() {
        List<Slutilager> out = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(
                properties.getProperty("dbString"),
                properties.getProperty("username"),
                properties.getProperty("password"))) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            PreparedStatement pstmt = con.prepareStatement("select * from slutilager");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Slutilager temp = new Slutilager();
                temp.setId(rs.getInt("id"));
                temp.setSkoid(rs.getInt("skoid")); //hämta sko objekt?
                temp.setDatum(rs.getDate("datum"));
                out.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }


}
