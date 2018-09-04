package sthlm.malmo.christofferwiregren.gogogreen;

import android.support.annotation.NonNull;

public class Vegetable {
    private String name;
    private String ID;
    private int price;
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal(){ return quantity * price; }

    public Vegetable() {
    }

    public Vegetable(String name, String ID, int price, int quantity) {

        this.name = name;
        this.ID = ID;
        this.price = price;
        this.quantity = quantity;

    }

    @Override
    public String toString() {
        return "Vegetable{" +
                "name='" + name + '\'' +
                ", ID=" + ID +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }


}
