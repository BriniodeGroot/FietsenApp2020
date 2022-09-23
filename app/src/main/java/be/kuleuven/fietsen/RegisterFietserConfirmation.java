package be.kuleuven.fietsen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterFietserConfirmation extends AppCompatActivity {

    private RequestQueue requestQueue;
    private String info;
    private static final String CREATE_FIETSER = "https://studev.groept.be/api/a20sd402/create_fietser/";
    private static final String ALLES_URL = "https://studev.groept.be/api/a20sd402/alles_fietser_email/";
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fietser_confirmation);

        requestQueue = Volley.newRequestQueue(this);

        Bundle extras = getIntent().getExtras();
        String requestURL = CREATE_FIETSER + extras.get("voornaam") + "/" +
                extras.get("achternaam") + "/" +
                extras.get("datum") + "/" +
                extras.get("email") + "/" +
                extras.get("phone") + "/" +
                extras.get("wachtwoord");

        String email = String.valueOf(extras.get("email"));
        Log.i("email", email);

        JsonArrayRequest queueRequest = new JsonArrayRequest(Request.Method.GET,ALLES_URL + email,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                info = "";
                JSONObject o = null;
                try {
                    o = response.getJSONObject(0);
                    info = o.get("id") + "\n";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("id", info);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterFietserConfirmation.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RegisterFietserConfirmation.this, "Registratie gelukt", Toast.LENGTH_SHORT).show();
                requestQueue.add(queueRequest);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterFietserConfirmation.this, "Registratie mislukt", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);
    }

    public void onBtnNextClicked(View caller)
    {
        sp = getSharedPreferences("MyUserPrefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("id", info);
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}