package com.example.parcial2.Clases;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Luz {
    public String locacion;
    public boolean estado;

    public Luz(String olocacion, boolean oestado){
        locacion = olocacion;
        estado = oestado;
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
