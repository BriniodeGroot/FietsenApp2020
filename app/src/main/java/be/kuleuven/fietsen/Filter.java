package be.kuleuven.fietsen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class Filter extends AppCompatActivity {

    private String herenfiets = "2";
    private String damesfiets  = "2";
    private String koersfiets = "2";
    private String elektrischefiets = "2";
    private String mountainbike = "2";
    private String speedpedelecs = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }

    public void btnOpslaanFilters_Clicked(View caller)
    {
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

        Intent intent = new Intent(this, Lijst2.class);
        intent.putExtra("herenfiets", herenfiets);
        intent.putExtra("damesfiets", damesfiets);
        intent.putExtra("koersfiets", koersfiets);
        intent.putExtra("elektrischefietsen", elektrischefiets);
        intent.putExtra("mountainbike", mountainbike);
        intent.putExtra("speedpedelecs", speedpedelecs);

        startActivity(intent);
    }
}