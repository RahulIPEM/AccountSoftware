package ibridotechnologies.com.accountsoftware;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText1,editText2,editText3,editText4;
    private int previousLength;
    private boolean backSpace;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = (EditText)findViewById(R.id.edit_text1);
        editText2 = (EditText)findViewById(R.id.edit_text2);
        editText3 = (EditText)findViewById(R.id.edit_text3);
        editText4 = (EditText)findViewById(R.id.edit_text4);

        editText1.setBackgroundResource(R.drawable.passkey_rounded_border);
        editText2.setBackgroundResource(R.drawable.passkey_rounded_border);
        editText3.setBackgroundResource(R.drawable.passkey_rounded_border);
        editText4.setBackgroundResource(R.drawable.passkey_rounded_border);

        editText1.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        editText2.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        editText3.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        editText4.setTransformationMethod(new AsteriskPasswordTransformationMethod());

//        editText1.setTextSize(40);
//        editText2.setTextSize(40);
//        editText3.setTextSize(40);
//        editText4.setTextSize(40);

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer txtlength = editText1.getText().length();
                if (txtlength >= 1) {
                    editText2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                previousLength = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer txtlength = editText2.getText().length();
                if (txtlength >= 1) {
                    editText3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                backSpace = previousLength > editable.length();
                if(backSpace){
                    editText1.requestFocus();
                }
            }
        });

        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                previousLength = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer txtlength = editText3.getText().length();
                if (txtlength >= 1) {
                    editText4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                backSpace = previousLength > editable.length();
                if(backSpace){
                    editText2.requestFocus();
                }
            }
        });

        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                previousLength = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Integer txtlength = editText3.getText().length();
                if (txtlength >= 1) {
                    String firstPass,secondPass,thirdPass,fourthPass;
                    firstPass=editText1.getText().toString();
                    secondPass=editText2.getText().toString();
                    thirdPass=editText3.getText().toString();
                    fourthPass=editText4.getText().toString();
                    if(firstPass.equals("3") && secondPass.equals("4") && thirdPass.equals("5") && fourthPass.equals("6")){
                        Intent intent = new Intent(MainActivity.this,OptionsActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Invalid",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                backSpace = previousLength > editable.length();
                if(backSpace){
                    editText3.requestFocus();
                }
            }
        });

        imgView = (ImageView)findViewById(R.id.imglogosrs);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSRS();
            }
        });
    }

    public void goToSRS () {
        goToUrl ( "http://www.srstechsolution.com/");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
