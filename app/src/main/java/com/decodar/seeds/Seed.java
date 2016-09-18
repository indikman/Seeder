package com.decodar.seeds;

import android.graphics.Bitmap;

/**
 * Created by indik on 9/1/2016.
 */
public class Seed {

    private String ID;
    private String seed_text;
    private Bitmap seed_image;
    private int no_of_likes;
    private int no_of_replies;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSeed_text() {
        return seed_text;
    }

    public void setSeed_text(String seed_text) {
        this.seed_text = seed_text;
    }

    public Bitmap getSeed_image() {
        return seed_image;
    }

    public void setSeed_image(Bitmap seed_image) {
        this.seed_image = seed_image;
    }

    public int getNo_of_likes() {
        return no_of_likes;
    }

    public void setNo_of_likes(int no_of_likes) {
        this.no_of_likes = no_of_likes;
    }

    public int getNo_of_replies() {
        return no_of_replies;
    }

    public void setNo_of_replies(int no_of_replies) {
        this.no_of_replies = no_of_replies;
    }

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
