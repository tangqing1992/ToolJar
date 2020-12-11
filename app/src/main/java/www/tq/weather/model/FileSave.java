package www.tq.weather.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSave {
    /*图片目录*/
    public static  final String dirpath_default = Environment.getExternalStorageDirectory().getAbsolutePath() + "/www.bitcon.mixswap/photos";

    /** 保存相机的图片 **/
    public static String saveCameraImage(Intent data) {
        // 检查sd card是否存在
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return null;
        }

        String cameradir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+"/Camera";
        String dirpath = "";
        try {
            if (cameradir!=null&&!cameradir.equals("")){
                File file = new File(cameradir);
                if (file.exists()&&file.isDirectory()){
                    dirpath = cameradir;
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if (dirpath==null||dirpath.equals(""))
        {
            dirpath = dirpath_default;
        }
        DbUnit.logd("dirpath","---dirpath="+dirpath);

        String name = UnitDate.getTime(System.currentTimeMillis(),"yyyyMMddHHmmss")+ ".jpg";
        Bitmap bmp = (Bitmap) data.getExtras().get("data");// 解析返回的图片成bitmap
        // 保存文件
        FileOutputStream fos = null;
        boolean direxist = newFileDir(dirpath);
        if (!direxist)
            return null;
        String fileName =dirpath+ "/" + name;// 保存路径

        try {// 写入SD card
            fos = new FileOutputStream(fileName);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }// 显示图片
        return fileName;
    }

    public static  boolean newFileDir(String dirpath){
        try {
            File file = new File(dirpath);
            if (!file.exists())
                file.mkdirs();
            if (file.exists()&&file.isDirectory())
                return true;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }


    /*//添加到图库,将媒体文件扫描到媒体库中*/
    public static void updateGallery(Context context, String path) {

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }
}
