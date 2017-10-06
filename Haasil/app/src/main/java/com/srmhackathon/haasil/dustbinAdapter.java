package com.srmhackathon.haasil;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class dustbinAdapter extends ArrayAdapter<dustbinPOJO> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();

    private  Context c;
    public dustbinAdapter(Context context, List<dustbinPOJO> objects) {
        super(context, 0, objects);
        c=  context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get item for selected view
        dustbinPOJO item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.waste_list_item, parent, false);
            // binding view parts to view holder
            viewHolder.number = (TextView) cell.findViewById(R.id.number);
            viewHolder.numberDesc = (TextView) cell.findViewById(R.id.numberDesc);
            viewHolder.percDesc = (TextView) cell.findViewById(R.id.percenDesc);

            viewHolder.perc = (TextView) cell.findViewById(R.id.percent);
            viewHolder.img = (ImageView) cell.findViewById(R.id.img);

            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }


        // bind data from selected element to view through view holder
        viewHolder.number.setText(item.getDustbinID());
        viewHolder.numberDesc.setText(item.getDustbinID());
        viewHolder.perc.setText(item.getPercentage()+"%");
        viewHolder.percDesc.setText(item.getPercentage()+"%");

        update(Float.parseFloat(item.getPercentage()), viewHolder);

        return cell;
    }


    private void update(float progressing, ViewHolder viewHolder) {
        if (progressing < 15)
            viewHolder.img.setImageResource(R.drawable.bin1);

        else if (progressing >= 15 && progressing < 35) {
            viewHolder.img.setImageResource(R.drawable.transition);
            ((TransitionDrawable) viewHolder.img.getDrawable()).startTransition(600);

        } else if (progressing >= 35 && progressing < 65) {
            viewHolder.img.setImageResource(R.drawable.trasn);
            ((TransitionDrawable) viewHolder.img.getDrawable()).startTransition(600);

        } else if (progressing >= 65 && progressing < 85) {
            viewHolder.img.setImageResource(R.drawable.trasn1);
            ((TransitionDrawable) viewHolder.img.getDrawable()).startTransition(600);
            android.support.v4.app.NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(c)
                            .setSmallIcon(R.drawable.applogo)
                            .setContentTitle("Smart Waste Management Application")
                            .setContentText("Your Bin Is Almost 75% Full");

            Intent notificationIntent = new Intent(c, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(c, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            // Add as notification
            NotificationManager manager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());

        } else if (progressing >= 85 && progressing <= 100) {
            viewHolder.img.setImageResource(R.drawable.trasn2);
            ((TransitionDrawable) viewHolder.img.getDrawable()).startTransition(600);
            android.support.v4.app.NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(c)
                            .setSmallIcon(R.drawable.applogo)
                            .setContentTitle("Smart Waste Management Application")
                            .setContentText("Your Bin Is Almost 100% Full");

            Intent notificationIntent = new Intent(c, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(c, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            // Add as notification
            NotificationManager manager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());

        }

    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }


    // View lookup cache
    private static class ViewHolder {
        TextView number;
        TextView numberDesc;
        TextView percDesc;
        TextView perc;
        ImageView img;
    }
}