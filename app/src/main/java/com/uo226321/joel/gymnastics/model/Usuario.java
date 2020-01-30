package com.uo226321.joel.gymnastics.model;

import android.databinding.BaseObservable;

import java.io.Serializable;


public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userid;
    private String password;
    private String rutinaasig;
    private int altura;
    private int peso;
    private int edad;
    private int gymId;
    private boolean personalizar;
    private String somatotipo;
    private String objetivo;

    public Usuario() {

    }

    public Usuario(String userid, String password, String rutinaasig, int altura, int peso, boolean personalizar, int gymId, String somatotipo, String objetivo, int edad) {
        this.userid = userid;
        this.password = password;
        this.rutinaasig = rutinaasig;
        this.altura = altura;
        this.peso = peso;
        this.edad = edad;
        this.setPersonalizar(personalizar);
        this.gymId = gymId;
        this.setObjetivo(objetivo);
        this.setSomatotipo(somatotipo);
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRutinaasig() {
        return rutinaasig;
    }

    public void setRutinaasig(String rutinaasig) {
        this.rutinaasig = rutinaasig;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public boolean isPersonalizar() {
        return personalizar;
    }

    public void setPersonalizar(boolean personalizar) {
        this.personalizar = personalizar;
    }

    public int getGymId() {
        return gymId;
    }

    public void setGymId(int gymId) {
        this.gymId = gymId;
    }

    public String getSomatotipo() {
        return somatotipo;
    }

    public void setSomatotipo(String somatotipo) {
        this.somatotipo = somatotipo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + altura;
        result = prime * result + gymId;
        result = prime * result + ((objetivo == null) ? 0 : objetivo.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + (personalizar ? 1231 : 1237);
        result = prime * result + peso;
        result = prime * result + ((rutinaasig == null) ? 0 : rutinaasig.hashCode());
        result = prime * result + ((somatotipo == null) ? 0 : somatotipo.hashCode());
        result = prime * result + ((userid == null) ? 0 : userid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (altura != other.altura)
            return false;
        if (gymId != other.gymId)
            return false;
        if (objetivo == null) {
            if (other.objetivo != null)
                return false;
        } else if (!objetivo.equals(other.objetivo))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (personalizar != other.personalizar)
            return false;
        if (peso != other.peso)
            return false;
        if (rutinaasig == null) {
            if (other.rutinaasig != null)
                return false;
        } else if (!rutinaasig.equals(other.rutinaasig))
            return false;
        if (somatotipo == null) {
            if (other.somatotipo != null)
                return false;
        } else if (!somatotipo.equals(other.somatotipo))
            return false;
        if (userid == null) {
            if (other.userid != null)
                return false;
        } else if (!userid.equals(other.userid))
            return false;
        return true;
    }


    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
