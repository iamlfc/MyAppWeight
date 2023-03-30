# MyAppWeight
android 桌面小部件的初步开发

 [![Security Status](https://s.murphysec.com/badge/iamlfc/MyAppWeight.svg)](https://www.murphysec.com/p/iamlfc/MyAppWeight)

  /**
     *   as   File --> new --->widget-->appwidget
     *   as 自动创建了   1  继承与 AppWidgetProvider 的一个类
     *                  2  res-->xml文件夹--> xxx.xml（这个是对小部件的一个配置  其中宽高属性 直接影响在桌面上的大小）
     *                  3   清单文件中 中的 <receiver></receiver> 节点
     *                  4   res -->layout-->xxx.xml 布局文件（显示在桌面上的布局  支持的控件有限）
     
     
     
     
     *    简单设置 ：     mywidgetone
     *                  1   修改布局文件 设置为自己需求的布局
     *                  2   创建 RemoteViews  传入 参数（包名，显示的布局id）
     *                  3   设置参数  setTextViewText（）  setImageViewResource（）等
     *                  4    更新设置的参数 appWidgetManager.updateAppWidget(appWidgetId, views);
     
     
     
     *    点击事件
     *
     *                  1   intent的创建
     *                  2    pendingintent的创建    使用  参数 PendingInte是nt.FLAG_CANCEL_CURRENT
     *                  3     设置监听  views.setOnClickPendingIntent(id, pendingIntent);
     *                  4    更新设置的参数  appWidgetManager.updateAppWidget(appWidgetId, views);
     
     
     
     
     *
     *   定时或者异步操作
     *                   1  设置服务  继承与 Service
     *                   2   在服务中 设置展示结果      同样是 简单设置中的  流程  2,3,4
     
     
     
     *
     *    列表的操作    （这里用 listview ， recyclerview 可能不支持 ）
     *                     1  设置服务  继承与 Service
     *                     2   创建 RemoteViewsFactory  继承与RemoteViewsService.RemoteViewsFactory（相当于 listview的适配器）
     *                     3   AppWidgetProvider 中  设置 onReceive()方法  RemoteViewsFactory 和 AppWidgetProvider 通过广播传递消息
     *
     */
