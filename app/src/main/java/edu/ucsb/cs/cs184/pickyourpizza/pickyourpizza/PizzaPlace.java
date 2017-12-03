package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

/**
 * Created by dsefl on 11/30/2017.
 */

public class PizzaPlace {
    private String chain;
    private String pizzaTypes;
    private String price;
    private int photoID;

    public PizzaPlace(String chain, String pizzaTypes, String price, int photoID) {
        this.chain = chain;
        this.pizzaTypes = pizzaTypes;
        this.price = price;
        this.photoID = photoID;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public String getPizzaSizes() {
        return pizzaTypes;
    }

    public void setPizzaSizes(String pizzaTypes) {
        this.pizzaTypes = pizzaTypes;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }
}
