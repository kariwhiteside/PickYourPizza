package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Loading... on 12/4/17.
 */
public class BuildPizzaListTask extends AsyncTask<Void, Void, Void>
{
    private ArrayList<PizzaPlaceInfo> business;

    public BuildPizzaListTask(ArrayList<PizzaPlaceInfo> business){
        this.business = business;
    }

    @Override
    protected Void doInBackground(Void...params) {

        //this while loop waits until the database loads all four pizza business into the arraylist business
        while(business.size() != 4){
            //do nothing
        }
        return null;
    }

    protected void onProgressUpdate(Void... progress) {

    }

    @Override
    protected void onPostExecute(Void v) {
        //business information is completely loaded and ready to use

    }
}