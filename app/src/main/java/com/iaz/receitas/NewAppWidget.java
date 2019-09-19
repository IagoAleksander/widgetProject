package com.iaz.receitas;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.iaz.receitas.presentation.ui.activities.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 */


public class NewAppWidget extends AppWidgetProvider {

//    TODO (4): Para sabermos qual a receita em questão, precisamos adicionar um identificador recipeId

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


//        TODO (7): Atualizamos então o layout referenciado pela RemoteView
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);


//        TODO (8): E atualizamos as propriedades das views criadas



        Intent intent = new Intent(context, RecipeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }



//    TODO (6) : Como nao precisaremos atualizar o widget de tempos em tempos, o método onUpdate será substituido
//            por outro que aceite o id e o nome da receita como parametros
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

