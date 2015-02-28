package com.topofwave.lib.utils.file;

import com.topofwave.lib.utils.debug.DebugLog;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @describe 文件工具类.
 * @author adison
 * @date: 2014-10-22 下午3:42:16 <br/>
 */
public class FileUtils {

    /**
     * createDirInSDCard:创建目录以及检查是否创建成功. <br/>
     * @author adison
     * @param dirName
     * @return
     */
    public static File createDirInSDCard(String dirName) {
        File file = createDirInSDCarder(Environment.getExternalStorageDirectory(), dirName);
        return file;
    }

    public static File createDirInSDCarder(File parent, String dirName) {
        File file = new File(parent, dirName);
        if( ! file.exists() || ! file.isFile()) {
            boolean isCreated = file.mkdir();
            // if(isCreated || file.isDirectory()) {
            // LogUtil.i(TAG, dirName + " create successfully!!!");
            // }
        }
        return file;
    }

    /**
     * 创建文件
     * @param path 文件路径
     * @return 创建的文件
     */
    public static File createNewFile(String path) {
        File file = new File(path);
        if( ! file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                return null;
            }
        }
        return file;
    }

    /**
     * SD卡是否可用
     * @return true 可用
     */
    public static boolean isExternalStorageMounted() {
        boolean canRead = Environment.getExternalStorageDirectory().canRead();
        boolean onlyRead = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
        boolean unMounted = Environment.getExternalStorageState().equals(Environment.MEDIA_UNMOUNTED);
        return ! ( ! canRead || onlyRead || unMounted);
    }

    /**
     * 获取SD卡剩余空间
     * @return
     */
    public static long getAvailableStorage() {

        String storageDirectory = null;
        storageDirectory = Environment.getExternalStorageDirectory().toString();

        try {
            StatFs stat = new StatFs(storageDirectory);
            long avaliableSize = ((long)stat.getAvailableBlocks() * (long)stat.getBlockSize());
            return avaliableSize;
        } catch(RuntimeException ex) {
            return 0;
        }

    }

    /**
     * SD卡是否存在
     * @return
     */
    public static boolean isSDCardPresent() {

        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    
    /**
     * 获取内部存储区/data/剩余空间
     * @return Byte字节数
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = (long)stat.getBlockSize();
        long availableBlocks = (long)stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取/data/data/cache目录
     * @param context
     * @return
     */
    public static File getCacheDirectory(Context context) {
        File appCacheDir = null;
        if(appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if(appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    /**
     * 获取/data/data/files目录
     * @param context
     * @return
     */
    public static File getFileDirectory(Context context) {
        File appCacheDir = null;
        if(appCacheDir == null) {
            appCacheDir = context.getFilesDir();
        }
        if(appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/files/";
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    /**
     * 把源文件复制到目标文件中。
     * @param source 源文件
     * @param dest 目标文件
     * @throws IOException 如果源文件不存在或者目标文件不可写入，抛出IO异常。
     */
    public static void copy(File source, File dest) throws IOException {
        FileInputStream fileIS = null;
        FileOutputStream fileOS = null;
        try {
            fileIS = new FileInputStream(source);
            fileOS = new FileOutputStream(dest);
            FileChannel fic = fileIS.getChannel();
            MappedByteBuffer mbuf = fic.map(FileChannel.MapMode.READ_ONLY, 0, source.length());
            fic.close();
            fileIS.close();
            if( ! dest.exists()) {
                String destPath = dest.getPath();
                String destDir = destPath.substring(0, destPath.lastIndexOf(File.separatorChar));
                File dir = new File(destDir);
                if( ! dir.exists()) {
                    if(dir.mkdirs()) {
                        DebugLog.i("Directory created");
                    } else {
                        DebugLog.e("Directory not created");
                    }
                }
            }
            FileChannel foc = fileOS.getChannel();
            foc.write(mbuf);
            foc.close();
            mbuf.clear();
        } catch(Exception ex) {
            DebugLog.e("Source File not exist !");
        }finally{
            if(fileOS != null){
                fileOS.close();
            }
            if(fileIS != null){
                fileIS.close();
            }
        }
    }

    /**
     * 复制文件
     * @param source 源文件路径
     * @param dest 目标文件路径
     * @throws IOException 如果源文件不存在或者目标文件不可写入，抛出IO异常。
     */
    public static void copy(String source, String dest) throws IOException {
        copy(new File(source), new File(dest));
    }

    /**
     * 保存一个输入流到指定路径中，保存完成后输入流将被关闭。
     * @param is
     * @param path
     * @throws IOException
     */
    public static void save(InputStream is, String path) throws IOException {
        save(is, path, true);
    }

    /**
     * 保存一个输入流到指定路径中
     * @param is 输入流
     * @param path 路径
     * @param closeInputStream 是否关闭输入流
     * @throws IOException
     */
    public static void save(InputStream is, String path, boolean closeInputStream) throws IOException {
        FileOutputStream os = new FileOutputStream(createFile(path));
        byte[] cache = new byte[10 * 1024];
        for(int len = 0; (len = is.read(cache)) != - 1;) {
            os.write(cache, 0, len);
        }
        os.close();
        if(closeInputStream)
            is.close();
    }

    public static File createFile(String path) throws IOException {
        File distFile = new File(path);
        if( ! distFile.exists()) {
            File dir = distFile.getParentFile();
            if(dir != null && ! dir.exists()) {
                dir.mkdirs();
            }
            distFile.createNewFile();
        }
        return distFile;
    }

    /**
     * 保存一个字节数组流到指定路径中
     * @param data
     * @param path
     */
    public static void save(byte[] data, String path) throws IOException {
        FileOutputStream os = new FileOutputStream(createFile(path));
        os.write(data, 0, data.length);
        os.close();
    }

    /**
     * 移动文件
     * @param source
     * @param dest
     * @throws IOException
     */
    public static void moveFile(String source, String dest) throws IOException {
        copy(source, dest);
        File src = new File(source);
        if(src.exists() && src.canRead()) {
            if(src.delete()) {
                DebugLog.i("Source file was deleted");
            } else {
                src.deleteOnExit();
            }
        } else {
            DebugLog.w("Source file could not be accessed for removal");
        }
    }

    /**
     * 删除文件夹及其下内容
     * @param dirPath
     * @return
     * @throws IOException
     */
    public static boolean deleteDirectory(String dirPath) throws IOException {
        if(dirPath == null)
            return false;
        return deleteDirectory(new File(dirPath));
    }

    /**
     * 删除文件夹及其下内容
     * @param dirFile
     * @return
     */
    public static boolean deleteDirectory(File dirFile) {
        boolean result = false;
        if(dirFile != null && dirFile.isDirectory()) {
            if(dirFile != null) {
                for(File file: dirFile.listFiles()) {
                    if( ! file.delete()) {
                        file.deleteOnExit();
                    }
                }
                if(dirFile.delete()) {
                    result = true;
                } else {
                    dirFile.deleteOnExit();
                }
            }
        }
        return result;
    }

    /**
     * 删除文件夹及其下内容。如果文件夹被系统锁定或者文件夹不能被清空，将返回false。
     * @param directory
     * @return 文件夹删除成功则返回true，文件夹不存在则返回false。
     * @throws IOException 如果文件夹不能被删除，则抛出异常。
     */
    public static boolean deleteDirectoryWithOSNative(String directory) throws IOException {
        boolean result = false;
        Process process = null;
        Thread std = null;
        try {
            Runtime runTime = Runtime.getRuntime();
            if(File.separatorChar == '\\') {
                process = runTime.exec("CMD /D /C \"RMDIR /Q /S " + directory.replace('/', '\\') + "\"");
            } else {
                process = runTime.exec("rm -rf " + directory.replace('\\', File.separatorChar));
            }
            std = stdOut(process);
            while(std.isAlive()) {
                try {
                    Thread.sleep(250);
                } catch(Exception e) {
                }
            }
            result = true;
        } catch(Exception e) {
            DebugLog.e("Error running delete script");
        } finally {
            if(null != process) {
                process.destroy();
                process = null;
            }
            std = null;
        }
        return result;
    }

    /**
     * 删除文件
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        boolean result = false;
        result = file.delete();
        if( ! result)
            file.deleteOnExit();
        return result;
    }

    /**
     * 使用本地系统命令重命名一个文件。
     * @param from 原文件名
     * @param to 新文件名
     */
    public static void rename(String from, String to) {
        Process process = null;
        Thread std = null;
        try {
            Runtime runTime = Runtime.getRuntime();
            if(File.separatorChar == '\\') {
                process = runTime.exec("CMD /D /C \"REN " + from + ' ' + to + "\"");
            } else {
                process = runTime.exec("mv -f " + from + ' ' + to);
            }
            std = stdOut(process);
            while(std.isAlive()) {
                try {
                    Thread.sleep(250);
                } catch(Exception e) {
                }
            }
        } catch(Exception e) {
            DebugLog.e("Error running delete script");
        } finally {
            if(null != process) {
                process.destroy();
                process = null;
                std = null;
            }
        }
    }

    /**
     * 创建一个文件夹。
     * @param directory
     * @return 创建成功则返回true，否则返回false。
     * @throws IOException
     */
    public static boolean makeDirectory(String directory) throws IOException {
        return makeDirectory(directory, false);
    }

    /**
     * 提取文件名
     * @param path
     * @return
     */
    public static String extractName(String path) {
        if(path == null)
            return null;
        boolean hasFileName = path.substring(path.length() - 5, path.length()).contains(".");
        if(hasFileName) {
            return path.substring(path.lastIndexOf(File.separator) + 1);
        } else {
            return null;
        }
    }

    /**
     * 导入文件后缀名
     * @param path
     * @return
     */
    public static String extractSuffix(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }

    /**
     * 创建一个文件夹 如果 被标记为true，则父级文件夹不存在将会被自动创建。
     * @param directory 需要被创建的文件夹
     * @param createParents 是否创建父级文件夹标识
     * @return 如果文件夹创建成功，返回true。如果文件夹已经存在，返回false。
     * @throws IOException 如果文件夹不能被创建，则抛出异常
     */
    public static boolean makeDirectory(String directory, boolean createParents) throws IOException {
        boolean created = false;
        File dir = new File(directory);
        if(createParents) {
            created = dir.mkdirs();
        } else {
            created = dir.mkdir();
        }
        return created;
    }

    /**
     * 计算文件夹大小
     * @param directory
     * @return
     * @throws IOException
     */
    public static long getSize(File directory) throws IOException {

        File[] files = directory.listFiles();
        long size = 0;
        for(File f: files) {
            if(f.isDirectory())
                size += getSize(f);
            else {
                FileInputStream fis = new FileInputStream(f);
                size += fis.available();
                fis.close();
            }
        }
        return size;
    }

    /**
     * Special method for capture of StdOut.
     * @return
     */
    private final static Thread stdOut(final Process p) {
        final byte[] empty = new byte[128];
        for(int b = 0; b < empty.length; b ++ ) {
            empty[b] = (byte)0;
        }
        Thread std = new Thread() {

            public void run() {
                StringBuilder sb = new StringBuilder(1024);
                byte[] buf = new byte[128];
                BufferedInputStream bis = new BufferedInputStream(p.getInputStream());
                try {
                    while(bis.read(buf) != - 1) {
                        sb.append(new String(buf).trim());
                        System.arraycopy(empty, 0, buf, 0, buf.length);
                    }
                    bis.close();
                } catch(Exception e) {
                    DebugLog.e(String.format("%1$s", e));
                }
            }
        };
        std.setDaemon(true);
        std.start();
        return std;
    }

    /**
     * 判断sdcard是否可写
     * @return
     */
    public static boolean isSDCardWritable() {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
