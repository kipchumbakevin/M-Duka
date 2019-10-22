package com.example.shopkipa.networking;

import android.content.Context;

import com.example.shopkipa.utils.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient mInstance;
    private static final String BaseUrl = Constants.BASE_URL;
    private Retrofit retrofit;
    private RetrofitClient(Context context) {
        final String accessToken;

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        return chain.proceed(request);
                    }
                });
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(okHttpClient.build())
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
