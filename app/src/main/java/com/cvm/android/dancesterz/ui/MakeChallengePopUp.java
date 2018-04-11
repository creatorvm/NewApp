package com.cvm.android.dancesterz.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MakeChallengePopUp extends Activity {
    private Button    makecpopup_view;
    private Button    makecpopup_btnclose;
    private CircleImageView makecpopup_profpic;
    PreferencesManager pManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_make_challenge_pop_up);
        makecpopup_view     = findViewById(R.id.makecpopup_btnview);
        makecpopup_btnclose = findViewById(R.id.makecpopup_btnclose);
        makecpopup_profpic  = findViewById(R.id.makecpopup_profpic);

        pManager = new PreferencesManager(MakeChallengePopUp.this);

        Picasso.with(MakeChallengePopUp.this).load(pManager.read(AppConstants.KEY_PROFILE_PIC)).placeholder(R.drawable.t2)
                .error(R.drawable.t2).into(makecpopup_profpic);

        makecpopup_btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 finish();
                startActivity(new Intent(getApplicationContext(),HomeScreenActivity.class));

            }
        });
    }
}
