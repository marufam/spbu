package com.developer.amien.spbu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.developer.amien.spbu.data.rest.ApiClient;
import com.developer.amien.spbu.data.rest.ApiInterface;
import com.developer.amien.spbu.data.retrofit.GetRating;
import com.developer.amien.spbu.data.retrofit.GetUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tambahrate extends AppCompatActivity {

    RatingBar RateBar;
    Button btnRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahrate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RateBar = (RatingBar) findViewById(R.id.ratingspbu);
        btnRate = (Button) findViewById(R.id.btnRate);
        final SharedPreferences spbu = getApplicationContext().getSharedPreferences("spbu", 0); // 0 - for private mode

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface mApiInterface = ApiClient.GetRating().create(ApiInterface.class);
                final Call<GetRating> rateCall = mApiInterface.insertRating(null,
                        getIntent().getStringExtra("id_spbu"),
                        spbu.getString("id_user",""),
                        String.valueOf(RateBar.getRating()),
                        "POST");
                rateCall.enqueue(new Callback<GetRating>() {

                    @Override
                    public void onResponse(Call<GetRating> call, Response<GetRating> response) {

                        Toast.makeText(tambahrate.this, "Rate Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<GetRating> call, Throwable t) {
                        Toast.makeText(tambahrate.this, "Rate Failed "+t.getCause(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}
