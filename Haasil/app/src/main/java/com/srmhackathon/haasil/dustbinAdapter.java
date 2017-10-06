package com.srmhackathon.haasil;


import android.content.Context;
import android.os.Build;
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


    public dustbinAdapter(Context context, List<dustbinPOJO> objects) {
        super(context, 0, objects);
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
        viewHolder.perc.setText(item.getPercentage());
        viewHolder.img.setImageResource(R.drawable.flash);



        return cell;
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
        TextView perc;
        ImageView img;
    }
}