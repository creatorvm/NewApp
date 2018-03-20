package com.cvm.android.dancesterz.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cvm.android.dancesterz.dto.AuthResponse;
import com.cvm.android.dancesterz.dto.ErrorResponse;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

import org.springframework.web.client.HttpStatusCodeException;


public class LoginDao extends BaseDao {


    private static final String TAG = "LoginDao";
    ProgressDialog mProgressDialog;
    Context context;


    public  LoginDao(Context context, PreferencesManager preferencesManager, OnTaskCompleted parent) {
        this.parent = parent;
        this.preferencesManager = preferencesManager;
        this.context = context;
    }

    private LoginDao(OnTaskCompleted parent) {

    }


    public void checkLogin() {
        Log.d(TAG, "Inside check Login");
        new HttpRequestTask().execute();
    }

    public boolean checkLogin(String username, String password) {
        new HttpRequestTask().execute(username, password);
        return true;
    }

    private class HttpRequestTask extends AsyncTask<String, String, AuthResponse> {
        ErrorResponse result;

        @Override
        protected AuthResponse doInBackground(String... params) {
            Log.d(TAG, "Inside check Login -> doInBackground");
            try {
                if (params == null || params.length <= 0) {
                    TOKEN = preferencesManager.read(AppConstants.KEY_TOKEN);
                    REFRESH_TOKEN = preferencesManager.read(AppConstants.KEY_REFRESHTOKEN);
                    Log.d(TAG, "Token :" + TOKEN + "\n Refresh Token :" + REFRESH_TOKEN);
                    if (TOKEN == null || REFRESH_TOKEN == null) {
                        STATUS = false;
                        return null;
                    }
                    validateToken(TOKEN);
                } else if (params.length >= 2) {
                    try {
                        login(params[0], params[1]);
                    } catch (HttpStatusCodeException e) {
                        result = getErrorResponse(e.getResponseBodyAsString());
                        Log.e(TAG, result.getMessage());
                        return null;
                    }
                } else {
                    throw new IllegalArgumentException("No Argument for Authentication");
                }
                return authResponse;
            } catch (Exception e) {
                Log.e(TAG, "Error while Login Process", e);
                STATUS = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(AuthResponse authResponse) {
            super.onPostExecute(authResponse);
            Log.d(TAG, "Inside onPostExecute" + authResponse + "|" + STATUS);
            if (authResponse != null) {
//                mProgressDialog.dismiss();
                TOKEN = authResponse.getToken();
                if (authResponse.getRefreshToken() != null)
                    REFRESH_TOKEN = authResponse.getRefreshToken();

                preferencesManager.store(AppConstants.KEY_TOKEN, TOKEN);
                preferencesManager.store(AppConstants.KEY_REFRESHTOKEN, REFRESH_TOKEN);
                preferencesManager.store(AppConstants.KEY_USERID, authResponse.getUser().getUserId().toString());
                preferencesManager.store(AppConstants.KEY_USERNAME, authResponse.getUser().getUsername().toString());
            } else if (result != null) {
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "BAD CREDENTIALS");
                STATUS = false;
            }


            if (STATUS) {
                preferencesManager.store(AppConstants.KEY_LOGGEDIN, "true");
            } else {
                preferencesManager.store(AppConstants.KEY_LOGGEDIN, "false");

            }
            Log.d(TAG, "End of onPostExecute -> Invoking onTaskComplete of " + parent);
            parent.onTaskCompleted();
        }
    }
}

