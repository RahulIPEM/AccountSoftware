package ibridotechnologies.com.accountsoftware;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class EditDetails extends AppCompatActivity {

    Button button1,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        button1 = (Button)findViewById(R.id.btnModifyBook);
        button2 = (Button)findViewById(R.id.btnPaymentModify);

        button1.setBackgroundResource(R.drawable.button_rounded_border);
        button2.setBackgroundResource(R.drawable.button_rounded_border);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");

        button1.setTypeface(font);
        button1.setText("\uf02d Update Books Order");

        button2.setTypeface(font);
        button2.setText("\uf0d6 Update Payment Details");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditDetails.this,SelectBook.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditDetails.this,EditOrder.class);
                startActivity(intent);
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
