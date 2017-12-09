package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                .replace(R.id.fragmentContainer, startPageFragment)
                .commit();
    }

    @Override
    public void changeFragment(String fragment) {
        switch (fragment) {
            case "NumPeopleFragment":
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, numPeopleFragment)
                        .commit();
                break;
            case "StyleSelectionFragment":
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, styleSelectionFragment)
                        .commit();
                break;
            case "SauceSelectionFragment":
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, sauceSelectionFragment)
                        .commit();
                break;
            case "CheeseSelectionFragment":
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, cheeseSelectionFragment)
                        .commit();
                break;
            case "VeggieSelectionFragment":
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, veggieSelectionFragment)
                        .commit();
                break;
            case "MeatSelectionFragment":
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, meatSelectionFragment)
                        .commit();
                break;
        }
    }


}
