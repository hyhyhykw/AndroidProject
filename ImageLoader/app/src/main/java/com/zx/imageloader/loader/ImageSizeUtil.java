package com.zx.imageloader.loader;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created time : 2017/3/9 10:42.
 *
 * @author HY
 */
@SuppressWarnings("WeakerAccess")
public class ImageSizeUtil {

    private ImageSizeUtil() {
        throw new RuntimeException("此类不可被实例化");
    }

    /**
     * 根据需求的宽和高以及图片的宽和高计算SampleSize
     *
     * @param options   图片压缩选项
     * @param reqWidth  需求的宽度
     * @param reqHeight 需求的高度
     * @return SampleSize
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        int width = options.outWidth;
        int height = options.outHeight;
        if (width > reqWidth || height > reqHeight) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);
            inSampleSize = Math.max(widthRadio, heightRadio);
        }
        return inSampleSize;
    }


    /**
     * 根据给定的ImageView确定压缩的图片大小
     *
     * @param imageView imageview
     * @return 图片压缩的大小
     */
    public static ImageSize getImageViewSize(ImageView imageView) {
        ImageSize imageSize = new ImageSize();

        DisplayMetrics metrics = imageView.getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams params = imageView.getLayoutParams();

        int width = imageView.getWidth();//获取ImageView的实际宽度

        if (width <= 0) {
            width = params.width;//获取ImageView在layout中声明的宽度
        }
        if (width <= 0) {
            if (Build.VERSION.SDK_INT >= 16) {
                width = imageView.getMaxWidth();
            } else {
                width = getImageViewFiledValue(imageView, "mMaxWidth");
            }
        }

        if (width <= 0) {
            width = metrics.widthPixels;
        }

        int height = imageView.getHeight();//获取ImageView 的实际高度

        if (height <= 0) {
            height = params.height;//获取layout中声明的高度
        }

        if (height <= 0) {
            // 由于getMaxWidth()需要至少API 16 所以通过反射获取该值
            if (Build.VERSION.SDK_INT >= 16) {
                height = imageView.getMaxHeight();
            } else {
                height = getImageViewFiledValue(imageView, "mMaxHeight");
            }
        }

        if (height <= 0) {
            height = metrics.heightPixels;
        }

        imageSize.width = width;
        imageSize.height = height;
        return imageSize;
    }

    /**
     * 通过反射获取某个属性值
     *
     * @param obj       目标对象
     * @param fieldName 属性名称
     * @return 属性
     */
    private static int getImageViewFiledValue(Object obj, String fieldName) {
        int value = 0;

        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = field.getInt(obj);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return value;
    }


    public static class ImageSize {
        int width;
        int height;
    }
}
