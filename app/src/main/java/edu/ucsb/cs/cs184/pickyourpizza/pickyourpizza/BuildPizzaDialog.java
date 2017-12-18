package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by dsefl on 12/9/2017.
 */

public class BuildPizzaDialog extends DialogFragment{
    View view;
    FragmentHelper activityCallback;
    public static boolean pizzaConfigured = false;

    public interface FragmentHelper {
        public void changeFragment(String newFragment, boolean forward);
        public void calculatePizza();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.finished_dialog, container, false);

        Button button = (Button)view.findViewById(R.id.YesButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pizzaConfigured = true;
                activityCallback.calculatePizza();
                new BuildPizzaListTask(MainActivity.businessList,MainActivity.selectedStyle,MainActivity.selectedSauce,MainActivity.selectedVeggies,MainActivity.selectedMeats,MainActivity.selectedCheeses).execute();
                Log.i("PizzaDia/calculatePizza", "THIS IS THE NUMBER OF PEOPLE " +Integer.toString(MainActivity.numPeople) );

                getDialog().dismiss();
                activityCallback.changeFragment("ListViewFragment", true);
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
