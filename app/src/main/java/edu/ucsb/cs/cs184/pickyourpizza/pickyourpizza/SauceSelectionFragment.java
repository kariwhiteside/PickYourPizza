package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by joesong on 12/4/17.
 */

public class SauceSelectionFragment extends Fragment {
    View rootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GridView gridView = rootView.findViewById(R.id.gridview);
        final SauceImageAdapter adapterImage = new SauceImageAdapter(getActivity());
        gridView.setAdapter(adapterImage);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapterImage.setSelectedPositions(i);
                adapterImage.notifyDataSetChanged();
            }
        });
        return gridView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


    }

}
