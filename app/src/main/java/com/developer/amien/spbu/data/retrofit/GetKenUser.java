package com.developer.amien.spbu.data.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetKenUser {
    @SerializedName("status")
    @Expose
    private String status;

    private List<Kenuser> ken_user=null;

    public GetKenUser(String status, List<Kenuser> ken_user) {
        this.status = status;
        this.ken_user = ken_user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Kenuser> getKen_user() {
        return ken_user;
    }

    public void setKen_user(List<Kenuser> ken_user) {
        this.ken_user = ken_user;
    }
}
