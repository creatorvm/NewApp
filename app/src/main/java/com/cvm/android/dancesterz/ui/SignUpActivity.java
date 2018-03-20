package com.cvm.android.dancesterz.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.SignUpDao;
import com.cvm.android.dancesterz.ui.listeners.ParameterListener;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;
import com.cvm.android.dancesterz.utilities.URLs;

public class SignUpActivity extends BaseUIActivity {
    EditText editUsername, editPassword, editCpass, editEmail;
    Button SignIn;
    PreferencesManager preferencesManager;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editpass);
        editCpass = (EditText) findViewById(R.id.editConfirmpass);
        editEmail = (EditText) findViewById(R.id.editEmail);
        SignIn = (Button) findViewById(R.id.regbtnSignUp);
        AppConstants.contextOfApplication = getApplicationContext();
        preferencesManager = new PreferencesManager(AppConstants.contextOfApplication);
        SignIn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProgressDialog = new ProgressDialog(getApplicationContext());
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setMessage("Please Wait for your registration...");
                        mProgressDialog.setCanceledOnTouchOutside(false);
                        submitRegistration();
                    }
                });
    }

    private void submitRegistration() {
        AppConstants.awesomeValidation.addValidation(this, R.id.editUsername, AppConstants.validUsername, R.string.error_username);
        AppConstants.awesomeValidation.addValidation(this, R.id.editEmail, AppConstants.emailPattern, R.string.error_invalid_email);
        String regexPassword = ".{8,}";
        AppConstants.awesomeValidation.addValidation(this, R.id.editpass, regexPassword, R.string.error_invalid_password);
        AppConstants.awesomeValidation.addValidation(this, R.id.editConfirmpass, R.id.editpass, R.string.error_confirm_password);
        if (AppConstants.awesomeValidation.validate()) {

            new SignUpDao("", editUsername.getText().toString(), editPassword.getText().toString(), editCpass.getText().toString(), editEmail.getText().toString(), null, null, null, "", "", "", "", "", "", "", "", URLs.URL_REGISTER, "", new ParameterListener() {
                @Override
                public void OnTaskCompletedWithParameter(String message) {
                    mProgressDialog.dismiss();
                    if (message.equals("Registration success")) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Duplicate Entry!!Please choose another username..", Toast.LENGTH_LONG).show();
                    }
                }

            });
        }
    }
}
