package ibridotechnologies.com.accountsoftware;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LedgerOptions extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4,btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger_options);

        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");

        btn1.setTypeface(font);
        btn1.setText("\uf022 Bills");

        btn2.setTypeface(font);
        btn2.setText("\uf02d All Book Bill Details");

        btn3.setTypeface(font);
        btn3.setText("\uf156 All Payment Details");

        btn4.setTypeface(font);
        btn4.setText("\uf24e Balance");

        btn5.setTypeface(font);
        btn5.setText("\uf295 Discount");

        btn1.setBackgroundResource(R.drawable.button_rounded_border);
        btn2.setBackgroundResource(R.drawable.button_rounded_border);
        btn3.setBackgroundResource(R.drawable.button_rounded_border);
        btn4.setBackgroundResource(R.drawable.button_rounded_border);
        btn5.setBackgroundResource(R.drawable.button_rounded_border);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LedgerOptions.this,GetBook.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LedgerOptions.this,BookReportParameter.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LedgerOptions.this,PaymentReportParameter.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LedgerOptions.this,BalanceReportParameter.class);
                startActivity(intent);
            }
        });
    }
}
