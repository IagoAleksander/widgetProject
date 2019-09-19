package com.iaz.receitas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.iaz.receitas.database.AppDatabase;
import com.iaz.receitas.database.models.Step;

import java.util.ArrayList;
import java.util.List;

import static com.iaz.receitas.NewAppWidget.recipeId;


public class WidgetServiceListSteps extends RemoteViewsService {
    private List<String> stepsList;


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
            ArrayList<Step> steps = new ArrayList<>(mDb.stepDao().loadAllStepsFromRecipe(recipeId));

            for (Step step : steps) {
                if (step.getDescription().isEmpty()) {
                    recipeIngredientsForWidgets.add(String.format("-- %s --", step.getShortDescription()).toUpperCase());
                } else {
                    recipeIngredientsForWidgets.add(String.format("%s", step.getDescription()));
                }
            }

            stepsList = recipeIngredientsForWidgets;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return stepsList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views;

            views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_view_item_standard);
            views.setTextViewText(R.id.widget_list_view_item, stepsList.get(i));

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