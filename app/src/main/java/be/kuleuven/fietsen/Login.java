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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private static final String URL_CHECK = "https://studev.groept.be/api/a20sd402/check_login_fietser/";
    private RequestQueue requestQueue;
    int n = 0;
    String wachtwoord = "";
    String id = "";
    String password;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void onBtnRegister_Clicked(View caller)
    {
        Intent intent = new Intent(this, RegisterFietser.class);
        startActivity(intent);
    }
    public void onBtnLogin_Clicked(View caller)
    {
        EditText txtEmail = (EditText) findViewById(R.id.username);

        Editable username = txtEmail.getText();


        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest queueRequest = new JsonArrayRequest(Request.Method.GET,URL_CHECK + username,null,new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                JSONObject o = null;
                try {
                    o = response.getJSONObject(0);
                    wachtwoord = (String) o.get("wachtwoord");
                    id = (String) o.get("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                createMain();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(queueRequest);

    }

    public void createMain()
    {
        sp = getSharedPreferences("MyUserPrefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("id", id);
        editor.apply();

        EditText txtPassword = (EditText) findViewById(R.id.password);
        Editable password = txtPassword.getText();

        Log.i("wachtwoord", wachtwoord);
        Log.i("password", String.valueOf(password));
        if(wachtwoord.equals(String.valueOf(password)))
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

}