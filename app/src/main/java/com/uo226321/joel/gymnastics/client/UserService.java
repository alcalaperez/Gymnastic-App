package com.uo226321.joel.gymnastics.client;

import com.uo226321.joel.gymnastics.model.GymUser;
import com.uo226321.joel.gymnastics.model.Usuario;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @Headers({ "Accept: application/json" })
    @GET("verify/{numeroSocio}/{key}")
    Call<GymUser> verifyUser(@Path("numeroSocio") int numeroSocio,
                             @Path("key") String key);

    @Headers({ "Accept: application/json" })
    @GET("findLog/{login}/{password}")
    Call<ResponseBody> findLoggableUser(@Path("login") String login,
                                    @Path("password") String password);

    @PUT("createUser")
    public Call<Void> createUser(@Body Usuario user);

    @Headers({ "Accept: application/json" })
    @GET("alreadyReg/{numeroSocio}")
    Call<Boolean> verifyAlreadyRegistered(@Path("numeroSocio") int numeroSocio);

    @Headers({ "Accept: application/json" })
    @GET("alreadyTak/{login}")
    Call<Boolean> verifyAlreadyTaken(@Path("login") String login);

}