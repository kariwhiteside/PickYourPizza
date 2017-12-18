package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza.FirebaseCreate.businessList;

/**
 * Created by dsefl on 11/30/2017.
 */

public class ListViewFragment extends Fragment {

    View view;

    FragmentHelper activityCallback;
    public static ArrayList<PizzaPlaceInfo> businessList;
    public static String listOfSizes;
    public static List<Double> priceList;



    public interface FragmentHelper {
        public void changeFragment(String newFragment, boolean forward);
        public void clearSelections();
        public void setBusinessName(String businessName);
    }

    private ArrayList<PizzaPlace> pizzaPlaces;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCallback = (FragmentHelper)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FragmentHelper");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview_fragment, container, false);


        Log.i("ListViewFr/onCreateView","Selected Sauce is: " + MainActivity.selectedSauce);
        Log.i("ListViewFr/onCreateView","Selected Style is: " + MainActivity.selectedStyle);
        Log.i("ListViewFr/onCreateView","Selected Veggies is: " +MainActivity.selectedVeggies);
        Log.i("ListViewFr/onCreateView","Selected Meats is: " + MainActivity.selectedMeats);
        Log.i("ListViewFr/onCreateView","Selected Cheeses is"  + MainActivity.selectedCheeses);
        Log.i("ListViewFr/onCreateView","Determined Pizzas are: "+ MainActivity.numXLarge +" X-Large  " + MainActivity.numLarge + " Large " + MainActivity.numMedium + " Medium  " + MainActivity.numSmall +" Small");


        final Context context;
        context = this.getContext();
        //String chain, String pizzaTypes, String price, int photoID
        pizzaPlaces = new ArrayList<PizzaPlace>();
        String XLargeToLarge = listOfSizes.replaceAll("X-Large","Large");
        //iterate through all businesses in the list and create a PizzaPlace object
        for(int i = 0; i < businessList.size(); i ++){
            //if the listOfSizes contains one or more X-Large make adjustment to reflect available sizes
            if(listOfSizes.contains("X-Large")){
                //if the business has XLarge sized pizzas don't make string adjustment
                if(businessList.get(i).getSize().get("X-Large") != null){
                    pizzaPlaces.add(new PizzaPlace(businessList.get(i).getName(),listOfSizes,"$"+new DecimalFormat("###.##").format(priceList.get(i)), getResources().getIdentifier(determineDrawableName(businessList.get(i).getName()),"drawable",context.getPackageName())));

                }
                //the business has no X-Large sized pizzas so display Large pizzas instead
                else{
                    pizzaPlaces.add(new PizzaPlace(businessList.get(i).getName(),XLargeToLarge,"$"+new DecimalFormat("###.##").format(priceList.get(i)), getResources().getIdentifier(determineDrawableName(businessList.get(i).getName()),"drawable",context.getPackageName())));
                }
            }
            //No X-Large pizza sizes selected so just display the sizes
            else{
                pizzaPlaces.add(new PizzaPlace(businessList.get(i).getName(),listOfSizes,"$"+new DecimalFormat("###.##").format(priceList.get(i)), getResources().getIdentifier(determineDrawableName(businessList.get(i).getName()),"drawable",context.getPackageName())));
            }

        }
        ListView listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(new PizzaPlaceAdapter(this.getContext(), pizzaPlaces));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // open Details Fragment
                activityCallback.changeFragment("DetailPageFragment", true);
                //Toast.makeText(context, pizzaPlaces.get(i).getChain(), Toast.LENGTH_SHORT ).show();
                activityCallback.setBusinessName(pizzaPlaces.get(i).getChain());
            }
        });

        Button modifyButton = (Button)view.findViewById(R.id.ModifyPizza);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCallback.changeFragment("NumPeopleFragment", false);
            }
        });

        Button createNewPizzaButton = (Button)view.findViewById(R.id.CreateAnotherPizza);
        createNewPizzaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCallback.clearSelections();
                activityCallback.changeFragment("NumPeopleFragment", false);
            }
        });

        return view;
    }

    //used to pass business ArrayList from AsynTask to ListViewFragment so ListView can be created
    //Called in BuildPizzaListTask
    public static void setBusinessList(ArrayList<PizzaPlaceInfo> business){
        businessList = business;
    }

    //used to pass List of Sizes (One String) from AsynTask to ListViewFragment so ListView can be created
    //Called in BuildPizzaListTask
    public static void setListOfSizes(String sizeList){
        listOfSizes = sizeList;
    }

    public String determineDrawableName(String businessName){
        switch (businessName){
            case "WoodStock's Pizza":
                return "woodstocks_logo";
            case "Rusty's Pizza Parlor":
                return "rustys_pizza_parlor_logo";
            case "Pizza My Heart":
                return "pizza_my_heart_logo";
            case "Papa John's":
                return "papa_johns_logo";
            default:
                Log.i("ERROR", "LOGGED A NULL AT LISTVIEW FRAGMENT/determineDrawableName");
                return null;
        }
    }

    //Used to get calculated price list from AsynTask to ListViewFragment for ListView
    //Called in BuildPizzaListTask
    public static void setPriceList(List<Double> prices){
        priceList = prices;
    }
}