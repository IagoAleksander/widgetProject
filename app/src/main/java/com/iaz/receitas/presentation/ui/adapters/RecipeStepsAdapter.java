package com.iaz.receitas.presentation.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.iaz.receitas.R;
import com.iaz.receitas.database.models.Step;
import com.iaz.receitas.databinding.ItemStepBinding;

import java.util.ArrayList;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.StepViewHolder> {

    private ArrayList<Step> stepsList;
    final private Context context;

    public RecipeStepsAdapter(@NonNull Context context, ArrayList<Step> stepsList) {
        this.stepsList = stepsList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeStepsAdapter.StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemStepBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_step, parent, false);


        return new StepViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsAdapter.StepViewHolder recipeViewHolder, int i) {

        final Step step = stepsList.get(i);

        if (step.getDescription().isEmpty()) {
            recipeViewHolder.binding.tvDescription.setText(String.format("-- %s --", step.getShortDescription()).toUpperCase());
            recipeViewHolder.binding.tvDescription.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else {
            recipeViewHolder.binding.tvDescription.setText(String.format("%s", step.getDescription()));
            recipeViewHolder.binding.tvDescription.setTextColor(ContextCompat.getColor(context, R.color.primaryTextColor));
        }
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public void setNewList(ArrayList<Step> results) {
        this.stepsList = results;
    }

    class StepViewHolder extends RecyclerView.ViewHolder {
        private final ItemStepBinding binding;

        StepViewHolder(ItemStepBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
