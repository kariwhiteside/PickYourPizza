package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by dsefl on 11/30/2017.
 */

public class ListViewFragment extends Fragment {

    View view;

    private ArrayList<PizzaPlace> pizzaPlaces;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview_fragment, container, false);


        final Context context;
        context = this.getContext();
        //String chain, String pizzaTypes, String price, int photoID
        pizzaPlaces = new ArrayList<PizzaPlace>();
        pizzaPlaces.add(new PizzaPlace("Pizza Hut", "1 Medium, 1 Large", "16.99 + tax", R.drawable.pizza_hut_logo));
        pizzaPlaces.add(new PizzaPlace("Woodstocks", "2 Small", "13.44 + tax", R.drawable.woodstocks_logo));
        pizzaPlaces.add(new PizzaPlace("Pizza My Heart", "1 Medium, 1 Large", "16.99 + tax", R.drawable.pizza_my_heart_logo));
        pizzaPlaces.add(new PizzaPlace("Rusty's Pizza Parlor", "2 Small", "13.44 + tax", R.drawable.rustys_pizza_parlor_logo));
        pizzaPlaces.add(new PizzaPlace("Papa John's", "1 Medium, 1 Large", "16.99 + tax", R.drawable.papa_johns_logo));

        ListView listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(new PizzaPlaceAdapter(this.getContext(), pizzaPlaces));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //open Details Fragment
                Toast.makeText(context, pizzaPlaces.get(i).getChain(), Toast.LENGTH_SHORT ).show();
            }
        });

        return view;
    }
}
