package com.leautolink.leautocamera.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import com.leautolink.leautocamera.config.Constant;

import java.io.File;

/**
 * Created by tianwei1 on 2015/11/28.
 */
public class SdCardUtils {
    private static final String TAG = "SdCardUtils";

    /**
     * 获取SDCard的路径---获取到SDcard下的本应用的包名路径
     */
    public static String getSDCardRootPath(Context context) {
//        1、判断SD卡的状态是否为mounted
//        2、判断获取到的SD卡的file是否exist
        File root = context.getExternalFilesDir(null);
        return root.toString();
    }

    /**
     * 获取本地视频所在的文件夹的路径
     *
     * @param context
     * @return
     */
    public static String getMVideoPath(Context context) {
        String mSDCardRootPath = getSDCardRootPath(context);
        if (mSDCardRootPath != null) {//内存卡有问题
            String mEMVideoPath = mSDCardRootPath + "/VIDEO/M_video";
            File mEMVideoFile = new File(mEMVideoPath);
            if (!mEMVideoFile.exists()) {
                mEMVideoFile.mkdirs();
            }
            return mEMVideoPath;
        }
        return null;
    }

    /**
     * 获取突发事件视频的文件路径
     *
     * @param context
     * @return
     */
    public static String getEMVideoPath(Context context) {
        String mSDCardRootPath = getSDCardRootPath(context);
        if (mSDCardRootPath != null) {//内存卡有问题
            String mEMVideoPath = mSDCardRootPath + "/EVENT/M_video";
            File mEMVideoFile = new File(mEMVideoPath);
            if (!mEMVideoFile.exists()) {
                mEMVideoFile.mkdirs();
            }
            return mEMVideoPath;
        }
        return null;
    }

    /**
     * 获取普通事件视频的文件路径
     *
     * @param context
     * @return
     */
    public static String getNMVideoPath(Context context) {
        String mSDCardRootPath = getSDCardRootPath(context);
        if (mSDCardRootPath != null) {//内存卡有问题
            String mNMVideoPath = mSDCardRootPath + "/NORMAL/M_video";
            File mNMVideoFile = new File(mNMVideoPath);
            if (!mNMVideoFile.exists()) {
                mNMVideoFile.mkdirs();
            }
            return mNMVideoPath;
        }
        return null;
    }

    /**
     * 获取图片的文件路径
     *
     * @param context
     * @return
     */
    public static String getPhotoPath(Context context) {
        String mSDCardRootPath = getSDCardRootPath(context);
        if (mSDCardRootPath != null) {//内存卡有问题
            String mPhotoPath = mSDCardRootPath + "/PHOTO/M_photo";
            File mPhotoFile = new File(mPhotoPath);
            if (!mPhotoFile.exists()) {
                mPhotoFile.mkdirs();
            }
            return mPhotoPath;
        }
        return null;
    }

    /**
     * 获取DATA根目录下的文件路径
     *
     * @return
     */
    public static String getDataRootPath(Context context) {
        String mSDCardRootPath = getSDCardRootPath(context);
        if (mSDCardRootPath != null) {//内存卡有问题
            String mDataPath = mSDCardRootPath + "/DATA";
            File mDataFile = new File(mDataPath);
            if (!mDataFile.exists()) {
                mDataFile.mkdirs();
            }
            return mDataPath;
        }
        return null;
    }
    /**
     * 获取DATA的文件路径
     *
     * @return
     */
    public static String getDataPath(Context context,String fileName) {
        String mSDCardRootPath = getSDCardRootPath(context);
        if (mSDCardRootPath != null) {//内存卡有问题
            String mDataPath = mSDCardRootPath + "/DATA/"+fileName;
            File mPhotoFile = new File(mDataPath);
            if (!mPhotoFile.exists()) {
                mPhotoFile.mkdirs();
            }
            return mDataPath;
        }
        return null;
    }

    public static final int TOTAL_LARGER_THAN_REMAINING = 2;
    public static final int TOTAL_LESS_THAN_REMAINING = 3;

    /**
     * 比较当前要下载的文件大小与SD卡剩余的内存
     *
     * @param total
     * @return
     */
    public static int compareRemainingtoTotla(Context context, long total) {
        return (total > getSdSize(context, TYPE_AVAIABLE)) ? TOTAL_LARGER_THAN_REMAINING : TOTAL_LESS_THAN_REMAINING;
    }

    public static final int TYPE_COUNT = 0;
    public static final int TYPE_AVAIABLE = 1;

    /**
     * 获取SD卡的剩余内存
     *
     * @return
     */
    public static long getSdSize(Context context, int type) {
        //获取SD卡路径
        File sdFile = Environment.getExternalStorageDirectory();
        //获取StatFs
        StatFs statFs = new StatFs(sdFile.getPath());
        long blockSize = 0l;
        long totalBlocks = 0l;
        long avaiableBlocks = 0l;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSizeLong();
            totalBlocks = statFs.getBlockCountLong();
            avaiableBlocks = statFs.getAvailableBlocksLong();
        } else {
            blockSize = statFs.getBlockSize();
            totalBlocks = statFs.getBlockCount();
            avaiableBlocks = statFs.getAvailableBlocks();
        }
        Logger.e(TAG, "总存储：" + Formatter.formatFileSize(context, blockSize * totalBlocks));
        Logger.e(TAG, "可用存储：" + Formatter.formatFileSize(context, blockSize * avaiableBlocks));
        if (TYPE_COUNT == type) {
            return blockSize * totalBlocks;
        } else if (TYPE_AVAIABLE == type) {
            return blockSize * avaiableBlocks;
        }
        return -1;
    }

    /**
     * 获取制定文件夹的大小
     *
     * @param file
     * @return
     */
    public static synchronized long getFileSize(File file) {
        long size = 0l;
        File[] flist = file.listFiles();
        if (flist != null && flist.length > 0) {
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size += getFileSize(flist[i]);
                } else {
                    size += flist[i].length();
                }
            }
        }
        return size;
    }

    /**
     * 删除文件夹里的内容
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file != null) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteFile(files[i]);
                    }
                    files[i].delete();
                }
            }
        }
    }

    /**
     * 格式化文件大小
     *
     * @param context
     * @param size
     * @return
     */
    public static String formateSize(Context context, long size) {
        return Formatter.formatFileSize(context, size);
    }

    /**
     * 判断给定path的文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean isExists(String path) {
        if (path != null) {
            File file = new File(path);
            if (file != null && file.exists()) {
                return true;
            }
        }
        return false;
    }

}
