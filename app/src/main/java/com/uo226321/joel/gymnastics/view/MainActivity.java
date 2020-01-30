package com.uo226321.joel.gymnastics.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.uo226321.joel.gymnastics.R;
import com.uo226321.joel.gymnastics.client.endpoints.FindEjercicioByNameEndpoint;
import com.uo226321.joel.gymnastics.databinding.ActivityMainBinding;
import com.uo226321.joel.gymnastics.model.Ejercicio;
import com.uo226321.joel.gymnastics.model.Rutina;
import com.uo226321.joel.gymnastics.model.Serie;
import com.uo226321.joel.gymnastics.client.EjercicioService;
import com.uo226321.joel.gymnastics.view.Adapters.MyListadoRutinaRecyclerViewAdapter;
import com.uo226321.joel.gymnastics.viewmodel.MainViewModel;
import com.uo226321.joel.gymnastics.viewmodel.contracts.MainResultCallback;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ListadoRutinaFragment.OnListFragmentInteractionListener, MainResultCallback {
    private RecyclerView rw;
    private TabLayout tabLayout;
    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    public ProgressDialog dialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        rw = (RecyclerView) findViewById(R.id.list);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                clear();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mainViewModel = new MainViewModel(this, this);
        binding.setMainViewModel(mainViewModel);
    }

    @Override
    public void loadTabs(int dias) {
        for (int i = 0; i < dias; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("Dia " + (i + 1)));
        }
    }

    @Override
    public void loadRutina(Rutina rutinaActual) {
        MyListadoRutinaRecyclerViewAdapter adapter = new MyListadoRutinaRecyclerViewAdapter(rutinaActual.getDiasEntrenamiento().get(0).getSeries(), this);
        rw.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ejercicios) {
            mainViewModel.loadMusculos();
            return true;
        } else if(id == R.id.informacion) {
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void clear() {
        if(mainViewModel != null) {
            rw.setAdapter(null);
            MyListadoRutinaRecyclerViewAdapter adapter =
                    new MyListadoRutinaRecyclerViewAdapter(mainViewModel.getRutinaActual()
                            .getDiasEntrenamiento().get(tabLayout.getSelectedTabPosition()).getSeries(),
                            this);
            rw.setAdapter(adapter);
        }
    }


    @Override
    public void onListFragmentInteraction(final Serie item) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("¿Que quieres hacer?")
                .setMessage("Puedes ver la información detallada del ejercicio o reproducirlo.")
                .setPositiveButton("Reproducir ejercicio",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                callReproducirEjercicio(item);
                            }
                        })
                .setNegativeButton("Información ejercicio",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                loadEjercicio(item.getEjercicioId());
                            }
                        }).show();


    }

    private void callReproducirEjercicio(Serie serie) {
        Intent intent = new Intent(this, ReproductorActivity.class);
        intent.putExtra("descanso", serie.getDescanos());
        intent.putExtra("nombre", serie.getEjercicioId());
        intent.putExtra("series", serie.getSeries());
        intent.putExtra("repeticiones", serie.getRepeticiones());

        startActivity(intent);
    }


    public void loadEjercicio(String ejerc) {
        dialogLoading = ProgressDialog.show(this, "",
                "Cargando...", true);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gimnsatics.herokuapp.com/rest/EjercicioServiceRs/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        //Create service
        EjercicioService es = retrofit.create(EjercicioService.class);
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        Call<Ejercicio> gu = es.findByName("Bearer " + prefs.getString("Token", ""), ejerc);
        new FindEjercicioByNameEndpoint(this).execute(gu);

    }

    @Override
    public void restartMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
