package ibridotechnologies.com.accountsoftware;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ibridotechnologies.com.accountsoftware.Model.Ledger;
import ibridotechnologies.com.accountsoftware.Model.OrderToUpdate;

public class Bills extends AppCompatActivity {

    final String TAG = "Manifest Permission";

    TableRow trPages,trArtwork,trColoring,trWriting,trProofing,trTotal,trShare;
    TextView txtFontComputer,txtPartyName,txtBillDate,txtBillNo,txtBookName,pagesNo,pagesRate,pagesTotal,artwork,artworkRate,artworkTotal,coloring,coloringRate,coloringTotal,writing,writingRate,writingTotal,proofing,proofingRate,proofingTotal,grandTotal;
    ImageButton imgShareBtn,imgHomeBtn;
    AlertDialog.Builder builder;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);

        txtFontComputer = (TextView)findViewById(R.id.txtFontComputer);
        txtPartyName = (TextView)findViewById(R.id.txtPartyName);
        txtBillDate = (TextView)findViewById(R.id.txtBillDate);
        txtBillNo = (TextView)findViewById(R.id.txtBillNo);
        txtBookName = (TextView)findViewById(R.id.txtBookName);

        trPages = (TableRow)findViewById(R.id.trPages);
        pagesNo = (TextView)trPages.findViewById(R.id.pagesNo);
        pagesRate = (TextView)trPages.findViewById(R.id.pagesRate);
        pagesTotal = (TextView)trPages.findViewById(R.id.pagesTotal);

        trArtwork = (TableRow)findViewById(R.id.trArtwork);
        artwork = (TextView)trArtwork.findViewById(R.id.artwork);
        artworkRate = (TextView)trArtwork.findViewById(R.id.artworkRate);
        artworkTotal = (TextView)trArtwork.findViewById(R.id.artworkTotal);

        trColoring = (TableRow)findViewById(R.id.trColoring);
        coloring = (TextView)trColoring.findViewById(R.id.coloring);
        coloringRate = (TextView)trColoring.findViewById(R.id.coloringRate);
        coloringTotal = (TextView)trColoring.findViewById(R.id.coloringTotal);

        trWriting = (TableRow)findViewById(R.id.trWriting);
        writing = (TextView)trWriting.findViewById(R.id.writing);
        writingRate = (TextView)trWriting.findViewById(R.id.writingRate);
        writingTotal = (TextView)trWriting.findViewById(R.id.writingTotal);

        trProofing = (TableRow)findViewById(R.id.trProofing);
        proofing = (TextView)trProofing.findViewById(R.id.proofing);
        proofingRate = (TextView)trProofing.findViewById(R.id.ProofingRate);
        proofingTotal = (TextView)trProofing.findViewById(R.id.proofingTotal);

        trTotal = (TableRow)findViewById(R.id.trTotal);
        grandTotal = (TextView)trTotal.findViewById(R.id.grandTotal);

        trShare = (TableRow)findViewById(R.id.trShare);
        imgShareBtn = (ImageButton)trShare.findViewById(R.id.shareBtn);

        builder = new AlertDialog.Builder(Bills.this);

        Ledger ledger = new Ledger();
        getBillReport(ledger.getFinancialYear(),ledger.getPartyId(),ledger.getBookId());

        imgShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Click Event : ","Fired");
                if(isStoragePermissionGranted()) {
                    takeScreenshot();
                }
            }
        });

        imgHomeBtn = (ImageButton)findViewById(R.id.homeBtn);
        imgHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Bills.this,OptionsActivity.class));
            }
        });
    }

    protected void getBillReport(String financialYear, String partyId, String bookId) {
        int id = new ibridotechnologies.com.accountsoftware.Model.Order().getOrderId();
        final String url = "http://www.acmecreations.co.in/api/AccountManagement/GetPerBookBillReport/"+financialYear+"/"+partyId+"/"+bookId;
        Log.d("Order URL :",url);
        RequestQueue queue = Volley.newRequestQueue(Bills.this);

// prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, (String) null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        OrderToUpdate toUpdate = new OrderToUpdate();
                        Log.d("Order Response :", response.toString());
                        if (response.length() > 0) {
                            try {
                                // Loop through the array elements
                                Log.d("length :", String.valueOf(response.length()));
                                for (int i = 0; i < response.length(); i++) {
                                    // Get current json object
                                    JSONObject bill = response.getJSONObject(i);
                                    Log.d("Bill Json", bill.toString());

                                    txtPartyName.setText(bill.getString("Party_Name"));
                                    String[] date = bill.getString("OrderReceivedDate").split("T");
                                    txtBillDate.setText(date[0]);
                                    txtBillNo.setText(bill.getString("Order_Id"));
                                    txtBookName.setText(bill.getString("Book_Title"));
                                    pagesNo.setText(bill.getString("PagesNo"));
                                    pagesRate.setText(bill.getString("Rate"));
                                    artwork.setText(bill.getString("Artwork"));
                                    artworkRate.setText(bill.getString("ArtworkRate"));
                                    coloring.setText(bill.getString("Coloring"));
                                    coloringRate.setText(bill.getString("ColoringRate"));
                                    writing.setText(bill.getString("Writing"));
                                    writingRate.setText(bill.getString("WritingRate"));
                                    proofing.setText(bill.getString("ProofingPage"));
                                    proofingRate.setText(bill.getString("ProofingAmount"));

                                    pagesTotal.setText(String.valueOf(Float.parseFloat(pagesNo.getText().toString()) * Float.parseFloat(pagesRate.getText().toString())));
                                    artworkTotal.setText(String.valueOf(Float.parseFloat(artwork.getText().toString()) * Float.parseFloat(artworkRate.getText().toString())));
                                    coloringTotal.setText(String.valueOf(Float.parseFloat(coloring.getText().toString()) * Float.parseFloat(coloringRate.getText().toString())));
                                    writingTotal.setText(String.valueOf(Float.parseFloat(writing.getText().toString()) * Float.parseFloat(writingRate.getText().toString())));
                                    proofingTotal.setText(String.valueOf(Float.parseFloat(proofing.getText().toString()) * Float.parseFloat(proofingRate.getText().toString())));

                                    grandTotal.setText(String.valueOf(Float.parseFloat(pagesTotal.getText().toString()) + Float.parseFloat(artworkTotal.getText().toString()) + Float.parseFloat(coloringTotal.getText().toString()) + Float.parseFloat(writingTotal.getText().toString()) + Float.parseFloat(proofingTotal.getText().toString())));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //Toast.makeText(context,toUpdate.getRate(),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            builder.setTitle("Status");
                            builder.setMessage("Selected combination does not have any data.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(Bills.this,OptionsActivity.class));
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Order Error :", error.toString());
                    }
                }
        );

// add it to the RequestQueue
        queue.add(getRequest);
    }


    private void takeScreenshot() {
        String date = new SimpleDateFormat("dd-MM-yyyy_hh:mm:ss", Locale.getDefault()).format(new Date());

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String Path = Environment.getExternalStorageDirectory().toString() + "/AccountSoftware/Image/";
            File dir = new File(Path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String mPath = Environment.getExternalStorageDirectory().toString() + "/AccountSoftware/Image/invoice_"+ date + ".jpg";
            Log.d("Path : ",mPath);

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            createPdf(mPath);
        }
        catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public void createPdf(String path) throws IOException, DocumentException {
        String date = new SimpleDateFormat("dd-MM-yyyy_hh:mm:ss", Locale.getDefault()).format(new Date());

        Document document = new Document();
        String directoryPath = Environment.getExternalStorageDirectory().toString()+"/AccountSoftware/PDF/";
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String pdfPath = directoryPath + "invoice_"+date+".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(pdfPath)); //  Change pdf's name.

        document.open();

        Image image = Image.getInstance(path);  // Change image's name and extension.

        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                - document.rightMargin() - 0) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
        image.scalePercent(scaler);
        image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);

        document.add(image);
        document.close();
        sharePdf(pdfPath);
    }

    public void sharePdf(String path){

        /*Code to bypass the strict mode policies and app will ignore the security warning. Basically, Its not the right way to solve this. */
        /*
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        */

        //Right way to do this
        File pdfFile = new File(path);
        Log.d("File Path :",pdfFile.toString());
        Uri uri = FileProvider.getUriForFile(Bills.this,
                "ibridotechnologies.com.accountsoftware.provider",
                pdfFile);
        Log.d("URI :",uri.toString());
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("application/pdf");
        startActivity(shareIntent);
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
