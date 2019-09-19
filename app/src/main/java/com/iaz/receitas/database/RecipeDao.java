package com.iaz.receitas.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.iaz.receitas.database.models.Recipe;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    List<Recipe> loadAllRecipes();

    @Query("SELECT * FROM recipe")
    Single<List<Recipe>> loadAllRecipesSingle();

    @Query("SELECT * FROM recipe WHERE :recipeId == recipe.id")
    Recipe loadRecipe(long recipeId);

    @Query("SELECT * FROM recipe WHERE :recipeId == recipe.id")
    Single<Recipe> loadRecipeSingle(long recipeId);

    @Insert
    long insertRecipe(Recipe recipe);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);
}
