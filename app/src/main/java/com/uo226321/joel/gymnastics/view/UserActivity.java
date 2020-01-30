package com.uo226321.joel.gymnastics.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.app.progresviews.ProgressLine;
import com.nineoldandroids.animation.ValueAnimator;
import com.uo226321.joel.gymnastics.R;
import com.uo226321.joel.gymnastics.databinding.ActivityLoginBinding;
import com.uo226321.joel.gymnastics.databinding.ActivityUserBinding;
import com.uo226321.joel.gymnastics.viewmodel.LoginViewModel;
import com.uo226321.joel.gymnastics.viewmodel.UserViewModel;
import com.uo226321.joel.gymnastics.viewmodel.contracts.LoginResultCallback;
import com.uo226321.joel.gymnastics.viewmodel.contracts.UserResultCallback;

import java.util.Arrays;
import java.util.List;

public class UserActivity extends AppCompatActivity implements UserResultCallback {
    private Toolbar mToolbar;
    private ActivityUserBinding binding;
    private ProgressLine meter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user);


        meter = (ProgressLine) findViewById(R.id.progress_line);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });


        }
        UserViewModel userViewModel = new UserViewModel(this, this);
        binding.setUserViewModel(userViewModel);
    }

    @Override
    public void setImcLine(double imc) {
        meter.setmPercentage((int) imc*100/45);
        meter.setmValueText("" + Math.round(imc * 100.0) / 100.0);
    }
}
