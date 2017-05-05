package com.zx.imageshow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 可以缩放和移动的ImageView
 * <p>
 * 实现功能如下：
 * 1.初始化时图片居中垂直显示，拉伸图片至ImageView宽度
 * 2.使用两根手指放大缩小图片，可以设置最大放大倍数，
 * 当图片小于ImageVIew宽度时，手指离开屏幕会恢复到ImageView宽度
 * 3.支持双击缩小和放大。当图片位放大时，双击放大到指定倍数，
 * 当图片处于放大状态时，双击恢复到未放大状态
 * 4.图片拖动效果：
 * a.当图片处于未放大状态时，不可被拖动；
 * b.当放大后的高度不超过ImageView时，不可垂直拖动（由于默认拉伸宽度，宽度可以不判断）；
 * c.当图片向右拖动时，若左边超出左边界，则不可再拖动，上下拖动同理
 * </p>
 *
 * @author HY
 */
@SuppressWarnings("unused")
public class MatrixImageView extends AppCompatImageView {

    private static final String TAG = MatrixImageView.class.getSimpleName();

    /**
     * 手势识别类
     * View类有个View.OnTouchListener内部接口，
     * 通过重写他的onTouch(View v, MotionEvent event)方法，
     * 我们可以处理一些touch事件，但是这个方法太过简单，
     * 如果需要处理一些复杂的手势，用这个接口就会很麻烦
     * 由于用到了双击缩放效果，在此引用了手势类 GestureDetector，
     * GestureListener就是继承与SimpleOnGestureListener的手势监听类了。
     * MatrixTouchListener继承与onTouchListner，是Touch事件监听的主体。
     * 在构造函数最后一句setScaleType(ScaleType.FIT_CENTER)，便是满足功能一的要点。
     */
    private GestureDetector mGestureDetector;

    /* 模版Matrix，用以初始化 */
    private Matrix mMatrix = new Matrix();

    /* 图片的宽度和高度 */
    private float mImgWidth;
    private float mImgHeight;


    public MatrixImageView(Context context) {
        this(context, null);
    }

    public MatrixImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context 用于获取属性值
     */
    private void init(Context context) {
        MatrixTouchListener listener = new MatrixTouchListener();
        setOnTouchListener(listener);
        // TODO: 2017/3/9
        mGestureDetector = new GestureDetector(context, new GestureListener(listener));
        setBackgroundColor(Color.BLACK);
        setScaleType(ScaleType.FIT_CENTER);
    }

    //####################################      触摸事件实现  start     #################################
    @SuppressWarnings("JavaDoc")
    private class MatrixTouchListener implements View.OnTouchListener {


        /* 拖拉照片模式 */
        private static final int MODE_DRAG = 1;

        /* 缩放照片模式 */
        private static final int MODE_ZOOM = 2;

        /* 不支持Matrix */
        private static final int MODE_UNABLE = 3;

        private int mMode = 0;//模式

        /* 最大缩放级别 */
        private float mMaxScale = 6;

        /* 双击时的缩放级别 */
        private float mDoubleClickScale = 2;

        /* 缩放开始时的手指间距 */
        private float mStartDis;

        /* 当前的Matrix */
        private Matrix mCurrentMatrix = new Matrix();

        /* 用于记录开始时的坐标位置 */
        private PointF mStartPoint = new PointF();

        /**
         * 在ACTION_DOWN和ACTION_POINTER_DOWN中主要是进行当前事件模式的确定。
         * 当我们按下一个点时，会触发Down事件，而按下第二个点后，又会触发Action_Pointer_Down事件，
         * 在此我们把按下一个点标记为拖动事件，按下两个点标记为缩放事件。
         */
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    mMode = MODE_DRAG;//设置为拖动模式
                    mStartPoint.set(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_POINTER_UP://抬起和取消
                case MotionEvent.ACTION_CANCEL:
                    reSetMatrix();
                    break;
                case MotionEvent.ACTION_HOVER_MOVE://移动
                    if (mMode == MODE_ZOOM) {
                        setZoomMatrix(event);
                    } else if (mMode == MODE_DRAG) {
                        setDragMatrix(event);
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN://多点触摸
                    if (mMode == MODE_UNABLE) return true;
                    mMode = MODE_ZOOM;//切换为缩放模式
                    mStartDis = distance(event);
                    break;
                default:
                    break;
            }
            return mGestureDetector.onTouchEvent(event);
        }

