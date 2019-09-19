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

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//        TODO (3): Obter a largura do widget para decisão de qual layout será utilizado
//         atraves do appWidgetmanager



//        TODO (4): Escolher o layout baseado na largura obtida



        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        views.setTextViewText(R.id.widget_title, recipeName);

        Intent updateIngredientsListIntent = new Intent(context, WidgetServiceListIngredients.class);
        views.setRemoteAdapter(R.id.widget_list_view, updateIngredientsListIntent);


//        TODO (5): Caso o layout utilizado contenha a lista de etapas de preparação da receita,
//         a atualizamos com as informações correspondentes


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
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);

//            TODO (6): Notificamos também o adapter da lista de etapas de que suas informações foram atualizadas



            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }
        }
    }


//    TODO (2): Sobrescrever método onAppWidgetOptionsChanged para fazer com que o widget
//     seja sempre atualizado quando suas configurações de tamanho mudarem

}

