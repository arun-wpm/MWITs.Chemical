package th.ac.mwits.www.mwitschemical;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public class ChemicalSearch extends AppCompatActivity {
    String ID;
    String Role;
    String Parent;

    StringBuilder urlName = new StringBuilder();
    String url;

    EditText Query;
    String query;

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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getString("EXTRA_ID");
            Role = extras.getString("EXTRA_ROLE");
            Parent = extras.getString("EXTRA_PARENT");
            //Log.d("TAG", ID+" "+Role);
        }

        urlName.append("http://");
        urlName.append(getString(R.string.serverAddress));
        urlName.append(":8080/SearchChemicals.php");
        url = urlName.toString();
        //Log.d("TAG", url);
        setContentView(R.layout.activity_chemical_search);
        list = (ListView) findViewById(R.id.listView);
        Query = (EditText) findViewById(R.id.query);
        ChemList = new ArrayList<HashMap<String, String>>();
        Query.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                query = s.toString();
                getData(query);
            }

            @Override
            public void afterTextChanged(Editable s) {
                query = s.toString();
                getData(query);
            }

        });
    }

    private void getData(final String theQuery) {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            SearchUserClass suc = new SearchUserClass();

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("Query", params[0]);
                //Log.d("TAG", params[0]);

                String result = suc.sendPostRequest(url, data);
                //Log.d("TAG", result);
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                //Log.d("TAG", myJSON);
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(theQuery);
    }

    protected void showList(){
        //Log.d("TAG", "show list");
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            //Log.d("TAG", "past line 111");
            Chemicals = jsonObj.getJSONArray(TAG_RESULTS);
            //Log.d("TAG", "past line 113");

            ChemList.clear();
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
                //Log.d("TAG", "Add chemical");
            }

            ListAdapter adapter = new SimpleAdapter(
                    ChemicalSearch.this, ChemList, R.layout.chemical_list_element,
                    new String[]{TAG_ID,TAG_NAME,TAG_FORMULA},
                    new int[]{R.id.ID, R.id.Name, R.id.Formula}
            );

            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> list, View view, int position, long id) {
                    Intent intentDetails = new Intent(ChemicalSearch.this, ChemicalDetails.class);
                    intentDetails.putExtra("EXTRA_ID", ID);
                    intentDetails.putExtra("EXTRA_ROLE", Role);
                    intentDetails.putExtra("EXTRA_PARENT", Parent);
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
            //Log.d("TAG", "JSONException");
            e.printStackTrace();
        }

    }
}
