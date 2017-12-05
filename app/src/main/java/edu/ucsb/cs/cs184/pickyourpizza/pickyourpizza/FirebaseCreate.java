package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Loading... on 12/1/17.
 */

public class FirebaseCreate {
    public static DatabaseReference dBRef;
    public static ArrayList<PizzaPlaceInfo> businessList;

    public FirebaseCreate() {
    }

    public static void Initialize() {
        /*//only has to be called when constructing database for first time
        //populate FirebaseDatabase with Pizza place product info
        dBRef = FirebaseDatabase.getInstance().getReference("PizzaBusiness/");

        HashMap<String,Double> pizzaMyHeart_Size = new HashMap<>();
        pizzaMyHeart_Size.put("Heart",24.0);
        pizzaMyHeart_Size.put("Small",13.0);
        pizzaMyHeart_Size.put("Medium",16.0);
        pizzaMyHeart_Size.put("Large",20.0);
        pizzaMyHeart_Size.put("Rice-Flour Crust",13.0);
        HashMap<String,Double> pizzaMyHeart_Style = new HashMap<>();
        pizzaMyHeart_Style.put("Original",0.0);
        HashMap<String,Double> pizzaMyHeart_Toppings_Price = new HashMap<>();
        pizzaMyHeart_Toppings_Price.put("Marinara",0.0);
        pizzaMyHeart_Toppings_Price.put("BBQ Sauce",1.0);
        pizzaMyHeart_Toppings_Price.put("Pesto",1.0);
        pizzaMyHeart_Toppings_Price.put("Canadian Bacon",1.0);
        pizzaMyHeart_Toppings_Price.put("Pepperoni",1.0);
        pizzaMyHeart_Toppings_Price.put("Salami",1.0);
        pizzaMyHeart_Toppings_Price.put("Chicken",2.0);
        pizzaMyHeart_Toppings_Price.put("Sausage",1.0);
        pizzaMyHeart_Toppings_Price.put("Anchovy",1.0);
        pizzaMyHeart_Toppings_Price.put("Clams and Garlic",2.0);
        pizzaMyHeart_Toppings_Price.put("Bacon",1.0);
        pizzaMyHeart_Toppings_Price.put("BBQ Chicken",2.0);
        pizzaMyHeart_Toppings_Price.put("Pastrami",2.0);
        pizzaMyHeart_Toppings_Price.put("Extra Cheese",1.0);
        pizzaMyHeart_Toppings_Price.put("Feta",1.0);
        pizzaMyHeart_Toppings_Price.put("Ricotta",1.0);
        pizzaMyHeart_Toppings_Price.put("Gargonzola",1.0);
        pizzaMyHeart_Toppings_Price.put("Parmesan",1.0);
        pizzaMyHeart_Toppings_Price.put("Fresh Sage",1.0);
        pizzaMyHeart_Toppings_Price.put("Spinach",1.0);
        pizzaMyHeart_Toppings_Price.put("Black Olive",1.0);
        pizzaMyHeart_Toppings_Price.put("Mushroom",1.0);
        pizzaMyHeart_Toppings_Price.put("Artichoke",1.0);
        pizzaMyHeart_Toppings_Price.put("Tomato",1.0);
        pizzaMyHeart_Toppings_Price.put("Pineapple",1.0);
        pizzaMyHeart_Toppings_Price.put("Jalapeno",1.0);
        pizzaMyHeart_Toppings_Price.put("Sun Dried Tomato",1.0);
        pizzaMyHeart_Toppings_Price.put("Red Onion",1.0);
        pizzaMyHeart_Toppings_Price.put("Broccoli",1.0);
        pizzaMyHeart_Toppings_Price.put("Chopped Garlic",1.0);
        pizzaMyHeart_Toppings_Price.put("Herb Seasoning",1.0);
        pizzaMyHeart_Toppings_Price.put("Fresh Cilantro",1.0);
        pizzaMyHeart_Toppings_Price.put("Green Bell Pepper",1.0);
        pizzaMyHeart_Toppings_Price.put("Fresh Basil",1.0);
        pizzaMyHeart_Toppings_Price.put("Serrano Chili",1.0);
        pizzaMyHeart_Toppings_Price.put("Portabello Mushroom",2.0);
        pizzaMyHeart_Toppings_Price.put("Roasted Garlic CLove",1.0);
        pizzaMyHeart_Toppings_Price.put("Apple",1.0);
        pizzaMyHeart_Toppings_Price.put("Green Onion",1.0);
        pizzaMyHeart_Toppings_Price.put("Mustard",1.0);
        pizzaMyHeart_Toppings_Price.put("Peperonicini",1.0);
        pizzaMyHeart_Toppings_Price.put("Sweety Drop Peppers",1.0);
        pizzaMyHeart_Toppings_Price.put("Sliced Figs",1.0);
        pizzaMyHeart_Toppings_Price.put("Cherry Tomato",1.0);
        pizzaMyHeart_Toppings_Price.put("Roasted Red Bell Pepper",1.0);

        DatabaseReference pizzaMyHeart = dBRef.child("Pizza My Heart");
        pizzaMyHeart.setValue("Pizza My Heart");
        pizzaMyHeart.child("Name").setValue("Pizza My Heart");
        pizzaMyHeart.child("Size").setValue(pizzaMyHeart_Size);
        pizzaMyHeart.child("Style").setValue(pizzaMyHeart_Style);
        pizzaMyHeart.child("Toppings_Price").setValue(pizzaMyHeart_Toppings_Price);


        HashMap<String,Double> papaJohns_Size = new HashMap<>();
        papaJohns_Size.put("Small",8.0);
        papaJohns_Size.put("Medium",10.0);
        papaJohns_Size.put("Large",12.0);
        papaJohns_Size.put("X-Large",15.0);
        HashMap<String,Double> papaJohns_Style = new HashMap<>();
        papaJohns_Style.put("Pan",0.0);
        papaJohns_Style.put("Gluten-Free",0.0);
        papaJohns_Style.put("Thin",0.0);
        HashMap<String,Double> papaJohns_Toppings_Price = new HashMap<>();
        papaJohns_Toppings_Price.put("Marinara",0.0);
        papaJohns_Toppings_Price.put("BBQ Sauce",0.0);
        papaJohns_Toppings_Price.put("Green Peppers",1.0);
        papaJohns_Toppings_Price.put("Mushrooms",1.0);
        papaJohns_Toppings_Price.put("Onions",1.0);
        papaJohns_Toppings_Price.put("Tomatos",1.0);
        papaJohns_Toppings_Price.put("Banana Peppers",1.0);
        papaJohns_Toppings_Price.put("Pineapple",1.0);
        papaJohns_Toppings_Price.put("Ripe Olives",1.0);
        papaJohns_Toppings_Price.put("Green Olives",1.0);
        papaJohns_Toppings_Price.put("Jalepeno Peppers",1.0);
        papaJohns_Toppings_Price.put("Anchovies",1.0);
        papaJohns_Toppings_Price.put("Beef",1.0);
        papaJohns_Toppings_Price.put("Bacon",1.0);
        papaJohns_Toppings_Price.put("Canadian Bacon",1.0);
        papaJohns_Toppings_Price.put("Chicken",1.0);
        papaJohns_Toppings_Price.put("Italian Sausage",1.0);
        papaJohns_Toppings_Price.put("Sausage",1.0);
        papaJohns_Toppings_Price.put("Pepperoni",1.0);
        papaJohns_Toppings_Price.put("Parmesian",1.0);
        papaJohns_Toppings_Price.put("Romano",1.0);
        papaJohns_Toppings_Price.put("Three Cheese Blend",1.0);
        papaJohns_Toppings_Price.put("Anchovies",1.0);

        DatabaseReference papaJohn = dBRef.child("Papa John's");
        papaJohn.setValue("Papa John's");
        papaJohn.child("Name").setValue("Papa John's");
        papaJohn.child("Size").setValue(papaJohns_Size);
        papaJohn.child("Style").setValue(papaJohns_Style);
        papaJohn.child("Toppings_Price").setValue(papaJohns_Toppings_Price);

        HashMap<String,Double> woodStock_Size = new HashMap<>();
        woodStock_Size.put("Personal",6.95);
        woodStock_Size.put("Medium",13.25);
        woodStock_Size.put("Large",16.90);
        woodStock_Size.put("X-Large",18.90);
        HashMap<String,Double> woodStock_Style = new HashMap<>();
        woodStock_Style.put("White",0.0);
        woodStock_Style.put("Wheat",0.0);
        woodStock_Style.put("Gluten-Free",2.55);
        HashMap<String,Double> woodStock_Toppings_Price = new HashMap<>();
        woodStock_Toppings_Price.put("Zesty Red",0.0);
        woodStock_Toppings_Price.put("Creamy Siracha",0.0);
        woodStock_Toppings_Price.put("Souther Chipotle",0.0);
        woodStock_Toppings_Price.put("Pesto",0.0);
        woodStock_Toppings_Price.put("Creamy Garlic",0.0);
        woodStock_Toppings_Price.put("BBQ",0.0);
        woodStock_Toppings_Price.put("Red Onions",1.0);
        woodStock_Toppings_Price.put("Jalepenos",1.0);
        woodStock_Toppings_Price.put("Tomato",1.0);
        woodStock_Toppings_Price.put("Black Olives",1.0);
        woodStock_Toppings_Price.put("Bell Peppers",1.0);
        woodStock_Toppings_Price.put("Green Onions",1.0);
        woodStock_Toppings_Price.put("Mushrooms",1.0);
        woodStock_Toppings_Price.put("Pineapple",1.0);
        woodStock_Toppings_Price.put("Broccoli",1.0);
        woodStock_Toppings_Price.put("Katamala Olives",1.0);
        woodStock_Toppings_Price.put("Peppercini",1.0);
        woodStock_Toppings_Price.put("Organic Spinach",1.0);
        woodStock_Toppings_Price.put("Onions",1.0);
        woodStock_Toppings_Price.put("Zuchini",1.0);
        woodStock_Toppings_Price.put("Cilantro",1.0);
        woodStock_Toppings_Price.put("Roasted Garlic",1.0);
        woodStock_Toppings_Price.put("Sun-dried Tomato",1.0);
        woodStock_Toppings_Price.put("Pepperoni",1.0);
        woodStock_Toppings_Price.put("Canadian Bacon",1.0);
        woodStock_Toppings_Price.put("Feta",1.0);
        woodStock_Toppings_Price.put("Italian Sausage",1.0);
        woodStock_Toppings_Price.put("Parmesian",1.0);
        woodStock_Toppings_Price.put("Salami",1.0);
        woodStock_Toppings_Price.put("Applewood Smoked Bacon",1.0);
        woodStock_Toppings_Price.put("Grilled Chicken",1.0);
        woodStock_Toppings_Price.put("BBQ Chicken",1.0);
        woodStock_Toppings_Price.put("Carnitas",1.0);
        woodStock_Toppings_Price.put("Daiya Vegan Cheese",1.0);

        DatabaseReference woodStock = dBRef.child("WoodStock's Pizza");
        woodStock.setValue("WoodStock's Pizza");
        woodStock.child("Name").setValue("WoodStock's Pizza");
        woodStock.child("Size").setValue(woodStock_Size);
        woodStock.child("Style").setValue(woodStock_Style);
        woodStock.child("Toppings_Price").setValue(woodStock_Toppings_Price);

        HashMap<String,Double> rustyPizzaParlor_Size = new HashMap<>();
        rustyPizzaParlor_Size.put("Small",9.50);
        rustyPizzaParlor_Size.put("Medium",11.75);
        rustyPizzaParlor_Size.put("Large",15.25);
        HashMap<String,Double> rustyPizzaParlor_Style = new HashMap<>();
        rustyPizzaParlor_Style.put("Thin",0.0);
        rustyPizzaParlor_Style.put("Pan",0.0);
        HashMap<String,Double> rustyPizzaParlor_Toppings_Price = new HashMap<>();
        rustyPizzaParlor_Toppings_Price.put("Marinara",0.0);
        rustyPizzaParlor_Toppings_Price.put("Salami",1.0);
        rustyPizzaParlor_Toppings_Price.put("Canadian Bacon",1.0);
        rustyPizzaParlor_Toppings_Price.put("Italian Sausage",1.0);
        rustyPizzaParlor_Toppings_Price.put("Chicken",1.0);
        rustyPizzaParlor_Toppings_Price.put("Meatball",1.0);
        rustyPizzaParlor_Toppings_Price.put("Anchovy",1.0);
        rustyPizzaParlor_Toppings_Price.put("Chorizo",1.0);
        rustyPizzaParlor_Toppings_Price.put("Jalepeno",1.0);
        rustyPizzaParlor_Toppings_Price.put("Black Olive",1.0);
        rustyPizzaParlor_Toppings_Price.put("Pineapple",1.0);
        rustyPizzaParlor_Toppings_Price.put("Fresh Onion",1.0);
        rustyPizzaParlor_Toppings_Price.put("Tomato",1.0);
        rustyPizzaParlor_Toppings_Price.put("Red Onion",1.0);
        rustyPizzaParlor_Toppings_Price.put("Garlic",1.0);
        rustyPizzaParlor_Toppings_Price.put("Bell Pepper",1.0);
        rustyPizzaParlor_Toppings_Price.put("Mushroom",1.0);
        rustyPizzaParlor_Toppings_Price.put("Zucchini",1.0);
        rustyPizzaParlor_Toppings_Price.put("Cilantro",1.0);
        rustyPizzaParlor_Toppings_Price.put("Cashews",1.0);
        rustyPizzaParlor_Toppings_Price.put("Oretega Green Chile",1.0);

        DatabaseReference rustyPizzaParlor = dBRef.child("Rusty's Pizza Parlor");
        rustyPizzaParlor.setValue("Rusty's Pizza Parlor");
        rustyPizzaParlor.child("Name").setValue("Rusty's Pizza Parlor");
        rustyPizzaParlor.child("Size").setValue(rustyPizzaParlor_Size);
        rustyPizzaParlor.child("Style").setValue(rustyPizzaParlor_Style);
        rustyPizzaParlor.child("Toppings_Price").setValue(rustyPizzaParlor_Toppings_Price);

        */

        //get reference to the database that has information regarding the pizza businesses
        dBRef = FirebaseDatabase.getInstance().getReference();

        //add each business to a list
        final ChildEventListener listener = dBRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Test for types
                //System.out.println("Papa John's Name is stored as a : " + dataSnapshot.child("Papa John's/Name").getValue().getClass());
                //System.out.println("Papa John's Style Hashmap is stored as a :"+dataSnapshot.child("Papa John's/Style").getValue().getClass());

                //Ignore
                /* W/ClassMapper: No setter/field for Toppings_Price found on class edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza.PizzaPlaceInfo (fields/setters are case sensitive!)
                PizzaPlaceInfo info = dataSnapshot.child("/Papa John's").getValue(PizzaPlaceInfo.class);
                System.out.println("THIS IS THE NAME OF THE BUSINESS" + info.getName());
                System.out.println("THIS IS THE HASHMAP "+ info.getSize());
                */

                //Get datasnapshot child information and store the information into variables
                String name = (String) dataSnapshot.child("Papa John's/Name").getValue();
                HashMap<String,String> style = (HashMap<String,String>) dataSnapshot.child("Papa John's/Style").getValue();
                HashMap<String,String> size = (HashMap<String,String>) dataSnapshot.child("Papa John's/Size").getValue();
                HashMap<String,String> toppings = (HashMap<String,String>) dataSnapshot.child("Papa John's/Toppings_Price").getValue();

                String name2 = (String) dataSnapshot.child("Pizza My Heart/Name").getValue();
                HashMap<String,String> style2 = (HashMap<String,String>) dataSnapshot.child("Pizza My Heart/Style").getValue();
                HashMap<String,String> size2 = (HashMap<String,String>) dataSnapshot.child("Pizza My Heart/Size").getValue();
                HashMap<String,String> toppings2 = (HashMap<String,String>) dataSnapshot.child("Pizza My Heart/Toppings_Price").getValue();

                String name3 = (String) dataSnapshot.child("Rusty's Pizza Parlor/Name").getValue();
                HashMap<String,String> style3 = (HashMap<String,String>) dataSnapshot.child("Rusty's Pizza Parlor/Style").getValue();
                HashMap<String,String> size3 = (HashMap<String,String>) dataSnapshot.child("Rusty's Pizza Parlor/Size").getValue();
                HashMap<String,String> toppings3 = (HashMap<String,String>) dataSnapshot.child("Rusty's Pizza Parlor/Toppings_Price").getValue();

                String name4 = (String) dataSnapshot.child("WoodStock's Pizza/Name").getValue();
                HashMap<String,String> style4 = (HashMap<String,String>) dataSnapshot.child("WoodStock's Pizza").getValue();
                HashMap<String,String> size4 = (HashMap<String,String>) dataSnapshot.child("WoodStock's Pizza/Size").getValue();
                HashMap<String,String> toppings4 = (HashMap<String,String>) dataSnapshot.child("WoodStock's Pizza/Toppings_Price").getValue();


                //Create objects that simply access to pizzaInfo
                PizzaPlaceInfo pj = new PizzaPlaceInfo(name,size,style,toppings);
                PizzaPlaceInfo pmh = new PizzaPlaceInfo(name2,size2,style2,toppings2);
                PizzaPlaceInfo rpp = new PizzaPlaceInfo(name3,size3,style3,toppings3);
                PizzaPlaceInfo wsp = new PizzaPlaceInfo(name4,size4,style4,toppings4);

                businessList.add(pj);
                businessList.add(pmh);
                businessList.add(rpp);
                businessList.add(wsp);


            }

            //implement for future features
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            //implement for future features
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //passes in business arraylist that will be updated with business information at Initialize() call
    public static void setBusinessList(ArrayList<PizzaPlaceInfo> business) {
        businessList = business;
    }

}

