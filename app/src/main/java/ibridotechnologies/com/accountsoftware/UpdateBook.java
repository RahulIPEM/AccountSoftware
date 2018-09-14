package ibridotechnologies.com.accountsoftware;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

import ibridotechnologies.com.accountsoftware.getJson.getBookToUpdate.JSONDownloaderSP;

public class UpdateBook extends AppCompatActivity {

    TextView txtFontBook;
    Spinner spinnerBook;
    EditText bookTitle,authorName,isbnNo;
    Button btnUpdate;
    String JsonURL = "http://www.acmecreations.co.in/api/AccountManagement/GetBookDetails";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        spinnerBook = (Spinner)findViewById(R.id.spinnerBook);
        bookTitle = (EditText)findViewById(R.id.bookTitle);
        authorName = (EditText)findViewById(R.id.authorName);
        isbnNo = (EditText)findViewById(R.id.isbnNo);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontBook = (TextView)findViewById(R.id.txtFontBook);
        txtFontBook.setTypeface(font);
        txtFontBook.setText("\uf02d");

        spinnerBook.setBackgroundResource(R.drawable.spinner_rounded_border);
        bookTitle.setBackgroundResource(R.drawable.edit_text_rounded_border);
        authorName.setBackgroundResource(R.drawable.edit_text_rounded_border);
        isbnNo.setBackgroundResource(R.drawable.edit_text_rounded_border);
        btnUpdate.setBackgroundResource(R.drawable.button_rounded_border);

        builder = new AlertDialog.Builder(UpdateBook.this);

        new JSONDownloaderSP(UpdateBook.this,JsonURL,spinnerBook,bookTitle,authorName,isbnNo).execute();
    }

    protected void updateBookDetails(int bookId, String bookTitle, String bookAuthor, String isbnNo){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://www.acmecreations.co.in/api/AccountManagement/UpdateBookDetails";
            JSONObject params = new JSONObject();
            params.put("Book_Id", bookId); //Add the data you'd like to send to the server.
            params.put("Book_Title", bookTitle);
            params.put("Book_Author", bookAuthor);
            params.put("Book_ISBN_No", isbnNo);
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
                                startActivity(new Intent(UpdateBook.this,OptionsActivity.class));
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
                                startActivity(new Intent(UpdateBook.this,OptionsActivity.class));
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
