package com.developer.amien.spbu.data.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Fasilitas_ken {
    @SerializedName("id_fasilitas")
    @Expose
    private String idFasilitas;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("id_spbu")
    @Expose
    private String idSpbu;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("nama_fasilitas")
    @Expose
    private String namaFasilitas;

    public String getIdFasilitas() {
        return idFasilitas;
    }

    public void setIdFasilitas(String idFasilitas) {
        this.idFasilitas = idFasilitas;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getIdSpbu() {
        return idSpbu;
    }

    public void setIdSpbu(String idSpbu) {
        this.idSpbu = idSpbu;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNamaFasilitas() {
        return namaFasilitas;
    }

    public void setNamaFasilitas(String namaFasilitas) {
        this.namaFasilitas = namaFasilitas;
    }
}
