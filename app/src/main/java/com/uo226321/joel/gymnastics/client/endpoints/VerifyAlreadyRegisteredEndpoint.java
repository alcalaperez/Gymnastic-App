package com.uo226321.joel.gymnastics.client.endpoints;

import android.os.AsyncTask;

import com.uo226321.joel.gymnastics.viewmodel.VerifyViewModel;

import java.io.IOException;

import retrofit2.Call;

public class VerifyAlreadyRegisteredEndpoint extends AsyncTask<Call, Void, String> {

    private Boolean u;
    private VerifyViewModel vvm;

    public VerifyAlreadyRegisteredEndpoint(VerifyViewModel vvm) {
        this.vvm = vvm;

    }


    @Override
    protected String doInBackground(Call[] calls) {
        try {
            Call<Boolean> gu = calls[0];

            u = gu.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        vvm.checkAlreadyRegistered(u);



    }
}