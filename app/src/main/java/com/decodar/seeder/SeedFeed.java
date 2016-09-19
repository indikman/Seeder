package com.decodar.seeder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.decodar.db.dbmanager;
import com.decodar.seeds.Seed;
import com.decodar.seeds.SeedAdaptor;

import java.util.ArrayList;
import java.util.List;

public class SeedFeed extends AppCompatActivity {

    private dbmanager seed_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Changing the stats bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.DKGRAY);

        }

        //populat the seed feed
        refreshSeeds();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewSeed(); // goto add new seed activitys
            }
        });


        ImageButton btnFavourites = (ImageButton)findViewById(R.id.btn_seed_favourites);
        btnFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFavourites();
            }
        });
    }

    public void addNewSeed(){
        Intent intent = new Intent(this, NewSeed.class);
        this.startActivity(intent);
    }

    private void openFavourites(){
        Intent intent = new Intent(this, SeedFavourites.class);
        this.startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_seed_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshSeeds(){
        seed_db = dbmanager.getInstance(this);
        List<Seed> seedlist = new ArrayList<Seed>();

        //load seeds from cursor
        Cursor seeds = seed_db.getAllSeeds();

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
        RecyclerView cardList = (RecyclerView)findViewById(R.id.seedList);
        cardList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cardList.setLayoutManager(llm);

        //Setting the adaptor
        cardList.setAdapter(seedAd);
    }



    //Singleton
    public static SeedFeed seedfeed = null;
    public static SeedFeed getInstance(){
        if(seedfeed==null)
            seedfeed = new SeedFeed();

        return seedfeed;
    }
}
