package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by dsefl on 12/3/2017.
 */

public class StartPageFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.start_page, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.app_logo);
        image.bringToFront();
        return view;
    }
}
