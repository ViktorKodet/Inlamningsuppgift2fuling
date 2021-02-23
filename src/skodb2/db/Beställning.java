package skodb2.db;

import java.util.List;

public class Beställning {
    int id;
    int kundid;
    List<Sko> skoList;
    boolean avslutad;

    public void printAllShoes(){
        for (Sko s : skoList){
            System.out.println(s.toStringForCustomer());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKundid() {
        return kundid;
    }

    public void setKundid(int kundid) {
        this.kundid = kundid;
    }

    public List<Sko> getSkoList() {
        return skoList;
    }

    public void setSkoList(List<Sko> skoList) {
        this.skoList = skoList;
    }

    public boolean isAvslutad() {
        return avslutad;
    }

    public void setAvslutad(boolean avslutad) {
        this.avslutad = avslutad;
    }
}
