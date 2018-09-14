package ibridotechnologies.com.accountsoftware;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ibridotechnologies.com.accountsoftware.Model.PaymentParams;

public class Payment extends AppCompatActivity {

    Button btnCash,btnCheque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btnCash = (Button)findViewById(R.id.btnCashReceived);
        btnCheque = (Button)findViewById(R.id.btnChequeReceived);

        btnCash.setBackgroundResource(R.drawable.button_rounded_border);
        btnCheque.setBackgroundResource(R.drawable.button_rounded_border);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");

        btnCash.setTypeface(font);
        btnCash.setText("\uf156 Cash");

        btnCheque.setTypeface(font);
        btnCheque.setText("\uf0d6 Cheque");

        btnCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentParams paymentParams = new PaymentParams();
                paymentParams.setPaymentModeId(1);
                Intent intent = new Intent(Payment.this,CashReceived.class);
                startActivity(intent);
            }
        });

        btnCheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentParams paymentParams = new PaymentParams();
                paymentParams.setPaymentModeId(2);
                Intent intent = new Intent(Payment.this,ChequeReceived.class);
                startActivity(intent);
            }
        });
    }
}
