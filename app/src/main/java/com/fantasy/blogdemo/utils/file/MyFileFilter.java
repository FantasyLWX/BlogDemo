package com.fantasy.blogdemo.utils.file;

import java.io.File;
import java.io.FileFilter;

/**
 * 我的文件过滤器
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2018-04-12
 *     since   : 1.0, 2018-04-12
 * </pre>
 */
public class MyFileFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
        if (file.exists()) {
            if (file.isDirectory() && file.canRead() && file.canWrite()) {
                // 文件夹只要可读可写就返回
                return file.listFiles().length > 0;
            }

            if (file.isFile() && file.canRead() && file.canWrite()) {
                return true;
            }
        }
        return false;
    }
}
