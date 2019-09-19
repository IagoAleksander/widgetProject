package com.iaz.receitas;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.iaz.receitas.presentation.ui.activities.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 */


public class NewAppWidget extends AppWidgetProvider {

    static Long recipeId;
    static String recipeName;
    static int width;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);

        RemoteViews views;

        if (width < 300)
            views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        else
            views = new RemoteViews(context.getPackageName(), R.layout.widget_layout_big);

        views.setTextViewText(R.id.widget_title, recipeName);

        Intent updateIngredientsListIntent = new Intent(context, WidgetServiceListIngredients.class);
        views.setRemoteAdapter(R.id.widget_list_ingredients, updateIngredientsListIntent);

        if (width > 300) {
            Intent updateStepsListIntent = new Intent(context, WidgetServiceListSteps.class);
            views.setRemoteAdapter(R.id.widget_list_steps, updateStepsListIntent);
        }

//        TODO (1): Crie um intent para abrir a tela de detalhes da receita quando clicado
//                encapsule o intent em um PendingIntent e ligue uma collectionView com esse PendingIntent
//                utilizando um PendingIntentTemplate. Faremos isso para as listas de ingredientes e modo de preparo.



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Long recipeIdForWidget, String recipeNameForWidget) {

        if (recipeIdForWidget != null) {
            recipeId = recipeIdForWidget;
        }

        if (recipeNameForWidget != null) {
            recipeName = recipeNameForWidget;
        }

        if (recipeId != null) {
            // aqui notificamos o adapter de que os dados correspondentes foram atualizados
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_ingredients);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_steps);

            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        updateAppWidget(context, appWidgetManager, appWidgetId);
    }
}

