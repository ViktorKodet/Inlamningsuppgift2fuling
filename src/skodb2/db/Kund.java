package skodb2.db;

import java.util.Date;
import java.util.List;

public class Kund {

    private int id;
    private String namn;
    private Ort ort;
    private String användarnamn;
    private String lösenord;
    private Date created;
    private Date lastUpdated;
    List<Beställning> beställningList;


    Kund(){
        beställningList = Repository.getAllOrders(this);
        beställningList.forEach(e -> e.setKund(this));
    }

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

    public Ort getOrt() {
        return ort;
    }

    public void setOrt(Ort ort) {
        this.ort = ort;
    }

    public String getAnvändarnamn() {
        return användarnamn;
    }

    public void setAnvändarnamn(String användarnamn) {
        this.användarnamn = användarnamn;
    }

    public String getLösenord() {
        return lösenord;
    }

    public void setLösenord(String lösenord) {
        this.lösenord = lösenord;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Kund{" +
                "id=" + id +
                ", namn='" + namn + '\'' +
                ", ort=" + ort.getNamn() +
                ", användarnamn='" + användarnamn + '\'' +
                ", lösenord='" + lösenord + '\'' +
                ", created=" + created +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
