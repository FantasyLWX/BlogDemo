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
import com.fantasy.blogdemo.crypto.utils.RSAUtils;

/**
 * 非对称加密
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-08-25
 *     since   : 1.0, 2019-08-25
 * </pre>
 */
public class AsymmetricEncryptActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEtPublicKey;
    private EditText mEtPrivateKey;
    private EditText mEtData;
    private EditText mEtResult;
    private int mOutputModeCheckedId;
    private String mTransformation;

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
        initData();
    }

    private void bindEvent() {
        ((TextView) findViewById(R.id.tv_title_bar_title)).setText(R.string.crypto_title_asymmetric);
        findViewById(R.id.iv_title_bar_back).setOnClickListener(this);
        mEtPublicKey = findViewById(R.id.et_crypto_public_key);
        mEtPrivateKey = findViewById(R.id.et_crypto_private_key);
        mEtData = findViewById(R.id.et_crypto_data);
        mEtResult = findViewById(R.id.et_crypto_result);
        findViewById(R.id.btn_crypto_rsa_encrypt).setOnClickListener(this);
        findViewById(R.id.btn_crypto_rsa_decrypt).setOnClickListener(this);
        ((RadioGroup) findViewById(R.id.rg_crypto_output_mode)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mOutputModeCheckedId = checkedId;
            }
        });
    }

    private void initData() {
        mOutputModeCheckedId = R.id.rb_crypto_base64;
        mTransformation = "RSA/None/PKCS1Padding";

        //String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYHGvdORdwsK5i+s9rKaMPL1O5eDK2XwNHRUWaxmGB/cxLxeinJrrqdAN+mME7XtGN9bklnOR3MUBQLVnWIn/IU0pnIJY9DpPTVc7x+1zFb8UUq1N0BBo/NpUG5olxuQULuAAHZOg28pnP/Pcb5XVEvpNKL0HaWjN8pu/Dzf8gZwIDAQAB";
        //String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJgca905F3CwrmL6z2spow8vU7l4MrZfA0dFRZrGYYH9zEvF6Kcmuup0A36YwTte0Y31uSWc5HcxQFAtWdYif8hTSmcglj0Ok9NVzvH7XMVvxRSrU3QEGj82lQbmiXG5BQu4AAdk6Dbymc/89xvldUS+k0ovQdpaM3ym78PN/yBnAgMBAAECgYAFdX+pgNMGiFC53KZ1AhmIAfrPPTEUunQzqpjE5Tm6oJEkZwXiedFbeK5nbLQCnXSH07nBT9AjNvFH71i6BqLvT1l3/ezPq9pmRPriHfWQQ3/J3ASf1O9F9CkYbq/s/qqkXEFcl8PdYQV0xU/kS4jZPP+60Lv3sPkLg2DpkhM+AQJBANTl+/v6sBqqQSS0Anl5nE15Ck3XGBcq0nvATHfFkJYtG9rrXz3ZoRATLxF1iJYwGSAtirhev9W7qFayjci0ztcCQQC25/kkFbeMEWT6/kyV8wcPIog1mKy8RVB9+2l6C8AzbWBPZYtLlB7uaGSJeZBTEGfvRYzpFm5xO0JqwCfDddjxAkBmxtgM3wqg9MwaAeSn6/Nu2x4EUfBJTtzp7P19XJzeQsyNtM73ttYwQnKYhRr5FiMrC5FKTENj1QIBSJV17QNlAkAL5cUAAuWgl9UQuo/yxQ81fdKMYfUCfiPBPiRbSv5imf/Eyl8oOGdWrLW1d5HaxVttZgHHe60NcoRce0la3oSRAkAe8OqLsm9ryXNvBtZxSG+1JUvePVxpRSlJdZIAUKxN6XQE0S9aEe/IkNDBgVeiUEtop76R2NkkGtGTwzbzl0gm";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhSzPPnFn41iaz+t4tI4kbaXNuNFOsI8hFeCYtlwPFKRbETHbBS10bMvUbOWLFtRgZV3L924GQ9orbomEmJ1nWyaSO8iBbZAyiWUP5PJJh/b9kHj1MMwG712bGfYYPdjkRprNpzU9w4UBzUMKKUoHU4c/Gbb4XeBK9LNTPWQL4YwIDAQAB";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOFLM8+cWfjWJrP63i0jiRtpc240U6wjyEV4Ji2XA8UpFsRMdsFLXRsy9Rs5YsW1GBlXcv3bgZD2ituiYSYnWdbJpI7yIFtkDKJZQ/k8kmH9v2QePUwzAbvXZsZ9hg92ORGms2nNT3DhQHNQwopSgdThz8Ztvhd4Er0s1M9ZAvhjAgMBAAECgYEAxwNLTUXsJGfn4Gzm/jC52MEZ+mu2zgT90IAGGZeg+PUG63gwHyeXo4MsCVRz7/m8xAX/ykew+IEQwFt8Pdvc+rrs5yml4gOBPfhpau5QaI75xNjnyH7UA3mbRCZeyZrvuKqtY/f8pCgzy3EBWnRpkcsqeE6bsOQrD45mltr+0QECQQDynvhKEh+hD5xBpF/DIP8Fp6fizexHdA6+aZT/gLaFA4XgZ9HEDDBhvNdadyYUNOLWhkxRHv6CkT5azfLXsJEhAkEA7begtbBCDXDf1+DRh3j2S8zcv6+utYgcpjvxZqjbPi6UIWXLxI80PIwQ0uouHCUMjikBA6VX9vTbw9TZ/IelAwJBAKI3W7baiz86mrTg3A4w/5GeWQexuurDVCBHo5F5U493nYk+oOe9ZpPSmQIpa9JS0d+xB1GtsWlHBzPbQySnL0ECQA/btCjqvT1QTl6EbPXwp92eqQtQmQMbNW4RiaUjlpyrVs5zkAho1T9EyMqJPNI71n6VVa/8k8WxyAdkZ7ZlBikCQEkNe1+sAKnh+AFGCJ+6WAq1J2RuIgcA6bVL3ip7F2NHdE+N+tR9JqWw3JNCweWmAlzKIGs6eKSVD5egzKaLXss=";
        mEtPublicKey.setText(publicKey);
        mEtPublicKey.setSelection(mEtPublicKey.length());
        mEtPrivateKey.setText(privateKey);
        mEtPrivateKey.setSelection(mEtPrivateKey.length());
        mEtData.requestFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_bar_back:
                finish();
                break;
            case R.id.btn_crypto_rsa_encrypt: // RSA加密（公钥）
                String key = mEtPublicKey.getText().toString();
                String data = mEtData.getText().toString();
                String result;
                if (checkPublicKey() && checkData()) {
                    if (mOutputModeCheckedId == R.id.rb_crypto_base64) {
                        result = RSAUtils.encryptBase64(data, key, 1024, mTransformation);
                    } else {
                        result = RSAUtils.encryptHex(data, key, 1024, mTransformation);
                    }
                    mEtResult.setText(result);
                    Log.d(Constant.TAG, "publicKey : " + key + "\ndata : " + data + "\nresult : " + result);
                }
                break;
            case R.id.btn_crypto_rsa_decrypt: // RSA解密（私钥）
                key = mEtPrivateKey.getText().toString();
                data = mEtData.getText().toString();
                if (checkPrivateKey() && checkData()) {
                    if (mOutputModeCheckedId == R.id.rb_crypto_base64) {
                        result = RSAUtils.decryptBase64(data, key, 1024, mTransformation);
                    } else {
                        result = RSAUtils.decryptHex(data, key, 1024, mTransformation);
                    }
                    mEtResult.setText(result);
                    Log.d(Constant.TAG, "privateKey : " + key + "\ndata : " + data + "\nresult : " + result);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 检查输入的公钥
     *
     * @return 如果符合输入规则，则返回true；不符合则返回false
     */
    private boolean checkPublicKey() {
        if (TextUtils.isEmpty(mEtPublicKey.getText().toString())) {
            mEtPublicKey.requestFocus();
            mEtResult.setText("");
            showToast(R.string.crypto_public_key_hint);
            return false;
        }
        return true;
    }

    /**
     * 检查输入的私钥
     *
     * @return 如果符合输入规则，则返回true；不符合则返回false
     */
    private boolean checkPrivateKey() {
        if (TextUtils.isEmpty(mEtPrivateKey.getText().toString())) {
            mEtPrivateKey.requestFocus();
            mEtResult.setText("");
            showToast(R.string.crypto_private_key_hint);
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
