package com.uo226321.joel.gymnastics.client.endpoints;

import android.os.AsyncTask;
import android.widget.Toast;

import com.uo226321.joel.gymnastics.view.RegisterActivity;

import java.io.IOException;

import retrofit2.Call;

public class VerifyLoginNameEndpoint extends AsyncTask<Call, Void, String> {

    private Boolean u;
    private RegisterActivity registerActivity;

    public VerifyLoginNameEndpoint(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;

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
        if (!u) {
            Toast.makeText(registerActivity,
                    "Nombre no escogido", Toast.LENGTH_SHORT).show();
            registerActivity.chooseRutina();
        } else {
            Toast.makeText(registerActivity,
                    "Nombre escogido", Toast.LENGTH_SHORT).show();

            registerActivity.showProgress(false);
        }
    }
}