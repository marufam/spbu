package com.developer.amien.spbu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.amien.spbu.data.rest.ApiClient;
import com.developer.amien.spbu.data.rest.ApiInterface;
import com.developer.amien.spbu.data.retrofit.GetUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends AppCompatActivity {
    EditText txtNama, txtAlamat, txtTelepon, txtEmail, txtUsername, txtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final SharedPreferences spbu = getApplicationContext().getSharedPreferences("spbu", 0); // 0 - for private mode

        txtNama = (EditText) findViewById(R.id.profile_nama);
        txtAlamat = (EditText) findViewById(R.id.profile_alamat);
        txtTelepon = (EditText) findViewById(R.id.profile_telp);
        txtEmail = (EditText) findViewById(R.id.profile_email);
        txtUsername = (EditText) findViewById(R.id.profile_username);
        txtPassword = (EditText) findViewById(R.id.profile_password);

        ApiInterface mApiInterface = ApiClient.Getuser().create(ApiInterface.class);
        Call<GetUser> karyawanCall = mApiInterface.getUser();
        karyawanCall.enqueue(new Callback<GetUser>() {

            @Override
            public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                Integer index=0;
                for (int i=0; i<response.body().getUser().size(); i++){
                    if (response.body().getUser().get(i).getId_user().equalsIgnoreCase(spbu.getString("id_user",null))){
                        index=i;
                        break;
                    }else{
//                        Toast.makeText(ProfileFragment.this, "Loading..", Toast.LENGTH_SHORT).show();
                    }
                }
                txtNama.setText(response.body().getUser().get(index).getNama());
                txtAlamat.setText(response.body().getUser().get(index).getAlamat());
                txtTelepon.setText(response.body().getUser().get(index).getTelp());
                txtEmail.setText(response.body().getUser().get(index).getEmail());
                txtUsername.setText(response.body().getUser().get(index).getUsername());
                txtPassword.setText(response.body().getUser().get(index).getPassword());

            }

            @Override
            public void onFailure(Call<GetUser> call, Throwable t) {
                Toast.makeText(ProfileFragment.this, "Error Connection "+t, Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface mApiInterface = ApiClient.Getuser().create(ApiInterface.class);
                Call<GetUser> userCall = mApiInterface.user_insert(spbu.getString("id_user",null),
                        txtNama.getText().toString(),
                        txtAlamat.getText().toString(),
                        txtTelepon.getText().toString(),
                        txtEmail.getText().toString(),
                        txtUsername.getText().toString(),
                        txtPassword.getText().toString(), "PUT");
                userCall.enqueue(new Callback<GetUser>() {

                    @Override
                    public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                        Toast.makeText(getApplicationContext(), "Update Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<GetUser> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Update Failed "+t.getCause(), Toast.LENGTH_SHORT).show();
                    }
                });
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

}
