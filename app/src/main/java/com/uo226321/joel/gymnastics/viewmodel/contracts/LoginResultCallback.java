package com.uo226321.joel.gymnastics.viewmodel.contracts;

/**
 * Created by Joel on 12/04/2018.
 */

public interface LoginResultCallback {
    void onSuccess(String s);
    void onUserError(String s);
    void onPasswordError(String s);
    void showProgress(boolean mostrar);
    void onError(String s);
    void onRegister();

}