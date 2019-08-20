package com.fantasy.blogdemo.crypto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fantasy.blogdemo.R;
import com.fantasy.blogdemo.base.BaseActivity;

/**
 * 加解密
 * <pre>
 *     author  : Fantasy
 *     version : 1.2, 2019-08-20
 *     since   : 1.0, 2019-07-10
 * </pre>
 */
public class CryptoActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 打开“加解密”模块
     *
     * @param context 上下文
     */
    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, CryptoActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto);
        bindEvent();
    }

    private void bindEvent() {
        ((TextView) findViewById(R.id.tv_title_bar_title)).setText(R.string.main_crypto);
        findViewById(R.id.iv_title_bar_back).setOnClickListener(this);
        findViewById(R.id.btn_crypto_hash).setOnClickListener(this);
        findViewById(R.id.btn_crypto_symmetric).setOnClickListener(this);
        findViewById(R.id.btn_crypto_asymmetric).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_bar_back:
                finish();
                break;
            case R.id.btn_crypto_hash:
                HashActivity.actionStart(mContext);
                break;
            case R.id.btn_crypto_symmetric:
                SymmetricEncryptActivity.actionStart(mContext);
                break;
            case R.id.btn_crypto_asymmetric:
                AsymmetricEncryptActivity.actionStart(mContext);
                break;
            default:
                break;
        }
    }

}
