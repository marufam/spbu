package com.developer.amien.spbu.data.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Rating {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("id_spbu")
    @Expose
    private String id_spbu;

    @SerializedName("id_user")
    @Expose
    private String id_user;

    @SerializedName("rate")
    @Expose
    private String rate;

    public Rating(String id, String id_spbu, String id_user, String rate) {
        this.id = id;
        this.id_spbu = id_spbu;
        this.id_user = id_user;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_spbu() {
        return id_spbu;
    }

    public void setId_spbu(String id_spbu) {
        this.id_spbu = id_spbu;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
