package ibridotechnologies.com.accountsoftware.getJson.gvJson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ibridotechnologies.com.accountsoftware.EditPages;
import ibridotechnologies.com.accountsoftware.GridViewAdapter;
import ibridotechnologies.com.accountsoftware.Model.Order;
import ibridotechnologies.com.accountsoftware.OptionsActivity;

/**
 * Created by Rahul on 06-Nov-17.
 */

public class JSONParser extends AsyncTask<Void,Void,Boolean> {

    Context c;
    String jsonData;
    String preJson;
    GridView gv;
    ProgressDialog pg;
    Typeface font;

    ProgressBar pb;
    ArrayList<String> Order_Id = new ArrayList<>();
    ArrayList<String> Book_Title = new ArrayList<>();

    public JSONParser(Context c, String jsonData, GridView gv, ProgressDialog pg, Typeface font) {
        this.c = c;
        this.jsonData = jsonData;
        this.gv = gv;
        this.pg = pg;
        this.font = font;
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
            //ArrayAdapter<String> adapter=new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,category_name);
            if(!Order_Id.isEmpty()) {
                GridViewAdapter adapter = new GridViewAdapter(c, Order_Id, Book_Title, font);
                gv.setAdapter(adapter);

                if (pg.isShowing()) {
                    pg.dismiss();
                }

                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Order order = new Order();
                        order.setOrderId(Integer.parseInt(Order_Id.get(i)));
                        Toast.makeText(c, Book_Title.get(i) + " : Selected", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {

                Toast.makeText(c,"Selected combination doesn't contain any data.",Toast.LENGTH_SHORT).show();
                new RedirectToHome().sendto(c);

            }
        }else{
            Toast.makeText(c,"Unable to parse, Check your log output.",Toast.LENGTH_SHORT).show();
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

            Order_Id.clear();
            Book_Title.clear();

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                String _name = jo.getString("Book_Title");
                String _id = jo.getString("Order_Id");
                Book_Title.add(_name);
                Order_Id.add(_id);
            }


            return true;
        }
        catch (JSONException e){
            e.printStackTrace();
            return false;
        }
    }
}

class RedirectToHome extends Activity {
    public void sendto(Context c){
        Intent i = new Intent(c,OptionsActivity.class);
        c.startActivity(i);
    }
}