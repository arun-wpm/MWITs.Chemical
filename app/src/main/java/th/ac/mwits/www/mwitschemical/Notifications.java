package th.ac.mwits.www.mwitschemical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Notifications extends AppCompatActivity implements View.OnClickListener {

    String ID;
    String Role;
    Button ToGrant;
    Button ToReturn;
    Button ToReceive;
    TextView Chemical;
    Button Have0Bottles;
    Button Have1Bottle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getString("EXTRA_ID");
            Role = extras.getString("EXTRA_ROLE");
            //Log.d("TAG", ID + " " + Role);
        }

        ToGrant = (Button) findViewById(R.id.ToGrant);
        ToGrant.setOnClickListener(this);
        ToReturn = (Button) findViewById(R.id.ToReturn);
        ToReturn.setOnClickListener(this);
        ToReceive = (Button) findViewById(R.id.ToReceive);
        ToReceive.setOnClickListener(this);
        Chemical = (TextView) findViewById(R.id.textView2);
        Have0Bottles = (Button) findViewById(R.id.Have0Bottles);
        Have0Bottles.setOnClickListener(this);
        Have1Bottle = (Button) findViewById(R.id.Have1Bottle);
        Have1Bottle.setOnClickListener(this);

        switch (Role) {
            case "S":
                Chemical.setVisibility(View.GONE);
                Have0Bottles.setVisibility(View.GONE);
                Have1Bottle.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ToGrant:
                Intent intentGrant = new Intent(Notifications.this, TabToGrant.class);
                intentGrant.putExtra("EXTRA_ID", ID);
                intentGrant.putExtra("EXTRA_ROLE", Role);
                intentGrant.putExtra("EXTRA_PARENT", "List");
                startActivity(intentGrant);
                break;
            case R.id.ToReturn:
                Intent intentReturn = new Intent(Notifications.this, TabToReturn.class);
                intentReturn.putExtra("EXTRA_ID", ID);
                intentReturn.putExtra("EXTRA_ROLE", Role);
                intentReturn.putExtra("EXTRA_PARENT", "List");
                startActivity(intentReturn);
                break;
            case R.id.ToReceive:
                Intent intentReceive = new Intent(Notifications.this, TabToReceive.class);
                intentReceive.putExtra("EXTRA_ID", ID);
                intentReceive.putExtra("EXTRA_ROLE", Role);
                intentReceive.putExtra("EXTRA_PARENT", "List");
                startActivity(intentReceive);
                break;
            case R.id.Have0Bottles:
                Intent intent0Bottles = new Intent(Notifications.this, Tab0Bottles.class);
                intent0Bottles.putExtra("EXTRA_ID", ID);
                intent0Bottles.putExtra("EXTRA_ROLE", Role);
                intent0Bottles.putExtra("EXTRA_PARENT", "List");
                startActivity(intent0Bottles);
                break;
            case R.id.Have1Bottle:
                Intent intent1Bottle = new Intent(Notifications.this, Tab1Bottle.class);
                intent1Bottle.putExtra("EXTRA_ID", ID);
                intent1Bottle.putExtra("EXTRA_ROLE", Role);
                intent1Bottle.putExtra("EXTRA_PARENT", "List");
                startActivity(intent1Bottle);
                break;
        }
    }
}
