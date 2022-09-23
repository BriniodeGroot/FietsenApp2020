package be.kuleuven.fietsen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class RegisterFietsenmaker extends AppCompatActivity {

    private String herenfiets = "0";
    private String damesfiets  = "0";
    private String koersfiets = "0";
    private String elektrischefiets = "0";
    private String mountainbike = "0";
    private String speedpedelecs = "0";

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_fietsenmaker);
    }

    public void onBtnOpslaanFietsenmaker_Clicked(View caller)
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

        Intent intent = new Intent(this, RegisterFietsenmakerConfirmation.class);
        intent.putExtra("voornaamfietsenmaker", txtVoornaamFietsenmaker.getText());
        intent.putExtra("achternaamfietsenmaker", txtAchternaamFietsenmaker.getText());
        intent.putExtra("naamwinkel", txtNaamWinkel.getText());
        intent.putExtra("emailfietsenmaker", txtEmailFietsenmaker.getText());
        intent.putExtra("adreswinkel", txtAdresWinkel.getText());
        intent.putExtra("telefoon", txtTelnrFietsenmaker.getText());
        intent.putExtra("wachtwoordfietsenmaker", txtPasswordFietsenmaker.getText());
        intent.putExtra("herenfiets", herenfiets);
        intent.putExtra("damesfiets", damesfiets);
        intent.putExtra("koersfiets", koersfiets);
        intent.putExtra("elektrischefietsen", elektrischefiets);
        intent.putExtra("mountainbike", mountainbike);
        intent.putExtra("speedpedelecs", speedpedelecs);

        startActivity(intent);
    }
}