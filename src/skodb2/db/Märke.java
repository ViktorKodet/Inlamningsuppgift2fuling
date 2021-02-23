package skodb2.db;

import java.sql.Date;
import java.util.List;

public class Märke {
    int id;
    String namn;
    private Date created;
    private Date lastUpdated;
    private static List<Märke> allBrands = Repository.getAllBrands();

    public String getNamn() {
        return namn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Märke{" +
                "id=" + id +
                ", namn='" + namn + '\'' +
                ", created=" + created +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
