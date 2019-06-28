package com.fantasy.blogdemo.screenshot;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;

/**
 * 系统截屏监听工具，监听系统截屏，然后对截图进行处理
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-06-05
 *     since   : 1.0, 2019-06-05
 * </pre>
 */
public class ScreenShot {
    private static final String TAG = "ScreenShot";
    private static final String[] MEDIA_PROJECTIONS = {
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.DATE_ADDED
    };

    /**
     * 截屏依据中的路径判断关键字
     */
    private static final String[] KEYWORDS = {
            "screenshot", "screen_shot", "screen-shot", "screen shot", "screencapture",
            "screen_capture", "screen-capture", "screen capture", "screencap", "screen_cap",
            "screen-cap", "screen cap"
    };

    private ContentResolver mContentResolver;
    private CallbackListener mCallbackListener;
    private MediaContentObserver mInternalObserver;
    private MediaContentObserver mExternalObserver;
    private static ScreenShot mInstance;

    private ScreenShot() {
    }

    /**
     * 获取 ScreenShot 对象
     *
     * @return ScreenShot对象
     */
    public static ScreenShot getInstance() {
        if (mInstance == null) {
            synchronized (ScreenShot.class) {
                mInstance = new ScreenShot();
            }
        }
        return mInstance;
    }

    /**
     * 注册
     *
     * @param context          上下文
     * @param callbackListener 回调监听
     */
    public void register(Context context, CallbackListener callbackListener) {
        mContentResolver = context.getContentResolver();
        mCallbackListener = callbackListener;

        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        mInternalObserver = new MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, handler);
        mExternalObserver = new MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, handler);

        mContentResolver.registerContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                false, mInternalObserver);
        mContentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                false, mExternalObserver);
    }

    /**
     * 注销
     */
    public void unregister() {
        if (mContentResolver != null) {
            mContentResolver.unregisterContentObserver(mInternalObserver);
            mContentResolver.unregisterContentObserver(mExternalObserver);
        }
    }

    private void handleMediaContentChange(Uri uri) {
        Cursor cursor = null;
        try {
            // 数据改变时，查询数据库中最后加入的一条数据
            cursor = mContentResolver.query(uri, MEDIA_PROJECTIONS, null, null,
                    MediaStore.Images.ImageColumns.DATE_ADDED + " desc limit 1");
            if (cursor == null) {
                return;
            }
            if (!cursor.moveToFirst()) {
                return;
            }
            int dataIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            int dateAddedIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_ADDED);
            // 处理获取到的第一行数据
            handleMediaRowData(cursor.getString(dataIndex), cursor.getLong(dateAddedIndex));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    /**
     * 处理监听到的资源
     */
    private void handleMediaRowData(String path, long dateAdded) {
        long duration = 0;
        long step = 100;

        // 发现个别手机会自己修改截图文件夹的文件，截屏功能会误以为是用户在截屏操作，进行捕获，所以加了一个时间判断
        if (!isTimeValid(dateAdded)) {
            Log.d(TAG, "图片插入时间大于1秒，不是截屏");
            return;
        }

        // 设置最大等待时间为500ms，因为魅族的部分手机保存截图有延迟
        while (!checkScreenShot(path) && duration <= 500) {
            try {
                duration += step;
                Thread.sleep(step);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (checkScreenShot(path)) {
            if (mCallbackListener != null) {
                mCallbackListener.onShot(path);
            }
        }
    }

    /**
     * 判断时间是否合格，图片插入时间小于1秒才有效
     */
    private boolean isTimeValid(long dateAdded) {
        return Math.abs(System.currentTimeMillis() / 1000 - dateAdded) <= 1;
    }

    private boolean checkScreenShot(String path) {
        if (path == null) {
            return false;
        }
        path = path.toLowerCase();
        for (String keyword : KEYWORDS) {
            if (path.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 媒体内容观察者
     */
    private class MediaContentObserver extends ContentObserver {
        private Uri mUri;

        MediaContentObserver(Uri uri, Handler handler) {
            super(handler);
            mUri = uri;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            //Log.d("ScreenShot", "图片数据库发生变化：" + selfChange);
            handleMediaContentChange(mUri);
        }
    }

    /**
     * 回调监听器
     */
    public interface CallbackListener {
        /**
         * 截屏
         *
         * @param path 图片路径
         */
        void onShot(String path);
    }

}
