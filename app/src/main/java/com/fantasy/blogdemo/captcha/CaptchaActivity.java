package com.fantasy.blogdemo.captcha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fantasy.blogdemo.Constant;
import com.fantasy.blogdemo.R;
import com.fantasy.blogdemo.base.BaseActivity;

/**
 * 图形验证码
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-06-28
 *     since   : 1.0, 2019-06-28
 * </pre>
 */
public class CaptchaActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEtCaptcha;
    private ImageView mIvCaptcha;
    private Captcha mCaptcha;

    /**
     * 打开“图形验证码”模块
     *
     * @param context 上下文
     */
    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, CaptchaActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);
        bindEvent();
        initData();
    }

    private void bindEvent() {
        ((TextView) findViewById(R.id.tv_title_bar_title)).setText(R.string.main_captcha);
        findViewById(R.id.iv_title_bar_back).setOnClickListener(this);
        mEtCaptcha = findViewById(R.id.et_captcha);
        mIvCaptcha = findViewById(R.id.iv_captcha);
        findViewById(R.id.btn_captcha).setOnClickListener(this);

        mIvCaptcha.setOnClickListener(this);

        mEtCaptcha.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handleResult();
                    return true;
                }
                return false;
            }
        });
    }

    private void initData() {
        mEtCaptcha.requestFocus();
        mCaptcha = Captcha.getInstance()
                .setType(Captcha.TYPE.CHARS)
                .setSize(200, 80)
                .setBackgroundColor(Color.WHITE)
                .setLength(4)
                .setLineNumber(2)
                .setFontSize(50)
                .setFontPadding(20, 20, 45, 15);
        mIvCaptcha.setImageBitmap(mCaptcha.create());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_bar_back:
                finish();
                break;
            case R.id.iv_captcha:
                mIvCaptcha.setImageBitmap(mCaptcha.create());
                break;
            case R.id.btn_captcha:
                handleResult();
                break;
            default:
                break;
        }
    }

    private void handleResult() {
        String result = mEtCaptcha.getText().toString();
        if (TextUtils.isEmpty(result)) {
            showToast(R.string.captcha_hint);
        } else {
            String code = mCaptcha.getCode();
            Log.d(Constant.TAG, mClassName + " result : " + result + " code : " + code);
            if (result.equals(code)) {
                showToast(R.string.captcha_correct);
            } else {
                showToast(R.string.captcha_wrong);
            }
        }
    }

}
