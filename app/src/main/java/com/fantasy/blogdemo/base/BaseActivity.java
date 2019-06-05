package com.fantasy.blogdemo.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fantasy.blogdemo.Constant;
import com.fantasy.blogdemo.R;

/**
 * 活动基础类
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-06-05
 *     since   : 1.0, 2019-06-05
 * </pre>
 */
public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    /**
     * 当前类名
     */
    protected String mClassName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mClassName = getClass().getSimpleName();
        Log.d(Constant.TAG, mClassName + " onCreate");
    }

    @Override
    protected void onDestroy() {
        Log.d(Constant.TAG, mClassName + " onDestroy");
        super.onDestroy();
    }

    /**
     * 重写getResources()方法，让APP的字体不受系统设置字体大小影响
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 短时间的Toast
     *
     * @param message 提示内容
     */
    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间的Toast
     *
     * @param message 提示内容
     */
    protected void showToast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间的Toast
     *
     * @param message 提示内容
     */
    protected void showLongToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间的Toast
     *
     * @param message 提示内容
     */
    protected void showLongToast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 提示对话框，带有“确定”按钮，而且setCancelable(false)
     *
     * @param message  提示内容
     * @param listener “确定”按钮的点击监听器
     */
    protected void showConfirmDialog(String message, DialogInterface.OnClickListener listener) {
        AlertDialog dialog = new AlertDialog.Builder(this).setCancelable(false)
                .setMessage(message).setPositiveButton(R.string.btn_confirm, listener).create();
        dialog.show();
    }

    /**
     * 提示对话框，带有“确定”按钮，而且setCancelable(false)
     *
     * @param message  提示内容
     * @param listener “确定”按钮的点击监听器
     */
    protected void showConfirmDialog(int message, DialogInterface.OnClickListener listener) {
        AlertDialog dialog = new AlertDialog.Builder(this).setCancelable(false)
                .setMessage(message).setPositiveButton(R.string.btn_confirm, listener).create();
        dialog.show();
    }

    /**
     * 提示对话框，带有“确定”按钮
     *
     * @param message 提示内容
     */
    protected void showAlertDialog(String message) {
        AlertDialog dialog = new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton(R.string.btn_confirm, null).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 提示对话框，带有“确定”按钮
     *
     * @param message 提示内容
     */
    protected void showAlertDialog(int message) {
        AlertDialog dialog = new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton(R.string.btn_confirm, null).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 提示对话框，带有“确定”按钮
     *
     * @param title   标题
     * @param message 提示内容
     */
    protected void showAlertDialog(String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle(title).setMessage(message)
                .setPositiveButton(R.string.btn_confirm, null).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 提示对话框，带有“确定”和“取消”两个按钮
     *
     * @param message  提示内容
     * @param listener “确定”按钮的点击监听器
     */
    protected void showAlertDialog(String message, DialogInterface.OnClickListener listener) {
        AlertDialog dialog = new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton(R.string.btn_confirm, listener)
                .setNegativeButton(R.string.btn_cancel, null).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 提示对话框，带有“确定”和“取消”两个按钮
     *
     * @param message  提示内容
     * @param listener “确定”按钮的点击监听器
     */
    protected void showAlertDialog(int message, DialogInterface.OnClickListener listener) {
        AlertDialog dialog = new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton(R.string.btn_confirm, listener)
                .setNegativeButton(R.string.btn_cancel, null).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 提示对话框，带有“确定”和“取消”两个按钮
     *
     * @param title    标题
     * @param message  提示内容
     * @param listener “确定”按钮的点击监听器
     */
    protected void showAlertDialog(String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle(title).setMessage(message)
                .setPositiveButton(R.string.btn_confirm, listener)
                .setNegativeButton(R.string.btn_cancel, null).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

}
