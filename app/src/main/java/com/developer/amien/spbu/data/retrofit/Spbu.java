package com.developer.amien.spbu.data.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Spbu {
    @SerializedName("id_spbu")
    @Expose
    private String id_spbu;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("lnglat")
    @Expose
    private String lnglat;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("buka")
    @Expose
    private String buka;
    @SerializedName("tutup")
    @Expose
    private String tutup;

    public Spbu(String id_spbu, String nama, String lnglat, String alamat, String buka, String tutup) {
        this.id_spbu = id_spbu;
        this.nama = nama;
        this.lnglat = lnglat;
        this.alamat = alamat;
        this.buka = buka;
        this.tutup = tutup;
    }

    public String getId_spbu() {
        return id_spbu;
    }

    public void setId_spbu(String id_spbu) {
        this.id_spbu = id_spbu;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLnglat() {
        return lnglat;
    }

    public void setLnglat(String lnglat) {
        this.lnglat = lnglat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getBuka() {
        return buka;
    }

    public void setBuka(String buka) {
        this.buka = buka;
    }

    public String getTutup() {
        return tutup;
    }

    public void setTutup(String tutup) {
        this.tutup = tutup;
    }
}
