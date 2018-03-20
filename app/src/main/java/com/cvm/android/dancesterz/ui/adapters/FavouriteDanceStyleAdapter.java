package com.cvm.android.dancesterz.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.ui.fragments.ProfileFragment;

import java.util.List;

import cn.refactor.library.SmoothCheckBox;

/**
 * Created by Development-2 on 27-12-2017.
 */

public class FavouriteDanceStyleAdapter extends RecyclerView.Adapter<FavouriteDanceStyleAdapter.MyViewHolder> {
    Context context = null;
    List<String> danceStyles = null;
    List<String> userFavouriteList = null;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fav_style_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String danceStyle = danceStyles.get(position);
        holder.favouriteDanceStyleTextView.setText(danceStyle);
        if (userFavouriteList.contains(danceStyle)) {
            holder.smoothCheckBox.setChecked(true);
        } else {
            holder.smoothCheckBox.setChecked(false);
        }

        holder.smoothCheckBox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox smoothCheckBox, boolean select) {
                if (select) {
                    userFavouriteList.add(danceStyles.get(position));
                } else {
                    userFavouriteList.remove(danceStyles.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return danceStyles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView favouriteDanceStyleTextView = null;
        SmoothCheckBox smoothCheckBox = null;
        public MyViewHolder(View view) {
             super(view);
            favouriteDanceStyleTextView = view.findViewById(R.id.favouriteDanceStyleTextView);
            smoothCheckBox = view.findViewById(R.id.scb);
        }
    }

    public FavouriteDanceStyleAdapter(Context context, List<String> danceStyles) {
        this.context = context;
        this.danceStyles = danceStyles;

        userFavouriteList = ProfileFragment.favouriteDanceSelected;
        if (userFavouriteList == null) {
            userFavouriteList = ProfileFragment.favouriteStyleDtos;
        }
    }

    // method to access in activity after updating selection
    public List<String> getFavouriteDanceList() {
        return userFavouriteList;
    }
}
