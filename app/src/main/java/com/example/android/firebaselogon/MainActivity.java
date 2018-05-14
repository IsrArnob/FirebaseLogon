package com.example.android.firebaselogon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUserEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private static final int RC_SIGN_IN = 8008;
    private GoogleSignInClient mGoogleSignInClient;
    private ProgressDialog progressDialog;
    private String userUniqueId;
    private String userLegalName;
    private String userEmail;
    SignInButton googleSignInButton;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseAuth.AuthStateListener mAuthListener;

//    TextView signInButtonView = (TextView) googleSignInButton.getChildAt(0);

    @Override
    protected void onStart(){
        /* Checks whether the user is currently signed in */
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void onClickRegister(View v) {
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Hide the Status bar
         * If the Android version is lower than Jellybean, use this call to hide
         * the status bar
         */

        if (Build.VERSION.SDK_INT < 99) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);

        editTextUserEmail = findViewById(R.id.login_user_id_edit_text);
        editTextPassword = findViewById(R.id.login_password_edit_text);
        buttonLogin = findViewById(R.id.login_button);

        googleSignInButton = (SignInButton) findViewById(R.id.sign_in_with_google);
        /* Set Google Sign In Button Text */
//        TextView signInButtonView = (TextView) googleSignInButton.getChildAt(0);
//        signInButtonView.setText("Sign In With Google");

        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

        /* Capturing sign in using google click */
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        /* Decide what to do when Auth state is changed */
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                /* user found */
                if (firebaseAuth.getCurrentUser() != null){
                    currentUser = mAuth.getCurrentUser();
                    userEmail = currentUser.getEmail();
                    userLegalName = currentUser.getDisplayName();
                    writeUserInfo();
                    Intent nextScreen = new Intent(MainActivity.this, LogoutActivity.class);
                    nextScreen.putExtra("EXTRA_USERNAME", userEmail);
                    nextScreen.putExtra("EXTRA_LEGALNAME", userLegalName);
                    nextScreen.putExtra("EXTRA_NAVIGATION_METHOD", "Sign in with Google");
                    startActivity(nextScreen);
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void logIn() {
        String inputEmail = editTextUserEmail.getText().toString();
        String inputPassword = editTextPassword.getText().toString();

        if ((TextUtils.isEmpty(inputEmail))){
            Toast.makeText(this, "Email ID field can't be left blank during logging in", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(inputPassword)){
            Toast.makeText(this, "Password can't be left blank during logging in", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in ....");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(inputEmail, inputPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ((task.isSuccessful())){
                            writeUserInfo();
                            /* Sign in success, update UI with the signed-in user's information */
                            Intent nextScreen = new Intent(MainActivity.this, LogoutActivity.class);
                            nextScreen.putExtra("EXTRA_USERNAME", editTextUserEmail.getText().toString());
                            nextScreen.putExtra("EXTRA_LEGALNAME", "");
                            nextScreen.putExtra("EXTRA_NAVIGATION_METHOD", "Login");
                            startActivity(nextScreen);
                        } else {
                            progressDialog.hide();
                            Toast.makeText(MainActivity.this, "Authentication failed\nPlease try again ....", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void writeUserInfo(){
        currentUser = mAuth.getCurrentUser();
        userUniqueId = currentUser.getUid();
        userEmail = currentUser.getEmail();
        userLegalName = currentUser.getDisplayName();
        UserInfoHandler userInfoHandler = new UserInfoHandler( userUniqueId, userEmail, userLegalName, "U", 0 );
    }

    private void signIn() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in ....");
        progressDialog.show();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInResult res = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleResult(res);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAGSIGNINFAILURE", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void handleResult(GoogleSignInResult result){
        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            userLegalName = account.getDisplayName();
            userEmail = account.getEmail();
            String imageUrl = account.getPhotoUrl().toString();
            Log.v("URL Message", "Image URL: " + imageUrl);
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
             .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()) {
                         // Sign in success, update UI with the signed-in user's information
                         Log.d("TAGSIGNINSUCCESS", "signInWithCredential:success");
                         FirebaseUser user = mAuth.getCurrentUser();
                         progressDialog.hide();
                         //updateUI(user);
                     } else {
                         // If sign in fails, display a message to the user.
                         Log.w("TAGSIGNINWITHCREDFAILED", "signInWithCredential:failure", task.getException());
                         progressDialog.show();
                         Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                         //updateUI(null);
                     }

                     // ...
                 }
                });
    }
}