        /**
         * 计算两指间的距离
         *
         * @param event 触摸事件
         * @return 两指间的距离
         */
        private float distance(MotionEvent event) {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
        /* 使用勾股定理返回两点间的距离 */
            return (float) Math.sqrt(dx * dx + dy * dy);
        }

        /**
         * 图片拖动效果
         *
         * @param event 触摸事件
         */
        private void setDragMatrix(MotionEvent event) {
            if (isZoomChanged()) {
                float dx = event.getX() - mStartPoint.x;//得到X轴的移动距离
                float dy = event.getY() - mStartPoint.y;//得到Y轴的移动距离
                //避免和双击冲突，大于10像素才算拖动
                if (Math.sqrt(dx * dx + dy * dy) > 10f) {
                    mStartPoint.set(event.getX(), event.getY());
                    //在当前基础上移动
                    mCurrentMatrix.set(getImageMatrix());
                    float values[] = new float[9];
                    mCurrentMatrix.getValues(values);
                    dx = checkDxBound(values, dx);
                    dy = checkDyBound(values, dy);
                    mCurrentMatrix.postTranslate(dx, dy);
                    setImageMatrix(mCurrentMatrix);
                }

            }
        }

        /**
         * 和当前矩阵对比，检验dx，使图像移动后不会超出ImageView边界
         *
         * @param values
         * @param dx
         * @return
         */
        private float checkDxBound(float[] values, float dx) {
            float width = getWidth();
            if (mImgWidth * values[Matrix.MSCALE_X] < width)
                return 0;
            if (values[Matrix.MTRANS_X] + dx > 0)
                dx = -values[Matrix.MTRANS_X];
            else if (values[Matrix.MTRANS_X] + dx < -(mImgWidth * values[Matrix.MSCALE_X] - width))
                dx = -(mImgWidth * values[Matrix.MSCALE_X] - width) - values[Matrix.MTRANS_X];
            return dx;
        }

        /**
         * 和当前矩阵对比，检验dy，使图像移动后不会超出ImageView边界
         *
         * @param values
         * @param dy
         * @return
         */
        private float checkDyBound(float[] values, float dy) {
            float height = getHeight();
            if (mImgHeight * values[Matrix.MSCALE_Y] < height)
                return 0;
            if (values[Matrix.MTRANS_Y] + dy > 0)
                dy = -values[Matrix.MTRANS_Y];
            else if (values[Matrix.MTRANS_Y] + dy < -(mImgHeight * values[Matrix.MSCALE_Y] - height))
                dy = -(mImgHeight * values[Matrix.MSCALE_Y] - height) - values[Matrix.MTRANS_Y];
            return dy;
        }

        /**
         * 设置缩放的Matrix
         * 首先我们判断是否点击了两个点，如果不是直接返回。
         * 接着，使用distance方法计算出两个点之间的距离。
         * 之前在Action_Pointer_Down中已经计算出了初始距离，
         * 这里计算出的是移动后的两个点的距离。
         * 通过这两个距离我们可以得出本次移动的缩放倍数，但这还没
         * 完，我们需要验证这个缩放倍数是否越界了
         *
         * @param event 触摸事件
         */
        private void setZoomMatrix(MotionEvent event) {
            //只有同时触摸两个点时才执行
            if (event.getPointerCount() < 2) return;
            float endDis = distance(event);//结束的距离

            if (endDis > 10f) {//两个手指并拢时的距离为10像素
                float scale = endDis / mStartDis;//得到缩放倍数
                mStartDis = endDis;//重置距离
                mCurrentMatrix.set(getImageMatrix());

                float[] values = new float[9];
                mCurrentMatrix.getValues(values);

                scale = checkMaxScale(scale, values);
                setImageMatrix(mCurrentMatrix);
            }

        }

        /**
         * 检验scale，使图像缩放不会超过最大倍数
         * 我们获取了Matrix矩阵中保存的数组，在这个数组中，
         * values[Matrix.MSCALE_X](事实上就是数组的第0个)代表了X轴的缩放级别
         * ，判断一下图片当前的缩放级别再乘以刚得到的scale后是否回去越界，
         * 会的话就将其控制在边界值。之后，以ImageView的中心点为原点，
         * 在当前Matrix的基础上进行指定倍数的缩放。
         *
         * @param scale  缩放倍数
         * @param values values
         * @return 缩放背数
         */
        private float checkMaxScale(float scale, float[] values) {
            if (scale * values[Matrix.MSCALE_X] > mMaxScale) {
                scale = mMaxScale / values[Matrix.MSCALE_X];
            }
            mCurrentMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
            return scale;
        }

