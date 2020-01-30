package com.uo226321.joel.gymnastics.client.endpoints;

import android.os.AsyncTask;

import com.uo226321.joel.gymnastics.model.Rutina;
import com.uo226321.joel.gymnastics.viewmodel.MainViewModel;

import java.io.IOException;

import retrofit2.Call;

public class FindRutinaByUser extends AsyncTask<Call, Void, String> {

    private MainViewModel mvm;
    private Rutina rutina;

    public FindRutinaByUser(MainViewModel mvm) {
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
        mvm.checkRutinaPersonalizada(rutina);



    }
}