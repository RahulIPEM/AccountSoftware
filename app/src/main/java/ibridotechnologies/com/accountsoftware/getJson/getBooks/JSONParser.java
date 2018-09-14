package ibridotechnologies.com.accountsoftware.getJson.getBooks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ibridotechnologies.com.accountsoftware.Model.Order;
import ibridotechnologies.com.accountsoftware.Params;

/**
 * Created by Rahul on 22-Jul-18.
 */

public class JSONParser extends AsyncTask<Void,Void,Boolean> {

    Context c;
    String jsonData;
    String preJson;
    Spinner sp;

    ProgressBar pb;
    ArrayList<String> Book_Id = new ArrayList<>();
    ArrayList<String> Book_Title = new ArrayList<>();

    public JSONParser(Context c, String jsonData, Spinner sp) {
        this.c = c;
        this.jsonData = jsonData;
        this.sp = sp;
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
                    Params params = new Params();
                    params.setBookId(Integer.parseInt(Book_Id.get(i)));
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
}
