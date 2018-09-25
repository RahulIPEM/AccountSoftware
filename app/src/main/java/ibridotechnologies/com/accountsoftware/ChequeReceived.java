package ibridotechnologies.com.accountsoftware;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import ibridotechnologies.com.accountsoftware.Model.PaymentParams;
import ibridotechnologies.com.accountsoftware.getJson.getBanks.JSONDownloaderSP;

public class ChequeReceived extends AppCompatActivity {

    Spinner spinnerBank;
    EditText editText2,editText3,editText4;
    Button btnSave;
    TextView txtFontCheque;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    String JsonURL = "http://www.acmecreations.co.in/api/AccountManagement/GetBankDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheque_received);

        txtFontCheque = (TextView)findViewById(R.id.txtFontCheque);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontCheque.setTypeface(font);
        txtFontCheque.setText("\uf0d6");

        spinnerBank = (Spinner) findViewById(R.id.spinnerBank);
        editText2 = (EditText)findViewById(R.id.editTextChequeNo);
        editText3 = (EditText)findViewById(R.id.editTextChequeAmount);
        editText4 = (EditText)findViewById(R.id.editTextChequeDate);
        btnSave = (Button)findViewById(R.id.btnSaveCheque);
        builder = new AlertDialog.Builder(this);

        spinnerBank.setBackgroundResource(R.drawable.spinner_rounded_border);
        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText3.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText4.setBackgroundResource(R.drawable.edit_text_rounded_border);
        btnSave.setBackgroundResource(R.drawable.button_rounded_border);

        progressDialog = new ProgressDialog(ChequeReceived.this);
        progressDialog.setTitle("Getting Bank List");
        progressDialog.setMessage("Loading... Please wait.");
        progressDialog.show();

        new JSONDownloaderSP(this,JsonURL,spinnerBank,progressDialog).execute();

        editText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int startYear = calendar.get(Calendar.YEAR);
                int startMonth = calendar.get(Calendar.MONTH);
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ChequeReceived.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        String date=selectedyear+"-"+(selectedmonth+1)+"-"+selectedday;
                        editText4.setText(date);
                    }
                },startYear,startMonth,startDay);
                datePickerDialog.setTitle("Select Amount Received Date");
                datePickerDialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ChequeReceived.this,"Save Clicked Not Saved.",Toast.LENGTH_SHORT).show();
                PaymentParams payment = new PaymentParams();
                int partyId = payment.getPartyId();
                int paymentModeId = payment.getPaymentModeId();
                String paymentReceivedDate = "01-01-2190";
                String paymentAmount = editText3.getText().toString();
                String bankId = payment.getBankId();
                String chequeNo = editText2.getText().toString();
                String chequeDate = editText4.getText().toString();
                String financialYear = payment.getFinancialYear();

                if (partyId > 0 && paymentModeId > 0 && !paymentReceivedDate.equals("") && !paymentAmount.equals("") && !bankId.equals("") && !chequeNo.equals("") && !chequeDate.equals("") && !financialYear.equals("")) {
                    addPaymentDetails(partyId, paymentModeId, paymentAmount, paymentReceivedDate, bankId, chequeNo, chequeDate, financialYear);
                }
                else {
                    Toast.makeText(ChequeReceived.this,"Please fill the above fields.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void addPaymentDetails(int partyId, int paymentModeId, String paymentAmount, String paymentReceivedDate, String bankId, String chequeNo, String chequeDate, String financialYear){
        try {
            String savedBy = "Self";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://www.acmecreations.co.in/api/AccountManagement/AddPaymentAgaintBookOrder";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Party_Id", partyId); //Add the data you'd like to send to the server.
            jsonBody.put("Bank_Id", bankId);
            jsonBody.put("PaymentMode_Id", paymentModeId);
            jsonBody.put("Payment_Amount", paymentAmount);
            jsonBody.put("Payment_ReceivedDate", paymentReceivedDate);
            jsonBody.put("ChequeNo", chequeNo);
            jsonBody.put("ChequeDate", chequeDate);
            jsonBody.put("Saved_By", savedBy);
            jsonBody.put("FinancialYear", financialYear);
            final String requestBody = jsonBody.toString();
            Log.d("JSON Data to Server :",requestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Response JSON :",response);
                    if(response.equals("true")) {
                        builder.setTitle("Payment status");
                        builder.setMessage("Cheque details saved successfully.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(ChequeReceived.this,OptionsActivity.class));
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else {
                        builder.setTitle("Payment status");
                        builder.setMessage("Something went wrong. Cheque details not saved. Please, try again.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(ChequeReceived.this,OptionsActivity.class));
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    Log.d("Error",error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
