package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

/**
 * Created by joesong on 12/3/17.
 */

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


import java.util.ArrayList;
import java.util.Arrays;

public class StyleImageAdapter extends BaseAdapter {
    private Context mContext;
    private int selectedPosition = -1;
    private String selectedStyle = "";
    // references to our images
    private ArrayList<Integer> styleImages = new ArrayList<Integer>();
    private ArrayList<String> styleNames = new ArrayList<String>();

    public StyleImageAdapter(Context c) {
        mContext = c;
        styleImages.addAll(Arrays.asList(R.drawable.glutenfreecrust, R.drawable.panpizza,
                R.drawable.thincrust, R.drawable.wheatcrust,
                R.drawable.whitecrust));
        styleNames.addAll(Arrays.asList("Gluten Free Crust", "Pan Pizza",
                "Thin Crust", "Wheat Crust",
                "White Crust"));
    }

    public int getCount() {
        return styleImages.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void setSelectedPositions(int position){
        selectedPosition = position;
        selectedStyle = styleNames.get(position);
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("Style Adapter: ", "Get View Called");

        View myView;

        LayoutInflater li = ((Activity) mContext).getLayoutInflater();
        myView = li.inflate(R.layout.grid_item, null);
        ImageView imageView = myView.findViewById(R.id.grid_img);
        TextView txt = myView.findViewById(R.id.grid_txt);

        imageView.setImageResource(styleImages.get(position));
        txt.setText(styleNames.get(position));

        if(selectedPosition == position) {
            imageView.setBackgroundColor(Color.BLUE);
            Log.i("Style Adapter: ", "Background Blue");

        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT);
        }
        return myView;
    }
}
