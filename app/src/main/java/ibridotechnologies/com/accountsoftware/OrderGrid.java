package ibridotechnologies.com.accountsoftware;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ibridotechnologies.com.accountsoftware.Model.EditOrderParams;
import ibridotechnologies.com.accountsoftware.getJson.getParty.JSONDownloaderSP;
import ibridotechnologies.com.accountsoftware.getJson.gvJson.JSONDownloaderGV;

public class OrderGrid extends AppCompatActivity {

    ProgressDialog progressDialog;
    GridView gridView;

    String JsonURLGetOrder = "http://www.acmecreations.co.in/api/AccountManagement/GetOrderDetailsByPartyDetails/";
    String JsonURLParty = "http://www.acmecreations.co.in/api/AcmeQuotation/GetPartyList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_grid);

        progressDialog = new ProgressDialog(OrderGrid.this);
        progressDialog.setTitle("Getting Order List");
        progressDialog.setMessage("Loading... Please wait.");
        progressDialog.show();

        gridView = (GridView)findViewById(R.id.GridOrder);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");

        //new JSONDownloaderSP(this,JsonURLParty,spinnerPartyName,progressDialog).execute();


                    EditOrderParams eop = new EditOrderParams();

                    String getOrderURL = JsonURLGetOrder+eop.getFinancialYear()+"/"+eop.getPartyId();
                    Log.d("Order URL :",getOrderURL);
                    new JSONDownloaderGV(OrderGrid.this,getOrderURL,gridView,progressDialog,font).execute();
                    //Intent intent = new Intent(SelectBook.this, EditPages.class);
                    //startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.navigation_home:
                startActivity(new Intent(this,OptionsActivity.class));
                break;
            case R.id.navigation_about:
                startActivity(new Intent(this,AboutUs.class));
                break;
            case R.id.navigation_exit:
                finishAffinity();
                System.exit(0);
        }

        return true;
    }
}
