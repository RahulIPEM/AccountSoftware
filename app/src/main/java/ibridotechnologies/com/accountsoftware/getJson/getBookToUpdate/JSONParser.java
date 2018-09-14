package ibridotechnologies.com.accountsoftware.getJson.getBookToUpdate;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import ibridotechnologies.com.accountsoftware.Model.BookToUpdate;
import ibridotechnologies.com.accountsoftware.Model.PartyToUpdate;
import ibridotechnologies.com.accountsoftware.Params;
import ibridotechnologies.com.accountsoftware.UpdateParty;

/**
 * Created by Rahul on 22-Jul-18.
 */

public class JSONParser extends AsyncTask<Void,Void,Boolean> {

    Context c;
    String jsonData;
    String preJson;
    Spinner sp;
    TextView txtBookTitle,txtAuthorName,txtIsbnNo;

    ProgressBar pb;
    ArrayList<String> Book_Id = new ArrayList<>();
    ArrayList<String> Book_Title = new ArrayList<>();

    public JSONParser(Context c, String jsonData, Spinner sp, TextView txtBookTitle, TextView txtAuthorName, TextView txtIsbnNo) {
        this.c = c;
        this.jsonData = jsonData;
        this.sp = sp;
        this.txtBookTitle = txtBookTitle;
        this.txtAuthorName = txtAuthorName;
        this.txtIsbnNo = txtIsbnNo;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //String[] jsondata=jsonData.split("[<>]");
        preJson=jsonData;
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);

        if(isParsed){
            //Bind
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(c,android.R.layout.simple_spinner_dropdown_item, Book_Title);
            sp.setAdapter(adapter);

            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    BookToUpdate bookToUpdate = new BookToUpdate();
                    bookToUpdate.setBookId(Book_Id.get(i));
                    getBookDetailsByPartyId(Book_Id.get(i),txtBookTitle,txtAuthorName,txtIsbnNo);
                    //Toast.makeText(c, String.valueOf(order.getBookId()),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }else{
            Toast.makeText(c,"Unable to parse, Check your log output.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return parse();
    }

    //PARSE
    private Boolean parse(){
        try {
            JSONArray ja = new JSONArray(preJson);
            JSONObject jo;

            Book_Id.clear();
            Book_Title.clear();

            Book_Title.add("Select existing book");
            Book_Id.add("0");

            for(int i=0;i< ja.length();i++){
                jo=ja.getJSONObject(i);
                String _name=jo.getString("Book_Title");
                String _id=jo.getString("Book_Id");
                Book_Title.add(_name);
                Book_Id.add(_id);
            }
            return true;
        }
        catch (JSONException e){
            e.printStackTrace();
            return false;
        }
    }


    protected void getBookDetailsByPartyId(String id, final TextView txtBookTitle, final TextView txtAuthorName, final TextView txtIsbnNo){
        final String url = "http://www.acmecreations.co.in/api/AccountManagement/GetBookDetailsByBookId/"+id;
        Log.d("Order URL :",url);
        RequestQueue queue = Volley.newRequestQueue(c);

        //prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, (String) null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        BookToUpdate bookToUpdate = new BookToUpdate();
                        Log.d("Party Data :", response.toString());
                        try{
                            // Loop through the array elements
                            Log.d("length :",String.valueOf(response.length()));

                            Log.d("In :",response.getJSONObject(0).toString());
                            JSONObject party = response.getJSONObject(0);

                            bookToUpdate.setBookTitle(party.getString("Book_Title"));
                            bookToUpdate.setBookAuthor(party.getString("Book_Author"));
                            bookToUpdate.setBookIsbnNo(party.getString("Book_ISBN_No"));
                            // Get the current order (json object) data
                            txtBookTitle.setText(party.getString("Book_Title"));
                            txtAuthorName.setText(party.getString("Book_Author"));
                            txtIsbnNo.setText(party.getString("Book_ISBN_No"));

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
