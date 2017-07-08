package com.example.sashank.pokedex;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Communicator{

    FragmentManager fragmentManager;
    MainFragment mainFragment;
    SearchResultFragment searchResultFragment;
    //HistoryFragment historyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //historyFragment = new HistoryFragment();

        mainFragment = new MainFragment();
        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_activity_layout,mainFragment,"MainFragment");

        transaction.commit();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_layout, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.history_button) {
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.replace(R.id.main_activity_layout,historyFragment,"History");
//            transaction.addToBackStack("Stack");
//
//            transaction.commit();
//            return true;
//        }
//        else
//            return super.onOptionsItemSelected(item);
//    }

    @Override
    public void startResultFragment(String input) {


        searchResultFragment = SearchResultFragment.newInstance(input);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_activity_layout,searchResultFragment,"SearchResult");
        transaction.addToBackStack("Stack");

        transaction.commit();
    }

//    @Override
//    public void sendHistory(MyPokemon pokemon) {
//        historyFragment.addHistoryItem(pokemon);
//    }
}
