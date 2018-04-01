package com.example.android.saveme;

import android.app.Application;

import com.example.android.saveme.common.network.API;
import com.example.android.saveme.common.utils.Constants;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by harshika on 1/4/18.
 * The main application class
 */

public class SaveMeApplication extends Application {

    private static API api;

    @Override
    public void onCreate() {
        super.onCreate();

        File cacheDirectory = new File(getApplicationContext().getCacheDir(), "cache");

        // 10 MB cache
        Cache cache = new Cache(cacheDirectory, (10 * 1024 * 1024));

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);
    }

    public static API getAPI() {
        return api;
    }
}
