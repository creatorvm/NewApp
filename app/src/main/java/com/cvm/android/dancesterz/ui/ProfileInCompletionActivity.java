package com.cvm.android.dancesterz.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.ui.fragments.ProfileFragment;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;
import com.squareup.picasso.Picasso;

public class ProfileInCompletionActivity extends Activity {

    ImageView profileInCompleteImageView;
    LinearLayout profileInCompleteLinearLayout;
    PreferencesManager pManager;
    Button profileInCompletionCloseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profile_in_completion);
        profileInCompleteImageView = findViewById(R.id.userProfileInComImageView);
        profileInCompleteLinearLayout = findViewById(R.id.profileInCompleteLinearLayout);
        profileInCompletionCloseButton = findViewById(R.id.profileInCompletionCloseButton);
        pManager = new PreferencesManager(ProfileInCompletionActivity.this);
        String pic = pManager.read(AppConstants.KEY_PROFILE_PIC);
        Picasso.with(ProfileInCompletionActivity.this).load(pManager.read(AppConstants.KEY_PROFILE_PIC)).into(profileInCompleteImageView);
        profileInCompleteLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        profileInCompletionCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
