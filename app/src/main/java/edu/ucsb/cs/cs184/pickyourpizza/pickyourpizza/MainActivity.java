package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CheeseSelectionFragment.FragmentHelper,
        NumPeopleFragment.FragmentHelper, StartPageFragment.FragmentHelper, MeatSelectionFragment.FragmentHelper,
        SauceSelectionFragment.FragmentHelper, StyleSelectionFragment.FragmentHelper, VeggieSelectionFragment.FragmentHelper {

    FragmentManager fragmentManager = getSupportFragmentManager();
    StartPageFragment startPageFragment = new StartPageFragment();
    NumPeopleFragment numPeopleFragment = new NumPeopleFragment();
    StyleSelectionFragment styleSelectionFragment = new StyleSelectionFragment();
    SauceSelectionFragment sauceSelectionFragment = new SauceSelectionFragment();
    CheeseSelectionFragment cheeseSelectionFragment = new CheeseSelectionFragment();
    VeggieSelectionFragment veggieSelectionFragment = new VeggieSelectionFragment();
    MeatSelectionFragment meatSelectionFragment = new MeatSelectionFragment();
    ListViewFragment listViewFragment = new ListViewFragment();

    // SAVED DATA FROM FRAGMENTS

    // Style
    int selectedStylePosition;
    String selectedStyle;

    // Sauce
    int selectedSaucePosition;
    String selectedSauce;

    // Veggies
    ArrayList<Integer> selectedVeggiesPositions = new ArrayList<>();
    ArrayList<String> selectedVeggies = new ArrayList<>();

    // Meats
    ArrayList<Integer> selectedMeatsPositions = new ArrayList<>();
    ArrayList<String> selectedMeats = new ArrayList<>();

    // Cheeses
    ArrayList<Integer> selectedCheesesPositions = new ArrayList<>();
    ArrayList<String> selectedCheeses = new ArrayList<>();


    public DatabaseReference dBRef;
    public ArrayList<PizzaPlaceInfo> businessList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create list of business to select pizza from
        businessList = new ArrayList<>();

        //pass reference to business arraylist to add businesses from database
        FirebaseCreate.setBusinessList(businessList);
        //initialize/retrieve reference database to update businessList with information
        FirebaseCreate.Initialize();

        //Initialize task that waits for the all business information to be loaded into the Arraylist business
        new BuildPizzaListTask(businessList).execute();

        // Set StartPageFragment to be the first fragment to show
        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, startPageFragment)
                .commit();
    }

    @Override
    public void changeFragment(String newFragment) {
        switch (newFragment) {
            case "NumPeopleFragment":
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, numPeopleFragment)
                        .commit();
                break;
            case "StyleSelectionFragment":
                styleSelectionFragment.setSelectedPositionAndStyle(selectedStylePosition, selectedStyle);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, styleSelectionFragment)
                        .commit();
                break;
            case "SauceSelectionFragment":
                sauceSelectionFragment.setSelectedPositionAndSauce(selectedSaucePosition, selectedSauce);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, sauceSelectionFragment)
                        .commit();
                break;
            case "CheeseSelectionFragment":
                cheeseSelectionFragment.setSelectedPositionsAndCheeses(selectedCheesesPositions, selectedCheeses);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, cheeseSelectionFragment)
                        .commit();
                break;
            case "VeggieSelectionFragment":
                veggieSelectionFragment.setSelectedPositionsAndVeggies(selectedVeggiesPositions, selectedVeggies);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, veggieSelectionFragment)
                        .commit();
                break;
            case "MeatSelectionFragment":
                meatSelectionFragment.setSelectedPositionsAndMeats(selectedMeatsPositions, selectedMeats);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, meatSelectionFragment)
                        .commit();
                break;
            case "ListViewFragment":
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, listViewFragment)
                        .commit();
                break;

        }
    }

    @Override
    public void setSelectedPositionAndStyle(int position, String style) {
        selectedStylePosition = position;
        selectedStyle = style;
    }

    @Override
    public void setSelectedPositionAndSauce(int position, String sauce) {
        selectedSaucePosition = position;
        selectedSauce = sauce;
    }

    @Override
    public void setSelectedPositionsAndVeggies(ArrayList<Integer> positions, ArrayList<String> veggies) {
        selectedVeggiesPositions = positions;
        selectedVeggies = veggies;
    }

    @Override
    public void setSelectedPositionsAndMeats(ArrayList<Integer> positions, ArrayList<String> meats) {
        selectedMeatsPositions = positions;
        selectedMeats = meats;
    }

    @Override
    public void setSelectedPositionsAndCheeses(ArrayList<Integer> positions, ArrayList<String> cheeses) {
        selectedCheesesPositions = positions;
        selectedCheeses = cheeses;
    }
}
