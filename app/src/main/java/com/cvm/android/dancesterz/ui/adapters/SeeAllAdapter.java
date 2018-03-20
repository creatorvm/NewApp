package com.cvm.android.dancesterz.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.ui.listeners.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Devalopment-1 on 28-02-2018.
 */

public class SeeAllAdapter extends RecyclerView.Adapter<SeeAllAdapter.MyViewHolder> {
    List<ChallengeDto> alhotchallenge;
    Context context;

    public SeeAllAdapter(List<ChallengeDto> alhotchallenge, Context context) {
        this.alhotchallenge = alhotchallenge;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.seeall_layout, parent, false);
        return new SeeAllAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChallengeDto challengeDto = alhotchallenge.get(position);
        holder.sa_nickname.setText(challengeDto.getOwner().getNickName());
        holder.sa_challege_name.setText(challengeDto.getName());
        holder.sa_votes.setText(challengeDto.getTotalVotes().toString());
        Picasso.with(context).load(alhotchallenge.get(position).getVideo().getThumbnail()).placeholder(R.drawable.t2)// Place holder image from drawable folder
                .error(R.drawable.t2).resize(110, 110).centerCrop()
                .into(holder.sa_danceThumbnail);
//        Picasso.with(context).load(alhotchallenge.get(position).getOwner().getProfileImage()).placeholder(R.drawable.t2)// Place holder image from drawable folder
//                .transform(new CropCircleTransformation())
//                .into(holder.sa_challenger_pic);
        Picasso.with(context).load(alhotchallenge.get(position).getOwner().getProfileImage()).placeholder(R.drawable.t2).into(holder.sa_challenger_pic);
    }

    @Override
    public int getItemCount() {
        return alhotchallenge.size();
    }

    public void add(ChallengeDto s) {
        alhotchallenge.add(s);
        notifyDataSetChanged();
    }

    /*
    CLEAR DATA FROM ADAPTER
     */
    public void clear() {
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView sa_danceThumbnail;
        ImageView sa_challenger_pic;
        TextView sa_views;
        TextView sa_votes;
        TextView sa_nickname;
        TextView sa_challege_name;
        private ItemClickListener clickListener;

        public MyViewHolder(View view) {
            super(view);
            sa_danceThumbnail = (ImageView) itemView.findViewById(R.id.seeall_thumbnail);
            sa_challenger_pic = (ImageView) itemView.findViewById(R.id.seeall_challenger);
            sa_views = (TextView) itemView.findViewById(R.id.sa_views);
            sa_votes = (TextView) itemView.findViewById(R.id.sa_votes);
            sa_nickname = (TextView) itemView.findViewById(R.id.sa_nickname);
            sa_challege_name = (TextView) itemView.findViewById(R.id.sa_challenge_name);
            itemView.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }
    }
}
