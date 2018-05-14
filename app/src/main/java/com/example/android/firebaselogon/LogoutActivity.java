package com.example.android.firebaselogon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogoutActivity extends AppCompatActivity {

    String userEmail;
    String legalName;
    String userUniqueId;
    TextView textViewMail, textViewName, textViewUserUniqueId;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        Intent prevScreen = getIntent();
//        userEmail = prevScreen.getStringExtra("EXTRA_USERNAME");
//        legalName = prevScreen.getStringExtra("EXTRA_LEGALNAME");
//        userUniqueId = prevScreen.getStringExtra("EXTRA_NAVIGATION_METHOD");

        /* Get the Legal Name of the current user */
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            legalName = currentUser.getDisplayName();
            userEmail = currentUser.getEmail();
            userUniqueId = currentUser.getUid();
            Log.v( "TAGNAME", "The User name fetched: " + legalName );
        }

        textViewMail = findViewById(R.id.logout_email_id);
        textViewName = findViewById(R.id.logout_legal_name);
        textViewUserUniqueId = findViewById(R.id.logout_from_activity);

        textViewMail.setText(userEmail);
        textViewName.setText(legalName);
        textViewUserUniqueId.setText(userUniqueId);

        Button logOutButton = findViewById(R.id.logout_button_logout_page);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(LogoutActivity.this, MainActivity.class));
                }
            }
        };

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });
    }

    public void onClickNextPage(View v){
        Intent nextScreen = new Intent(LogoutActivity.this, PredictActivity.class);
        nextScreen.putExtra("EXTRA_USEREMAIL", userEmail.toString());
        nextScreen.putExtra("EXTRA_USERUNIQUE", userUniqueId.toString());
        startActivity(nextScreen);
//        startActivity( new Intent( LogoutActivity.this, PredictActivity.class ));
    }
}
