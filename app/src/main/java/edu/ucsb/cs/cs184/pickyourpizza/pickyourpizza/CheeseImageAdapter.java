package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by joesong on 12/4/17.
 */

public class CheeseImageAdapter {
    private Context mContext;
    private ArrayList<Integer> selectedPositions = new ArrayList<>();
    private ArrayList<String> selectedCheeses = new ArrayList<>();

    public CheeseImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return cheeseImages.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void setSelectedPositions(int position){
        if(selectedPositions.contains(position)){
            selectedPositions.remove(Integer.valueOf(position));
            selectedCheeses.remove(cheeseNames[position]);
        }
        else {
            selectedPositions.add(position);
            selectedCheeses.add(cheeseNames[position]);
        }
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView;

        LayoutInflater li = ((Activity) mContext).getLayoutInflater();
        myView = li.inflate(R.layout.grid_item, null);
        ImageView imageView = (ImageView) myView.findViewById(R.id.grid_img);
        TextView txt = (TextView) myView.findViewById(R.id.grid_txt);

        imageView.setImageResource(cheeseImages[position]);
        txt.setText(cheeseNames[position]);

        if(selectedPositions.contains(position)) {
            imageView.setBackgroundColor(Color.BLUE);
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT);
        }
        return myView;
    }

    // references to our images
    private Integer[] cheeseImages = {
            R.drawable.extramozzarella, R.drawable.fetacheese,
            R.drawable.gorgonzolacheese, R.drawable.parmesancheese,
            R.drawable.ricottacheese, R.drawable.romanocheese,
            R.drawable.vegancheese
    };

    private String[] cheeseNames = {
            "Extra Mozzarella", "Feta Cheese",
            "Gorgonzola Cheese", "Parmesan Cheese",
            "Ricotta Cheese", "Romano Cheese",
            "Vegan Cheese"
    };
}
