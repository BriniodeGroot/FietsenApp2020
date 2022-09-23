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

public class BerichtencentrumFietser extends AppCompatActivity {

    private static final String URL_GET_MESSAGE = "https://studev.groept.be/api/a20sd402/receive_messages_fietser/";
    private static final String URL_GET_STORENAME = "https://studev.groept.be/api/a20sd402/get_storename_fietsenmaker/";
    private RequestQueue requestQueue;
    private String id;
    private String idVerzender;
    private String bericht;
    private String winkelnaam;
    private String tekst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berichtencentrum_fietser);



        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        id = sp.getString("id", "");

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest queueRequest = new JsonArrayRequest(Request.Method.GET,URL_GET_MESSAGE + id,null,new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                bericht = "";
                for(int i = 0; i<response.length(); ++i)
                {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(i);
                        idVerzender = (String) o.get("verzender");
                        tekst = (String) o.get("bericht");
                        getName(idVerzender, tekst);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BerichtencentrumFietser.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(queueRequest);


    }

    public void onBtnNieuwBerichtFietser(View caller)
    {
        Intent intent = new Intent(this, NieuwBerichtFietser.class);
        startActivity(intent);
    }

    public void getName(String idVerzender1, String tekstje)
    {
        requestQueue = Volley.newRequestQueue(this);
        TextView txtGegevens = findViewById(R.id.textView10);

        JsonArrayRequest queueRequest = new JsonArrayRequest(Request.Method.GET,URL_GET_STORENAME + idVerzender1,null,new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                winkelnaam = "";
                JSONObject o = null;
                try {
                    o = response.getJSONObject(0);
                    winkelnaam = (String) o.get("naamwinkel");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bericht += "Van: " + winkelnaam + "\n" + "Bericht: " + tekstje + "\n" + "_______________________" + "\n";
                txtGegevens.setText(bericht);
                txtGegevens.setMovementMethod(new ScrollingMovementMethod());
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BerichtencentrumFietser.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(queueRequest);
    }
}