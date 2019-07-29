package com.fantasy.blogdemo.captcha;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * 图形验证码工具
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-06-27
 *     since   : 1.0, 2019-06-27
 * </pre>
 */
public class Captcha {
    private static final char[] CHARS_NUMBER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char[] CHARS_LETTER = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'};
    private static final char[] CHARS_ALL = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'};
    /**
     * 验证码图片的宽度
     */
    private int mWidth;
    /**
     * 验证码图片的高度
     */
    private int mHeight;
    private int mBackgroundColor;
    /**
     * 验证码的长度
     */
    private int mLength;
    /**
     * 干扰线的数量
     */
    private int mLineNumber;
    private int mFontSize;
    /**
     * 字体左边距
     */
    private int mFontPaddingLeft;
    /**
     * 字体左边距范围值
     */
    private int mFontPaddingLeftRang;
    /**
     * 字体上边距
     */
    private int mFontPaddingTop;
    /**
     * 字体上边距范围值
     */
    private int mFontPaddingTopRang;
    private TYPE mType;
    private Random mRandom;

    private static Captcha sInstance;
    /**
     * 生成的验证码
     */
    private String mCode;

    public enum TYPE {
        NUMBER, LETTER, CHARS
    }

    private Captcha() {
        mType = TYPE.CHARS;
        mWidth = 200;
        mHeight = 80;
        mBackgroundColor = Color.WHITE;
        mLength = 4;
        mLineNumber = 2;
        mFontSize = 50;
        mFontPaddingLeft = 20;
        mFontPaddingLeftRang = 20;
        mFontPaddingTop = 45;
        mFontPaddingTopRang = 15;
        mRandom = new Random();
    }

    public static Captcha getInstance() {
        if (sInstance == null) {
            synchronized (Captcha.class) {
                sInstance = new Captcha();
            }
        }
        return sInstance;
    }

    /**
     * 设置验证码类型，有：数字、英文字母、数字加英文字母
     *
     * @param type 类型
     * @return CaptchaUtils实例
     */
    public Captcha setType(TYPE type) {
        mType = type;
        return sInstance;
    }

    /**
     * 设置验证码图片大小
     *
     * @param width  宽度
     * @param height 高度
     * @return CaptchaUtils实例
     */
    public Captcha setSize(int width, int height) {
        mWidth = width;
        mHeight = height;
        return sInstance;
    }

    /**
     * 设置背景颜色
     *
     * @param color 颜色，十六进制形式，例如：0xAEAEAEAE
     * @return CaptchaUtils实例
     */
    public Captcha setBackgroundColor(int color) {
        mBackgroundColor = color;
        return sInstance;
    }

    /**
     * 设置验证码的长度
     *
     * @param length 长度
     * @return CaptchaUtils实例
     */
    public Captcha setLength(int length) {
        mLength = length;
        return sInstance;
    }

    /**
     * 设置干扰性数量
     *
     * @param number 数量
     * @return CaptchaUtils实例
     */
    public Captcha setLineNumber(int number) {
        mLineNumber = number;
        return sInstance;
    }

    /**
     * 设置字体大小
     *
     * @param size 字体大小
     * @return CaptchaUtils实例
     */
    public Captcha setFontSize(int size) {
        mFontSize = size;
        return sInstance;
    }

    /**
     * 设置字体间距
     *
     * @param paddingLeft 左边距
     * @param paddingTop  上边距
     * @return CaptchaUtils实例
     */
    public Captcha setFontPadding(int paddingLeft, int paddingTop) {
        mFontPaddingLeft = paddingLeft;
        mFontPaddingTop = paddingTop;
        return sInstance;
    }

    /**
     * 设置字体间距
     *
     * @param paddingLeft     左边距
     * @param paddingLeftRang 左边距范围值
     * @param paddingTop      上边距
     * @param paddingTopRang  上边距范围值
     * @return CaptchaUtils实例
     */
    public Captcha setFontPadding(int paddingLeft, int paddingLeftRang, int paddingTop,
                                  int paddingTopRang) {
        mFontPaddingLeft = paddingLeft;
        mFontPaddingLeftRang = paddingLeftRang;
        mFontPaddingTop = paddingTop;
        mFontPaddingTopRang = paddingTopRang;
        return sInstance;
    }

