package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwichRoot = new JSONObject(json);
            JSONObject name = sandwichRoot.getJSONObject("name");
            String mainName = name.getString("mainName");


            JSONArray alternativeNames = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<String>();
            for (int i = 0; i < alternativeNames.length(); i++) {
                alsoKnownAs.add(alternativeNames.getString(i));
            }

            JSONArray ingredientsData = sandwichRoot.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<String>();
            for (int i = 0; i < ingredientsData.length(); i++) {
                ingredients.add(ingredientsData.getString(i));
            }

            String placeOfOrigin = sandwichRoot.getString("placeOfOrigin");
            String description = sandwichRoot.getString("description");
            String image = sandwichRoot.getString("image");

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
