package com.example.money.utils;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by wangyonghua on 15-11-10.
 */
public class IOUtil {

    private static final String TAG = IOUtil.class.getSimpleName();
    /**
     * Close closable object and wrap {@link IOException} with {@link RuntimeException}
     * @param closeable closeable object
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Logger.e(e);
            }
        }
    }

    /**
     * Close closable and hide possible {@link IOException}
     * @param closeable closeable object
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // Ignored
            }
        }
    }

    public static void writeObject(Serializable s, String filePath) {
        writeObject(s, new File(filePath));
    }

    public static void writeObject(Serializable s, File file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(s);
            oos.close();
        } catch (IOException e) {
            Log.e(TAG, "writeObject", e);
        } finally {
            close(oos);
            close(fos);
        }
    }

    public static boolean saveBitmap(Bitmap bitmap, String filename) {
        boolean status = true;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            status = false;
            Logger.e(TAG, e);
        } finally {
            close(out);
            return status;
        }
    }
}
