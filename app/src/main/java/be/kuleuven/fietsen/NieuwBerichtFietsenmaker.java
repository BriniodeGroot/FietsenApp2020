package be.kuleuven.fietsen;

import androidx.appcompat.app.AppCompatActivity;

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

public class NieuwBerichtFietsenmaker extends AppCompatActivity {

    private RequestQueue requestQueue;
    private Editable voornaam;
    private Editable achternaam;
    private Editable bericht;
    private String id;
    private String idFM;

    private static final String URL_GET_ID = "https://studev.groept.be/api/a20sd402/get_id_fietser/";
    private static final String URL_CREATE_MESSAGE = "https://studev.groept.be/api/a20sd402/create_message_fietsenmaker/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nieuw_bericht_fietsenmaker);

        EditText txtVoornaam = (EditText) findViewById(R.id.txtVoornaamFietser);
        voornaam = txtVoornaam.getText();

        EditText txtAchternaam = (EditText) findViewById(R.id.txtAchternaamFietser);
        achternaam = txtAchternaam.getText();

        EditText txtBericht = (EditText) findViewById(R.id.txtBericht1);
        bericht = txtBericht.getText();

    }

    public void onBtnVerstuurFietser_Clicked(View caller)
    {
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest queueRequest = new JsonArrayRequest(Request.Method.GET,URL_GET_ID + voornaam + "/" + achternaam,null,new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {
                Log.i("url1", URL_GET_ID + voornaam + achternaam);
                id = "";
                JSONObject o = null;
                try {
                    o = response.getJSONObject(0);
                    id = (String) o.get("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("idFM", id);
                sendMessageToFietser();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NieuwBerichtFietsenmaker.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(queueRequest);
    }

    public void sendMessageToFietser()
    {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        idFM = sp.getString("idFM", "");

        requestQueue = Volley.newRequestQueue(this);

        String requestURL = URL_CREATE_MESSAGE + idFM + "/" + id + "/" + bericht;
        Log.i("url", requestURL);

        StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(NieuwBerichtFietsenmaker.this, "Versturen gelukt", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NieuwBerichtFietsenmaker.this, "Versturen mislukt", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(submitRequest);

    }
}