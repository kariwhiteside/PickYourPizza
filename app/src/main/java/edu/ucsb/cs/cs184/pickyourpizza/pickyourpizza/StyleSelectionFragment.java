package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.io.Serializable;

/**
 * Created by joesong on 12/4/17.
 */

public class StyleSelectionFragment extends Fragment {

    View rootView;
    FragmentHelper activityCallback;

    StyleImageAdapter adapterImage;
    int selectedPosition;
    String selectedStyle;

    public interface FragmentHelper {
        public void changeFragment(String newFragment);
        public void setSelectedPositionAndStyle(int position, String style);
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
        adapterImage = new StyleImageAdapter(getActivity());
        gridView.setAdapter(adapterImage);

        // set selectedPosition in gridView adapter
        adapterImage.setSelectedPositions(selectedPosition);
        adapterImage.notifyDataSetChanged();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("hello", "onClick");

                // set selectedPosition in gridView adapter
                adapterImage.setSelectedPositions(i);
                adapterImage.notifyDataSetChanged();

                // set member variables selectedPosition and selectedStyle
                selectedPosition = i;
                selectedStyle = adapterImage.getSelectedStyle();

                // set the selectedStylePosition and selectedStyle in the MainActivity
                activityCallback.setSelectedPositionAndStyle(selectedPosition, selectedStyle);
            }
        });

        Button prevButton = rootView.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCallback.changeFragment("NumPeopleFragment");
            }
        });

        Button nextButton = rootView.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCallback.changeFragment("SauceSelectionFragment");
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    public void setSelectedPositionAndStyle(int position, String style) {

        // set member variables
        selectedPosition = position;
        selectedStyle = style;

        // set adapter variables
        adapterImage = new StyleImageAdapter(getActivity());
        adapterImage.setSelectedPositions(position);
        adapterImage.notifyDataSetChanged();

    }



}
