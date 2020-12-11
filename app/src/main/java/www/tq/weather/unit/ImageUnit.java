package www.tq.weather.unit;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUnit {

    /**
     * 从Assets中读取图片
     */
    public static Bitmap getBitmapFromAssets(Context context, String fileName) {
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

    /*图片保存到指定文件夹*/
    public static String saveToDir(Bitmap bitmap, String dirpath, String filname) {

        File dirfile = new File(dirpath);
        if (!dirfile.exists()) {
            dirfile.mkdirs();
        }
        File imgfile = new File(dirpath, filname);
        if (imgfile.exists()) {
            imgfile.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(imgfile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            return  imgfile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*将位图转换成字符串*/
    public static String getStrFromBitmap(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = new String(bitmapBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /*将byte转换成位图 并旋转degress度*/
    public static Bitmap getBitmapFrombyte(byte[] data,int degress) {

        Bitmap bitmap = null;
        try {
            // 将得到的照片进行 degress 度旋转，使其竖直
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            matrix.preRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /*将位图转换成base64*/
    public static String getBase64FromBitmap(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap getBitmapFromBase64(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /*将图片缩小zoomvalue*/
    public static Bitmap getSmallBitmap(Bitmap bitmap,float zoomvalue) {
        try {
            float zoomValuesize = 1/zoomvalue;
            Bitmap newBitmap = small(bitmap,zoomValuesize);
            return  newBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    /**Bitmap缩小的方法*/
    private static Bitmap small(Bitmap bitmap,float zoomvalue) {
        DbUnit.logd("Bitmap","--------zoomvalue="+zoomvalue);
        Matrix matrix = new Matrix();
        matrix.postScale(zoomvalue,zoomvalue); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }

}
