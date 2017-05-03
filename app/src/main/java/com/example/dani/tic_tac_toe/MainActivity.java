package com.example.dani.tic_tac_toe;

import android.app.Activity;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import Game.Option;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.fragmentBoardHolder, homeFragment);
        fragmentTransaction.commit();


        setupButton();
    }
    HomeFragment homeFragment ;
    private int level;
    private String option;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private void setupButton() {
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardFragment boardFragment = new BoardFragment();
                Bundle homeBundle = homeFragment.getArguments();
                level = -1;
                option ="X";
                if (homeBundle != null) {
                    level = homeBundle.getInt("level", -1);
                    option = homeBundle.getString("option","");
                }
                if(level>=0 && !option.equals("")){
                    Bundle bundle = new Bundle();
                    bundle.putInt("level",level);
                    bundle.putString("option",option);
                    boardFragment.setArguments(bundle);
                }
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentBoardHolder,boardFragment);
                //if (!(fragmentManager.findFragmentById(R.id.fragmentBoardHolder) instanceof BoardFragment))
                  //  fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentManager.findFragmentById(R.id.fragmentBoardHolder) instanceof BoardFragment)
                {
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentBoardHolder, homeFragment);
                    fragmentTransaction.commit();
                    Bundle homeBundle = homeFragment.getArguments();
                    level = -1;
                    option ="X";
                    if (homeBundle != null) {
                        level = homeBundle.getInt("level", -1);
                        option = homeBundle.getString("option","");

                    }
                }
                if(fragmentManager.findFragmentById(R.id.fragmentBoardHolder) instanceof StatisticsFragment)
                {
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentBoardHolder, homeFragment);
                    fragmentTransaction.commit();
                    Bundle homeBundle = homeFragment.getArguments();
                    level = -1;
                    option ="X";
                    if (homeBundle != null) {
                        level = homeBundle.getInt("level", -1);
                        option = homeBundle.getString("option","");

                    }
                }
            }
        });
        Button staticticsButton = (Button) findViewById(R.id.staticticsButton);
        staticticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatisticsFragment statisticsFragment = new StatisticsFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentBoardHolder, statisticsFragment);
                fragmentTransaction.commit();
                Bundle homeBundle = homeFragment.getArguments();
                level = -1;
                option ="X";
                if (homeBundle != null) {
                    level = homeBundle.getInt("level", -1);
                    option = homeBundle.getString("option","");

                }
            }
        });


    }
}