        /**
         * 在Move事件中缩放时我们只会阻止超越最大值的缩放，
         * 在UP事件中我们会对小于原始缩放值的缩放进行重置。方法如下。
         */
        private void reSetMatrix() {
            if (checkRest()) {
                mCurrentMatrix.set(mMatrix);
                setImageMatrix(mCurrentMatrix);
            }
        }

        /**
         * 判断是否需要重置
         * 首先获取当前X轴缩放级别(由于默认拉伸宽度至ImageView宽度，缩放级别以X轴为准)，
         * 再通过模板Matrix得到原始的X轴缩放级别，判断当前缩放级别是否小于模板缩放级别，
         * 若小于，则重置成模板缩放级别。
         *
         * @return 当前缩放级别小于模板缩放级别时，重置
         */
        private boolean checkRest() {
            float[] values = new float[9];
            getImageMatrix().getValues(values);
            //获取当前X轴缩放级别
            float scale = values[Matrix.MSCALE_X];
            //获取模版中的缩放级别，进行比较
            mMatrix.getValues(values);
            return scale < values[Matrix.MSCALE_X];

        }

        //双击时触发
        void onDoubleClick() {
            float scale = isZoomChanged() ? 1 : mDoubleClickScale;
            mCurrentMatrix.set(mMatrix);//初始化Matrix
            mCurrentMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
            setImageMatrix(mCurrentMatrix);
        }

        /**
         * 判断缩放级别是否改变过
         *
         * @return true表示非初始值, false表示初始值
         */
        private boolean isZoomChanged() {
            float[] values = new float[9];
            getImageMatrix().getValues(values);
            //获取当前X轴的缩放级别
            float scale = values[Matrix.MSCALE_X];
            //获取模版的缩放级别，两者进行比较
            mMatrix.getValues(values);
            return scale != values[Matrix.MSCALE_X];
        }
    }
    //####################################      触摸事件实现 end      #################################

    /*
      三种宽度的区别(非官方叫法，只为理解)

      1.显示宽度：ImageView中的图片(Bitmap、Drawable)在ImageView中显示的高度，
      是通过Matrix计算之后的宽度。当放大图片时，这个宽度可能超过ImageView的宽度。

      2.真实宽度：ImageView中的图片在Matrix计算之前的宽度。
      当ImageView宽为390，图片X轴缩放级别为0.5，一个填充满ImageView的X轴的图片的真实宽度为780。
      这个宽度和ImageView的ScaleType相关。

      3.文件宽度：文件X轴分辨率，不一定等于真实宽度。
     */

    /**
     * 重写setImageBitmap方法，初始化几个重要的变量
     * <p>
     * 1.mMatrix：图片的原始Matrix，记录下来作为模版，之后的变化都在此基础上
     * <p>
     * 2.mImageWidth:图片的真实宽度，注意这个宽度是指图片在ImageView中的真实宽度，
     * 非显示宽度也非文件宽度。当我们把图片放入ImageView中时，
     * 会根据ImageView的宽高进行一个转换，转换结果记录在Matrix中。
     * 我们根据显示宽度与Matrix进行计算获得真实宽度。
     * <p>
     * 3.mImageHeight:同宽度。
     */
    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        //设置图片 后，获取图片的变换坐标矩阵
        mMatrix.set(getImageMatrix());
        float[] values = new float[9];
        mMatrix.getValues(values);

        //图片宽度为屏幕宽度除缩放倍数
        mImgWidth = getWidth() / values[Matrix.MSCALE_X];
        mImgHeight = (getHeight() - values[Matrix.MTRANS_Y] * 2) / values[Matrix.MSCALE_Y];
    }

    //#####################          双击放大缩小图片效果         ###################

    /**
     * 在构造函数中将onTouchListner传递来进来。在此只重写两个方法：Down和onDoubleTap，
     * 只有在Down事件中返回true，onDoubleTap才能正常触发。
     * 在onDoubleClick事件中，首先通过isZoomChanged方法判断当前的缩放级别是
     * 否是模板Matrix的缩放级别，是的话将缩放倍数设置为2倍，否的话设置成1倍。
     * 在载入模板Matrix，在此基础上做缩放。
     */
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private MatrixTouchListener mListener;

        GestureListener(MatrixTouchListener listener) {
            mListener = listener;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            //捕获DOWN事件
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mListener.onDoubleClick();
            return true;
        }
    }
    //#####################          双击放大缩小图片效果         ###################
}