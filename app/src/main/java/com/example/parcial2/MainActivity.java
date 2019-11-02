package com.example.parcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.parcial2.Clases.Luz;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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
    public String URLGET = "http://192.168.43.178:3000/aptos/aptostatus";


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


        arreglo.add(new Luz("luzBano", false));
        arreglo.add(new Luz("luzCocina", false));
        arreglo.add(new Luz("luzHabitacion1", true));
        arreglo.add(new Luz("luzHabitacion2", true));
        arreglo.add(new Luz("luzHabitacion3", true));
        arreglo.add(new Luz("luzHabitacion4", true));
        setAllImages();

        get();

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
    public void getCurrentStatus(){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    URL urlService = new URL ("http://192.168.43.178:3000/aptos/aptostatus" );
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

                listaLuces.add(new Luz(key, jsonObject.getBoolean(key)));

        }
        return listaLuces;
    }
    public void get() {
        // Conex exion = new Conex();
        // exion.get();

        // n1 = exion.n1;
        // r1 = exion.r1;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlService = new URL(URLGET);
                    HttpURLConnection connection = (HttpURLConnection) urlService.openConnection();
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
                    }

                } catch (Exception e) {

                    setAllImages();
                }
            }
        });
    }

    public void o_hab4(View view) {
        arreglo.get(5).estado = !arreglo.get(5).estado;
        setImage(arreglo.get(5),hab4);
        callPostService();
    }
    public void o_hab3(View view) {
        arreglo.get(4).estado = !arreglo.get(4).estado;
        setImage(arreglo.get(4),hab3);
        callPostService();
    }
    public void o_hab2(View view) {
        arreglo.get(3).estado = !arreglo.get(3).estado;
        setImage(arreglo.get(3),hab2);
        callPostService();
    }
    public void o_hab1(View view) {
        arreglo.get(2).estado = !arreglo.get(2).estado;
        setImage(arreglo.get(2),hab1);
        callPostService();
    }
    public void o_bath(View view) {
        arreglo.get(0).estado = !arreglo.get(0).estado;
        setImage(arreglo.get(0),bath);
        callPostService();
    }
    public void o_kitch(View view) {
        arreglo.get(1).estado = !arreglo.get(1).estado;
        setImage(arreglo.get(1),kitch);
        callPostService();
    }

    public void callPostService () {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlService = new URL ("http://192.168.43.178:3000/aptos/changeAptoStatusBody" );
                    HttpURLConnection connection =  (HttpURLConnection) urlService.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);
                    DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
                    wr.writeBytes("{\"luzBano\":"+Boolean.toString(arreglo.get(0).estado)+",\"luzCocina\":"+Boolean.toString(arreglo.get(1).estado)+",\"luzHabitacion1\":"+Boolean.toString(arreglo.get(2).estado)+",\"luzHabitacion2\":"+Boolean.toString(arreglo.get(3).estado)+",\"luzHabitacion3\":"+Boolean.toString(arreglo.get(4).estado)+",\"luzHabitacion4\":"+Boolean.toString(arreglo.get(5).estado)+"}");
                    wr.close();

                    InputStream responseBody = connection.getInputStream();
                    if (connection.getResponseCode() == 200) {
                        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
