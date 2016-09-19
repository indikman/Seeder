package com.decodar.seeder;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.decodar.db.dbmanager;
import com.decodar.seeds.Seed;
import com.decodar.seeds.SeedAdaptor;

import java.util.ArrayList;
import java.util.List;

public class SeedFavourites extends AppCompatActivity {

    private dbmanager seed_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_favourites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        refreshSeeds();
    }

    public void refreshSeeds(){
        seed_db = dbmanager.getInstance(this);
        List<Seed> seedlist = new ArrayList<Seed>();

        //load seeds from cursor
        Cursor seeds = seed_db.getFavouriteSeeds();

        if (seeds.moveToFirst()) {
            while (seeds.isAfterLast() == false) {

                String ID = seeds.getString(seeds.getColumnIndex(dbmanager.seed_id));
                String seed_text = seeds.getString(seeds.getColumnIndex(dbmanager.seed_text));
                String seed_image = seeds.getString(seeds.getColumnIndex(dbmanager.seed_image));
                String seed_likes = seeds.getString(seeds.getColumnIndex(dbmanager.seed_likes));
                String seed_replies = seeds.getString(seeds.getColumnIndex(dbmanager.seed_replies));

                Bitmap image = new ImageHandler(this).loadImage(seed_image);

                Seed seed = new Seed(ID,seed_text,image,Integer.parseInt(seed_likes),Integer.parseInt(seed_replies));

                seedlist.add(seed);

                seeds.moveToNext();
            }
        }
        seeds.close();


        SeedAdaptor seedAd = new SeedAdaptor(seedlist);


        //Adding the Recucler View
        RecyclerView cardList = (RecyclerView)findViewById(R.id.favouriteSeedsList);
        cardList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cardList.setLayoutManager(llm);

        //Setting the adaptor
        cardList.setAdapter(seedAd);
    }

}
