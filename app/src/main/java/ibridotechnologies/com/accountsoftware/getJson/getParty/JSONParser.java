package ibridotechnologies.com.accountsoftware.getJson.getParty;

import android.app.ProgressDialog;
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

import ibridotechnologies.com.accountsoftware.Params;

/**
 * Created by Rahul on 22-Jul-18.
 */

public class JSONParser extends AsyncTask<Void,Void,Boolean> {

    Context c;
    String jsonData;
    String preJson;
    Spinner sp;
    ProgressDialog pg;

    ProgressBar pb;
    ArrayList<String> Party_Id = new ArrayList<>();
    ArrayList<String> Party_Name = new ArrayList<>();

    public JSONParser(Context c, String jsonData, Spinner sp, ProgressDialog pg) {
        this.c = c;
        this.jsonData = jsonData;
        this.sp = sp;
        this.pg = pg;
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
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(c,android.R.layout.simple_spinner_dropdown_item, Party_Name);
            sp.setAdapter(adapter);

            if(pg.isShowing()){
                pg.dismiss();
            }

            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Params params=new Params();
                    params.setPartyId(Integer.parseInt(Party_Id.get(i)));
                    //String id = Party_Id.get(i);
                    //String districtURL = params.getDistrictListURL()+id;
                    //Toast.makeText(c,id,Toast.LENGTH_SHORT).show();
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

            Party_Id.clear();
            Party_Name.clear();

            Party_Name.add("Select existing party");
            Party_Id.add("0");

            for(int i=0;i< ja.length();i++){
                jo=ja.getJSONObject(i);
                String _name=jo.getString("Party_Name");
                String _id=jo.getString("Party_Id");
                Party_Name.add(_name);
                Party_Id.add(_id);
            }
            return true;
        }
        catch (JSONException e){
            e.printStackTrace();
            return false;
        }
    }
}
