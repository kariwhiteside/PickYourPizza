package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Loading... on 12/4/17.
 */
public class BuildPizzaListTask extends AsyncTask<Void, Void, Void>
{
    private ArrayList<PizzaPlaceInfo> business;

    private String listOfSizes;
    private List<String> pizzaSizes;
    private String sauce;
    private String style;
    private List<String> veggieToppings;
    private List<String> meatToppings;
    private List<String> cheeseToppings;

    private List<String> ingredientsSelected;
    private List<List<String>> options_Available;
    private List<List<String>> options_Not_Available;
    private List<Double> options_Available_Price;

    public BuildPizzaListTask(ArrayList<PizzaPlaceInfo> business,String style, String sauce, ArrayList<String> veggieToppings, ArrayList<String> meatToppings, ArrayList<String> cheeseToppings){
        this.business = business;
        this.style = style;
        this.sauce = sauce;
        this.veggieToppings = veggieToppings;
        this.meatToppings = meatToppings;
        this.cheeseToppings = cheeseToppings;
    }

    //public static void setSelectedIngredients()
    @Override
    protected Void doInBackground(Void...params) {

        //this while loop waits until the database loads all four pizza business into the arraylist business
        while(business.size() != 4){
            //do nothing
        }

        //this loop waits until pizza configuration is completed by user
        //this occurs when YES button is selected in Dialog Fragment
        while(!BuildPizzaDialog.pizzaConfigured){}
        return null;
    }

    protected void onProgressUpdate(Void... progress) {

    }

    @Override
    protected void onPostExecute(Void v) {
        //business information is completely loaded and ready to use

        //populate ArrayList with Strings containing determined pizza sizes (i.e. 1 Medium, 2 Large)
        pizzaSizes = new ArrayList<>();
        determinePizzaSizes();
        Log.i("BuildPizzaListTask",pizzaSizes+ "THIS IS THE DETERMINED AMOUNT OF PIZZAS");

        //merge separate topping lists into one ingredients list
        mergeLists();

        //create 2-D arraylist to hold ingredients not available for each pizza business
        options_Available = new ArrayList<List<String>>();
        //create ArrayList to hold price of each pizza with available toppings
        options_Available_Price = new ArrayList<Double>();
        //create 2-D ArrayList to hold price of each pizza with non-available toppings
        options_Not_Available = new ArrayList<List<String>>();

        for(int i = 0; i < business.size();i++){
            options_Available.add(i, new ArrayList<String>());
            options_Not_Available.add(i, new ArrayList<String>());
        }

        //determine the price of each pizza order from each business
        checkOptionAvailabilityPrice();

        Log.i("BPLTask/OnPostExecute","Ingredients Selected " +ingredientsSelected.toString());
        Log.i("BPLTask/OnPostExecute","Options Available "+options_Available.toString());
        Log.i("BPLTask/OnPostExecute","Options Not Available " +options_Not_Available.toString());
        Log.i("BPLTask/OnPostExecute","Price for the Pizza" + options_Available_Price);

        //Pass business information for business name before ListViewFragment is created
        ListViewFragment.setBusinessList(business);

        listOfSizes = pizzaSizes.get(0);
        //this code will merge all pizza sizes into a string (e.g. for a 1 Medium and 1 X-Large will turn into Medium, X-Large
        for(int i = 1; i < pizzaSizes.size(); i++){
            listOfSizes =  listOfSizes + ", " + pizzaSizes.get(i);
        }
        //Pass determined pizza sizes to ListViewFragment before ListViewFragment is created
        ListViewFragment.setListOfSizes(listOfSizes);

        Log.i("BPLTASK/OnPostExecute", " THIS IS THE PIZZA LIST " + listOfSizes);

        //Pass determined prices to the ListViewFragment before ListViewFragment
        ListViewFragment.setPriceList(options_Available_Price);

        //By this time ListViewFragment should have all the information to create the List View when the Dialog fragment is clicked YES

    }
    //Determine the price of each pizza order from each business based off their availability of size/style/ingredients
    //Also store in an arraylist of all ingredients not available for a pizza order for that business
    private void checkOptionAvailabilityPrice() {
        //for all businesses check to see if user input contains pizza requirements

        for (int i = 0; i < business.size(); i++) {
            double pizzaPrice = 0;

            //calculate and return pizza price determined by pizza size
            pizzaPrice = getPizzaSizePrice(i,pizzaPrice);

            //calculate and return pizza price determined by pizza style
            pizzaPrice = getPizzaStylePrice(i,pizzaPrice);

            //calculate and return pizza price determined by pizza toppings
            pizzaPrice = getPizzaToppingPrice(i,pizzaPrice);

            //add pizza price to order for that business
            options_Available_Price.add(i,pizzaPrice);
        }
    }

