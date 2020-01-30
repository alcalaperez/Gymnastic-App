package com.uo226321.joel.gymnastics.client;

import com.uo226321.joel.gymnastics.model.Rutina;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface RutinaService {
    @Headers({ "Accept: application/json" })
    @GET("findAssignedRutina/{somatotipo}/{objetivo}")
    Call<Rutina> findRutinaBySomObj(@Path("somatotipo") String somatotipo,
                                          @Path("objetivo") String objetivo);

    @Headers({ "Accept: application/json" })
    @GET("findRutinaByUser/{user}")
    Call<Rutina> findRutinaByUser(@Header("Authorization") String lang, @Path("user") String user);

}