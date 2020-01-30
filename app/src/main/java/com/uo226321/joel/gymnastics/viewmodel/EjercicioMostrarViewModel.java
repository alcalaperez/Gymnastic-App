package com.uo226321.joel.gymnastics.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.uo226321.joel.gymnastics.model.Ejercicio;
import com.uo226321.joel.gymnastics.viewmodel.contracts.MostrarEjercicioResultCallback;
import com.uo226321.joel.gymnastics.viewmodel.contracts.UserResultCallback;

/**
 * Created by Joel on 13/04/2018.
 */

public class EjercicioMostrarViewModel extends BaseObservable {
    private MostrarEjercicioResultCallback mDataListener;
    private Ejercicio ejercicio;
    private String descripcion;
    private String consejos;

    public EjercicioMostrarViewModel(@NonNull Ejercicio ejercicio, @NonNull final MostrarEjercicioResultCallback mostrarEjercicioResultCallback) {
        mDataListener = mostrarEjercicioResultCallback;
        this.ejercicio = ejercicio;
        descripcion = ejercicio.getDescripcion();
        consejos = ejercicio.getConsejo();
    }


    @Bindable
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Bindable
    public String getConsejos() {
        return consejos;
    }

    public void setConsejos(String consejos) {
        this.consejos = consejos;
    }
}
