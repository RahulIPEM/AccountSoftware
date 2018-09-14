package ibridotechnologies.com.accountsoftware.getJson.gvBalanceJson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ibridotechnologies.com.accountsoftware.GridViewAdapterAllPayment;
import ibridotechnologies.com.accountsoftware.GridViewAdapterBalance;
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
    TextView tv;

    ArrayList<String> Payment_type = new ArrayList<>();
    ArrayList<String> Amount = new ArrayList<>();

    public JSONParser(Context c, String jsonData, GridView gv, ProgressDialog pg, TextView tv) {
        this.c = c;
        this.jsonData = jsonData;
        this.gv = gv;
        this.pg = pg;
        this.tv = tv;
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
            if(!Payment_type.isEmpty()) {
                GridViewAdapterBalance adapter = new GridViewAdapterBalance(c, Payment_type, Amount, tv);
                gv.setAdapter(adapter);

                if (pg.isShowing()) {
                    pg.dismiss();
                }

                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Order order = new Order();
                        order.setOrderId(Integer.parseInt(Payment_type.get(i)));
                        Toast.makeText(c, Payment_type.get(i) + " : Selected", Toast.LENGTH_SHORT).show();
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

            Payment_type.clear();
            Amount.clear();

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                String _paymentType = jo.getString("Description");
                String _amount = jo.getString("Amount");
                Payment_type.add(_paymentType);
                Amount.add(_amount);
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