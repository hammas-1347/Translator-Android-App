package com.translator;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL=
                "https://translate.googleapis.com/";

    public APIService getTranslation()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(makeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        return service;
    }
    private OkHttpClient makeOkHttpClient()
    {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .build();
    }
}
