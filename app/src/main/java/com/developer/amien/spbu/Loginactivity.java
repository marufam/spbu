package com.developer.amien.spbu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.amien.spbu.data.rest.ApiClient;
import com.developer.amien.spbu.data.rest.ApiInterface;
import com.developer.amien.spbu.data.retrofit.GetUser;
import com.developer.amien.spbu.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loginactivity extends AppCompatActivity{
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        btnSignup = (Button) findViewById(R.id.sign_up);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiInterface mApiInterface = ApiClient.Getuser().create(ApiInterface.class);
                Call<GetUser> karyawanCall = mApiInterface.getUser();
                karyawanCall.enqueue(new Callback<GetUser>() {

                    @Override
                    public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("spbu", 0); // 0 - for private mode where the created file can only be accessed by the calling application
                        SharedPreferences.Editor editor = pref.edit();
                        boolean a=false;
                        Integer index=0;
                        for (int i=0; i<response.body().getUser().size(); i++){
                            if (response.body().getUser().get(i).getUsername().equalsIgnoreCase(mEmailView.getText().toString()) && response.body().getUser().get(i).getPassword().equalsIgnoreCase(mPasswordView.getText().toString())){
                                a=true;
                                index=i;
                                break;

                            }else{
                                a=false;
                            }
                        }
                        if(a==true){
                            Intent mainIntent = new Intent(Loginactivity.this,MainActivity.class);
                            editor.putString("id_user", response.body().getUser().get(index).getId_user());
                            editor.putString("nama", response.body().getUser().get(index).getNama());
                            editor.putString("alamat", response.body().getUser().get(index).getAlamat());
                            editor.putString("telp", response.body().getUser().get(index).getTelp());
                            editor.putString("email", response.body().getUser().get(index).getEmail());
                            editor.putString("username", response.body().getUser().get(index).getUsername());
                            editor.putString("password", response.body().getUser().get(index).getPassword());
                            editor.putString("jarak_tempuh", "0");
                            editor.commit();
                            Loginactivity.this.startActivity(mainIntent);
                            Loginactivity.this.finish();
                        }else{
                            Toast.makeText(Loginactivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<GetUser> call, Throwable t) {
//                        Toast.makeText(getApplicationContext(), "Check your connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(Loginactivity.this,Register.class);
                Loginactivity.this.startActivity(pindah);
            }
        });
    }
    private boolean attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError("Invalid Password");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("Field Required");
            focusView = mEmailView;
            cancel = true;
        } else if (email!="") {
            mEmailView.setError("Invalid Email");
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        return cancel;
    }



    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 1;
    }
}
