package com.decodar.seeds;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.decodar.seeder.R;

import java.util.List;

/**
 * Created by indik on 9/1/2016.
 */
public class SeedAdaptor extends RecyclerView.Adapter<SeedAdaptor.SeedViewHolder> {

    private List<Seed> seedList;

    public SeedAdaptor(List<Seed> seedList){
        this.seedList = seedList;
    }

    @Override
    public SeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View seedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.seed_card, parent, false);
        return new SeedViewHolder(seedView);
    }

    @Override
    public void onBindViewHolder(SeedViewHolder holder, int position) {
        //what happens here :P
        Seed seed = seedList.get(position);
        holder.view_seedText.setText(seed.seed_text);
        holder.view_seedImage.setImageBitmap(seed.seed_image);
        holder.view_likeCount.setText("" + seed.no_of_likes);
        holder.view_replyCount.setText("" + seed.no_of_replies);
    }

    @Override
    public int getItemCount() {
        return seedList.size();
    }

    public static class SeedViewHolder extends RecyclerView.ViewHolder{
        protected ImageView view_seedImage;
        protected TextView view_seedText;
        protected TextView view_likeCount;
        protected TextView view_replyCount;


        public SeedViewHolder(View itemView) {
            super(itemView);
            view_seedImage = (ImageView)itemView.findViewById(R.id.seed_thumbnail);
            view_seedText = (TextView)itemView.findViewById(R.id.seed_text);
            view_likeCount = (TextView)itemView.findViewById(R.id.seed_like_count);
            view_replyCount = (TextView)itemView.findViewById(R.id.seed_replies_count);
        }
    }
}

