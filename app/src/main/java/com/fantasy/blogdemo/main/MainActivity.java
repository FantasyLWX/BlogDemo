package com.fantasy.blogdemo.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fantasy.blogdemo.R;
import com.fantasy.blogdemo.base.BaseActivity;
import com.fantasy.blogdemo.captcha.CaptchaActivity;
import com.fantasy.blogdemo.screenshot.ScreenShotActivity;

/**
 * 主界面
 * <pre>
 *     author  : Fantasy
 *     version : 1.1, 2019-06-28
 *     since   : 1.0, 2019-06-05
 * </pre>
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 打开主界面
     *
     * @param context 上下文
     */
    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindEvent();
    }

    private void bindEvent() {
        findViewById(R.id.iv_title_bar_back).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.tv_title_bar_title)).setText(R.string.app_name);
        findViewById(R.id.cd_main_screen_shot).setOnClickListener(this);
        findViewById(R.id.cd_main_captcha).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cd_main_screen_shot:
                ScreenShotActivity.actionStart(mContext);
                break;
            case R.id.cd_main_captcha:
                CaptchaActivity.actionStart(mContext);
                break;
            default:
                break;
        }
    }

}
