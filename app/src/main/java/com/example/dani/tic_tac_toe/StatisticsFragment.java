package com.example.dani.tic_tac_toe;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;
/**
 * Created by Dani on 5/2/2017.
 */

public class StatisticsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_fragment, container, false);
        initButtons(view);
        setValues(view);
        return view;
    }

    private void setValues(View view) {
        Spinner spinner = (Spinner)view.findViewById(R.id.levelSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Spinner spinner = (Spinner) parentView.findViewById(parentView.getId());
                if (spinner.getSelectedItem().equals("Easy")) {
                    TextView textViewPlayer= (TextView)getActivity().findViewById(R.id.textViewPlayer);
                    TextView textViewComputer= (TextView)getActivity().findViewById(R.id.textViewComputer);
                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    int defaultValue = 0;
                    int highScore1 = sharedPref.getInt(getString(R.string.player0), defaultValue);
                    int highScore2 = sharedPref.getInt(getString(R.string.computer0), defaultValue);
                    textViewPlayer.setText(String.valueOf(highScore1));
                    textViewComputer.setText(String.valueOf(highScore2));
                }
                if (spinner.getSelectedItem().equals("Normal")) {
                    TextView textViewPlayer= (TextView)getActivity().findViewById(R.id.textViewPlayer);
                    TextView textViewComputer= (TextView)getActivity().findViewById(R.id.textViewComputer);
                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    int defaultValue = 0;
                    int highScore1 = sharedPref.getInt(getString(R.string.player1), defaultValue);
                    int highScore2 = sharedPref.getInt(getString(R.string.computer1), defaultValue);
                    textViewPlayer.setText(String.valueOf(highScore1));
                    textViewComputer.setText(String.valueOf(highScore2));
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
    }

    private void initButtons(View view) {
        Button homeButton = (Button) getActivity().findViewById(R.id.homeButton);
        Button startButton = (Button) getActivity().findViewById(R.id.startButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if(fragmentManager.findFragmentById(R.id.fragmentBoardHolder) instanceof StatisticsFragment)
                {
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentBoardHolder, homeFragment);
                    fragmentTransaction.commit();


                }
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if(fragmentManager.findFragmentById(R.id.fragmentBoardHolder) instanceof StatisticsFragment){
                    BoardFragment boardFragment = new BoardFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("level", 0);
                    bundle.putString("player","X");
                    boardFragment.setArguments(bundle);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentBoardHolder,boardFragment);
                    fragmentTransaction.commit();
                }
            }
        });
    }


}
