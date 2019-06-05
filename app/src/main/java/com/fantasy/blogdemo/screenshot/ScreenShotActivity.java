package com.fantasy.blogdemo.screenshot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fantasy.blogdemo.Constant;
import com.fantasy.blogdemo.R;
import com.fantasy.blogdemo.base.BaseActivity;
import com.fantasy.blogdemo.utils.CheckSoulPermissionListener;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;

/**
 * 截屏
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-06-05
 *     since   : 1.0, 2019-06-05
 * </pre>
 */
public class ScreenShotActivity extends BaseActivity implements View.OnClickListener {
    private Button mBtnSystem;
    private ScreenShot mScreenShot;

    /**
     * 打开“截屏”模块
     *
     * @param context 上下文
     */
    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, ScreenShotActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);
        bindEvent();
        initData();
    }

    @Override
    protected void onDestroy() {
        mScreenShot.unregister();
        super.onDestroy();
    }

    private void bindEvent() {
        ((TextView) findViewById(R.id.tv_title_bar_title)).setText(R.string.main_screen_shot);
        findViewById(R.id.iv_title_bar_back).setOnClickListener(this);
        mBtnSystem = findViewById(R.id.btn_screen_shot_system);
        mBtnSystem.setOnClickListener(this);
    }

    private void initData() {
        mScreenShot = ScreenShot.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_bar_back:
                finish();
                break;
            case R.id.btn_screen_shot_system:
                SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                        new CheckSoulPermissionListener(getString(R.string.screen_shot_permission), new Runnable() {
                            @Override
                            public void run() {
                                mBtnSystem.callOnClick();
                            }
                        }) {
                            @Override
                            public void onPermissionOk(Permission permission) {
                                handelSystemScreenShot();
                            }
                        });
                break;
            default:
                break;
        }
    }

    private void handelSystemScreenShot() {
        if (mBtnSystem.getText().toString().equals(getString(R.string.screen_shot_system))) {
            mBtnSystem.setText(R.string.screen_shot_system_cancel);
            mScreenShot.register(mContext, new ScreenShot.CallbackListener() {
                @Override
                public void onShot(String path) {
                    Log.d(Constant.TAG, mClassName + " shot : " + path);
                    showLongToast("捕获到系统截屏，截屏路径：" + path);
                }
            });
        } else {
            mBtnSystem.setText(R.string.screen_shot_system);
            mScreenShot.unregister();
        }
    }

}
