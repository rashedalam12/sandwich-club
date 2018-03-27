package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    private TextView alsoKnownTextView;
    private TextView originTextView;
    private TextView descriptionTextView;
    private TextView ingredientsTextView;
    private TextView alsoKnownLabelTextView;
    private TextView originLabelTextView;
    private TextView descriptionLabelTextView;
    private TextView ingredientsLabelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownTextView = (TextView) findViewById(R.id.also_known_tv);
        originTextView = (TextView) findViewById(R.id.origin_tv);
        descriptionTextView = (TextView) findViewById(R.id.description_tv);
        ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);

        alsoKnownLabelTextView = (TextView) findViewById(R.id.also_known_label_tv);
        originLabelTextView = (TextView) findViewById(R.id.origin_label_tv);
        descriptionLabelTextView = (TextView) findViewById(R.id.description_label_tv);
        ingredientsLabelTextView = (TextView) findViewById(R.id.ingredients_label_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich ) {

        if (sandwich.getAlsoKnownAs().isEmpty()){
            alsoKnownTextView.setVisibility(View.GONE);
            alsoKnownLabelTextView.setVisibility(View.GONE);
        } else {
            alsoKnownTextView.setText(listToString(sandwich.getAlsoKnownAs()));
            // alsoKnownTextView.setText(sandwich.getAlsoKnownAs().toString()); // With bracket
        }

        if(sandwich.getPlaceOfOrigin().equals("") || sandwich.getPlaceOfOrigin() == null){
            originLabelTextView.setVisibility(View.GONE);
            originTextView.setVisibility(View.GONE);
        }else {
            originTextView.setText(sandwich.getPlaceOfOrigin());
        }

        if(sandwich.getDescription().equals("") || sandwich.getDescription() == null){
            descriptionTextView.setVisibility(View.GONE);
            descriptionLabelTextView.setVisibility(View.GONE);
        }else {
            descriptionTextView.setText(sandwich.getDescription());
        }

        if(sandwich.getIngredients().isEmpty()){
            ingredientsTextView.setVisibility(View.GONE);
            ingredientsLabelTextView.setVisibility(View.GONE);
        }else {
            ingredientsTextView.setText(listToString(sandwich.getIngredients()));
            //ingredientsTextView.setText(sandwich.getIngredients().toString());
        }
    }

    private String listToString(List<String> list){
         StringBuilder builder = new StringBuilder();
        for(String in : list){
            builder.append(in + ", ");
        }
        return builder.substring(0, builder.length() - 2);
    }

}
