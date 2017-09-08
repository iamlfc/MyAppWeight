package com.lfc.myappweight;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Implementation of App Widget functionality.
 */
public class MyImgShowWeight extends AppWidgetProvider {


    public static AppWidgetManager appWidgetManager;

    //模拟的图片资源
    private static int[] imgs = new int[]{
            R.drawable.img_01,
            R.drawable.img02,
            R.drawable.img03,
            R.drawable.img04,
            R.drawable.girl,
            R.mipmap.ic_launcher
    };
    private static int index;//图片下标
    private RemoteViews remoteViews;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        new MyImgShowWeight().appWidgetManager = appWidgetManager;//管理器对象
        context.startService(new Intent(context, MyService_ImgShow.class));  //启动服务

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_img_show_weight);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 200, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        点击事件
        remoteViews.setOnClickPendingIntent(R.id.img_show_weight, pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.tv_img_show, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        context.stopService(new Intent(context, MyService_ImgShow.class));//停止服务
    }

    public static class MyService_ImgShow extends Service {
        private boolean active = false;//服务运行标识位，优化处理


        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onStart(Intent intent, int startId) {
            super.onStart(intent, startId);
            active = true;
            new Thread() {
                @Override
                public void run() {

                    while (active) {
                        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.my_img_show_weight);
                        remoteViews.setImageViewResource(R.id.img_show_weight, imgs[new Random().nextInt(6)]);
                        ComponentName componentName = new ComponentName(MyService_ImgShow.this, MyImgShowWeight.class);
                        appWidgetManager.updateAppWidget(componentName, remoteViews);
                        try {
                            Thread.sleep(5 * 1000);// 15s刷新一次
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }.start();
        }

        @Override
        public void onCreate() {
            super.onCreate();

        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            active = false;
        }
    }
}

