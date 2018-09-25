package ibridotechnologies.com.accountsoftware;

import android.content.Intent;
import android.graphics.Typeface;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ibridotechnologies.com.accountsoftware.Model.EditOrderParams;
import ibridotechnologies.com.accountsoftware.Model.OrderToUpdate;

public class EditPages extends AppCompatActivity {

    EditText editText1,editText2;
    Button btnModify;
    TextView txtUpdateFontPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pages);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtUpdateFontPages = (TextView)findViewById(R.id.txtUpdateFontPages);
        txtUpdateFontPages.setTypeface(font);
        txtUpdateFontPages.setText("\uf15b");

        editText1 = (EditText)findViewById(R.id.editTextEditPages);
        editText2 = (EditText)findViewById(R.id.editTextEditPagesRate);

        editText1.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);

        //getPaymentDataById();
        OrderToUpdate orderToUpdate = new OrderToUpdate();
        editText1.setText(orderToUpdate.getPagesNo());
        editText2.setText(orderToUpdate.getRate());

        btnModify = (Button)findViewById(R.id.btnModifyAndSave);
        btnModify.setBackgroundResource(R.drawable.button_rounded_border);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pages = editText1.getText().toString().trim();
                String pagesRate = editText2.getText().toString().trim();

                if(!pages.isEmpty() && ! pagesRate.isEmpty()) {
                    OrderToUpdate eop = new OrderToUpdate();
                    eop.setPagesNo(pages);
                    eop.setRate(pagesRate);
                    Intent intent = new Intent(EditPages.this, EditArtWork.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(EditPages.this,"Please enter above fields.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void getOrderDataById(){
        int id = new ibridotechnologies.com.accountsoftware.Model.Order().getOrderId();
        final String url = "http://www.acmecreations.co.in/api/AccountManagement/GetOrderDetailsByOrderId/"+id;
        Log.d("Order URL :",url);
        RequestQueue queue = Volley.newRequestQueue(EditPages.this);

// prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, (String) null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            // display response
                            OrderToUpdate toUpdate = new OrderToUpdate();
                            Log.d("Order Response :", response.toString());
                            try {
                                // Loop through the array elements
                                Log.d("length :", String.valueOf(response.length()));
                                for (int i = 0; i < response.length(); i++) {
                                    // Get current json object
                                    Log.d("In :", response.getJSONObject(i).toString());
                                    JSONObject order = response.getJSONObject(i);

                                    // Get the current order (json object) data
                                    editText1.setText(order.getString("PagesNo"));
                                    editText2.setText(order.getString("Rate"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(context,toUpdate.getRate(),Toast.LENGTH_SHORT).show();
                        }
                        else {

                        }
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
