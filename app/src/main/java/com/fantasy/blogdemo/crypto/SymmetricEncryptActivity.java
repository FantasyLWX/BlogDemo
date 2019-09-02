package com.fantasy.blogdemo.crypto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fantasy.blogdemo.Constant;
import com.fantasy.blogdemo.R;
import com.fantasy.blogdemo.base.BaseActivity;
import com.fantasy.blogdemo.crypto.utils.AESUtils;
import com.fantasy.blogdemo.crypto.utils.TripleDESUtils;

/**
 * 对称加密
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-08-20
 *     since   : 1.0, 2019-08-20
 * </pre>
 */
public class SymmetricEncryptActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEtKey;
    private EditText mEtIv;
    private EditText mEtData;
    private EditText mEtResult;

    private int mEncryptionModeCheckedId;
    private int mOutputModeCheckedId;

    /**
     * 打开“对称加密”模块
     *
     * @param context 上下文
     */
    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, SymmetricEncryptActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_symmetric);
        bindEvent();
        initData();
    }

    private void bindEvent() {
        ((TextView) findViewById(R.id.tv_title_bar_title)).setText(R.string.crypto_title_symmetric);
        findViewById(R.id.iv_title_bar_back).setOnClickListener(this);
        mEtKey = findViewById(R.id.et_crypto_key);
        mEtIv = findViewById(R.id.et_crypto_iv);
        mEtData = findViewById(R.id.et_crypto_data);
        mEtResult = findViewById(R.id.et_crypto_result);
        findViewById(R.id.btn_crypto_3des_encrypt).setOnClickListener(this);
        findViewById(R.id.btn_crypto_3des_decrypt).setOnClickListener(this);
        findViewById(R.id.btn_crypto_aes_encrypt).setOnClickListener(this);
        findViewById(R.id.btn_crypto_aes_decrypt).setOnClickListener(this);
        ((RadioGroup) findViewById(R.id.rg_crypto_encryption_mode)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mEncryptionModeCheckedId = checkedId;
            }
        });
        ((RadioGroup) findViewById(R.id.rg_crypto_output_mode)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mOutputModeCheckedId = checkedId;
            }
        });
    }

    private void initData() {
        mEncryptionModeCheckedId = R.id.rb_crypto_ecb;
        mOutputModeCheckedId = R.id.rb_crypto_base64;
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.iv_title_bar_back:
                    finish();
                    break;
                case R.id.btn_crypto_3des_encrypt: // 3DES加密
                    String key = mEtKey.getText().toString(); // 密钥长度16位、24位
                    String iv = mEtIv.getText().toString(); // IV偏移量的长度必须为8位
                    String data = mEtData.getText().toString();
                    String transformation;
                    String result;
                    if (mEncryptionModeCheckedId == R.id.rb_crypto_ecb) {
                        transformation = "DESede/ECB/PKCS5Padding";
                        if (checkKey() && checkData()) {
                            if (mOutputModeCheckedId == R.id.rb_crypto_base64) {
                                result = TripleDESUtils.encryptBase64(data, key, transformation, null);
                            } else {
                                result = TripleDESUtils.encryptHex(data, key, transformation, null);
                            }
                            mEtResult.setText(result);
                            Log.d(Constant.TAG, mClassName + " data : " + data + " result : " + result + " key : " + key);
                        }
                    } else {
                        transformation = "DESede/CBC/PKCS5Padding";
                        if (checkKey() && checkIV() && checkData()) {
                            if (mOutputModeCheckedId == R.id.rb_crypto_base64) {
                                result = TripleDESUtils.encryptBase64(data, key, transformation, iv);
                            } else {
                                result = TripleDESUtils.encryptHex(data, key, transformation, iv);
                            }
                            mEtResult.setText(result);
                            Log.d(Constant.TAG, mClassName + " data : " + data + " result : " + result + " key : " + key + " iv : " + iv);
                        }
                    }
                    break;
                case R.id.btn_crypto_3des_decrypt: // 3DES解密
                    key = mEtKey.getText().toString(); // 密钥长度16位、24位
                    iv = mEtIv.getText().toString(); // IV偏移量的长度必须为8位
                    data = mEtData.getText().toString();
                    if (mEncryptionModeCheckedId == R.id.rb_crypto_ecb) {
                        transformation = "DESede/ECB/PKCS5Padding";
                        if (checkKey() && checkData()) {
                            if (mOutputModeCheckedId == R.id.rb_crypto_base64) {
                                result = TripleDESUtils.decryptBase64(data, key, transformation, null);
                            } else {
                                result = TripleDESUtils.decryptHex(data, key, transformation, null);
                            }
                            mEtResult.setText(result);
                            Log.d(Constant.TAG, mClassName + " data : " + data + " result : " + result + " key : " + key);
                        }
                    } else {
                        transformation = "DESede/CBC/PKCS5Padding";
                        if (checkKey() && checkIV() && checkData()) {
                            if (mOutputModeCheckedId == R.id.rb_crypto_base64) {
                                result = TripleDESUtils.decryptBase64(data, key, transformation, iv);
                            } else {
                                result = TripleDESUtils.decryptHex(data, key, transformation, iv);
                            }
                            mEtResult.setText(result);
                            Log.d(Constant.TAG, mClassName + " data : " + data + " result : " + result + " key : " + key + " iv : " + iv);
                        }
                    }
                    break;
                case R.id.btn_crypto_aes_encrypt: // AES加密
                    key = mEtKey.getText().toString(); // 密钥长度16位、24位、32位
                    iv = mEtIv.getText().toString(); // IV偏移量的长度必须为16位
                    data = mEtData.getText().toString();
                    if (mEncryptionModeCheckedId == R.id.rb_crypto_ecb) {
                        transformation = "AES/ECB/PKCS5Padding";
                        if (checkKey() && checkData()) {
                            if (mOutputModeCheckedId == R.id.rb_crypto_base64) {
                                result = AESUtils.encryptBase64(data, key, transformation, null);
                            } else {
                                result = AESUtils.encryptHex(data, key, transformation, null);
                            }
                            mEtResult.setText(result);
                            Log.d(Constant.TAG, mClassName + " data : " + data + " result : " + result + " key : " + key);
                        }
                    } else {
                        transformation = "AES/CBC/PKCS5Padding";
                        if (checkKey() && checkIV() && checkData()) {
                            if (mOutputModeCheckedId == R.id.rb_crypto_base64) {
                                result = AESUtils.encryptBase64(data, key, transformation, iv);
                            } else {
                                result = AESUtils.encryptHex(data, key, transformation, iv);
                            }
                            mEtResult.setText(result);
                            Log.d(Constant.TAG, mClassName + " data : " + data + " result : " + result + " key : " + key + " iv : " + iv);
                        }
                    }
                    break;
                case R.id.btn_crypto_aes_decrypt: // AES解密
                    key = mEtKey.getText().toString(); // 密钥长度16位、24位、32位
                    iv = mEtIv.getText().toString(); // IV偏移量的长度必须为16位
                    data = mEtData.getText().toString();
                    if (mEncryptionModeCheckedId == R.id.rb_crypto_ecb) {
                        transformation = "AES/ECB/PKCS5Padding";
                        if (checkKey() && checkData()) {
                            if (mOutputModeCheckedId == R.id.rb_crypto_base64) {
                                result = AESUtils.decryptBase64(data, key, transformation, null);
                            } else {
                                result = AESUtils.decryptHex(data, key, transformation, null);
                            }
                            mEtResult.setText(result);
                            Log.d(Constant.TAG, mClassName + " data : " + data + " result : " + result + " key : " + key);
                        }
                    } else {
                        transformation = "AES/CBC/PKCS5Padding";
                        if (checkKey() && checkIV() && checkData()) {
                            if (mOutputModeCheckedId == R.id.rb_crypto_base64) {
                                result = AESUtils.decryptBase64(data, key, transformation, iv);
                            } else {
                                result = AESUtils.decryptHex(data, key, transformation, iv);
                            }
                            mEtResult.setText(result);
                            Log.d(Constant.TAG, mClassName + " data : " + data + " result : " + result + " key : " + key + " iv : " + iv);
                        }
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
