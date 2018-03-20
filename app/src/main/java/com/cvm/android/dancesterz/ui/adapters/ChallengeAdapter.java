package com.cvm.android.dancesterz.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.VotingDao;
import com.cvm.android.dancesterz.dto.AcceptChallengeDto;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.ui.HomeScreenActivity;
import com.cvm.android.dancesterz.ui.fragments.Exoplayer_Fragment;
import com.cvm.android.dancesterz.ui.fragments.VideoPlayer;
import com.cvm.android.dancesterz.ui.fragments.VotingFragment;
import com.cvm.android.dancesterz.ui.listeners.ItemClickListener;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Devalopment-1 on 11-01-2018.
 */
public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ViewHolder> {

    List<AcceptChallengeDto> alaccept;
    List<ChallengeDto> challengeList;
    Context context;
    private final static String TAG = "ChallengeAdapter";

    String str;
    AcceptChallengeDto selectedRecord;
    Long ChallengeResponseId;
    Long ChallengeID;
    protected BigInteger ownerVote, totalvotes;
    protected BigInteger responseVote;
    VotingDao votingDao;
    Long CandidateId;
    PreferencesManager preferencesManager;

    public ChallengeAdapter(List<AcceptChallengeDto> alaccept, Context context, Long CandidateId, List<ChallengeDto> challengeList) {
        this.alaccept = alaccept;
        this.context = context;
        this.CandidateId = CandidateId;
        this.challengeList = challengeList;
    }

    @Override
    public ChallengeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.votinglayout, viewGroup, false);
        ChallengeAdapter.ViewHolder viewHolder = new ChallengeAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChallengeAdapter.ViewHolder viewHolder, final int i) {
        final AcceptChallengeDto dto = alaccept.get(i);
        selectedRecord = alaccept.get(i);

        ChallengeResponseId = alaccept.get(i).getResponseId();

        ChallengeID = alaccept.get(i).getChallengeId();

//        ownerVote    = new BigInteger(alaccept.get(i).getChallengerVote().toString());
//
//        responseVote = new BigInteger(alaccept.get(i).getAccepterVote().toString());
//
//        totalvotes   = ownerVote.add(responseVote);
//
//        str          = totalvotes.toString();
//
//        Log.e(TAG,"totalvoteee"+ str+" "+ownerVote+" "+responseVote);
//        VotingFragment.getInstance().setCandidateId(CandidateId);
//        VotingFragment.getInstance().setAccepterId(alaccept.get(i).getAccepter().getId());
//        VotingFragment.getInstance().setChallengeID(ChallengeID);
//        VotingFragment.getInstance().setChallengeResponseId(ChallengeResponseId);
//        Log.e(TAG, ownerVote + ", " + responseVote);
//        VotingFragment.getInstance().setOwnerVote(ownerVote);
//        VotingFragment.getInstance().setResponseVote(responseVote);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                VideoPlayer.getInstance().setMediaUrl(Uri.parse(dto.getVideo().getSourcePath()));
//                VideoPlayer.getInstance().play();

                VotingFragment votingFragment = VotingFragment.newInstance();
                votingFragment.setAccepterId(alaccept.get(i).getAccepter().getId());
                votingFragment.setChallengeID(ChallengeID);
                votingFragment.setChallengeResponseId(ChallengeResponseId);
                votingFragment.setCandidateId(CandidateId);

                if (alaccept.get(i).getChallengerVote() != null) {
                    votingFragment.setOwnerVote(alaccept.get(i).getChallengerVote());
                } else {
                    votingFragment.setOwnerVote(new BigInteger("0"));
                }
                votingFragment.setAccepterId(alaccept.get(i).getAccepter().getId());
                Log.i(TAG,alaccept.get(i).getAccepter().getId()+"");
                if (alaccept.get(i).getAccepterVote() != null) {
                    votingFragment.setResponseVote(alaccept.get(i).getAccepterVote());
                } else {
                    votingFragment.setResponseVote(new BigInteger("0"));
                }

                if (alaccept.get(i).getAccepter().getId()!=0) {

                    votingFragment.setAccepterId(alaccept.get(i).getAccepter().getId());
                } else {
                    votingFragment.setAccepterId(Long.valueOf(0));
                }

                if (challengeList != null) {
                    if (challengeList.get(0).getOwner().getFirstName() != null ) {
                        votingFragment.setOwnerName(challengeList.get(0).getOwner().getFirstName());
                    } else {
                        votingFragment.setOwnerName("Challenger");
                    }
                    votingFragment.setOwnerProfilePic(challengeList.get(0).getOwner().getProfileImage());

                    if (alaccept.get(i).getAccepter().getFirstName() != null) {
                        votingFragment.setAccepterName(alaccept.get(i).getAccepter().getFirstName());
                    }
                    else
                    {
                        votingFragment.setAccepterName("Accepter");
                    }
                    votingFragment.setAccepterProfilePic(alaccept.get(i).getAccepter().getProfileImage());
                }
                ((HomeScreenActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.challengeVoteLinearLayout, VotingFragment.getInstance())
                        .commit();
                ((HomeScreenActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.challengeVideoLinearLayout, Exoplayer_Fragment.newInstance(dto.getVideo().getSourcePath()))
                        .commit();

                Log.e(TAG, "In first vote Method button click");
            }
        };
        viewHolder.vchallege_name.setText(challengeList.get(0).getName());

        Log.e(TAG, "b4" + str + " " + ownerVote + " " + responseVote);
        if (dto.getChallengerVote() != null && dto.getAccepterVote() != null) {

            ownerVote = new BigInteger(dto.getChallengerVote().toString());

            responseVote = new BigInteger(dto.getAccepterVote().toString());

            totalvotes = ownerVote.add(responseVote);

            str = totalvotes.toString();

            viewHolder.cvotes.setText(str +"");
        } else {
            viewHolder.cvotes.setText("0");
        }

        viewHolder.vbtnview.setOnClickListener(listener);

        if (dto.getAccepter().getFirstName() != null) {
            Log.e(TAG,"accept"+dto.getAccepter().getFirstName());
            viewHolder.nickname2.setText(dto.getAccepter().getFirstName());
        }
        else {
            viewHolder.nickname2.setText("Accepter");
        }
        if (challengeList.get(0).getOwner().getFirstName() != null ) {
            viewHolder.nickname1.setText(challengeList.get(0).getOwner().getFirstName());
        } else {
            viewHolder.nickname1.setText("Challenger");
        }
    }

    public Long getChallengeResponseId() {
        return ChallengeResponseId;
    }

    public void setChallengeResponseId(Long challengeResponseId) {
        ChallengeResponseId = challengeResponseId;
    }

    public Long getChallengeID() {
        return ChallengeID;
    }

    public void setChallengeID(Long challengeID) {
        ChallengeID = challengeID;
    }

    public BigInteger getOwnerVote() {
        return ownerVote;
    }

    public void setOwnerVote(BigInteger ownerVote) {
        this.ownerVote = ownerVote;
    }

    public BigInteger getResponseVote() {
        return responseVote;
    }

    public void setResponseVote(BigInteger responseVote) {
        this.responseVote = responseVote;
    }

    @Override
    public int getItemCount() {
        return alaccept.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView vimgThumbnail;
        public TextView cviews;
        public TextView cvotes;
        public TextView vchallege_name;
        public TextView nickname1;
        public TextView nickname2;
        Button vbtnview;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            vimgThumbnail = (ImageView) itemView.findViewById(R.id.vimg_thumbnail);
            cviews = (TextView) itemView.findViewById(R.id.ch_views);
            cvotes = (TextView) itemView.findViewById(R.id.ch_votes);
            vchallege_name = (TextView) itemView.findViewById(R.id.vchallenge_name);
            nickname1 = (TextView) itemView.findViewById(R.id.vnickname1);
            nickname2 = (TextView) itemView.findViewById(R.id.vnickname2);
            vbtnview = (Button) itemView.findViewById(R.id.vbtnview);

            //vbtnview.setOnClickListener(listener);
            //itemView.setOnClickListener(listener);

        }


    }

    public AcceptChallengeDto getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(AcceptChallengeDto selectedRecord) {
        this.selectedRecord = selectedRecord;
    }
}
