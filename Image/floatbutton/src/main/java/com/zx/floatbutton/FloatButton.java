package com.zx.floatbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * Created Time: 2017/3/2 13:21.
 *
 * @author HY
 */
public class FloatButton extends RelativeLayout {

//    private int[] resIds = new int[]{
//            R.drawable.ic_add_normal,
//            R.drawable.ic_bluetooth_normal,
//            R.drawable.ic_enhanced_normal,
//            R.drawable.ic_highlight_normal,
//            R.drawable.ic_mail_normal,
//            R.drawable.ic_power_normal,
//            R.drawable.ic_settings_normal,
//            R.drawable.ic_wifi_normal
//    };
//    private Paint mPaint;
//    private DisplayMetrics mMetrics;

    public FloatButton(Context context) {
        this(context, null);
    }

    public FloatButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_float_button2, this);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        Bitmap add = getBitmap(resIds[0]);
//        canvas.drawBitmap(add, add.getWidth(), getWindowHeight() / 2, mPaint);
//    }
//
//    //dp转像素
//    private int getPixel(int dpInt) {
//        return dpInt * getDpi() / 160;
//    }
//
//    //获取屏幕密度
//    private int getDpi() {
//        return mMetrics.densityDpi;
//    }
//
//    //屏幕高度
//    private int getWindowHeight() {
//        return mMetrics.heightPixels;
//    }
//
//    //屏幕宽度
//    private int getWindowWidth() {
//        return mMetrics.widthPixels;
//    }
//
//    private Bitmap getBitmap(int vectorDrawableId) {
//        Bitmap bitmap;
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            Drawable vectorDrawable = getContext().getDrawable(vectorDrawableId);
//            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
//                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas(bitmap);
//            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//            vectorDrawable.draw(canvas);
//        } else {
//            bitmap = BitmapFactory.decodeResource(getResources(), vectorDrawableId);
//        }
//        return bitmap;
//    }

}
