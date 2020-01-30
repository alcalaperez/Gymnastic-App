package com.uo226321.joel.gymnastics.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.uo226321.joel.gymnastics.R;
import com.uo226321.joel.gymnastics.client.endpoints.VerifyAlreadyRegisteredEndpoint;
import com.uo226321.joel.gymnastics.client.endpoints.VerifyUserEndpoint;
import com.uo226321.joel.gymnastics.client.UserService;
import com.uo226321.joel.gymnastics.view.FormActivity;
import com.uo226321.joel.gymnastics.model.GymUser;
import com.uo226321.joel.gymnastics.model.Usuario;
import com.uo226321.joel.gymnastics.view.LoginActivity;
import com.uo226321.joel.gymnastics.viewmodel.contracts.VerifyResultCallback;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joel on 13/04/2018.
 */

public class VerifyViewModel extends BaseObservable {
    private Context context;
    private VerifyResultCallback mDataListener;
    private String key1;
    private String key2;
    private String key3;
    private String key4;
    private String numberCard;
    private Usuario usuario;

    public VerifyViewModel(@NonNull Context context, @NonNull final VerifyResultCallback verifyDataListener) {
        this.context = context;
        mDataListener = verifyDataListener;
        usuario = new Usuario();
    }


    @Bindable
    public String getNumberCard() {
        return numberCard;
    }

    @Bindable
    public String getKey1() {
        return key1;
    }

    @Bindable
    public String getKey2() {
        return key2;
    }

    @Bindable
    public String getKey3() {
        return key3;
    }

    @Bindable
    public String getKey4() {
        return key4;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }


    public void setKey1(String key1) {
        this.key1 = key1;
    }


    public void setKey2(String key2) {
        this.key2 = key2;
    }


    public void setKey3(String key3) {
        this.key3 = key3;
    }


    public void setKey4(String key4) {
        this.key4 = key4;
    }

    public View.OnClickListener onVerifyButtonClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataListener.showProgress(true);
                verify();
            }
        };
    }


    public void verify() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gimnsatics.herokuapp.com/rest/UserServiceRs/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        //Create service
        UserService us = retrofit.create(UserService.class);
        if (checkEmptyFields()) {
            showDialog("Datos introducidos vacios", context.getResources().getString(R.string.emptyVerifyDialog));
            mDataListener.showProgress(false);
        } else {
            String key = getKey1().toString() + "-" + getKey2().toString() + "-" + getKey3().toString() + "-" + getKey4().toString();
            Call<GymUser> gu = us.verifyUser(Integer.parseInt(getNumberCard().toString()), key);
            new VerifyUserEndpoint(this).execute(gu);
        }
    }

    private boolean checkEmptyFields() {
        if (getNumberCard() == null || getKey1() == null
                || getKey2() == null || getKey3() == null
                || getKey4() == null) {
            return true;
        }
        return false;
    }

    public void alreadyRegistered() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gimnsatics.herokuapp.com/rest/UserServiceRs/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        //Create service
        UserService us = retrofit.create(UserService.class);
        Call<Boolean> gu = us.verifyAlreadyRegistered(Integer.parseInt(getNumberCard().toString()));
        new VerifyAlreadyRegisteredEndpoint(this).execute(gu);
    }


    private void showDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialogBuilder.show();
    }

    public void checkGymUser(GymUser g) {
        if (g != null) {
            alreadyRegistered();

        } else {
            showDialog("Verificación", "La key proporcionada es incorrecta.");
            mDataListener.showProgress(false);
        }
    }

    public void callForm() {
        usuario.setGymId(Integer.parseInt(getNumberCard().toString()));
        Intent intent = new Intent(context, FormActivity.class);
        intent.putExtra("usuario", usuario);
        context.startActivity(intent);
    }

    public void callLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public void checkAlreadyRegistered(Boolean u) {
        if (!u) {
            Toast.makeText(context,
                    "Verificado", Toast.LENGTH_SHORT).show();
            usuario.setGymId(Integer.parseInt(getNumberCard().toString()));
            mDataListener.onSuccess("");
            callForm();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Verificación");
            alertDialogBuilder.setMessage("Usted ya esta tiene una cuenta asociada a su tarjeta de socio. " +
                    "Si ha olvidado su contraseña, contacte con el encargado de su gimnasio.");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Volver al inicio", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    callLogin();
                }
            });

            mDataListener.showProgress(false);
            alertDialogBuilder.show();
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
