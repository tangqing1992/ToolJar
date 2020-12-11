package www.tq.weather.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import www.tq.weather.model.MediaFile;

public class FileSearch {

    public static final Uri uri_media = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private   ExecutorService executorService = Executors.newFixedThreadPool(3);
    public static final String[] imagecondition = new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media._ID};
    public static final String[] videocondition = new String[]{MediaStore.Video.Media.DATA, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media._ID};
    public static final String[] musiccondition = new String[]{MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATE_ADDED, MediaStore.Audio.Media._ID};
    /*根据时间排序
     * @param context 上下文
     * @param conditions 需要从媒体库中查找的信息
     * */
    public  void getFiles(Context context, MediaFile.FileType fileType, OnCallBackMediaFile onCallBackMediaFile) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //扫描图片
                ContentResolver mContentResolver = context.getContentResolver();
                String [] conditions = null;
                if (fileType== MediaFile.FileType.img){
                    conditions = imagecondition;
                }

                if (fileType== MediaFile.FileType.video){
                    conditions = videocondition;
                }
                if (fileType== MediaFile.FileType.music){
                    conditions = musiccondition;
                }
                Cursor mCursor = mContentResolver.query(uri_media, conditions, null, null, conditions[2]);
                ArrayList<MediaFile> mediaFiles = new ArrayList<>();
                //读取扫描到的图片
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor.getColumnIndex(conditions[0]));
                    //获取图片名称
                    String name = mCursor.getString(mCursor.getColumnIndex(conditions[1]));
                    //获取图片时间
                    long time = mCursor.getLong(mCursor.getColumnIndex(conditions[2]));
                    mediaFiles.add(new MediaFile(path, time, name,fileType));
                }
                mCursor.close();
                Collections.reverse(mediaFiles);
                onCallBackMediaFile.callback(fileType,mediaFiles);
            }
        };
        executorService.execute(runnable);
    }


    /*将文件添加到媒体库*/
    public static void addToMedia(Context context, String path) {
        try {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File file = new File(path);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public  interface  OnCallBackMediaFile{
        void callback(MediaFile.FileType fileType, ArrayList<MediaFile> mediaFiles);
    }
}
