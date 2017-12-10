package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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

public class CheeseSelectionFragment extends Fragment {

    View rootView;
    FragmentHelper activityCallback;

    CheeseImageAdapter adapterImage;
    ArrayList<Integer> selectedPositions = new ArrayList<>();
    ArrayList<String> selectedCheeses = new ArrayList<>();

    public interface FragmentHelper {
        public void changeFragment(String newFragment);
        public void setSelectedPositionsAndCheeses(ArrayList<Integer> positions, ArrayList<String> cheeses);
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
        adapterImage = new CheeseImageAdapter(getActivity());
        gridView.setAdapter(adapterImage);

        // set selectedPositions in gridView adapter
        adapterImage.setSelectedPositions(selectedPositions);
        adapterImage.setSelectedCheeses(selectedCheeses);
        adapterImage.notifyDataSetChanged();

        TextView textView = (TextView)rootView.findViewById(R.id.gridviewTextView);
        textView.setText("Cheese Toppings:");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // set selectedPosition in gridView adapter
                adapterImage.setSelectedPositions(i);
                adapterImage.notifyDataSetChanged();

                // set member variables selectedPositions and selectedVeggies
                selectedPositions = adapterImage.getSelectedPositions();
                selectedCheeses = adapterImage.getSelectedCheeses();

                // set the selectedVeggiesPositions and selectedVeggies in the MainActivity
                activityCallback.setSelectedPositionsAndCheeses(selectedPositions, selectedCheeses);
            }
        });

        Button prevButton = rootView.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCallback.changeFragment("MeatSelectionFragment");
            }
        });

        Button nextButton = rootView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BuildPizzaDialog dialog = BuildPizzaDialog.instantiate(getActivity(), "Hello world");
                BuildPizzaDialog dialog;
                dialog = new BuildPizzaDialog();
                dialog.show(getFragmentManager(), "dialog");

                // MAKE THIS CREATE THE PIZZA. GO TO LISTVIEW FRAGMENT.
                // activityCallback.changeFragment("SauceSelectionFragment");
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    public void setSelectedPositionsAndCheeses(ArrayList<Integer> positions, ArrayList<String> cheeses) {

        // set member variables
        selectedPositions = positions;
        selectedCheeses = cheeses;

        // set adapter variables
        adapterImage = new CheeseImageAdapter(getActivity());
        adapterImage.setSelectedPositions(positions);
        adapterImage.setSelectedCheeses(cheeses);
        adapterImage.notifyDataSetChanged();

    }

}
