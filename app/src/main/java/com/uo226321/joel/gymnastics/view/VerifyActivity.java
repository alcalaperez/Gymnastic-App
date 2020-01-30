package com.uo226321.joel.gymnastics.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.uo226321.joel.gymnastics.R;
import com.uo226321.joel.gymnastics.databinding.ActivityVerifyBinding;
import com.uo226321.joel.gymnastics.viewmodel.VerifyViewModel;
import com.uo226321.joel.gymnastics.viewmodel.contracts.VerifyResultCallback;

public class VerifyActivity extends AppCompatActivity implements VerifyResultCallback {
    private EditText key1;
    private EditText key2;
    private EditText key3;
    private EditText key4;

    private View mProgressView;
    private View formVerify;
    private ActivityVerifyBinding binding;
    private VerifyViewModel verifyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify);

        verifyViewModel = new VerifyViewModel(this, this);
        binding.setVerifyViewModel(verifyViewModel);

        key1 = (EditText) findViewById(R.id.key1);
        key2 = (EditText) findViewById(R.id.key2);
        key3 = (EditText) findViewById(R.id.key3);
        key4 = (EditText) findViewById(R.id.key4);

        key1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {
                if (key1.getText().toString().length() == 3)
                {
                    key2.requestFocus();
                }
            }
        });

        key2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {
                if (key2.getText().toString().length() == 3)
                {
                    key3.requestFocus();
                }
            }
        });

        key3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {
                if (key3.getText().toString().length() == 3)
                {
                    key4.requestFocus();
                }
            }
        });


        formVerify = findViewById(R.id.form_verify);
        mProgressView = findViewById(R.id.login_progress);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    public void callLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public void callForm() {
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra("usuario", verifyViewModel.getUsuario());
        startActivity(intent);
    }


    @Override
    public void onSuccess(String s) {
        Toast.makeText(this,
                "Verificado", Toast.LENGTH_SHORT).show();
        callForm();
    }


    @Override
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            formVerify.setVisibility(show ? View.GONE : View.VISIBLE);
            formVerify.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    formVerify.setVisibility(show ? View.GONE : View.VISIBLE);
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
            formVerify.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        callLogin();
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

}
