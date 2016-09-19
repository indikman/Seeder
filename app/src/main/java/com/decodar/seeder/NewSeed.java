package com.decodar.seeder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.decodar.db.dbmanager;
import com.decodar.plugins.ImagePicker;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class NewSeed extends AppCompatActivity {

    //Database
    private dbmanager seed_db;

    //New seed textbox
    private EditText txt_newseed;
    private TextView txt_char_counter;
    private ImageView img_add_image;
    private CheckBox chk_favourite;

    private ProgressDialog dialog;


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

        seed_db = dbmanager.getInstance(this);


        txt_newseed = (EditText) findViewById(R.id.text_newseed);                                   //Initiating UI components
        txt_char_counter = (TextView)findViewById(R.id.seed_character_count);
        img_add_image = (ImageView)findViewById(R.id.seed_thumbnail_newseed);
        chk_favourite = (CheckBox)findViewById(R.id.checkBox_addtofav);

        ImagePicker.setMinQuality(600,600);

        final int max_char_count = getResources().getInteger(R.integer.max_char_count);             //Max character count

        txt_char_counter.setText("0/" + max_char_count);                                            //Setting the character counter to 0

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);                   //Adding a new seed
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(txt_newseed.getText().length()<5){
                    Snackbar.make(view, "Please type your message!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else
                {
                    //Show process Dialog
                    processDialog(true);

                    //Save image
                    String id = generateID();
                    String imagepath = saveImage(id, ((BitmapDrawable)img_add_image.getDrawable()).getBitmap());

                    if(chk_favourite.isChecked()){
                        //Add to favourites
                        seed_db.addSeed(id,txt_newseed.getText().toString(),imagepath,"0","0",1);
                    }else
                    {
                        seed_db.addSeed(id,txt_newseed.getText().toString(),imagepath,"0","0",0);
                    }


                    //Todo generate the json object and start broadcasting

                    processDialog(false);
                    SeedFeed.getInstance().refreshSeeds();


                }

                Toast.makeText(NewSeed.this, "New seed created successfully!", Toast.LENGTH_SHORT).show();
                finish();

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


    public String generateID(){
        //TODO @malith change the generateID method
        return UUID.randomUUID().toString() + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + "";
    }


    public String saveImage(String imageName, Bitmap image){
        new ImageHandler(this).saveImage(imageName, image);
        return imageName + ".jpg";
    }

    public void processDialog(boolean isShow){
        if(isShow)
            dialog = ProgressDialog.show(this, "Creating message", "Generating a new seed...", true);
        else
            dialog.dismiss();
    }
}
