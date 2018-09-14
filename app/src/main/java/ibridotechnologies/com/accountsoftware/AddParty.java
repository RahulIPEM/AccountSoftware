package ibridotechnologies.com.accountsoftware;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
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

import ibridotechnologies.com.accountsoftware.Model.NewParty;
import ibridotechnologies.com.accountsoftware.getJson.spinnerstate.JSONDownloaderSP;

public class AddParty extends AppCompatActivity {

    String spJsonURL ="http://www.acmecreations.co.in/api/AcmeQuotation/GetStateList";
    TextView txtView,txtFontAwesome,txtPartyName,txtPartyAddress,txtPartyMobile,txtPartyEmail,txtPartyTelephone;
    Spinner spinnerState,spinnerDistrict;
    Button btnAddParty;
    ProgressDialog pg;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_party);

        txtView = (TextView)findViewById(R.id.txtView);
        txtView.setPaintFlags(txtView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        txtPartyName = (TextView)findViewById(R.id.txt_party_name);
        txtPartyAddress = (TextView)findViewById(R.id.txt_party_address);
        txtPartyMobile = (TextView)findViewById(R.id.txt_party_mobile);
        txtPartyEmail = (TextView)findViewById(R.id.txt_party_email);
        txtPartyTelephone = (TextView)findViewById(R.id.txt_party_telephone);
        btnAddParty = (Button)findViewById(R.id.btn_add_party);

        txtPartyName.setBackgroundResource(R.drawable.edit_text_rounded_border);
        txtPartyAddress.setBackgroundResource(R.drawable.edit_text_rounded_border);
        txtPartyMobile.setBackgroundResource(R.drawable.edit_text_rounded_border);
        txtPartyEmail.setBackgroundResource(R.drawable.edit_text_rounded_border);
        txtPartyTelephone.setBackgroundResource(R.drawable.edit_text_rounded_border);
        btnAddParty.setBackgroundResource(R.drawable.button_rounded_border);

        txtFontAwesome = (TextView)findViewById(R.id.textFontIcon);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontAwesome.setTypeface(font);
        txtFontAwesome.setText("\uf234");

        spinnerState = (Spinner)findViewById(R.id.spinner_state);
        spinnerDistrict = (Spinner)findViewById(R.id.spinner_district);

        spinnerState.setBackgroundResource(R.drawable.spinner_rounded_border);
        spinnerDistrict.setBackgroundResource(R.drawable.spinner_rounded_border);

        pg = new ProgressDialog(AddParty.this);
        pg.setTitle("States");
        pg.setMessage("Loading... Please wait.");
        pg.show();

        new JSONDownloaderSP(AddParty.this,spJsonURL,spinnerState,spinnerDistrict,pg).execute();

        builder = new AlertDialog.Builder(AddParty.this);

        btnAddParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewParty params = new NewParty();
                String partyName = txtPartyName.getText().toString();
                String partyAddress = txtPartyAddress.getText().toString();
                String stateId = String.valueOf(params.getState_id());
                String districtId = String.valueOf(params.getDistrict_id());
                String partyMobile = txtPartyMobile.getText().toString();
                String partyEmail = txtPartyEmail.getText().toString();
                String partyTelephone = txtPartyTelephone.getText().toString();

                if(!partyName.isEmpty() && !partyAddress.isEmpty() && !partyMobile.isEmpty() && !partyEmail.isEmpty() && !partyTelephone.isEmpty() && Integer.parseInt(stateId) > 0 && Integer.parseInt(districtId) > 0) {
                    addNewPartyWithCheck(partyName, partyAddress, stateId, districtId, partyMobile, partyEmail, partyTelephone);
                }
                else {
                    Toast.makeText(AddParty.this,"Please fill above fields.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void addNewPartyWithCheck(String partyName, String partyAddress, String partyStateId, String partyDistrictId, String partyMobile, String partyEmail, String partyTelephone){
        try {
            String savedBy = "Self";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://www.acmecreations.co.in/api/AcmeQuotation/AddPartyDetailsWithCheck";

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Party_Name", partyName); //Add the data you'd like to send to the server.
            jsonBody.put("Party_Address", partyAddress);
            jsonBody.put("Party_StateId", partyStateId);
            jsonBody.put("Party_DistrictId", partyDistrictId);
            jsonBody.put("Party_Mob", partyMobile);
            jsonBody.put("Party_Email", partyEmail);
            jsonBody.put("Party_TelNo", partyTelephone);
            jsonBody.put("Saved_By", "self");
            final String requestBody = jsonBody.toString();
            Log.d("JSON Data to Server :", requestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Add Party Response : ",response);
                    if(Integer.parseInt(response) > 0) {
                        builder.setTitle("Status");
                        builder.setMessage("Party added successfully.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                txtPartyName.setText("");
                                txtPartyAddress.setText("");
                                txtPartyMobile.setText("");
                                txtPartyEmail.setText("");
                                txtPartyTelephone.setText("");
                                spinnerState.setSelection(0);
                                spinnerDistrict.setSelection(0);
                                startActivity(new Intent(AddParty.this,OptionsActivity.class));
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else if(response.equals("-2")) {
                        builder.setTitle("Status");
                        builder.setMessage("Party already exist.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(AddParty.this,OptionsActivity.class));
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else if(response.equals("-1")) {
                        builder.setTitle("Status");
                        builder.setMessage("Something went wrong. If, this problem persist. Please contact to the support team.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(AddParty.this,OptionsActivity.class));
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else {
                        builder.setTitle("Status");
                        builder.setMessage("Something went wrong. Party not added. Please, try again.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(AddParty.this,OptionsActivity.class));
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
