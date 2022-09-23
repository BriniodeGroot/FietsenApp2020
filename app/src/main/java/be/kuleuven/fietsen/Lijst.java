package be.kuleuven.fietsen;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
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

public class Lijst extends AppCompatActivity {

    private RequestQueue requestQueue;
    private static final String URL_ALLES = "https://studev.groept.be/api/a20sd402/alles_fietsenmaker";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lijst);

        TextView txtGegevens = findViewById(R.id.txtGegevens);
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest queueRequest = new JsonArrayRequest(Request.Method.GET,URL_ALLES,null,new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                String info = "";
                for(int i = 0; i<response.length(); ++i)
                {
                    String heren = "nee";
                    String dames = "nee";
                    String koers = "nee";
                    String elektrisch = "nee";
                    String mountain = "nee";
                    String speed = "nee";
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(i);
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
                        info += "Naam winkel: " + o.get("naamwinkel") + "\n" + "Adres van winkel: " + o.get("adreswinkel") +  "\n" + "Email: " + o.get("emailfietsenmaker") + "\n" + "Telefoon: " + o.get("telefoon") + "\n" + "Herenfietsen: " + heren + "\n" + "Damesfiets: " + dames + "\n" + "Koersfiets: " + koers + "\n" + "Elektrische fietsen: " + elektrisch + "\n" + "Mountainbikes: " + mountain + "\n" + "Speed pedelecs: " + speed + "\n" + "__________________________" + "\n" + "\n";
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                txtGegevens.setText(info);
                txtGegevens.setMovementMethod(new ScrollingMovementMethod());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Lijst.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(queueRequest);
    }

    public void onBtnFiler_Clicked(View caller)
    {
        Intent intent = new Intent(this, Filter.class);
        startActivity(intent);
    }
}
