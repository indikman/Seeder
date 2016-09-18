package com.decodar.seeder;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.decodar.seeds.Seed;
import com.decodar.seeds.SeedAdaptor;

import java.util.ArrayList;
import java.util.List;

public class SeedFavourites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_favourites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //---------------Create the list of favourite seeds --------------
        List<Seed> favouriteSeeds = new ArrayList<Seed>();
        RecyclerView favouritesList = (RecyclerView)findViewById(R.id.favouriteSeedsList);


        //TODO-----------Retrieve data from the database------------------


        //TODO-----------Populate the favourites to the list -------------
        favouriteSeeds.add(new Seed("Sample", "This is a sample!", BitmapFactory.decodeResource(getResources(), R.drawable.sample), 10,5)); //test


        //---------------Create the list----------------------------------
        SeedAdaptor favouritesAdaptor = new SeedAdaptor(favouriteSeeds);
        favouritesList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        favouritesList.setLayoutManager(llm);

        //Setting the adaptor
        favouritesList.setAdapter(favouritesAdaptor);
    }

}
