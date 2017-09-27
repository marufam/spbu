package com.developer.amien.spbu;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.developer.amien.spbu.data.rest.ApiClient;
import com.developer.amien.spbu.data.rest.ApiInterface;
import com.developer.amien.spbu.data.retrofit.Getspbu;
import com.developer.amien.spbu.data.retrofit.Spbu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;


public class MapFragment extends Fragment {
    MapView mMapView;
    private GoogleMap googleMap;
    double myLat = 0, myLng = 0;
    Location loc;
    LocationManager locationManager;
    String mprovider;
    Circle mapCircle;
    private Marker myMarker;
    ApiInterface mApiInterface;
    private LocationListener listener;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

//        Toast.makeText(getContext(), "jarak tempuh saat ini : "+spbu.getString("jarak_tempuh",null), Toast.LENGTH_SHORT).show();
        prepareallmap();
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                t.append("\n " + location.getLongitude() + " " + location.getLatitude());
                if (myLat == 0 && myLng == 0) {
//                    Toast.makeText(getContext(), "" + location.getLatitude() + "  " + location.getLongitude(), Toast.LENGTH_LONG).show();
                    myLat = location.getLatitude();
                    myLng = location.getLongitude();
                } else {
                    myLat = location.getLatitude();
                    myLng = location.getLongitude();
                }

