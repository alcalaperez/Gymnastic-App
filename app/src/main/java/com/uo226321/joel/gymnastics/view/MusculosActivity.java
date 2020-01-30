package com.uo226321.joel.gymnastics.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.uo226321.joel.gymnastics.R;
import com.uo226321.joel.gymnastics.model.Ejercicio;
import com.uo226321.joel.gymnastics.client.endpoints.FindEjerciciosEndpoint;
import com.uo226321.joel.gymnastics.client.EjercicioService;
import com.uo226321.joel.gymnastics.view.Adapters.RecyclerViewAdapterMusculos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MusculosActivity extends AppCompatActivity implements RecyclerViewAdapterMusculos.ItemClickListener{
    RecyclerViewAdapterMusculos adapter;
    public ProgressDialog dialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musculos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();

        List<String> musculos = (ArrayList<String>)extras.getSerializable("musculos");


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvMusculos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapterMusculos(this, musculos);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        loadEjercicios(adapter.getItem(position));
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

    public void loadEjercicios(String musculo) {
        dialogLoading = ProgressDialog.show(this, "",
                "Cargando...", true);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gimnsatics.herokuapp.com/rest/EjercicioServiceRs/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        //Create service
        EjercicioService es = retrofit.create(EjercicioService.class);
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        Call<List<Ejercicio>> gu = es.findByMusculos("Bearer " + prefs.getString("Token", ""), musculo);
        new FindEjerciciosEndpoint(this).execute(gu);

    }



}
