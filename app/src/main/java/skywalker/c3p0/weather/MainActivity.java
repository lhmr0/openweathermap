package skywalker.c3p0.weather;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class MainActivity extends Activity {

    JSONObject data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getJSON("Sydney");
    }

    public void getJSON(final String city) {

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String a = "ea574594b9d36ab688642d5fbeab847e";
                    String ci = "3691175";
                    String lat="-7.1639629";
                    String lon="-78.50";
                    URL url1 = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&APPID="+a"");

                    URL url = new URL("http://api.openweathermap.org/data/2.5/weather?id="+ci+"&APPID=ea574594b9d36ab688642d5fbeab847e");


                    HttpURLConnection connection = (HttpURLConnection) url1.openConnection();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuffer json = new StringBuffer(1024);
                    String tmp = "";

                    while((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    data = new JSONObject(json.toString());

                    if(data.getInt("cod") != 200) {
                        System.out.println("Cancelled");
                        return null;
                    }


                } catch (Exception e) {

                    System.out.println("Exception "+ e.getMessage());
                    return null;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void Void) {
                if(data!=null){
                    Log.d("datos recibidos: ",data.toString());
                    String valorMain="";
                    String valorDes="";

                    try {
                        //    contenidoJson es tu string conteniendo el json.
                      JSONObject mainObject = new JSONObject(data.toString());
                        //JSONArray mainObject = new JSONArray(data.toString());
                        //Obtenemos los objetos dentro del objeto principal.
                        Iterator<String> keys = mainObject.keys();

                        while (keys.hasNext())
                        {
                            // obtiene el nombre del objeto.
                            String key = keys.next();
                            String ke = "weather";
                            Log.i("Parser", "objeto : " + ke);
                           JSONObject jsonObject = mainObject.getJSONObject(key);
                           // JSONArray json = mainObject.getJSONArray(ke);

                            //obtiene valores dentro del objeto.
                            //valorMain = jsonObject.getString("main");

                            //valorMain = json.getString(0);
                            //valorDes = json.getString(1);

                            //Imprimimos los valores.
                            Log.i("Parser", valorMain);
                            Log.i("Parser", valorDes);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("Parser", e.getMessage());
                    }
                }

            }


        }.execute();

    }


}
