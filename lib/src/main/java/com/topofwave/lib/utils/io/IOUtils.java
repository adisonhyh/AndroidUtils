package com.topofwave.lib.utils.io;

import com.topofwave.lib.utils.debug.DebugLog;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @describe 对InputStream进行各类型数据的转换。转换类型包括Drawabel,Bitmap,String,byte[]等.
 * @author adison
 * @date: 2014-10-22 下午3:29:03 <br/>
 */
public class IOUtils {

    /**
     * closeQuietly:Closeable 是可以关闭的数据源或目标。调用 closeQuietly 方法可释放对象保存的资源（如打开文件）. <br/>
     * @author adison
     * @param resource
     */
    public static void closeQuietly(Closeable resource) {
        if(resource == null) {
            return;
        }

        try {
            resource.close();
        } catch(Exception e) {
            // ignore
        }
    }

    /**
     * 将InputStream流转换成BitmapDrawable。BitmapDrawable是Drawable的直接子类，可用于Drawable对象
     * @param is
     * @return
     * @throws IOException
     */
    public static BitmapDrawable toBitmapDrawable(InputStream is) throws IOException {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(is);
        is.close();
        return bitmapDrawable;
    }

    /**
     * 将InputStream流转换成Bitmap对象。
     * @param is InputStream对象
     * @return Bitmap对象
     * @throws IOException
     */
    public static Bitmap toBitmap(InputStream is) throws IOException {
        if(null == is)
            return null;
        return toBitmapDrawable(is).getBitmap();
    }

    /**
     * 将InputStream转换成StringBuffer对象。
     * @param is InputStream对象
     * @return StringBuffer对象
     * @throws IOException
     */
    public static StringBuffer toStringBuffer(InputStream is) throws IOException {
        if(null == is)
            return null;
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while((line = in.readLine()) != null) {
            buffer.append(line).append("\n");
        }
        is.close();
        return buffer;
    }

    /**
     * 将InputStream转换成String对象。
     * @param is InputStream对象
     * @return String对象
     * @throws IOException
     */
    public static String toString(InputStream is) throws IOException {
        if(null == is)
            return null;
        return toStringBuffer(is).toString();
    }

    /**
     * 将InputStream转换为字符串
     * @param is
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String convertToString(InputStream is, String encoding) throws IOException {
        if(null == is)
            return null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, encoding));
        char cache[] = new char[2 * 512];
        int cacheSize = - 1;
        StringBuffer buffer = new StringBuffer();
        while((cacheSize = reader.read(cache)) != - 1) {
            buffer.append(new String(cache, 0, cacheSize));
            cacheSize = reader.read(cache);
        }
        is.close();
        return buffer.toString();
    }

    /**
     * 将InputStream转换成字节数组。
     * @param is InputStream对象
     * @return 字节数组
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream is) throws IOException {
        if(null == is)
            return null;
        byte[] cache = new byte[1 * 1024];
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        for(int length; (length = is.read(cache)) != - 1;) {
            buffer.write(cache, 0, length);
        }
        is.close();
        return buffer.toByteArray();
    }

    /**
     * 获取InputStream的编码格式
     * @param is
     * @return
     * @throws IOException
     */
    public static String getStreamEncoding(InputStream is) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        bis.mark(2);
        byte[] first3bytes = new byte[3];
        bis.read(first3bytes);
        bis.reset();
        String encoding = null;
        if(first3bytes[0] == (byte)0xEF && first3bytes[1] == (byte)0xBB && first3bytes[2] == (byte)0xBF) {
            encoding = "utf-8";
        } else if(first3bytes[0] == (byte)0xFF && first3bytes[1] == (byte)0xFE) {
            encoding = "unicode";
        } else if(first3bytes[0] == (byte)0xFE && first3bytes[1] == (byte)0xFF) {
            encoding = "utf-16be";
        } else if(first3bytes[0] == (byte)0xFF && first3bytes[1] == (byte)0xFF) {
            encoding = "utf-16le";
        } else {
            encoding = "GBK";
        }
        return encoding;
    }

    private final static int FIND_CHARSET_CACHE_SIZE = 4 * 1024;

    private final static String CHARSET_REGX = "<meta.*charset=\"?([a-zA-Z0-9-_/]+)\"?";

    /**
     * 获取HTML的编码格式
     * @param is
     * @return
     * @throws IOException
     */
    public static String getEncodingFromHTML(InputStream is) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        bis.mark(FIND_CHARSET_CACHE_SIZE);
        byte[] cache = new byte[FIND_CHARSET_CACHE_SIZE];
        bis.read(cache);
        bis.reset();
        return getHtmlCharset(new String(cache));
    }

    public static String getHtmlCharset(String content) {
        String encoding = null;
        Matcher m = Pattern.compile(CHARSET_REGX).matcher(content);
        if(m.find()) {
            encoding = m.group(1);
        }
        return encoding;
    }

    /**
     * 音频转换成字符串
     * @param filePath
     * @return
     */
    public static String audioToString(String filePath) {
        try {
            File tmpFile = new File(filePath);
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(tmpFile));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[in.available()];
            int length;
            while((length = in.read(buffer)) != - 1) {
                baos.write(buffer, 0, length);
            }
            byte[] b = baos.toByteArray();
            return Base64.encodeToString(b, Base64.DEFAULT);
        } catch(Exception e) {
            DebugLog.i(e.getMessage());
        }
        return null;
    }

    /**
     * 文件转换成字符串
     * @param filePath
     * @return
     */
    public static String fileToString(String filePath) {
        try {
            File tmpFile = new File(filePath);
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(tmpFile));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[in.available()];
            int length;
            while((length = in.read(buffer)) != - 1) {
                baos.write(buffer, 0, length);
            }
            byte[] b = baos.toByteArray();
            return Base64.encodeToString(b, Base64.DEFAULT);
        } catch(Exception e) {
            DebugLog.i(e.getMessage());
        }
        return null;
    }

    /**
     * 将输入流中的数据全部读取出来, 一次性返回
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] load(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while((len = is.read(buffer)) != - 1)
            baos.write(buffer, 0, len);
        baos.close();
        is.close();
        return baos.toByteArray();
    }

    /**
     * 根据文件得到字节流
     * @param file
     * @return
     */
    public static byte[] getFileByte(File file) {
        if( ! file.exists()) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            int len = fis.available();
            byte[] bytes = new byte[len];
            fis.read(bytes);
            fis.close();
            return bytes;
        } catch(Exception e) {

        }

        return null;
    }

    /**
     * 比较网络流和本地数据大小
     * @param file
     * @param url
     * @return
     * @throws IOException
     */
    public static boolean compareFileAndStream(File file, String url) throws IOException {
        if( ! file.exists() || url == null) {
            return false;
        }
        URL uri = new URL(url);
        // 打开连接
        HttpURLConnection connection = (HttpURLConnection)uri.openConnection();
        InputStream in = connection.getInputStream();
        byte[] ins = load(in);
        byte[] fis = getFileByte(file);
        if(ins.length == fis.length)
            return true;
        return false;

    }

}