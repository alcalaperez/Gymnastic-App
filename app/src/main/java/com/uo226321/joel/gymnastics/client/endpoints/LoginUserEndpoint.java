package com.uo226321.joel.gymnastics.client.endpoints;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.uo226321.joel.gymnastics.model.Usuario;
import com.uo226321.joel.gymnastics.viewmodel.LoginViewModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class LoginUserEndpoint extends AsyncTask<Call, Void, String> {

    private Usuario usuario;
    private LoginViewModel lvm;
    private String token;

    public LoginUserEndpoint(LoginViewModel lvm) {
        this.lvm = lvm;
    }


    @Override
    protected String doInBackground(Call[] calls) {
        try {
            Call<ResponseBody> gu = calls[0];
            Response<ResponseBody> responseBody = gu.execute();

            token =  responseBody.headers().get("Token");
            Gson gson = new Gson();
            if(responseBody.body() != null) {
                usuario = gson.fromJson(responseBody.body().string(), Usuario.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        lvm.checkUser(usuario, token);

    }
}