package th.ac.mwits.www.mwitschemical;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LogDetails extends AppCompatActivity implements View.OnClickListener {

    String ID;
    String Role;
    String Parent;
    String JSON;
    String JSONC;
    String JSONB;

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

    private static String TAG_NAME = "Name";
    private static String TAG_FORMULA = "Formula";
    private static String TAG_STORE = "Store";
    private static String TAG_PRICE = "Price";
    private static String TAG_VOLUME = "Volume";
    private static String TAG_BOTAVAILABLE = "BotAvailable";
    //private static String TAG_BOTBORROWING = "BotBorrowing";

    private static String TAG_SURNAME = "Surname";

    TextView TextName;
    TextView TextFormula;
    TextView TextStore;
    TextView TextPrice;
    TextView TextVolume;
    TextView TextTotal;
    TextView TextBotAvailable;
    TextView TextBotBorrowing;

    TextView TextBotBorrow;
    TextView TextBorrower;
    TextView TextBorrowed;
    TextView TextGranter;
    TextView TextGranted;
    TextView TextReturned;
    TextView TextReceiver;
    TextView TextReceived;
    Button button;

    String name;
    String surname;

    String id;
    String chemname;
    String formula;
    String store;
    String price;
    String volume;
    String botavailable;
    String botborrowing;

    String chemid;
    String botborrow;
    String borrowerid;
    String borrowed;
    String granterid;
    String granted;
    String returned;
    String receiverid;
    String received;
    int bota, botb;

    final Context context = this;

    StringBuilder urlName = new StringBuilder();
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_details);

        TextName = (TextView) findViewById(R.id.DetailName);
        TextFormula = (TextView) findViewById(R.id.DetailFormula);
        TextStore = (TextView) findViewById(R.id.DetailStore);
        TextPrice = (TextView) findViewById(R.id.DetailPrice);
        TextVolume = (TextView) findViewById(R.id.DetailVolume);
        TextTotal = (TextView) findViewById(R.id.DetailTotal);
        TextBotAvailable = (TextView) findViewById(R.id.DetailBotAvailable);
        TextBotBorrowing = (TextView) findViewById(R.id.DetailBotBorrowing);
        TextBotBorrow = (TextView) findViewById(R.id.DetailBotBorrow);
        TextBorrower = (TextView) findViewById(R.id.DetailBorrower);
        TextBorrowed = (TextView) findViewById(R.id.DetailBorrowed);
        TextGranter = (TextView) findViewById(R.id.DetailGranter);
        TextGranted = (TextView) findViewById(R.id.DetailGranted);
        TextReturned = (TextView) findViewById(R.id.DetailReturned);
        TextReceiver = (TextView) findViewById(R.id.DetailReceiver);
        TextReceived = (TextView) findViewById(R.id.DetailReceived);
        button = (Button) findViewById(R.id.DetailButton);
        button.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getString("EXTRA_ID");
            Role = extras.getString("EXTRA_ROLE");
            Parent = extras.getString("EXTRA_PARENT");
            JSON = extras.getString("EXTRA_JSON");
            JSONC = extras.getString("EXTRA_JSONC");
            JSONB = extras.getString("EXTRA_JSONB");

            try {
                JSONObject jsonObj = new JSONObject(JSON);
                id = jsonObj.getString(TAG_ID);
                chemid = jsonObj.getString(TAG_CHEMID);
                botborrow = jsonObj.getString(TAG_BOTBORROWING);
                borrowerid = jsonObj.getString(TAG_BORROWERID);
                borrowed = jsonObj.getString(TAG_BORROWED);
                granterid = jsonObj.getString(TAG_GRANTERID);
                granted = jsonObj.getString(TAG_GRANTED);
                returned = jsonObj.getString(TAG_RETURNED);
                receiverid = jsonObj.getString(TAG_RECEIVERID);
                received = jsonObj.getString(TAG_RECEIVED);

                JSONObject jsonObjC = new JSONObject(JSONC);
                JSONArray C = jsonObjC.getJSONArray("results");
                JSONObject c = C.getJSONObject(0);
                //chemid = jsonObjC.getString("ID");
                chemname = c.getString(TAG_NAME);
                formula = c.getString(TAG_FORMULA);
                store = c.getString(TAG_STORE);
                price = c.getString(TAG_PRICE);
                volume = c.getString(TAG_VOLUME);
                botavailable = c.getString(TAG_BOTAVAILABLE);
                botborrowing = c.getString(TAG_BOTBORROWING);
                ////Log.d("TAG", price + " " + botavailable + " " + botborrowing);

                JSONObject jsonObjB = new JSONObject(JSONB);
                JSONArray B = jsonObjB.getJSONArray("results");
                JSONObject b = B.getJSONObject(0);
                name = b.getString(TAG_NAME);
                surname = b.getString(TAG_SURNAME);

                if (botavailable.equals("null"))
                    bota = 0;
                else
                    bota = Integer.valueOf(botavailable);
                if (botborrowing.equals("null"))
                    botb = 0;
                else
                    botb = Integer.valueOf(botborrowing);
                int tot = bota + botb;

                TextName.setText("Name: " + chemname);
                TextFormula.setText("Formula: " + formula);
                //TextStore.setText("Store: " + store);
                TextStore.setText(Html.fromHtml("Store: " + store));
                TextPrice.setText("Price: " + price);
                TextVolume.setText("Volume: " + volume);
                TextTotal.setText("Total Amount: " + tot);
                TextBotAvailable.setText("Amount Available: " + bota);
                TextBotBorrowing.setText("Amount Borrowing: " + botb);

                TextBotBorrow.setText("Bottles Borrowed: " + botborrow);
                TextBorrower.setText("Borrower: " + name + " " + surname + " (" + borrowerid + ")");
                TextBorrowed.setText("Date and Time Borrowed: " + borrowed);
                TextGranter.setText("Granter: " + granterid);
                TextGranted.setText("Date and Time Granted Permission: " + granted);
                TextReturned.setText("Date and Time Returned: " + returned);
                TextReceiver.setText("Receiver: " + receiverid);
                TextReceived.setText("Date and Time Received: " + received);

                switch(Parent)
                {
                    case "Search":
                    case "List":
                        button.setVisibility(View.INVISIBLE);
                        break;
                    case "Grant Permission":
                        //Log.d("TAG", granted);
                        if (!granted.equals("null"))
                            button.setVisibility(View.GONE);
                    case "Receive":
                        if (!received.equals("null"))
                            button.setVisibility(View.GONE);
                    default:
                        button.setText(Parent);
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (Parent) {
            case "Grant Permission":
                if (Integer.valueOf(botborrow) > bota) {
                    Toast.makeText(getApplicationContext(), "Amount exceeds amount available", Toast.LENGTH_LONG).show();
                }
                else {
                    urlName.append("http://");
                    urlName.append(getString(R.string.serverAddress));
                    urlName.append(":8080/GrantPermission.php");
                    url = urlName.toString();
                    grant(Integer.toString(bota - Integer.valueOf(botborrow)), Integer.toString(botb + Integer.valueOf(botborrow)));
                    button.setVisibility(View.GONE);
                }
                break;
            case "Return":
                urlName.append("http://");
                urlName.append(getString(R.string.serverAddress));
                urlName.append(":8080/ReturnChemical.php");
                url = urlName.toString();
                Return();
                button.setVisibility(View.GONE);
                break;
            case "Receive":
                if (Integer.valueOf(botborrow) > botb) {
                    Toast.makeText(getApplicationContext(), "Something must have gone wrong", Toast.LENGTH_LONG).show();
                }
                else {
                    urlName.append("http://");
                    urlName.append(getString(R.string.serverAddress));
                    urlName.append(":8080/ReceiveChemical.php");
                    url = urlName.toString();
                    receive(Integer.toString(bota + Integer.valueOf(botborrow)), Integer.toString(botb - Integer.valueOf(botborrow)));
                    button.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void grant(final String NewBota, final String NewBotb) {
        class GrantPermission extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            BorrowBottlesClass bbc = new BorrowBottlesClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LogDetails.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                AlertDialog.Builder alert = new AlertDialog.Builder(LogDetails.this);
                alert.setTitle("Notice");
                alert.setMessage("Please return to the main screen for the update to take effect.");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();

                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String grantTime = sdf.format(dt);
                data.put("LogID", id);
                data.put("ChemID", chemid);
                data.put("NewBotA", NewBota);
                data.put("NewBotB", NewBotb);
                data.put("Granted", grantTime);
                data.put("GranterID", ID);

                String result = bbc.sendPostRequest(url, data);
                //Log.d("TAG", result);

                return result;
            }
        }

        GrantPermission gp = new GrantPermission();
        gp.execute(NewBota, NewBotb);
    }

    private void Return () {
        class ReturnChemical extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            BorrowBottlesClass bbc = new BorrowBottlesClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LogDetails.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                AlertDialog.Builder alert = new AlertDialog.Builder(LogDetails.this);
                alert.setTitle("Notice");
                alert.setMessage("Please return to the main screen for the update to take effect.");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();

                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String returnTime = sdf.format(dt);
                data.put("LogID", id);
                data.put("Returned", returnTime);

                String result = bbc.sendPostRequest(url, data);
                //Log.d("TAG", result);

                return result;
            }
        }

        ReturnChemical rc = new ReturnChemical();
        rc.execute();
    }

    private void receive (final String NewBota, final String NewBotb) {
        class ReceiveChemical extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            BorrowBottlesClass bbc = new BorrowBottlesClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LogDetails.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                AlertDialog.Builder alert = new AlertDialog.Builder(LogDetails.this);
                alert.setTitle("Notice");
                alert.setMessage("Please return to the main screen for the update to take effect.");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();

                java.util.Date dt = new java.util.Date();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = sdf.format(dt);
                data.put("LogID", id);
                data.put("ChemID", chemid);
                data.put("NewBotA", NewBota);
                data.put("NewBotB", NewBotb);
                data.put("Received", receiveTime);
                data.put("ReceiverID", ID);

                String result = bbc.sendPostRequest(url, data);
                //Log.d("TAG", result);

                return result;
            }
        }

        ReceiveChemical rc = new ReceiveChemical();
        rc.execute(NewBota, NewBotb);
    }
}
