package com.udacity.sandwichclub;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    LinearLayout descriptionLayout;
    LinearLayout originLayout;
    LinearLayout ingredientsLayout;
    LinearLayout alternativeNamesLayout;

    TextView sandwichDescription;
    TextView sandwichOrigin;
    TextView sandwichIngredients;
    TextView sandwichAlternativeNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(this, json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        descriptionLayout = findViewById(R.id.description_ll);
        originLayout = findViewById(R.id.origin_ll);
        alternativeNamesLayout = findViewById(R.id.also_known_ll);
        ingredientsLayout = findViewById(R.id.ingredients_ll);

        sandwichDescription = findViewById(R.id.description_tv);
        sandwichOrigin = findViewById(R.id.origin_tv);
        sandwichAlternativeNames = findViewById(R.id.also_known_tv);
        sandwichIngredients = findViewById(R.id.ingredients_tv);

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

    private void populateUI(Sandwich sandwich) {
        sandwichDescription.setText(sandwich.getDescription());

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin.isEmpty()) {
            originLayout.setVisibility(View.INVISIBLE);
        } else {
            sandwichOrigin.setText(placeOfOrigin);
        }

        List<String> alternativeNames = sandwich.getAlsoKnownAs();
        if (alternativeNames.isEmpty()) {
            alternativeNamesLayout.setVisibility(View.INVISIBLE);
        } else {
            for (String alternativeName : alternativeNames) {
                sandwichAlternativeNames.append("- " + alternativeName + "\n");
            }
        }

        List<String> ingredients = sandwich.getIngredients();
        if (ingredients.isEmpty()) {
            ingredientsLayout.setVisibility(View.INVISIBLE);
        } else {
            for (String ingredient : sandwich.getIngredients()) {
                sandwichIngredients.append("* " + ingredient + "\n");
            }
        }
    }
}
