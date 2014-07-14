package Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class SigninActivity  extends AsyncTask<String,Void,String>{

   private TextView statusField,roleField;
   private Context context;
   private int byGetOrPost = 0; 
   //flag 0 means get and 1 means post.(By default it is get.)
   // Costruttore che prendere due parametri, il contesto di utilizzo e la stringa da scrivere nel database
   public SigninActivity(Context context,String city,double lon,double lat) throws URISyntaxException, ClientProtocolException, IOException {
      this.context = context;
     
      doInBackground(city,lon,lat);
   }

   protected void onPreExecute(){

   }
   /**
    * Metodo che scrive nel database
    * @param username1
    * @return
    * @throws URISyntaxException
    * @throws ClientProtocolException
    * @throws IOException
    */
   protected String doInBackground(String city,double lat,double lon) throws URISyntaxException, ClientProtocolException, IOException {
      
        	 Log.v("Eseguo registrazione",city);
            String code[]=city.split(" ");
            Log.v("Code.size",""+code.length);
           
            //city=code[0]+"%20"+code[1]+"%20"+code[2]+"%20";
        	 
            String link = "http://gestionespese.com/database2.php?city="
            +city+"&lat="+lat+"&lng="+lon;
            Log.v("url",link);
            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);
            return city;
      
   }
   @Override
   protected void onPostExecute(String result){
     
   }

@Override
protected String doInBackground(String... params) {
	// TODO Auto-generated method stub
	return null;
}
}