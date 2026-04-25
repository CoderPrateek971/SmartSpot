package com.example.smartspot.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final boolean USE_REAL_DEVICE = false;

    // Emulator (Android Studio)
    private static final String EMULATOR_URL = "http://10.0.2.2:3000/";

    // Real phone (your PC IP)
    private static final String REAL_DEVICE_URL = "http://10.98.104.72:3000/";

    // Final BASE URL (only ONE declaration)
    private static final String BASE_URL = USE_REAL_DEVICE ? REAL_DEVICE_URL : EMULATOR_URL;

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}