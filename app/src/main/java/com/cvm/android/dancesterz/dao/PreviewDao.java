package com.cvm.android.dancesterz.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;
import com.cvm.android.dancesterz.utilities.URLs;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by Devalopment-1 on 16-01-2018.
 */
public class PreviewDao {

    Long               challengeId;
    Long               challengeVideoId;
    Long               audioId;
    String             token;
    PreferencesManager preferencesManager;
    OnTaskCompleted parent;
    String             filename;
    Context            con;
    private String     response;
    private String TAG = "PreviewDao";

    public String getResponse() {
        return response;
    }

    public PreviewDao(PreferencesManager preferencesManager) {
        Log.d(TAG,"In Constructor");
        this.preferencesManager = preferencesManager;

    }

    public void preview(Long challengeId,Long challengeVideoId, Long audioId, String filepath, OnTaskCompleted parent) {
        Log.d(TAG,"In method getVideoDatas");
        this.challengeId        = challengeId;
        this.challengeVideoId   = challengeVideoId;
        this.audioId            = audioId;
        this.parent             = parent;
        this.preferencesManager = preferencesManager;
        this.filename           = filepath;
        this.token              = this.preferencesManager.read(AppConstants.KEY_TOKEN);

        Log.d(TAG,"Recorded Video Location " + filepath + "|" +this.filename );

        Log.d(TAG,"Challenge Id  "+challengeId+"Challenge Video Id "+challengeVideoId+"Audioid "+audioId);
        new PreviewMixedTask().execute();
    }

    private class PreviewMixedTask extends AsyncTask<String, Void, String> {
        private MultiValueMap<String, Object> formData;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG,"In preexecute() " + filename);
            Resource resource = new FileSystemResource(filename);
            // populate the data to post
            formData = new LinkedMultiValueMap<String, Object>();
            formData.add("file", resource);
            formData.add("challengeId", challengeId.toString());
            formData.add("videoId", challengeVideoId.toString());
            formData.add("audioId", audioId.toString());
        }

        @Override
        protected String doInBackground(String... voids) {
            try {
                // The URL for making the POST request
                Log.d(TAG,"In do in background()");
                String end_url = URLs.URL_PREVIEW;
                HttpHeaders requestHeaders  = new HttpHeaders();
                RestTemplate restTemplate   = new RestTemplate(true);

                requestHeaders.add("X-Authorization",token);
                requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
                // Populate the MultiValueMap being serialized and headers in an HttpEntity object to use for the request
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);
                // Create a new RestTemplate instance

                MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
                mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_OCTET_STREAM));

                restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
                response= restTemplate.postForObject(end_url, requestEntity, String.class);
                Log.d(TAG,  "mix video Task Completed ");
                //TODO Return the response body to display to the user
            } catch (Exception e) {
                Log.e("PreviewDao", e.getMessage(), e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            Log.d(TAG,"Loading Mixed video file from server");

            try {
//                FileOutputStream fileOuputStream=null;
                //fileOuputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "previewvideo.mp4"));
                //fileOuputStream.write(response);
                parent.onTaskCompleted();
                Log.i(TAG, "filecreated" + response);
//                if(new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "previewvideo.mp4").exists()){
//                    Log.i(TAG, "file exists @"+Environment.getExternalStorageDirectory().getPath() + File.separator + "previewvideo.mp4");
//                }

                //fileOuputStream.close();
            }
            catch (Exception e) {
                Log.e(TAG, "Exception in PreviewDao", e);
            }
        }
    }
}
