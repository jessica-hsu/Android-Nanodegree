package com.udacity.android.podcastbemine;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.udacity.android.podcastbemine.model.User;
import com.udacity.android.podcastbemine.ui.MainMenuActivity;
import com.udacity.android.podcastbemine.ui.SignInErrorActivity;
import com.udacity.android.podcastbemine.utils.Constant;

public class MainActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    private static GoogleSignInClient mGoogleSignInClient;
    private static String userId;
    SignInButton googleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        googleButton = findViewById(R.id.sign_in_button);
        googleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                googleSignInProcess();
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleCheckExistingUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == Constant.RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    /**
     * Check for existing Google Sign In account, if the user is already signed in
     * the GoogleSignInAccount will be non-null.
     */
    private void googleCheckExistingUser() {

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            // go to main menu
            googleButton.setVisibility(View.GONE);
            userId = account.getId();
            User user = new User(account.getId(), account.getGivenName());
            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.putExtra(Constant.INTENT_LABEL_USER_INFO, user);
            startActivity(intent);
        } else {
            // start sign in process
            googleButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Begin google sign in process
     */
    public void googleSignInProcess() {
        boolean internet = checkInternetAccess();
        if (internet) {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, Constant.RC_SIGN_IN);
        } else {
            Intent intent = new Intent(this, SignInErrorActivity.class);
            intent.putExtra(Constant.INTENT_LABEL_ERROR, Constant.NO_INTERNET_ERROR);
            startActivity(intent);
        }
    }

    /**
     * Handle what happens after user signs in
     * @param completedTask
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, go to main menu page TODO given name returns null
            User user = new User(account.getId(), account.getGivenName());
            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.putExtra(Constant.INTENT_LABEL_USER_INFO, user);
            startActivity(intent);

        } catch (ApiException e) {

            // failed to sign in. Show Sign In Error activity
            Intent intent = new Intent(this, SignInErrorActivity.class);
            intent.putExtra(Constant.INTENT_LABEL_ERROR, Constant.GOOGLE_SIGN_IN_ERROR);
            startActivity(intent);
        }
    }

    /**
     * Check for internet access
     * @return boolean
     */
    private boolean checkInternetAccess() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public static GoogleSignInClient getmGoogleSignInClient() {
        return mGoogleSignInClient;
    }
    public static String getUserId() { return userId; }
}
