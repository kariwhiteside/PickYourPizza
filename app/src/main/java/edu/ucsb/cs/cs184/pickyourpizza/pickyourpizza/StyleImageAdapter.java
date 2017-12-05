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

public class StyleImageAdapter extends BaseAdapter {
    private Context mContext;
    private int selectedPosition = -1;
    private String selectedStyle = "";

    public StyleImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return styleImages.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void setSelectedPositions(int position){
        selectedPosition = position;
        selectedStyle = styleNames[position];
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView;

        LayoutInflater li = ((Activity) mContext).getLayoutInflater();
        myView = li.inflate(R.layout.grid_item, null);
        ImageView imageView = myView.findViewById(R.id.grid_img);
        TextView txt = myView.findViewById(R.id.grid_txt);

        imageView.setImageResource(styleImages[position]);
        txt.setText(styleNames[position]);

        if(selectedPosition == position) {
            imageView.setBackgroundColor(Color.BLUE);
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT);
        }
        return myView;
    }

    // references to our images
    private Integer[] styleImages = {
            R.drawable.glutenfreecrust, R.drawable.panpizza,
            R.drawable.thincrust, R.drawable.wheatcrust,
            R.drawable.whitecrust
    };

    private String[] styleNames = {
            "Gluten Free Crust", "Pan Pizza",
            "Thin Crust", "Wheat Crust",
            "White Crust"
    };
}