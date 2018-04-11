package com.cvm.android.dancesterz.ui;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class VotePopUp extends AppCompatActivity {

    private CircleImageView circularImageView;
    private TextView votepopup_username;
    private TextView votepopup_no_totalvotes;
    private Button votepopup_btnclose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vote_pop_up);

        circularImageView       = findViewById(R.id.votepopup_profileimg);
        votepopup_no_totalvotes = findViewById(R.id.votepopup_no_totalvotes);
        votepopup_username      = findViewById(R.id.votepopup_username);
        votepopup_btnclose      = findViewById(R.id.votepopup_btnclose);

        PreferencesManager preferencesManager=new PreferencesManager(getApplicationContext());
        votepopup_username.setText(preferencesManager.read(AppConstants.KEY_USERNAME));
        votepopup_btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
