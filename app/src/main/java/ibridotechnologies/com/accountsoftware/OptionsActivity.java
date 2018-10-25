package ibridotechnologies.com.accountsoftware;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btn4);
        btn5=(Button)findViewById(R.id.btn5);
        btn6=(Button)findViewById(R.id.btn6);
        btn7=(Button)findViewById(R.id.btn7);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");

        btn1.setTypeface(font);
        btn1.setText("\uf02d Book Order");

        btn2.setTypeface(font);
        btn2.setText("\uf156 Payment received");

        btn3.setTypeface(font);
        btn3.setText("\uf044 Edit Details");

        btn4.setTypeface(font);
        btn4.setText("\uf07b Ledger");

        btn5.setTypeface(font);
        btn5.setText("\uf08b Quit");

        btn6.setTypeface(font);
        btn6.setText("\uf234 Add New Party");

        btn7.setTypeface(font);
        btn7.setText("\uf02d Add New Book");

        btn1.setBackgroundResource(R.drawable.button_rounded_border);
        btn2.setBackgroundResource(R.drawable.button_rounded_border);
        btn3.setBackgroundResource(R.drawable.button_rounded_border);
        btn4.setBackgroundResource(R.drawable.button_rounded_border);
        btn5.setBackgroundResource(R.drawable.button_rounded_border);
        btn6.setBackgroundResource(R.drawable.button_rounded_border);
        btn7.setBackgroundResource(R.drawable.button_rounded_border);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this,PartyName.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this,Order.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this,EditDetails.class);
                startActivity(intent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionsActivity.this,LedgerDetails.class));
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                System.exit(0);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionsActivity.this,PartyOptions.class);
                startActivity(intent);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionsActivity.this,AddBook.class));
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
                Toast.makeText(OptionsActivity.this,"You already at home.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_about:
                startActivity(new Intent(OptionsActivity.this,AboutUs.class));
                break;
            case R.id.navigation_exit:
                finishAffinity();
                System.exit(0);
        }

        return true;
    }
}
