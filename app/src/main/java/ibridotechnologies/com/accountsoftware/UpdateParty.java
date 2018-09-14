package ibridotechnologies.com.accountsoftware;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import ibridotechnologies.com.accountsoftware.Model.PartyToUpdate;
import ibridotechnologies.com.accountsoftware.getJson.spinnerstate.JSONDownloaderSP;

public class UpdateParty extends AppCompatActivity {

    String spJsonURL ="http://www.acmecreations.co.in/api/AcmeQuotation/GetStateList";
    TextView txtPartyName,txtPartyAddress,txtPartyMobile,txtPartyEmail,txtPartyTelephone;
    Spinner spinnerState,spinnerDistrict;
    Button btnUpdateParty;
    ProgressDialog pg;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_party);

        final PartyToUpdate partyToUpdate = new PartyToUpdate();
        String id = partyToUpdate.getPartyId();

        txtPartyName = (TextView)findViewById(R.id.txt_party_name);
        txtPartyAddress =(TextView)findViewById(R.id.txt_party_address);
        txtPartyMobile = (TextView)findViewById(R.id.txt_party_mobile);
        txtPartyEmail = (TextView)findViewById(R.id.txt_party_email);
        txtPartyTelephone = (TextView)findViewById(R.id.txt_party_telephone);

        txtPartyName.setBackgroundResource(R.drawable.edit_text_rounded_border);
        txtPartyAddress.setBackgroundResource(R.drawable.edit_text_rounded_border);
        txtPartyMobile.setBackgroundResource(R.drawable.edit_text_rounded_border);
        txtPartyEmail.setBackgroundResource(R.drawable.edit_text_rounded_border);
        txtPartyTelephone.setBackgroundResource(R.drawable.edit_text_rounded_border);

        spinnerState = (Spinner)findViewById(R.id.spinner_state);
        spinnerDistrict = (Spinner)findViewById(R.id.spinner_district);

        spinnerState.setBackgroundResource(R.drawable.spinner_rounded_border);
        spinnerDistrict.setBackgroundResource(R.drawable.spinner_rounded_border);

        pg = new ProgressDialog(UpdateParty.this);
        pg.setTitle("States");
        pg.setMessage("Loading... Please wait.");
        pg.show();

        new JSONDownloaderSP(UpdateParty.this,spJsonURL,spinnerState,spinnerDistrict,pg).execute();

        builder = new AlertDialog.Builder(UpdateParty.this);

        getPartyDetailsByPartyId(id);

        btnUpdateParty = (Button)findViewById(R.id.btn_update_party);
        btnUpdateParty.setBackgroundResource(R.drawable.button_rounded_border);
        btnUpdateParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=txtPartyName.getText().toString().trim();
                String address=txtPartyAddress.getText().toString().trim();
                String mobile=txtPartyMobile.getText().toString().trim();
                String email=txtPartyEmail.getText().toString().trim();
                String telephone=txtPartyTelephone.getText().toString().trim();
                long stateId= spinnerState.getSelectedItemId();
                long districtId= spinnerDistrict.getSelectedItemId();
                if(!name.isEmpty() && !address.isEmpty() && !mobile.isEmpty() && !email.isEmpty() && !telephone.isEmpty() && stateId > 0 && districtId > 0) {
                    updatePartyDetails(Integer.parseInt(partyToUpdate.getPartyId()),name,address,String.valueOf(stateId),String.valueOf(districtId),mobile,email,telephone);
                }
                else {
                    Toast.makeText(UpdateParty.this,"Please fill above fields carefully.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void getPartyDetailsByPartyId(String id){
        final String url = "http://www.acmecreations.co.in/api/AccountManagement/GetPartyDetailsByPartyId/"+id;
        Log.d("Order URL :",url);
        RequestQueue queue = Volley.newRequestQueue(UpdateParty.this);

        //prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, (String) null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        PartyToUpdate partyToUpdate = new PartyToUpdate();
                        Log.d("Party Data :", response.toString());
                        try{
                            // Loop through the array elements
                            Log.d("length :",String.valueOf(response.length()));

                                Log.d("In :",response.getJSONObject(0).toString());
                                JSONObject party = response.getJSONObject(0);

                                // Get the current order (json object) data
                                txtPartyName.setText(party.getString("Party_Name"));
                                txtPartyAddress.setText(party.getString("Party_Address"));
                                spinnerState.setSelection(Integer.parseInt(party.getString("Party_StateId")));
                                spinnerDistrict.setSelection(Integer.parseInt(party.getString("Party_DistrictId")));
                                txtPartyMobile.setText(party.getString("Party_Mob"));
                            txtPartyEmail.setText(party.getString("Party_Email"));
                            txtPartyTelephone.setText(party.getString("Party_TelNo"));

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        //Toast.makeText(context,toUpdate.getRate(),Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Order Error :", error.toString());
                    }
                }
        );

        // add it to the RequestQueue
        queue.add(getRequest);
    }


    protected void updatePartyDetails(int partyId, String partyName, String partyAddress, String partyStateId, String partyDistrictId, String partyMobile, String partyEmail, String partyTelephone){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://www.acmecreations.co.in/api/AccountManagement/UpdatePartyDetails";
            JSONObject params = new JSONObject();
            params.put("Party_Id", partyId); //Add the data you'd like to send to the server.
            params.put("Party_Name", partyName);
            params.put("Party_Address", partyAddress);
            params.put("Party_StateId", partyStateId);
            params.put("Party_DistrictId", partyDistrictId);
            params.put("Party_Mob", partyMobile);
            params.put("Party_Email", partyEmail);
            params.put("Party_TelNo", partyTelephone);
            params.put("LastMod_By", "self");

            final String requestParams = params.toString();
            Log.d("JSON to Server :",requestParams);

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Response JSON :", response.toString());
                    if(response.equals("true")) {
                        builder.setTitle("Update Status");
                        builder.setMessage("Party updated successfully.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(UpdateParty.this,OptionsActivity.class));
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else {
                        builder.setTitle("Update Status");
                        builder.setMessage("Party not updated successfully.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(UpdateParty.this,OptionsActivity.class));
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
}
