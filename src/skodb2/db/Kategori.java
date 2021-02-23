package skodb2.db;

import java.sql.Date;
import java.util.List;

public class Kategori {

    public static List<Kategori> allCategories;

    private int id;
    private String namn;
    private Date created;
    private Date lastUpdated;

    public Kategori(int id, String namn) {
        this.id = id;
        this.namn = namn;
    }

    public Kategori(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public Date getCreated() {
        return created;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}