package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by joesong on 12/4/17.
 */

public class VeggieImageAdapter extends BaseAdapter {
    private Context mContext;

    private ArrayList<Integer> selectedPositions = new ArrayList<>();
    private ArrayList<String> selectedVeggies = new ArrayList<>();

    // references to our images
    private ArrayList<Integer> veggieImages = new ArrayList<>();
    private ArrayList<String> veggieNames = new ArrayList<>();

    public VeggieImageAdapter(Context c) {
        mContext = c;
        veggieImages.addAll(Arrays.asList(R.drawable.apples, R.drawable.artichokehearts, R.drawable.bananapeppers,
                R.drawable.basil, R.drawable.bellpeppers, R.drawable.blackolives,
                R.drawable.broccoli, R.drawable.cashews, R.drawable.cherrytomatoes,
                R.drawable.choppedgarlic, R.drawable.cilantro, R.drawable.greenchile,
                R.drawable.greenolives, R.drawable.greenonions, R.drawable.greenpeppers,
                R.drawable.jalapenos, R.drawable.kalamataolives, R.drawable.mushrooms,
                R.drawable.mustard, R.drawable.onions, R.drawable.pepperocini,
                R.drawable.pineapple, R.drawable.portobellomushrooms, R.drawable.redonions,
                R.drawable.roastedbellpepper, R.drawable.roastedgarlic, R.drawable.sage,
                R.drawable.serranochili, R.drawable.slicedfigs, R.drawable.spinach,
                R.drawable.sundriedtomatoes, R.drawable.sweetydroppeppers, R.drawable.tatertots,
                R.drawable.tomatoes, R.drawable.zucchini));
        veggieNames.addAll(Arrays.asList("Apples", "Artichoke Hearts", "Banana Peppers",
                "Basil", "Bell Peppers", "Black Olives",
                "Broccoli", "Cashews", "Cherry Tomatoes",
                "Chopped Garlic", "Cilantro", "Green Chile",
                "Green Olives", "Green Onions", "Green Peppers",
                "Jalapenos", "Kalamata Olives", "Mushrooms",
                "Mustard", "Onions", "Pepperoncini", "Pineapple",
                "Portobello Mushrooms", "Red Onions", "Roasted Bell Pepper",
                "Roasted Garlic", "Sage", "Serrano Chili", "Sliced Figs",
                "Spinach", "Sun Dried Tomatoes", "Sweety Drop Peppers",
                "Tater Tots", "Tomatoes", "Zucchini"));
    }

    public int getCount() {
        return veggieImages.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView;

        LayoutInflater li = ((Activity) mContext).getLayoutInflater();
        myView = li.inflate(R.layout.grid_item, null);
        ImageView imageView = myView.findViewById(R.id.grid_img);
        TextView txt = myView.findViewById(R.id.grid_txt);

        imageView.setImageResource(veggieImages.get(position));
        txt.setText(veggieNames.get(position));

        if(selectedPositions.contains(position)) {
            imageView.setBackgroundColor(Color.BLUE);
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT);
        }
        return myView;
    }

    public ArrayList<Integer> getSelectedPositions() {
        return selectedPositions;
    }

    public void setSelectedPositions(int position){

        if(selectedPositions.contains(position)){
            selectedPositions.remove(Integer.valueOf(position));
            selectedVeggies.remove(veggieNames.get(position));
        }
        else {
            selectedPositions.add(position);
            selectedVeggies.add(veggieNames.get(position));
        }
    }

    public void setSelectedPositions(ArrayList<Integer> positions) {
        selectedPositions = positions;
    }

    public ArrayList<String> getSelectedVeggies() {
        return selectedVeggies;
    }

    public void setSelectedVeggies(ArrayList<String> names){
        selectedVeggies = names;
    }
}
