package be.kuleuven.fietsen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
    }

    public void onBtnFietser_Clicked(View caller)
    {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    public void onBtnFietsenmaker_Clicked(View caller)
    {
        Intent intent = new Intent(this, LoginFietsenmaker.class);
        startActivity(intent);
    }
}