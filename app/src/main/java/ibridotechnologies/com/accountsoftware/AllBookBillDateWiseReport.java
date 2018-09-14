package ibridotechnologies.com.accountsoftware;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ibridotechnologies.com.accountsoftware.Model.AllBookDetails;
import ibridotechnologies.com.accountsoftware.Model.Ledger;
import ibridotechnologies.com.accountsoftware.getJson.gvAllBookJson.JSONDownloaderGV;

public class AllBookBillDateWiseReport extends AppCompatActivity {

    ProgressDialog progressDialog;
    GridView gv;
    TextView txtGrandTotal,txtPartyName;
    ImageButton imgShareBtn,imgHomeBtn;
    String JsonURL = "http://www.acmecreations.co.in/api/AccountManagement/GetAllBookBillDateWiseReport/";
    final String TAG = "Manifest Permission";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_book_bill_date_wise_report);

        gv = (GridView)findViewById(R.id.GridBookReport);

        progressDialog = new ProgressDialog(AllBookBillDateWiseReport.this);
        progressDialog.setTitle("Report");
        progressDialog.setMessage("Loading... Please wait.");
        progressDialog.show();

        txtPartyName = (TextView)findViewById(R.id.txtPartyName);
        Ledger l = new Ledger();
        txtPartyName.setText(l.getPartyName());

        txtGrandTotal = (TextView)findViewById(R.id.grandTotal);

        AllBookDetails allBookDetails = new AllBookDetails();
        String getAllBookReportURL = JsonURL+allBookDetails.getFinancialYear()+"/"+allBookDetails.getPartyId()+"/"+allBookDetails.getFromDate()+"/"+allBookDetails.getToDate();
        Log.d("Book Details URL",getAllBookReportURL);
        new JSONDownloaderGV(AllBookBillDateWiseReport.this,getAllBookReportURL,gv,progressDialog,txtGrandTotal).execute();

        imgShareBtn = (ImageButton)findViewById(R.id.shareBtn);
        imgShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isStoragePermissionGranted()) {
                    takeScreenshot();
                }
            }
        });

        imgHomeBtn = (ImageButton)findViewById(R.id.homeBtn);
        imgHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllBookBillDateWiseReport.this,OptionsActivity.class));
            }
        });
    }

    private void takeScreenshot() {
        String date = new SimpleDateFormat("dd-MM-yyyy_hh:mm:ss", Locale.getDefault()).format(new Date());

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String Path = Environment.getExternalStorageDirectory().toString() + "/AccountSoftware/Image/Book/";
            File dir = new File(Path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String mPath = Environment.getExternalStorageDirectory().toString() + "/AccountSoftware/Image/Book/invoice_"+ date + ".jpg";
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
        String directoryPath = Environment.getExternalStorageDirectory().toString()+"/AccountSoftware/PDF/Book/";
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
        Uri uri = FileProvider.getUriForFile(AllBookBillDateWiseReport.this,
                "ibridotechnologies.com.accountsoftware.provider",
                pdfFile);
        Log.d("URI :",uri.toString());
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("application/pdf");
        startActivity(shareIntent);
    }
}
