package com.developer.amien.spbu.data.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetFasilitas {


    @SerializedName("id_fasilitas")
    @Expose
    private String id_fasilitas;

    private List<Fasilitas> fasilitas=null;

    public GetFasilitas(String id_fasilitas, List<Fasilitas> fasilitas) {
        this.id_fasilitas = id_fasilitas;
        this.fasilitas = fasilitas;
    }

    public String getId_fasilitas() {
        return id_fasilitas;
    }

    public void setId_fasilitas(String id_fasilitas) {
        this.id_fasilitas = id_fasilitas;
    }

    public List<Fasilitas> getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(List<Fasilitas> fasilitas) {
        this.fasilitas = fasilitas;
    }
}
