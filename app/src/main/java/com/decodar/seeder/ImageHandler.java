package com.decodar.seeder;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by indik on 9/18/2016.
 */
public class ImageHandler {

    private String folderName = "seed_images";
    private Context context;

    public ImageHandler(Context context){
        this.context = context;
    }

    //Compressing and saving the image
    public void saveImage(String imageName, Bitmap bitmap){
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());

        File directory = cw.getDir(folderName, Context.MODE_PRIVATE);

        File path = new File(directory, imageName+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 30, fos); //compressing the image
            Log.e("Image Save", "Image saved successfully");
            Log.e("Image Save", directory.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("Image Save issue", "Problem in saving the image");
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Returning the image
    public Bitmap loadImage(String imageName){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(createFile(imageName));
            return BitmapFactory.decodeStream(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //Deleting the image
    public boolean deleteImage(String imageName){
        File file = createFile(imageName);
        return file.delete();
    }


    public File createFile(String imageName){
        File directory = context.getDir(folderName, Context.MODE_PRIVATE);
        return new File(directory, imageName);
    }
}
