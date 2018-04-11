package com.cvm.android.dancesterz.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.cvm.android.dancesterz.dto.AcceptChallengeDto;
import com.cvm.android.dancesterz.dto.AudioDto;
import com.cvm.android.dancesterz.dto.ChallegeResponse;
import com.cvm.android.dancesterz.dto.ChallengeDto;
import com.cvm.android.dancesterz.dto.ChallengeRequest;
import com.cvm.android.dancesterz.dto.ErrorResponse;
import com.cvm.android.dancesterz.dto.UserSummary;
import com.cvm.android.dancesterz.dto.UserVideoDto;
import com.cvm.android.dancesterz.dto.VideoDto;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;
import com.cvm.android.dancesterz.utilities.URLs;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devalopment-1 on 03-01-2018.
 */
public class MediaDao extends BaseDao {

    Long savedVideoId;
    Long saveVideoIdResponse;
    Long audioId;
    String filePath;

    String videoTitle;
    String videoDesc;
    String challengeTitle;
    String challengeDesc;
    Context context;

    ProgressDialog progressDialog;


    private static final String TAG = "VideoUploadDao";

    public MediaDao(PreferencesManager preferencesManager, Context context) {
        this.preferencesManager = preferencesManager;
        this.context = context;
    }

    public void upload(String title, String desc, Long audioId, String filePath, OnTaskCompleted callback) throws Exception {
        Log.d(TAG, "In VideoUploadDao Dao");
        this.audioId = audioId;
        this.filePath = filePath;
        this.videoTitle = title;
        this.videoDesc = desc;

        new VideoUploadTask(callback).execute();
    }

    public void createChallenge(String challengeName, String challengeDec, Long audioId, String filePath, OnTaskCompleted callback) throws Exception {
        this.videoTitle = challengeName;
        this.videoDesc = challengeDec;
        this.audioId = audioId;
        this.filePath = filePath;
        Log.d(TAG, "challengeName" + challengeName + "challengeDec" + challengeDec + "audioId" + audioId + "filePath" + filePath);
        new VideoUploadTask(callback, Boolean.TRUE).execute();
    }

    private class VideoUploadTask extends AsyncTask<String, Void, Long> {
        private MultiValueMap<String, Object> formData;
        OnTaskCompleted callback;
        Boolean isChallenge = Boolean.FALSE;

        VideoUploadTask(OnTaskCompleted callback) {
            this.callback = callback;
        }

        VideoUploadTask(OnTaskCompleted callback, Boolean isChallenge) {
            this.callback = callback;
            this.isChallenge = isChallenge;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Preparing ...");
            progressDialog.show();
            Log.d(TAG, "VideoUploadTask -> In onPreExecute()");
            // populate the data to post
            Log.v(TAG, "Uploading File in server task Initiated");
            formData = new LinkedMultiValueMap<String, Object>();
            formData.add("file", new FileSystemResource(filePath));
            formData.add("title", videoTitle);
            formData.add("desc",  videoDesc);
            formData.add("audioId", audioId.toString());
            formData.add("userId", preferencesManager.read(AppConstants.KEY_USERID));
        }

        @Override
        protected Long doInBackground(String... voids) {
            try {
                // The URL for making the POST request
                Log.d(TAG, "VideoUploadTask -> In doInBackground Uploading File in server task");

                HttpHeaders requestHeaders = getHeaders();
                requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

                // Populate the MultiValueMap being serialized and headers in an HttpEntity object to use for the request

                HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);
                RestTemplate restTemplate = getRestTemplateWithMultiForm();

                Log.d(TAG, "Video Upload id from server" + request + restTemplate);
                //restTemplate.setInterceptors(LoggingRequestInterceptor.getIntercepter());

                String resp = restTemplate.postForObject(URLs.URL_UPLOADVIDEO, request, String.class);

//                Log.d(TAG,  "Video Upload id from server "+ resp );
//                ResponseEntity<String> response = restTemplate.postForEntity("http://192.168.1.6:8002/global/test", request, String.class);
//                Log.d(TAG,  "Video Upload id from server "+ response +response.getBody() );

                savedVideoId = new Long(resp);

                Log.d(TAG, "Video Upload id from server" + savedVideoId);

            } catch (HttpStatusCodeException e) {
                e.printStackTrace();
                Log.d(TAG, "In HttpStatusCodeException " + e.getResponseBodyAsString());
                ErrorResponse result = getErrorResponse(e.getResponseBodyAsString(), new VideoUploadTask(callback));
            } catch (Exception e) {
                Log.e(TAG, "In Exception -> " + e.getMessage(), e);
                e.printStackTrace();
                throw e;
            }
            return savedVideoId;
        }

