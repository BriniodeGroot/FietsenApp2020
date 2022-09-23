package be.kuleuven.fietsen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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

public class BerichtencentrumFietsenmaker extends AppCompatActivity {

    private static final String URL_GET_MESSAGE = "https://studev.groept.be/api/a20sd402/receive_messages_fietsenmaker/";
    private static final String URL_GET_NAME = "https://studev.groept.be/api/a20sd402/get_name_fietser/";
    private RequestQueue requestQueue;
    private String idFM;
    private String idVerzender;
    private String bericht;
    private String voornaam;
    private String achternaam;
    private String tekst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berichtencentrum_fietsenmaker);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        idFM = sp.getString("idFM", "");

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest queueRequest = new JsonArrayRequest(Request.Method.GET,URL_GET_MESSAGE + idFM,null,new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i<response.length(); ++i)

                {
                    JSONObject o = null;
                    try {
                        Log.i("waarde", String.valueOf(i));
                        o = response.getJSONObject(i);
                        idVerzender = (String) o.get("verzender");
                        Log.i("idVerzender", idVerzender);
                        tekst = (String) o.get("bericht");
                        Log.i("bericht", tekst);
                        getNameFietser(idVerzender, tekst);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BerichtencentrumFietsenmaker.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(queueRequest);
    }

    public void onBtnNewMessageFietsenMaker(View caller) {
        Intent intent = new Intent(this, NieuwBerichtFietsenmaker.class);
        startActivity(intent);
    }

    public void getNameFietser(String idVerzender1, String tekstje)
    {
        requestQueue = Volley.newRequestQueue(this);
        TextView txtGegevens = findViewById(R.id.textView10);

        JsonArrayRequest queueRequest = new JsonArrayRequest(Request.Method.GET,URL_GET_NAME + idVerzender1,null,new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                voornaam = "";
                achternaam = "";
                JSONObject o = null;
                try {
                    o = response.getJSONObject(0);
                    voornaam = (String) o.get("voornaam");
                    achternaam = (String) o.get("achternaam");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("voornaam", voornaam);
                bericht += "Van: " + voornaam + " " + achternaam +  "\n" + "Bericht: " + tekstje + "\n" + "_______________________" + "\n";
                Log.i("bericht2", bericht);
                txtGegevens.setText(bericht);
                txtGegevens.setMovementMethod(new ScrollingMovementMethod());
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BerichtencentrumFietsenmaker.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(queueRequest);
    }
}