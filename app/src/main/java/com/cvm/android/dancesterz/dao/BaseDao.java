package com.cvm.android.dancesterz.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.cvm.android.dancesterz.dto.AuthRequest;
import com.cvm.android.dancesterz.dto.AuthResponse;
import com.cvm.android.dancesterz.dto.ErrorResponse;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;
import com.cvm.android.dancesterz.utilities.URLs;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by prasadprabhakaran on 2/18/18.
 */

public class BaseDao {


    public static String TOKEN;
    public static String REFRESH_TOKEN;
    static final String TAG = "BaseDao";
    AuthResponse authResponse;
    public static String TOKEN_ERROR = "TOKEN_EXPIRED";
    public static Boolean STATUS = true;
    ObjectMapper mapper = new ObjectMapper();
    PreferencesManager preferencesManager = null;
    OnTaskCompleted parent;

    protected HttpHeaders getHeaders(){
//        checkToken();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("X-Authorization", TOKEN);
        return requestHeaders;
    }

    protected RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        return restTemplate;
    }

    protected RestTemplate getRestTemplateWithMultiForm(){
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mJacksonConverter = new MappingJackson2HttpMessageConverter();
        mJacksonConverter.setSupportedMediaTypes(Arrays.asList(MediaType.MULTIPART_FORM_DATA,MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        //converter.setSupportedMediaTypes(Arrays.asList({MediaType.ALL}));
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        return restTemplate;
    }

    /**
     *
     * @param token
     * @return
     */
    protected AuthResponse validateToken(String token){
        Log.d(TAG, "validateToken Validate Token with Server : " + token);
        RestTemplate restTemplate = getRestTemplate();

        try {
            AuthRequest authRequest = new AuthRequest(token);
            authResponse = restTemplate.postForObject(URLs.URL_LOGIN_VALIDATE, authRequest, AuthResponse.class);
            Log.d(TAG,authResponse.getToken() + " | " + authResponse.getUser().getUserId());
            //refreshToken = authResponse.getRefreshToken();
            TOKEN        = authResponse.getToken();
            Log.d(TAG, "Got Response " + TOKEN);
            //Log.d(TAG,"Got Refresh Token " + refreshToken);
            return authResponse;
        }
        catch(HttpStatusCodeException e){
            ErrorResponse result =  getErrorResponse(e.getResponseBodyAsString());
            if(checkInValidToken(result))
                refreshToken(REFRESH_TOKEN);
            throw e;
        }
        catch (Exception e) {
            throw e;
        }
    }

    /**
     *
     * @param token
     * @return
     */
    protected AuthResponse refreshToken(String token){
        Log.d(TAG, "refreshToken Refresh Token with Server : " + token);
        RestTemplate restTemplate = getRestTemplate();

        try {
            AuthRequest authRequest = new AuthRequest(token);
            authResponse = restTemplate.postForObject(URLs.URL_LOGIN_REFRESH, authRequest, AuthResponse.class);
            Log.d(TAG,authResponse.getToken()+authResponse.getUser().getUserId());
            REFRESH_TOKEN = authResponse.getRefreshToken();
            TOKEN        = authResponse.getToken();
            Log.d(TAG, "Got Response " + TOKEN);
            Log.d(TAG,"Got Refresh Token " + REFRESH_TOKEN);
            STATUS = true;
            return authResponse;
        }
        catch(HttpStatusCodeException e){
            ErrorResponse result =  getErrorResponse(e.getResponseBodyAsString());
            Log.d(TAG, "Got Response refreshToken.HttpStatusCodeException " + result);
            throw e;
        }
        catch (Exception e) {
            this.STATUS = false;
            throw e;
        }
    }
    /**
     *
     * @param username
     * @param password
     * @return
     */
    protected AuthResponse login(String username, String password){
        Log.d(TAG, "login Login with Server");
        RestTemplate restTemplate = getRestTemplate();
        try {
            AuthRequest authRequest = new AuthRequest(username, password,"ANDROID","HOME");
            authResponse            = restTemplate.postForObject(URLs.URL_LOGIN, authRequest, AuthResponse.class);
            Log.d(TAG,authResponse.getToken()+authResponse.getUser().getUserId());
            REFRESH_TOKEN = authResponse.getRefreshToken();
            TOKEN        = authResponse.getToken();
            Log.d(TAG, "Got Response " + TOKEN);
            Log.d(TAG,"Got Refresh Token " + REFRESH_TOKEN);
            STATUS = true;
            return authResponse;
        }

        catch (HttpStatusCodeException e) {
            ErrorResponse result = getErrorResponse(e.getResponseBodyAsString());
            Log.d(TAG, "Bad credentialsss...HttpStatusCodeException " + result);
            Log.d(TAG, result.getMessage());
            throw e;
        }

    }

    protected boolean checkInValidToken(ErrorResponse result){
        return TOKEN_ERROR.equalsIgnoreCase(result.getErrorCode());
    }

    /**
     *
     * @param message
     * @return
     */
    protected ErrorResponse getErrorResponse(String message){
        try {
            ErrorResponse result = mapper.readValue(message, ErrorResponse.class);



            return result;
        } catch (IOException e) {
            Log.e(TAG,"Error While Procesing Error Message " + e.getMessage());
            return ErrorResponse.of("Unknown Error : " + e.getMessage(), "COMMON001");
        }
    }

    /**
     *
     * @param message
     * @param task
     * @return
     */
    protected ErrorResponse getErrorResponse(String message, AsyncTask task){

        try {
            ErrorResponse result = mapper.readValue(message,
                    ErrorResponse.class);
            Log.d(TAG,"Got Error " + result);
            if(checkInValidToken(result)){
                Log.d(TAG,"Re-trying for token " + REFRESH_TOKEN);
                new RefreshTokenTask().execute(task);
            }
            return result;
        } catch (IOException e) {
            Log.e(TAG,"Error While Procesing Error Message " + e.getMessage());
            return ErrorResponse.of("Unknown Error : " + e.getMessage(), "COMMON001");
        }
    }

    private class RefreshTokenTask extends AsyncTask<AsyncTask, Void, Void> {
        AsyncTask task = null;

        @Override
        protected Void doInBackground(AsyncTask[] asyncTasks) {
            if(asyncTasks.length>0 && asyncTasks[0] != null)
                task    = asyncTasks[0];
            refreshToken(REFRESH_TOKEN);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(task != null)
                task.execute();
        }
    }

    public boolean checkToken() {
        if(TOKEN == null){
            TOKEN = preferencesManager.read(AppConstants.KEY_TOKEN);
        }
        return true;
    }

    public boolean checkRefreshToken() {
        if(REFRESH_TOKEN == null){
            REFRESH_TOKEN = preferencesManager.read(AppConstants.KEY_REFRESHTOKEN);
        }
        return true;
    }

}
