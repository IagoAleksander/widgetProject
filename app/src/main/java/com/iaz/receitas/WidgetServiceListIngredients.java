package com.iaz.receitas;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;


import com.iaz.receitas.database.AppDatabase;
import com.iaz.receitas.database.models.Ingredient;

import java.util.ArrayList;
import java.util.List;
import static com.iaz.receitas.NewAppWidget.recipeId;

public class WidgetServiceListIngredients extends RemoteViewsService {
    private List<String> ingredientsList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class GridRemoteViewsFactory implements RemoteViewsFactory {

        Context mContext;

        public GridRemoteViewsFactory(Context applicationContext, Intent intent) {
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {

        }


        @Override
        public void onDataSetChanged() {
            ArrayList<String> recipeIngredientsForWidgets = new ArrayList<>();

            AppDatabase mDb = AppDatabase.getInstance(mContext);
            ArrayList<Ingredient> ingredients = new ArrayList<>(mDb.ingredientDao().loadAllIngredientsFromRecipe(recipeId));

            for (Ingredient ingredient : ingredients) {
                StringBuilder ingredientString = new StringBuilder();

                if (!ingredient.getQuantity().isEmpty()) {
                    ingredientString.append(String.format("- %s", ingredient.getQuantity()));
                    ingredientString.append(String.format(" %s", ingredient.getMeasure()));
                    ingredientString.append(String.format(" de %s", ingredient.getIngredient()));
                } else {
                    ingredientString.append(String.format("-- %s --", ingredient.getIngredient()).toUpperCase());
                }

                recipeIngredientsForWidgets.add(ingredientString.toString());
            }

            ingredientsList = recipeIngredientsForWidgets;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {

            RemoteViews views;
            views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_view_item_standard);

//            TODO (3): Popule cada item da lista com um dos ingredientes da receita
//                    usando setTextViewText para ligar essa informação com o TextView
//                    do layout que acabou de ser criado

            views.setTextViewText(R.id.widget_list_view_item, ingredientsList.get(i));

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}