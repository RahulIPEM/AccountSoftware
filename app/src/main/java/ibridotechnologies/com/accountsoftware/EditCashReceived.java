package ibridotechnologies.com.accountsoftware;

import android.app.DatePickerDialog;
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

import ibridotechnologies.com.accountsoftware.Model.PaymentToUpdate;
import ibridotechnologies.com.accountsoftware.Model.UpdatePaymentDetails;

public class EditCashReceived extends AppCompatActivity {

    EditText editText1,editText2;
    Button btnModifyCash;
    AlertDialog.Builder builder;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cash_received);

        editText1 = (EditText)findViewById(R.id.editTextAmountModify);
        editText2 = (EditText)findViewById(R.id.editTextDateModify);

        editText1.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);

        PaymentToUpdate p = new PaymentToUpdate();
        editText1.setText(p.getPayment_Amount());
        String[] dateData = p.getPayment_ReceivedDate().split("T");
        editText2.setText(dateData[0]);
        editText2.setEnabled(false);

        builder = new AlertDialog.Builder(this);

        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int startYear = calendar.get(Calendar.YEAR);
                int startMonth = calendar.get(Calendar.MONTH);
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditCashReceived.this, new DatePickerDialog.OnDateSetListener() {
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

        btnModifyCash = (Button)findViewById(R.id.btnModifyCash);
        btnModifyCash.setBackgroundResource(R.drawable.button_rounded_border);
        btnModifyCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentToUpdate p = new PaymentToUpdate();

                String paymentId = p.getPayment_Id();
                String paymentModeId = p.getPaymentMode_Id();
                String paymentAmount = editText1.getText().toString().trim();
                String paymentReceivedDate = editText2.getText().toString().trim();
                int bankId = 0000;
                String chequeNo = "0000";
                String chequeDate = "01-01-2190";
                updatePaymentDetailsData(paymentId,paymentModeId,paymentAmount,paymentReceivedDate,bankId,chequeNo,chequeDate);
            }
        });
    }

    protected void updatePaymentDetailsData(String paymentId, String paymentModeId, String paymentAmount, String paymentReceivedDate, int bankId, String chequeNo, String chequeDate){
        try {
            String savedBy = "Self";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://www.acmecreations.co.in/api/AccountManagement/UpdatePaymentAgaintBookOrder";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Payment_Id", paymentId); //Add the data you'd like to send to the server.
            jsonBody.put("PaymentMode_Id", paymentModeId);
            jsonBody.put("Payment_Amount", paymentAmount);
            jsonBody.put("Bank_Id", "0000");
            jsonBody.put("ChequeNo", "0000");
            jsonBody.put("ChequeDate", "01-01-2190");
            jsonBody.put("LastMod_By", savedBy);
            final String requestBody = jsonBody.toString();
            Log.d("JSON Data to Server :",requestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Response JSON :",response);
                    if (response.equals("true")) {
                        builder.setTitle("Payment update status");
                        builder.setMessage("Updated successfully.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(EditCashReceived.this,OptionsActivity.class));
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else if(response.equals("false")) {
                        builder.setTitle("Payment update status");
                        builder.setMessage("Not updated.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(EditCashReceived.this,OptionsActivity.class));
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else {
                        builder.setTitle("Payment update status");
                        builder.setMessage("Something went wrong.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(EditCashReceived.this,OptionsActivity.class));
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
