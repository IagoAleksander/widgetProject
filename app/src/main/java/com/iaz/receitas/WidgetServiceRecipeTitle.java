package com.iaz.receitas;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.iaz.receitas.database.AppDatabase;
import com.iaz.receitas.database.models.Recipe;

import static com.iaz.receitas.NewAppWidget.recipeId;

public class WidgetServiceRecipeTitle extends RemoteViewsService {
    private String title;


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

            AppDatabase mDb = AppDatabase.getInstance(mContext);
            Recipe recipe = mDb.recipeDao().loadRecipe(recipeId);
            title = recipe.getName();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_title_item);

            views.setTextViewText(R.id.widget_title_item, title);

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
