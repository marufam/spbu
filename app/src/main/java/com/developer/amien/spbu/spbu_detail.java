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
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.amien.spbu.data.rest.ApiClient;
import com.developer.amien.spbu.data.rest.ApiInterface;
import com.developer.amien.spbu.data.retrofit.Fasilitas_ken;
import com.developer.amien.spbu.data.retrofit.GetFasilitas_ken;
import com.developer.amien.spbu.data.retrofit.GetRating;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class spbu_detail extends AppCompatActivity {
    ApiClient a = new ApiClient(); //buat ngambil alamat

    private RecyclerView mRecycleview;
    private RecyclerView.LayoutManager mLayoutmanager;
    private RecyclerView.Adapter mAdapter;
    private List<Fasilitas_ken> mFasilitas_ken = new ArrayList<>();
    ApiInterface mApiInterface;

    TextView nama_spbu, alamat, buka, tutup, latlng;
    Button btntambah, btnRate;
    RatingBar spburate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spbu_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nama_spbu = (TextView) findViewById(R.id.txtnama);
        alamat = (TextView) findViewById(R.id.txtalamat);
        buka = (TextView) findViewById(R.id.txtbuka);
        tutup = (TextView) findViewById(R.id.txttutup);
        btntambah = (Button) findViewById(R.id.btntambah);
        spburate = (RatingBar) findViewById(R.id.ratingspbu);
        btnRate = (Button) findViewById(R.id.btnRate);

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), tambahrate.class);
                i.putExtra("id_spbu",getIntent().getStringExtra("id_spbu"));
                startActivity(i);
            }
        });

        mApiInterface = ApiClient.GetRating().create(ApiInterface.class);
        Call<GetRating> ratingCall = mApiInterface.getRating();
        ratingCall.enqueue(new Callback<GetRating>() {
                               @Override
                               public void onResponse(Call<GetRating> call, Response<GetRating> response) {
                                    float jumlah=0;
                                   int count=0;
                                   for (int i=0;i<response.body().getRating().size();i++){
                                       if(response.body().getRating().get(i).getId_spbu().equalsIgnoreCase(getIntent().getStringExtra("id_spbu"))){
                                           jumlah+= Float.parseFloat(response.body().getRating().get(i).getRate());
                                           count++;
                                       }

                                   }
                                   float rata;
                                   rata=jumlah/count;
//                                   Toast.makeText(spbu_detail.this, "rate "+rata, Toast.LENGTH_SHORT).show();
                                   spburate.setRating(rata);

                               }

                               @Override
                               public void onFailure(Call<GetRating> call, Throwable throwable) {
                                   Toast.makeText(spbu_detail.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                               }
                           });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecycleview = (RecyclerView) findViewById(R.id.rcfasilitas);
        mLayoutmanager = new LinearLayoutManager(getApplicationContext());
        mRecycleview.setLayoutManager(mLayoutmanager);
        if (getIntent().getStringExtra("id_spbu") != null) {

            nama_spbu.setText(getIntent().getStringExtra("nama_spbu"));
            alamat.setText(getIntent().getStringExtra("alamat"));
            buka.setText(getIntent().getStringExtra("buka"));
            tutup.setText(getIntent().getStringExtra("tutup"));

            mApiInterface = ApiClient.GetFasilitas_ken().create(ApiInterface.class);
            Call<GetFasilitas_ken> fasilitas_kenCall = mApiInterface.getFasilitas_ken();
            fasilitas_kenCall.enqueue(new Callback<GetFasilitas_ken>() {
                @Override
                public void onResponse(Call<GetFasilitas_ken> call, Response<GetFasilitas_ken> response) {
                    for (int i = 0; i < response.body().getFasilitas_ken().size(); i++) {
                        if (response.body().getFasilitas_ken().get(i).getIdSpbu().equalsIgnoreCase(getIntent().getStringExtra("id_spbu"))) {
                            mFasilitas_ken.add(response.body().getFasilitas_ken().get(i));
                        }
                    }
//                    Toast.makeText(spbu_detail.this, "respon " + response.body().getFasilitas_ken().size(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(spbu_detail.this, "yang ada " + mFasilitas_ken.size(), Toast.LENGTH_SHORT).show();
                    mAdapter = new fasilitas_adapter(mFasilitas_ken, getApplicationContext());
                    mRecycleview.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Call<GetFasilitas_ken> call, Throwable throwable) {

                }
            });
        }

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Tambah_Fasilitas.class);
                i.putExtra("id_spbu",getIntent().getStringExtra("id_spbu"));
                startActivity(i);
            }
        });
    }


}
