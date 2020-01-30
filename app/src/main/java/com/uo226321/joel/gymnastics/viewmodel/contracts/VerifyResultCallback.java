package com.uo226321.joel.gymnastics.viewmodel.contracts;

/**
 * Created by Joel on 12/04/2018.
 */

public interface VerifyResultCallback {
    void onSuccess(String s);
    void showProgress(boolean mostrar);
}