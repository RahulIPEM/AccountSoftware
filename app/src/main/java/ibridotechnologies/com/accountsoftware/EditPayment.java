package ibridotechnologies.com.accountsoftware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ibridotechnologies.com.accountsoftware.Model.UpdatePaymentDetails;

public class EditPayment extends AppCompatActivity {

    Button btnCash,btnCheque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_payment);

        btnCash = (Button)findViewById(R.id.btnEditCashReceived);
        btnCheque = (Button)findViewById(R.id.btnEditChequeReceived);

        btnCash.setBackgroundResource(R.drawable.button_rounded_border);
        btnCheque.setBackgroundResource(R.drawable.button_rounded_border);

        btnCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdatePaymentDetails updatePaymentDetails = new UpdatePaymentDetails();
                updatePaymentDetails.setPaymentModeId(1);
                Intent intent = new Intent(EditPayment.this,EditCashReceived.class);
                startActivity(intent);
            }
        });

        btnCheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdatePaymentDetails updatePaymentDetails = new UpdatePaymentDetails();
                updatePaymentDetails.setPaymentModeId(2);
                Intent intent = new Intent(EditPayment.this,EditChequeReceived.class);
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
