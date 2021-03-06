package ibridotechnologies.com.accountsoftware.getJson.gvBalanceJson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Rahul on 06-Nov-17.
 */

public class Connector {

    public static Object connect(String jsonURL) {
        try{
            URL url=new URL(jsonURL);
            HttpURLConnection con=(HttpURLConnection) url.openConnection();

            //con props
            con.setRequestMethod("GET");
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            con.setDoInput(true);
            return con;
        }
        catch (MalformedURLException e){
            e.printStackTrace();
            return "Error "+e.getMessage();
        }
        catch (IOException e){
            e.printStackTrace();
            return "Error "+e.getMessage();
        }
    }

}