    //iterate through the determined pizza sizes and determine & add their price to the total
    private double getPizzaSizePrice(int index, double currentTotal) {
        for(String pizzaSize: pizzaSizes){
            //if pizzaSize is available for current business then add price to currentTotal and keep track of available options
            if(business.get(index).getSize().containsKey(pizzaSize)){
                Log.i("ATask/getPizzaSizePrice","Price for a " + pizzaSize+ " "+business.get(index).getSize().get(pizzaSize).toString());
                currentTotal = currentTotal +   business.get(index).getSize().get(pizzaSize);
                options_Available.get(index).add(pizzaSize);
            }
            else{
                //default case set order to X medium pizza sizes, where X is pizzaSizes.size()
                //keep track of non available toppings for future reference
                currentTotal = currentTotal +  business.get(index).getSize().get("Large");
                options_Not_Available.get(index).add(pizzaSize);
                options_Available.get(index).add("Large");
            }
        }

        return currentTotal;
    }

    //check to see if pizza style is available and add its price to the total
    private double getPizzaStylePrice(int index, double currentTotal){
        //if business has the available style then add its price to the total and keep track of the available style
        if(business.get(index).getStyle().containsKey(style)){
            Log.i("Task/getPizzaStylePrice","THIS IS THE STYLE: "+style);
            Log.i("Task/getPizzaStylePrice","Price for a " + style + " "+business.get(index).getStyle().get(style));
            currentTotal = currentTotal +  business.get(index).getStyle().get(style);
            options_Available.get(index).add(style);
        }
        //get default White style pizza if style is not available
        //keep track of non available toppings for future reference
        else{
            currentTotal = currentTotal +  business.get(index).getStyle().get("White");
            options_Not_Available.get(index).add(style);
        }
        return currentTotal;
    }

    //check to see what pizza toppings are available and add its price to the total while keeping track of unavailable toppings
    private double getPizzaToppingPrice(int index, double currentTotal){
        ArrayList<Double> multiplier;
        //if no extra ingredients are selected return total (Makes CHEESE PIZZA)
        if(ingredientsSelected.size() == 0)
        {
            return currentTotal;
        }
        //go through each ingredient and see if business has the topping
        else {
            //if no ingredients are selected then just return currentTotal
            if (ingredientsSelected.size() == 0)
                return currentTotal;
            else {
                //get the multiplier for each pizza size
                multiplier = toppingPriceMultiplierBySize(index);
                for (String ingredient : ingredientsSelected) {
                    //if business has the topping apply the appropriate price to the total and keep track of ingredients added
                    if (business.get(index).getToppings_Price().containsKey(ingredient)) {
                        //determine the size of multiplier. This value is determined by the number of pizzas determined appropraite for order. For each of the pizza sizes determine the price of topping and add it to the currentTotal
                        for(int i = 0; i < multiplier.size(); i++){
                            currentTotal = currentTotal + multiplier.get(i)*business.get(index).getToppings_Price().get(ingredient);
                        }
                        options_Available.get(index).add(ingredient);
                    }
                    //keep track of non available toppings for future reference
                    else {
                        options_Not_Available.get(index).add(ingredient);
                    }
                }
            }
        }
        return currentTotal;
    }

    private void mergeLists(){
        ingredientsSelected = new ArrayList<>();
        ingredientsSelected.add(sauce);
        ingredientsSelected.addAll(veggieToppings);
        ingredientsSelected.addAll(meatToppings);
        ingredientsSelected.addAll(cheeseToppings);
    }

