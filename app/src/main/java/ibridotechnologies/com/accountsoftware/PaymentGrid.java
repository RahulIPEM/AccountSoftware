package ibridotechnologies.com.accountsoftware;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import ibridotechnologies.com.accountsoftware.Model.EditOrderParams;
import ibridotechnologies.com.accountsoftware.getJson.gvPaymentJson.JSONDownloaderGV;

public class PaymentGrid extends AppCompatActivity {

    ProgressDialog progressDialog;
    GridView gridView;

    String JsonURLGetPayment = "http://www.acmecreations.co.in/api/AccountManagement/GetPaymentDetailsByPartyDetails/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_grid);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String financial_year = bundle.getString("financial_year");
        String party_id = bundle.getString("party_id");

        Log.d("Party Id :",party_id);
        Log.d("Financial Year",financial_year);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");

        progressDialog = new ProgressDialog(PaymentGrid.this);
        progressDialog.setTitle("Getting Payments");
        progressDialog.setMessage("Loading... Please wait.");
        progressDialog.show();

        gridView = (GridView)findViewById(R.id.GridPayment);

        String getOrderURL = JsonURLGetPayment+financial_year+"/"+party_id;
        Log.d("Order URL :",getOrderURL);
        new JSONDownloaderGV(PaymentGrid.this,getOrderURL,gridView,progressDialog,font).execute();
    }
}
