package com.decodar.seeder;

import android.content.Intent;
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

import com.decodar.seeds.Seed;
import com.decodar.seeds.SeedAdaptor;

import java.util.ArrayList;
import java.util.List;

public class SeedFeed extends AppCompatActivity {

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

        List<Seed> seedlist = new ArrayList<Seed>();
        seedlist.add(new Seed("Sample", "This is a sample!", BitmapFactory.decodeResource(getResources(), R.drawable.sample), 10,5));
        seedlist.add(new Seed("Sample", "This is a sample text! I found an awesome Aquarium to get amazing fish for your tank! Check this out!", BitmapFactory.decodeResource(getResources(), R.drawable.sample), 10,5));
        seedlist.add(new Seed("Sample", "This is a sample!", BitmapFactory.decodeResource(getResources(), R.drawable.sample), 10,5));
        seedlist.add(new Seed("Sample", "This is a sample!", BitmapFactory.decodeResource(getResources(), R.drawable.sample), 10,5));
        seedlist.add(new Seed("Sample", "This is a sample!", BitmapFactory.decodeResource(getResources(), R.drawable.sample), 10,5));
        SeedAdaptor seedAd = new SeedAdaptor(seedlist);


        //Adding the Recucler View
        RecyclerView cardList = (RecyclerView)findViewById(R.id.seedList);
        cardList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cardList.setLayoutManager(llm);

        //Setting the adaptor
        cardList.setAdapter(seedAd);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                     //   .setAction("Action", null).show();

                addNewSeed();

//                Intent startCustomCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(startCustomCameraIntent, 1);


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
}
