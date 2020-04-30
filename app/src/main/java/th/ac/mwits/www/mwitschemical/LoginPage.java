package th.ac.mwits.www.mwitschemical;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {
    StringBuilder urlName = new StringBuilder();

    String url;

    String role;
    String name;
    String surname;
    EditText User;
    EditText Pass;
    Button Enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlName.append("http://");
        urlName.append(getString(R.string.serverAddress));
        urlName.append(":8080/LoginCheck.php");
        url = urlName.toString();
        //Log.d("TAG", url);
        setContentView(R.layout.activity_login_page);
        User = (EditText) findViewById(R.id.User);
        Pass = (EditText) findViewById(R.id.Pass);
        Enter = (Button) findViewById(R.id.Button);
        Enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String Username;
        String Password;
        Username = User.getText().toString();
        Password = Pass.getText().toString();
        User.setText("");
        Pass.setText("");
        User.setHint("Username");
        Pass.setHint("Password");
        login(Username, Password);
    }

    private void login(final String Username, String Password) {
        class LoginUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            LoginUserClass luc = new LoginUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginPage.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray User = jsonObj.getJSONArray("results");
                    JSONObject u = User.getJSONObject(0);
                    role = u.getString("Role");
                    name = u.getString("Name");
                    surname = u.getString("Surname");

                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginPage.this);
                    alert.setTitle("Log in completed");
                    alert.setMessage("Hello, " + name + " " + surname);
                    alert.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = new Intent(LoginPage.this, MainScreen.class);
                            intent.putExtra("EXTRA_ID", Username);
                            intent.putExtra("EXTRA_ROLE", role);
                            startActivity(intent);
                        }
                    });
                    alert.show();
                } catch (JSONException e) {
                    //Log.d("TAG", "JSONException");
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginPage.this);
                    alert.setTitle("Error");
                    alert.setMessage(s);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });
                    alert.show();
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("ID", params[0]);
                data.put("Password", params[1]);
                //Log.d("TAG", params[0] + " " + params[1]);

                String webResponse = luc.sendPostRequest(url, data);
                //Log.d("TAG", webResponse);

                return webResponse;
            }
        }

        LoginUser lu = new LoginUser();
        lu.execute(Username, Password);
    }
}
