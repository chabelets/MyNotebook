package com.example.tom.mynotebook.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.example.tom.mynotebook.R;
import com.example.tom.mynotebook.utility.BackendSettings;
import com.example.tom.mynotebook.utility.LoadingCallback;
import com.example.tom.mynotebook.utility.Validator;

public class LoginActivity extends Activity {

    private static final int REGISTER_REQUEST_CODE = 1;
    private EditText loginEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Backendless.initApp( this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY);

        loginEditText = findViewById(R.id.loginEditTextLoginActivity);
        passwordEditText = findViewById(R.id.passwordEditTextLoginActivity);

        loginButton = findViewById(R.id.loginButtonLoginActivity);
        loginButton.setOnClickListener(createLoginButtonListener());

        registerButton = findViewById(R.id.registrationButtonLoginActivity);
        registerButton.setOnClickListener(createRegistrationButtonListener());
    }

    private View.OnClickListener createRegistrationButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrationIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivityForResult( registrationIntent, REGISTER_REQUEST_CODE );
            }
        };
    }

    public View.OnClickListener createLoginButtonListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                EditText emailField = (EditText) findViewById( R.id.loginEditTextLoginActivity );
                EditText passwordField = (EditText) findViewById( R.id.passwordEditTextLoginActivity );

                CharSequence email = emailField.getText();
                CharSequence password = passwordField.getText();

                if( isLoginValuesValid( email, password ) )
                {
                    LoadingCallback<BackendlessUser> loginCallback = createLoginCallback();

                    loginCallback.showLoading();
                    loginUser( email.toString(), password.toString(), loginCallback );
                }
            }
        };
    }

    public void loginUser( String email, String password, AsyncCallback<BackendlessUser> loginCallback )
    {
        Backendless.UserService.login( email, password, loginCallback );
    }

    public LoadingCallback<BackendlessUser> createLoginCallback()
    {
        return new LoadingCallback<BackendlessUser>( this, getString( R.string.loading_login ) )
        {
            @Override
            public void handleResponse( BackendlessUser loggedInUser )
            {
                super.handleResponse( loggedInUser );
                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivityForResult( loginIntent, REGISTER_REQUEST_CODE );
                Toast.makeText( LoginActivity.this, String.format( getString( R.string.info_logged_in ), loggedInUser.getObjectId() ), Toast.LENGTH_LONG ).show();
            }
        };
    }

    public boolean isLoginValuesValid( CharSequence email, CharSequence password )
    {
        return Validator.isEmailValid( this, email ) && Validator.isPasswordValid( this, password );
    }


}
