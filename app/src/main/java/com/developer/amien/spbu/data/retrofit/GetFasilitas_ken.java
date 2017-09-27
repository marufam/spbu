package com.developer.amien.spbu.data.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetFasilitas_ken {

    @SerializedName("id_fasilitas")
    @Expose
    private String id_fasilitas;

    private List<Fasilitas_ken> fasilitas_ken=null;

    public GetFasilitas_ken(String id_fasilitas, List<Fasilitas_ken> fasilitas_ken) {
        this.id_fasilitas = id_fasilitas;
        this.fasilitas_ken = fasilitas_ken;
    }

    public String getId_fasilitas() {
        return id_fasilitas;
    }

    public void setId_fasilitas(String id_fasilitas) {
        this.id_fasilitas = id_fasilitas;
    }

    public List<Fasilitas_ken> getFasilitas_ken() {
        return fasilitas_ken;
    }

    public void setFasilitas_ken(List<Fasilitas_ken> fasilitas_ken) {
        this.fasilitas_ken = fasilitas_ken;
    }
}


