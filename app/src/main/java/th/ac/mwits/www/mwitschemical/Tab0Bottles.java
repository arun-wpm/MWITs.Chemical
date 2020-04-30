package th.ac.mwits.www.mwitschemical;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Tab0Bottles extends AppCompatActivity {

    String ID;
    String Role;
    String Parent;

    String myJSON;
    private static String TAG_RESULTS = "results";
    private static String TAG_ID = "ID";
    private static String TAG_NAME = "Name";
    private static String TAG_FORMULA = "Formula";
    private static String TAG_STORE = "Store";
    private static String TAG_PRICE = "Price";
    private static String TAG_VOLUME = "Volume";
    private static String TAG_BOTAVAILABLE = "BotAvailable";
    private static String TAG_BOTBORROWING = "BotBorrowing";

    JSONArray Chemicals = null;
    ArrayList<HashMap<String, String>> ChemList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab0_bottles);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getString("EXTRA_ID");
            Role = extras.getString("EXTRA_ROLE");
        }

        list = (ListView) findViewById(R.id.listView);
        ChemList = new ArrayList<HashMap<String, String>>();
        getData();
    }

    private void getData() {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... args) {
                /*DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://192.168.1.34/ListChemicals.php");

                httppost.setHeader("Content-type", "application/json");*/
                URL url = null;
                try {
                    url = new URL("http://" + getString(R.string.serverAddress) + ":8080/0Bottles.php");
                    //url = new URL("http://10.4.3.49:8080/ListChemicals.php");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                InputStream inputStream = null;
                String result = null;
                try {
                    /*HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();*/
                    inputStream = urlConnection.getInputStream();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        ////Log.d("TAG", "read line");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                } finally {
                    /*try {
                        if (inputStream != null) inputStream.close();
                    } catch (Exception squish) {
                    }*/
                    urlConnection.disconnect();
                }
                ////Log.d("TAG", result);
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                ////Log.d("TAG", myJSON);
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    protected void showList(){
        ////Log.d("TAG", "show list");
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            ////Log.d("TAG", "past line 111");
            Chemicals = jsonObj.getJSONArray(TAG_RESULTS);
            ////Log.d("TAG", "past line 113");

            for(int i=0;i<Chemicals.length();i++){
                JSONObject c = Chemicals.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String formula = c.getString(TAG_FORMULA);

                HashMap<String,String> Chemical = new HashMap<String,String>();

                Chemical.put(TAG_ID,id);
                Chemical.put(TAG_NAME,name);
                Chemical.put(TAG_FORMULA,formula);

                ChemList.add(Chemical);
                ////Log.d("TAG", "Add chemical");
            }

            ListAdapter adapter = new SimpleAdapter(
                    Tab0Bottles.this, ChemList, R.layout.chemical_list_element,
                    new String[]{TAG_ID,TAG_NAME,TAG_FORMULA},
                    new int[]{R.id.ID, R.id.Name, R.id.Formula}
            );

            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> list, View view, int position, long id) {
                    Intent intentDetails = new Intent(Tab0Bottles.this, ChemicalDetails.class);
                    intentDetails.putExtra("EXTRA_ID", ID);
                    intentDetails.putExtra("EXTRA_ROLE", Role);
                    intentDetails.putExtra("EXTRA_PARENT", "List");
                    try {
                        intentDetails.putExtra("EXTRA_JSON", Chemicals.getJSONObject(position).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intentDetails);
                }
            });
            TextView None = (TextView) findViewById(R.id.textView);
            None.setVisibility(View.INVISIBLE);

        } catch (JSONException e) {
            ////Log.d("TAG", "JSONException");
            e.printStackTrace();
        }
    }
}
