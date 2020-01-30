package com.uo226321.joel.gymnastics.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.progresviews.ProgressWheel;
import com.uo226321.joel.gymnastics.R;

public class ReproductorActivity extends AppCompatActivity {
    private ProgressWheel mProgressWheel;
    private int percentage = 360;
    private int descanso;
    private String nombreEjercicio;
    private int series;
    private int serieActual = 0;

    private int repeticiones;
    private int discount;
    private TextView titulo;
    private CountDownTimer timer;
    private boolean reproduciendio = true;
    private boolean descansando;
    private long actualSeconds;
    private LinearLayout layoutSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);

        Bundle bundle = getIntent().getExtras();
        descanso = bundle.getInt("descanso");
        repeticiones = bundle.getInt("repeticiones");
        series = bundle.getInt("series");
        nombreEjercicio = bundle.getString("nombre");

        mProgressWheel = (ProgressWheel) findViewById(R.id.wheelprogress);
        layoutSeries = (LinearLayout) findViewById(R.id.seriesButtons);

        for(int i = 0; i < series; i++) {
            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView1.setText("" + (i+1));
            textView1.setTextSize(30);
            textView1.setGravity(Gravity.CENTER);
            textView1.setTextColor(Color.WHITE);

            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.setMargins(10, 0, 10, 0); // llp.setMargins(left, top, right, bottom);
            textView1.setLayoutParams(llp);

            textView1.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_textview)); // hex color 0xAARRGGBB
            layoutSeries.addView(textView1);

        }

        titulo = (TextView) findViewById(R.id.text_exercise);
        final Button button = findViewById(R.id.bt_play);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(reproduciendio) {
                    timer.cancel();
                    button.setText("Reproducir");
                    percentage += discount;

                    reproduciendio = false;
                } else {
                    if(descansando) {
                        startTimer(actualSeconds, true, getApplicationContext());
                    } else {
                        startTimer(actualSeconds, false, getApplicationContext());

                    }
                    button.setText("Pausar");
                    reproduciendio = true;
                }

            }
        });
        startEjercicio(this);
    }

    private void startEjercicio(Context context) {
        titulo.setText(nombreEjercicio + " - "  + repeticiones + " repeticiones");
        startTimer(30*1000, false, context);

    }


    private void startDescanso(Context context) {
        titulo.setText("Descanso");
        startTimer(descanso*1000, true, context);
    }

    private void startTimer(long timerStartFrom, final boolean descansar, final Context context) {
        if(descansar) {
            discount = 360/descanso;
            descansando = true;

        } else {
            discount = 360 / 30;
            descansando = false;

        }
        mProgressWheel.setPercentage(percentage);

        timer = new CountDownTimer(timerStartFrom, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mProgressWheel.setStepCountText("" + millisUntilFinished / 1000);
                percentage -= discount;
                mProgressWheel.setPercentage(percentage);
                actualSeconds = millisUntilFinished;

            }

            @Override
            public void onFinish() {
                percentage = 360;

                if(descansar) {
                    mProgressWheel.setStepCountText("0");
                    mProgressWheel.setPercentage(0);
                    if(serieActual == series) {
                        callPrincipal();
                        finish();
                    } else {
                        startEjercicio(context);
                    }
                } else {
                    TextView t = (TextView) layoutSeries.getChildAt(serieActual);
                    serieActual++;

                    t.setBackground(context.getResources().getDrawable(R.drawable.rounded_textview_finished)); // hex color 0xAARRGGBB
                    mProgressWheel.setStepCountText("0");
                    mProgressWheel.setPercentage(0);

                    startDescanso(context);

                }
            }
        }.start();
    }

    private void callPrincipal() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
