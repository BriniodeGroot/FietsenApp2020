package be.kuleuven.fietsen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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


public class FietserProfielActivity extends AppCompatActivity {

    private static final String ALLES_URL = "https://studev.groept.be/api/a20sd402/alles";
    private RequestQueue requestQueue;
    private String id;
    private String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fietser_profiel);

        TextView txtProfile = findViewById(R.id.txtProfiel);
        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        id = sp.getString("id", "");

        JsonArrayRequest queueRequest = new JsonArrayRequest(Request.Method.GET,"https://studev.groept.be/api/a20sd402/gegevens_profiel_fietser/" + id,null,new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                info = "";
                JSONObject o = null;
                try {
                    o = response.getJSONObject(0);
                    info = "Voornaam: " + o.get("voornaam") + "\n" + "Achternaam: " + o.get("achternaam") + "\n" + "Leeftijd: " + o.get("leeftijd") + "\n" + "Email: " + o.get("email") + "\n" + "Telefoon: " + o.get("telefoon") + "\n";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                txtProfile.setText(info);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FietserProfielActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(queueRequest);
    }

    public void onBtnLogOut_Clicked(View caller)
    {
        Toast.makeText(FietserProfielActivity.this, "Uitgelogd", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, FirstScreen.class);
        startActivity(intent);
    }

        public void onBtnUpdate_Clicked(View caller)
    {
        Intent intent = new Intent(this, UpdateFietserProfiel.class);
        startActivity(intent);
    }
}