package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by dsefl on 12/9/2017.
 */

public class BuildPizzaDialog extends DialogFragment{
    View view;
    CheeseSelectionFragment.FragmentHelper activityCallback;
    boolean isNOO = false;

    public interface FragmentHelper {
        public void changeFragment(String newFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.finished_dialog, container, false);

        Button button = (Button)view.findViewById(R.id.YesButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityCallback.changeFragment("ListViewFragment");
            }
        });

        Button button2 = (Button)view.findViewById(R.id.NoButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        return view;
    }
}
