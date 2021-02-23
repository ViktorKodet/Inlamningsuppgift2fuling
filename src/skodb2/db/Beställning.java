package skodb2.db;

import java.util.List;

public class Beställning {
    int id;
    Kund kund;
    List<Sko> skoList;
    boolean avslutad;


    Beställning(){
        skoList = Repository.getOrderProducts(this);
    }

    public void printAllShoes(){
        for (Sko s : skoList){
            System.out.println(s.toStringForCustomer());
        }
    }

    public void printAllShoesForOrder(){
        for (Sko s : skoList){
            System.out.println(s.toStringForOrder());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Kund getKund() {
        return kund;
    }

    public void setKund(Kund kund) {
        this.kund = kund;
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
