package com.developer.amien.spbu.data.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Getspbu {
    @SerializedName("status")
    @Expose
    private String status;

    private List<Spbu> spbu=null;

    public Getspbu(String status, List<Spbu> spbu) {
        this.status = status;
        this.spbu = spbu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Spbu> getSpbu() {
        return spbu;
    }

    public void setSpbu(List<Spbu> spbu) {
        this.spbu = spbu;
    }
}
