package com.developer.amien.spbu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.developer.amien.spbu.data.rest.ApiClient;
import com.developer.amien.spbu.data.rest.ApiInterface;
import com.developer.amien.spbu.data.retrofit.GetKenUser;
import com.developer.amien.spbu.data.retrofit.GetKendaraan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahKendaraan extends AppCompatActivity {

    Button btnSimpan;
    public String[] kendaraans1;
    public List<String> kendaraans = new ArrayList<String>();
    public Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kendaraan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = (Spinner) findViewById(R.id.pilih_kendaraan);
//        spinner.setOnItemSelectedListener(this);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);

        ApiInterface mApiInterface = ApiClient.GetKendaraan().create(ApiInterface.class);
        Call<GetKendaraan> kendaraanCall = mApiInterface.getKendaraan();
        kendaraanCall.enqueue(new Callback<GetKendaraan>() {

            @Override
            public void onResponse(Call<GetKendaraan> call, Response<GetKendaraan> response) {
//
                for (int i = 0; i < response.body().getKendaraan().size(); i++) {
                    kendaraans.add(response.body().getKendaraan().get(i).getNama_kendaraan().toString());
                }
                kendaraans1 = new String[response.body().getKendaraan().size()];
                kendaraans1 = kendaraans.toArray(new String[]{});
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.my_spinner, kendaraans1);
                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(R.layout.layout_spinner_dropdown);
                spinner.setAdapter(dataAdapter);


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(TambahKendaraan.this, spinner.getSelectedItem() + " selected", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }

            @Override
            public void onFailure(Call<GetKendaraan> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Check your connection", Toast.LENGTH_SHORT).show();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkpoint();
            }
        });

    }

    public void insertkendaraan(){
        final SharedPreferences spbu = getApplicationContext().getSharedPreferences("spbu", 0); // 0 - for private mode
        ApiInterface kApiInterface = ApiClient.GetKendaraan().create(ApiInterface.class);
        Call<GetKenUser> kendaraanCall = kApiInterface.ken_user_insert(null,
                spbu.getString("id_user", null),
                String.valueOf((int) spinner.getSelectedItemPosition() + 1), "POST");
        kendaraanCall.enqueue(new Callback<GetKenUser>() {
            @Override
            public void onResponse(Call<GetKenUser> call, Response<GetKenUser> response) {
                Toast.makeText(TambahKendaraan.this, "Insert Success", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<GetKenUser> call, Throwable throwable) {
                Toast.makeText(TambahKendaraan.this, "Failed insert, Check your connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatekendaraan(String id){
        final SharedPreferences spbu = getApplicationContext().getSharedPreferences("spbu", 0); // 0 - for private mode
        ApiInterface kApiInterface = ApiClient.GetKendaraan().create(ApiInterface.class);
        Call<GetKenUser> kendaraanCall = kApiInterface.ken_user_insert(id.toString(),
                spbu.getString("id_user", null),
                String.valueOf((int) spinner.getSelectedItemPosition() + 1), "PUT");
        kendaraanCall.enqueue(new Callback<GetKenUser>() {
            @Override
            public void onResponse(Call<GetKenUser> call, Response<GetKenUser> response) {
                Toast.makeText(TambahKendaraan.this, "Update Success", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<GetKenUser> call, Throwable throwable) {
                Toast.makeText(TambahKendaraan.this, "Failed update, Check your connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    int Status=-1;
    public void checkpoint(){
        final SharedPreferences spbu = getApplicationContext().getSharedPreferences("spbu", 0); // 0 - for private mode
        ApiInterface kApiInterface = ApiClient.GetkenUser().create(ApiInterface.class);
        Call<GetKenUser> kendaraanCall = kApiInterface.getken_user();
        kendaraanCall.enqueue(new Callback<GetKenUser>() {
            @Override
            public void onResponse(Call<GetKenUser> call, Response<GetKenUser> response) {
//                Toast.makeText(TambahKendaraan.this, ""+response.body().getKen_user().size(), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < response.body().getKen_user().size(); i++){
                        if(response.body().getKen_user().get(i).getId_user().equalsIgnoreCase(spbu.getString("id_user",null))){
                            Status=Integer.parseInt(response.body().getKen_user().get(i).getId());
                            break;
                        }
                    }
//                Toast.makeText(TambahKendaraan.this, "Status = "+Status+" as"+spbu.getString("id_user",null), Toast.LENGTH_SHORT).show();
                    if(Status>=0){
                        updatekendaraan(String.valueOf(Status));
                    }else{
                        insertkendaraan();
                    }
            }

            @Override
            public void onFailure(Call<GetKenUser> call, Throwable throwable) {
                Toast.makeText(TambahKendaraan.this, "Check your connection. ", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
