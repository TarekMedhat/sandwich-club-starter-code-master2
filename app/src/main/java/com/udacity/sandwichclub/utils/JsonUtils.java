package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject data = new JSONObject(json);
        JSONObject nameJSON = data.getJSONObject("name");

        Sandwich sandwich = new Sandwich();
        sandwich.setMainName(nameJSON.getString("mainName"));

        JSONArray alsoKnownAsJSON = nameJSON.getJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = new ArrayList<>();
        for (int i=0;i<alsoKnownAsJSON.length();i++) {
            alsoKnownAs.add(alsoKnownAsJSON.getString(i));
        }
        sandwich.setAlsoKnownAs(alsoKnownAs);

        sandwich.setPlaceOfOrigin(data.getString("placeOfOrigin"));
        sandwich.setDescription(data.getString("description"));
        sandwich.setImage(data.getString("image"));

        JSONArray ingredientsJSON = data.getJSONArray("ingredients");
        List<String> ingredients = new ArrayList<>();
        for (int i=0;i<ingredientsJSON.length();i++) {
            ingredients.add(ingredientsJSON.getString(i));
        }
        sandwich.setIngredients(ingredients);



        return sandwich;
    }
}
