package be.kuleuven.fietsen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class UpdateFietserProfiel extends AppCompatActivity {

    private static final String URL_UPDATE = "https://studev.groept.be/api/a20sd402/update_profiel_fietser/";
    private RequestQueue requestQueue;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fietser_profiel);
        Toast.makeText(UpdateFietserProfiel.this, "Gelieve alles opnieuw in te vullen", Toast.LENGTH_LONG).show();
    }

    public void btnUpdateNew_Clicked(View caller)
    {
        EditText txtVoornaam = (EditText) findViewById(R.id.txtVoornaamUp);
        EditText txtAchternaam = (EditText) findViewById(R.id.txtAchternaamUp);
        EditText lblDatum = (EditText) findViewById(R.id.txtLeeftijdUp);
        EditText txtEmail = (EditText) findViewById(R.id.txtEmailUp);
        EditText lblPhone = (EditText) findViewById(R.id.txtTelefoonUp);
        EditText txtWachtwoord = (EditText) findViewById(R.id.txtWachtwoordUp);

        Editable voornaam = txtVoornaam.getText();
        Editable achternaam = txtAchternaam.getText();
        Editable datum = lblDatum.getText();
        Editable email = txtEmail.getText();
        Editable telefoon = lblPhone.getText();
        Editable wachtwoord = txtWachtwoord.getText();

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        id = sp.getString("id", "");

        requestQueue = Volley.newRequestQueue(this);

        String requestURL = URL_UPDATE + voornaam + "/" +
                achternaam + "/" +
                datum + "/" +
                email + "/" +
                telefoon + "/" +
                wachtwoord + "/" +
                id;

        //Toast.makeText(UpdateFietserProfiel.this, requestURL, Toast.LENGTH_LONG).show();

        StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(UpdateFietserProfiel.this, "Updaten gelukt", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateFietserProfiel.this, "Updaten mislukt", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);
    }
}