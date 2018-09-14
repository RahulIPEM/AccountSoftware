package ibridotechnologies.com.accountsoftware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import ibridotechnologies.com.accountsoftware.Model.Ledger;

public class GetBook extends AppCompatActivity {

    Spinner spBookName;
    String JsonBooks = "http://www.acmecreations.co.in/api/AccountManagement/GetBookDetails";
    Button btnGetDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_book);

        spBookName = (Spinner)findViewById(R.id.spBookName);
        spBookName.setBackgroundResource(R.drawable.spinner_rounded_border);
        new ibridotechnologies.com.accountsoftware.getJson.getBooks.JSONDownloaderSP(GetBook.this,JsonBooks,spBookName).execute();

        btnGetDetails = (Button)findViewById(R.id.btnGetDetails);
        btnGetDetails.setBackgroundResource(R.drawable.button_rounded_border);
        btnGetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Params params = new Params();

                Ledger ledger = new Ledger();
                ledger.setBookId(String.valueOf(params.getBookId()));

                startActivity(new Intent(GetBook.this,Bills.class));
            }
        });
    }
}
