package com.fantasy.blogdemo.utils.file;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * 处理文件工具类
 * <pre>
 *     author  : Fantasy
 *     version : 1.5, 2018-12-06
 *     since   : 1.0, 2017-11-21
 * </pre>
 */
public class FileUtils {
    /**
     * 升序
     */
    public static final int ASC = 0;
    /**
     * 降序
     */
    public static final int DESC = 1;

    /**
     * 删除文件
     *
     * @param path 绝对地址
     * @return 删除成功，则返回true，删除失败，则返回false
     */
    public static boolean delete(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        } else {
            File file = new File(path);
            return file.exists() && file.delete();
        }
    }

    /**
     * 删除文件夹里面的所有文件
     *
     * @param path 绝对地址
     * @return 删除成功，则返回true，删除失败，则返回false
     */
    public static boolean deleteAll(String path) {
        if (!path.endsWith(File.separator)) { // 如果path不以文件分隔符结尾，自动添加文件分隔符
            path = path + File.separator;
        }

        File dirFile = new File(path);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false; // 如果dirFiler对应的文件不存在，或者不是一个目录，则退出
        }

        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                file.delete(); // 删除子文件
            } else { // 删除子目录
                flag = deleteAll(file.getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            return false;
        }

        return dirFile.delete(); // 删除当前目录
    }

    /**
     * 写文件
     *
     * @param fullFileName 绝对路径 + 文件名 + 类型后缀
     * @param content      内容
     */
    public static void write(String fullFileName, String content) {
        File f = new File(fullFileName);
        if (f.exists()) {
            f.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(content);
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读文件
     *
     * @param fullFileName 绝对路径 + 文件名 + 类型后缀
     */
    public static String read(String fullFileName) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();
        try {
            fileReader = new FileReader(fullFileName);
            bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }

                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    /**
     * 得到特定路径下的所有有效文件，按照文件的创建时间升序排序
     *
     * @param file 文件
     * @return 文件列表
     */
    public static List<File> getFiles(File file) {
        if (file != null && file.exists()) {
            File[] files = file.listFiles(new MyFileFilter());
            List<File> filterFile = new ArrayList<>();
            if (Collections.addAll(filterFile, files)) {
                return filterFile;
            }
        }
        return null;
    }

    /**
     * 得到特定路径下的所有有效文件，按照文件的最后的修改时间排序
     *
     * @param file 文件
     * @param rule ASC：升序，DESC：降序
     * @return 文件列表
     */
    public static List<File> getFilesByDate(File file, final int rule) {
        if (file != null && file.exists()) {
            File[] files = file.listFiles(new MyFileFilter());

            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File lhs, File rhs) {
                    long diff = lhs.lastModified() - rhs.lastModified();
                    int a;
                    int b;

                    if (rule == ASC) {
                        a = 1;
                        b = -1;
                    } else {
                        a = -1;
                        b = 1;
                    }

                    if (diff > 0) {
                        return a;
                    } else if (diff == 0) {
                        return 0;
                    } else {
                        return b; //如果 if 中修改为 返回-1 同时此处修改为返回 1  排序就会是递减
                    }
                }
            });

            List<File> filterFile = new ArrayList<>();
            if (Collections.addAll(filterFile, files)) {
                return filterFile;
            }
        }
        return null;
    }

    /**
     * 得到特定路径下的所有有效文件，按照文件的创建时间升序排序
     *
     * @param path 文件路径
     * @return 文件列表
     */
    public static List<File> getFiles(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            return getFiles(file);
        }
        return null;
    }

    /**
     * 得到特定路径下的所有有效文件，按照文件的最后的修改时间排序
     *
     * @param path 文件路径
     * @param rule ASC：升序，DESC：降序
     * @return 文件列表
     */
    public static List<File> getFilesByDate(String path, int rule) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            return getFilesByDate(file, rule);
        }
        return null;
    }

    /**
     * 获取文件最后修改日期
     *
     * @param file 文件
     * @return 时间，格式为 yyyy/MM/dd HH:mm
     */
    public static String getFileLastDate(File file) {
        if (file != null && file.exists() && file.isFile()) {
            long date = file.lastModified();
            if (date == 0) {
                return "2001/01/01 00:00";
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.CHINA);
            return dateFormat.format(date);
        }
        return null;
    }

    /**
     * 获取文件的大小
     */
    public static String getFileSize(File file) {
        String result = "0 B";
        if (file != null && file.exists() && file.isFile()) {
            DecimalFormat df = new DecimalFormat("#.##");
            long size = file.length();
            if (size < 1024) {
                result = df.format(size) + " B";
            } else if (size < 1048576) {
                result = df.format((double) size / 1024) + " KB";
            } else if (size < 1073741824) {
                result = df.format((double) size / 1048576) + " MB";
            } else {
                result = df.format((double) size / 1073741824) + " GB";
            }
        }
        return result;
    }

    /**
     * 获取文件的后缀
     */
    public static String getSuffix(File file) {
        if (file != null && file.exists() && file.isFile()) {
            String fileName = file.getName();
            if (fileName.equals("") || fileName.endsWith(".")) {
                return null;
            }
            int index = fileName.lastIndexOf(".");
            if (index != -1) {
                return fileName.substring(index + 1).toLowerCase(Locale.US);
            }
        }
        return null;
    }

    /**
     * 获取文件的MimeType
     */
    public static String getMimeType(File file) {
        String suffix = getSuffix(file);
        if (suffix == null) {
            return "file/*";
        }
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
        if (type != null && !type.isEmpty()) {
            return type;
        }
        return "file/*";
    }

    /**
     * 获取文件选择器选中的文件绝对路径
     *
     * @param context 上下文
     * @param uri     文件URI
     * @return 文件绝对路径
     */
    public static String getPath(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
                // ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                // DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                // MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // MediaStore (and general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // File
            return uri.getPath();
        }
        return null;
    }

    /**
     * 获取此Uri的数据列的值。这对MediaStore Uris和其他基于文件的ContentProviders非常有用。
     *
     * @param context       上下文
     * @param uri           要查询的uri
     * @param selection     （可选）查询中使用的过滤器
     * @param selectionArgs （可选）查询中使用的选择参数
     * @return _data列的值，通常是文件路径
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        final String column = "_data";
        final String[] projection = {column};
        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }
        return null;
    }

}
