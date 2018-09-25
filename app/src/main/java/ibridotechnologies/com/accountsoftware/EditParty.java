package ibridotechnologies.com.accountsoftware;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ibridotechnologies.com.accountsoftware.Model.PartyToUpdate;
import ibridotechnologies.com.accountsoftware.getJson.getParty.JSONDownloaderSP;

public class EditParty extends AppCompatActivity {

    Spinner spinnerPartyName;
    TextView txtFontParty;
    Button btnSave;
    ProgressDialog progressDialog;
    String JsonURL = "http://www.acmecreations.co.in/api/AcmeQuotation/GetPartyList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_party);

        spinnerPartyName = (Spinner)findViewById(R.id.searchableSpinner);
        spinnerPartyName.setBackgroundResource(R.drawable.spinner_rounded_border);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontParty = (TextView)findViewById(R.id.txtFontParty);
        txtFontParty.setTypeface(font);
        txtFontParty.setText("\uf234");

        progressDialog = new ProgressDialog(EditParty.this);
        progressDialog.setTitle("Getting Client List");
        progressDialog.setMessage("Loading... Please wait.");
        progressDialog.show();

        new JSONDownloaderSP(EditParty.this, JsonURL, spinnerPartyName, progressDialog).execute();

        btnSave = (Button) findViewById(R.id.btnGetParty);
        btnSave.setBackgroundResource(R.drawable.button_rounded_border);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PartyToUpdate partyToUpdate = new PartyToUpdate();
                partyToUpdate.setPartyId(String.valueOf(spinnerPartyName.getSelectedItemId()));

                int spinnerIndex = spinnerPartyName.getSelectedItemPosition();
                //Log.d("Spinner Index :",String.valueOf(spinnerIndex));
                if(spinnerIndex > 0) {
                    Intent intent = new Intent(EditParty.this, UpdateParty.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(EditParty.this,"Please select a party before proceeding further.",Toast.LENGTH_SHORT).show();
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
