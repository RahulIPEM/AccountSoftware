package ibridotechnologies.com.accountsoftware;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import ibridotechnologies.com.accountsoftware.Model.*;
import ibridotechnologies.com.accountsoftware.getJson.getParty.JSONDownloaderSP;

public class BookReportParameter extends AppCompatActivity {

    EditText etFromDate,etToDate;
    Spinner spinnerFinancialYear;
    ProgressDialog progressDialog;
    TextView txtFontParty;
    Button btnGetDetails;
    String JsonURL = "http://www.acmecreations.co.in/api/AcmeQuotation/GetPartyList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_report_parameter);

        spinnerFinancialYear = (Spinner)findViewById(R.id.financialYear);
        spinnerFinancialYear.setBackgroundResource(R.drawable.spinner_rounded_border);

        //setting icon
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontParty = (TextView)findViewById(R.id.txtFontColoring);
        txtFontParty.setTypeface(font);
        txtFontParty.setText("\uf02d");

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
                AllBookDetails allBookDetails = new AllBookDetails();
                allBookDetails.setFinancialYear(spinnerFinancialYear.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        etFromDate = (EditText)findViewById(R.id.etFromDate);
        etToDate = (EditText)findViewById(R.id.etToDate);

        etFromDate.setBackgroundResource(R.drawable.edit_text_rounded_border);
        etToDate.setBackgroundResource(R.drawable.edit_text_rounded_border);

        etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int startYear = calendar.get(Calendar.YEAR);
                int startMonth = calendar.get(Calendar.MONTH);
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookReportParameter.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        String date=selectedyear+"-"+(selectedmonth+1)+"-"+selectedday;
                        etFromDate.setText(date);
                    }
                },startYear,startMonth,startDay);
                datePickerDialog.setTitle("Select Order Date");
                datePickerDialog.show();
            }
        });

        etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int startYear = calendar.get(Calendar.YEAR);
                int startMonth = calendar.get(Calendar.MONTH);
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookReportParameter.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        String date=selectedyear+"-"+(selectedmonth+1)+"-"+selectedday;
                        etToDate.setText(date);
                    }
                },startYear,startMonth,startDay);
                datePickerDialog.setTitle("Select Order Date");
                datePickerDialog.show();
            }
        });

        btnGetDetails = (Button)findViewById(R.id.btnGetDetails);
        btnGetDetails.setBackgroundResource(R.drawable.button_rounded_border);

        btnGetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ledger ledger = new Ledger();
                int partyId = Integer.parseInt(ledger.getPartyId());
                AllBookDetails allBookDetails = new AllBookDetails();
                allBookDetails.setPartyId(String.valueOf(partyId));
                allBookDetails.setFinancialYear(spinnerFinancialYear.getSelectedItem().toString());
                allBookDetails.setFromDate(etFromDate.getText().toString().trim());
                allBookDetails.setToDate(etToDate.getText().toString().trim());

                if (!allBookDetails.getFinancialYear().isEmpty() && !allBookDetails.getFromDate().isEmpty() && !allBookDetails.getToDate().isEmpty()) {
                    startActivity(new Intent(BookReportParameter.this,AllBookBillDateWiseReport.class));
                }
                else {
                    Toast.makeText(BookReportParameter.this,"Please select the above fields.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
