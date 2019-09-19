package com.iaz.receitas;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.iaz.receitas.presentation.ui.activities.RecipeActivity;

import static com.iaz.receitas.util.Constants.ACTION_SHOW_NEXT_RECIPE;
import static com.iaz.receitas.util.Constants.ACTION_SHOW_PREVIOUS_RECIPE;

/**
 * Implementation of App Widget functionality.
 */


public class NewAppWidget extends AppWidgetProvider {

    static Long recipeId;
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

        Intent intent = new Intent(context, WidgetServiceRecipeTitle.class);
        views.setRemoteAdapter(R.id.widget_title, intent);

        Intent updateIngredientsListIntent = new Intent(context, WidgetServiceListIngredients.class);
        views.setRemoteAdapter(R.id.widget_list_ingredients, updateIngredientsListIntent);

        if (width > 300) {
            Intent updateStepsListIntent = new Intent(context, WidgetServiceListSteps.class);
            views.setRemoteAdapter(R.id.widget_list_steps, updateStepsListIntent);

//            TODO (2): Replicar as configurações acima para o botão de próxima receita

            if (recipeId <= 0) {
                views.setViewVisibility(R.id.button, View.GONE);
            }
            else {
                views.setViewVisibility(R.id.button, View.VISIBLE);

                Intent broadcastPreviousRecipeIntent = new Intent(context, NewAppWidget.class);
                broadcastPreviousRecipeIntent.setAction(ACTION_SHOW_PREVIOUS_RECIPE);
                PendingIntent broadcastPreviousRecipePendingIntent = PendingIntent.getBroadcast(context, 0, broadcastPreviousRecipeIntent, 0);
                views.setOnClickPendingIntent(R.id.button, broadcastPreviousRecipePendingIntent);
            }

            if (recipeId >= 3) {
                views.setViewVisibility(R.id.button2, View.GONE);
            }
            else {
                views.setViewVisibility(R.id.button2, View.VISIBLE);

                Intent broadcastNextRecipeIntent = new Intent(context, NewAppWidget.class);
                broadcastNextRecipeIntent.setAction(ACTION_SHOW_NEXT_RECIPE);
                PendingIntent broadcastNextRecipePendingIntent = PendingIntent.getBroadcast(context, 0, broadcastNextRecipeIntent, 0);
                views.setOnClickPendingIntent(R.id.button2, broadcastNextRecipePendingIntent);
            }
        }

        Intent appIntent;
        appIntent = new Intent(context, RecipeActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.widget_title, pendingIntent);
        views.setPendingIntentTemplate(R.id.widget_list_ingredients, pendingIntent);

        if (width > 300) {
            views.setPendingIntentTemplate(R.id.widget_list_steps, pendingIntent);
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

//    TODO (5): Remover recipeName
    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Long recipeIdForWidget) {

        if (recipeIdForWidget != null) {
            recipeId = recipeIdForWidget;
        }

        if (recipeId != null) {
            // aqui notificamos o adapter de que os dados correspondentes foram atualizados
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_title);
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

    // TODO (3): Para capturar essa transmissão, sobrescrevemos o método onReceive do Provider e definimos a ação desejada.
    //  No caso, mudar a receita exibida no widget.

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, NewAppWidget.class));

        if (ACTION_SHOW_PREVIOUS_RECIPE.equals(intent.getAction())) {
            NewAppWidget.updateWidgets(context, appWidgetManager, appWidgetIds, recipeId-1);
        }

        // TODO (4): Replique para o botão de próximo

        if (ACTION_SHOW_NEXT_RECIPE.equals(intent.getAction())) {
            NewAppWidget.updateWidgets(context, appWidgetManager, appWidgetIds, recipeId+1);
        }
    }
}

