package com.cvm.android.dancesterz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

/**
 * Created by prasadprabhakaran on 2/18/18.
 */

public class BaseUIActivity extends AppCompatActivity {

    private static final String TAG = "BaseUIActivity";
    PreferencesManager pManager;
    DrawerLayout drawer;

    public PreferencesManager getPreferencesManager() {
        return pManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pManager = new PreferencesManager(getApplicationContext());
    }

    @Override
    protected void onResume() {
        //navigateToMainActivity();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        //navigateToMainActivity();
        super.onRestart();
    }

    protected void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    protected void navigateToHomeScreen() {

        Boolean loggedIn = new Boolean(pManager.read(AppConstants.KEY_LOGGEDIN));
        Log.d(TAG, "Inside navigateToHomeScreen " + loggedIn);
        if (loggedIn) {
            Log.d(TAG, "navigateToHomeScreen");
            Intent i = new Intent(getApplicationContext(), HomeScreenActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(i);
        } else {
            Log.d(TAG, "navigate Login Screen");
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }
}
