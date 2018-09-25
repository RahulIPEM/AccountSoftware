package ibridotechnologies.com.accountsoftware;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import ibridotechnologies.com.accountsoftware.Model.PaymentToUpdate;
import ibridotechnologies.com.accountsoftware.Model.UpdatePaymentDetails;
import ibridotechnologies.com.accountsoftware.getJson.getBanks.JSONDownloaderSP;

public class EditChequeReceived extends AppCompatActivity {

    EditText editText1,editText2,editText3,editText4;
    Button btnModify;
    Spinner spinnerBank;
    ProgressDialog progressDialog;
    String JsonURL = "http://www.acmecreations.co.in/api/AccountManagement/GetBankDetails";

    AlertDialog.Builder builder;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cheque_received);

        spinnerBank = (Spinner) findViewById(R.id.spinnerBankModify);
        editText2 = (EditText)findViewById(R.id.editTextChequeNoModify);
        editText3 = (EditText)findViewById(R.id.editTextChequeAmountModify);
        editText4 = (EditText)findViewById(R.id.editTextChequeDateModify);
        btnModify = (Button)findViewById(R.id.btnModifyCheque);

        spinnerBank.setBackgroundResource(R.drawable.spinner_rounded_border);
        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText3.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText4.setBackgroundResource(R.drawable.edit_text_rounded_border);
        btnModify.setBackgroundResource(R.drawable.button_rounded_border);

        PaymentToUpdate pt = new PaymentToUpdate();
        spinnerBank.setSelection(Integer.parseInt(pt.getBank_Id()));
        editText2.setText(pt.getChequeNo());
        editText3.setText(pt.getPayment_Amount());
        String[] date = pt.getChequeDate().split("T");
        editText4.setText(date[0]);

        progressDialog = new ProgressDialog(EditChequeReceived.this);
        progressDialog.setTitle("Getting Bank List");
        progressDialog.setMessage("Loading... Please wait.");
        progressDialog.show();

        new JSONDownloaderSP(this,JsonURL,spinnerBank,progressDialog).execute();

        builder = new AlertDialog.Builder(this);

        editText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int startYear = calendar.get(Calendar.YEAR);
                int startMonth = calendar.get(Calendar.MONTH);
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditChequeReceived.this, new DatePickerDialog.OnDateSetListener() {
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

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentParams payParams = new PaymentParams();
                PaymentToUpdate p = new PaymentToUpdate();
                String paymentId = p.getPayment_Id();
                String paymentModeId = p.getPaymentMode_Id();
                String paymentAmount = editText3.getText().toString().trim();
                String bankId = payParams.getBankId();
                String chequeNo = editText2.getText().toString().trim();
                String chequeDate = editText4.getText().toString().trim();
                updatePaymentDetailsData(paymentId,paymentModeId,paymentAmount,bankId,chequeNo,chequeDate);
            }
        });
    }

    protected void updatePaymentDetailsData(String paymentId, String paymentModeId, String paymentAmount , String bankId, String chequeNo, String chequeDate){
        try {
            String savedBy = "Self";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://www.acmecreations.co.in/api/AccountManagement/UpdatePaymentAgaintBookOrder";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Payment_Id", paymentId); //Add the data you'd like to send to the server.
            jsonBody.put("PaymentMode_Id", paymentModeId);
            jsonBody.put("Payment_Amount", paymentAmount);
            jsonBody.put("Bank_Id", bankId);
            jsonBody.put("ChequeNo", chequeNo);
            jsonBody.put("ChequeDate", chequeDate);
            jsonBody.put("LastMod_By", savedBy);
            final String requestBody = jsonBody.toString();
            Log.d("JSON Data to Server :",requestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Response JSON :",response);
                    builder.setTitle("Payment update status");
                    builder.setMessage("Response : "+response.toString());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            spinnerBank.setSelection(0);
                            editText2.setText("");
                            editText3.setText("");
                            editText4.setText("");
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
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
