package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PizzaPlaceInfo {

    private String businessName;
    private HashMap<String,Double> pizzaSizes;
    private HashMap<String,Double> pizzaStyles;
    private HashMap<String,Double> toppings_Prices;

    public PizzaPlaceInfo(){}

    public PizzaPlaceInfo(String businessName,HashMap<String,Double> pizzaSizes,HashMap<String,Double> pizzaStyles, HashMap<String,Double> toppings_Prices){
        this.businessName = businessName;
        this.pizzaSizes = pizzaSizes;
        this.pizzaStyles = pizzaStyles;
        this.toppings_Prices = toppings_Prices;
    }

    public String getName(){
        return businessName;
    }

    public void setName(String name){ businessName = name;}

    public HashMap<String,Double> getSize(){
        return pizzaSizes;
    }

    public void setPizzaSizes(HashMap<String,Double> sizes) { pizzaStyles = sizes;}

    public HashMap<String,Double> getStyle(){
        return pizzaStyles;
    }

    public void setPizzaStyles(HashMap<String,Double> styles) { pizzaStyles = styles;}

    public HashMap<String, Double> getToppings_Price(){
        return toppings_Prices;
    }

    public void setToppings_Prices(HashMap<String,Double> toppings_Prices) { this.toppings_Prices = toppings_Prices;}







}
