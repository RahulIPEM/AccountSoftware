package ibridotechnologies.com.accountsoftware;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PartyOptions extends AppCompatActivity {

    Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_options);

        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");

        btn1.setTypeface(font);
        btn1.setText("\uf234 Party Details");

        btn2.setTypeface(font);
        btn2.setText("\uf040 Modify Party Details");

        btn1.setBackgroundResource(R.drawable.button_rounded_border);
        btn2.setBackgroundResource(R.drawable.button_rounded_border);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PartyOptions.this,AddParty.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PartyOptions.this,EditParty.class);
                startActivity(intent);
            }
        });
    }
}
