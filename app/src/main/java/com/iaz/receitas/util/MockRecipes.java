package com.iaz.receitas.util;

import android.content.Context;

import com.iaz.receitas.database.AppDatabase;
import com.iaz.receitas.database.models.Ingredient;
import com.iaz.receitas.database.models.Recipe;
import com.iaz.receitas.database.models.Step;

import java.util.Arrays;
import java.util.List;

public class MockRecipes {

    public static void generateRecipes(Context context) {

        AppDatabase mDb = AppDatabase.getInstance(context);

        Recipe recipe = new Recipe(0, "8", "Bolo de Limao");
        long recipeId = mDb.recipeDao().insertRecipe(recipe);

        List<Ingredient> ingredients = Arrays.asList(new Ingredient("", "", "Bolo", recipeId),
                new Ingredient("2", "", "ovos", recipeId),
                new Ingredient("1", "xicara", "leite", recipeId),
                new Ingredient("3", "colheres(sopa)", "manteiga", recipeId),
                new Ingredient("1 + 1/2", "xicara", "açúcar", recipeId),
                new Ingredient("2", "", "limoes", recipeId),
                new Ingredient("3 + 1/2", "xicaras", "farinha de trigo", recipeId),
                new Ingredient("2", "colheres(sopa)", "fermento em po", recipeId),
                new Ingredient("", "", "Calda", recipeId),
                new Ingredient("2", "colheres(sopa)", "manteiga", recipeId),
                new Ingredient("1", "lata", "leite condensado", recipeId),
                new Ingredient("1/2", "", "limao", recipeId));

        for (Ingredient ingredient : ingredients) {
            mDb.ingredientDao().insertIngredient(ingredient);
        }


        List<Step> steps = Arrays.asList(new Step("", "Bolo", recipeId),
                new Step("Separe as claras das gemas e reserve as claras.", "", recipeId),
                new Step("Em um liquidificador, bata as gemas, o leite, a manteiga e o açúcar.", "", recipeId),
                new Step("Em uma tigela, acrescente o suco de dois limões e as raspas à mistura.", "", recipeId),
                new Step("Acrescente a farinha de trigo aos poucos.", "", recipeId),
                new Step("Bata as claras em neve e adicione à massa.", "", recipeId),
                new Step("Acrescente o fermento em pó.", "", recipeId),
                new Step("Leve ao forno preaquecido a 180º C por 40 minutos.", "", recipeId),
                new Step("", "Calda", recipeId),
                new Step("Em uma frigideira, misture a manteiga, o leite condensado e o creme de leite.", "", recipeId),
                new Step("Quando estiver bem misturado, desligue o fogo e acrescente o suco de meio limão.", "", recipeId),
                new Step("Despeje a calda sobre o bolo desenformado.", "", recipeId),
                new Step("Finalize com raspas de limão.", "", recipeId));

        for (Step step : steps) {
            mDb.stepDao().insertStep(step);
        }

        recipe = new Recipe(1, "12", "Cocada");
        recipeId = mDb.recipeDao().insertRecipe(recipe);

        ingredients = Arrays.asList(new Ingredient("2", "xicaras", "coco fresco ralado", recipeId),
                new Ingredient("1", "lata", "leite condensado", recipeId),
                new Ingredient("2", "xícaras", "açúcar", recipeId),
                new Ingredient("1", "colher(sopa)", "manteiga", recipeId));

        for (Ingredient ingredient : ingredients) {
            mDb.ingredientDao().insertIngredient(ingredient);
        }


        steps = Arrays.asList(new Step("Unte uma pedra de mármore ou uma assadeira com um pouco de óleo.", "", recipeId),
                new Step("Misture em uma panela o coco, o leite condensado e o açúcar.", "", recipeId),
                new Step("Leve ao fogo e misture bem, cozinhe misturando regularmente com uma colher de pau.", "", recipeId),
                new Step("Quando o doce começar a ser soltar do fundo da panela, retire do fogo e acrescente a manteiga.", "", recipeId),
                new Step("Bata bem com a colher de pau para que o doce açucare.", "", recipeId),
                new Step("Despeje sobre o mármore untado e deixe esfriar.", "", recipeId),
                new Step("Corte quadrados de 10 x 10 cm.", "", recipeId));

        for (Step step : steps) {
            mDb.stepDao().insertStep(step);
        }

        recipe = new Recipe(2, "26", "Sonho com doce de leite");
        recipeId = mDb.recipeDao().insertRecipe(recipe);

        ingredients = Arrays.asList(new Ingredient("", "", "Massa", recipeId),
                new Ingredient("5", "xícaras(chá)", "farinha de trigo", recipeId),
                new Ingredient("3", "colheres(sopa)", "açúcar", recipeId),
                new Ingredient("1", "envelope(10g)", "fermento biológico seco", recipeId),
                new Ingredient("3", "colheres(sopa)", "manteiga", recipeId),
                new Ingredient("1", "pitada", "sal", recipeId),
                new Ingredient("2", "", "ovos", recipeId),
                new Ingredient("1", "xícara(chá)", "leite morno", recipeId),
                new Ingredient("", "", "Recheio", recipeId),
                new Ingredient("1", "lata", "doce de leite", recipeId));

        for (Ingredient ingredient : ingredients) {
            mDb.ingredientDao().insertIngredient(ingredient);
        }

        steps = Arrays.asList(new Step("Em uma tigela, colocar a farinha de trigo, o açúcar e o fermento, misturando bem e acrescentando os demais ingredientes aos poucos.", "", recipeId),
                new Step("Passar para uma bancada enfarinhada.", "", recipeId),
                new Step("Sovar a massa até ficar lisinha e não grudar.", "", recipeId),
                new Step("Colocar novamente na tigela, cobrir com um pano ou filme plástico e deixar descansar por 20 minutos.", "", recipeId),
                new Step("Após esse tempo, abrir a massa com rolo, não deixando muito fina.", "", recipeId),
                new Step("Cortar com a boca de um copo americano.", "", recipeId),
                new Step("Deixar descansar por 20 minutos em uma forma enfarinhada.", "", recipeId),
                new Step("Fritar em óleo não muito quente aos poucos.", "", recipeId),
                new Step("Esperar esfriar, cortar ao meio e rechear.", "", recipeId),
                new Step("Polvilhar o açúcar de confeiteiro e servir.", "", recipeId));

        for (Step step : steps) {
            mDb.stepDao().insertStep(step);
        }

        recipe = new Recipe(3, "10", "Pudim de tapioca");
        recipeId = mDb.recipeDao().insertRecipe(recipe);

        ingredients = Arrays.asList(new Ingredient("1/2", "xícara", "tapioca granulada", recipeId),
                new Ingredient("1/2", "litro", "leite", recipeId),
                new Ingredient("1/2", "garrafa(pequena)", "leite de coco", recipeId),
                new Ingredient("1", "pacote(50g)", "coco ralado", recipeId),
                new Ingredient("1", "lata", "leite condensado", recipeId),
                new Ingredient("3", "", "ovos", recipeId));

        for (Ingredient ingredient : ingredients) {
            mDb.ingredientDao().insertIngredient(ingredient);
        }

        steps = Arrays.asList(new Step("Levar ao fogo o leite e a tapioca, fazer um mingau e deixar esfriar por completo (ficará como se fosse um grude, mas é normal).", "", recipeId),
                new Step("Acrescentar o leite condensado e o coco ralado.", "", recipeId),
                new Step("Bater os ovos no liquidificador com o leite de coco e misturar com o restante.", "", recipeId),
                new Step("Colocar novamente na tigela, cobrir com um pano ou filme plástico e deixar descansar por 20 minutos.", "", recipeId),
                new Step("Caramelar a forma e assar em banho-maria por aproximadamente 40 minutos.", "", recipeId));

        for (Step step : steps) {
            mDb.stepDao().insertStep(step);
        }
    }
}
