package com.cvm.android.dancesterz.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.cvm.android.dancesterz.dto.ErrorResponse;
import com.cvm.android.dancesterz.dto.Role;
import com.cvm.android.dancesterz.dto.User;
import com.cvm.android.dancesterz.dto.UserAuth;
import com.cvm.android.dancesterz.dto.UserCreateRequest;
import com.cvm.android.dancesterz.dto.UserResponse;
import com.cvm.android.dancesterz.ui.listeners.ParameterListener;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.URLs;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devalopment-1 on 05-12-2017.
 */

public class SignUpDao extends BaseDao {

    String token;

    //For Signup
    String username, password, confirm_password, email;
    UserResponse userResponse1;

    //For Profile
    String firstName = null;
    String nickName = null;
    String secondName = null;
    String dob = null;
    String gender = null;
    String contactNumber = null;
    String country = null;
    String state = null;
    String region = null;
    List<String> userDanceStyle = null;
    List<String> userDanceStyleWholeList = null;
    String process = null;
    String userid = null;
    ParameterListener parent;
    static UserResponse userResponse = null;

    public SignUpDao(String nickName, String username, String password, String confirm_password, String email, UserResponse userResponse1, List<String> userDanceStyle, List<String> userDanceStyleWholeList, String firstName, String secondName, String dob, String gender, String contactNumber, String country, String state, String region, String process, String userid, ParameterListener parent) {
        this.nickName = nickName;
        this.username = username;
        this.password = password;
        this.confirm_password = confirm_password;
        this.email = email;
        this.userResponse1 = userResponse1;
        this.userDanceStyle = userDanceStyle;
        this.userDanceStyleWholeList = userDanceStyleWholeList;
        this.firstName = firstName;
        this.secondName = secondName;
        this.dob = dob;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.country = country;
        this.state = state;
        this.region = region;
        this.process = process;
        this.parent = parent;
        this.userid = userid;
        this.token = LoginDao.TOKEN;
        new HttpRequestTask().execute(process);
    }

    private class HttpRequestTask extends AsyncTask<String, Void, UserResponse> {

        private static final String TAG = "RESPONSE";
        ErrorResponse result;

        @Override
        protected UserResponse doInBackground(String... params) {
            RestTemplate restTemplate = getRestTemplate();
            try {
                Role role = new Role();
                role.setId(1);
                role.setName("Challenge");
                List<Role> roleList = new ArrayList<>();
                roleList.add(role);
                HttpHeaders requestHeaders = new HttpHeaders();
                if (params[0].equals(URLs.PROFILE)) {
                    requestHeaders = getHeaders();
                    requestHeaders.add("Content-Type", "application/json");
                    HttpEntity<String> request = new HttpEntity<String>(requestHeaders);
                    Log.e("Profile", params[0] + username);
                    ResponseEntity<UserResponse> userResponseEntity = restTemplate.exchange(URLs.URL_GET_PROFILE + username, HttpMethod.GET, request, UserResponse.class);
                    userResponse = userResponseEntity.getBody();
                } else if (params[0].equals(URLs.UPDATE)) {
                    requestHeaders = getHeaders();
                    requestHeaders.add("Content-Type", "application/json");
                    HttpEntity<String> request = new HttpEntity<String>(requestHeaders);
                    UserAuth userAuth = new UserAuth(token, "", "", roleList, username, "", Long.parseLong(userid));
                    User user = new User(nickName, username, email, contactNumber, userAuth, dob, userDanceStyle, userDanceStyleWholeList, firstName, secondName, region, country, state, gender, "teststatus", "testtype");
                    UserCreateRequest userCreateRequest = new UserCreateRequest();
                    userCreateRequest.setUser(user);
                    HttpEntity<UserCreateRequest> httpEntity = new HttpEntity<UserCreateRequest>(userCreateRequest, requestHeaders);
                    userResponse = restTemplate.postForObject(URLs.URL_UPDATE_PROFILE, httpEntity, UserResponse.class);
                    Log.e(TAG, userResponse.getUser().toString() + ", " + userResponse.getRequestSource());
                } else if (params[0].equals(AppConstants.WHOLE_DANCE_STYLES)) {
                    requestHeaders = getHeaders();
                    HttpEntity<String> request = new HttpEntity<String>(requestHeaders);
                    ResponseEntity<UserResponse> userResponseEntity = restTemplate.exchange(URLs.URL_USER_WHOLE_DANCE_STYLES, HttpMethod.GET, request, UserResponse.class);
                    userResponse = userResponseEntity.getBody();

                } else {
                    UserAuth userAuth = new UserAuth("testclient", "testauth", username, password);
                    User user = new User(null, username, email, null, userAuth, null, null, null, null, null, null, null, null, null, "teststatus", "testtype");
//                    user.setUserName(username);
                    UserCreateRequest userCreateRequest = new UserCreateRequest();
                    userCreateRequest.setUser(user);
                    HttpEntity<UserCreateRequest> httpEntity = new HttpEntity<UserCreateRequest>(userCreateRequest);
                    userResponse = restTemplate.postForObject(params[0], httpEntity, UserResponse.class);
                    Log.e(TAG, userResponse.getUser().toString() + ", " + userResponse.getRequestSource());
                }
            } catch (HttpStatusCodeException e) {
                result = getErrorResponse(e.getResponseBodyAsString());
                Log.e(TAG, result.getMessage());
                return null;
            }
            return userResponse;
        }

        @Override
        protected void onPostExecute(UserResponse userResponse) {
            super.onPostExecute(userResponse);

            if (process.equals(URLs.UPDATE) && userResponse != null) {
//                parent.OnTaskCompletedWithParameter(result.getMessage());
                parent.OnTaskCompletedWithParameter("Updated Successfully");
            } else if (process.equals(URLs.PROFILE) && userResponse != null) {
//                parent.OnTaskCompletedWithParameter(result.getMessage());
                parent.OnTaskCompletedWithParameter("");
            } else if (process.equals(URLs.URL_REGISTER) && userResponse != null) {
                parent.OnTaskCompletedWithParameter("Registration success");
            } else if (process.equals(AppConstants.WHOLE_DANCE_STYLES) && userResponse != null) {
                parent.OnTaskCompletedWithParameter("");
            } else {
                parent.OnTaskCompletedWithParameter(result.getMessage());
            }
        }
    }


    public static UserResponse getUserResponse() {
        return userResponse;
    }

    public static UserResponse getWholeDanceStyles() {
        return userResponse;
    }

}

