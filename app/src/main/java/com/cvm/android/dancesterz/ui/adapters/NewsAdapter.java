package com.cvm.android.dancesterz.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.ui.MainActivity;
import com.cvm.android.dancesterz.ui.listeners.ItemClickListener;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Development-2 on 15-03-2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean isLoadingAdded = false;
    List<ChallengeDto> alhotchallenge;
    Context context;
    String heading;
    private static final int ITEM = 0;
    private static final int LOADING = 1;

//    public NewsAdapter(List<ChallengeDto> alhotchallenge) {
//        this.alhotchallenge = alhotchallenge;
//    }

    public NewsAdapter(Context context, String heading, List<ChallengeDto> alhotchallenge) {
        this.context = context;
        this.heading = heading;
        this.alhotchallenge = alhotchallenge;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case ITEM:
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.newslayout, viewGroup, false);
                viewHolder = new NewsAdapter.ViewHolder(v);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, viewGroup, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                if (viewHolder instanceof NewsAdapter.ViewHolder) {
                    NewsAdapter.ViewHolder holder = (ViewHolder) viewHolder;
                    holder.challege_name.setText(alhotchallenge.get(position).getName());
                    holder.nickname.setText(alhotchallenge.get(position).getOwner().getFirstName());
                    holder.votes.setText(alhotchallenge.get(position).getTotalVotes() + "");
                    Picasso.with(context).load(alhotchallenge.get(position).getVideo().getThumbnail()).placeholder(R.drawable.t4)
                            .error(R.drawable.t2).resize(110, 110).centerCrop()
                            .into(holder.dimgThumbnail);// Place holder image from drawable folder
                    if (alhotchallenge.get(position).getVideo().getThumbnail() != null) {
                        Picasso.with(context).load(alhotchallenge.get(position).getVideo().getThumbnail()).into(holder.dimgThumbnail);
                    } else {
                        Picasso.with(context).load(R.drawable
                                .t2).into(holder.dimgThumbnail);
                    }
                    holder.setClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            if (isLongClick) {
                                Toast.makeText(context, "#" + position + " - " + alhotchallenge.get(position).getChallengeId() + " ", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, ChallengeVoteActivity.class);
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra(AppConstants.PLAY_VIDEO, alhotchallenge.get(position).getSourcePath());
                                intent.putExtra(AppConstants.CHALLENGE_ID, alhotchallenge.get(position).getChallengeId());
                                intent.putExtra(AppConstants.CHALLENGE_VIDEOID, alhotchallenge.get(position).getVideo().getId());
                                intent.putExtra(AppConstants.TOTAL_VOTES, alhotchallenge.get(position).getTotalVotes());
                                intent.putExtra(AppConstants.CHALLENGE_AUDIOPATH, alhotchallenge.get(position).getAudio().getSourcePath());
                                intent.putExtra(AppConstants.OWNER_USERID, alhotchallenge.get(position).getOwner().getId());
                                intent.putExtra(AppConstants.CHAUDIO_ID, alhotchallenge.get(position).getAudio().getId());
//                    Log.i("HotChAdapter", alhotchallenge.get(position).getResponses().get(position).getResponseId().toString());
//                    intent.putExtra(AppConstants.CHALLENGE_VAUDIOID,alhotchallenge.get(position).get);
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, alhotchallenge.get(position).getChallengeId() + "", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, ChallengeVoteActivity.class);
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra(AppConstants.PLAY_VIDEO, alhotchallenge.get(position).getVideo().getSourcePath());
                                intent.putExtra(AppConstants.CHALLENGE_ID, alhotchallenge.get(position).getChallengeId());
                                intent.putExtra(AppConstants.CHALLENGE_VIDEOID, alhotchallenge.get(position).getVideo().getId());
                                intent.putExtra(AppConstants.OWNER_USERID, alhotchallenge.get(position).getOwner().getId());
                                intent.putExtra(AppConstants.CHALLENGE_AUDIOPATH, alhotchallenge.get(position).getAudio().getSourcePath());
                                intent.putExtra(AppConstants.CHAUDIO_ID, alhotchallenge.get(position).getAudio().getId());
                                intent.putExtra(AppConstants.CHALLENGE_NAME, alhotchallenge.get(position).getName());
//                    intent.putExtra(AppConstants.CHALLENGE_VAUDIOID,alhotchallenge.get(position).get);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        }
                    });
                }
                break;
            case LOADING:
//                Do nothing
                break;
        }
    }


    @Override
    public int getItemCount() {
        return alhotchallenge.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public ImageView dimgThumbnail;
        public TextView views;
        public TextView votes;
        public TextView challege_name;
        public TextView nickname;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            dimgThumbnail = (ImageView) itemView.findViewById(R.id.nimg_thumbnail);
            views = (TextView) itemView.findViewById(R.id.nviews);
            votes = (TextView) itemView.findViewById(R.id.nvotes);
            challege_name = (TextView) itemView.findViewById(R.id.nchallenge_name);
            nickname = (TextView) itemView.findViewById(R.id.nnickname);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

    //Helpers
    public void add(ChallengeDto challengeDto) {
        alhotchallenge.add(challengeDto);
        notifyItemInserted(alhotchallenge.size() - 1);
    }

    public void addAll(List<ChallengeDto> challengeDtoList) {
//        for (ChallengeDto challengeDto : challengeDtoList) {
//            add(challengeDto);
//            alhotchallenge.add(challengeDto);
        alhotchallenge.addAll(challengeDtoList);
        notifyItemInserted(alhotchallenge.size() - 1);
//        }
    }

    public void remove(ChallengeDto challengeDto) {
        int position = alhotchallenge.indexOf(challengeDto);
        if (position > -1) {
            alhotchallenge.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ChallengeDto());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = alhotchallenge.size() - 1;
        ChallengeDto challengeDto = getItem(position);

        if (challengeDto != null) {
            alhotchallenge.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ChallengeDto getItem(int position) {
        return alhotchallenge.get(position);
    }

    /**
     * Main list's content ViewHolder
     */

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
