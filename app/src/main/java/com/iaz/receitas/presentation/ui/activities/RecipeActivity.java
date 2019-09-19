package com.iaz.receitas.presentation.ui.activities;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.iaz.receitas.NewAppWidget;
import com.iaz.receitas.R;
import com.iaz.receitas.database.AppDatabase;
import com.iaz.receitas.database.models.Ingredient;
import com.iaz.receitas.database.models.Step;
import com.iaz.receitas.databinding.ActivityRecipeBinding;
import com.iaz.receitas.presentation.ui.adapters.RecipeIngredientsAdapter;
import com.iaz.receitas.presentation.ui.adapters.RecipeStepsAdapter;

import java.security.Provider;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.iaz.receitas.util.Constants.RECIPE_ID;
import static com.iaz.receitas.util.Constants.RECIPE_NAME;

public class RecipeActivity extends AppCompatActivity {

    private long recipeId;
    private String recipeName;
    private ActivityRecipeBinding binding;
    private RecipeIngredientsAdapter ingredientsAdapter;
    private RecipeStepsAdapter stepsAdapter;
    private AppDatabase mDb;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);

        mDb = AppDatabase.getInstance(this);

        // recover selected recipe id, name
        if (getIntent().getExtras() != null) {
            recipeId = getIntent().getExtras().getLong(RECIPE_ID);
            recipeName = getIntent().getExtras().getString(RECIPE_NAME);
        }

        // set recipe name as action bar title
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(recipeName);

        // recover ingredients of chosen recipe from database to list
        // (rxJava used to access database from computation thread)
        mDb.ingredientDao().loadAllIngredientsFromRecipeSingle(recipeId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipes -> {

                        setAdapterIngredients(new ArrayList<>(recipes));

                });

//        TODO (5): E passar o identificador para o Provider

    }

    @SuppressLint("CheckResult")
    private void setAdapterIngredients(ArrayList<Ingredient> ingredients) {

        // update headers to indicate that what are being shown are the ingredients
        binding.tvRecipeStepsHeader.setText(getString(R.string.recipe_ingredients_header));
        binding.btRecipeSteps.setText(getString(R.string.recipe_steps_button));
        binding.btRecipeSteps.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_navigate_next_black, 0);

        // if the adapter was not instantiated yet, do it, otherwise just update the current one
        if (ingredientsAdapter == null)
            ingredientsAdapter = new RecipeIngredientsAdapter(this, ingredients);
        else {
            ingredientsAdapter.setNewList(ingredients);
            ingredientsAdapter.notifyDataSetChanged();
        }

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(ingredientsAdapter);

        // if the button to show steps is clicked, the current recipe's correspondent
        // ones are recovered from database and displayed in a list
        // (rxJava used to access database from computation thread)
        binding.btRecipeSteps.setOnClickListener(view ->
                mDb.stepDao().loadAllStepsFromRecipeSingle(recipeId)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(steps -> {

                            setAdapterSteps(new ArrayList<>(steps));

                        }));
    }

    @SuppressLint("CheckResult")
    private void setAdapterSteps(ArrayList<Step> steps) {

        // update headers to indicate that what are being shown are the recipe steps
        binding.tvRecipeStepsHeader.setText(getString(R.string.recipe_steps_header));
        binding.btRecipeSteps.setText(getString(R.string.recipe_ingredients_button));
        binding.btRecipeSteps.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_navigate_before_black, 0, 0, 0);

        // if the adapter was not instantiated yet, do it, otherwise just update the current one
        if (stepsAdapter == null)
            stepsAdapter = new RecipeStepsAdapter(this, steps);
        else {
            stepsAdapter.setNewList(steps);
            stepsAdapter.notifyDataSetChanged();
        }

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(stepsAdapter);

        // if the button to show ingredients is clicked, the current recipe's correspondent
        // ones are recovered from database and displayed in a list
        // (rxJava used to access database from computation thread)
        binding.btRecipeSteps.setOnClickListener(view ->
                mDb.ingredientDao().loadAllIngredientsFromRecipeSingle(recipeId)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(recipes -> {

                            setAdapterIngredients(new ArrayList<>(recipes));

                        }));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, SelectRecipeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
