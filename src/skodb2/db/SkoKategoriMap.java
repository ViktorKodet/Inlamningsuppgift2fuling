package skodb2.db;

import java.util.List;

public class SkoKategoriMap {

    private int id;
    private int skoId;
    private int kategoriId;
    public static List<SkoKategoriMap> allShoeCategoryMappings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSkoId() {
        return skoId;
    }

    public void setSkoId(int skoId) {
        this.skoId = skoId;
    }

    public int getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
    }

    public static List<SkoKategoriMap> getAllShoeCategoryMappings() {
        return allShoeCategoryMappings;
    }
}
