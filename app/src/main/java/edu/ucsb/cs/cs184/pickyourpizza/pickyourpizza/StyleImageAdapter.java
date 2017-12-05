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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class StyleImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Integer> selectedPositions = new ArrayList<>();

    public StyleImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
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
        }
        else {
            selectedPositions.add(position);
        }
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView;

        LayoutInflater li = ((Activity) mContext).getLayoutInflater();
        myView = li.inflate(R.layout.grid_item, null);
        ImageView imageView = (ImageView) myView.findViewById(R.id.grid_img);
        TextView txt = (TextView) myView.findViewById(R.id.grid_txt);

        imageView.setImageResource(mThumbIds[position]);
        txt.setText(mThumbNames[position]);

        if(selectedPositions.contains(position)) {
            imageView.setBackgroundColor(Color.BLUE);
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT);
        }
        return myView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.glutenfreecrust, R.drawable.panpizza,
            R.drawable.thincrust, R.drawable.wheatcrust,
            R.drawable.whitecrust
    };

    private String[] mThumbNames = {
            "Gluten Free Crust", "Pan Pizza",
            "Thin Crust", "Wheat Crust",
            "White Crust"
    };
}
