package com.lfc.myappweight;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.lfc.myappweight.demo.ListViewService;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidgetTwo extends AppWidgetProvider {
    //    广播的动作
    public static final String CHANGE_IMAGE = "com.lfc.myappweight.action.CHANGE_IMAGE";

    private RemoteViews remoteViews;
    private ComponentName componentName;


    private int[] imgs = new int[]{
            R.drawable.img_01,
            R.drawable.img02,
            R.drawable.img03,
            R.drawable.img04,
            R.drawable.girl,
            R.mipmap.ic_launcher
    };

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget_two);
        remoteViews.setImageViewResource(R.id.iv_test, R.drawable.girl);
        remoteViews.setTextViewText(R.id.btn_test, "来吧宝贝！");
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 200, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        点击事件
        remoteViews.setOnClickPendingIntent(R.id.btn_test, pendingIntent);
// 设置 ListView 的adapter。
        // (01) intent: 对应启动 ListViewService(RemoteViewsService) 的intent
        // (02) setRemoteAdapter: 设置 ListView 的适配器
        // 通过setRemoteAdapter将 ListView 和ListViewService关联起来，
        // 以达到通过 GridWidgetService 更新 gridview 的目的
        Intent lvIntent = new Intent(context, ListViewService.class);
        remoteViews.setRemoteAdapter(R.id.lv_test, lvIntent);
        remoteViews.setEmptyView(R.id.lv_test,android.R.id.empty);
    /*
         * setPendingIntentTemplate 设置pendingIntent 模板
         * setOnClickFillInIntent   可以将fillInIntent 添加到pendingIntent中
         */
        Intent toIntent = new Intent(CHANGE_IMAGE);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 200, toIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.lv_test, pendingIntent1);


//        更新视图
        componentName = new ComponentName(context, MyWidgetTwo.class);
        appWidgetManager.updateAppWidget(componentName, remoteViews);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (TextUtils.equals(CHANGE_IMAGE, intent.getAction())) {

            Bundle bundle = intent.getExtras();
            int index = bundle.getInt(com.lfc.myappweight.demo.ListViewService.INITENT_DATA);

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget_two);
            remoteViews.setImageViewResource(R.id.iv_test, imgs[index]);

            //        更新视图
            componentName = new ComponentName(context, MyWidgetTwo.class);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);


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

