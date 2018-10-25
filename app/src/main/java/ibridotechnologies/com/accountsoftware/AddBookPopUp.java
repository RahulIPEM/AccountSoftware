package ibridotechnologies.com.accountsoftware;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

public class AddBookPopUp extends AppCompatActivity {

    EditText editText1,editText2,editText3;
    Button btnAdd,btnUpdate;
    AlertDialog.Builder builder;
    TextView txtFontBook;

    String URL = "http://www.acmecreations.co.in/api/AccountManagement/AddBookDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_pop_up);
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.7));

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontBook = (TextView)findViewById(R.id.txtFontBook);
        txtFontBook.setTypeface(font);
        txtFontBook.setText("\uf02d");

        editText1 = (EditText)findViewById(R.id.bookTitle);
        editText2 = (EditText)findViewById(R.id.authorName);
        editText3 = (EditText)findViewById(R.id.isbnNo);

        editText1.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText3.setBackgroundResource(R.drawable.edit_text_rounded_border);

        btnAdd = (Button)findViewById(R.id.btnAddBook);
        btnAdd.setBackgroundResource(R.drawable.button_rounded_border);
        builder = new AlertDialog.Builder(AddBookPopUp.this);

        btnAdd.setBackgroundResource(R.drawable.button_rounded_border);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText1.getText().toString().isEmpty() && !editText2.getText().toString().isEmpty() && !editText3.getText().toString().isEmpty()) {
                    //addNewBook(editText1.getText().toString(), editText2.getText().toString(), editText3.getText().toString());
                    addNewBookWithCheck(editText1.getText().toString(), editText2.getText().toString(), editText3.getText().toString());
                }
                else{
                    Toast.makeText(AddBookPopUp.this,"Please fill up above fields.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate = (Button)findViewById(R.id.btnGoToUpdate);
        btnUpdate.setBackgroundResource(R.drawable.button_rounded_border);

        btnUpdate.setBackgroundResource(R.drawable.button_rounded_border);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddBookPopUp.this,UpdateBook.class));
            }
        });
    }

    protected void addNewBookWithCheck(String bookTitle, String authorName, String isbnNo) {
        try {
            String savedBy = "Self";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://www.acmecreations.co.in/api/AccountManagement/AddBookDetailsWithCheck";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Book_Title", bookTitle); //Add the data you'd like to send to the server.
            jsonBody.put("Book_Author", authorName);
            jsonBody.put("Book_ISBN_No", isbnNo);
            jsonBody.put("Saved_By", savedBy);
            final String requestBody = jsonBody.toString();
            Log.d("JSON Data to Server :",requestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Response JSON :",response.toString());
                    if(Integer.parseInt(response) > 0) {
                        builder.setTitle("Status");
                        builder.setMessage("Book added successfully.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                editText1.setText("");
                                editText2.setText("");
                                editText3.setText("");
                                startActivity(new Intent(AddBookPopUp.this,OptionsActivity.class));
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else if(response.equals("-2")) {
                        builder.setTitle("Status");
                        builder.setMessage("Book already exist.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(AddBookPopUp.this,OptionsActivity.class));
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
                                startActivity(new Intent(AddBookPopUp.this,OptionsActivity.class));
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else {
                        builder.setTitle("Status");
                        builder.setMessage("Something went wrong. Book not added. Please, try again.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(AddBookPopUp.this,OptionsActivity.class));
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
