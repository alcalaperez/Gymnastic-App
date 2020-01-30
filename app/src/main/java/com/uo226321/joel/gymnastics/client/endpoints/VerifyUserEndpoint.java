package com.uo226321.joel.gymnastics.client.endpoints;

import android.os.AsyncTask;

import com.uo226321.joel.gymnastics.model.GymUser;
import com.uo226321.joel.gymnastics.viewmodel.VerifyViewModel;

import java.io.IOException;

import retrofit2.Call;

public class VerifyUserEndpoint extends AsyncTask<Call, Void, String> {

    private GymUser g;
    private VerifyViewModel vvm;

    public VerifyUserEndpoint(VerifyViewModel vvm) {
        this.vvm = vvm;

    }


    @Override
    protected String doInBackground(Call[] calls) {
        try {
            Call<GymUser> gu = calls[0];

            g = gu.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        vvm.checkGymUser(g);

    }
}