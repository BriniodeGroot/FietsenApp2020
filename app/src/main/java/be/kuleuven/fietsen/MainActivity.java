package be.kuleuven.fietsen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String URL_ADRESSEN = "https://studev.groept.be/api/a20sd402/alles_fietsenmaker";
    private GoogleMap mMap;
    private Geocoder geocoder;
    private ArrayList<String> naamWinkels = new ArrayList<String>();
    private ArrayList<String> adreswinkels = new ArrayList<String>();
    private ArrayList<String> telefoonnummers = new ArrayList<String>();
    private ArrayList<LatLng> locationArrayList = new ArrayList<LatLng>();
    private RequestQueue requestQueue;
    private String adreswinkel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geocoder = new Geocoder(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocation();

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest queueRequest = new JsonArrayRequest(Request.Method.GET,URL_ADRESSEN,null,new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                String info = "";
                String nameStore = "";
                String addressStore = "";
                String phoneNumber = "";
                for (int i=0; i<response.length(); ++i) {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(i);
                        info = (String) o.get("adreswinkel");
                        nameStore = (String) o.get("naamwinkel");
                        addressStore = (String) o.get("adreswinkel");
                        phoneNumber = (String) o.get("telefoon");
                        //Log.i("adres", info);
                        naamWinkels.add(nameStore);
                        adreswinkels.add(addressStore);
                        telefoonnummers.add(phoneNumber);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocationName(info, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addresses.get(0);
                    LatLng place = new LatLng(address.getLatitude(), address.getLongitude());
                    locationArrayList.add(place);
                    //Log.i("latlng", String.valueOf(place));
                }
                for(int j = 0; j < locationArrayList.size(); j++)
                {
                    mMap.addMarker(new MarkerOptions().position(locationArrayList.get(j)).title(naamWinkels.get(j)).snippet("adres: " + adreswinkels.get(j)));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(j)));
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(queueRequest);

        // Add a marker in Sydney and move the camera
        //LatLng leuven = new LatLng(50.879058, 4.701539);
        //mMap.addMarker(new MarkerOptions().position(leuven).title("Marker in Leuven"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(leuven));
        //Toast.makeText(MainActivity.this, adressen.get(0), Toast.LENGTH_LONG).show();



        /*MarkerOptions markerOptions = new MarkerOptions()
                .position(place)
                .title(address.getLocality());
        mMap.addMarker(markerOptions);*/


        //mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title("Marker"));


        /*for (int j=0; j<adressen.size(); ++j)
        {

            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocationName(adressen.get(j), 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addresses.get(0);
            LatLng place = new LatLng(address.getLatitude(), address.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(place)
                    .title(address.getLocality());
            mMap.addMarker(markerOptions);
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 16));
        }*/



    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                    break;
                }
        }
    }

    public void onBtnProfile_Clicked(View caller)
    {
        Intent intent = new Intent(this, FietserProfielActivity.class);
        startActivity(intent);
    }

    public void onBtnList_Clicked(View caller)
    {
        Intent intent = new Intent(this, Lijst.class);
        startActivity(intent);
    }

    public void onBtnBerichtencentrumFietser_Clicked(View caller)
    {
        Intent intent = new Intent(this, BerichtencentrumFietser.class);
        startActivity(intent);
    }

}