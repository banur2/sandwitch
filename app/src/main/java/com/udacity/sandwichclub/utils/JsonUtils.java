package com.udacity.sandwichclub.utils;

import android.content.Context;
import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {

            JSONObject sandwichJSONObj = new JSONObject(json);
            JSONObject nameJSONObj = sandwichJSONObj.getJSONObject("name");
            JSONArray aliasJSONArray = nameJSONObj.getJSONArray("alsoKnownAs");
            ArrayList<String> aliasList = new ArrayList<>();
            if (aliasJSONArray != null) {
                for (int i = 0; i < aliasJSONArray.length(); i++) {
                    aliasList.add(aliasJSONArray.getString(i));
                }
            }
            JSONArray ingredientsJSONArray = sandwichJSONObj.getJSONArray("ingredients");
            ArrayList<String> ingredientsList = new ArrayList<>();
            for (int i=0; i< ingredientsJSONArray.length(); i++){
                ingredientsList.add(ingredientsJSONArray.getString(i));
            }
            Sandwich sandwitchObj = new Sandwich(
                    nameJSONObj.getString("mainName"),
                    aliasList,
                    sandwichJSONObj.getString("placeOfOrigin"),
                    sandwichJSONObj.getString("description"),
                    sandwichJSONObj.getString("image"),ingredientsList);

            return sandwitchObj;



        }
        catch (JSONException e){
            Log.e("JSONUtils", e.getMessage());

        }

        return null;
    }
}
