package com.zx.imageloader.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 图片加载框架
 * <p>
 * 加载框架的架构：
 * 1、单例，包含一个LruCache用于管理我们的图片；
 * 2、任务队列，我们每来一次加载图片的请求，我们会封装成Task存入我们的TaskQueue;
 * 3、包含一个后台线程，这个线程在第一次初始化实例的时候启动，然后会一直在后台运行；
 * 任务呢？还记得我们有个任务队列么，有队列存任务，得有人干活呀；
 * 所以，当每来一次加载图片请求的时候，我们同时发一个消息到后台线程，
 * 后台线程去使用线程池去TaskQueue去取一个任务执行；
 * 4、调度策略；3中说了，后台线程去TaskQueue去取一个任务，这个任务不是随便取的，
 * 有策略可以选择，一个是FIFO，一个是LIFO，我倾向于后者。
 * </p>
 *
 * @author HY
 */
@SuppressWarnings({"FieldCanBeLocal,WeakerAccess", "ConstantConditions"})
public class ImageLoader {

    private static final String TAG = ImageLoader.class.getSimpleName();

    private static ImageLoader mImageLoader;

    /* 图片缓存核心对象 */
    private LruCache<String, Bitmap> mLruCache;

    /* 线程池 */
    private ExecutorService mThreadPool;
    private static final int DEFAULT_THREAD_COUNT = 1;

    /* 队列的调度方式 */
    private Type mType = Type.LIFO;

    /* 任务队列 */
    private LinkedList<Runnable> mTaskQueue;

    /* 后台轮询线程 */
    private Thread mPoolThread;
    private Handler mThreadPoolHandler;

    /**
     * UI线程中的Handler
     * 这个Handler用于更新我们的imageview
     */
    private Handler mMainHandler;


    /**
     * 信号量
     * Semaphore可以控制某个资源可被同时访问的个数，
     * 通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
     * 第一个：mSemaphorePoolThreadHandler = new Semaphore(0);
     * 用于控制我们的mPoolThreadHandler的初始化完成，我们在使用mPoolThreadHandler会进行判空，
     * 如果为null，会通过mSemaphorePoolThreadHandler.acquire()进行阻塞；
     * 当mPoolThreadHandler初始化结束，我们会调用.release();解除阻塞。
     * 第二个：mSemaphoreThreadPool = new Semaphore(threadCount);
     * 这个信号量的数量和我们加载图片的线程个数一致；每取一个任务去执行，
     * 我们会让信号量减一；每完成一个任务，会让信号量+1，再去取任务；
     * 目的是什么呢？为什么当我们的任务到来时，如果此时在没有空闲线程，
     * 任务则一直添加到TaskQueue中，当线程完成任务，
     * 可以根据策略去TaskQueue中去取任务，只有这样，我们的LIFO才有意义。
     */
    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
    private Semaphore mSemaphoreThreadPool;

    private boolean isDiskCacheEnable = true;

    /**
     * 队列的调度方式
     */
    public enum Type {
        /* First In First Out */
        FIFO,
        /* Last In First Out */
        LIFO
    }

    private ImageLoader(int threadCount, Type type) {
        init(threadCount, type);
    }

    /**
     * 初始化
     *
     * @param threadCount 线程数量
     * @param type        调度方式
     */
    private void init(int threadCount, Type type) {
        //初始化后台轮询线程
        initBackThread();

        //获取应用的最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;//取最大内存的1/8作为缓存内存

        mLruCache = new LruCache<String, Bitmap>(cacheMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };

        //创建线程池
        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<>();
        mType = type;
        mSemaphoreThreadPool = new Semaphore(threadCount);

    }

