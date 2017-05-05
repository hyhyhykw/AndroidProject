package zx.com.cliet;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import zx.com.aidl.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMyAidlInterface myAidl = IMyAidlInterface.Stub.asInterface(service);
            try {
                Toast.makeText(MainActivity.this, "" + myAidl.getContent(), Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent("com.hy.AIDL");
        Intent service = new Intent(getExplicitIntent(this, intent));

        bindService(Build.VERSION.SDK_INT >= 21 ? service : intent,
                conn, BIND_AUTO_CREATE);

    }


    public static Intent getExplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        //检索可以与给定的意图匹配的所有服务。
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // Make sure only one match was found
        //确定只有一个匹配
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        //获取组件信息并创建ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        //创建一个新的意图。使用旧的意图和这样的重用
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        //将组件设置为显式
        explicitIntent.setComponent(component);
        return explicitIntent;
    }
}
