package com.uo226321.joel.gymnastics.viewmodel;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.uo226321.joel.gymnastics.client.endpoints.FindMusculosEndpoint;
import com.uo226321.joel.gymnastics.client.endpoints.FindRutinaByUser;
import com.uo226321.joel.gymnastics.client.endpoints.FindRutinaCaracsEndpoint;
import com.uo226321.joel.gymnastics.client.EjercicioService;
import com.uo226321.joel.gymnastics.client.RutinaService;
import com.uo226321.joel.gymnastics.model.Rutina;
import com.uo226321.joel.gymnastics.view.MusculosActivity;
import com.uo226321.joel.gymnastics.viewmodel.contracts.MainResultCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joel on 12/04/2018.
 */

public class MainViewModel extends BaseObservable {
    private Context context;
    private SharedPreferences prefs;
    private Gson gson;
    private MainResultCallback mDataListener;
    public static final String RUTINA_REST_API = "https://gimnsatics.herokuapp.com/rest/RutinaServiceRs/";
    private Rutina rutinaActual;
    private ProgressDialog dialogLoading;

    public MainViewModel(Context context, @NonNull final MainResultCallback mainDataListener) {
        this.context = context;
        mDataListener = mainDataListener;
        prefs = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        gson = new Gson();
        checkNewUser();
        loadNumberTabs();
    }

    private void loadNumberTabs() {
        if (getRutinaActual() != null) {
            mDataListener.loadTabs(getRutinaActual().getDiasEntrenamiento().size());
        }
    }

    private void checkNewUser() {
        if (prefs.getString("rutina", null) != null) {
            String rutina_json = prefs.getString("rutina", null);
            rutinaActual = gson.fromJson(rutina_json, Rutina.class);
            mDataListener.loadRutina(rutinaActual);

        } else {
            if (prefs.getBoolean("Personalizar", false)) {
                checkRutinaUser();

            } else {
                chooseRutina(prefs.getString("Complexión", ""), prefs.getString("Objetivo", ""));
            }
        }
    }

    public void chooseRutina(String tipoCuerpo, String objetivo) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RUTINA_REST_API)
                .addConverterFactory(GsonConverterFactory.create()).build();
        //Create service
        RutinaService us = retrofit.create(RutinaService.class);
        Call<Rutina> gu = us.findRutinaBySomObj(tipoCuerpo, objetivo);
        new FindRutinaCaracsEndpoint(this).execute(gu);
    }


    public void checkRutinaUser() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RUTINA_REST_API)
                .addConverterFactory(GsonConverterFactory.create()).build();
        //Create service
        RutinaService us = retrofit.create(RutinaService.class);
        Call<Rutina> gu = us.findRutinaByUser("Bearer " + prefs.getString("Token", ""), prefs.getString("Nick", ""));
        new FindRutinaByUser(this).execute(gu);
    }

    public void loadMusculos() {
        dialogLoading = ProgressDialog.show(context, "",
                "Cargando...", true);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gimnsatics.herokuapp.com/rest/EjercicioServiceRs/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        //Create service
        EjercicioService es = retrofit.create(EjercicioService.class);
        Call<List<String>> gu = es.findMusculos("Bearer " + prefs.getString("Token", ""));
        new FindMusculosEndpoint(this).execute(gu);


    }


    public void changeRutina(Rutina rutina) {
        SharedPreferences.Editor editor = prefs.edit();
        if (rutinaActual != null) {
            if (!rutinaActual.getNombre().equals(rutina.getNombre())) {
                editor.putString("rutina", gson.toJson(rutina));
                editor.commit();
                showRestartDialog("Cambio de rutina", "¡Enhorabuena! Ya has pasado al siguiente nivel. " +
                        "Reinicia la aplicación para obtener tu nueva rutina.");
            }
        } else {
            editor.putString("rutina", gson.toJson(rutina));
            editor.commit();
            mDataListener.restartMainActivity();

        }
    }

    public void checkRutinaPersonalizada(Rutina rutina) {
        if (rutina != null) {
            SharedPreferences.Editor editor = prefs.edit();
            if (rutinaActual == null) {
                showRestartDialog("Asignacion de rutina", "Te han asignado tu nueva rutina. " +
                        "Reinicia la aplicación para obtener tu nueva rutina.");
                editor.putString("rutina", gson.toJson(rutina));
                editor.commit();
            } else {
                if (!rutinaActual.getNombre().equals(rutina.getNombre())) {
                    showRestartDialog("Asignacion de rutina", "Te han asignado tu nueva rutina. " +
                            "Reinicia la aplicación para obtener tu nueva rutina.");
                    editor.putString("rutina", gson.toJson(rutina));
                    editor.commit();
                }
            }
        } else {
            showNormalDialog("Rutina no asignada", "Has seleccionado personalizar tu rutina. Esto significa que deberas contactar con el entrenador de tu gimnasio " +
                    "para que te asigne una rutina.");

        }
    }

    public void showRestartDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Reiniciar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDataListener.restartMainActivity();
            }
        });

        alertDialogBuilder.show();
    }

    public void showNormalDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        alertDialogBuilder.show();
    }


    public void callActivityMusculos(ArrayList<String> musculos) {
        Intent intent = new Intent(context, MusculosActivity.class);
        intent.putExtra("musculos", musculos);
        dialogLoading.dismiss();
        context.startActivity(intent);
    }

    public Rutina getRutinaActual() {
        return rutinaActual;
    }
}
