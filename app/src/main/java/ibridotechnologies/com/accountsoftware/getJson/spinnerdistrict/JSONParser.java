package ibridotechnologies.com.accountsoftware.getJson.spinnerdistrict;

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

/**
 * Created by Rahul on 22-Jul-18.
 */

public class JSONParser extends AsyncTask<Void,Void,Boolean> {

    Context c;
    String jsonData;
    String preJson;
    Spinner sp;
    ProgressDialog pg;

    ArrayList<String> District_ID = new ArrayList<>();
    ArrayList<String> DistrictName = new ArrayList<>();

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
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(c,android.R.layout.simple_spinner_dropdown_item, DistrictName);
            sp.setAdapter(adapter);

            if(pg.isShowing()){
                pg.dismiss();
            }

            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    NewParty np=new NewParty();
                    np.setDistrict_id(District_ID.get(i));
                    //String id = District_ID.get(i);
                    //Toast.makeText(c,id,Toast.LENGTH_LONG).show();

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

            District_ID.clear();
            DistrictName.clear();

            DistrictName.add("Select a District");
            District_ID.add("0");

            for(int i=0;i< ja.length();i++){
                jo=ja.getJSONObject(i);
                String _name=jo.getString("DistrictName");
                String _id=jo.getString("District_ID");
                DistrictName.add(_name);
                District_ID.add(_id);
            }
            return true;
        }
        catch (JSONException e){
            e.printStackTrace();
            return false;
        }
    }
}
