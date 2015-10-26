package com.awesomego.cocos.watchface;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.wearable.watchface.WatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxGLSurfaceView;
import org.cocos2dx.lib.Cocos2dxHelper;
import org.cocos2dx.lib.Cocos2dxRenderer;

public class MyWatchFace extends WatchFaceService {

    @Override
    public Engine onCreateEngine() {
        return new MyEngine();
    }

    protected class MyEngine extends Engine implements Cocos2dxHelper.Cocos2dxHelperListener {
        private MyGlSurfaceView mGlSurfaceView;
        private Cocos2dxRenderer mRenderer;
        private int[] mGLContextAttrs = null;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);

            setWatchFaceStyle(new WatchFaceStyle.Builder(MyWatchFace.this)
                    .setCardPeekMode(WatchFaceStyle.PEEK_MODE_SHORT)
                    .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                    .setShowSystemUiTime(false)
                    .setStatusBarGravity(Gravity.TOP + 1)
                    .build());

            onLoadNativeLibraries();
            Cocos2dxHelper.init(MyWatchFace.this, this);
            // Must call getGLContextAttrs, because cocos_android_app_init will call by it.
            this.mGLContextAttrs = Cocos2dxActivity.getGLContextAttrs();
            mGlSurfaceView = onCreateView();
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int screenHeight = getResources().getDisplayMetrics().heightPixels;
            mRenderer = new Cocos2dxRenderer();
            mRenderer.setScreenWidthAndHeight(screenWidth, screenHeight);
            mGlSurfaceView.setCocos2dxRenderer(mRenderer);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);

            if (visible) {
                mGlSurfaceView.onResume();
                Cocos2dxHelper.onResume();
            } else {
                mGlSurfaceView.onPause();
                Cocos2dxHelper.onPause();
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mGlSurfaceView.onDestroy();
            Cocos2dxHelper.end();
            Cocos2dxHelper.terminateProcess();
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
        }

        @Override
        public void showDialog(String pTitle, String pMessage) {

        }

        @Override
        public void showEditTextDialog(String pTitle, String pMessage, int pInputMode, int pInputFlag, int pReturnType, int pMaxLength) {

        }

        @Override
        public void runOnGLThread(Runnable pRunnable) {
            mGlSurfaceView.queueEvent(pRunnable);
        }

        protected void onLoadNativeLibraries() {
            try {
                ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
                Bundle bundle = ai.metaData;
                String libName = bundle.getString("android.app.lib_name");
                System.loadLibrary(libName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public MyGlSurfaceView onCreateView() {
            MyGlSurfaceView glSurfaceView = new MyGlSurfaceView(MyWatchFace.this);
            //this line is need on some device if we specify an alpha bits
            if (this.mGLContextAttrs[3] > 0)
                glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

            Cocos2dxActivity.Cocos2dxEGLConfigChooser chooser = new Cocos2dxActivity.Cocos2dxEGLConfigChooser(this.mGLContextAttrs);
            glSurfaceView.setEGLConfigChooser(chooser);
            return glSurfaceView;
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            mGlSurfaceView.onTouchEvent(event);
        }

        public class MyGlSurfaceView extends Cocos2dxGLSurfaceView {

            public MyGlSurfaceView(Context context) {
                super(context);
            }

            @Override
            public SurfaceHolder getHolder() {
                return MyEngine.this.getSurfaceHolder();
            }

            public void onDestroy() {
                super.onDetachedFromWindow();
            }
        }
    }
}