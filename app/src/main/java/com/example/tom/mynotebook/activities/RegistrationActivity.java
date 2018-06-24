package com.example.tom.mynotebook.activities;

//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.EditText;
//
//import com.example.tom.mynotebook.R;
//
//public class RegistrationActivity extends AppCompatActivity{
//
//    private EditText login;
//    private EditText password;
//    private EditText confirmPassword;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration);
//
//        login = findViewById(R.id.loginEditTextSignInActivity);
//        password = findViewById(R.id.passwordEditTextSignInActivity);
//        confirmPassword = findViewById(R.id.confirmPasswordEditTextSignInActivity);
//
//
//
//    }
//}

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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


/**BackendSettings
 * Handles registration flow.
 */
public class RegistrationActivity extends AppCompatActivity
{
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registration );

        Backendless.initApp( this, BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY);

        Button registerButton = findViewById( R.id.registrationButtonRegistrationActivity );

        View.OnClickListener registerButtonClickListener = createRegisterButtonClickListener();

        registerButton.setOnClickListener( registerButtonClickListener );
    }

    /**
     * Validates the values, which user entered on registration screen.
     * Shows Toast with a warning if something is wrong.
     *
     * @param name            user's name
     * @param email           user's email
     * @param password        user's password
     * @param passwordConfirm user's password confirmation
     * @return true if all values are OK, false if something is wrong
     */
    public boolean isRegistrationValuesValid( CharSequence name, CharSequence email, CharSequence password,
                                              CharSequence passwordConfirm )
    {
        return Validator.isNameValid( this, name )
                && Validator.isEmailValid( this, email )
                && Validator.isPasswordValid( this, password )
                && isPasswordsMatch( password, passwordConfirm );
    }

    /**
     * Determines whether password and password confirmation are the same.
     * Displays Toast with a warning if not.
     *
     * @param password        password
     * @param passwordConfirm password confirmation
     * @return true if password and password confirmation match, else false
     */
    public boolean isPasswordsMatch( CharSequence password, CharSequence passwordConfirm )
    {
        if( !TextUtils.equals( password, passwordConfirm ) )
        {
            Toast.makeText( this, getString( R.string.warning_passwords_do_not_match ), Toast.LENGTH_LONG ).show();
            return false;
        }

        return true;
    }

    /**
     * Sends a request to Backendless to register user.
     *
     * @param name                 user's name
     * @param email                user's email
     * @param password             user's password
     * @param registrationCallback a callback, containing actions to be executed on request result
     */
    public void registerUser( String name, String email, String password,
                              AsyncCallback<BackendlessUser> registrationCallback )
    {
        BackendlessUser user = new BackendlessUser();
        user.setEmail( email );
        user.setPassword( password );
        user.setProperty( "name", name );

        //Backendless handles password hashing by itself, so we don't need to send hash instead of plain text
        Backendless.UserService.register( user, registrationCallback );
    }

    /**
     * Creates a callback, containing actions to be executed on registration request result.
     * Shows a Toast with BackendlessUser's objectId on success,
     * show a dialog with an error message on failure.
     *
     * @return a callback, containing actions to be executed on registration request result
     */
    public LoadingCallback<BackendlessUser> createRegistrationCallback()
    {
        return new LoadingCallback<BackendlessUser>( this, getString( R.string.loading_register ) )
        {
            @Override
            public void handleResponse( BackendlessUser registeredUser )
            {
                super.handleResponse( registeredUser );
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

                Toast.makeText( RegistrationActivity.this, String.format( getString( R.string.info_registered ), registeredUser.getObjectId() ), Toast.LENGTH_LONG ).show();
            }
        };
    }

    /**
     * Creates a listener, which proceeds with registration on button click.
     *
     * @return a listener, handling registration button click
     */
    public View.OnClickListener createRegisterButtonClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                EditText nameField = findViewById( R.id.loginEditTextRegistrationActivity );
                EditText emailField = findViewById( R.id.mailEditTextRegistrationActivity );
                EditText passwordField = findViewById( R.id.passwordEditTextRegistrationActivity );
                EditText passwordConfirmField = findViewById( R.id.confirmPasswordEditTextRegistrationActivity );

                CharSequence name = nameField.getText();
                CharSequence email = emailField.getText();
                CharSequence password = passwordField.getText();
                CharSequence passwordConfirmation = passwordConfirmField.getText();

                if( isRegistrationValuesValid( name, email, password, passwordConfirmation ) )
                {
                    LoadingCallback<BackendlessUser> registrationCallback = createRegistrationCallback();

                    registrationCallback.showLoading();
                    registerUser( name.toString(), email.toString(), password.toString(), registrationCallback );
                }
            }
        };
    }
}