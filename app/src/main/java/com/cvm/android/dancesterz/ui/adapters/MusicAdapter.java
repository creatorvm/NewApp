package com.cvm.android.dancesterz.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dto.AudioDto;
import com.cvm.android.dancesterz.ui.MainActivity;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.ui.listeners.onItemClickListener;

import java.util.List;
import java.util.Locale;


/**
 * Created by Development-2 on 13-12-2017.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
    Context context = null;
    List<AudioDto> audioArrayList = null;
    onItemClickListener onItemClickListener = null;

    public MusicAdapter(Context context, List<AudioDto> audioArrayList) {
        this.context = context;
        this.audioArrayList = audioArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_player_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.songNameTextView.setText(audioArrayList.get(position).getTitle());
        holder.songSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, VideoPlayerActivity.class);
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(AppConstants.PLAY_AUDIO, audioArrayList.get(position).getSourcePath());
                intent.putExtra(AppConstants.AUDIO_ID, audioArrayList.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return audioArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView musicImageView = null;
        TextView songNameTextView = null;
        TextView sourcePath = null;
        Button songSelectButton = null;
        LinearLayout songsListLinearLayout = null;
        LinearLayout songsPlayLinearLayout = null;
        ImageView musicPlayImageView = null;
        ImageView musicPauseImageView = null;

        MyViewHolder(final View itemView) {
            super(itemView);
            musicImageView = itemView.findViewById(R.id.musicImageView);
            songNameTextView = itemView.findViewById(R.id.title);
            sourcePath = itemView.findViewById(R.id.sourcePath);
            songSelectButton = itemView.findViewById(R.id.songSelectButton);
            songsListLinearLayout = itemView.findViewById(R.id.songListLinearLayout);
            songsPlayLinearLayout = itemView.findViewById(R.id.songPlayLinearLayout);
            musicPlayImageView = itemView.findViewById(R.id.playImageView);
            musicPauseImageView = itemView.findViewById(R.id.pauseImageView);

            songSelectButton.setOnClickListener(this);
            songsListLinearLayout.setOnClickListener(this);
            songsPlayLinearLayout.setOnClickListener(this);
            musicPlayImageView.setOnClickListener(this);
            musicPauseImageView.setOnClickListener(this);
            sourcePath.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                onItemClickListener.onClick(view, position);
                if (view.getId() == R.id.songListLinearLayout) {
                    songsListLinearLayout.setVisibility(View.GONE);
                    songsPlayLinearLayout.setVisibility(View.VISIBLE);
                } else if (view.getId() == R.id.playImageView) {
                    musicPlayImageView.setVisibility(View.GONE);
                    musicPauseImageView.setVisibility(View.VISIBLE);
                } else if (view.getId() == R.id.pauseImageView) {
                    musicPlayImageView.setVisibility(View.VISIBLE);
                    musicPauseImageView.setVisibility(View.GONE);
                }
            }
        }
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
//        MusicPlayActivity.media.clear();
//        if (charText.length() == 0) {
//            MusicPlayActivity.media.addAll(audioArrayList);
//        } else {
//            for (AudioDto bm : audioArrayList) {
//                if (bm.getSourcePath().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    MusicPlayActivity.media.add(bm);
//                }
//            }
//        }
        notifyDataSetChanged();
    }
}
