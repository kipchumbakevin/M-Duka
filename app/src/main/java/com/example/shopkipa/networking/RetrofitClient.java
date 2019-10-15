package com.example.shopkipa.networking;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient mInstance;
    private static final String BaseUrl = "http://192.168.42.230:8000/";
    private Retrofit retrofit;
    private RetrofitClient(Context context){
        final String accessToken;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        OkHttpClient.Builder okHttpBuilder=new OkHttpClient.Builder();
        retrofit=new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static  synchronized RetrofitClient getInstance(Context context){
        if(mInstance==null){
            mInstance=new RetrofitClient(context);
        }
        return mInstance;
    }

    public JsonPlaceHolderInterface getApiConnector(){
        return retrofit.create(JsonPlaceHolderInterface.class);
    }


}
