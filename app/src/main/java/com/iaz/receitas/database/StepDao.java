package com.iaz.receitas.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.iaz.receitas.database.models.Step;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface StepDao {

    @Query("SELECT * FROM step WHERE :recipeId == step.recipeId ORDER BY id")
    List<Step> loadAllStepsFromRecipe(long recipeId);

    @Query("SELECT * FROM step WHERE :recipeId == step.recipeId ORDER BY id")
    Single<List<Step>> loadAllStepsFromRecipeSingle(long recipeId);

    @Insert
    void insertStep(Step step);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateStep(Step step);

    @Delete
    void deleteStep(Step step);
}
