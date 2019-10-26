package com.example.parcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.parcial2.Clases.Luz;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView hab1;
    private ImageView hab2;
    private ImageView hab3;
    private ImageView hab4;
    private ImageView bath;
    private ImageView kitch;



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


        List<Luz> arreglo = new ArrayList<>();

        arreglo.add(new Luz("luzBano", true));
        arreglo.add(new Luz("luzCocina", true));
        arreglo.add(new Luz("luzHabitacion1", true));
        arreglo.add(new Luz("luzHabitacion2", true));
        arreglo.add(new Luz("luzHabitacion3", true));
        arreglo.add(new Luz("luzHabitacion4", true));

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
    }
