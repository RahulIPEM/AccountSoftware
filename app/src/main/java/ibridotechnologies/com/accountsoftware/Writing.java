package ibridotechnologies.com.accountsoftware;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import ibridotechnologies.com.accountsoftware.Model.Order;

public class Writing extends AppCompatActivity {

    EditText editText1,editText2;
    Button btnSave;
    TextView txtFontWriting;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        editText1 = (EditText)findViewById(R.id.Writing);
        editText2 = (EditText)findViewById(R.id.WritingRate);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontWriting = (TextView)findViewById(R.id.txtFontWriting);
        txtFontWriting.setTypeface(font);
        txtFontWriting.setText("\uf040");

        editText1.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);

        editText1.setFocusable(true);

        builder = new AlertDialog.Builder(Writing.this);

        btnSave = (Button)findViewById(R.id.btnWriting);
        btnSave.setBackgroundResource(R.drawable.button_rounded_border);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Writing.this,"Flow Ends Here.",Toast.LENGTH_SHORT).show();

                String writingVal = editText1.getText().toString().trim();
                String writingRateVal = editText2.getText().toString().trim();

                if(!writingVal.isEmpty() && !writingRateVal.isEmpty()) {
                    Order order = new Order();
                    order.setWriting(writingVal);
                    order.setWritingRate(writingRateVal);
                    startActivity(new Intent(Writing.this,Proofing.class));
                }
                else{
                    Toast.makeText(Writing.this,"Please enter value for Writing and Price, before proceeding further.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
