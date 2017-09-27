package com.developer.amien.spbu.data.rest;

import com.developer.amien.spbu.data.retrofit.GetFasilitas;
import com.developer.amien.spbu.data.retrofit.GetFasilitas_ken;
import com.developer.amien.spbu.data.retrofit.GetKenUser;
import com.developer.amien.spbu.data.retrofit.GetKendaraan;
import com.developer.amien.spbu.data.retrofit.GetRating;
import com.developer.amien.spbu.data.retrofit.GetUser;
import com.developer.amien.spbu.data.retrofit.Getspbu;
import com.developer.amien.spbu.data.retrofit.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface ApiInterface {

    @GET("index.php/user_api")
    Call<GetUser> getUser();

    @FormUrlEncoded
    @POST("index.php/user_api")
    Call<GetUser> user_insert(@Field("id_user") String id_user,
                              @Field("nama") String nama,
                              @Field("alamat") String alamat,
                              @Field("telp") String telp,
                              @Field("email") String email,
                              @Field("username") String username,
                              @Field("password") String password,
                              @Field("action") String action);

    @GET("index.php/spbu_api")
    Call<Getspbu> getspbu();

    @GET("index.php/kendaraan_api")
    Call<GetKendaraan> getKendaraan();

    @GET("index.php/fasilitas_api")
    Call<GetFasilitas> getFasilitas();

    @GET("index.php/ken_user_api")
    Call<GetKenUser> getken_user();

    @FormUrlEncoded
    @POST("index.php/ken_user_api")
    Call<GetKenUser> ken_user_insert(@Field("id") String id,
                              @Field("id_user") String id_user,
                              @Field("id_kendaraan") String id_kendaraan,
                                       @Field("action") String action);

    @GET("index.php/fasilitas_ken_api")
    Call<GetFasilitas_ken> getFasilitas_ken();

    @FormUrlEncoded
    @POST("index.php/fasilitas_ken_api")
    Call<GetFasilitas_ken> Fasilitasken(@Field("id_fasilitas") String id_fasilitas,
                                     @Field("id_spbu") String id_spbu,
                                     @Field("id_user") String id_user,
                                     @Field("action") String action);

    @FormUrlEncoded
    @POST("index.php/fasilitas_ken_api")
    Call<GetFasilitas_ken> Fasilitasken_delete(@Field("id_fasilitas") String id_fasilitas,
                                               @Field("id_spbu") String id_spbu,
                                               @Field("action") String action);

    @GET("index.php/Rating_api")
    Call<GetRating> getRating();

    @FormUrlEncoded
    @POST("index.php/Rating_api")
    Call<GetRating> insertRating(@Field("id") String id,
                                        @Field("id_spbu") String id_spbu,
                                        @Field("id_user") String id_user,
                                        @Field("rate") String rate,
                                        @Field("action") String action);
}
