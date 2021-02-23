package skodb2.db;

import java.sql.Date;

public class Betygsättning {

    private int id;
    private String kommentar;
    private Betyg betyg;
    private Sko sko;
    private Kund kund;
    private Date created;
    private Date lastUpdated;

    public Betygsättning(int id, String kommentar, Betyg betyg, Sko sko, Kund kund) {
        this.id = id;
        this.kommentar = kommentar;
        this.betyg = betyg;
        this.sko = sko;
        this.kund = kund;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public Betyg getBetyg() {
        return betyg;
    }

    public void setBetyg(Betyg betyg) {
        this.betyg = betyg;
    }

    public Sko getSko() {
        return sko;
    }

    public Kund getKund() {
        return kund;
    }
}
