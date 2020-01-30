package com.uo226321.joel.gymnastics.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.uo226321.joel.gymnastics.model.Usuario;
import com.uo226321.joel.gymnastics.viewmodel.contracts.LoginResultCallback;
import com.uo226321.joel.gymnastics.viewmodel.contracts.UserResultCallback;

/**
 * Created by Joel on 13/04/2018.
 */

public class UserViewModel extends BaseObservable {
    private UserResultCallback mDataListener;
    private Context context;
    private SharedPreferences prefs;
    private String peso;
    private String edad;
    private String altura;
    private String imcInfo;
    private String ingesta;
    private String nombre;

    public UserViewModel(@NonNull Context context, @NonNull final UserResultCallback userDataListener) {
        mDataListener = userDataListener;
        this.context = context;
        prefs =  context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        loadInformation();
    }

    private void loadInformation() {
        double calculoPeso = Double.parseDouble(prefs.getString("Peso", "No ha introducido ningun peso"));
        double calculoEdad = Double.parseDouble(prefs.getString("Edad", "No ha introducido ningun altura"));
        double calculoAltura = Double.parseDouble(prefs.getString("Altura", "No ha introducido ninguna altura")) / 100;
        String complexionElegida = prefs.getString("Complexión", null);
        String objetivoElegido = prefs.getString("Objetivo", null);

        peso = Math.round(calculoPeso) + "kg";
        altura = Math.round(calculoAltura*100) + "cms";
        edad = Math.round(calculoEdad) + " años";
        nombre = prefs.getString("Nick", "No ha introducido ningun nombre");


        double imc = calculoPeso / (calculoAltura * calculoAltura);
        double tmb = calculoPeso * 10 + calculoAltura * 100 * 6.25 - calculoEdad * 5 + 5;

        mDataListener.setImcLine(imc);

        tmb = tmb * 1.375;
        tmb = Math.round(tmb * 100.0) / 100.0;

        if (objetivoElegido.equals("Aumento de masa muscular") || objetivoElegido.equals("Ganancia de masa muscular")) {
            setIngesta("Su ingesta calórica debe ser de " + (int) (tmb + 400) + " kcal para lograr su objetivo");
        } else if (objetivoElegido.equals("Pérdida de grasa")) {
            setIngesta("Su ingesta calórica debe ser de " + (int) (tmb - 450) + " kcal para lograr su objetivo");
        } else {
            setIngesta("Su ingesta calórica debe ser de " + (int) tmb + " kcal para lograr su objetivo");
        }


        if(imc < 18.5) {
            setImc("Tiene un peso inferior al recomendado.");
        } else if(imc >= 18.5 && imc < 25) {
            setImc("Tiene un peso apropiado.");
        } else if(imc >= 25 && imc < 30) {
            if(complexionElegida.equals("Mesomorfo")) {
                setImc("Tiene un peso apropiado.");
            } else {
                setImc("Tiene un peso superior al recomendado.");
            }
        } else if(imc >= 30 && imc < 40) {
            if(complexionElegida.equals("Mesomorfo")) {
                setImc("Tiene un peso superior al recomendado.");
            } else {
                setImc("Tiene sobrepeso.");
            }
        } else if(imc >= 40) {
            if(complexionElegida.equals("Mesomorfo")) {
                setImc("Tiene sobrepeso.");
            } else {
                setImc("Tiene un peso muy superior al recomendado.");
            }
        }
    }


    public void setPeso(String peso) {
        this.peso = peso;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public void setImc(String imcInfo) {
        this.imcInfo = imcInfo;
    }

    public void setIngesta(String ingesta) {
        this.ingesta = ingesta;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Bindable
    public String getPeso() {
        return peso;
    }
    @Bindable
    public String getEdad() {
        return edad;
    }
    @Bindable
    public String getAltura() {
        return altura;
    }
    @Bindable
    public String getImc() {
        return imcInfo;
    }
    @Bindable
    public String getIngesta() {
        return ingesta;
    }
    @Bindable
    public String getNombre() {
        return nombre;
    }
}
