package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.stream.Collectors;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    //View Holders
    private Sandwich sandwich = null;
    private ImageView ingredientsIv = null;
    private TextView originTv = null;
    private TextView aliasTv = null;
    private TextView descTv = null;
    private TextView ingTv  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Initialize the View Holders
        ingredientsIv = findViewById(R.id.image_iv);
        originTv      = findViewById(R.id.origin_tv);
        aliasTv       = findViewById(R.id.also_known_tv);
        descTv        = findViewById(R.id.description_tv);
        ingTv         = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = DEFAULT_POSITION;

        try {
             position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
        catch (NullPointerException e){
            closeOnError();
        }
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        //Hide the image completely and show it if images gets loaded.
        ingredientsIv.setVisibility(View.GONE);
        // Load image and any error hide the image view
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv, new Callback() {
                    @Override
                    public void onSuccess() {
                        ingredientsIv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        ingredientsIv.setVisibility(View.GONE);
                    }
                });


        originTv.setText(sandwich.getPlaceOfOrigin());
        //List of Also known as Names concatenated using commas
        aliasTv.setText(TextUtils.join(",", sandwich.getAlsoKnownAs()));
        descTv.setText(sandwich.getDescription() );
        //List of Ingredients concatenated using commas
        ingTv.setText(TextUtils.join(",", sandwich.getIngredients()));




    }
}
