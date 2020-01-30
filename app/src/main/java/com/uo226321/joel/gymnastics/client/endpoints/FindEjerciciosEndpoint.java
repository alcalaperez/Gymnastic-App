package com.uo226321.joel.gymnastics.client.endpoints;

import android.content.Intent;
import android.os.AsyncTask;

import com.uo226321.joel.gymnastics.view.EjerciciosActivity;
import com.uo226321.joel.gymnastics.view.MusculosActivity;
import com.uo226321.joel.gymnastics.model.Ejercicio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class FindEjerciciosEndpoint extends AsyncTask<Call, Void, String> {

    private static final String URI_BOOK = "https://gimnsatics.herokuapp.com/rest/UserServiceRs/";
    private List<Ejercicio> ejercicios;
    private MusculosActivity musculosActivity;

    public FindEjerciciosEndpoint(MusculosActivity musculosActivity) {
        this.musculosActivity = musculosActivity;

    }


    @Override
    protected String doInBackground(Call[] calls) {
        try {
            Call<List<Ejercicio>> gu = calls[0];
            Response s = gu.execute();
            ejercicios = (List<Ejercicio>) s.body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent(musculosActivity, EjerciciosActivity.class);
        intent.putExtra("ejercicios", (ArrayList<Ejercicio>) ejercicios);
        musculosActivity.dialogLoading.dismiss();
        musculosActivity.startActivity(intent);
    }


}