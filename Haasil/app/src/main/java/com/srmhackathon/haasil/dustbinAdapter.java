package com.srmhackathon.haasil;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ravi on 06-10-2017.
 */

public class dustbinAdapter extends RecyclerView.Adapter<dustbinAdapter.VH> {

    List<dustbinPOJO> dustList;
    public static Context context;
    static int config;

    public dustbinAdapter(Context context, List<dustbinPOJO> dustList, int config) {
        this.dustList = dustList;
        this.context = context;
        this.config = config;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        VH holder = new VH(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.setData(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title, website, time;
        WebView image;
        LikeButton likeButton;

        public VH(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.list_item_class_card);
            title = (TextView) itemView.findViewById(R.id.title);
            website = (TextView) itemView.findViewById(R.id.website);
            time = (TextView) itemView.findViewById(R.id.time);
            image = (WebView) itemView.findViewById(R.id.image);
            likeButton = (LikeButton) itemView.findViewById(R.id.star_button);

        }

        public void setData(final NewsPOJO data) {

            title.setText(data.getTITLE());
            website.setText(data.getPUBLISHER());
            time.setText(data.getTIMESTAMP());
            if (config == Configuration.ORIENTATION_PORTRAIT)
                image.loadUrl("https://logo.clearbit.com/" + data.getHOSTNAME() + "?size=100");
            else
                image.loadUrl("https://logo.clearbit.com/" + data.getHOSTNAME() + "?size=90");

            if(db.getNews(data))
                likeButton.setLiked(true);
            else
                likeButton.setLiked(false);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(context, Uri.parse(data.getURL()));
                }
            });


            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(context, Uri.parse(data.getURL()));
                }
            });

            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(context, Uri.parse(data.getURL()));
                }
            });

            likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    Snackbar.make(likeButton.getRootView(),"Added To Liked List",Snackbar.LENGTH_SHORT).show();
                    db.addContact(data);
                    if(context instanceof MainActivity)
                        ((MainActivity)context).refresh();
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    Snackbar.make(likeButton.getRootView(),"Removed from Liked List",Snackbar.LENGTH_SHORT).show();
                    db.deleteContact(data);

                    if(context instanceof MainActivity)
                        ((MainActivity)context).refresh();
                }
            });
        }
    }


}
