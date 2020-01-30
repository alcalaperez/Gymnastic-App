package com.uo226321.joel.gymnastics.view;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.uo226321.joel.gymnastics.R;
import com.uo226321.joel.gymnastics.databinding.ActivityMostrarEjercicioBinding;
import com.uo226321.joel.gymnastics.model.Ejercicio;
import com.uo226321.joel.gymnastics.viewmodel.EjercicioMostrarViewModel;
import com.uo226321.joel.gymnastics.viewmodel.LoginViewModel;
import com.uo226321.joel.gymnastics.viewmodel.contracts.LoginResultCallback;
import com.uo226321.joel.gymnastics.viewmodel.contracts.MostrarEjercicioResultCallback;

import java.io.InputStream;
import java.net.URL;

public class EjercicioMostrarActivity extends AppCompatActivity implements MostrarEjercicioResultCallback {
    private ActivityMostrarEjercicioBinding binding;
    private SliderLayout sliderShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        Ejercicio ejercicio = (Ejercicio) extras.getSerializable("ejercicio");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_ejercicio_mostrar);
        EjercicioMostrarViewModel loginViewModel = new EjercicioMostrarViewModel(ejercicio, this);
        binding.setMostrarEjercicioViewModel(loginViewModel);

        SliderLayout sliderShow = (SliderLayout) findViewById(R.id.slider);
        DefaultSliderView foto1 = new DefaultSliderView(this);
        foto1.setScaleType(BaseSliderView.ScaleType.CenterInside);
        foto1.image(ejercicio.getFoto1());

        DefaultSliderView foto2 = new DefaultSliderView(this);
        foto2.setScaleType(BaseSliderView.ScaleType.CenterInside);
        foto2.image(ejercicio.getFoto2());

        sliderShow.addSlider(foto1);
        sliderShow.addSlider(foto2);

        sliderShow.setPresetTransformer(SliderLayout.Transformer.Foreground2Background);
        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

    }

    @Override
    public void onBackPressed() {
        finish();
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