        @Override
        protected void onPostExecute(Long response) {
            super.onPostExecute(response);
            progressDialog.dismiss();
            Log.d(TAG, "VideoUploadTask -> onPostExecute Calling Save video Id service task");
            if (isChallenge) {
                new SaveVideoTask(null).execute(String.valueOf(response));
                new CreateChallengeTask(callback).execute();
            } else {
                new SaveVideoTask(callback).execute();
            }
        }
    }

    /**
     *
     */
    private class SaveVideoTask extends AsyncTask<String, Void, Boolean> {
        Object response;
        Boolean status;
        ResponseEntity<Object> responseEntity;

        OnTaskCompleted callback;

        SaveVideoTask(OnTaskCompleted callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(String... voids) {

            try {

                if (savedVideoId == null) {
                    throw new Exception("Error While Saving Video !!");
                }
                // The URL for making the POST request
                Log.d(TAG, "SaveVideoTask -> Save User Video task Initiated");
                UserVideoDto userVideo = new UserVideoDto();
                userVideo.setVideoId(savedVideoId);
                userVideo.setUserId(new Long(preferencesManager.read(AppConstants.KEY_USERID)));

                HttpEntity<UserVideoDto> request = new HttpEntity<UserVideoDto>(userVideo, getHeaders());
                RestTemplate restTemplate = getRestTemplate();

                //restTemplate.setInterceptors(LoggingRequestInterceptor.getIntercepter());

                responseEntity = restTemplate.exchange(URLs.URL_SAVEUSERVIDEO, HttpMethod.PUT, request, Object.class);
                response = responseEntity.getBody();
                if (response != null) {
                    if (response instanceof Boolean) {
                        status = (Boolean) response;
                    }
                }
                Log.d(TAG, "Saved video Id from server");
            } catch (HttpStatusCodeException e) {
                Log.e(TAG, "In HttpStatusCodeException " + e.getResponseBodyAsString());
                ErrorResponse result = getErrorResponse(e.getResponseBodyAsString());
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Video Saving");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean response) {
            super.onPostExecute(response);
            progressDialog.dismiss();
//            saveVideoIdResponse=response.getVideoId();
            Log.v(TAG, "SaveVideoTask -> Calling Create Challenge service task");
            if (this.callback != null) {
                this.callback.onTaskCompleted();
            }
        }
    }

    private class CreateChallengeTask extends AsyncTask<String, Void, ChallegeResponse> {
        ChallegeResponse challegeResponse;
        ResponseEntity<ChallegeResponse> responseEntity;
        OnTaskCompleted callback;

        CreateChallengeTask(OnTaskCompleted callback) {
            this.callback = callback;
        }

        @Override
        protected ChallegeResponse doInBackground(String... voids) {
            try {
                Log.v(TAG, " SaveVideoTask -> Create Challenge service task initiated");
                // The URL for making the POST request

                AcceptChallengeDto acceptChallengeDto = new AcceptChallengeDto();
                List<AcceptChallengeDto> responses = new ArrayList<>();
                responses.add(acceptChallengeDto);

                String dateTime = null;
                UserSummary userSummary = new UserSummary();
                userSummary.setId(new Long(preferencesManager.read(AppConstants.KEY_USERID)));

                VideoDto videoDto = new VideoDto();
                videoDto.setId(savedVideoId);
                videoDto.setAudioId(audioId);

                ChallengeDto challengeDto = new ChallengeDto();
                challengeDto.setName(videoTitle);
                challengeDto.setDesc(challengeDesc);
                challengeDto.setVideo(videoDto);
                challengeDto.setUserId(new Long(preferencesManager.read(AppConstants.KEY_USERID)));
                challengeDto.setOwner(userSummary);
                AudioDto audio = new AudioDto();
                audio.setId(audioId);
                challengeDto.setAudio(audio);

                ChallengeRequest cRequest = new ChallengeRequest();
                cRequest.setChallenge(challengeDto);

                RestTemplate restTemplate = getRestTemplate();

                HttpEntity<ChallengeRequest> entity = new HttpEntity<ChallengeRequest>(cRequest, getHeaders());
                responseEntity = restTemplate.exchange(URLs.URL_CREATECHALLENGE, HttpMethod.PUT, entity, ChallegeResponse.class);
                challegeResponse = responseEntity.getBody();

                Log.v(TAG, "Created Challenge  Completed " + challegeResponse);
            } catch (HttpStatusCodeException e) {
                Log.e(TAG, " SaveVideoTask -> In HttpStatusCodeException " + e.getResponseBodyAsString());
                ErrorResponse result = getErrorResponse(e.getResponseBodyAsString());
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return challegeResponse;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Creating Challenge");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ChallegeResponse response) {
            super.onPostExecute(response);
            progressDialog.dismiss();
            Log.v(TAG, "SaveVideoTask -> onPostExecute" + this.callback);
            if (this.callback != null) {
                this.callback.onTaskCompleted();
            }
        }
    }
}
