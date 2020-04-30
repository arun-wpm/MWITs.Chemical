package th.ac.mwits.www.mwitschemical;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TabToReceive extends AppCompatActivity {
    String ID;
    String Role;
    String Parent;

    StringBuilder urlName = new StringBuilder();
    String url;

    EditText Query;
    String query;

    String myJSON;
    String myJSONC;
    String myJSONB;
    private static String TAG_RESULTS = "results";
    private static String TAG_ID = "ID";
    private static String TAG_CHEMID = "ChemID";
    private static String TAG_BOTBORROWING = "BotBorrowing";
    private static String TAG_BORROWERID = "BorrowerID";
    private static String TAG_BORROWED = "Borrowed";
    private static String TAG_GRANTERID = "GranterID";
    private static String TAG_GRANTED = "Granted";
    private static String TAG_RETURNED = "Returned";
    private static String TAG_RECEIVERID = "ReceiverID";
    private static String TAG_RECEIVED = "Received";

    JSONArray Logs = null;
    ArrayList<HashMap<String, String>> LogList;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getString("EXTRA_ID");
            Role = extras.getString("EXTRA_ROLE");
            Parent = extras.getString("EXTRA_PARENT");
            ////Log.d("TAG", ID+" "+Role);
        }

        urlName.append("http://");
        urlName.append(getString(R.string.serverAddress));
        urlName.append(":8080/ToReceive.php");
        url = urlName.toString();
        ////Log.d("TAG", url);
        setContentView(R.layout.activity_tab_to_receive);
        list = (ListView) findViewById(R.id.listView);
        LogList = new ArrayList<HashMap<String, String>>();
        getData(ID);
    }

    private void getData(String ID) {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            SearchUserClass suc = new SearchUserClass();

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("BorrowerID", params[0]);
                ////Log.d("TAG", params[0]);

                String result = suc.sendPostRequest(url, data);
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
        g.execute(ID);
    }

    private void getChemical (String ChemID, final int position) {
        StringBuilder urlNameC = new StringBuilder();
        urlNameC.append("http://");
        urlNameC.append(getString(R.string.serverAddress));
        urlNameC.append(":8080/GrantListC.php");
        final String urlC = urlNameC.toString();
        /*class GetChemJSON {
            protected void execute(String... params) {
                SearchUserClass suc = new SearchUserClass();
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("ChemID", ChemID);

                myJSONC = suc.sendPostRequest(urlC, data);
            }
        }*/
        class GetChemJSON extends AsyncTask<String, Void, String> {
            SearchUserClass suc = new SearchUserClass();

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("ChemID", params[0]);
                ////Log.d("TAG", params[0]);

                String result = suc.sendPostRequest(urlC, data);
                ////Log.d("TAG", result);
                myJSONC = result;
                //Log.d("TAG", "myJSONC : " + myJSONC);
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                //myJSONC = result;
                ////Log.d("TAG", myJSON);
                Intent intentDetails = new Intent(TabToReceive.this, LogDetails.class);
                intentDetails.putExtra("EXTRA_ID", ID);
                intentDetails.putExtra("EXTRA_ROLE", Role);
                intentDetails.putExtra("EXTRA_PARENT", Parent);
                try {
                    intentDetails.putExtra("EXTRA_JSON", Logs.getJSONObject(position).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intentDetails.putExtra("EXTRA_JSONC", myJSONC);
                intentDetails.putExtra("EXTRA_JSONB", myJSONB);
                startActivity(intentDetails);
            }
        }
        GetChemJSON g = new GetChemJSON();
        g.execute(ChemID);
    }

    private void getPerson (String BorrowerID, final int position) {
        StringBuilder urlNameB = new StringBuilder();
        urlNameB.append("http://");
        urlNameB.append(getString(R.string.serverAddress));
        urlNameB.append(":8080/GrantListB.php");
        final String urlB = urlNameB.toString();
        /*class GetPersonJSON {
            protected void execute(String... params) {
                SearchUserClass suc = new SearchUserClass();
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("BorrowerID", BorrowerID);

                myJSONB = suc.sendPostRequest(urlB, data);
            }
        }*/
        class GetPersonJSON extends AsyncTask<String, Void, String> {
            SearchUserClass suc = new SearchUserClass();

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("BorrowerID", params[0]);
                ////Log.d("TAG", params[0]);

                String result = suc.sendPostRequest(urlB, data);
                ////Log.d("TAG", result);
                myJSONB = result;
                //Log.d("TAG", "myJSONB : "+myJSONB);
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                try {
                    getChemical(Logs.getJSONObject(position).getString(TAG_CHEMID), position);
                    //Log.d("TAG", Logs.getJSONObject(position).getString(TAG_CHEMID));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.d("TAG", "JSONB : " + myJSONB);
                //myJSONB = result;
                ////Log.d("TAG", myJSON);
            }
        }
        GetPersonJSON g = new GetPersonJSON();
        g.execute(BorrowerID);
    }

    protected void showList(){
        ////Log.d("TAG", "show list");
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            ////Log.d("TAG", "past line 111");
            Logs = jsonObj.getJSONArray(TAG_RESULTS);
            ////Log.d("TAG", "past line 113");

            LogList.clear();
            for(int i=0;i<Logs.length();i++){
                JSONObject c = Logs.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String chemid = c.getString(TAG_CHEMID);
                String botborrowing = c.getString(TAG_BOTBORROWING);
                String borrowerid = c.getString(TAG_BORROWERID);
                String borrowed = c.getString(TAG_BORROWED);
                String granterid = c.getString(TAG_GRANTERID);
                String granted = c.getString(TAG_GRANTED);
                String returned = c.getString(TAG_RETURNED);
                String receiverid = c.getString(TAG_RECEIVERID);
                String received = c.getString(TAG_RECEIVED);

                HashMap<String,String> Chemical = new HashMap<String,String>();

                Chemical.put(TAG_ID,id);
                Chemical.put(TAG_CHEMID,chemid);
                Chemical.put(TAG_BOTBORROWING,botborrowing);
                Chemical.put(TAG_BORROWERID,borrowerid);
                Chemical.put(TAG_BORROWED,borrowed);
                Chemical.put(TAG_GRANTERID, granterid);
                Chemical.put(TAG_GRANTED, granted);
                Chemical.put(TAG_RETURNED, returned);
                Chemical.put(TAG_RECEIVERID, receiverid);
                Chemical.put(TAG_RECEIVED, received);

                LogList.add(Chemical);
                ////Log.d("TAG", "Add chemical");
            }

            ListAdapter adapter = new SimpleAdapter(
                    TabToReceive.this, LogList, R.layout.log_list_element,
                    new String[]{TAG_ID,TAG_CHEMID,TAG_BORROWERID},
                    new int[]{R.id.ID, R.id.Name, R.id.DateTime}
            );

            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> list, View view, int position, long id) {
                    try {
                        getPerson(Logs.getJSONObject(position).getString(TAG_BORROWERID), position);
                        //Log.d("TAG", Logs.getJSONObject(position).getString(TAG_BORROWERID));
                        //Log.d("TAG", "JSONC : " + myJSONC);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
