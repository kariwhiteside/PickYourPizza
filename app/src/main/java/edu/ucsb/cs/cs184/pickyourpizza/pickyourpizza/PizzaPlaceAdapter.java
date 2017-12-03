package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dsefl on 11/30/2017.
 */

public class PizzaPlaceAdapter extends ArrayAdapter<PizzaPlace> {

    View view;

    private Context context;
    private ArrayList<PizzaPlace> pizzaPlaces;

    public PizzaPlaceAdapter(Context context, ArrayList<PizzaPlace> pizzaPlaces){
        super(context, R.layout.listview_fragment, pizzaPlaces);
        this.context = context;
        this.pizzaPlaces = pizzaPlaces;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item, parent, false);

        ImageView imageView = (ImageView)view.findViewById(R.id.logo);
        imageView.setImageResource(pizzaPlaces.get(position).getPhotoID());

        TextView placeTextview = (TextView)view.findViewById(R.id.Chain);
        placeTextview.setText(pizzaPlaces.get(position).getChain());

        TextView pizzatypesTextview = (TextView)view.findViewById(R.id.PizzaTypes);
        pizzatypesTextview.setText(pizzaPlaces.get(position).getPizzaSizes());

        TextView priceTextview = (TextView) view.findViewById(R.id.Price);
        priceTextview.setText(pizzaPlaces.get(position).getPrice());

        return view;
    }
}
