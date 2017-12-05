package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

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

/**
 * Created by joesong on 12/4/17.
 */

public class SauceImageAdapter extends BaseAdapter {
    private Context mContext;
    private int selectedPosition = -1;
    private String selectedSauce = "";

    public SauceImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return sauceImages.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void setSelectedPositions(int position){
        selectedPosition = position;
        selectedSauce = sauceNames[position];

    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView;

        LayoutInflater li = ((Activity) mContext).getLayoutInflater();
        myView = li.inflate(R.layout.grid_item, null);
        ImageView imageView = myView.findViewById(R.id.grid_img);
        TextView txt = myView.findViewById(R.id.grid_txt);

        imageView.setImageResource(sauceImages[position]);
        txt.setText(sauceNames[position]);

        if(selectedPosition == position) {
            imageView.setBackgroundColor(Color.BLUE);
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT);
        }
        return myView;
    }

    // references to our images
    private Integer[] sauceImages = {
            R.drawable.barbequesauce, R.drawable.creamygarlicsauce,
            R.drawable.creamysriracha, R.drawable.marinarasauce,
            R.drawable.pestosauce, R.drawable.ranchsauce,
            R.drawable.southernchipotlesauce, R.drawable.spinachalfredosauce,

    };

    private String[] sauceNames = {
            "Barbeque Sauce", "Creamy Garlic Sauce",
            "Creamy Sriracha", "Marinara Sauce",
            "Pesto Sauce", "Ranch Sauce",
            "Southern Chipotle Sauce",
            "Spinach Alfredo Sauce"
    };
}