package com.udacity.android.podcastbemine;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.udacity.android.podcastbemine.utils.Constant;

/**
 * Implementation of App Widget functionality.
 */
public class PodcastPlayerWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.podcast_player_widget);
        try {
            // Construct the RemoteViews object
            SharedPreferences pref = context.getSharedPreferences(Constant.WIDGET_PREFERENCE, 0);
            String title = pref.getString(Constant.WIDGET_LABEL, null);
            String description = pref.getString(Constant.WIDGET_LABEL_2, null);
            if (title == null) {
                title = "No title available";
            }
            if (description == null) {
                description = "No description available";
            }
            views.setTextViewText(R.id.widget_text_title, title);
            views.setTextViewText(R.id.widget_text, description);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        } catch (Exception e) {
            Log.e(Constant.WIDGET_ERROR_TAG, e.getStackTrace().toString());
            views.setTextViewText(R.id.widget_text, Constant.WIDGET_ERROR);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

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

