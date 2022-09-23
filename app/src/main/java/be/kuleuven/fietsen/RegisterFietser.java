package be.kuleuven.fietsen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFietser extends AppCompatActivity {

    private boolean correct = false;
    private int n;
    private int datum;
    private int telefoon;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fietser);

        /*EditText lblDatum = findViewById(R.id.txtDatum);
        EditText lblPhone = findViewById(R.id.txtPhone);
        EditText txtWachtwoord = findViewById(R.id.txtWachtwoord);
        EditText txtHerhaalWachtwoord = findViewById(R.id.txtHerhaalwachtwoord);
        Button btnOpslaan = findViewById(R.id.btnOpslaan);
        btnOpslaan.setEnabled(false);

        while(correct == false) {
            try {
                datum = Integer.parseInt(lblDatum.getText().toString());
                n++;
            } catch (NumberFormatException e) {
                Toast.makeText(RegisterFietser.this, "Foute datum", Toast.LENGTH_LONG).show();
            }
            try {
                telefoon = Integer.parseInt(lblPhone.getText().toString());
                n++;
            } catch (NumberFormatException e) {
                Toast.makeText(RegisterFietser.this, "Fout telefoonnummer", Toast.LENGTH_LONG).show();
            }
            if(txtWachtwoord.equals(txtHerhaalWachtwoord))
            {
                n++;
            }
            else
            {
                Toast.makeText(RegisterFietser.this, "Wachtwoord komt niet overeen", Toast.LENGTH_LONG).show();
            }
            if(n == 3)
            {
                correct = true;
            }

        }*/

    }

    public void onBtnOpslaan_Clicked(View caller)
    {
        EditText txtVoornaam = (EditText) findViewById(R.id.txtVoornaam);
        EditText txtAchternaam = (EditText) findViewById(R.id.txtAchternaam);
        EditText lblDatum = (EditText) findViewById(R.id.txtDatum);
        EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        EditText lblPhone = (EditText) findViewById(R.id.txtPhone);
        EditText txtWachtwoord = (EditText) findViewById(R.id.txtWachtwoord);

        //Toast.makeText(RegisterFietser.this, txtWachtwoord.getText(), Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, RegisterFietserConfirmation.class);
        intent.putExtra("voornaam", txtVoornaam.getText());
        intent.putExtra("achternaam", txtAchternaam.getText());
        intent.putExtra("datum", lblDatum.getText());
        intent.putExtra("email", txtEmail.getText());
        intent.putExtra("phone", lblPhone.getText());
        intent.putExtra("wachtwoord", txtWachtwoord.getText());

        startActivity(intent);
    }

}