package com.example.dani.tic_tac_toe;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import Game.Option;

/**
 * Created by Dani on 4/18/2017.
 */

public class HomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homefragment,container,false);
        this.view = view;
        bundle = new Bundle();
        setupButtons();
        setLevelSpinner();
        Button startButton = (Button)getActivity().findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardFragment boardFragment = new BoardFragment();

                boardFragment.setArguments(bundle);
                //FragmentManager fragmentManger =  getFragmentManager();
                //FragmentTransaction fragmentTransaction = fragmentManger.beginTransaction();
                FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentBoardHolder,boardFragment);
                //if (!(fragmentManager.findFragmentById(R.id.fragmentBoardHolder) instanceof BoardFragment))
                  //  fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private View view;

    private void setupButtons() {
        final ImageButton firstButton = (ImageButton)view.findViewById(R.id.icsButton);
        final ImageButton secondButton = (ImageButton)view.findViewById(R.id.zeroButton);
        final Button pvpButton = (Button) view.findViewById(R.id.pvpButton);
        firstButton.setBackground(getResources().getDrawable(R.drawable.buttonroundedpressed));
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerChoice = Option.X;
                bundle.putString("option","X");
                firstButton.setBackground(getResources().getDrawable(R.drawable.buttonroundedpressed));
                secondButton.setBackground(getResources().getDrawable(R.drawable.buttonrounded));
                pvpButton.setBackground(getResources().getDrawable(R.drawable.buttonrounded));
            }
        });
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerChoice = Option.O;
                bundle.putString("option","O");
                secondButton.setBackground(getResources().getDrawable(R.drawable.buttonroundedpressed));
                firstButton.setBackground(getResources().getDrawable(R.drawable.buttonrounded));
                pvpButton.setBackground(getResources().getDrawable(R.drawable.buttonrounded));
            }
        });

        pvpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("option","pvp");
                pvpButton.setBackground(getResources().getDrawable(R.drawable.buttonroundedpressed));
                firstButton.setBackground(getResources().getDrawable(R.drawable.buttonrounded));
                secondButton.setBackground(getResources().getDrawable(R.drawable.buttonrounded));
            }
        });



    }
    private Bundle bundle;
    private Option playerChoice = Option.X;
    private int level = 0;

    public void setLevelSpinner()
    {
        Spinner spinner = (Spinner)view.findViewById(R.id.levelSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Spinner spinner = (Spinner) parentView.findViewById(parentView.getId());
                if(spinner.getSelectedItem().equals("Easy"))
                    level = 0;
                if(spinner.getSelectedItem().equals("Normal"))
                    level = 1;


                bundle.putInt("level",level);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                level=0;
            }

        });


    }

}
