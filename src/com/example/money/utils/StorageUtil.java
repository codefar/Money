package com.example.money.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class StorageUtil {
    public static boolean isExternalStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    @TargetApi(19)
    public static boolean isExternalStorageAvailable(long neededSize) {
        if (isExternalStorageAvailable()) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long size = 0;
            if (Build.VERSION.SDK_INT >= 18) {
                size = sf.getAvailableBytes();
            } else {
                size = sf.getBlockSize() * sf.getAvailableBlocks();
            }
            return size > neededSize;
        }
        return false;
    }
}
