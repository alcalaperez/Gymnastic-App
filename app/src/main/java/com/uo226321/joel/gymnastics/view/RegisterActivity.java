package com.uo226321.joel.gymnastics.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.uo226321.joel.gymnastics.R;
import com.uo226321.joel.gymnastics.model.Rutina;
import com.uo226321.joel.gymnastics.model.Usuario;
import com.uo226321.joel.gymnastics.client.endpoints.CreateUserEndpoint;
import com.uo226321.joel.gymnastics.client.endpoints.FindRutinaCaracsEndpoint;
import com.uo226321.joel.gymnastics.client.endpoints.VerifyLoginNameEndpoint;
import com.uo226321.joel.gymnastics.client.RutinaService;
import com.uo226321.joel.gymnastics.client.UserService;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mRepitePasswordView;
    private SharedPreferences prefs;
    private View mProgressView;
    private View mLoginFormView;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mRepitePasswordView = (EditText) findViewById(R.id.reppassword);
        prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        Bundle bundle = getIntent().getExtras();
        usuario = (Usuario) bundle.get("usuario");

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String repassword = mRepitePasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isPasswordValid(password, repassword)) {
            mRepitePasswordView.setError("Passwords no coinciden");
            focusView = mRepitePasswordView;
            cancel = true;
        }

        if (!cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            usuario.setUserid(email);
            usuario.setPassword(password);

            showProgress(true);

            checkLoginName();

        }
    }

    public void chooseRutina() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Nick", usuario.getUserid());
        editor.commit();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gimnsatics.herokuapp.com/rest/RutinaServiceRs/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        //Create service
        RutinaService us = retrofit.create(RutinaService.class);
        Call<Rutina> gu = us.findRutinaBySomObj(usuario.getSomatotipo(), usuario.getObjetivo());
        new FindRutinaCaracsEndpoint(this).execute(gu);
    }

    public void createUser() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gimnsatics.herokuapp.com/rest/UserServiceRs/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        //Create service
        UserService us = retrofit.create(UserService.class);
        Call<Void> gu = us.createUser(usuario);
        new CreateUserEndpoint(this).execute(gu);
    }

    public void checkLoginName() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gimnsatics.herokuapp.com/rest/UserServiceRs/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        //Create service
        UserService us = retrofit.create(UserService.class);
        Call<Boolean> gu = us.verifyAlreadyTaken(usuario.getUserid());
        new VerifyLoginNameEndpoint(this).execute(gu);
    }

    private boolean isPasswordValid(String password, String repassword) {
        if(password.equals(repassword)) {
            return true;
        }
        return false;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public void callLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void callSomatipos() {
        Intent intent = new Intent(this, SomatiposActivity.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        callSomatipos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showNameNotAvailable(RegisterActivity registerActivity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(registerActivity);
        alertDialogBuilder.setTitle("Nombre no disponible");
        alertDialogBuilder.setMessage("El nombre de usuario que has seleccionado no esta disponible, por favor utilice otro.");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialogBuilder.show();
    }
}

