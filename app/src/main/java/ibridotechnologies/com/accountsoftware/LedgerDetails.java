package ibridotechnologies.com.accountsoftware;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ibridotechnologies.com.accountsoftware.Model.Ledger;
import ibridotechnologies.com.accountsoftware.getJson.getParty.JSONDownloaderSP;

public class LedgerDetails extends AppCompatActivity {

    TextView txtFontLedger;
    EditText etFinancialYear;
    Spinner spPartyName;
    ProgressDialog progressDialog;
    Button btnGetBill;
    String JsonParty = "http://www.acmecreations.co.in/api/AcmeQuotation/GetPartyList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger_details);

        setTitle("Select Party : Account Software");

        txtFontLedger = (TextView)findViewById(R.id.txtFontLedger);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontLedger.setTypeface(font);
        txtFontLedger.setText("\uf07b");

        etFinancialYear = (EditText)findViewById(R.id.editFinancialYear);
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        etFinancialYear.setText(thisYear+"-"+(thisYear+1));
        etFinancialYear.setBackgroundResource(R.drawable.edit_text_rounded_border);

        progressDialog = new ProgressDialog(LedgerDetails.this);
        progressDialog.setTitle("Getting Client List");
        progressDialog.setMessage("Loading... Please wait.");
        progressDialog.show();

        spPartyName = (Spinner)findViewById(R.id.spPartyName);
        spPartyName.setBackgroundResource(R.drawable.spinner_rounded_border);
        new JSONDownloaderSP(LedgerDetails.this,JsonParty,spPartyName,progressDialog).execute();

        btnGetBill = (Button)findViewById(R.id.btnGetDetails);
        btnGetBill.setBackgroundResource(R.drawable.button_rounded_border);
        btnGetBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spPartyName.getSelectedItemPosition() > 0 && !etFinancialYear.getText().toString().isEmpty()) {
                    Params params = new Params();

                    Ledger ledger = new Ledger();
                    ledger.setPartyId(String.valueOf(params.getPartyId()));
                    ledger.setPartyName(spPartyName.getSelectedItem().toString());
                    ledger.setFinancialYear(etFinancialYear.getText().toString());
                    startActivity(new Intent(LedgerDetails.this,LedgerOptions.class));
                }
                else {
                    Toast.makeText(LedgerDetails.this,"Please select the above fields.",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
