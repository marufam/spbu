package com.developer.amien.spbu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.amien.spbu.data.rest.ApiClient;
import com.developer.amien.spbu.data.rest.ApiInterface;
import com.developer.amien.spbu.data.retrofit.GetKenUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pencarian extends AppCompatActivity {
    EditText txtKendaraan, txtJumlah;
    Button btnCari;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtKendaraan = (EditText) findViewById(R.id.cari_kendaraan);
        txtJumlah = (EditText) findViewById(R.id.jumlah);
        txtKendaraan.setEnabled(false);
        btnCari = (Button) findViewById(R.id.btnCari);

        final SharedPreferences spbu = getApplicationContext().getSharedPreferences("spbu", 0); // 0 - for private mode
        ApiInterface kApiInterface = ApiClient.GetkenUser().create(ApiInterface.class);
        Call<GetKenUser> kendaraanCall = kApiInterface.getken_user();

        kendaraanCall.enqueue(new Callback<GetKenUser>() {
            @Override
            public void onResponse(Call<GetKenUser> call, final Response<GetKenUser> response) {

//                Toast.makeText(TambahKendaraan.this, ""+response.body().getKen_user().size(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < response.body().getKen_user().size(); i++){
                    if(response.body().getKen_user().get(i).getId_user().equalsIgnoreCase(spbu.getString("id_user",null))){
                       txtKendaraan.setText(response.body().getKen_user().get(i).getNama_kendaraan());
                        index=i;
                        break;
                    }
                }
                btnCari.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("spbu", 0); // 0 - for private mode where the created file can only be accessed by the calling application
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("jarak_tempuh", String.valueOf(Integer.parseInt(txtJumlah.getText().toString())* Integer.parseInt(response.body().getKen_user().get(index).getJarak().toString()) * 1000));
                        editor.commit();
                    }
                });

            }

            @Override
            public void onFailure(Call<GetKenUser> call, Throwable throwable) {
                Toast.makeText(Pencarian.this, "Check your connection. ", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
