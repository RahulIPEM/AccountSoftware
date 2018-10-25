package ibridotechnologies.com.accountsoftware;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ibridotechnologies.com.accountsoftware.Model.Order;
import ibridotechnologies.com.accountsoftware.getJson.getBooks.JSONDownloaderSP;

public class BookName extends AppCompatActivity {

    Spinner spinnerBook;
    EditText editText2;
    TextView txtFontBook;
    Button btnSave,btnAddBooks;
    String JsonURL = "http://www.acmecreations.co.in/api/AccountManagement/GetBookDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_name);

        spinnerBook=(Spinner) findViewById(R.id.spinnerBook);
        editText2=(EditText)findViewById(R.id.editText2);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontBook = (TextView)findViewById(R.id.txtFontBook);
        txtFontBook.setTypeface(font);
        txtFontBook.setText("\uf02d");

        spinnerBook.setBackgroundResource(R.drawable.spinner_rounded_border);
        new JSONDownloaderSP(BookName.this,JsonURL,spinnerBook).execute();

        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);

        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int startYear = calendar.get(Calendar.YEAR);
                int startMonth = calendar.get(Calendar.MONTH);
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookName.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        String date=selectedyear+"-"+(selectedmonth+1)+"-"+selectedday;
                        editText2.setText(date);
                    }
                },startYear,startMonth,startDay);
                datePickerDialog.setTitle("Select Order Date");
                datePickerDialog.show();
            }
        });

        btnAddBooks = (Button)findViewById(R.id.btnAddBooks);
        btnAddBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookName.this,AddBookPopUp.class));
            }
        });


        btnSave = (Button)findViewById(R.id.btnSaveBook);
        btnSave.setBackgroundResource(R.drawable.button_rounded_border);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                order.setBookId(new Params().getBookId());
                order.setOrderReceivedDate(editText2.getText().toString());

                int spinnerIndex = spinnerBook.getSelectedItemPosition();
                String date = editText2.getText().toString().trim();

                if(spinnerIndex > 0 && !date.isEmpty()) {
                    Intent intent = new Intent(BookName.this, Pages.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(BookName.this,"Please select a book and choose a date before proceeding further.",Toast.LENGTH_SHORT).show();
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