                if (mapCircle != null) {
                    mapCircle.remove();
                }

//                googleMap.clear();
                if (myLat > 0 && myLng > 0) {


                    mapCircle = googleMap.addCircle(new CircleOptions()
                            .center(new LatLng(myLat, myLng))
                            .radius(300) //1km=+-10000
                            .strokeColor(Color.argb(90, 255, 189, 31))
                            .fillColor(Color.argb(60, 255, 189, 31))
                            .strokeWidth((float) 2));
                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {


            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        configure_button();


        return rootView;
    }



    public String getDistance(final double lat1, final double lon1, final double lat2, final double lon2){
        final String[] parsedDistance = new String[1];
        final String[] response = new String[1];

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://maps.googleapis.com/maps/api/directions/json?origin=" + lat1 + "," + lon1 + "&destination=" + lat2 + "," + lon2 + "&sensor=false&units=metric&mode=driving");
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    response[0] = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

                    JSONObject jsonObject = new JSONObject(response[0]);
                    JSONArray array = jsonObject.getJSONArray("routes");
                    JSONObject routes = array.getJSONObject(0);
                    JSONArray legs = routes.getJSONArray("legs");
                    JSONObject steps = legs.getJSONObject(0);
                    JSONObject distance = steps.getJSONObject("distance");
                    parsedDistance[0] =distance.getString("value");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return parsedDistance[0];
    }



    public void prepareallmap() {
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                final SharedPreferences spbu = getContext().getSharedPreferences("spbu", 0);
                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                CameraPosition cameraPosition;
                // For dropping a marker at a point on the Map
                if (loc != null) {
                    myLat = loc.getLatitude();
                    myLng = loc.getLongitude();
                }
                LatLng myLocation = new LatLng(myLat, myLng);
                cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(14).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                }
//                Toast.makeText(getContext(), ""+googleMap.getMyLocation().getLatitude(), Toast.LENGTH_SHORT).show();
                CameraPosition PosisiTempat;


                googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
//                        Toast.makeText(getContext(), ""+googleMap.getMyLocation().getLatitude(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                SharedPreferences pref = getContext().getSharedPreferences("spbu", 0); // 0 - for private mode

                mApiInterface = ApiClient.GetSpbu().create(ApiInterface.class);
                Call<Getspbu> getspbuCall = mApiInterface.getspbu();
                getspbuCall.enqueue(new Callback<Getspbu>() {
                    @Override
                    public void onResponse(Call<Getspbu> call, final Response<Getspbu> response) {
                        final List<Spbu> spbuList = response.body().getSpbu();
//                        Log.d("Retrofit Get", "Jumlah data : " + String.valueOf(jadwalList.size()));

                        for (int i = 0; i < response.body().getSpbu().size(); i++) {
                            String[] lnglat = spbuList.get(i).getLnglat().split(",");
                            final LatLng origin = new LatLng(myLat,myLng);
                            final String[] lat = response.body().getSpbu().get(i).getLnglat().split(",");
//                            Toast.makeText(getContext(), "jarak tempuh "+spbu.getString("jarak_tempuh","0"), Toast.LENGTH_SHORT).show();
                            if(myLat!=0 && myLng!=0) {
                                String num = getDistance(myLat, myLng, Double.parseDouble(lat[0].toString()), Double.parseDouble(lat[1].toString()));
//                                Toast.makeText(getContext(), "jarak marker "+num, Toast.LENGTH_SHORT).show();
                                if(Integer.parseInt(num)<Integer.parseInt(spbu.getString("jarak_tempuh","0"))) {
                                    //radius  hp ke spbu
                                    googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Double.parseDouble(lnglat[0].toString()), Double.parseDouble(lnglat[1].toString())))
                                            .title("Lokasi")
                                            .snippet(spbuList.get(i).getNama())
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
                                            .setTag(spbuList.get(i));

                                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker marker) {
                                            final Spbu k = (Spbu) marker.getTag();
                                            String[] lat1 = k.getLnglat().split(",");
                                            LatLng dest = new LatLng(Double.parseDouble(lat1[0].toString()), Double.parseDouble(lat1[1].toString()));


//                                    float[] results = new float[1];
//                                    Location.distanceBetween(myLat,myLng,Double.parseDouble(lat[0].toString()), Double.parseDouble(lat[1].toString()), results);
                                            // Getting URL to the Google Directions API
                                            String url = getDirectionsUrl(origin, dest);
                                            DownloadTask downloadTask = new DownloadTask();

                                            downloadTask.execute(url);

                                            Snackbar snackbar = Snackbar.make(getView(), "Lokasi " + k.getNama(), Snackbar.LENGTH_SHORT)
                                                    .setActionTextColor(Color.parseColor("#FFAA00"))
                                                    .setAction("Lihat", new View.OnClickListener() {

                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent i = new Intent(getContext(), spbu_detail.class);
                                                            i.putExtra("id_spbu", k.getId_spbu());
                                                            i.putExtra("nama_spbu", k.getNama());
                                                            i.putExtra("latlng", k.getLnglat());
                                                            i.putExtra("buka", k.getBuka());
                                                            i.putExtra("tutup", k.getTutup());
                                                            i.putExtra("alamat", k.getAlamat());
                                                            startActivity(i);
                                                        }
                                                    });
                                            snackbar.show();

                                            return false;
                                        }
                                    });
                                }
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Getspbu> call, Throwable t) {
                        Toast.makeText(getContext(), "Check your connection (1)", Toast.LENGTH_SHORT).show();
                    }


                });

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listener);
        if (locationManager != null) {
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            prepareallmap();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        prepareallmap();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);



        }
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        String duration, distance;
//        Toast t;
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                JSONObject jsonObject = new JSONObject(jsonData[0]);

//// routesArray contains ALL routes
                JSONArray routesArray = jsonObject.getJSONArray("routes");
//// Grab the first route
                JSONObject route = routesArray.getJSONObject(0);
//// Take all legs from the route
                JSONArray legs = route.getJSONArray("legs");
//// Grab first leg
                JSONObject leg = legs.getJSONObject(0);
//
                JSONObject durationObject = leg.getJSONObject("duration");
                duration = durationObject.getString("text");

                JSONObject distanceObject = leg.getJSONObject("distance");
                distance = distanceObject.getString("text");
//                Toast.makeText(getContext(), "Jarak : "+duration, Toast.LENGTH_SHORT).show();
                if(distance!=null && duration!=null) {
                    Log.d("JSON", duration.toString() + " .. " + distance.toString());
//                    t = new Toast(getContext());
//                    t.makeText(getActivity().getApplicationContext(),"asasas",Toast.LENGTH_LONG);
//                    Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                    getActivity().runOnUiThread(new Runnable() {

                        public void run() {

                            Toast.makeText(getContext(), "Jarak : "+distance+" Waktu Tempuh : "+duration, Toast.LENGTH_LONG).show();

                        }
                    });
                }

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(5);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route
            googleMap.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
