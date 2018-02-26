package com.udacity.sandwichclub.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static String getString(Context context, int key_resource_id) {
        return context.getString(key_resource_id);
    }

    private static ArrayList<String> toArrayList(JSONArray jsonArray) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            arrayList.add(jsonArray.optString(i));
        }
        return arrayList;
    }

    private static ArrayList<String> getArrayList(Context context, JSONObject jsonObject, int key_resource_id) {
        return toArrayList(jsonObject.optJSONArray(getString(context, key_resource_id)));
    }

    public static Sandwich parseSandwichJson(Context context, String json) {

        try {
            JSONObject sandwichRoot = new JSONObject(json);
            JSONObject name = sandwichRoot.optJSONObject(getString(context, R.string.sandwich_name_key));
            String mainName = name.optString(getString(context, R.string.sandwich_main_name_key));

            ArrayList<String> alsoKnownAs = getArrayList(context, name, R.string.sandwich_alternative_names_key);
            ArrayList<String> ingredients = getArrayList(context, sandwichRoot, R.string.sandwich_ingredients_key);

            String placeOfOrigin = sandwichRoot.optString(getString(context, R.string.sandwich_place_of_origin_key));
            String description = sandwichRoot.optString(getString(context, R.string.sandwich_description_key));
            String image = sandwichRoot.optString(getString(context, R.string.sandwich_image_key));

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
