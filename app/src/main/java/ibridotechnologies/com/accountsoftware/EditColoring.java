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

import ibridotechnologies.com.accountsoftware.Model.EditOrderParams;
import ibridotechnologies.com.accountsoftware.Model.OrderToUpdate;

public class EditColoring extends AppCompatActivity {

    EditText editText1,editText2;
    Button btnModify;
    TextView txtUpdateFontColoring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_coloring);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtUpdateFontColoring = (TextView)findViewById(R.id.txtUpdateFontColoring);
        txtUpdateFontColoring.setTypeface(font);
        txtUpdateFontColoring.setText("\uf1fc");

        editText1 = (EditText)findViewById(R.id.editColoring);
        editText2 = (EditText)findViewById(R.id.editColoringRate);

        editText1.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);

        OrderToUpdate orderToUpdate = new OrderToUpdate();
        editText1.setText(orderToUpdate.getColoring());
        editText2.setText(orderToUpdate.getColoringRate());

        btnModify = (Button)findViewById(R.id.btnModifyColoring);
        btnModify.setBackgroundResource(R.drawable.button_rounded_border);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String coloring = editText1.getText().toString().trim();
                String coloringRate = editText2.getText().toString().trim();

                if(!coloring.isEmpty() && !coloringRate.isEmpty()) {
                    OrderToUpdate eop = new OrderToUpdate();
                    eop.setColoring(coloring);
                    eop.setColoringRate(coloringRate);
                    Intent intent = new Intent(EditColoring.this, EditWriting.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(EditColoring.this,"Please enter value for coloring and price, before update.",Toast.LENGTH_SHORT).show();
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
