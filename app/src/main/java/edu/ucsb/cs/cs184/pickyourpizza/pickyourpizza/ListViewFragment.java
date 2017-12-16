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

        System.out.println("Selected Sauce is: " + MainActivity.selectedSauce + "  Call at ListViewFragment Create");
        System.out.println("Selected Style is: " + MainActivity.selectedStyle+ "  Call at ListViewFragment Create");
        System.out.println("Selected Veggies is: " +MainActivity.selectedVeggies+ "  Call at ListViewFragment Create");
        System.out.println("Selected Meats is: " + MainActivity.selectedMeats+ "  Call at ListViewFragment Create");
        System.out.println("Selected Cheeses is"  + MainActivity.selectedCheeses+ "  Call at ListViewFragment Create");
        System.out.println("Determined Pizzas are: "+ MainActivity.numXLarge +" X-Large  " + MainActivity.numLarge + " Large " + MainActivity.numMedium + " Medium  " + MainActivity.numSmall +" Small");

        final Context context;
        context = this.getContext();
        //String chain, String pizzaTypes, String price, int photoID
        pizzaPlaces = new ArrayList<PizzaPlace>();
        //iterate through all businesses in the list and create a PizzaPlace object
        for(int i = 0; i < businessList.size(); i ++){
            pizzaPlaces.add(new PizzaPlace(businessList.get(i).getName(),listOfSizes,"$"+new DecimalFormat("###.##").format(priceList.get(i)), getResources().getIdentifier(determineDrawableName(businessList.get(i).getName()),"drawable",context.getPackageName())));
        }
        /*
        pizzaPlaces.add(new PizzaPlace("WoodStock's Pizza", "2 Small", "13.44 + tax", R.drawable.woodstocks_logo));
        pizzaPlaces.add(new PizzaPlace("Pizza My Heart", "1 Medium, 1 Large", "16.99 + tax", R.drawable.pizza_my_heart_logo));
        pizzaPlaces.add(new PizzaPlace("Rusty's Pizza Parlor", "2 Small", "13.44 + tax", R.drawable.rustys_pizza_parlor_logo));
        pizzaPlaces.add(new PizzaPlace("Papa John's", "1 Medium, 1 Large", "16.99 + tax", R.drawable.papa_johns_logo));
*/
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