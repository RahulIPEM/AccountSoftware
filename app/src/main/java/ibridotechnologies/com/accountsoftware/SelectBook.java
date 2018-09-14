package ibridotechnologies.com.accountsoftware;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class SelectBook extends AppCompatActivity {

    TextView txtFontBook;
    EditText etFinancialYear;
    Spinner spinnerPartyName;
    Button btnModifySelectedBook;
    ProgressDialog progressDialog;
    GridView gridView;

    String JsonURLGetOrder = "http://www.acmecreations.co.in/api/AccountManagement/GetOrderDetailsByPartyDetails/";
    String JsonURLParty = "http://www.acmecreations.co.in/api/AcmeQuotation/GetPartyList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_book);

        txtFontBook = (TextView)findViewById(R.id.txtFontBook);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontBook.setTypeface(font);
        txtFontBook.setText("\uf044");

        etFinancialYear = (EditText)findViewById(R.id.editFinancialYear);
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        etFinancialYear.setText(thisYear+"-"+(thisYear+1));
        etFinancialYear.setBackgroundResource(R.drawable.edit_text_rounded_border);

        spinnerPartyName = (Spinner)findViewById(R.id.spinnerPartyName);

        btnModifySelectedBook = (Button)findViewById(R.id.btnModifySelectedBook);

        spinnerPartyName.setBackgroundResource(R.drawable.spinner_rounded_border);
        btnModifySelectedBook.setBackgroundResource(R.drawable.button_rounded_border);

        //spinnerBooksOrder = (Spinner)findViewById(R.id.spinnerBooksOrder);
        //spinnerBooksOrder.setBackgroundResource(R.drawable.spinner_rounded_border);

        progressDialog = new ProgressDialog(SelectBook.this);
        progressDialog.setTitle("Getting Client List");
        progressDialog.setMessage("Loading... Please wait.");
        progressDialog.show();

        //gridView = (GridView)findViewById(R.id.orderGrid);

        String editFinancialYear = etFinancialYear.getText().toString();

        new JSONDownloaderSP(this,JsonURLParty,spinnerPartyName,progressDialog).execute();

        btnModifySelectedBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int spinnerIndex = spinnerPartyName.getSelectedItemPosition();
                //int spinnerBooksIndex = spinnerBooksOrder.getSelectedItemPosition();

                if(spinnerIndex > 0 && !etFinancialYear.getText().toString().equals("")) {
                    Params p = new Params();
                    EditOrderParams eop = new EditOrderParams();
                    eop.setPartyId(p.getPartyId());
                    eop.setFinancialYear(etFinancialYear.getText().toString());
                    String getOrderURL = JsonURLGetOrder+eop.getFinancialYear()+"/"+eop.getPartyId();
                    Log.d("Order URL :",getOrderURL);
                    //new JSONDownloaderGV(SelectBook.this,getOrderURL,gridView).execute();
                    Intent intent = new Intent(SelectBook.this, OrderGrid.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SelectBook.this,"Please select above fields.", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(SelectBook.this,OptionsActivity.class));
                }
            }
        });
    }
}
