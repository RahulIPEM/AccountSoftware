package ibridotechnologies.com.accountsoftware;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class CashReceived extends AppCompatActivity {

    EditText editText1,editText2;
    Button btnSave;
    TextView txtFontCash1;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_received);

        editText1 = (EditText)findViewById(R.id.editTextAmount);
        editText2 = (EditText)findViewById(R.id.editTextDate);

        editText1.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontCash1 = (TextView)findViewById(R.id.txtFontCash1);
        txtFontCash1.setTypeface(font);
        txtFontCash1.setText("\uf156");

        builder = new AlertDialog.Builder(this);

        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int startYear = calendar.get(Calendar.YEAR);
                int startMonth = calendar.get(Calendar.MONTH);
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CashReceived.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        String date=selectedyear+"-"+(selectedmonth+1)+"-"+selectedday;
                        editText2.setText(date);
                    }
                },startYear,startMonth,startDay);
                datePickerDialog.setTitle("Select Amount Received Date");
                datePickerDialog.show();
            }
        });

        btnSave = (Button)findViewById(R.id.btnSaveCash);
        btnSave.setBackgroundResource(R.drawable.button_rounded_border);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!editText1.getText().toString().equals("") && !editText2.getText().toString().equals("")) {
                    PaymentParams paymentParams = new PaymentParams();
                    paymentParams.setPaymentAmount(editText1.getText().toString());
                    paymentParams.setPaymentReceivedDate(editText2.getText().toString());

                    int orderId = paymentParams.getPartyId();
                    int paymentModeId = paymentParams.getPaymentModeId();
                    String paymentAmount = paymentParams.getPaymentAmount();
                    String paymentReceivedDate = paymentParams.getPaymentReceivedDate();
                    String bankId = paymentParams.getBankId();
                    String chequeNo = paymentParams.getChequeNo();
                    String chequeDate = paymentParams.getChequeDate();
                    String financialYear = paymentParams.getFinancialYear();

                    addPaymentDetails(orderId, paymentModeId, paymentAmount, paymentReceivedDate, bankId, chequeNo, chequeDate, financialYear);
                    //Toast.makeText(CashReceived.this,"Save fired, But not saved.",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CashReceived.this,"Please, fill the above fields.",Toast.LENGTH_SHORT).show();
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
            jsonBody.put("Bank_Id", "0000");
            jsonBody.put("PaymentMode_Id", paymentModeId);
            jsonBody.put("Payment_Amount", paymentAmount);
            jsonBody.put("Payment_ReceivedDate", paymentReceivedDate);
            jsonBody.put("ChequeNo", "0000");
            jsonBody.put("ChequeDate", "01-01-2190");
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
                        builder.setMessage("Saved successfully");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(CashReceived.this,OptionsActivity.class));
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else {
                        builder.setTitle("Payment status");
                        builder.setMessage("Something went wrong. Payment not saved. Please, try again.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

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
}
