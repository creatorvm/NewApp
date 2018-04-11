package com.cvm.android.dancesterz.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.ui.fragments.EditCircleFragment;
import com.cvm.android.dancesterz.ui.fragments.HomeScreenFragment;
import com.cvm.android.dancesterz.ui.fragments.MyCircleFragment;
import com.cvm.android.dancesterz.ui.fragments.ProfileFragment;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.BottomNavigationViewHelper;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

public class HomeScreenActivity extends BaseUIActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //    CircleImageView profileCircleImageView = null;
    TextView userNickNameTextView = null;
    TextView navigationTotalFollowersTextView = null;
    TextView navigationTotalViewsTextView = null;
    public static Button join = null;
    PreferencesManager preferencesManager;
    public static Context contextOfApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        join = findViewById(R.id.joinChallenge);
        join.setVisibility(View.GONE);
        toolbar.setTitleTextColor(getResources().getColor(R.color.darkgray));
        setSupportActionBar(toolbar);

        contextOfApplication = getApplicationContext();
        preferencesManager = new PreferencesManager(contextOfApplication);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(navigation);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
//        profileCircleImageView = headerView.findViewById(R.id.navigationProfilePic);
        userNickNameTextView = headerView.findViewById(R.id.navigationNickName);
        navigationTotalFollowersTextView = headerView.findViewById(R.id.navigationNoOffollowers);
        navigationTotalViewsTextView = headerView.findViewById(R.id.navigationNoOftotalvotes);

        String profilePic = preferencesManager.read(AppConstants.KEY_PROFILE_PIC);
        String nickName = preferencesManager.read(AppConstants.KEY_NICK_NAME);

//        Picasso.with(HomeScreenActivity.this).load(profilePic).into(profileCircleImageView);
        userNickNameTextView.setText(nickName);

        getSupportFragmentManager().beginTransaction().replace(R.id.parentFrameLayout, new HomeScreenFragment()).addToBackStack(null).commit();
    }

    public void changeFragments(Fragment fragment) {
        String TAG = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.parentFrameLayout, fragment, TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
//        backPressed = backPressed + 1;
//        if (backPressed <= 1) {
//            Toast.makeText(contextOfApplication, "Press twice to exit", Toast.LENGTH_SHORT).show();
//        } else if (backPressed == 2) {
        join.setVisibility(View.GONE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
//        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myvideos) {
            // Handle the camera action
        } else if (id == R.id.nav_mycollections) {
            getFragmentManager().beginTransaction().replace(R.id.parentFrameLayout, EditCircleFragment.newInstance("", "")).commit();
        } else if (id == R.id.nav_mycircle) {
            getFragmentManager().beginTransaction().replace(R.id.parentFrameLayout, MyCircleFragment.newInstance("", "")).commit();
        } else if (id == R.id.nav_terms) {

        } else if (id == R.id.nav_faq) {

        } else if (id == R.id.nav_signout) {
            preferencesManager.remove(AppConstants.KEY_TOKEN);
            preferencesManager.remove(AppConstants.KEY_REFRESHTOKEN);
            preferencesManager.remove(AppConstants.KEY_USERID);
            preferencesManager.store(AppConstants.KEY_LOGGEDIN, "false");
            navigateToMainActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.parentFrameLayout, new HomeScreenFragment()).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_alert:
                    return true;
                case R.id.navigation_profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.parentFrameLayout, new ProfileFragment()).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_settings:
                    return true;
            }
            return false;
        }
    };
}
