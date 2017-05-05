package com.zx.myjpush;

import android.app.Notification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.MultiActionsNotificationBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(MainActivity.this);
        //指定状态栏的显示图标
        builder.statusBarDrawable = R.drawable.icon;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder);

        MultiActionsNotificationBuilder builder2 = new MultiActionsNotificationBuilder(MainActivity.this);
        //添加按钮，参数(按钮图片、按钮文字、扩展数据)
        builder2.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "first", "my_extra1");
        builder2.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "second", "my_extra2");
        builder2.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "third", "my_extra3");
        JPushInterface.setPushNotificationBuilder(2, builder2);

        CustomPushNotificationBuilder builder3 = new CustomPushNotificationBuilder(
                this,
                R.layout.notification,
                R.id.img,
                R.id.txt1,
                R.id.txt2);
        builder3.statusBarDrawable = R.drawable.icon;
        // 指定最顶层状态栏小图标
        builder3.layoutIconDrawable = R.drawable.icon;
        // 指定下拉状态栏时显示的通知图标
//        JPushInterface.setPushNotificationBuilder(3, builder3);
        JPushInterface.setDefaultPushNotificationBuilder(builder3);

    }
}
