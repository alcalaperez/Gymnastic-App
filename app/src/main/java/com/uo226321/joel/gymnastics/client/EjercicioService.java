package com.uo226321.joel.gymnastics.client;

import com.uo226321.joel.gymnastics.model.Ejercicio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface EjercicioService {
    @Headers({ "Accept: application/json" })
    @GET("findMusculos")
    Call<List<String>> findMusculos(@Header("Authorization") String lang);

    @Headers({ "Accept: application/json" })
    @GET("findByMusculo/{musculo}")
    Call<List<Ejercicio>> findByMusculos(@Header("Authorization") String lang, @Path("musculo") String musculo);

    @Headers({ "Accept: application/json" })
    @GET("findByName/{nombre}")
    Call<Ejercicio> findByName(@Header("Authorization") String lang, @Path("nombre") String name);


}