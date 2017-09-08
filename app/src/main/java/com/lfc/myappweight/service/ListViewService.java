package com.lfc.myappweight.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.lfc.myappweight.MyWidgetTwo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LFC
 * on 2017/9/7.
 */

public class ListViewService extends RemoteViewsService {
    public static final String INITENT_DATA = "extra_data";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewFactory(this.getApplicationContext(), intent);
    }

    private class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context context = null;
        private List<String> mlist = new ArrayList<>();

        public ListRemoteViewFactory(Context context, Intent intent) {
            this.context = context;
        }

        @Override
        public void onCreate() {
        /*    mlist.add("老子天下第一");
            mlist.add("老子天下第二");
            mlist.add("老子天下第三");
            mlist.add("老子天下第四");
            mlist.add("老子天下第五");
            mlist.add("老子天下第六");*/
            mlist.add("一");
            mlist.add("二");
            mlist.add("三");
            mlist.add("四");
            mlist.add("五");
            mlist.add("六");
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            mlist.clear();
        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(context.getPackageName(), android.R.layout.simple_list_item_1);
            views.setTextViewText(android.R.id.text1, "item:" + mlist.get(position));

            Bundle bundle = new Bundle();
            bundle.putInt(ListViewService.INITENT_DATA, position);
            Intent Changeintent = new Intent();
            Changeintent.setAction(MyWidgetTwo.CHANGE_IMAGE);
            Changeintent.putExtras(bundle);
            /* android.R.layout.simple_list_item_1 --- id --- text1
             * listview的item click：将 changeIntent 发送，
             * changeIntent 它默认的就有action 是provider中使用 setPendingIntentTemplate 设置的action*/
            views.setOnClickFillInIntent(android.R.id.text1, Changeintent);

            return views;
        }

        /* 在更新界面的时候如果耗时就会显示 正在加载... 的默认字样，但是你可以更改这个界面
                * 如果返回null 显示默认界面
                * 否则 加载自定义的，返回RemoteViews
                */
        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
