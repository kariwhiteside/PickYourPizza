package edu.ucsb.cs.cs184.pickyourpizza.pickyourpizza;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Kari on 12/6/17.
 */

public class NumPeopleFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.num_people_fragment, container, false);

        final ImageView peopleImageView = (ImageView)view.findViewById(R.id.peopleImageView);
        final TextView numberTextView = (TextView)view.findViewById(R.id.numberTextView);

        SeekBar seekBar = (SeekBar)view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int prog = progress + 1;
                numberTextView.setText("" + prog);
                String fileName;
                if (prog < 10) {
                    fileName = "people0" + prog;
                } else {
                    fileName = "people" + prog;
                }
                Context context = peopleImageView.getContext();
                int id = context.getResources().getIdentifier(fileName, "drawable", context.getPackageName());
                peopleImageView.setImageResource(id);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }
}
