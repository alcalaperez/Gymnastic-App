package com.uo226321.joel.gymnastics.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.uo226321.joel.gymnastics.R;
import com.uo226321.joel.gymnastics.databinding.ActivityLoginBinding;
import com.uo226321.joel.gymnastics.viewmodel.LoginViewModel;
import com.uo226321.joel.gymnastics.viewmodel.contracts.LoginResultCallback;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginResultCallback {

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginViewModel loginViewModel = new LoginViewModel(this, this);
        binding.setLoginViewModel(loginViewModel);

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        getWindow().setBackgroundDrawableResource(R.drawable.loginback);
        getSupportActionBar().hide();

    }


    public void callPrincipalActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void callRegisterActivity() {
        Intent intent = new Intent(this, VerifyActivity.class);
        startActivity(intent);
    }


    @Override
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

    @Override
    public void onSuccess(String s) {
        Toast.makeText(this,
                s, Toast.LENGTH_SHORT).show();
        callPrincipalActivity();
    }

    @Override
    public void onRegister() {
        callRegisterActivity();
    }


    @Override
    public void onError(String s) {
        Toast.makeText(this,
                s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserError(String s) {
        mEmailView.setError(s);
    }

    @Override
    public void onPasswordError(String s) {
        mPasswordView.setError(s);
    }

}

