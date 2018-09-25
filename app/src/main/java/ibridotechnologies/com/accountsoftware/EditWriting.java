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

public class EditWriting extends AppCompatActivity {

    EditText editText1,editText2;
    TextView txtUpdateFontWriting;
    Button btnModify;
    AlertDialog.Builder builder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_writing);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtUpdateFontWriting = (TextView)findViewById(R.id.txtUpdateFontWriting);
        txtUpdateFontWriting.setTypeface(font);
        txtUpdateFontWriting.setText("\uf040");

        editText1 = (EditText)findViewById(R.id.editWriting);
        editText2 = (EditText)findViewById(R.id.editWritingRate);

        editText1.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);

        OrderToUpdate orderToUpdate = new OrderToUpdate();
        editText1.setText(orderToUpdate.getWriting());
        editText2.setText(orderToUpdate.getWritingRate());

        builder = new AlertDialog.Builder(this);

        btnModify = (Button)findViewById(R.id.btnModifyWriting);
        btnModify.setBackgroundResource(R.drawable.button_rounded_border);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(EditWriting.this,EditWriting.class);
//                startActivity(intent);

                String writing = editText1.getText().toString().trim();
                String writingRate = editText2.getText().toString().trim();

                if(!writing.isEmpty() && !writing.isEmpty()) {
                    OrderToUpdate eop = new OrderToUpdate();
                    eop.setWriting(writing);
                    eop.setWritingRate(writingRate);
                    startActivity(new Intent(EditWriting.this,EditProofing.class));
                }
                else{
                    Toast.makeText(EditWriting.this,"Please enter value for writing and price, before update.",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
