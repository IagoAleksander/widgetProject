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
    static Long recipeId;
    static String recipeName;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


//        TODO (7): Atualizamos então o layout referenciado pela RemoteView
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);


//        TODO (8): E atualizamos as propriedades das views criadas
        views.setTextViewText(R.id.widget_title, recipeName);

        Intent updateIngredientsListIntent = new Intent(context, WidgetServiceListIngredients.class);
        views.setRemoteAdapter(R.id.widget_list_view, updateIngredientsListIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    //    TODO (6) : Como nao precisaremos atualizar o widget de tempos em tempos, o método onUpdate será substituido
//            por outro que aceite o id e o nome da receita como parametros
    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Long recipeIdForWidget, String recipeNameForWidget) {

        if (recipeIdForWidget != null) {
            recipeId = recipeIdForWidget;
        }

        if (recipeNameForWidget != null) {
            recipeName = recipeNameForWidget;
        }

        if (recipeId != null) {
            // aqui notificamos o adapter de que os dados correspondentes foram atualizados
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);

            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }
        }
    }
}

