package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by joesong on 12/4/17.
 */

public class VeggieSelectionFragment extends Fragment {

    View rootView;
    FragmentHelper activityCallback;

    VeggieImageAdapter adapterImage;
    ArrayList<Integer> selectedPositions = new ArrayList<>();
    ArrayList<String> selectedVeggies = new ArrayList<>();

    public interface FragmentHelper {
        public void changeFragment(String newFragment, boolean forward);
        public void setSelectedPositionsAndVeggies(ArrayList<Integer> positions, ArrayList<String> veggies);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCallback = (FragmentHelper)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FragmentHelper");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gridview_fragment, container, false);
        GridView gridView = rootView.findViewById(R.id.gridview);
        adapterImage = new VeggieImageAdapter(getActivity());
        gridView.setAdapter(adapterImage);
      
        // set selectedPositions in gridView adapter
        adapterImage.setSelectedPositions(selectedPositions);
        adapterImage.setSelectedVeggies(selectedVeggies);
        adapterImage.notifyDataSetChanged();

        TextView textView = (TextView)rootView.findViewById(R.id.gridviewTextView);
        textView.setText("Veggie Toppings:");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // set selectedPosition in gridView adapter
                adapterImage.setSelectedPositions(i);
                adapterImage.notifyDataSetChanged();

                // set member variables selectedPositions and selectedVeggies
                selectedPositions = adapterImage.getSelectedPositions();
                selectedVeggies = adapterImage.getSelectedVeggies();

                // set the selectedVeggiesPositions and selectedVeggies in the MainActivity
                activityCallback.setSelectedPositionsAndVeggies(selectedPositions, selectedVeggies);
            }
        });

        Button prevButton = rootView.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCallback.changeFragment("SauceSelectionFragment", false);
            }
        });

        Button nextButton = rootView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check to see if string var selectedSauce is populated at user select
                Log.i("VeggieSelectionFragment",selectedVeggies+" THIS IS THE SELECTED VEGGIES");
                activityCallback.changeFragment("MeatSelectionFragment", true);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    public void setSelectedPositionsAndVeggies(ArrayList<Integer> positions, ArrayList<String> veggies) {

        // set member variables
        selectedPositions = positions;
        selectedVeggies = veggies;

        // set adapter variables
        adapterImage = new VeggieImageAdapter(getActivity());
        adapterImage.setSelectedPositions(positions);
        adapterImage.setSelectedVeggies(veggies);
        adapterImage.notifyDataSetChanged();

    }

}
