package com.zx.eshop.feature;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.zx.eshop.base.wrapper.ToastWrapper;

/**
 * Created Time: 2017/2/21 17:02.
 *
 * @author HY
 */

public class EShopApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {

            // 这个是用于分析内存的线程，我们不能再这里面初始化我们项目
            return;
        }
        LeakCanary.install(this);

        // 正常的app初始化

        //初始化Toast工具
        ToastWrapper.init(this);
    }
}
