package com.example.practiceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSingInClient;
    private  final int RC_SIGN_IN=1;
    private FirebaseAuth.AuthStateListener mauthStateListener;

    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mauth=FirebaseAuth.getInstance();

        SignInButton button=findViewById(R.id.google_button);
        button.setSize(SignInButton.SIZE_STANDARD);

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("329528446048-i0u2jgakpr77ak6p63tbvngbe8f0riok.apps.googleusercontent.com")
                .requestEmail().build();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void signIn() {
        Intent intent=mGoogleSingInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account=task.getResult(ApiException.class);
            }
            catch (ApiException e){
                Log.w("fail_1","google SignIn Failed",e);
            }
        }





    }

    @Override
    protected void onStart() {
        super.onStart();

        mauth.addAuthStateListener(mauthStateListener);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mauth.removeAuthStateListener(mauthStateListener);
    }
}
