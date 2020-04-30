package th.ac.mwits.www.mwitschemical;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class AddChemical extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName;
    private EditText editTextFormula;
    private EditText editTextStore;
    private EditText editTextPrice;
    private EditText editTextBottles;
    private EditText editTextVolume;

    private Button buttonAdd;

    StringBuilder urlName = new StringBuilder();

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chemical);
        urlName.append("http://");
        urlName.append(getString(R.string.serverAddress));
        urlName.append(":8080/AddChemical.php");
        url = urlName.toString();

        editTextName = (EditText) findViewById(R.id.Name);
        editTextFormula = (EditText) findViewById(R.id.Formula);
        editTextStore = (EditText) findViewById(R.id.Store);
        editTextPrice = (EditText) findViewById(R.id.Price);
        editTextBottles = (EditText) findViewById(R.id.Bottles);
        editTextVolume = (EditText) findViewById(R.id.Volume);

        buttonAdd = (Button) findViewById(R.id.Add);

        buttonAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        addChemical();
    }

    private void addChemical() {
        String name = editTextName.getText().toString();
        String formula = editTextFormula.getText().toString();
        String store = editTextStore.getText().toString();
        String price = editTextPrice.getText().toString();
        String bottles = editTextBottles.getText().toString();
        String volume = editTextVolume.getText().toString();
        add(name, formula, store, price, bottles, volume);
    }

    private void add(String name, String formula, String store, String price, String bottles, String volume) {
        class AddNewChemical extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            AddNewChemicalClass ancc = new AddNewChemicalClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddChemical.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("Name", params[0]);
                data.put("Formula", params[1]);
                data.put("Store", params[2]);
                data.put("Price", params[3]);
                data.put("Bottles", params[4]);
                data.put("Volume", params[5]);

                String result = ancc.sendPostRequest(url, data);
                //Log.d("TAG", result);

                return result;
            }
        }

        AddNewChemical anc = new AddNewChemical();
        anc.execute(name, formula, store, price, bottles, volume);
    }
}
