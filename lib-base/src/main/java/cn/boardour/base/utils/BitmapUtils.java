package cn.boardour.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Bitmap Drawable相互转换
 */
public class BitmapUtils {
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            throw new RuntimeException("drawable is null");
        }
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Drawable BitmapToDrawable(Context context, Bitmap bitmap) {
        if (bitmap == null) {
            throw new RuntimeException("bitmap is null");
        }
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static Bitmap getBitmap(Context context, int resId, int width) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, opts);
        opts.inDensity = opts.outWidth;
        opts.outWidth = width;
        opts.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), resId, opts);
    }
}
