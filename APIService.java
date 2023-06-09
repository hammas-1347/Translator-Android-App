package com.translator;


import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @POST("translate_a/single?client=gtx&dt=t&ie=UTF-8&oe=UTF-8")
    Call<JsonArray> getTranslation(
            @Query("sl") String source_l,
            @Query("tl") String target_l,
            @Query("q") String query_l);
}