    /**
     * 获取生成的图形验证码
     *
     * @return 图形验证码的字符串
     */
    public String getCode() {
        return mCode;
    }

    /**
     * 生成图形验证码
     *
     * @return 图形验证码的图片
     */
    public Bitmap create() {
        mCode = createCode();

        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(mBackgroundColor);
        Paint paint = new Paint();
        paint.setTextSize(mFontSize);

        int fontPaddingLeft = 0;
        for (int i = 0; i < mCode.length(); i++) {
            getRandomTextStyle(paint);
            fontPaddingLeft += getRandomFontPaddingLeft();
            canvas.drawText(String.valueOf(mCode.charAt(i)), fontPaddingLeft, getRandomFontPaddingTop(), paint);
        }

        for (int i = 0; i < mLineNumber; i++) {
            drawLine(canvas, paint);
        }

        canvas.save();
        canvas.restore();
        return bitmap;
    }

    /**
     * 生成图形验证码
     *
     * @return 图形验证码的字符串
     */
    private String createCode() {
        StringBuilder buffer = new StringBuilder();
        switch (mType) {
            case NUMBER:
                for (int i = 0; i < mLength; i++) {
                    buffer.append(CHARS_NUMBER[mRandom.nextInt(CHARS_NUMBER.length)]);
                }
                break;
            case LETTER:
                for (int i = 0; i < mLength; i++) {
                    buffer.append(CHARS_LETTER[mRandom.nextInt(CHARS_LETTER.length)]);
                }
                break;
            case CHARS:
                for (int i = 0; i < mLength; i++) {
                    buffer.append(CHARS_ALL[mRandom.nextInt(CHARS_ALL.length)]);
                }
                break;
            default:
                for (int i = 0; i < mLength; i++) {
                    buffer.append(CHARS_ALL[mRandom.nextInt(CHARS_ALL.length)]);
                }
                break;
        }

        return buffer.toString();
    }

    /**
     * 获取随机颜色
     *
     * @return 颜色
     */
    private int getRandomColor() {
        int red = mRandom.nextInt(256);
        int green = mRandom.nextInt(256);
        int blue = mRandom.nextInt(256);
        return Color.rgb(red, green, blue);
    }

    /**
     * 获取随机文本样式
     *
     * @param paint 涂料
     */
    private void getRandomTextStyle(Paint paint) {
        int color = getRandomColor();
        paint.setColor(color);
        paint.setFakeBoldText(mRandom.nextBoolean()); // true为粗体，false为非粗体
        int skewX = mRandom.nextInt(11) / 10;
        skewX = mRandom.nextBoolean() ? skewX : -skewX;
        paint.setTextSkewX(skewX); // 负数表示右斜，整数左斜
        // paint.setUnderlineText(true); // true为下划线，false为非下划线
        // paint.setStrikeThruText(true); // true为删除线，false为非删除线
    }

    /**
     * 获取随机字体左边距
     *
     * @return 左边距
     */
    private int getRandomFontPaddingLeft() {
        return mFontPaddingLeft + mRandom.nextInt(mFontPaddingLeftRang);
    }

    /**
     * 获取随机字体上边距
     *
     * @return 上边距
     */
    private int getRandomFontPaddingTop() {
        return mFontPaddingTop + mRandom.nextInt(mFontPaddingTopRang);
    }

    /**
     * 生成干扰性
     *
     * @param canvas 画布
     * @param paint  涂料
     */
    private void drawLine(Canvas canvas, Paint paint) {
        int color = getRandomColor();
        int startX = mRandom.nextInt(mWidth);
        int startY = mRandom.nextInt(mHeight);
        int stopX = mRandom.nextInt(mWidth);
        int stopY = mRandom.nextInt(mHeight);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

}
