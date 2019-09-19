package com.iaz.receitas.presentation.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.iaz.receitas.R;
import com.iaz.receitas.database.models.Recipe;
import com.iaz.receitas.databinding.ItemRecipeBinding;
import com.iaz.receitas.presentation.ui.activities.RecipeActivity;
import com.iaz.receitas.util.Constants;

import java.util.ArrayList;

import static com.iaz.receitas.util.Constants.RECIPE_NAME;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> recipesList;
    private final Context context;

    public RecipesAdapter(@NonNull Context context, ArrayList<Recipe> recipesList) {

        this.context = context;
        this.recipesList = recipesList;
    }

    @NonNull
    @Override
    public RecipesAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRecipeBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.RecipeViewHolder recipeViewHolder, int i) {

        final Recipe recipe = recipesList.get(i);

        if (recipe.getName() != null && !recipe.getName().isEmpty()) {
            recipeViewHolder.binding.tvName.setText(recipe.getName());
            recipeViewHolder.binding.tvName.setVisibility(View.VISIBLE);
        } else {
            recipeViewHolder.binding.tvName.setVisibility(View.GONE);
        }

        if (recipe.getServings() != null && !recipe.getServings().isEmpty()) {
            recipeViewHolder.binding.tvServings.setText(String.format("%s %s", context.getString(R.string.servings), recipe.getServings()));
            recipeViewHolder.binding.tvServings.setVisibility(View.VISIBLE);
        } else {
            recipeViewHolder.binding.tvServings.setVisibility(View.GONE);
        }

        recipeViewHolder.binding.cvRecipe.setOnClickListener(view -> {

            Intent intent = new Intent(context, RecipeActivity.class);
            intent.putExtra(Constants.RECIPE_ID, recipe.getId());
            intent.putExtra(RECIPE_NAME, recipe.getName());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public void setNewList(ArrayList<Recipe> results) {
        this.recipesList = results;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecipeBinding binding;

        RecipeViewHolder(ItemRecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
