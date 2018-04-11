package com.cvm.android.dancesterz.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.LoginDao;
import com.cvm.android.dancesterz.ui.listeners.OnTaskCompleted;

public class MainActivity extends BaseUIActivity {

    Button signInButton = null;
    Button signUpButton = null;

    private LoginDao loginDao;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        signInButton = findViewById(R.id.btnSignIn);
        signUpButton = findViewById(R.id.btnSignUp);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
//                new HomeScreenAsyncTask().execute();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    private class HomeScreenAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "Insde background Task ");
            loginDao = new LoginDao(MainActivity.this, getPreferencesManager(), new OnTaskCompleted() {
                @Override
                public void onTaskCompleted() {
                    Log.d(TAG, "onTaskCompleted");
                    navigateToHomeScreen();
                }
            });
            Log.d(TAG, "Checking Validatiy");
            loginDao.checkLogin();
            return null;
        }
    }
}
