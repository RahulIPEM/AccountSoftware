package ibridotechnologies.com.accountsoftware;

import android.app.VoiceInteractor;
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

import ibridotechnologies.com.accountsoftware.Model.EditOrderParams;
import ibridotechnologies.com.accountsoftware.Model.OrderToUpdate;

public class EditProofing extends AppCompatActivity {

    EditText editText1,editText2;
    Button btnSave;
    TextView txtFontProofing;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_proofing);

        editText1 = (EditText)findViewById(R.id.proofing);
        editText2 = (EditText)findViewById(R.id.ProofingRate);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontProofing = (TextView)findViewById(R.id.txtFontProofing);
        txtFontProofing.setTypeface(font);
        txtFontProofing.setText("\uf1c4");

        editText1.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);

        OrderToUpdate orderToUpdate = new OrderToUpdate();
        editText1.setText(orderToUpdate.getProofingPage());
        editText2.setText(orderToUpdate.getProofingAmount());

        builder = new AlertDialog.Builder(EditProofing.this);

        btnSave = (Button)findViewById(R.id.btnProofing);
        btnSave.setBackgroundResource(R.drawable.button_rounded_border);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Writing.this,"Flow Ends Here.",Toast.LENGTH_SHORT).show();

                String proofingVal = editText1.getText().toString().trim();
                String proofingRateVal = editText2.getText().toString().trim();

                if(!proofingVal.isEmpty() && !proofingRateVal.isEmpty()) {
                    OrderToUpdate eop = new OrderToUpdate();
                    eop.setProofingPage(proofingVal);
                    eop.setProofingAmount(proofingRateVal);

                    int partyId = Integer.parseInt(eop.getParty_Id());
                    int bookId = Integer.parseInt(eop.getBook_Id());
                    int orderId = Integer.parseInt(eop.getOrder_Id());
                    String orderReceivedDate = eop.getOrderReceivedDate();
                    String pages = eop.getPagesNo();
                    String pagesRate = eop.getRate();
                    String artWork = eop.getArtwork();
                    String artWorkRate = eop.getArtworkRate();
                    String coloring = eop.getColoring();
                    String coloringRate = eop.getColoringRate();
                    String writingVal = eop.getWriting();
                    String writingRateVal = eop.getWritingRate();
                    String proofing = eop.getProofingPage();
                    String proofingRate = eop.getProofingAmount();
                    String financialYear = eop.getFinancialYear();
                    updateBookOrder(partyId,bookId,orderId,orderReceivedDate,pages,pagesRate,artWork,artWorkRate,coloring,coloringRate,writingVal,writingRateVal,proofing,proofingRate,financialYear);
                }
                else{
                    Toast.makeText(EditProofing.this,"Please enter value for Proofing and Amount, before proceeding further.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void updateBookOrder(int partyId, int bookId, int orderId, String orderReceivedDate, String pagesNo, String pagesRate, String artWork, String artWorkRate, String coloring, String coloringRate, String writing, String writingRate, String proofing, String proofingRate, String financialYear){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://www.acmecreations.co.in/api/AccountManagement/UpdateBooksOrder";
            JSONObject params = new JSONObject();
            params.put("Party_Id", partyId); //Add the data you'd like to send to the server.
            params.put("Book_Id", bookId);
            params.put("OrderReceivedDate", orderReceivedDate);
            params.put("PagesNo", pagesNo);
            params.put("Rate", pagesRate);
            params.put("ArtWork", artWork);
            params.put("ArtWorkRate", artWorkRate);
            params.put("Coloring", coloring);
            params.put("ColoringRate", coloringRate);
            params.put("Writing", writing);
            params.put("WritingRate", writingRate);
            params.put("LastMod_By", "self");
            params.put("Order_id", orderId);
            params.put("ProofingPage", proofing);
            params.put("ProofingAmount", proofingRate);
            params.put("FinancialYear", financialYear);
            final String requestParams = params.toString();
            Log.d("JSON to Server :",requestParams);

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Response JSON :", response.toString());
                    if(response.equals("true")) {
                        builder.setTitle("Update Status");
                        builder.setMessage("Books order updated successfully.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(EditProofing.this,OptionsActivity.class));
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else {
                        builder.setTitle("Update Status");
                        builder.setMessage("Books order not updated successfully.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(EditProofing.this,OptionsActivity.class));
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
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestParams == null ? null : requestParams.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestParams, "utf-8");
                        return null;
                    }
                }
            };

            requestQueue.add(stringRequest);
        }
        catch (JSONException e) {
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
