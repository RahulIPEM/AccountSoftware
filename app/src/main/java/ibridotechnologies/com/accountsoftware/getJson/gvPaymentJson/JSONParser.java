package ibridotechnologies.com.accountsoftware.getJson.gvPaymentJson;

import android.app.ProgressDialog;
import android.content.Context;
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

import ibridotechnologies.com.accountsoftware.GridViewAdapterPayment;
import ibridotechnologies.com.accountsoftware.Model.Order;

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
    ArrayList<String> Payment_Id = new ArrayList<>();
    ArrayList<String> Payment_Amount = new ArrayList<>();
    ArrayList<String> Payment_date = new ArrayList<>();

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

            GridViewAdapterPayment adapter=new GridViewAdapterPayment(c, Payment_Id, Payment_Amount, Payment_date, font);
            gv.setAdapter(adapter);

            if(pg.isShowing()){
                pg.dismiss();
            }

            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Order order = new Order();
                    order.setOrderId(Integer.parseInt(Payment_Id.get(i)));
                    Toast.makeText(c, Payment_Amount.get(i)+" : Selected",Toast.LENGTH_SHORT).show();
                }
            });
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

            Payment_Id.clear();
            Payment_Amount.clear();

            for(int i=0;i< ja.length();i++){
                jo=ja.getJSONObject(i);
                String _name=jo.getString("Payment_Amount");
                String _id=jo.getString("Payment_Id");
                String _date=jo.getString("Payment_ReceivedDate");
                Payment_Amount.add(_name);
                Payment_Id.add(_id);
                Payment_date.add(_date);
            }
            return true;
        }
        catch (JSONException e){
            e.printStackTrace();
            return false;
        }
    }
}