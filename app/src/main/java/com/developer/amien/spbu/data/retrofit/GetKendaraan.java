package com.developer.amien.spbu.data.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetKendaraan {

    @SerializedName("status")
    @Expose
    private String status;

    private List<Kendaraan> kendaraan=null;

    public GetKendaraan(String status, List<Kendaraan> kendaraan) {
        this.status = status;
        this.kendaraan = kendaraan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Kendaraan> getKendaraan() {
        return kendaraan;
    }

    public void setKendaraan(List<Kendaraan> kendaraan) {
        this.kendaraan = kendaraan;
    }
}
