package com.example.alqudsapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiClass {

    @Headers("Content-Type: application/json")
    @GET("everything?")
    Call<DataItem> getLastNews(
            @Header("User-Agent") String Agent,
            @Query("q") String q,
            @Query("from") String from,
            @Query("to") String to,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey

    );
}
