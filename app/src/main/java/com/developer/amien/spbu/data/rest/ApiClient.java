package com.developer.amien.spbu.data.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    public static final String BASE_URL = "http://192.168.43.126/project_spbu_api-master/";



    private static Retrofit user = null;
    public static Retrofit Getuser() {
        if (user==null) {
            user = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return user;
    }

    private static Retrofit spbu = null;
    public static Retrofit GetSpbu() {
        if (spbu==null) {
            spbu = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return spbu;
    }

    private static Retrofit kendaraan = null;
    public static Retrofit GetKendaraan() {
        if (kendaraan==null) {
            kendaraan = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return kendaraan;
    }

    private static Retrofit fasilitas = null;
    public static Retrofit GetFasilitas() {
        if (fasilitas==null) {
            fasilitas = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return fasilitas;
    }

    private static Retrofit ken_user = null;
    public static Retrofit GetkenUser() {
        if (ken_user==null) {
            ken_user = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return ken_user;
    }

    private static Retrofit fasilitas_ken = null;
    public static Retrofit GetFasilitas_ken() {
        if (fasilitas_ken==null) {
            fasilitas_ken = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return fasilitas_ken;
    }

    private static Retrofit rating = null;
    public static Retrofit GetRating() {
        if (rating==null) {
            rating = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return rating;
    }
}
