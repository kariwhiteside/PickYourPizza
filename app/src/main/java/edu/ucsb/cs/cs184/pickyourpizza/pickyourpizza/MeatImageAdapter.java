package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

/**
 * Created by joesong on 12/3/17.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;

public class MeatImageAdapter extends BaseAdapter {
    private Context mContext;

    private ArrayList<Integer> selectedPositions = new ArrayList<>();
    private ArrayList<String> selectedMeats = new ArrayList<>();

    // references to our images
    ArrayList<Integer> meatImages = new ArrayList<>();
    ArrayList<String> meatNames = new ArrayList<>();

    public MeatImageAdapter(Context c) {
        mContext = c;
        meatImages.addAll(Arrays.asList(R.drawable.pepperoni, R.drawable.sausage,
                R.drawable.italiansausage, R.drawable.anchovies, R.drawable.bacon,
                R.drawable.barbequechicken, R.drawable.beef,
                R.drawable.canadianbacon, R.drawable.chorizo,
                R.drawable.garlicclams, R.drawable.grilledchicken, R.drawable.meatballs,
                R.drawable.pastrami,
                R.drawable.salami));
        meatNames.addAll(Arrays.asList("Pepperoni", "Sausage", "Italian Sausage",
                "Anchovies", "Bacon", "Barbecue Chicken", "Beef", "Canadian Bacon", "Chorizo",
                "Garlic Clams", "Grilled Chicken", "Meatballs", "Pastrami", "Salami"));
    }

    public int getCount() {
        return meatImages.size();
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

        imageView.setImageResource(meatImages.get(position));
        txt.setText(meatNames.get(position));

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
            selectedMeats.remove(meatNames.get(position));
        }
        else {
            selectedPositions.add(position);
            selectedMeats.add(meatNames.get(position));
        }
    }

    public void setSelectedPositions(ArrayList<Integer> positions) {
        selectedPositions = positions;
    }

    public ArrayList<String> getSelectedMeats() {
        return selectedMeats;
    }

    public void setSelectedMeats(ArrayList<String> names) {
        selectedMeats = names;
    }

}
