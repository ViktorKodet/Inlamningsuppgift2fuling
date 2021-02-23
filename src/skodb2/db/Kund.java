package skodb2.db;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Kund {

    private int id;
    private String namn;
    private int ortid;
    private String användarnamn;
    private String lösenord;
    private Date created;
    private Date lastUpdated;
    List<Beställning> beställningList;

    Kund(){
        beställningList = Repository.getAllOrders(this);
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

    public int getOrtid() {
        return ortid;
    }

    public void setOrtid(int ortid) {
        this.ortid = ortid;
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
                ", ortid=" + ortid +
                ", användarnamn='" + användarnamn + '\'' +
                ", lösenord='" + lösenord + '\'' +
                ", created=" + created +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
