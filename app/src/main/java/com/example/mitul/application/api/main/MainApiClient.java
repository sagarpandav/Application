package com.example.mitul.application.api.main;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApiClient {

    @GET("Fare/fare")
    Call<Object> getData(@Query("id1") String source, @Query("id2") String destination);

    @GET("Location/location?b_id=102")
    Call<Object> getlatlon();

    @GET("Bus/bus")
    Call<Object> getBusList(@Query("source") String source, @Query("destination") String destination);
}
