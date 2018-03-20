package com.cvm.android.dancesterz.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.LoginDao;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

public class SplashScreenActivity extends BaseUIActivity {
    PreferencesManager preferencesManager;
    public static Context contextOfApplication;
    LoginDao loginDao;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        contextOfApplication = getApplicationContext();
        preferencesManager = new PreferencesManager(contextOfApplication);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                loginDao = new LoginDao(SplashScreenActivity.this, preferencesManager, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted() {
                        Log.d(TAG, "onTaskCompleted");
                        finish();
                        navigateToHomeScreen();
                    }
                });
                loginDao.checkLogin();
            }
        }, 1000);
    }
}
