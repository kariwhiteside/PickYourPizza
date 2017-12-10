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

/**
 * Created by joesong on 12/4/17.
 */

public class SauceSelectionFragment extends Fragment {

    View rootView;
    FragmentHelper activityCallback;

    SauceImageAdapter adapterImage;
    int selectedPosition;
    String selectedSauce;

    public interface FragmentHelper {
        public void changeFragment(String newFragment);
        public void setSelectedPositionAndSauce(int position, String sauce);
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
        adapterImage = new SauceImageAdapter(getActivity());
        gridView.setAdapter(adapterImage);

        // set selectedPosition in gridView adapter
        adapterImage.setSelectedPositions(selectedPosition);
        adapterImage.notifyDataSetChanged();

        TextView textView = (TextView)rootView.findViewById(R.id.gridviewTextView);
        textView.setText("Sauce:");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // set selectedPosition in gridView adapter
                adapterImage.setSelectedPositions(i);
                adapterImage.notifyDataSetChanged();

                // set member variables selectedPosition and selectedSauce
                selectedPosition = i;
                selectedSauce = adapterImage.getSelectedSauce();

                // set the selectedStylePosition and selectedStyle in the MainActivity
                activityCallback.setSelectedPositionAndSauce(selectedPosition, selectedSauce);
            }
        });

        Button prevButton = rootView.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCallback.changeFragment("StyleSelectionFragment");
            }
        });

        Button nextButton = rootView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCallback.changeFragment("VeggieSelectionFragment");
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    public void setSelectedPositionAndSauce(int position, String sauce) {

        // set member variables
        selectedPosition = position;
        selectedSauce = sauce;

        // set adapter variables
        adapterImage = new SauceImageAdapter(getActivity());
        adapterImage.setSelectedPositions(position);
        adapterImage.notifyDataSetChanged();

    }

}
