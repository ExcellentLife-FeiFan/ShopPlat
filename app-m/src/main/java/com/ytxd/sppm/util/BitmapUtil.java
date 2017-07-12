package com.ytxd.sppm.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtil {

    /**
     * convert Bitmap to byte array
     */
    public static byte[] bitmapToByte(Bitmap b) {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    /**
     * convert byte array to Bitmap
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * scale image
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight());
    }

    /**
     * scale image
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
        if (org == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }

    public static Bitmap toRoundCorner(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        //paint.setColor(Color.TRANSPARENT);
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap createBitmapThumbnail(Bitmap bitMap, boolean needRecycle) {
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 设置想要的大小
        int newWidth = 120;
        int newHeight = 120;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height,
                matrix, true);
        if (needRecycle) bitMap.recycle();
        return newBitMap;
    }

    public static boolean saveBitmap(Bitmap bitmap, File file) {
        if (bitmap == null) return false;
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file, false);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean saveBitmap(Bitmap bitmap, String absPath) {
        return saveBitmap(bitmap, new File(absPath));
    }


    /**
     * 计算图片的缩放值
     * 如果图片的原始高度或者宽度大与我们期望的宽度和高度，我们需要计算出缩放比例的数值。否则就不缩放。
     * heightRatio是图片原始高度与压缩后高度的倍数，
     * widthRatio是图片原始宽度与压缩后宽度的倍数。
     * inSampleSize就是缩放值 ，取heightRatio与widthRatio中最小的值。
     * inSampleSize为1表示宽度和高度不缩放，为2表示压缩后的宽度与高度为原来的1/2(图片为原1/4)。
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions(尺寸) larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /**
     * 根据路径获得图片并压缩返回bitmap用于显示
     *
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int w, int h) {
        final BitmapFactory.Options options = new BitmapFactory.Options();

        //该值设为true那么将不返回实际的bitmap不给其分配内存空间而里面只包括一些解码边界信息即图片大小信息
        options.inJustDecodeBounds = true;//inJustDecodeBounds设置为true，可以不把图片读到内存中,但依然可以计算出图片的大小
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, w, h);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;//重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);// BitmapFactory.decodeFile()按指定大小取得图片缩略图
        return bitmap;
    }

    /**
     * 描述：获取原图.
     *
     * @param file File对象
     * @return Bitmap 图片
     */
    public static Bitmap getBitmap(File file) {
        Bitmap resizeBmp = null;
        try {
            resizeBmp = BitmapFactory.decodeFile(file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resizeBmp;
    }

    /**
     * 释放Bitmap对象.
     *
     * @param bitmap 要释放的Bitmap
     */
    public static void releaseBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                if (!bitmap.isRecycled()) {
                    Log.e("AbLogUtil",
                            "Bitmap释放" + bitmap.toString());
                    bitmap.recycle();
                }
            } catch (Exception e) {
            }
            bitmap = null;
        }
    }


    public static Bitmap FitNewSize(Bitmap bitmap, float scale) {
        int new_w = (int) (bitmap.getWidth() * scale);
        int new_h = (int) (bitmap.getHeight() * scale);

        int[] pix = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pix, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(),
                bitmap.getHeight());

        int[] new_pix = new int[new_w * new_h];
        int step = (int) (1 / scale);
        int c = step * step;
        for (int i = 0; i < new_h; i++) {
            for (int j = 0; j < new_w; j++) {

                int x = (int) (i / scale);
                int y = (int) (j / scale);

                int a = 0;
                int r = 0;
                int g = 0;
                int b = 0;

                int ww = Math.min(bitmap.getWidth(), x + step / 2);
                int hh = Math.min(bitmap.getWidth(), y + step / 2);
                for (int ii = Math.max(0, x - step / 2); ii < ww; ii++) {
                    for (int jj = Math.max(0, y - step / 2); jj < hh; jj++) {
                        int co = pix[ii * bitmap.getWidth() + jj];

                        a += Color.alpha(co);
                        r += Color.red(co);
                        g += Color.green(co);
                        b += Color.blue(co);
                    }
                }

                if (c != 0) {
                    a /= c;
                    r /= c;
                    g /= c;
                    b /= c;
                }

                new_pix[i * new_w + j] = Color.argb(a, r, g, b);
            }
        }
        Bitmap b = Bitmap.createBitmap(new_w, new_h, Bitmap.Config.ARGB_8888);
        b.setPixels(new_pix, 0, new_w, 0, 0, new_w, new_h);
        return b;
    }

    /**
     * 根据比率收缩图片
     */
    public static Bitmap shrink(Bitmap b, float scale) {
        if (true) {
            int w = (int) (scale * b.getWidth());
            int h = (int) (scale * b.getHeight());
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

            Canvas c = new Canvas(bitmap);

            c.drawBitmap(b, new Rect(0, 0, b.getWidth(), b.getHeight()),
                    new RectF(0, 0, scale * b.getWidth(), h), null);
            return bitmap;
        } else {
            return FitNewSize(b, scale);
        }
    }

    public static Bitmap getImageFromAssetsFile(String fileName, Context context) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }

    /**
     * Drawable转Bitmap.
     *
     * @param drawable 要转化的Drawable
     * @return Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Bitmap对象转换Drawable对象.
     *
     * @param bitmap 要转化的Bitmap对象
     * @return Drawable 转化完成的Drawable对象
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        BitmapDrawable mBitmapDrawable = null;
        try {
            if (bitmap == null) {
                return null;
            }
            mBitmapDrawable = new BitmapDrawable(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmapDrawable;
    }

    private static boolean checkBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            Log.e("AbLogUtil", "原图Bitmap为空了");
            return false;
        }

        if (bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
            Log.e("AbLogUtil", "原图Bitmap大小为0");
            return false;
        }
        return true;
    }
}
