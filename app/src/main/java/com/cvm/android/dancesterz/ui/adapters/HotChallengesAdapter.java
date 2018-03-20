package com.cvm.android.dancesterz.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.ui.ChallengeVoteActivity;
import com.cvm.android.dancesterz.ui.HomeScreenActivity;
import com.cvm.android.dancesterz.ui.MainActivity;
import com.cvm.android.dancesterz.ui.fragments.ChallengeResponse;
import com.cvm.android.dancesterz.ui.fragments.ChallengeVoteFragment;
import com.cvm.android.dancesterz.ui.listeners.ItemClickListener;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Devalopment-1 on 19-12-2017.
 */
public class HotChallengesAdapter extends RecyclerView.Adapter<HotChallengesAdapter.ViewHolder> {
    List<ChallengeDto> alhotchallenge;
    PreferencesManager preferencesManager;
    Context context;
    String heading;

    public HotChallengesAdapter(List<ChallengeDto> alhotchallenge, Context context, String heading) {
        this.alhotchallenge = alhotchallenge;
        this.context = context;
        this.heading = heading;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboardlayout, viewGroup, false);
        HotChallengesAdapter.ViewHolder viewHolder = new HotChallengesAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        //        viewHolder.views.setText(alhotchallenge.get(i).getH_views());
        viewHolder.challege_name.setText(alhotchallenge.get(i).getName());
        viewHolder.votes.setText(alhotchallenge.get(i).getTotalVotes() + "");
        viewHolder.nickname.setText(alhotchallenge.get(i).getOwner().getFirstName());
//      viewHolder.nickname.setText(alhotchallenge.get(i).getChallengeId()+"");
        Picasso.with(context).load(alhotchallenge.get(i).getVideo().getThumbnail()).placeholder(R.drawable.t2)// Place holder image from drawable folder
                .error(R.drawable.t2).resize(110, 110).centerCrop()
                .into(viewHolder.dimgThumbnail);
        if (alhotchallenge.get(i).getVideo().getThumbnail() != null) {
            Picasso.with(context).load(alhotchallenge.get(i).getVideo().getThumbnail()).into(viewHolder.dimgThumbnail);
        } else {
            Picasso.with(context).load(R.drawable
                    .t2).into(viewHolder.dimgThumbnail);
        }
        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
//                    Toast.makeText(context, "#" + position + " - " + alhotchallenge.get(position).getChallengeId() + " ", Toast.LENGTH_SHORT).show();
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
//                    intent.putExtra(AppConstants.PLAY_VIDEO, alhotchallenge.get(position).getVideo().getSourcePath());
//                    intent.putExtra(AppConstants.CHALLENGE_ID, alhotchallenge.get(position).getChallengeId());
//                    intent.putExtra(AppConstants.CHALLENGE_VIDEOID, alhotchallenge.get(position).getVideo().getId());
//                    intent.putExtra(AppConstants.OWNER_USERID, alhotchallenge.get(position).getOwner().getId());
//                    intent.putExtra(AppConstants.CHALLENGE_AUDIOPATH, alhotchallenge.get(position).getAudio().getSourcePath());
//                    intent.putExtra(AppConstants.CHAUDIO_ID, alhotchallenge.get(position).getAudio().getId());
//                    intent.putExtra(AppConstants.CHALLENGE_NAME, alhotchallenge.get(position).getName());
////                    intent.putExtra(AppConstants.CHALLENGE_VAUDIOID,alhotchallenge.get(position).get);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                    ((HomeScreenActivity) context).changeFragments(ChallengeVoteFragment.newInstance(alhotchallenge.get(position).getVideo().getSourcePath(),
//                            alhotchallenge.get(position).getChallengeId(), alhotchallenge.get(position).getVideo().getId(),
//                            alhotchallenge.get(position).getOwner().getId(), alhotchallenge.get(position).getAudio().getSourcePath(),
//                            alhotchallenge.get(position).getAudio().getId(), alhotchallenge.get(position).getName()));

                    ((HomeScreenActivity) context).changeFragments(ChallengeResponse.newInstance(alhotchallenge.get(position).getVideo().getSourcePath(),
                            alhotchallenge.get(position).getChallengeId(), alhotchallenge.get(position).getVideo().getId(),
                            alhotchallenge.get(position).getOwner().getId(), alhotchallenge.get(position).getAudio().getSourcePath(),
                            alhotchallenge.get(position).getAudio().getId(), alhotchallenge.get(position).getName()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("Count", alhotchallenge.size() + "");
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
            dimgThumbnail = (ImageView) itemView.findViewById(R.id.dimg_thumbnail);
            views = (TextView) itemView.findViewById(R.id.dviews);
            votes = (TextView) itemView.findViewById(R.id.dvotes);
            challege_name = (TextView) itemView.findViewById(R.id.dchallenge_name);
            nickname = (TextView) itemView.findViewById(R.id.dnickname);

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
}
