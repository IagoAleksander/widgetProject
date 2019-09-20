package com.iaz.receitas.presentation.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.iaz.receitas.R;
import com.iaz.receitas.database.AppDatabase;
import com.iaz.receitas.database.models.Recipe;
import com.iaz.receitas.databinding.ActivityMainBinding;
import com.iaz.receitas.presentation.ui.adapters.RecipesAdapter;
import com.iaz.receitas.util.Constants;
import com.iaz.receitas.util.MockRecipes;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelectRecipeActivity extends AppCompatActivity {

    private RecipesAdapter recipesAdapter;
    private ActivityMainBinding binding;
    private ArrayList<Recipe> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // recover recipes from the database. If there are no saved recipes in the
        // database, new ones are generated and recovered to be displayed in the adapter

        // Numero de receitas adicionado no shared preferences para configuração do widget
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.pref_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.number_of_recipes), 4);
        editor.apply();

        setRecipesAdapter(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (recipes != null) {
            outState.putParcelableArrayList(Constants.RECIPE_BUNDLE, recipes);
        }
    }

    @SuppressLint("CheckResult")
    private void setRecipesAdapter(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            recipes = savedInstanceState.getParcelableArrayList(Constants.RECIPE_BUNDLE);
            setAdapter(recipes);
        } else {
            // load recipes from database
            AppDatabase mDb = AppDatabase.getInstance(this);
            mDb.recipeDao().loadAllRecipesSingle()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(recipes -> {
                                if (recipes.isEmpty()) {
                                    // if there are no saved recipes, populate the database with new recipes
                                    Completable.fromAction(() ->
                                            MockRecipes.generateRecipes(this))
                                            .subscribeOn(Schedulers.computation())
                                            .subscribe(() -> mDb.recipeDao().loadAllRecipesSingle()
                                                            .subscribeOn(Schedulers.computation())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .subscribe(recipes2 -> setAdapter(new ArrayList<>(recipes2)),
                                                                    Throwable::printStackTrace),
                                                    Throwable::printStackTrace);
                                } else {
                                    setAdapter(new ArrayList<>(recipes));
                                }
                            },
                            Throwable::printStackTrace);
        }
    }

    // populate the adapter with the recovered recipes
    private void setAdapter(ArrayList<Recipe> results) {

        if (recipesAdapter == null)
            recipesAdapter = new RecipesAdapter(SelectRecipeActivity.this, results);
        else {
            recipesAdapter.setNewList(results);
            recipesAdapter.notifyDataSetChanged();
        }

        int columnsNumber = 2;
        binding.recyclerView.setLayoutManager(new GridLayoutManager(SelectRecipeActivity.this, columnsNumber));
        binding.recyclerView.setAdapter(recipesAdapter);
    }
}
