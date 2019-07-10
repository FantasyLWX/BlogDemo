package com.fantasy.blogdemo.crypto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fantasy.blogdemo.Constant;
import com.fantasy.blogdemo.R;
import com.fantasy.blogdemo.base.BaseActivity;
import com.fantasy.blogdemo.crypto.utils.CryptoHelper;
import com.fantasy.blogdemo.crypto.utils.TripleDESUtils;

/**
 * 加解密
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-07-10
 *     since   : 1.0, 2019-07-10
 * </pre>
 */
public class CryptoActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEtKey;
    private EditText mEtIv;
    private EditText mEtData;
    private EditText mEtResult;

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
        initData();
    }

    private void bindEvent() {
        ((TextView) findViewById(R.id.tv_title_bar_title)).setText(R.string.main_crypto);
        findViewById(R.id.iv_title_bar_back).setOnClickListener(this);
        mEtKey = findViewById(R.id.et_crypto_key);
        mEtIv = findViewById(R.id.et_crypto_iv);
        mEtData = findViewById(R.id.et_crypto_data);
        mEtResult = findViewById(R.id.et_crypto_result);
        findViewById(R.id.btn_crypto_md5).setOnClickListener(this);
        findViewById(R.id.btn_crypto_3des_ecb_encrypt).setOnClickListener(this);
        findViewById(R.id.btn_crypto_3des_ecb_decrypt).setOnClickListener(this);
        findViewById(R.id.btn_crypto_3des_cbc_encrypt).setOnClickListener(this);
        findViewById(R.id.btn_crypto_3des_cbc_decrypt).setOnClickListener(this);
    }

    private void initData() {
        mEtKey.requestFocus();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.iv_title_bar_back:
                    finish();
                    break;
                case R.id.btn_crypto_md5: // MD5加密
                    if (checkData()) {
                        String data = mEtData.getText().toString();
                        String result = CryptoHelper.encryptMD5(data);
                        mEtResult.setText(result);
                        Log.d(Constant.TAG, "data : " + data + " result : " + result);
                    }
                    break;
                case R.id.btn_crypto_3des_ecb_encrypt: // 3DES（ECB模式）加密
                    if (checkKey() && checkData()) {
                        String key = mEtKey.getText().toString(); // 密钥长度16位或者24位
                        String data = mEtData.getText().toString();
                        String result = TripleDESUtils.encryptECB(key, data);
                        mEtResult.setText(result);
                        Log.d(Constant.TAG, "data : " + data + " result : " + result + " key : " + key);
                    }
                    break;
                case R.id.btn_crypto_3des_ecb_decrypt: // 3DES（ECB模式）解密
                    if (checkKey() && checkData()) {
                        String key = mEtKey.getText().toString(); // 密钥长度16位或者24位
                        String data = mEtData.getText().toString();
                        String result = TripleDESUtils.decryptECB(key, data);
                        mEtResult.setText(result);
                        Log.d(Constant.TAG, "data : " + data + " result : " + result + " key : " + key);
                    }
                    break;
                case R.id.btn_crypto_3des_cbc_encrypt: // 3DES（CBC模式）加密
                    if (checkKey() && checkIV() && checkData()) {
                        String key = mEtKey.getText().toString(); // 密钥长度16位或者24位
                        String iv = mEtIv.getText().toString(); // IV偏移量的长度必须为8位
                        String data = mEtData.getText().toString();
                        String result = TripleDESUtils.encryptCBC(key, iv, data);
                        mEtResult.setText(result);
                        Log.d(Constant.TAG, "data : " + data + " result : " + result
                                + " key : " + key + " iv : " + iv);
                    }
                    break;
                case R.id.btn_crypto_3des_cbc_decrypt: // 3DES（CBC模式）解密
                    if (checkKey() && checkIV() && checkData()) {
                        String key = mEtKey.getText().toString(); // 密钥长度16位或者24位
                        String iv = mEtIv.getText().toString(); // IV偏移量的长度必须为8位
                        String data = mEtData.getText().toString();
                        String result = TripleDESUtils.decryptCBC(key, iv, data);
                        mEtResult.setText(result);
                        Log.d(Constant.TAG, "data : " + data + " result : " + result
                                + " key : " + key + " iv : " + iv);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showLongToast(getString(R.string.crypto_exception) + e.getMessage());
            mEtResult.setText("");
        }
    }

    /**
     * 检查输入的密钥
     *
     * @return 如果符合输入规则，则返回true；不符合则返回false
     */
    private boolean checkKey() {
        String key = mEtKey.getText().toString();
        if (TextUtils.isEmpty(key)) {
            mEtKey.requestFocus();
            mEtResult.setText("");
            showToast(R.string.crypto_key_hint);
            return false;
        }
        return true;
    }

    /**
     * 检查输入的偏移量
     *
     * @return 如果符合输入规则，则返回true；不符合则返回false
     */
    private boolean checkIV() {
        String key = mEtIv.getText().toString();
        if (TextUtils.isEmpty(key)) {
            mEtIv.requestFocus();
            mEtResult.setText("");
            showToast(R.string.crypto_iv_hint);
            return false;
        }
        return true;
    }

    /**
     * 检查输入的待加密或待解密的数据
     *
     * @return 如果符合输入规则，则返回true；不符合则返回false
     */
    private boolean checkData() {
        if (TextUtils.isEmpty(mEtData.getText().toString())) {
            mEtData.requestFocus();
            mEtResult.setText("");
            showToast(R.string.crypto_data_hint);
            return false;
        }
        return true;
    }

}
