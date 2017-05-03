package com.example.dani.tic_tac_toe;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

import Game.*;
/**
 * Created by Dani on 4/17/2017.
 */


public class BoardFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.board_fragment,container,false);
        bundle = this.getArguments();
        if(bundle != null)
        {
            level = bundle.getInt("level",0);
            String s = bundle.getString("option","X");

            if(s.equals("X"))
            {
                player=Option.X;
                computer=Option.O;
                computerImage = R.drawable.zero;
                playerImage = R.drawable.ics;
            }
            if(s.equals("O"))
            {
                computerImage = R.drawable.ics;
                playerImage = R.drawable.zero;
                player=Option.O;
                computer=Option.X;
            }
            if(s.equals("pvp"))
            {
                computerImage = R.drawable.ics;
                playerImage = R.drawable.zero;
                player=Option.O;
                computer=Option.X;
                pvpMode=true;
            }
        }

        a = new Algorithm(this.computer,this.player,level);

        board = new Board();
        a.setTable(board);
        this.view = view;
        if(player.equals(Option.X) && !pvpMode)
            setPlayerFirst(view);
        if(player.equals(Option.O) && !pvpMode)
            setComputerFirst(view);

        if(pvpMode == true)
            setPvpMode();
        Button startButton = (Button)getActivity().findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoardFragment boardFragment = new BoardFragment();

                boardFragment.setArguments(bundle);
                FragmentManager fragmentManger = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManger.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentBoardHolder,boardFragment);
                //if (!(fragmentManager.findFragmentById(R.id.fragmentBoardHolder) instanceof BoardFragment))
                //  fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;

    }
    private Board pvpBoard;
    private boolean firstPlayer = true;
    private Option player ;
    private Option computer;
    private int level;
    private void setPvpMode() {
        pvpBoard = new Board();

        GridLayout gridLayout = (GridLayout)view.findViewById(R.id.gridViewLayout);
        for(int i=0; i<gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            ImageButton img = (ImageButton)view.findViewById(child.getId());
            img.setTag(i);
            img.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ImageButton img = (ImageButton)v;
                    if(firstPlayer)
                    {
                        if(img.getDrawable() == null){
                        img.setImageResource(R.drawable.ics);
                        int[] move = a.tagToMove(img.getTag());
                        board.move(move[0],move[1],Option.X);
                        if(board.checkWin(Option.X))
                        {
                            Toast.makeText(getActivity(),"X won",Toast.LENGTH_LONG).show();

                            endGame();
                        }
                        firstPlayer = false;}
                    }
                    else
                    {
                        if(img.getDrawable() == null){
                             img.setImageResource(R.drawable.zero);
                            int[] move = a.tagToMove(img.getTag());
                            board.move(move[0],move[1],Option.O);
                            if(board.checkWin(Option.O))
                            {
                                Toast.makeText(getActivity(),"O won",Toast.LENGTH_LONG).show();
                                endGame();
                            }
                            firstPlayer = true;
                            }
                    }
                }
            });
        }


    }

    private void setComputerFirst(View view) {

       // Toast.makeText(getActivity(),player.toString(),Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(),String.valueOf(level),Toast.LENGTH_LONG).show();
        GridLayout gridLayout = (GridLayout)view.findViewById(R.id.gridViewLayout);
        for(int i=0; i<gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            ImageButton img = (ImageButton)view.findViewById(child.getId());
            img.setTag(i);
        }
        Random random = new Random(SystemClock.currentThreadTimeMillis());
        int moveTag =Math.abs(random.nextInt()%9);
        int []move = a.tagToMove(moveTag);
        Board board = new Board();
        board.move(move[0],move[1],player);
        a.setTable(board);
        makeComputerMove(moveTag);

        for(int i=0; i<gridLayout.getChildCount(); i++)
        {

            View child = gridLayout.getChildAt(i);
            ImageButton img = (ImageButton)view.findViewById(child.getId());
            img.setTag(i);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ImageButton img = (ImageButton) v;
                    if(img.getDrawable() == null){
                        img.setImageResource(playerImage);
                        try {
                            makePlayerMove(v.getTag());
                            int[] move = a.makeBest(); // computer best move
                            makeComputerMove(a.moveToTag(move));
                        }
                        catch (CustomException e)
                        {

                            if(e.getPlayer().equals(computer))
                            {
                                makeComputerMove(a.moveToTag(e.getLastMove()));
                                statistics(false,level);
                            }
                            if(e.getPlayer().equals(player))
                            {
                                statistics(true,level);
                            }
                            if(e.getPlayer().equals(Option.EMPTY))
                            {
                                makeComputerMove(a.moveToTag(e.getLastMove()));
                            }
                            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                            endGame();
                        }

                    }

                }
            });}
    }


    private boolean pvpMode=false;
    private Bundle bundle;
    View view;
    int playerImage;
    int computerImage;
    private Algorithm a;
    private Board board;
    private void setPlayerFirst(View view) {
        Toast.makeText(getActivity(),player.toString(),Toast.LENGTH_LONG).show();
        GridLayout gridLayout = (GridLayout)view.findViewById(R.id.gridViewLayout);
        for(int i=0; i<gridLayout.getChildCount(); i++)
        {

            View child = gridLayout.getChildAt(i);
            ImageButton img = (ImageButton)view.findViewById(child.getId());
            img.setTag(i);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton img = (ImageButton) v;
                    if(img.getDrawable() == null){
                            img.setImageResource(playerImage);
                            try {
                                makePlayerMove(v.getTag());
//                                if(a.isFull())
//                                {
//                                    Toast.makeText(getActivity(),"Tie!",Toast.LENGTH_LONG).show();
//                                    endGame();
//                                }
                                int[] move = a.makeBest(); // computer best move
                                makeComputerMove(a.moveToTag(move));

                            }
                            catch (CustomException e)
                            {
                                if(e.getPlayer().equals(computer))
                                {
                                    makeComputerMove(a.moveToTag(e.getLastMove()));
                                    statistics(false, level);
                                }
                                if(e.getPlayer().equals(player))
                                {
                                    statistics(true, level);
                                }
                                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                                endGame();
                            }


                    }
                }
            });}
        }

    private void statistics(boolean player, int level) {
        if(player == true && level == 0)
        {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            int defaultValue=0;
            int highScore = sharedPref.getInt(getString(R.string.player0), defaultValue);
            highScore++;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.player0), highScore);
            editor.commit();
        }
        if(player == true && level == 1)
        {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            int defaultValue=0;
            int highScore = sharedPref.getInt(getString(R.string.player1), defaultValue);
            highScore++;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.player1), highScore);
            editor.commit();
        }
        if(player == false && level == 0)
        {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            int defaultValue=0;
            int highScore = sharedPref.getInt(getString(R.string.computer0), defaultValue);
            highScore++;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.computer0), highScore);
            editor.commit();
        }
        if(player == false && level == 1)
        {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            int defaultValue=0;
            int highScore = sharedPref.getInt(getString(R.string.computer1), defaultValue);
            highScore++;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.computer1), highScore);
            editor.commit();
        }

    }

    private void endGame() {

        GridLayout gridLayout = (GridLayout)view.findViewById(R.id.gridViewLayout);
        for(int i=0; i<gridLayout.getChildCount(); i++)
        {
            View v = gridLayout.getChildAt(i);
            ImageView img = (ImageView)v;
            img.setClickable(false);
        }
    }


    //puts on grid the computer's move
    private void makeComputerMove(Object move) {
        GridLayout gridLayout = (GridLayout)view.findViewById(R.id.gridViewLayout);
        for(int i=0; i<gridLayout.getChildCount(); i++)
        {
            View v = gridLayout.getChildAt(i);
            ImageView img = (ImageView)v;
            if(move != null){
                 if(img.getTag().equals(move) && img.getDrawable() == null)
                 {
                     img.setImageResource(computerImage);
                 }
        }}
    }


    //makes a move for player
    private void makePlayerMove(Object tag) throws CustomException{
        a.move(a.tagToMove(tag));
    }



}
