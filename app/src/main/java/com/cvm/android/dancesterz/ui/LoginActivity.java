package com.cvm.android.dancesterz.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.LoginDao;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

public class LoginActivity extends BaseUIActivity {
    private EditText editPass, editUsername;
    private Button SignIn;
    //    private Button      Forgot_password;
    public static Context contextOfApplication;
    private static final String TAG = "LoginActivity";
    private LoginDao loginDao;
    ProgressDialog mProgressDialog;
    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        contextOfApplication = getApplicationContext();
        preferencesManager = new PreferencesManager(contextOfApplication);
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Please Wait...");
        editPass = (EditText) findViewById(R.id.editpass);
        editUsername = (EditText) findViewById(R.id.editUsername);
        SignIn = (Button) findViewById(R.id.btnSignIn);
//        Forgot_password  = (Button) findViewById(R.id.btnforgot_password);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Invoking login");
                mProgressDialog.show();

                loginDao = new LoginDao(LoginActivity.this, preferencesManager, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted() {
                        Log.d(TAG, "onTaskCompleted");
                        mProgressDialog.dismiss();
                        finish();
                        navigateToHomeScreen();
                    }
                });
                loginDao.checkLogin(editUsername.getText().toString(), editPass.getText().toString());
            }
        });
    }
}
