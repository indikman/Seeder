package com.decodar.seeder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.decodar.plugins.TouchImageView;

public class ImageViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        TouchImageView img = new TouchImageView(this);
//        img.setImageBitmap();

    }
}
