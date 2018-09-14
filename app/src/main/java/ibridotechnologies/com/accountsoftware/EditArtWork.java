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

import ibridotechnologies.com.accountsoftware.Model.EditOrderParams;
import ibridotechnologies.com.accountsoftware.Model.OrderToUpdate;

public class EditArtWork extends AppCompatActivity {

    EditText editText1,editText2;
    Button btnModify;
    TextView txtUpdateArtWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_art_work);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        txtUpdateArtWork = (TextView)findViewById(R.id.txtUpdateArtWork);
        txtUpdateArtWork.setTypeface(font);
        txtUpdateArtWork.setText("\uf2dc");

        editText1 = (EditText)findViewById(R.id.editArtWork);
        editText2 = (EditText)findViewById(R.id.editArtWorkRate);

        editText1.setBackgroundResource(R.drawable.edit_text_rounded_border);
        editText2.setBackgroundResource(R.drawable.edit_text_rounded_border);

        OrderToUpdate orderToUpdate = new OrderToUpdate();
        editText1.setText(orderToUpdate.getArtwork());
        editText2.setText(orderToUpdate.getArtworkRate());

        btnModify = (Button)findViewById(R.id.btnModifyArtWork);
        btnModify.setBackgroundResource(R.drawable.button_rounded_border);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String artWork = editText1.getText().toString().trim();
                String artWorkRate = editText2.getText().toString().trim();

                if(!artWork.isEmpty() && !artWorkRate.isEmpty()) {
                    OrderToUpdate eop = new OrderToUpdate();
                    eop.setArtwork(artWork);
                    eop.setArtworkRate(artWorkRate);
                    Intent intent = new Intent(EditArtWork.this, EditColoring.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(EditArtWork.this,"Please enter value for ArtWork and price, before update.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
