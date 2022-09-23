package be.kuleuven.fietsen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivityFietsenmaker extends AppCompatActivity {

    private static final String ALLES_PROFIEL_URL = "https://studev.groept.be/api/a20sd402/gegevens_profiel_fietsenmaker/";
    private RequestQueue requestQueue;
    private String id;
    private String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__fietsenmaker);


        TextView txtProfile = findViewById(R.id.txtProfiel);
        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        id = sp.getString("idFM", "");

        JsonArrayRequest queueRequest = new JsonArrayRequest(Request.Method.GET,ALLES_PROFIEL_URL + id,null,new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                info = "";
                    String heren = "nee";
                    String dames = "nee";
                    String koers = "nee";
                    String elektrisch = "nee";
                    String mountain = "nee";
                    String speed = "nee";
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(0);
                        if(o.get("herenfiets").equals("1"))
                        {
                            heren = "ja";
                        }
                        if(o.get("damesfiets").equals("1"))
                        {
                            dames = "ja";
                        }
                        if(o.get("koersfiets").equals("1"))
                        {
                            koers = "ja";
                        }
                        if(o.get("elektrischefietsen").equals("1"))
                        {
                            elektrisch = "ja";
                        }
                        if(o.get("mountainbike").equals("1"))
                        {
                            mountain = "ja";
                        }
                        if(o.get("speedpedelecs").equals("1"))
                        {
                            speed = "ja";
                        }
                        info = "Voornaam: " + o.get("voornaamfietsenmaker") + "\n" + "Achternaam: " + o.get("achternaamfietsenmaker") + "\n" + "Naam winkel: " + o.get("naamwinkel") + "\n" + "Adres van winkel: " + o.get("adreswinkel") +  "\n" + "Email: " + o.get("emailfietsenmaker") + "\n" + "Telefoon: " + o.get("telefoon") + "\n" + "Herenfietsen: " + heren + "\n" + "Damesfiets: " + dames + "\n" + "Koersfiets: " + koers + "\n" + "Elektrische fietsen: " + elektrisch + "\n" + "Mountainbikes: " + mountain + "\n" + "Speed pedelecs: " + speed;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                txtProfile.setText(info);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivityFietsenmaker.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(queueRequest);
    }

    public void onBtnLogOutFM_Clicked(View caller)
    {
        Intent intent = new Intent(this, FirstScreen.class);
        startActivity(intent);
    }

    public void onBtnUpdateFM_Clicked(View caller)
    {
        Intent intent = new Intent(this, UpdateFietsenMakerProfiel.class);
        startActivity(intent);
    }
    public void onBtnBerichtencentrumFietsenmaker_Clicked(View caller)
    {
        Intent intent = new Intent(this, BerichtencentrumFietsenmaker.class);
        startActivity(intent);
    }
}
