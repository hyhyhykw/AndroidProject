package com.zx.easyshop;

import com.feicuiedu.apphx.HxBaseApplication;
import com.feicuiedu.apphx.HxModuleInitializer;
import com.feicuiedu.apphx.model.repository.DefaultLocalInviteRepo;
import com.feicuiedu.apphx.model.repository.DefaultLocalUsersRepo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zx.easyshop.model.CachePreferences;

/**
 * Created Time: 2017/2/14 15:38.
 *
 * @author HY
 */

public class EasyShopApplication extends HxBaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化SharedPreFerence
        CachePreferences.init(this);

        //###############     初始化ImageLoader    ############
        //加载选项
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)//开启硬盘存储
                .cacheInMemory(true)//开启内存缓存
                .resetViewBeforeLoading(true)//使用前重置ImageView
                .build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(4 * 1024 * 1024)//设置内存缓存（4M）
                .defaultDisplayImageOptions(options)//默认加载选项
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    //初始化环信相关
    @Override
    protected void initHxModule(HxModuleInitializer initializer) {
        initializer.setLocalInviteRepo(DefaultLocalInviteRepo.getInstance(this))
                .setLocalUsersRepo(DefaultLocalUsersRepo.getInstance(this))
                .setRemoteUsersRepo(new RemoteUserRepo())
                .init();
    }
}
