package com.example.tenx.test272.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tenx.test272.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private static int WELCOME_TIMEOUT = 4000;
    CallbackManager mCallbackManager;
    private static final String TAG = "FACEBOOKLOG";
    FirebaseAuth mAuth;
    ImageView imgView;
    Button btnLogin;
    LottieAnimationView lottieAnim;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onStart() {
        super.onStart();
        //check if user is signed on and update Ui accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            updateUI();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imgView = findViewById(R.id.iv_loader_image);
        btnLogin = findViewById(R.id.btn_login);
        imgView.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);

        //lottie animation view
        lottieAnim = findViewById(R.id.lottie_loading);


        //auth
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnimation("start");
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        showAnimation("stop");
                        Log.d(TAG, "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        showAnimation("stop");
                        Log.d(TAG, "facebook:onError", error);
                        // ...
                    }
                });
            }
        });




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imgView.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
            }
        },WELCOME_TIMEOUT);


    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            showAnimation("stop");
                        }

                        // ...
                    }
                });
    }

    public void updateUI(){
        Intent accountIntent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(accountIntent);
        finish();
        showAnimation("stop");

    }
    public void showAnimation(String s) {
        if (s.equals("start")) {
            btnLogin.setVisibility(View.GONE);
            lottieAnim.setVisibility(View.VISIBLE);
            lottieAnim.playAnimation();
        }
        if (s.equals("stop")) {
            btnLogin.setVisibility(View.VISIBLE);
            lottieAnim.setVisibility(View.GONE);
            lottieAnim.pauseAnimation();
        }
    }


}






