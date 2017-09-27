package com.developer.amien.spbu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView mNavigationView;
    private View navHeader;
    TextView email,username;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFragmentManager = getSupportFragmentManager();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menuNav = mNavigationView.getMenu();
        SharedPreferences spbu = getApplicationContext().getSharedPreferences("spbu", 0); // 0 - for private mode
        navHeader = mNavigationView.getHeaderView(0);
        username = (TextView) navHeader.findViewById(R.id.nav_username);
//        username.setText(pref.getString("username",null));
        username.setText(spbu.getString("username",null));
        email = (TextView) navHeader.findViewById(R.id.nav_email);
        email.setText("Email : "+spbu.getString("email",null));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Pencarian.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFragmentTransaction = mFragmentManager.beginTransaction(); ///fragment transaction ini digunakan jika ingin mengganti fragment
        mFragmentTransaction.replace(R.id.containerView, new MapFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView, new MapFragment()).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView, new MapFragment()).commit();

        } else if (id == R.id.profile) {
            Intent i = new Intent(getApplicationContext(),ProfileFragment.class);
            startActivity(i);
        } else if (id == R.id.tambahkendaraan) {
            Intent i =new Intent(getApplicationContext(), TambahKendaraan.class);
            startActivity(i);
        } else if (id == R.id.logout) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("spbu", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("id_user", "0");
            editor.putString("nama", "");
            editor.putString("alamat", "");
            editor.putString("telp", "");
            editor.putString("email", "");
            editor.putString("username", "");
            editor.putString("password", "");
            editor.commit();
            Intent i = new Intent(getApplicationContext(), Loginactivity.class);
            startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
