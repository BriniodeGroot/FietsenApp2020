package be.kuleuven.fietsen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class UpdateFietsenMakerProfiel extends AppCompatActivity {

    private static final String URL_UPDATE = "https://studev.groept.be/api/a20sd402/update_profiel_fietsenmaker/";
    private RequestQueue requestQueue;
    String id;

    private String herenfiets = "0";
    private String damesfiets  = "0";
    private String koersfiets = "0";
    private String elektrischefiets = "0";
    private String mountainbike = "0";
    private String speedpedelecs = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fietsen_maker_profiel);
        Toast.makeText(UpdateFietsenMakerProfiel.this, "Gelieve alles opnieuw in te vullen", Toast.LENGTH_LONG).show();
    }
    public void btnUpdateFMnew_Clicked(View caller)
    {
        EditText txtVoornaamFietsenmaker = (EditText) findViewById(R.id.txtVoornaamFietsenmaker);
        EditText txtAchternaamFietsenmaker = (EditText) findViewById(R.id.txtAchternaamFietsenmaker);
        EditText txtNaamWinkel = (EditText) findViewById(R.id.txtNaamWinkel);
        EditText txtEmailFietsenmaker = (EditText) findViewById(R.id.txtEmailFietsenmaker);
        EditText txtAdresWinkel = (EditText) findViewById(R.id.txtAdresWinkel);
        EditText txtTelnrFietsenmaker = (EditText) findViewById(R.id.txtTelnrFietsenmaker);
        EditText txtPasswordFietsenmaker = (EditText) findViewById(R.id.txtPasswordFietsenmaker);
        CheckBox cbHerenfietsen = (CheckBox) findViewById(R.id.cbHerenfietsen);

        if(cbHerenfietsen.isChecked() == true)
        {
            herenfiets = "1";
        }
        CheckBox cbDamesfietsen = (CheckBox) findViewById(R.id.cbDamesfietsen);
        if(cbDamesfietsen.isChecked() == true)
        {
            damesfiets = "1";
        }
        CheckBox cbKoersfietsen = (CheckBox) findViewById(R.id.cbKoersfietsen);
        if(cbKoersfietsen.isChecked() == true)
        {
            koersfiets = "1";
        }
        CheckBox cbElektrischeFietsen = (CheckBox) findViewById(R.id.cbElektrischeFietsen);
        if(cbElektrischeFietsen.isChecked() == true)
        {
            elektrischefiets = "1";
        }
        CheckBox cbMountainbikes = (CheckBox) findViewById(R.id.cbMountainbikes);
        if(cbMountainbikes.isChecked() == true)
        {
            mountainbike = "1";
        }
        CheckBox cbSpeedPedelecs = (CheckBox) findViewById(R.id.cbSpeedPedelecs);
        if(cbSpeedPedelecs.isChecked() == true)
        {
            speedpedelecs = "1";
        }

        Editable voornaam = txtVoornaamFietsenmaker.getText();
        Editable achternaam = txtAchternaamFietsenmaker.getText();
        Editable naamwinkel = txtNaamWinkel.getText();
        Editable email = txtEmailFietsenmaker.getText();
        Editable adres = txtAdresWinkel.getText();
        Editable telefoon = txtTelnrFietsenmaker.getText();
        Editable wachtwoord = txtPasswordFietsenmaker.getText();

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        id = sp.getString("idFM", "");

        requestQueue = Volley.newRequestQueue(this);

        String requestURL = URL_UPDATE + voornaam + "/" +
                achternaam + "/" +
                naamwinkel + "/" +
                email + "/" +
                adres + "/" +
                telefoon + "/" +
                wachtwoord + "/" +
                herenfiets + "/" +
                damesfiets + "/" +
                koersfiets + "/" +
                elektrischefiets + "/" +
                mountainbike + "/" +
                speedpedelecs + "/" +
                id;

        //Toast.makeText(UpdateFietsenMakerProfiel.this, requestURL, Toast.LENGTH_LONG).show();

        StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(UpdateFietsenMakerProfiel.this, "Updaten gelukt", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateFietsenMakerProfiel.this, "Updaten mislukt", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);
    }
}