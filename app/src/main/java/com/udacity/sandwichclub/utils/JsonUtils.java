package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = new Sandwich();
        JSONObject sandwichDetails = new JSONObject(json);
        sandwich.setDescription(sandwichDetails.getString("description"));
        sandwich.setImage(sandwichDetails.getString("image"));
        sandwich.setPlaceOfOrigin(sandwichDetails.getString("placeOfOrigin"));
        JSONObject name = sandwichDetails.getJSONObject("name");
        sandwich.setMainName(name.getString("mainName"));
        sandwich.setAlsoKnownAs(convertJSONArrayToList(name.getJSONArray("alsoKnownAs")));
        sandwich.setIngredients(convertJSONArrayToList(sandwichDetails.getJSONArray("ingredients")));
        return  sandwich;
    }

    private static List<String> convertJSONArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        if (jsonArray != null) {
            int len = jsonArray.length();
            for (int i=0;i<len;i++){
                list.add(jsonArray.get(i).toString());
            }
        }
        return list;
    }
    //https://stackoverflow.com/questions/3395729/convert-json-array-to-normal-java-array?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
}
