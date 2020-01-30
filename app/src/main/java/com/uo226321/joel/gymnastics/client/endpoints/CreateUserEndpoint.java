package com.uo226321.joel.gymnastics.client.endpoints;

import android.os.AsyncTask;
import android.widget.Toast;

import com.uo226321.joel.gymnastics.view.RegisterActivity;

import java.io.IOException;

import retrofit2.Call;

public class CreateUserEndpoint extends AsyncTask<Call, Void, String> {

    private RegisterActivity registerActivity;

    public CreateUserEndpoint(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;

    }


    @Override
    protected String doInBackground(Call[] calls) {
        try {
            Call<Void> gu = calls[0];

            gu.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(registerActivity,
                "Registrado", Toast.LENGTH_SHORT).show();
        registerActivity.showProgress(false);
        registerActivity.callLogin();


    }
}