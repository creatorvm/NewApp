package com.cvm.android.dancesterz.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.cvm.android.dancesterz.ui.listeners.ProfilePicResListener;
import com.cvm.android.dancesterz.utilities.URLs;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Devalopment-1 on 03-01-2018.
 */
public class ProfilePicUploadDao {

    ProfilePicResListener parent;
    String token;
    ProgressDialog mProgressDialog;
    Context con;
    private static final String TAG = "ProfilePicUploadDao";

    public ProfilePicUploadDao(ProfilePicResListener parent, Context con) throws Exception {
        Log.d(TAG, "In VideoUploadDao Dao");
        this.token = LoginDao.TOKEN;
        this.parent = parent;
        this.con = con;
        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<String, Void, Boolean> {
        private MultiValueMap<String, Object> formData;

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "In onPreExecute()");
            mProgressDialog = new ProgressDialog(con);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage("Uploading In Progress");
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.show();
            // populate the data to post
            Log.v(TAG, "Uploading File in server task Initiated");
            formData = new LinkedMultiValueMap<String, Object>();
            formData.add("file", new FileSystemResource(Environment.getExternalStorageDirectory().getPath() + File.separator + "demonuts" +
                    File.separator + "profile.jpg"));
        }

        @Override
        protected Boolean doInBackground(String... voids) {
            Boolean upload = false;
            try {
                // The URL for making the POST request
                Log.v(TAG, "In doInBackground Uploading File in server task");
                String end_url = URLs.URL_PROFILE_PIC;
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.add("X-Authorization", token);
                // Sending multipart/form-data
                requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                // Populate the MultiValueMap being serialized and headers in an HttpEntity object to use for the request
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);
                RestTemplate restTemplate = new RestTemplate(true);
                MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
                mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_OCTET_STREAM));
                restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
                upload = restTemplate.postForObject(end_url, requestEntity, Boolean.class);
                Log.v(TAG, "Video Upload id from server" + upload);
                //TODO Return the response body to display to the user
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return upload;
        }

        @Override
        protected void onPostExecute(Boolean response) {
            super.onPostExecute(response);
            mProgressDialog.dismiss();
            parent.profilePicUploaded(response);
            Log.v(TAG, "Calling Save video Id service task");
        }
    }
}
