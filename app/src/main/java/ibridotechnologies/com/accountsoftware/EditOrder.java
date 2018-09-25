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

import java.util.Calendar;

import ibridotechnologies.com.accountsoftware.Model.UpdatePaymentDetails;
import ibridotechnologies.com.accountsoftware.getJson.getParty.JSONDownloaderSP;

public class EditOrder extends AppCompatActivity {

    Spinner spinnerPartyName;
    Button btnGetOrder;
    ProgressDialog progressDialog;
    TextView txtFontCash;
    EditText etFinancialYear;
    String JsonURL = "http://www.acmecreations.co.in/api/AcmeQuotation/GetPartyList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        txtFontCash = (TextView)findViewById(R.id.txtFontCash);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontCash.setTypeface(font);
        txtFontCash.setText("\uf0d6");

        etFinancialYear = (EditText)findViewById(R.id.editFinancialYear);
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        etFinancialYear.setText(thisYear+"-"+(thisYear+1));
        etFinancialYear.setBackgroundResource(R.drawable.edit_text_rounded_border);

        spinnerPartyName = (Spinner)findViewById(R.id.PartyName);
        btnGetOrder = (Button)findViewById(R.id.btnGetOrder);

        spinnerPartyName.setBackgroundResource(R.drawable.spinner_rounded_border);
        btnGetOrder.setBackgroundResource(R.drawable.button_rounded_border);

        progressDialog = new ProgressDialog(EditOrder.this);
        progressDialog.setTitle("Getting Client List");
        progressDialog.setMessage("Loading... Please wait.");
        progressDialog.show();

        new JSONDownloaderSP(this,JsonURL, spinnerPartyName, progressDialog).execute();

        btnGetOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Params p = new Params();
                UpdatePaymentDetails paymentParams = new UpdatePaymentDetails();
                paymentParams.setPartyId(p.getPartyId());

                Intent intent = new Intent(EditOrder.this,PaymentGrid.class);

                //Create the bundle
                Bundle bundle = new Bundle();
                //Add your data to bundle
                bundle.putString("financial_year", etFinancialYear.getText().toString());
                bundle.putString("party_id", String.valueOf(p.getPartyId()));
                //Add the bundle to the intent
                intent.putExtras(bundle);
                startActivity(intent);
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
