package zx.com.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created Time: 2017/2/21 17:56.
 *
 * @author HY
 */

public class MyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IMyAidlInterfaceImpl();
    }
}
