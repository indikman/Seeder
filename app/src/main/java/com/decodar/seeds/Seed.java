package com.decodar.seeds;

import android.graphics.Bitmap;

/**
 * Created by indik on 9/1/2016.
 */
public class Seed {

    protected String ID;
    protected String seed_text;
    protected Bitmap seed_image;
    protected int no_of_likes;
    protected int no_of_replies;

    //Number of replies may depend, should add it to an arraylist
    public Seed(String ID, String seed_text, Bitmap seed_image, int no_of_likes, int no_of_replies)
    {
        this.ID = ID;
        this.seed_text = seed_text;
        this.seed_image = seed_image;
        this.no_of_likes = no_of_likes;
        this.no_of_replies = no_of_replies;
    }
}