    private void determinePizzaSizes(){
        //check to see if algorithm determined correct number of pizzas
        if((MainActivity.numSmall == 0)  && (MainActivity.numMedium == 0) && (MainActivity.numLarge == 0) && (MainActivity.numXLarge == 0)){
            Log.i("PIZZA ALGORITHM","FAILED TO DETERMINE NUMBER OF PIZZAS");
            Log.i("PIZZA ALGORITHM","FAILED TO DETERMINE NUMBER OF PIZZAS");
            Log.i("PIZZA ALGORITHM","FAILED TO DETERMINE NUMBER OF PIZZAS");
        }
        //populate List for future getPizzaSizePrice() call
        else{
            Log.i("determinePizzaSizes","Small: "+ Integer.toString(MainActivity.numSmall) +" Medium: " + Integer.toString(MainActivity.numMedium) + " Large: " + Integer.toString(MainActivity.numLarge) + " X-Large "+ Integer.toString(MainActivity.numXLarge));
            for(int i = 0; i < MainActivity.numSmall; i++){
                pizzaSizes.add("Small");
            }

            for(int j = 0; j < MainActivity.numMedium; j++){
                pizzaSizes.add("Medium");
            }

            for(int k = 0; k < MainActivity.numLarge; k++){
                pizzaSizes.add("Large");
            }

            for(int m = 0; m < MainActivity.numXLarge; m++){
                pizzaSizes.add("X-Large");
            }
        }
    }

    //each pizza place has a different price associated with a pizza size for each topping
    //find the topping price multiplier
    private ArrayList<Double> toppingPriceMultiplierBySize(int index){
        ArrayList<Double> toppingPriceMultiplier = new ArrayList<>();
        for(String pizzaSize: pizzaSizes){
            switch (business.get(index).getName()){
                case "WoodStock's Pizza":
                    switch (pizzaSize){
                        case "Small":
                            toppingPriceMultiplier.add(0.80);
                            break;
                        case "Medium":
                            toppingPriceMultiplier.add(1.60);
                            break;
                        case "Large":
                            toppingPriceMultiplier.add(2.001);
                            break;
                        case "X-Large":
                            toppingPriceMultiplier.add(2.25);
                            break;
                        default: toppingPriceMultiplier.add(1.001);
                    }
                case "Pizza My Heart":
                    switch (pizzaSize){
                        case "Small":
                            toppingPriceMultiplier.add(1.001);
                            break;
                        case "Medium":
                            toppingPriceMultiplier.add(2.001);
                            break;
                        case "Large":
                            toppingPriceMultiplier.add(3.001);
                            break;
                        //Pizza My Heart has no XLarge pizza. XLarge will default to Large so when calculating topping price by size this will account for this without having to modify pizzaSizes data structure
                        case "X-Large":
                            toppingPriceMultiplier.add(3.001);
                            break;
                        default: toppingPriceMultiplier.add(1.001);
                    }
                case "Rusty's Pizza Parlor":
                    switch (pizzaSize){
                        case "Small":
                            toppingPriceMultiplier.add(1.501);
                            break;
                        case "Medium":
                            toppingPriceMultiplier.add(1.901);
                            break;
                        case "Large":
                            toppingPriceMultiplier.add(2.101);
                            break;
                        //Rusty's Pizza Parlor has no XLarge pizza. XLarge will default to Large so when calculating topping price by size this will account for this without having to modify pizzaSizes data structure
                        case "X-Large":
                            toppingPriceMultiplier.add(2.001);
                            break;
                        default: toppingPriceMultiplier.add(1.001);
                    }
                case "Papa John's":
                    switch (pizzaSize){
                        case "Small":
                            toppingPriceMultiplier.add(1.001);
                            break;
                        case "Medium":
                            toppingPriceMultiplier.add(1.001);
                            break;
                        case "Large":
                            toppingPriceMultiplier.add(1.501);
                            break;
                        case "X-Large":
                            toppingPriceMultiplier.add(1.75);
                            break;
                        default: toppingPriceMultiplier.add(1.001);
                    }
            }
        }
        return toppingPriceMultiplier;
    }
}