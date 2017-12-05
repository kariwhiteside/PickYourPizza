package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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


    }

}
