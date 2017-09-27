package com.developer.amien.spbu.data.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Fasilitas {

    @SerializedName("id_fasilitas")
    @Expose
    private String id_fasilitas;

    @SerializedName("nama")
    @Expose
    private String nama;

    public Fasilitas(String id_fasilitas, String nama) {
        this.id_fasilitas = id_fasilitas;
        this.nama = nama;
    }

    public String getId_fasilitas() {
        return id_fasilitas;
    }

    public void setId_fasilitas(String id_fasilitas) {
        this.id_fasilitas = id_fasilitas;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
