package com.uo226321.joel.gymnastics.client.endpoints;

import android.content.Intent;
import android.os.AsyncTask;

import com.uo226321.joel.gymnastics.view.EjercicioMostrarActivity;
import com.uo226321.joel.gymnastics.view.EjerciciosActivity;
import com.uo226321.joel.gymnastics.model.Ejercicio;
import com.uo226321.joel.gymnastics.view.MainActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class FindEjercicioByNameEndpoint extends AsyncTask<Call, Void, String> {

    private Ejercicio ejercicio;
    private EjerciciosActivity ejerciciosActivity;
    private MainActivity mainActivity;

    public FindEjercicioByNameEndpoint(EjerciciosActivity ejerciciosActivity) {
        this.ejerciciosActivity = ejerciciosActivity;

    }

    public FindEjercicioByNameEndpoint(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

    }


    @Override
    protected String doInBackground(Call[] calls) {
        try {
            Call<Ejercicio> gu = calls[0];
            Response s = gu.execute();
            ejercicio = (Ejercicio) s.body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if(mainActivity != null) {
            Intent intent = new Intent(mainActivity, EjercicioMostrarActivity.class);
            intent.putExtra("ejercicio", ejercicio);
            mainActivity.dialogLoading.dismiss();
            mainActivity.startActivity(intent);
        } else {
            Intent intent = new Intent(ejerciciosActivity, EjercicioMostrarActivity.class);
            intent.putExtra("ejercicio", ejercicio);
            ejerciciosActivity.dialogLoading.dismiss();
            ejerciciosActivity.startActivity(intent);
        }
    }


}