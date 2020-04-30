package th.ac.mwits.www.mwitschemical;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {

    ImageButton List;
    ImageButton Insert;
    ImageButton Delete;
    ImageButton Search;
    ImageButton Borrow;
    ImageButton Return;
    ImageButton Notifications;
    String ID;
    String Role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getString("EXTRA_ID");
            Role = extras.getString("EXTRA_ROLE");
            //Log.d("TAG", ID+" "+Role);
        }

        List = (ImageButton) findViewById(R.id.List);
        List.setOnClickListener(this);
        Insert = (ImageButton) findViewById(R.id.Insert);
        Insert.setOnClickListener(this);
        Delete = (ImageButton) findViewById(R.id.Delete);
        Delete.setOnClickListener(this);
        Search = (ImageButton) findViewById(R.id.Search);
        Search.setOnClickListener(this);
        Borrow = (ImageButton) findViewById(R.id.Borrow);
        Borrow.setOnClickListener(this);
        Return = (ImageButton) findViewById(R.id.Return);
        Return.setOnClickListener(this);
        Notifications = (ImageButton) findViewById(R.id.Notifications);
        Notifications.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //Log.d("TAG", "ID" + v.getId());
        switch(v.getId()) {
            case R.id.Delete:
                if (Role.equals("S"))
                {
                    alert.setTitle("Error");
                    alert.setMessage("You do not have permission to access this feature.");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });
                    alert.show();
                }
                else
                {
                    /*alert.setTitle("Delete");
                    alert.setMessage("Sending you to another screen...");
                    alert.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {*/
                            Intent intentList = new Intent(MainScreen.this, ChemicalSearch.class);
                            intentList.putExtra("EXTRA_ID", ID);
                            intentList.putExtra("EXTRA_ROLE", Role);
                            intentList.putExtra("EXTRA_PARENT", "Delete");
                            startActivity(intentList);
                        /*}
                    });
                    alert.show();*/
                }
                break;
            case R.id.List:
                /*alert.setTitle("List");
                alert.setMessage("Sending you to another screen...");
                alert.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {*/
                        Intent intentList = new Intent(MainScreen.this, ChemicalList.class);
                        intentList.putExtra("EXTRA_ID", ID);
                        intentList.putExtra("EXTRA_ROLE", Role);
                        intentList.putExtra("EXTRA_PARENT", "List");
                        startActivity(intentList);
                    /*}
                });
                alert.show();*/
                break;
            case R.id.Insert:
                if (Role.equals("S"))
                {
                    alert.setTitle("Error");
                    alert.setMessage("You do not have permission to access this feature.");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });
                    alert.show();
                } else {
                    alert.setTitle("Insert");
                    alert.setMessage("Select mode of inserting chemicals");
                    alert.setPositiveButton("New", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intentAddNew = new Intent(MainScreen.this, AddChemical.class);
                            intentAddNew.putExtra("EXTRA_ID", ID);
                            intentAddNew.putExtra("EXTRA_ROLE", Role);
                            startActivity(intentAddNew);
                        }
                    });
                    alert.setNeutralButton("Existing", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intentInsert = new Intent(MainScreen.this, ChemicalSearch.class);
                            intentInsert.putExtra("EXTRA_ID", ID);
                            intentInsert.putExtra("EXTRA_ROLE", Role);
                            intentInsert.putExtra("EXTRA_PARENT", "Insert");
                            startActivity(intentInsert);
                        }
                    });
                    alert.show();
                }
                break;
            case R.id.Notifications:
                /*alert.setTitle("Notifications");
                alert.setMessage("Sending you to another screen...");
                alert.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {*/
                        Intent intentNoti = new Intent(MainScreen.this, Notifications.class);
                        intentNoti.putExtra("EXTRA_ID", ID);
                        intentNoti.putExtra("EXTRA_ROLE", Role);
                        intentNoti.putExtra("EXTRA_PARENT", "Notifications");
                        startActivity(intentNoti);
                    /*}
                });
                alert.show();*/
                break;
            case R.id.Return:
                if (Role.equals("S"))
                {
                    /*alert.setTitle("Return");
                    alert.setMessage("Sending you to another screen...");
                    alert.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {*/
                            Intent intentReturn = new Intent(MainScreen.this, ReturnChemical.class);
                            intentReturn.putExtra("EXTRA_ID", ID);
                            intentReturn.putExtra("EXTRA_ROLE", Role);
                            intentReturn.putExtra("EXTRA_PARENT", "Return");
                            startActivity(intentReturn);
                        /*}
                    });
                    alert.show();*/
                } else {
                    alert.setTitle("Return");
                    alert.setMessage("Select mode of returning chemicals");
                    alert.setPositiveButton("Return", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intentReturn = new Intent(MainScreen.this, ReturnChemical.class);
                            intentReturn.putExtra("EXTRA_ID", ID);
                            intentReturn.putExtra("EXTRA_ROLE", Role);
                            intentReturn.putExtra("EXTRA_PARENT", "Return");
                            startActivity(intentReturn);
                        }
                    });
                    alert.setNeutralButton("Receive", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intentReceive = new Intent(MainScreen.this, ReceiveChemical.class);
                            intentReceive.putExtra("EXTRA_ID", ID);
                            intentReceive.putExtra("EXTRA_ROLE", Role);
                            intentReceive.putExtra("EXTRA_PARENT", "Receive");
                            startActivity(intentReceive);
                        }
                    });
                    alert.show();
                }
                break;
            case R.id.Search:
                /*alert.setTitle("Search");
                alert.setMessage("Sending you to another screen...");
                alert.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {*/
                        Intent intentSearch = new Intent(MainScreen.this, ChemicalSearch.class);
                        intentSearch.putExtra("EXTRA_ID", ID);
                        intentSearch.putExtra("EXTRA_ROLE", Role);
                        intentSearch.putExtra("EXTRA_PARENT", "Search");
                        startActivity(intentSearch);
                    /*}
                });
                alert.show();*/
                break;
            case R.id.Borrow:
                if (Role.equals("S"))
                {
                    /*alert.setTitle("Borrow");
                    alert.setMessage("Sending you to another screen...");
                    alert.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {*/
                            Intent intentBorrow = new Intent(MainScreen.this, ChemicalSearch.class);
                            intentBorrow.putExtra("EXTRA_ID", ID);
                            intentBorrow.putExtra("EXTRA_ROLE", Role);
                            intentBorrow.putExtra("EXTRA_PARENT", "Borrow");
                            startActivity(intentBorrow);
                        /*}
                    });
                    alert.show();*/
                } else {
                    alert.setTitle("Borrow");
                    alert.setMessage("Select mode of borrowing chemicals");
                    alert.setPositiveButton("Borrow", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intentBorrow = new Intent(MainScreen.this, ChemicalSearch.class);
                            intentBorrow.putExtra("EXTRA_ID", ID);
                            intentBorrow.putExtra("EXTRA_ROLE", Role);
                            intentBorrow.putExtra("EXTRA_PARENT", "Borrow");
                            startActivity(intentBorrow);
                        }
                    });
                    alert.setNeutralButton("Grant Permission", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intentGrant = new Intent(MainScreen.this, GrantPermission.class);
                            intentGrant.putExtra("EXTRA_ID", ID);
                            intentGrant.putExtra("EXTRA_ROLE", Role);
                            intentGrant.putExtra("EXTRA_PARENT", "Grant Permission");
                            startActivity(intentGrant);
                        }
                    });
                    alert.show();
                }
                break;
        }
    }
}
