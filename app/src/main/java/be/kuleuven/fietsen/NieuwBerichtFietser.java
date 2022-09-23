package be.kuleuven.fietsen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class NieuwBerichtFietser extends AppCompatActivity {

    private String id;
    private String idFM;
    private Editable bericht;
    private Editable naamwinkel;
    private RequestQueue requestQueue;
    private static final String URL_CREATE_MESSAGE = "https://studev.groept.be/api/a20sd402/create_message_fietser/";
    private static final String URL_GET_ID = "https://studev.groept.be/api/a20sd402/get_id_fietsenmaker_naamwinkel/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nieuw_bericht_fietser);

        EditText txtNaamWinkel = (EditText) findViewById(R.id.txtVoornaamFietser);
        naamwinkel = txtNaamWinkel.getText();
        //Toast.makeText(NieuwBerichtFietser.this, naamwinkel, Toast.LENGTH_LONG).show();

        EditText txtBericht = (EditText) findViewById(R.id.txtBericht1);
        bericht = txtBericht.getText();
        //Toast.makeText(NieuwBerichtFietser.this, bericht, Toast.LENGTH_LONG).show();
    }

    public void onBtnVerstuurNaarFietsenmaker_Clicked(View caller)
    {

        Log.i("naamwinkel", String.valueOf(naamwinkel));

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest queueRequest = new JsonArrayRequest(Request.Method.GET,URL_GET_ID + naamwinkel,null,new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                idFM = "";
                JSONObject o = null;
                try {
                    o = response.getJSONObject(0);
                    idFM = (String) o.get("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("idFM", idFM);
                //Toast.makeText(NieuwBerichtFietser.this, idFM, Toast.LENGTH_LONG).show();
                sendMessage();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NieuwBerichtFietser.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(queueRequest);


    }
    public void sendMessage()
    {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        id = sp.getString("id", "");

        Log.i("id", id);


        requestQueue = Volley.newRequestQueue(this);

        Log.i("bericht", String.valueOf(bericht));
        //Toast.makeText(NieuwBerichtFietser.this, bericht, Toast.LENGTH_LONG).show();

        String requestURL = URL_CREATE_MESSAGE + id + "/" + idFM + "/" + bericht;
        //Toast.makeText(NieuwBerichtFietser.this, requestURL, Toast.LENGTH_LONG).show();

        StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL, new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Toast.makeText(NieuwBerichtFietser.this, "Versturen gelukt", Toast.LENGTH_SHORT).show();
            //start();
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(NieuwBerichtFietser.this, "Versturen mislukt", Toast.LENGTH_LONG).show();
        }
    });
        requestQueue.add(submitRequest);
    }
    public void start()
    {
        Intent intent = new Intent(this, BerichtencentrumFietser.class);
        startActivity(intent);
    }
}