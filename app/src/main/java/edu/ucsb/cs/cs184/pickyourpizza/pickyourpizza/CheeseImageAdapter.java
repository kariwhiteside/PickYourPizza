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
import java.util.Arrays;

/**
 * Created by joesong on 12/4/17.
 */

public class CheeseImageAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<Integer> selectedPositions = new ArrayList<>();
    private ArrayList<String> selectedCheeses = new ArrayList<>();

    // references to our images
    private ArrayList<Integer> cheeseImages = new ArrayList<>();
    private ArrayList<String> cheeseNames = new ArrayList<>();

    public CheeseImageAdapter(Context c) {
        mContext = c;
        cheeseImages.addAll(Arrays.asList(R.drawable.extramozzarella, R.drawable.fetacheese,
                R.drawable.gorgonzolacheese, R.drawable.parmesancheese,
                R.drawable.ricottacheese, R.drawable.romanocheese,
                R.drawable.vegancheese));
        cheeseNames.addAll(Arrays.asList("Extra Mozzarella", "Feta Cheese",
                "Gorgonzola Cheese", "Parmesan Cheese",
                "Ricotta Cheese", "Romano Cheese",
                "Vegan Cheese"));
    }

    public int getCount() {
        return cheeseImages.size();
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
            selectedCheeses.remove(cheeseNames.get(position));
        }
        else {
            selectedPositions.add(position);
            selectedCheeses.add(cheeseNames.get(position));
        }
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView;

        LayoutInflater li = ((Activity) mContext).getLayoutInflater();
        myView = li.inflate(R.layout.grid_item, null);
        ImageView imageView = myView.findViewById(R.id.grid_img);
        TextView txt = myView.findViewById(R.id.grid_txt);

        imageView.setImageResource(cheeseImages.get(position));
        txt.setText(cheeseNames.get(position));

        if(selectedPositions.contains(position)) {
            imageView.setBackgroundColor(Color.BLUE);
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT);
        }
        return myView;
    }

}
