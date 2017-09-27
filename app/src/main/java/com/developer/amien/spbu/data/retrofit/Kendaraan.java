package com.developer.amien.spbu.data.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Kendaraan {

    @SerializedName("id_kendaraan")
    @Expose
    private String id_kendaraan;

    @SerializedName("nama_kendaraan")
    @Expose
    private String nama_kendaraan;

    @SerializedName("jarak")
    @Expose
    private String jarak;

    @SerializedName("merek")
    @Expose
    private String merek;

    public Kendaraan(String id_kendaraan, String nama_kendaraan, String jarak, String merek) {
        this.id_kendaraan = id_kendaraan;
        this.nama_kendaraan = nama_kendaraan;
        this.jarak = jarak;
        this.merek = merek;
    }

    public String getId_kendaraan() {
        return id_kendaraan;
    }

    public void setId_kendaraan(String id_kendaraan) {
        this.id_kendaraan = id_kendaraan;
    }

    public String getNama_kendaraan() {
        return nama_kendaraan;
    }

    public void setNama_kendaraan(String nama_kendaraan) {
        this.nama_kendaraan = nama_kendaraan;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }
}
