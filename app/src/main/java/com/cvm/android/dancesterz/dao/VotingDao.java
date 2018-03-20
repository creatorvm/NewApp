package com.cvm.android.dancesterz.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.cvm.android.dancesterz.dto.ChallegeResponse;
import com.cvm.android.dancesterz.dto.ErrorResponse;
import com.cvm.android.dancesterz.dto.VoteDto;
import com.cvm.android.dancesterz.dto.VoteRequest;
import com.cvm.android.dancesterz.ui.listeners.ParameterListener;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;
import com.cvm.android.dancesterz.utilities.URLs;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


/**
 * Created by Devalopment-1 on 09-02-2018.
 */

public class VotingDao extends BaseDao {
    Long voterId;
    Long candidateId;
    Long challengeId;
    Long challengeResponseId;
    Long ownerVote;
    Long responseVote, totalvotes;
    ParameterListener parent;
    String errmsg;
    private static final String TAG = "VotingDao";

    public VotingDao() {

    }

    public VotingDao(Long voterId,Long candidateId, Long challengeId, Long challengeResponseId, Long ownerVote, Long responseVote, ParameterListener parent) throws Exception {
        Log.v(TAG, " Constructor Invoked");
        this.candidateId = candidateId;
        this.challengeId = challengeId;
        this.challengeResponseId = challengeResponseId;
        this.parent = parent;
        this.ownerVote = ownerVote;// (1 for Ist user ,0 for II user and vice versa)
        this.responseVote = responseVote;
        this.voterId =voterId;
        Log.d(TAG, "voterId  " + voterId + " candidateId " + candidateId + "challengeId " + challengeId + "challengeResponseId " + challengeResponseId + "ownerVote " + ownerVote + "responseVote " + responseVote);
        new VotingTask().execute();
    }

    public void getVotesmethod(Long totalvotes) {
        Log.d(TAG, "In getVotesmethod");
        this.totalvotes = totalvotes;
    }

    private class VotingTask extends AsyncTask<String, Void, ChallegeResponse> {
        ChallegeResponse challegeResponse;
        ResponseEntity<ChallegeResponse> responseEntity;

        @Override
        protected ChallegeResponse doInBackground(String... voids) {
            try {
                Log.v(TAG, " Voting service task initiated");
                // The URL for making the POST request
                HttpHeaders headers = getHeaders();
                headers.add("Content-Type", "application/json");
                VoteDto voteDto = new VoteDto(voterId, candidateId, challengeId, challengeResponseId, ownerVote, responseVote);
                VoteRequest voteRequest = new VoteRequest();
                voteRequest.setVote(voteDto);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                HttpEntity<VoteRequest> entity = new HttpEntity<VoteRequest>(voteRequest, headers);
                responseEntity = restTemplate.exchange(URLs.URL_VOTE, HttpMethod.PUT, entity, ChallegeResponse.class);
                challegeResponse = responseEntity.getBody();
                Log.v(TAG, " Voting service task completed tot votes");
                return challegeResponse;
            } catch (HttpStatusCodeException e) {
                ErrorResponse result = getErrorResponse(e.getResponseBodyAsString());
                errmsg = result.getMessage();
                Log.e(TAG, result.getMessage());
                return null;
//                Log.e(TAG, e.getMessage(), e);
            }

        }

        @Override
        protected void onPostExecute(ChallegeResponse response) {
            super.onPostExecute(response);
            if (response != null) {
                totalvotes = response.getChallenges().get(0).getTotalVotes();

            }
            else {

                totalvotes = Long.valueOf(0);
            }
            parent.OnTaskCompletedWithParameter("" + totalvotes);
//             Log.v(TAG,  " Votes"+totalvotes);

        }
    }
}
