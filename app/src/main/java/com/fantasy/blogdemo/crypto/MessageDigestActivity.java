package com.fantasy.blogdemo.crypto;

import android.Manifest;
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
import com.fantasy.blogdemo.utils.CheckSoulPermissionListener;
import com.fantasy.blogdemo.utils.file.FileUtils;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;

/**
 * 信息摘要
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-09-02
 *     since   : 1.0, 2019-08-20
 * </pre>
 */
public class MessageDigestActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEtData;
    private EditText mEtKey;
    private EditText mEtResult;

    private static final int REQUEST_CODE_SELECT_FILE = 0;

    /**
     * 打开“信息摘要”模块
     *
     * @param context 上下文
     */
    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, MessageDigestActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_message_digest);
        bindEvent();
    }

    private void bindEvent() {
        ((TextView) findViewById(R.id.tv_title_bar_title)).setText(R.string.crypto_title_message_digest);
        mEtData = findViewById(R.id.et_crypto_message_digest_data);
        mEtKey = findViewById(R.id.et_crypto_message_digest_key);
        mEtResult = findViewById(R.id.et_crypto_message_digest_result);
        findViewById(R.id.iv_title_bar_back).setOnClickListener(this);
        findViewById(R.id.btn_crypto_md5_file).setOnClickListener(this);
        findViewById(R.id.btn_crypto_md2).setOnClickListener(this);
        findViewById(R.id.btn_crypto_md5).setOnClickListener(this);
        findViewById(R.id.btn_crypto_sha1).setOnClickListener(this);
        findViewById(R.id.btn_crypto_sha224).setOnClickListener(this);
        findViewById(R.id.btn_crypto_sha256).setOnClickListener(this);
        findViewById(R.id.btn_crypto_sha384).setOnClickListener(this);
        findViewById(R.id.btn_crypto_sha512).setOnClickListener(this);
        findViewById(R.id.btn_crypto_hmacmd5).setOnClickListener(this);
        findViewById(R.id.btn_crypto_hmacsha1).setOnClickListener(this);
        findViewById(R.id.btn_crypto_hmacsha224).setOnClickListener(this);
        findViewById(R.id.btn_crypto_hmacsha256).setOnClickListener(this);
        findViewById(R.id.btn_crypto_hmacsha384).setOnClickListener(this);
        findViewById(R.id.btn_crypto_hmacsha512).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String data = mEtData.getText().toString();
        String key = mEtKey.getText().toString();
        String result = null;

        switch (v.getId()) {
            case R.id.iv_title_bar_back:
                finish();
                break;
            case R.id.btn_crypto_md5_file:
                SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                        new CheckSoulPermissionListener(getString(R.string.crypto_message_digest_permission), new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.btn_crypto_md5_file).callOnClick();
                            }
                        }) {
                            @Override
                            public void onPermissionOk(Permission permission) {
                                // 调用系统自带的文件选择器
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intent.setType("*/*");
                                startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
                            }
                        });
                break;
            case R.id.btn_crypto_md2:
            case R.id.btn_crypto_md5:
            case R.id.btn_crypto_sha1:
            case R.id.btn_crypto_sha224:
            case R.id.btn_crypto_sha256:
            case R.id.btn_crypto_sha384:
            case R.id.btn_crypto_sha512:
                if (checkData()) {
                    switch (v.getId()) {
                        case R.id.btn_crypto_md2:
                            // MD2加密无结果，是因为报异常了，Android SDK没有MD2算法
                            // java.security.NoSuchAlgorithmException: MD2 MessageDigest not available
                            result = CryptoHelper.encryptMD2ToString(data);
                            break;
                        case R.id.btn_crypto_md5:
                            result = CryptoHelper.encryptMD5ToString(data);
                            break;
                        case R.id.btn_crypto_sha1:
                            result = CryptoHelper.encryptSHA1ToString(data);
                            break;
                        case R.id.btn_crypto_sha224:
                            result = CryptoHelper.encryptSHA224ToString(data);
                            break;
                        case R.id.btn_crypto_sha256:
                            result = CryptoHelper.encryptSHA256ToString(data);
                            break;
                        case R.id.btn_crypto_sha384:
                            result = CryptoHelper.encryptSHA384ToString(data);
                            break;
                        case R.id.btn_crypto_sha512:
                            result = CryptoHelper.encryptSHA512ToString(data);
                            break;
                    }
                    mEtResult.setText(result);
                    Log.d(Constant.TAG, mClassName + " data : " + data + "\nresult : " + result);
                }
                break;
            case R.id.btn_crypto_hmacmd5:
            case R.id.btn_crypto_hmacsha1:
            case R.id.btn_crypto_hmacsha224:
            case R.id.btn_crypto_hmacsha256:
            case R.id.btn_crypto_hmacsha384:
            case R.id.btn_crypto_hmacsha512:
                if (checkData() && checkKey()) {
                    switch (v.getId()) {
                        case R.id.btn_crypto_hmacmd5:
                            result = CryptoHelper.encryptHmacMD5ToString(data, key);
                            break;
                        case R.id.btn_crypto_hmacsha1:
                            result = CryptoHelper.encryptHmacSHA1ToString(data, key);
                            break;
                        case R.id.btn_crypto_hmacsha224:
                            // 不知道什么原因，加密结果与 http://tool.oschina.net/encrypt?type=2 的加密结果不一样
                            result = CryptoHelper.encryptHmacSHA224ToString(data, key);
                            break;
                        case R.id.btn_crypto_hmacsha256:
                            result = CryptoHelper.encryptHmacSHA256ToString(data, key);
                            break;
                        case R.id.btn_crypto_hmacsha384:
                            // 不知道什么原因，加密结果与 http://tool.oschina.net/encrypt?type=2 的加密结果不一样
                            result = CryptoHelper.encryptHmacSHA384ToString(data, key);
                            break;
                        case R.id.btn_crypto_hmacsha512:
                            result = CryptoHelper.encryptHmacSHA512ToString(data, key);
                            break;
                    }
                    mEtResult.setText(result);
                    Log.d(Constant.TAG, mClassName + " data : " + data + "\nkey : " + key
                            + "\nresult : " + result);
                }
                break;
            default:
                break;
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
     * 检查输入待加密数据
     *
     * @return 如果符合输入规则，则返回true；不符合则返回false
     */
    private boolean checkData() {
        if (TextUtils.isEmpty(mEtData.getText().toString())) {
            mEtData.requestFocus();
            mEtResult.setText("");
            showToast(R.string.crypto_message_digest_data_hint);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SELECT_FILE:
                if (resultCode == RESULT_OK) {
                    String path = FileUtils.getPath(mContext, data.getData());
                    String result = CryptoHelper.encryptMD5FileToString(path);
                    mEtResult.setText(result);
                    Log.d(Constant.TAG, mClassName + " path : " + path + "\nresult : " + result);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

}
