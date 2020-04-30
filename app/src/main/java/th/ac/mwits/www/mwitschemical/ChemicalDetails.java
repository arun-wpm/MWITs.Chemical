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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ChemicalDetails extends AppCompatActivity implements View.OnClickListener {
    String ID;
    String Role;
    String Parent;
    String JSON;

    private static String TAG_ID = "ID";
    private static String TAG_NAME = "Name";
    private static String TAG_FORMULA = "Formula";
    private static String TAG_STORE = "Store";
    private static String TAG_PRICE = "Price";
    private static String TAG_VOLUME = "Volume";
    private static String TAG_BOTAVAILABLE = "BotAvailable";
    private static String TAG_BOTBORROWING = "BotBorrowing";

    TextView TextName;
    TextView TextFormula;
    TextView TextStore;
    TextView TextPrice;
    TextView TextVolume;
    TextView TextTotal;
    TextView TextBotAvailable;
    TextView TextBotBorrowing;
    Button button;

    String id;
    String name;
    String formula;
    String store;
    String price;
    String volume;
    String botavailable;
    String botborrowing;
    int bota, botb;

    final Context context = this;

    StringBuilder urlName = new StringBuilder();
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_details);

        TextName = (TextView) findViewById(R.id.DetailName);
        TextFormula = (TextView) findViewById(R.id.DetailFormula);
        TextStore = (TextView) findViewById(R.id.DetailStore);
        TextPrice = (TextView) findViewById(R.id.DetailPrice);
        TextVolume = (TextView) findViewById(R.id.DetailVolume);
        TextTotal = (TextView) findViewById(R.id.DetailTotal);
        TextBotAvailable = (TextView) findViewById(R.id.DetailBotAvailable);
        TextBotBorrowing = (TextView) findViewById(R.id.DetailBotBorrowing);
        button = (Button) findViewById(R.id.DetailButton);
        button.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getString("EXTRA_ID");
            Role = extras.getString("EXTRA_ROLE");
            Parent = extras.getString("EXTRA_PARENT");
            JSON = extras.getString("EXTRA_JSON");

            try {
                JSONObject jsonObj = new JSONObject(JSON);
                id = jsonObj.getString(TAG_ID);
                name = jsonObj.getString(TAG_NAME);
                formula = jsonObj.getString(TAG_FORMULA);
                store = jsonObj.getString(TAG_STORE);
                price = jsonObj.getString(TAG_PRICE);
                volume = jsonObj.getString(TAG_VOLUME);
                botavailable = jsonObj.getString(TAG_BOTAVAILABLE);
                botborrowing = jsonObj.getString(TAG_BOTBORROWING);
                //Log.d("TAG", price + " " + botavailable + " " + botborrowing);
                if (botavailable.equals("null"))
                    bota = 0;
                else
                    bota = Integer.valueOf(botavailable);
                if (botborrowing.equals("null"))
                    botb = 0;
                else
                    botb = Integer.valueOf(botborrowing);
                int tot = bota + botb;

                TextName.setText("Name: " + name);
                TextFormula.setText("Formula: " + formula);
                //TextStore.setText("Store: " + store);
                TextStore.setText(Html.fromHtml("Store: " + store));
                TextPrice.setText("Price: " + price);
                TextVolume.setText("Volume: " + volume);
                TextTotal.setText("Total Amount: " + tot);
                TextBotAvailable.setText("Amount Available: " + bota);
                TextBotBorrowing.setText("Amount Borrowing: " + botb);

                /*if (!Parent.equals("Search") && !Parent.equals("List"))
                    button.setText(Parent);
                else
                    button.setVisibility(View.INVISIBLE);*/
                switch(Parent)
                {
                    case "Search":
                    case "List":
                        button.setVisibility(View.INVISIBLE);
                        break;

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
            case "Insert":

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.details_dialog_insert, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                alertDialogBuilder.setTitle("Enter number of new bottles:");

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.DetailsDialogInsert);

                // set dialog message
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int NewBottles = Integer.valueOf(userInput.getText().toString());
                        urlName.append("http://");
                        urlName.append(getString(R.string.serverAddress));
                        urlName.append(":8080/DialogInsert.php");
                        url = urlName.toString();
                        insert(Integer.toString(NewBottles + bota));
                    }
                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

                button.setVisibility(View.GONE);
                break;
            case "Delete":

                // get prompts.xml view
                LayoutInflater lid = LayoutInflater.from(context);
                View promptsViewD = lid.inflate(R.layout.details_dialog_insert, null);

                AlertDialog.Builder alertDialogBuilderD = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilderD.setView(promptsViewD);
                alertDialogBuilderD.setTitle("Enter number of bottles to be deleted:");

                final EditText userInputD = (EditText) promptsViewD
                        .findViewById(R.id.DetailsDialogInsert);

                // set dialog message
                alertDialogBuilderD.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int DeleteBottles = Integer.valueOf(userInputD.getText().toString());
                        if (DeleteBottles > bota) {
                            Toast.makeText(getApplicationContext(), "Amount exceeds amount available", Toast.LENGTH_LONG).show();
                        }
                        else {
                            urlName.append("http://");
                            urlName.append(getString(R.string.serverAddress));
                            urlName.append(":8080/DialogInsert.php");
                            url = urlName.toString();
                            delete(Integer.toString(bota - DeleteBottles));
                        }
                    }
                });

                // create alert dialog
                AlertDialog alertDialogD = alertDialogBuilderD.create();

                // show it
                alertDialogD.show();

                button.setVisibility(View.GONE);
                break;
            case "Borrow":
                // get prompts.xml view
                LayoutInflater li2 = LayoutInflater.from(context);
                View promptsView2 = li2.inflate(R.layout.details_dialog_insert, null);

                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder2.setView(promptsView2);
                alertDialogBuilder2.setTitle("Enter number of bottles to borrow:");

                final EditText userInput2 = (EditText) promptsView2
                        .findViewById(R.id.DetailsDialogInsert);

                // set dialog message
                alertDialogBuilder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int BorrowBottles = Integer.valueOf(userInput2.getText().toString());
                        if (BorrowBottles > bota) {
                            Toast.makeText(getApplicationContext(), "Amount exceeds amount available", Toast.LENGTH_LONG).show();
                        }
                        else {
                            urlName.append("http://");
                            urlName.append(getString(R.string.serverAddress));
                            urlName.append(":8080/DialogBorrow.php");
                            url = urlName.toString();
                            borrow(Integer.toString(BorrowBottles));
                        }
                    }
                });

                // create alert dialog
                AlertDialog alertDialog2 = alertDialogBuilder2.create();

                // show it
                alertDialog2.show();

                button.setVisibility(View.GONE);
                break;
        }
    }

    private void borrow(final String borrowAmount) {
        class BorrowBottles extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            BorrowBottlesClass bbc = new BorrowBottlesClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ChemicalDetails.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                AlertDialog.Builder alert = new AlertDialog.Builder(ChemicalDetails.this);
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
                String borrowTime = sdf.format(dt);
                data.put("ID", ID);
                data.put("ChemID", id);
                data.put("BorrowAmount", borrowAmount);
                data.put("BorrowTime", borrowTime);

                String result = bbc.sendPostRequest(url, data);
                //Log.d("TAG", result);

                return result;
            }
        }

        BorrowBottles bb = new BorrowBottles();
        bb.execute(borrowAmount);
    }

    private void insert(final String newBottles) {
        class UpdateBottles extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            NewBottlesClass nbc = new NewBottlesClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ChemicalDetails.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                AlertDialog.Builder alert = new AlertDialog.Builder(ChemicalDetails.this);
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
                data.put("ID", id);
                data.put("NewBottles", newBottles);

                String result = nbc.sendPostRequest(url, data);
                //Log.d("TAG", result);

                return result;
            }
        }

        UpdateBottles ub = new UpdateBottles();
        ub.execute(newBottles);
    }

    private void delete(final String deleteBottles) {
        class UpdateBottles extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            NewBottlesClass nbc = new NewBottlesClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ChemicalDetails.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                AlertDialog.Builder alert = new AlertDialog.Builder(ChemicalDetails.this);
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
                data.put("ID", id);
                data.put("NewBottles", deleteBottles);

                String result = nbc.sendPostRequest(url, data);
                //Log.d("TAG", result);

                return result;
            }
        }

        UpdateBottles ub = new UpdateBottles();
        ub.execute(deleteBottles);
    }
}
