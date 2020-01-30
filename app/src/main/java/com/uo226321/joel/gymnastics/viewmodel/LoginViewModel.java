package com.uo226321.joel.gymnastics.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.view.View;

import com.uo226321.joel.gymnastics.R;
import com.uo226321.joel.gymnastics.client.endpoints.LoginUserEndpoint;
import com.uo226321.joel.gymnastics.client.UserService;
import com.uo226321.joel.gymnastics.model.Usuario;
import com.uo226321.joel.gymnastics.viewmodel.contracts.LoginResultCallback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joel on 12/04/2018.
 */

public class LoginViewModel extends BaseObservable {
    private Usuario usuario;
    private SharedPreferences prefs;
    private LoginResultCallback mDataListener;
    private Context context;
    public static final String USER_REST_API = "https://gimnsatics.herokuapp.com/rest/UserServiceRs/";

    public LoginViewModel(@NonNull Context context, @NonNull final LoginResultCallback loginDataListener) {
        mDataListener = loginDataListener;
        usuario = new Usuario();
        this.context = context;
        prefs =  context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        checkLogueado();
    }


    private void checkLogueado() {
        if(prefs.getBoolean("Logeado", false)) {
            mDataListener.onSuccess("Logueado anteriormente");

        }
    }

    @Bindable
    public String getUser() {
        return usuario.getUserid();
    }

    @Bindable
    public String getPassword() {
        return usuario.getPassword();
    }

    public void setUser(String user) {
        usuario.setUserid(user);
    }

    public void setPassword(String password) {
        usuario.setPassword(password);
    }

    public View.OnClickListener onLoginButtonClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        };
    }

    public View.OnClickListener onRegisterButtonClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataListener.onRegister();
            }
        };
    }



    private void attemptLogin() {
        boolean cancel = false;
        if (getPassword() == null) {
            mDataListener.onPasswordError(context.getString(R.string.error_field_required));
            cancel = true;
        }
        if (getUser() == null) {
            mDataListener.onUserError(context.getString(R.string.error_field_required));
            cancel = true;
        }

        if(!cancel) {
            mDataListener.showProgress(true);
            callApiLogin();
        }
    }

    private void callApiLogin() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(USER_REST_API)
                .addConverterFactory(GsonConverterFactory.create()).build();
        //Create service
        UserService us = retrofit.create(UserService.class);
        Call<ResponseBody> gu = us.findLoggableUser(getUser(), getPassword());
        new LoginUserEndpoint(this).execute(gu);
    }


    public void checkUser(Usuario usuario, String token) {
        if (usuario != null) {
            saveLoginInfo(usuario, token);
            mDataListener.onSuccess("Logueado correctamente");
        } else {
            mDataListener.onError("Credenciales incorrectas");
        }
        mDataListener.showProgress(false);
    }


    private void saveLoginInfo(Usuario usuario,String token) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("Logeado", true);
        editor.putString("Token", token);

        if (prefs.getString("Complexión", null) == null) {
            editor.putString("Complexión", usuario.getSomatotipo());
            editor.putString("Objetivo", usuario.getObjetivo());
            editor.putString("Nick", usuario.getUserid());
            editor.putString("Peso", "" + usuario.getPeso());
            editor.putString("Altura", "" + usuario.getAltura());
            editor.putString("Edad", "" + usuario.getEdad());
            editor.putBoolean("Personalizar", usuario.isPersonalizar());

        }
        editor.commit();
    }
}
