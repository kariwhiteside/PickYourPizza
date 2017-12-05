package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PizzaPlaceInfo {

    private String businessName;
    private HashMap<String,String> pizzaSizes;
    private HashMap<String,String> pizzaStyles;
    private HashMap<String,String> toppings_Prices;

    public PizzaPlaceInfo(){}

    public PizzaPlaceInfo(String businessName,HashMap<String,String> pizzaSizes,HashMap<String,String> pizzaStyles, HashMap<String,String> toppings_Prices){
        this.businessName = businessName;
        this.pizzaSizes = pizzaSizes;
        this.pizzaStyles = pizzaStyles;
        this.toppings_Prices = toppings_Prices;
    }

    public String getName(){
        return businessName;
    }

    public void setName(String name){ businessName = name;}

    public HashMap<String,String> getSize(){
        return pizzaSizes;
    }

    public void setPizzaSizes(HashMap<String,String> sizes) { pizzaStyles = sizes;}

    public HashMap<String,String> getStyle(){
        return pizzaStyles;
    }

    public void setPizzaStyles(HashMap<String,String> styles) { pizzaStyles = styles;}

    public HashMap<String, String> getToppings_Price(){
        return toppings_Prices;
    }

    public void setToppings_Prices(HashMap<String,String> toppings_Prices) { this.toppings_Prices = toppings_Prices;}







}
