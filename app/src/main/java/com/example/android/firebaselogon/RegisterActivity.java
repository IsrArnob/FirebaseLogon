package com.example.android.firebaselogon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registerSubmitButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextLegalName;
    private TextView errorText;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    String inputEmail, inputPassword, inputConfirmPassword, inputLegalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog( this );

        registerSubmitButton = findViewById( R.id.register_submit_button );
        editTextEmail = findViewById( R.id.register_user_id_edit_text );
        editTextPassword = findViewById( R.id.register_password_edit_text );
        editTextConfirmPassword = findViewById( R.id.register_confirm_password_edit_text );
        editTextLegalName = findViewById( R.id.register_legal_name_edit_text );
        errorText = findViewById( R.id.register_error_text_view );

        registerSubmitButton.setOnClickListener( this );

    }

    private void registerUser() {
        String inputEmail = editTextEmail.getText().toString();
        String inputPassword = editTextPassword.getText().toString();
        String inputConfirmPassword = editTextConfirmPassword.getText().toString();
        final String inputLegalName = editTextLegalName.getText().toString();

        if (TextUtils.isEmpty( inputEmail )) {
            Toast.makeText( this, "Email field can't be left empty", Toast.LENGTH_SHORT ).show();
            return;
        }
        if (TextUtils.isEmpty( inputPassword )) {
            Toast.makeText( this, "Password field can't be left empty", Toast.LENGTH_SHORT ).show();
            return;
        }
        if (TextUtils.isEmpty( inputConfirmPassword )) {
            Toast.makeText( this, "Confirm password field can't be left empty", Toast.LENGTH_SHORT ).show();
            return;
        }
        if (TextUtils.isEmpty( inputLegalName )) {
            Toast.makeText( this, "Name field can't be left blank", Toast.LENGTH_SHORT ).show();
            return;
        }
        if (inputPassword.compareTo( inputConfirmPassword ) != 0) {
            Toast.makeText( this, "Confirm password doesn't match", Toast.LENGTH_SHORT ).show();
            return;
        }

        progressDialog.setMessage( "Creating profile ...." );
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword( inputEmail, inputPassword )
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            /* Registration success, update UI with the signed-in user's information */
                            updateOtherInfoToProfile(inputLegalName);
                            Log.v( "INPTAG", "Input Legal name: " + inputLegalName );
                            progressDialog.hide();
                            Toast.makeText( RegisterActivity.this, "Profile created successfully", Toast.LENGTH_SHORT ).show();
                            mAuth.signOut();
//                            Intent nextScreen = new Intent(RegisterActivity.this, MainActivity.class);
//                            startActivity(nextScreen);
                        } else {
                            /* If registration fails, display a message to the user */
                            progressDialog.hide();
                            Toast.makeText( RegisterActivity.this, "Error in creating profile .... Please try again", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );
    }

    private void updateOtherInfoToProfile(final String legalName) {
        currentUser = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName( legalName )
                .setPhotoUri(Uri.parse(""))
                .build();

        currentUser.updateProfile( profileUpdates )
                .addOnCompleteListener( new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.v( "TAGUPDSUCCESS", "Inside successful update with: " + legalName );
                            Toast.makeText( RegisterActivity.this, "The user update is successful", Toast.LENGTH_SHORT ).show();
                        } else {
                            /* If registration fails, display a message to the user */
                            Log.v( "TAGUPD", "Inside update profile with " + legalName );
                            progressDialog.hide();
                            Toast.makeText( RegisterActivity.this, "Error in updating profile .... Please try again", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText( this, "The User name is: "+ currentUser.getDisplayName(), Toast.LENGTH_SHORT ).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == registerSubmitButton) {
            registerUser();
        }
    }
}
