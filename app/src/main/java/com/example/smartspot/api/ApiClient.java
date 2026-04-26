package com.example.smartspot.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // 1. Emulator URL
    private static final String EMULATOR_URL = "http://10.0.2.2:3000/";

    // 2. Real phone URL (Update this with your PC's IP)
    private static final String REAL_DEVICE_URL = "http://10.98.104.72:3000/";

    // 👉 CHANGE THIS TO EITHER EMULATOR_URL OR REAL_DEVICE_URL
    private static final String BASE_URL = REAL_DEVICE_URL;

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