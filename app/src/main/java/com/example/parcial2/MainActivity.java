package com.example.parcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.parcial2.Clases.Luz;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private ImageView hab1;
    private ImageView hab2;
    private ImageView hab3;
    private ImageView hab4;
    private ImageView bath;
    private ImageView kitch;
    public List<Luz> arreglo = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hab1 = findViewById(R.id.hab1);
        hab2 = findViewById(R.id.hab2);
        hab3 = findViewById(R.id.hab3);
        hab4 = findViewById(R.id.hab4);

        bath = findViewById(R.id.bath);
        kitch = findViewById(R.id.kitch);




        arreglo.add(new Luz("luzBano", true));
        arreglo.add(new Luz("luzCocina", true));
        arreglo.add(new Luz("luzHabitacion1", true));
        arreglo.add(new Luz("luzHabitacion2", false));
        arreglo.add(new Luz("luzHabitacion3", false));
        arreglo.add(new Luz("luzHabitacion4", true));
        setAllImages();

    }
    public void setAllImages(){
        setImage(arreglo.get(0),bath);
        setImage(arreglo.get(1),kitch);
        setImage(arreglo.get(2),hab1);
        setImage(arreglo.get(3),hab2);
        setImage(arreglo.get(4),hab3);
        setImage(arreglo.get(5),hab4);
    }
        public void setImage(Luz luz, ImageView imgView){

            if (luz.estado == true){
                imgView.setImageResource(R.drawable.on);
            }else {
                imgView.setImageResource(R.drawable.off);
            }
        }


    public void getCurrentStatus(String serviceEndPoint){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    URL urlService = new URL ("http://192.168.180.26:3000/aptos/aptoStatus" );
                    HttpsURLConnection connection =  (HttpsURLConnection) urlService.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream responseBody = connection.getInputStream();

                    if (connection.getResponseCode() == 200) {
                        // Success
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                        BufferedReader streamReader = new BufferedReader(responseBodyReader);
                        StringBuilder responseStrBuilder = new StringBuilder();

                        String inputStr;
                        while ((inputStr = streamReader.readLine()) != null)
                            responseStrBuilder.append(inputStr);

                        JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
                        arreglo = crearListaFromJSON(jsonObject);
                        setAllImages();
                    } else {
                        // Error handling code goes here
                        Log.v("ERROR", "ERROR");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<Luz> crearListaFromJSON(JSONObject jsonObject) throws JSONException {
        List<Luz> listaLuces = new ArrayList<>();
        Iterator<String> keys = jsonObject.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            if (jsonObject.get(key) instanceof JSONObject) {
                listaLuces.add(new Luz(key, jsonObject.getBoolean(key)));
            }
        }
        return listaLuces;
    }
}
