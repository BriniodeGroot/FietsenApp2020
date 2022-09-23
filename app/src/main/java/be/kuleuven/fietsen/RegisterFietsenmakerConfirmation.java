package be.kuleuven.fietsen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class RegisterFietsenmakerConfirmation extends AppCompatActivity {

    private RequestQueue requestQueue;
    private static final String CREATE_FIETSENMAKER = "https://studev.groept.be/api/a20sd402/create_fietsenmaker/";
        private static final String URL_ALLES = "https://studev.groept.be/api/a20sd402/alles_fietsenmaker_email/";
    private String info;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fietsenmaker_confirmation);

        requestQueue = Volley.newRequestQueue(this);

        Bundle extras = getIntent().getExtras();
        //String opties = (extras.getBoolean("herenfiets")? "+herenfiets" : "") + (extras.getBoolean("damesfiets")? "+damesfiets" : "") + (extras.getBoolean("koersfiets")? "+koersfiets" : "") + (extras.getBoolean("elektrischefietsen")? "+elektrischefietsen" : "") + (extras.getBoolean("mountainbike")? "+mountainbike" : "") + (extras.getBoolean("speedpedelecs")? "+speedpedelecs" : "");
        String request2URL = CREATE_FIETSENMAKER + extras.get("voornaamfietsenmaker") + "/" +
                extras.get("achternaamfietsenmaker") + "/" +
                extras.get("naamwinkel") + "/" +
                extras.get("emailfietsenmaker") + "/" +
                extras.get("adreswinkel") + "/" +
                extras.get("telefoon") + "/" +
                extras.get("wachtwoordfietsenmaker") + "/" +
                extras.get("herenfiets") + "/" +
                extras.get("damesfiets") + "/" +
                extras.get("koersfiets") + "/" +
                extras.get("elektrischefietsen") + "/" +
                extras.get("mountainbike") + "/" +
                extras.get("speedpedelecs");
               // ((opties.length() == 0)? "-" : opties);

        String email = String.valueOf(extras.get("emailfietsenmaker"));

        JsonArrayRequest queueRequest = new JsonArrayRequest(Request.Method.GET,URL_ALLES + email,null,new Response.Listener<JSONArray>() {
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
                Toast.makeText(RegisterFietsenmakerConfirmation.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });


        StringRequest submitRequest = new StringRequest(Request.Method.GET, request2URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RegisterFietsenmakerConfirmation.this, "Registratie gelukt", Toast.LENGTH_SHORT).show();
                requestQueue.add(queueRequest);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterFietsenmakerConfirmation.this, "Registratie mislukt", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(submitRequest);
    }

    public void onBtnConfirmationFietsenMaker(View caller)
    {
        sp = getSharedPreferences("MyUserPrefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("idFM", info);
        editor.apply();

        Intent intent = new Intent(this, MainActivityFietsenmaker.class);
        startActivity(intent);
    }
}