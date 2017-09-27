package com.developer.amien.spbu;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.developer.amien.spbu.data.rest.ApiClient;
import com.developer.amien.spbu.data.rest.ApiInterface;
import com.developer.amien.spbu.data.retrofit.GetKendaraan;
import com.developer.amien.spbu.data.retrofit.GetUser;
import com.developer.amien.spbu.data.retrofit.User;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    EditText txtNama, txtAlamat, txtTelepon, txtEmail, txtUsername, txtPassword;
    Button btnSimpan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        txtNama = (EditText) findViewById(R.id.register_nama);
        txtAlamat = (EditText) findViewById(R.id.register_alamat);
        txtTelepon = (EditText) findViewById(R.id.register_telp);
        txtEmail = (EditText) findViewById(R.id.register_email);
        txtUsername = (EditText) findViewById(R.id.register_username);
        txtPassword = (EditText) findViewById(R.id.register_password);


        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiInterface mApiInterface = ApiClient.Getuser().create(ApiInterface.class);
                final Call<GetUser> userCall = mApiInterface.user_insert(null,
                        txtNama.getText().toString(),
                        txtAlamat.getText().toString(),
                        txtTelepon.getText().toString(),
                        txtEmail.getText().toString(),
                        txtUsername.getText().toString(),
                        txtPassword.getText().toString(), "POST");
                userCall.enqueue(new Callback<GetUser>() {

                    @Override
                    public void onResponse(Call<GetUser> call, Response<GetUser> response) {

                        Toast.makeText(Register.this, "Register Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<GetUser> call, Throwable t) {
                        Toast.makeText(Register.this, "Register Failed "+t.getCause(), Toast.LENGTH_SHORT).show();
                    }
                });
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


    }



}
