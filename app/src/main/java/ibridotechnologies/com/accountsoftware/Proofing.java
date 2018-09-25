package ibridotechnologies.com.accountsoftware;

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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import ibridotechnologies.com.accountsoftware.Model.*;

public class Proofing extends AppCompatActivity {

    EditText editText1,editText2;
    Button btnSave;
    TextView txtFontProofing;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proofing);

        editText1 = (EditText)findViewById(R.id.proofing);
        editText2 = (EditText)findViewById(R.id.ProofingRate);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontProofing = (TextView)findViewById(R.id.txtFontProofing);
        txtFontProofing.setTypeface(font);
        txtFontProofing.setText("\uf1c4");

        editText1.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);

        editText1.setFocusable(true);

        builder = new AlertDialog.Builder(Proofing.this);

        btnSave = (Button)findViewById(R.id.btnProofing);
        btnSave.setBackgroundResource(R.drawable.button_rounded_border);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Writing.this,"Flow Ends Here.",Toast.LENGTH_SHORT).show();

                String proofingVal = editText1.getText().toString().trim();
                String proofingRateVal = editText2.getText().toString().trim();

                if(!proofingVal.isEmpty() && !proofingRateVal.isEmpty()) {
                    ibridotechnologies.com.accountsoftware.Model.Order order = new ibridotechnologies.com.accountsoftware.Model.Order();
                    order.setProofing(proofingVal);
                    order.setProofingRate(proofingRateVal);

                    int partyId = order.getPartyId();
                    int bookId = order.getBookId();
                    String orderReceivedDate = order.getOrderReceivedDate();
                    String pagesNo = order.getPageNo();
                    String pagesRate = order.getPagesRate();
                    String artWork = order.getArtWork();
                    String artWorkRate = order.getArtWorkRate();
                    String coloring = order.getColoring();
                    String coloringRate = order.getColoringRate();
                    String writing = order.getWriting();
                    String writingRate = order.getWritingRate();
                    String financialYear = order.getFinancialYear();
                    placeBookOrder(partyId, bookId, orderReceivedDate, pagesNo, pagesRate, artWork, artWorkRate, coloring, coloringRate, writing, writingRate, proofingVal, proofingRateVal, financialYear);
                }
                else{
                    Toast.makeText(Proofing.this,"Please enter value for Writing and Price, before proceeding further.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void placeBookOrder(int partyId, int bookId, String orderReceivedDate, String pagesNo, String pagesRate, String artWork, String artWorkRate, String coloring, String coloringRate, String writing, String writingRate, String proofing, String proofingRate, String financialYear){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://www.acmecreations.co.in/api/AccountManagement/AddBooksOrder";
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
            params.put("ProofingPage", proofing);
            params.put("ProofingAmount", proofingRate);
            params.put("FinancialYear", financialYear);
            params.put("Saved_By", "self");
            final String requestParams = params.toString();
            Log.d("JSON to Server :",requestParams);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Response JSON :", response.toString());
                    if(response.equals("true")) {
                        builder.setTitle("Order Status");
                        builder.setMessage("Book order placed successfully.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(Proofing.this, OptionsActivity.class));
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else {
                        builder.setTitle("Order Status");
                        builder.setMessage("Something went wrong. Please, try again.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(Proofing.this, PartyName.class));
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

//                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                    String responseString = "";
//                    if (response != null) {
//                        responseString = String.valueOf(response.statusCode);
//                        // can get more details such as response.headers
//                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                }
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
