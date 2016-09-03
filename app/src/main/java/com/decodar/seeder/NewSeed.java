package com.decodar.seeder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.decodar.plugins.ImagePicker;

import org.w3c.dom.Text;


public class NewSeed extends AppCompatActivity {

    //New seed textbox
    private EditText txt_newseed;
    private TextView txt_char_counter;
    private ImageView img_add_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_seed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Changing the stats bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.DKGRAY);
        }


        txt_newseed = (EditText) findViewById(R.id.text_newseed);                                   //Initiating UI components
        txt_char_counter = (TextView)findViewById(R.id.seed_character_count);
        img_add_image = (ImageView)findViewById(R.id.seed_thumbnail_newseed);
        ImagePicker.setMinQuality(600,600);

        final int max_char_count = getResources().getInteger(R.integer.max_char_count);             //Max character count


        txt_char_counter.setText("0/" + max_char_count);                                            //Setting the character counter to 0

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);                   //Adding a new seed
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adding a new Seed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        img_add_image.setOnClickListener(new View.OnClickListener() {                               //Adding a photo from gallery or camera
            @Override
            public void onClick(View v) {
                onPickImage(v);                                                                     //Picking the Image
            }
        });



        //-------------------------------TEXT CHARACTER COUNTER-------------------------------------

        txt_newseed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() >= max_char_count)
                {
                    View layout = (View)findViewById(R.id.card_view_newseed);
                    Snackbar.make(layout, "Character limit exceeded!", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                txt_char_counter.setText(s.toString().length() + "/" + max_char_count);
            }
        });

    }

    // Actiity results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data, ((BitmapDrawable)((ImageView)findViewById(R.id.seed_thumbnail_newseed)).getDrawable()).getBitmap());
        // TODO with bitmap
        img_add_image.setImageBitmap(bitmap);
    }

    public void onPickImage(View view) {
        // Click on image button
        ImagePicker.pickImage(this, "Pick or take a photo!");
    }

}
