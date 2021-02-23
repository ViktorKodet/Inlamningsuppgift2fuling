package skodb2.db;

import java.sql.Date;

public class Slutilager {
    int id;
    int skoid;
    Date datum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSkoid() {
        return skoid;
    }

    public void setSkoid(int skoid) {
        this.skoid = skoid;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
}