    //初始化后台轮询线程
    private void initBackThread() {
        mPoolThread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                mThreadPoolHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        //从线程池中取出一个任务去执行
                        mThreadPool.execute(getTask());
                        try {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //释放一个信号量
                mSemaphorePoolThreadHandler.release();
                Looper.loop();
            }
        };
        mPoolThread.start();
    }


    /**
     * 单例模式获取当前类的实例
     *
     * @return ImageLoader
     */
    public static ImageLoader getInstance() {
        return getInstance(DEFAULT_THREAD_COUNT, Type.LIFO);
    }

    /**
     * 获取当前类的实例
     *
     * @param threadCount 线程数量
     * @param type        调度方式
     * @return ImageLoader
     */
    public static ImageLoader getInstance(int threadCount, Type type) {
        if (mImageLoader == null) {
            synchronized (ImageLoader.class) {
                if (mImageLoader == null) {
                    mImageLoader = new ImageLoader(threadCount, type);
                }
            }
        }
        return mImageLoader;
    }

    /**
     * 加载图片
     *
     * @param path      图片路径
     * @param imageView ImageView
     * @param isFromNet 是否来自网络
     */
    public void loadImage(String path, ImageView imageView, boolean isFromNet) {
        imageView.setTag(path);

        if (null == mMainHandler) {
            mMainHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    //获取得到图片，为ImageView回调设置图片
                    ImgHolder holder = (ImgHolder) msg.obj;
                    Bitmap bitmap = holder.bitmap;
                    ImageView imageView = holder.imageView;
                    String path = holder.path;
                    //将path与getTag路径进行比较
                    if (imageView.getTag().toString().equals(path)) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            };
        }

        //根据path在缓存中获取bitmap
        Bitmap bitmap = getBitmapfromLurCache(path);
        if (null != bitmap) {
            refreshBitmap(path, imageView, bitmap);
        } else {
            addTask(buildTask(path, imageView, isFromNet));
        }

    }

    private Runnable buildTask(final String path, final ImageView imageView, final boolean isFromTask) {
        return new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                if (isFromTask) {
                    File file = getDiskCacheDir(imageView.getContext(), md5(path));

                    if (file.exists()) {
                        Log.d(TAG, "find image:" + path + " in disk cache");
                        bitmap = loadImageFromLocal(file.getAbsolutePath(), imageView);
                    } else {
                        if (isDiskCacheEnable) {//是否使用硬盘缓存
                            boolean downloadState = DownloadImgUtils.downloadImgByUrl(path, file);
                            if (downloadState) {//如果下载成功
                                Log.d(TAG, "download image :" + path
                                        + " to disk cache . path is "
                                        + file.getAbsolutePath());
                                bitmap = loadImageFromLocal(file.getAbsolutePath(), imageView);
                            }
                        } else {
                            //直接从网络下载
                            Log.d(TAG, "load image :" + path + " to memory.");
                            bitmap = DownloadImgUtils.downloadImgByUrl(path, imageView);
                        }

                    }
                } else {
                    bitmap = loadImageFromLocal(path, imageView);
                }

                //把图片加入到缓存
                addBitmapToLurch(path, bitmap);
                refreshBitmap(path, imageView, bitmap);
                mSemaphoreThreadPool.release();

            }
        };
    }

    /**
     * 将图片加入缓存
     *
     * @param path   路径
     * @param bitmap 图片
     */
    private void addBitmapToLurch(String path, Bitmap bitmap) {
        if (null == getBitmapfromLurCache(path)) {
            if (null != bitmap) {
                mLruCache.put(path, bitmap);
            }
        }
    }

    /**
     * 从本地加载图片
     *
     * @param path      路径
     * @param imageView ImageView
     * @return Bitmap
     */
    private Bitmap loadImageFromLocal(String path, ImageView imageView) {
        Bitmap bitmap;
        /*
          1.加载图片
          2.图片压缩
          3.图片显示的大小
         */
        ImageSizeUtil.ImageSize imageSize = ImageSizeUtil.getImageViewSize(imageView);
        bitmap = decodeSampleBitmapFromPath(path, imageSize.width, imageSize.height);
        return bitmap;
    }

    /**
     * 根据图片需要显示的宽高压缩图片
     *
     * @param path   路径
     * @param width  需要显示的宽
     * @param height 需要显示的高
     * @return 压缩后的图片
     */
    private Bitmap decodeSampleBitmapFromPath(String path, int width, int height) {
        // 获取图片的宽和高，不加入内存中
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = ImageSizeUtil.calculateInSampleSize(options, width, height);
        //使用获取到的SampleSize再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 获取缓存图片的地址
     *
     * @param context 上下文对象
     * @param md5     md5
     * @return 缓存文件
     */
    private File getDiskCacheDir(Context context, String md5) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + md5);
    }


    /**
     * 利用签名辅助类，将字符串转为字节数组
     *
     * @param path 路径
     * @return md5
     */
    private String md5(String path) {
        byte[] digest;
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            digest = md.digest(path.getBytes());
            return bytes2hex(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字节数组转16进制字符串
     *
     * @param digest 字节数组
     * @return md5
     */
    private String bytes2hex(byte[] digest) {
        StringBuilder sbl = new StringBuilder();
        String tmp;
        for (byte b : digest) {
            //将每个字符与0xFF进行运算得到10进制，再借助Integer转为16进制
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1) {//每个字节8位，转换为16进制，两个16进制位
                tmp = "0" + tmp;
            }
            sbl.append(tmp);
        }
        return sbl.toString();
    }


    /**
     * 添加任务
     *
     * @param runable 任务
     */
    private synchronized void addTask(Runnable runable) {
        mTaskQueue.add(runable);
        try {
            if (null == mThreadPoolHandler) {
                mSemaphorePoolThreadHandler.acquire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mThreadPoolHandler.sendEmptyMessage(0x110);
    }

    /**
     * 刷新图片显示
     *
     * @param path      路径
     * @param imageView ImageView
     * @param bitmap    Bitmap
     */
    private void refreshBitmap(String path, ImageView imageView, Bitmap bitmap) {
        Message msg = Message.obtain();
        ImgHolder holder = new ImgHolder();
        holder.path = path;
        holder.bitmap = bitmap;
        holder.imageView = imageView;
        msg.obj = holder;
        mMainHandler.sendMessage(msg);
    }

    /**
     * 从缓存中获取图片
     *
     * @param key 路径
     * @return 图片
     */
    private Bitmap getBitmapfromLurCache(String key) {
        return mLruCache.get(key);
    }

    /**
     * 从任务队列中取出一个方法
     *
     * @return 任务
     */
    private Runnable getTask() {
        if (mType == Type.FIFO) {
            return mTaskQueue.removeFirst();
        } else if (mType == Type.LIFO) {
            return mTaskQueue.removeLast();
        }
        return null;
    }


    private class ImgHolder {
        Bitmap bitmap;
        ImageView imageView;
        String path;
    }

}
