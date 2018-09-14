package ibridotechnologies.com.accountsoftware;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ibridotechnologies.com.accountsoftware.Model.*;

public class Confirmation extends AppCompatActivity {

    TextView txtFontConfirm,txtBookName,txtOrderDate,txtFinancialYear;
    Button btnSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        txtFontConfirm = (TextView)findViewById(R.id.txtFontConfirm);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontConfirm.setTypeface(font);
        txtFontConfirm.setText("\uf059");

        txtBookName = (TextView)findViewById(R.id.txtBookName);
        txtOrderDate = (TextView)findViewById(R.id.txtOrderDate);
        txtFinancialYear = (TextView)findViewById(R.id.txtFinancialYear);

        getOrderDataById();

        btnSure = (Button)findViewById(R.id.btnSure);
        btnSure.setBackgroundResource(R.drawable.button_rounded_border);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Confirmation.this,EditPages.class);
                startActivity(i);
            }
        });
    }

    protected void getOrderDataById(){
        int id = new ibridotechnologies.com.accountsoftware.Model.Order().getOrderId();
        final String url = "http://www.acmecreations.co.in/api/AccountManagement/GetOrderDetailsByOrderId/"+id;
        Log.d("Order URL :",url);
        RequestQueue queue = Volley.newRequestQueue(Confirmation.this);

// prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, (String) null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        OrderToUpdate toUpdate = new OrderToUpdate();
                        Log.d("Order Response :", response.toString());
                        try{
                            // Loop through the array elements
                            Log.d("length :",String.valueOf(response.length()));
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                Log.d("In :",response.getJSONObject(i).toString());
                                JSONObject order = response.getJSONObject(i);

                                // Get the current order (json object) data
                                txtBookName.setText(order.getString("Book_Title"));
                                txtOrderDate.setText(order.getString("OrderReceivedDate"));
                                txtFinancialYear.setText(order.getString("FinancialYear"));
                            }
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
}
