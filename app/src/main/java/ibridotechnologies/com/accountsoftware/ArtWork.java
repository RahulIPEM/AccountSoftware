package ibridotechnologies.com.accountsoftware;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ibridotechnologies.com.accountsoftware.Model.Order;

public class ArtWork extends AppCompatActivity {

    EditText editText1,editText2;
    TextView txtFontArtWork;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_work);

        editText1 = (EditText)findViewById(R.id.ArtWork);
        editText2 = (EditText)findViewById(R.id.ArtWorkRate);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtFontArtWork = (TextView)findViewById(R.id.txtFontArtWork);
        txtFontArtWork.setTypeface(font);
        txtFontArtWork.setText("\uf2dc");

        editText1.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);

        editText1.setFocusable(true);

        btnSave = (Button)findViewById(R.id.btnArtWork);
        btnSave.setBackgroundResource(R.drawable.button_rounded_border);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                order.setArtWork(editText1.getText().toString());
                order.setArtWorkRate(editText2.getText().toString());

                String artWork = editText1.getText().toString().trim();
                String artWorkRate = editText2.getText().toString().trim();

                if(!artWork.isEmpty() && !artWorkRate.isEmpty()) {
                    Intent intent = new Intent(ArtWork.this, Coloring.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ArtWork.this,"Please enter value for ArtWork and Price, before proceeding further.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
