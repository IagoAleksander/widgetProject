package com.iaz.receitas.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.iaz.receitas.database.models.Ingredient;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM ingredient WHERE :recipeId == ingredient.recipeId ORDER BY id")
    List<Ingredient> loadAllIngredientsFromRecipe(long recipeId);

    @Query("SELECT * FROM ingredient WHERE :recipeId == ingredient.recipeId ORDER BY id")
    Single<List<Ingredient>> loadAllIngredientsFromRecipeSingle(long recipeId);

    @Insert
    void insertIngredient(Ingredient ingredient);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateIngredient(Ingredient ingredient);

    @Delete
    void deleteIngredient(Ingredient ingredient);
}
