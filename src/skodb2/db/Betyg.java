package skodb2.db;

import java.sql.Date;

public class Betyg {

    private int värde;
    private String text;
    private Date created;
    private Date lastUpdated;

    public Betyg(int värde, String text) {
        this.värde = värde;
        this.text = text;
    }

    public int getVärde() {
        return värde;
    }

    public void setVärde(int värde) {
        this.värde = värde;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
