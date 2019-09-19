package com.iaz.receitas.presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.iaz.receitas.R;
import com.iaz.receitas.database.models.Ingredient;
import com.iaz.receitas.databinding.ItemIngredientBinding;

import java.util.ArrayList;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.IngredientViewHolder> {

    private ArrayList<Ingredient> ingredientsList;
    final private Context context;

    public RecipeIngredientsAdapter(@NonNull Context context, ArrayList<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeIngredientsAdapter.IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemIngredientBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_ingredient, parent, false);

        return new IngredientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientsAdapter.IngredientViewHolder recipeViewHolder, int i) {

        final Ingredient ingredient = ingredientsList.get(i);

        StringBuilder ingredientString = new StringBuilder();

        if (ingredient != null) {
            if (!ingredient.getQuantity().isEmpty()) {
                ingredientString.append(String.format("- %s", ingredient.getQuantity()));
                ingredientString.append(String.format(" %s", ingredient.getMeasure()));
                ingredientString.append(String.format(" de %s", ingredient.getIngredient()));
                recipeViewHolder.binding.tvDescription.setText(ingredientString.toString());
                recipeViewHolder.binding.tvDescription.setTextColor(ContextCompat.getColor(context, R.color.primaryTextColor));

            } else {
                ingredientString.append(String.format("-- %s --", ingredient.getIngredient()).toUpperCase());
                recipeViewHolder.binding.tvDescription.setText(ingredientString.toString());
                recipeViewHolder.binding.tvDescription.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
        }

    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public void setNewList(ArrayList<Ingredient> results) {
        this.ingredientsList = results;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        private final ItemIngredientBinding binding;

        IngredientViewHolder(ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
