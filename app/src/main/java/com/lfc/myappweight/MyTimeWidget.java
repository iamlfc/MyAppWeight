
package com.lfc.myappweight;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Implementation of App Widget functionality.
 */
public class MyTimeWidget extends AppWidgetProvider {

    Timer timer;
    Context context = null;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // 每次创建桌面小部件会调用 更新周期时间到也会调用
        this.context = context;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_time);
        views.setTextViewText(R.id.tv_time_show, "让开我要开始装逼了!");

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetIds, views);

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000); // 1 秒调用一次

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
//        第一次启动桌面小部件 调用
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
//    当最后一个被删除的时候调用
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
//    删除桌面小部件  调用
    }

    //      时间服务
    public static class MyTimeService extends Service {
        @Override
        public void onStart(Intent intent, int startId) {
            super.onStart(intent, startId);
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.my_widget_time);
            remoteViews.setTextViewText(R.id.tv_time_show, new Date().toLocaleString());
            ComponentName componentName = new ComponentName(this, MyTimeWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            appWidgetManager.updateAppWidget(componentName, remoteViews);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(context, MyTimeService.class);
            context.startService(intent);
        }
    };

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = Message.obtain();
            handler.sendMessage(message);
        }
    };
}

