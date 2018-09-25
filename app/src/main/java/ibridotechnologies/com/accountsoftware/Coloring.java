package ibridotechnologies.com.accountsoftware;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ibridotechnologies.com.accountsoftware.Model.Order;

public class Coloring extends AppCompatActivity {

    EditText editText1,editText2;
    TextView txtFontColoring;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coloring);

        editText1 = (EditText)findViewById(R.id.Coloring);
        editText2 = (EditText)findViewById(R.id.ColoringRate);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontColoring = (TextView)findViewById(R.id.txtFontColoring);
        txtFontColoring.setTypeface(font);
        txtFontColoring.setText("\uf1fc");

        editText1.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);

        editText1.setFocusable(true);

        btnSave = (Button)findViewById(R.id.btnColoring);
        btnSave.setBackgroundResource(R.drawable.button_rounded_border);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                order.setColoring(editText1.getText().toString());
                order.setColoringRate(editText2.getText().toString());

                String coloring = editText1.getText().toString().trim();
                String cologingRate = editText2.getText().toString().trim();

                if(!coloring.isEmpty() && !cologingRate.isEmpty()) {
                    Intent intent = new Intent(Coloring.this, Writing.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Coloring.this,"Please enter value for Coloring and Price, before proceeding further.",Toast.LENGTH_SHORT).show();
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
