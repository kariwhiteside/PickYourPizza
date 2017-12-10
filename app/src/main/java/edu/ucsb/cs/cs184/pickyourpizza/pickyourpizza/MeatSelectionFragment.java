package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

public class MeatSelectionFragment extends Fragment {

    View rootView;
    FragmentHelper activityCallback;

    MeatImageAdapter adapterImage;
    ArrayList<Integer> selectedPositions = new ArrayList<>();
    ArrayList<String> selectedMeats = new ArrayList<>();

    public interface FragmentHelper {
        public void changeFragment(String newFragment);
        public void setSelectedPositionsAndMeats(ArrayList<Integer> positions, ArrayList<String> meats);

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
        adapterImage = new MeatImageAdapter(getActivity());
        gridView.setAdapter(adapterImage);

        // set selectedPositions in gridView adapter
        adapterImage.setSelectedPositions(selectedPositions);
        adapterImage.setSelectedMeats(selectedMeats);
        adapterImage.notifyDataSetChanged();

        TextView textView = (TextView)rootView.findViewById(R.id.gridviewTextView);
        textView.setText("Meat Toppings:");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // set selectedPosition in gridView adapter
                adapterImage.setSelectedPositions(i);
                adapterImage.notifyDataSetChanged();

                // set member variables selectedPositions and selectedVeggies
                selectedPositions = adapterImage.getSelectedPositions();
                selectedMeats = adapterImage.getSelectedMeats();

                // set the selectedVeggiesPositions and selectedVeggies in the MainActivity
                activityCallback.setSelectedPositionsAndMeats(selectedPositions, selectedMeats);
            }
        });

        Button prevButton = rootView.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCallback.changeFragment("VeggieSelectionFragment");
            }
        });

        Button nextButton = rootView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCallback.changeFragment("CheeseSelectionFragment");
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    public void setSelectedPositionsAndMeats(ArrayList<Integer> positions, ArrayList<String> meats) {

        // set member variables
        selectedPositions = positions;
        selectedMeats = meats;

        // set adapter variables
        adapterImage = new MeatImageAdapter(getActivity());
        adapterImage.setSelectedPositions(positions);
        adapterImage.setSelectedMeats(meats);
        adapterImage.notifyDataSetChanged();

    }

}
