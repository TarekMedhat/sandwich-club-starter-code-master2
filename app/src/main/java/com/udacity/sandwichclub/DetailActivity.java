package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ActivityDetailBinding mDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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
                .into(mDetailBinding.imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs()!=null&&sandwich.getAlsoKnownAs().size()>0){
            mDetailBinding.alsoKnownTv.append(" " + sandwich.getAlsoKnownAs().get(0));
            for (int i=1;i<sandwich.getAlsoKnownAs().size();i++)
                mDetailBinding.alsoKnownTv.append(", " + sandwich.getAlsoKnownAs().get(i));
        }
        if (sandwich.getIngredients()!=null&&sandwich.getIngredients().size()>0){
            mDetailBinding.ingredientsTv.append(" " + sandwich.getIngredients().get(0));
            for (int i=1;i<sandwich.getIngredients().size();i++)
                mDetailBinding.ingredientsTv.append(", " + sandwich.getIngredients().get(i));
        }

        mDetailBinding.placeOfOriginTv.setText(" " + sandwich.getPlaceOfOrigin());
        mDetailBinding.descriptionTv.setText(sandwich.getDescription());

    }
}
