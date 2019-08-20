package com.fantasy.blogdemo.crypto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fantasy.blogdemo.R;
import com.fantasy.blogdemo.base.BaseActivity;

/**
 * 非对称加密
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-08-20
 *     since   : 1.0, 2019-08-20
 * </pre>
 */
public class AsymmetricEncryptActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 打开“非对称加密”模块
     *
     * @param context 上下文
     */
    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, AsymmetricEncryptActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_asymmetric);
        bindEvent();
    }

    private void bindEvent() {
        ((TextView) findViewById(R.id.tv_title_bar_title)).setText(R.string.crypto_title_asymmetric);
        findViewById(R.id.iv_title_bar_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_bar_back:
                finish();
                break;
            default:
                break;
        }
    }
}
