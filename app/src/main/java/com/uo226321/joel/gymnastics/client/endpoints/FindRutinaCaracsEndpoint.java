package com.uo226321.joel.gymnastics.client.endpoints;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.uo226321.joel.gymnastics.view.RegisterActivity;
import com.uo226321.joel.gymnastics.model.Rutina;
import com.uo226321.joel.gymnastics.viewmodel.MainViewModel;

import java.io.IOException;

import retrofit2.Call;

public class FindRutinaCaracsEndpoint extends AsyncTask<Call, Void, String> {

    private RegisterActivity registerActivity;
    private MainViewModel mvm;

    private SharedPreferences prefs;
    private Rutina rutina;

    public FindRutinaCaracsEndpoint(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
        prefs = registerActivity.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

    }

    public FindRutinaCaracsEndpoint(MainViewModel mvm) {
        this.mvm = mvm;
    }


    @Override
    protected String doInBackground(Call[] calls) {
        try {
            Call<Rutina> gu = calls[0];

            rutina = gu.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (registerActivity != null) {
            SharedPreferences.Editor editor = prefs.edit();
            Gson gson = new Gson();
            if(!prefs.getBoolean("Personalizar", false)){
                registerActivity.getUsuario().setRutinaasig(rutina.getNombre());
                editor.putString("rutina", gson.toJson(rutina));
                editor.commit();
                registerActivity.createUser();

            } else {
                registerActivity.createUser();

            }


        } else if(mvm != null) {
            mvm.changeRutina(rutina);

        }

    }
}