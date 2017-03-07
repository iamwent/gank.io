package com.iamwent.gank.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;

/**
 * Created by iamwent on 25/02/2017.
 *
 * @author iamwent
 * @since 25/02/2017
 */

public final class BitmapUtil {

    public static Observable<Uri> saveImageToGallery(@NonNull Bitmap bitmap, @NonNull String fileName) {
        return saveImage(bitmap, fileName)
                .map(path -> Uri.fromFile(new File(path)));
    }

    private static Observable<String> saveImage(@NonNull Bitmap bitmap, @NonNull String fileName) {

        return Observable.create(emitter -> {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File file = new File(dir, fileName);

            String path = file.getAbsolutePath();

            FileOutputStream out = null;
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }

                out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();

                emitter.onNext(path);
                emitter.onComplete();
            } catch (IOException e) {
                emitter.onError(e);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void addImageToGallery(@NonNull Context ctx, @NonNull Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        ctx.sendBroadcast(mediaScanIntent);
    }

    private BitmapUtil() {
        throw new AssertionError("NO INSTANCE.");
    }
}
