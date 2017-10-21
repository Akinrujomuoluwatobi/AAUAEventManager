package com.royalteck.progtobi.aauaeventmanager;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import java.util.concurrent.TimeUnit;

import com.royalteck.progtobi.aauaeventmanager.Util.NetworkInterceptor;
import okhttp3.OkHttpClient;
//import retrofit.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.royalteck.progtobi.aauaeventmanager.Util.Data.BASE_URL;


public class EventManagerApp extends Application {

    private static Retrofit retrofit = null;
    private OkHttpClient client;
    private static EventManagerApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRetrofit();
    }

    public static EventManagerApp getInstance() {
        if (instance == null) {
            instance = new EventManagerApp();
        }
        return instance;
    }


    /**
     * Setup retrofit and cache
     */
    private void initRetrofit() {
        // Cache cache = new Cache(getCacheDir(), 1024);
        client = new OkHttpClient.Builder()
                .addInterceptor(new NetworkInterceptor())
                // .cache(cache)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).client(client)
                .build();
    }

    /**
     * @return the Retrofit Rest Api Service
     */
    public static APIService getApiService() {
        return retrofit.create(APIService.class);
    }


    public boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenet = connectivityManager.getActiveNetworkInfo();
        return activenet != null;
    }
}
