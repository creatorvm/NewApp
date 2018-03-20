package com.cvm.android.dancesterz.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cvm.android.dancesterz.dto.AudioDto;
import com.cvm.android.dancesterz.dto.BaseMedia;
import com.cvm.android.dancesterz.dto.MediaRequest;
import com.cvm.android.dancesterz.dto.MediaResponse;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.URLs;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Devalopment-1 on 28-12-2017.
 */

public class JukeboxDao extends BaseDao{
    static final String TAG = "JukeboxDao";
    MediaRequest mediaRequest;
    List<AudioDto> media;
    Context context = null;
    OnTaskCompleted parent;

    public JukeboxDao(Context context, List<AudioDto> media,OnTaskCompleted  parent) {
        this.media = media;
        this.context = context;
        this.parent = parent;

    }

    public void searchAudio(String text){
        mediaRequest = new MediaRequest("audio", text);
        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, MediaResponse> {
        private static final String TAG = "REST RESPONSE";

        @Override
        protected MediaResponse doInBackground(Void... params) {
            media.clear();
            MediaResponse mediaResponse = null;
            try {
                HttpHeaders headers=getHeaders();
                headers.add("Content-Type", "application/json");
                RestTemplate restTemplate = getRestTemplate();
                HttpEntity<MediaRequest> request = new HttpEntity<MediaRequest>(mediaRequest, headers);
                mediaResponse = restTemplate.postForObject(URLs.URL_GETJUKEBOX, request, MediaResponse.class);
                Log.i(TAG,mediaResponse.toString());
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return mediaResponse;
        }

        @Override
        protected void onPostExecute(MediaResponse mediaResponse) {
            super.onPostExecute(mediaResponse);
            media.clear();
            if(mediaResponse!=null){
                if(mediaResponse.getMedia() != null && !mediaResponse.getMedia().isEmpty()) {
                    AudioDto dto = null;
                    for (BaseMedia baseMedia : mediaResponse.getMedia()) {
                        //TODO: Server Side Defect - Correct after the Server fix
                        dto = new AudioDto();
                        dto.setTitle(baseMedia.getTitle());
                        dto.settrack("Test Track");
                        dto.setSourcePath(baseMedia.getSourcePath());
                        dto.setId(baseMedia.getId());
                        media.add(dto);
                        Log.d(TAG, baseMedia.getSourcePath());
                        Log.d(TAG, "Audio id : " + baseMedia.getId());
                        Log.d(TAG, baseMedia.getTitle());
                    }
                }
                parent.onTaskCompleted();
            }
        }
    }
}
