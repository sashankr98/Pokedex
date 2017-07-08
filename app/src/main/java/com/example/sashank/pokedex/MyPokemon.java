package com.example.sashank.pokedex;

import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by sashank on 29/6/17.
 */

public class MyPokemon {

    private int id;
    private String name;
    private double height;   //metres
    private double weight;   //kg
    private ArrayList<String> types;
    private String frontDefaultSprite;

    public MyPokemon (int id, String name, double height, double weight, String type1, @Nullable String type2, String sprite) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;

        this.types = new ArrayList<>();
        this.types.add(type1);
        if (type2 != null)
            this.types.add(type2);

        this.frontDefaultSprite = sprite;
    }

    public MyPokemon (JSONObject response) {
        try {
            id = response.getInt("id");
            name = response.getString("name");
            height = (double)(response.getInt("height"))/10;
            weight = (double)(response.getInt("weight"))/10;

            JSONObject sprites = response.getJSONObject("sprites");
            if (sprites.has("front_default"))
                frontDefaultSprite = sprites.getString("front_default");

            types = new ArrayList<>();
            JSONArray typeArray = response.getJSONArray("types");
            for (int i =0; i< typeArray.length(); ++i) {
                JSONObject currentTypeObject = typeArray.getJSONObject(i);
                JSONObject currentType = currentTypeObject.getJSONObject("type");
                types.add(currentType.getString("name"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public String getFrontDefaultSprite() {
        return frontDefaultSprite;
    }

    public String formId () {
        return "#" + String.format(Locale.CANADA, "%03d", id);
    }
}
