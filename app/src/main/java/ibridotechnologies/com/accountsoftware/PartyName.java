package ibridotechnologies.com.accountsoftware;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import ibridotechnologies.com.accountsoftware.Model.Order;
import ibridotechnologies.com.accountsoftware.getJson.getParty.JSONDownloaderSP;

public class PartyName extends AppCompatActivity {

    Spinner spinnerPartyName,spinnerFinancialYear;
    TextView txtFontParty;
    Button btnSave,btnPopupAddParty;
    ProgressDialog progressDialog;
    String JsonURL = "http://www.acmecreations.co.in/api/AcmeQuotation/GetPartyList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_name);

        spinnerPartyName = (Spinner)findViewById(R.id.searchableSpinner);
        spinnerPartyName.setBackgroundResource(R.drawable.spinner_rounded_border);

        spinnerFinancialYear = (Spinner)findViewById(R.id.financialYear);
        spinnerFinancialYear.setBackgroundResource(R.drawable.spinner_rounded_border);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 2017; i--) {
            years.add(i+"-"+(i+1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        spinnerFinancialYear.setAdapter(adapter);

        spinnerFinancialYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Order order = new Order();
                order.setFinancialYear(spinnerFinancialYear.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontParty = (TextView)findViewById(R.id.txtFontParty);
        txtFontParty.setTypeface(font);
        txtFontParty.setText("\uf007");


        progressDialog = new ProgressDialog(PartyName.this);
        progressDialog.setTitle("Getting Client List");
        progressDialog.setMessage("Loading... Please wait.");
        progressDialog.show();

        new JSONDownloaderSP(PartyName.this, JsonURL, spinnerPartyName, progressDialog).execute();

        btnPopupAddParty = (Button)findViewById(R.id.btnAddParty);
        btnPopupAddParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PartyName.this,PopupAddParty.class));
            }
        });

        btnSave = (Button) findViewById(R.id.btnGetParty);
        btnSave.setBackgroundResource(R.drawable.button_rounded_border);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                Params p = new Params();
                order.setPartyId(p.getPartyId());
                Log.d("ID :", String.valueOf(p.getPartyId()));

                int spinnerIndex = spinnerPartyName.getSelectedItemPosition();
                //Log.d("Spinner Index :",String.valueOf(spinnerIndex));
                if(spinnerIndex > 0) {
                    Intent intent = new Intent(PartyName.this, BookName.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(PartyName.this,"Please select a party before proceeding further.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
