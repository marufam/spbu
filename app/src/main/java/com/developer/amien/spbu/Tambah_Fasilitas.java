package com.developer.amien.spbu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.developer.amien.spbu.data.rest.ApiClient;
import com.developer.amien.spbu.data.rest.ApiInterface;
import com.developer.amien.spbu.data.retrofit.Fasilitas;
import com.developer.amien.spbu.data.retrofit.Fasilitas_ken;
import com.developer.amien.spbu.data.retrofit.GetFasilitas;
import com.developer.amien.spbu.data.retrofit.GetFasilitas_ken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tambah_Fasilitas extends AppCompatActivity {

    ApiClient a = new ApiClient(); //buat ngambil alamat

    private RecyclerView mRecycleview;
    private RecyclerView.LayoutManager mLayoutmanager;
    private RecyclerView.Adapter mAdapter;
    private List<Fasilitas_ken> mFasilitas_ken = new ArrayList<>();
    private List<Fasilitas> kFasilitas = new ArrayList<>();
    ApiInterface mApiInterface, kApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah__fasilitas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecycleview = (RecyclerView) findViewById(R.id.tbfasilitas);
        mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        mRecycleview.setLayoutManager(mLayoutmanager);
        mApiInterface = ApiClient.GetFasilitas_ken().create(ApiInterface.class);
        Call<GetFasilitas_ken> fasilitas_kenCall = mApiInterface.getFasilitas_ken();
        final String id_spbu;
        Intent i = getIntent();
        id_spbu = i.getStringExtra("id_spbu");
        fasilitas_kenCall.enqueue(new Callback<GetFasilitas_ken>() {
            @Override
            public void onResponse(Call<GetFasilitas_ken> call, Response<GetFasilitas_ken> response) {
                for (int i = 0; i < response.body().getFasilitas_ken().size(); i++) {
                    if (response.body().getFasilitas_ken().get(i).getIdSpbu().equalsIgnoreCase(getIntent().getStringExtra("id_spbu"))) {
                        mFasilitas_ken.add(response.body().getFasilitas_ken().get(i));
                    }
                }

                kApiInterface = ApiClient.GetFasilitas().create(ApiInterface.class);
                Call<GetFasilitas> fasilitasCall = mApiInterface.getFasilitas();
                fasilitasCall.enqueue(new Callback<GetFasilitas>() {
                    @Override
                    public void onResponse(Call<GetFasilitas> call, Response<GetFasilitas> response) {
                        kFasilitas=response.body().getFasilitas();
//                        Toast.makeText(Tambah_Fasilitas.this, "ken"+mFasilitas_ken.size()+" fas"+kFasilitas.size(), Toast.LENGTH_SHORT).show();
                        mAdapter = new fasilitas_tambah_adapter(kFasilitas, mFasilitas_ken, getApplicationContext(), id_spbu);
                        mAdapter.notifyDataSetChanged();
                        mRecycleview.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(Call<GetFasilitas> call, Throwable throwable) {
                        Toast.makeText(Tambah_Fasilitas.this, "Check your connection.", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            @Override
            public void onFailure(Call<GetFasilitas_ken> call, Throwable throwable) {
                Toast.makeText(Tambah_Fasilitas.this, "Check your connection.", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
