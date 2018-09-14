package ibridotechnologies.com.accountsoftware;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ibridotechnologies.com.accountsoftware.Model.*;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.END;
import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;
import static android.view.Gravity.START;

/**
 * Created by Rahul on 08-Nov-17.
 */

public class GridViewAdapter extends BaseAdapter {

    //Context
    private Context context;
    AlertDialog.Builder builder;

    //Array List that would contain the urls and the titles for the images
    private ArrayList<String> _id;
    private ArrayList<String> _bookTitle;
    private Typeface font;

    public GridViewAdapter(Context context, ArrayList<String> _id, ArrayList<String> _category, Typeface font) {
        this.context = context;
        this._id = _id;
        this._bookTitle = _category;
        this.font = font;
        builder = new AlertDialog.Builder(context);
    }

    @Override
    public int getCount() {
        return _bookTitle.size();
    }

    @Override
    public Object getItem(int i) {
        return _bookTitle.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Creating a linear layout
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(CENTER);

        final TextView textView = new TextView(context);
        textView.setText(_id.get(i));
        textView.setVisibility(view.INVISIBLE);

        // Start of Horizontal Linear Layout nested in Vertical Linear Layout
        LinearLayout li = new LinearLayout(context);
        li.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams liParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        liParams.setMargins(20, 10, 20, 10);
        liParams.setLayoutDirection(CENTER);
        li.setGravity(LEFT);

        final TextView txtBookTitle = new TextView(context);
        txtBookTitle.setText(_bookTitle.get(i));
        txtBookTitle.setGravity(START);
        txtBookTitle.setWidth(500);
        txtBookTitle.setPaintFlags(txtBookTitle.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        txtBookTitle.setPadding(30,30,30,30);
        txtBookTitle.setTypeface(txtBookTitle.getTypeface(), Typeface.BOLD);

        final TextView txtSno = new TextView(context);
        txtSno.setText(String.valueOf(i+1+" ."));
        txtSno.setGravity(START);
        txtSno.setTextColor(Color.parseColor("#000000"));
        txtSno.setPadding(30,30,30,30);
        txtSno.setTypeface(txtSno.getTypeface(), Typeface.BOLD);

        // End of Horizontal Linear Layout nested in Vertical Linear Layout

        final Button btnCategory = new Button(context);
        btnCategory.setTypeface(font);
        btnCategory.setText("\uf040");
        btnCategory.setGravity(CENTER);
        btnCategory.setPadding(0,25,0,25);
        btnCategory.setBackgroundResource(R.drawable.button_rounded_border);
        btnCategory.setBackgroundColor(Color.parseColor("#00000000"));
        btnCategory.setTextColor(Color.parseColor("#000000"));
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,textView.getText().toString(),Toast.LENGTH_SHORT).show();
                EditOrderParams eop = new EditOrderParams();
                eop.setOrderId(Integer.parseInt(textView.getText().toString()));
                getOrderDataById(textView.getText().toString(), context);
                new AlertDialog.Builder(context)
                        .setTitle("Confirm")
                        .setMessage("Do you really want to proceed with selected order..?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Toast.makeText(context, "Yaay", Toast.LENGTH_SHORT).show();
                                new Redirect().sendto(context);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        final Button btnDelete = new Button(context);
        btnDelete.setTypeface(font);
        btnDelete.setText("\uf1f8");
        btnDelete.setGravity(CENTER);
        btnDelete.setPadding(0,25,0,25);
        btnDelete.setBackgroundResource(R.drawable.button_rounded_border);
        btnDelete.setBackgroundColor(Color.parseColor("#00000000"));
        btnDelete.setTextColor(Color.parseColor("#000000"));
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,textView.getText().toString(),Toast.LENGTH_SHORT).show();
                EditOrderParams eop = new EditOrderParams();
                eop.setOrderId(Integer.parseInt(textView.getText().toString()));
                getOrderDataById(textView.getText().toString(), context);
                new AlertDialog.Builder(context)
                        .setTitle("Confirm")
                        .setMessage("Do you really want to delete this record ..?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(context, "Delete function is not completed yet.", Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context,OptionsActivity.class));
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        li.addView(txtSno);
        li.addView(txtBookTitle);
        li.addView(btnCategory);
        li.addView(btnDelete);


//        LinearLayout liBtn = new LinearLayout(context);
//        liBtn.setOrientation(LinearLayout.HORIZONTAL);
//        LinearLayout.LayoutParams liBtnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        liBtnParams.setMargins(20, 10, 20, 10);
//        liBtnParams.setLayoutDirection(CENTER);
//        liBtn.setGravity(CENTER);
//
//        liBtn.addView(btnCategory);
//        liBtn.addView(btnDelete);
//
//        LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        txtParams.setMargins(20, 10, 20, 10);
//        txtParams.setLayoutDirection(CENTER);
//        btnCategory.setLayoutParams(txtParams);
//        btnDelete.setLayoutParams(txtParams);
//
//        //Adding views to the layout
//        linearLayout.addView(textView);
//        linearLayout.addView(li);
//        linearLayout.addView(liBtn);


        return li;
    }

    protected void getOrderDataById(String id, final Context context){
        final String url = "http://www.acmecreations.co.in/api/AccountManagement/GetOrderDetailsByOrderId/"+id;
        Log.d("Order URL :",url);
        RequestQueue queue = Volley.newRequestQueue(context);

// prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, (String) null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        OrderToUpdate toUpdate = new OrderToUpdate();
                        Log.d("Order Response :", response.toString());
                        try{
                            // Loop through the array elements
                            Log.d("length :",String.valueOf(response.length()));
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                Log.d("In :",response.getJSONObject(i).toString());
                                JSONObject order = response.getJSONObject(i);

                                // Get the current order (json object) data
                                toUpdate.setParty_Id(order.getString("Party_Id"));
                                toUpdate.setOrder_Id(order.getString("Order_Id"));
                                toUpdate.setBook_Id(order.getString("Book_Id"));
                                toUpdate.setParty_Name(order.getString("Party_Name"));
                                toUpdate.setBook_Title(order.getString("Book_Title"));
                                toUpdate.setOrderReceivedDate(order.getString("OrderReceivedDate"));
                                toUpdate.setPagesNo(order.getString("PagesNo"));
                                toUpdate.setRate(order.getString("Rate"));
                                toUpdate.setArtwork(order.getString("Artwork"));
                                toUpdate.setArtworkRate(order.getString("ArtworkRate"));
                                toUpdate.setColoring(order.getString("Coloring"));
                                toUpdate.setColoringRate(order.getString("ColoringRate"));
                                toUpdate.setWriting(order.getString("Writing"));
                                toUpdate.setWritingRate(order.getString("WritingRate"));
                                toUpdate.setProofingPage(order.getString("ProofingPage"));
                                toUpdate.setProofingAmount(order.getString("ProofingAmount"));
                                toUpdate.setFinancialYear(order.getString("FinancialYear"));
                                toUpdate.setIsDeleted(order.getString("IsDeleted"));
                                toUpdate.setSaved_By(order.getString("Saved_By"));
                                toUpdate.setSaved_Date(order.getString("Saved_Date"));
                                toUpdate.setLastMod_By(order.getString("LastMod_By"));
                                toUpdate.setLastMod_Date(order.getString("LastMod_Date"));
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        //Toast.makeText(context,toUpdate.getRate(),Toast.LENGTH_SHORT).show();
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
}

class Redirect extends Activity{
    public void sendto(Context c){
        Intent i = new Intent(c,EditPages.class);
        c.startActivity(i);
    }
}