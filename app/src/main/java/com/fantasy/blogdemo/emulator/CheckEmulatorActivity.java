package com.fantasy.blogdemo.emulator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fantasy.blogdemo.R;
import com.fantasy.blogdemo.base.BaseActivity;

import diff.strazzere.anti.AntiEmulator;

/**
 * 检测模拟器
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2020-06-20
 *     since   : 1.0, 2020-06-20
 * </pre>
 */
public class CheckEmulatorActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 打开“检测模拟器”模块
     *
     * @param context 上下文
     */
    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, CheckEmulatorActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_emulator);
        bindEvent();
    }

    private void bindEvent() {
        ((TextView) findViewById(R.id.tv_title_bar_title)).setText(R.string.main_check_emulator);
        findViewById(R.id.iv_title_bar_back).setOnClickListener(this);
        findViewById(R.id.btn_emulator_check).setOnClickListener(this);
        findViewById(R.id.btn_emulator_check_safely).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_bar_back:
                finish();
                break;
            case R.id.btn_emulator_check: // 标准模式
                if (AntiEmulator.check(mContext)) {
                    showToast(R.string.emulator_true);
                } else {
                    showToast(R.string.emulator_false);
                }
                break;
            case R.id.btn_emulator_check_safely: // 安全模式
                if (AntiEmulator.checkSafely(mContext)) {
                    showToast(R.string.emulator_true);
                } else {
                    showToast(R.string.emulator_false);
                }
                break;
            default:
                break;
        }
    }

}
