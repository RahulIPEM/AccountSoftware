package ibridotechnologies.com.accountsoftware.getJson.spinnerstate;

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

import ibridotechnologies.com.accountsoftware.Model.NewParty;
import ibridotechnologies.com.accountsoftware.PartyName;
import ibridotechnologies.com.accountsoftware.getJson.spinnerdistrict.JSONDownloaderSP;

/**
 * Created by Rahul on 22-Jul-18.
 */

public class JSONParser extends AsyncTask<Void,Void,Boolean> {

    Context c;
    String jsonData;
    String preJson;
    Spinner sp,spinnerDistrict;
    ProgressDialog pg;

    ProgressBar pb;
    ArrayList<String> State_ID = new ArrayList<>();
    ArrayList<String> StateName = new ArrayList<>();

    public JSONParser(Context c, String jsonData, Spinner sp, Spinner spinnerDistrict,ProgressDialog pg) {
        this.c = c;
        this.jsonData = jsonData;
        this.sp = sp;
        this.spinnerDistrict = spinnerDistrict;
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
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(c,android.R.layout.simple_spinner_dropdown_item, StateName);
            sp.setAdapter(adapter);

            if(pg.isShowing()){
                pg.dismiss();
            }

            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    NewParty params=new NewParty();
                    params.setState_id(State_ID.get(i));
                    String id = State_ID.get(i);
                    String districtURL = params.getDistrictListURL()+id;
                    //Toast.makeText(c,districtURL,Toast.LENGTH_SHORT).show();
                    //new JSONDownloaderSP(c,districtURL,spinnerDistrict).execute();

                    pg.setTitle("District");
                    pg.setMessage("Loading... Please wait.");
                    pg.show();

                    new JSONDownloaderSP(c,districtURL,spinnerDistrict,pg).execute();
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

            State_ID.clear();
            StateName.clear();

            StateName.add("Select State");
            State_ID.add("0");

            for(int i=0;i< ja.length();i++){
                jo=ja.getJSONObject(i);
                String _name=jo.getString("StateName");
                String _id=jo.getString("State_ID");
                StateName.add(_name);
                State_ID.add(_id);
            }
            return true;
        }
        catch (JSONException e){
            e.printStackTrace();
            return false;
        }
    }
}
