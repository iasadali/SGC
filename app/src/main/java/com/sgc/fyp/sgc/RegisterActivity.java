package com.sgc.fyp.sgc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    //constants
    public static final String USER_PREFS = "USERPrefs";
    public static final String DISPLAY_NAME_KEY = "username";

    AnimationDrawable animationDrawable;
    LinearLayout linearLayout;
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    // FireBase Instance Variables
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        linearLayout = findViewById(R.id.linearLayout);
        animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        mEmailView = findViewById(R.id.register_email);
        mPasswordView = findViewById(R.id.register_password);
        mConfirmPasswordView = findViewById(R.id.register_confirm_password);
        mUsernameView = findViewById(R.id.register_username);

        // Keyboard sign in action
        mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.register_form_finished || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });
        //Get hold of an instance of FireBase Auth
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning())
            animationDrawable.stop();
    }
    public void signUp(View v) {
        attemptRegistration();
    }
    private void attemptRegistration() {

        // Reset errors displayed in the form.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Call create FireBaseUser() here
            createFireBaseUser();
            }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //Add own logic to check for a valid password
        String confirmPassword = mConfirmPasswordView.getText().toString();
        return confirmPassword.equals(password) && password.length()>4;
    }
    // Creating FireBase User
    private void createFireBaseUser(){
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("SGC","Create User on Complete: "+task.isSuccessful());
                if (!task.isSuccessful())
                {
                    Log.d("SGC","User Creation Failed");
                    showErrorDialog("Registration Attempt Failed");
                }
                else {
                    saveDisplayName();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }


            }
        });
    }
    // saving display name to Shared preferences
    private void saveDisplayName(){
        String displayName = mUsernameView.getText().toString();
        SharedPreferences prefs = getSharedPreferences(USER_PREFS,0);
        prefs.edit().putString(DISPLAY_NAME_KEY, displayName).apply();
    }
    //Creating an Alert DialogBox to show in case registration Failed
    private void showErrorDialog(String message){
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}
