package com.cvm.android.dancesterz.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.ui.listeners.ParameterListener;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


/**
 * Created by Devalopment-1 on 16-02-2018.
 */

public class VotingFragment extends Fragment {

    private static VotingFragment votingFragment = new VotingFragment();
    private final static String TAG = "VotingFragment";
    private VotingDao votingDao = new VotingDao();
    TextView no_votes_user1, no_votes_user2;
    TextView ownerNickName;
    TextView accepterNickName;
    ImageView ownerImageView;
    ImageView accepterImageView;
    PreferencesManager preferencesManager;
    Long candidateId;
    Button btnvoteUser1, btnvoteUser2;
    Long ChallengeResponseId;
    Long ChallengeID;
    Context context;
    protected BigInteger ownerVote;
    protected BigInteger responseVote;

    protected String ownerProfilePic;
    protected String accepterProfilePic;
    protected String ownerName;
    protected String accepterName;
    protected String challengeName;
    protected Long accepterId;
    protected Long voterId;

    ProgressDialog progressDialog = null;

    public static VotingFragment getInstance() {
        return votingFragment;
    }

    public static VotingFragment newInstance() {
        VotingFragment fragment = new VotingFragment();
        return fragment;
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

    public String getOwnerProfilePic() {
        return ownerProfilePic;
    }

    public void setOwnerProfilePic(String ownerProfilePic) {
        this.ownerProfilePic = ownerProfilePic;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getAccepterName() {
        return accepterName;
    }

    public void setAccepterName(String accepterName) {
        this.accepterName = accepterName;
    }

    public String getAccepterProfilePic() {
        return accepterProfilePic;
    }

    public void setAccepterProfilePic(String accepterProfilePic) {
        this.accepterProfilePic = accepterProfilePic;
    }

    public Long getAccepterId() {
        return accepterId;
    }

    public void setAccepterId(Long accepterId) {
        this.accepterId = accepterId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.voting_fragment, container, false);
         preferencesManager=new PreferencesManager(getActivity());
         voterId=Long.valueOf(preferencesManager.read(AppConstants.KEY_USERID));


        Log.i(TAG, "getResponseId" + ChallengeResponseId);
        Log.i(TAG, "candidateId" + candidateId);
        Log.i(TAG, "ChallengeID" + ChallengeID);
        Log.i(TAG, "getAccepterId" + getAccepterId());
        Log.i(TAG, "voterId" + voterId);


        no_votes_user1 = view.findViewById(R.id.no_votes_user1);
        no_votes_user2 = view.findViewById(R.id.no_votes_user2);
        btnvoteUser1 = view.findViewById(R.id.btnvoteuser1);
        btnvoteUser2 = view.findViewById(R.id.btnvoteuser2);

        ownerNickName = view.findViewById(R.id.ownerUserName);
        accepterNickName = view.findViewById(R.id.accepterUserName);
        ownerImageView = view.findViewById(R.id.ownerImageView);
        accepterImageView = view.findViewById(R.id.accepterImageView);

        addButtonListeners();

        no_votes_user1.setText(getOwnerVote() + "");
        no_votes_user2.setText(getResponseVote() + "");
        ownerNickName.setText(getOwnerName());
        accepterNickName.setText(getAccepterName());
        Log.e(TAG,"Accc"+getAccepterId()+"");

        Picasso.with(context).load(getOwnerProfilePic()).placeholder(R.drawable.baby)
                .transform(new CropCircleTransformation())
                .into(ownerImageView);
        Picasso.with(context).load(getAccepterProfilePic()).placeholder(R.drawable.baby)
                .transform(new CropCircleTransformation())
                .into(accepterImageView);
        return view;
    }

    OnTaskCompleted saveCallback = new OnTaskCompleted() {
        @Override
        public void onTaskCompleted() {
            getActivity().finish();
        }
    };

    @Override

    public void onDetach() {
        super.onDetach();
    }

    private void addButtonListeners() {
        btnvoteUser1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                try {
                    Log.i(TAG, "In first user vote Method button click");
                    Log.i(TAG, "ChallengeResponseId" + ChallengeResponseId);
                    Log.i(TAG, "candidateId" + candidateId);
                    Log.i(TAG, "ChallengeID" + ChallengeID);
                    Log.i(TAG, "voterId" + voterId);

                    votingDao = new VotingDao(voterId,candidateId, ChallengeID, ChallengeResponseId, Long.valueOf(1), Long.valueOf(0), new ParameterListener() {
                        @Override
                        public void OnTaskCompletedWithParameter(String votes) {
                            progressDialog.dismiss();
                            if (votes.equals("0")) {
                                Toast.makeText(getActivity(), "Sorry your vote has already placed", Toast.LENGTH_LONG).show();
                            } else {
//                                startActivity(new Intent(getActivity(), VotePopUp.class));

//                                String text = (String) no_votes_user1.getText();
//                                BigInteger voteBigInteger = new BigInteger(text);
////                                BigInteger responsBigInteger = new BigInteger(votes);
//                                BigInteger responsBigInteger = new BigInteger("1");
//                                Log.e(TAG,voteBigInteger+"  "+responsBigInteger+"");
//                                BigInteger add = voteBigInteger.add(responsBigInteger);
//                                Log.e(TAG,add+"  ");

                                no_votes_user1.setText(votes + "");
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG,e.getMessage());
                }
            }
        });
        btnvoteUser2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                try {
                    Log.i(TAG, "In second user vote Method button click");
                    Log.i(TAG, "ChallengeResponseId" + ChallengeResponseId);
                    Log.i(TAG, "ChallengeID" + ChallengeID);
                    Log.i(TAG, "AccepterId" +getAccepterId());
                    Log.i(TAG, "voterId" + voterId);

                    votingDao = new VotingDao(voterId,getAccepterId(), ChallengeID, ChallengeResponseId, Long.valueOf(0), Long.valueOf(1), new ParameterListener() {
                        @Override
                        public void OnTaskCompletedWithParameter(String votes) {
                            progressDialog.dismiss();

                            if (votes.equals("0")) {
                                Toast.makeText(getActivity(), "Sorry your vote has already placed", Toast.LENGTH_LONG).show();
                            } else {
//                                startActivity(new Intent(getActivity(), VotePopUp.class));
//                                String text = (String) no_votes_user1.getText();
//                                BigInteger voteBigInteger = new BigInteger(text);
////                                BigInteger responsBigInteger = new BigInteger(votes);
//                                BigInteger responsBigInteger = new BigInteger("1");
//                                BigInteger add = voteBigInteger.add(responsBigInteger);
                                no_votes_user2.setText(votes +"");
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG,e.getMessage());
                }
            }
        });
    }

//    public PreferencesManager getPreferencesManager() {
//        return preferencesManager;
//    }
//
//    public void setPreferencesManager(PreferencesManager preferencesManager) {
//        this.preferencesManager = preferencesManager;
//    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
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


}
