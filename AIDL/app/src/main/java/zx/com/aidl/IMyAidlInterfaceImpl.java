package zx.com.aidl;

import android.os.RemoteException;

/**
 * Created Time: 2017/2/21 17:54.
 *
 * @author HY
 */

public class IMyAidlInterfaceImpl extends IMyAidlInterface.Stub {

    @Override
    public String getContent() throws RemoteException {
        return "10086";
    }
}
