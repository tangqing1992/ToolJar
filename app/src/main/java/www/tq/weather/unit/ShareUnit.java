package www.tq.weather.unit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

public class ShareUnit {

    public static boolean shareTxt(Context context, String dialogTitle, String contentTitle, String content) {
        try {
            /** * 分享文字内容 */
            Intent share_intent = new Intent();
            share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
            share_intent.setType("text/plain");//设置分享内容的类型
            share_intent.putExtra(Intent.EXTRA_SUBJECT, contentTitle);//添加分享内容标题
            share_intent.putExtra(Intent.EXTRA_TEXT, content);//添加分享内容
            //创建分享的Dialog
            share_intent = Intent.createChooser(share_intent, dialogTitle);
            context.startActivity(share_intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String MIME_TYPE_IMAGE = "image/*";
    public static boolean sharePic(Context context, String title, String picFilePath) {
        File shareFile = new File(picFilePath);
        if (!shareFile.exists()) return false;
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", shareFile);
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(shareFile));
        }
        intent.setType(MIME_TYPE_IMAGE);
        Intent chooser = Intent.createChooser(intent, title);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(chooser);
        }
        return true;
    }

    public static boolean sharePic(Activity context, String title, String picFilePath, int requestCode) {
        File shareFile = new File(picFilePath);
        if (!shareFile.exists()) return false;
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", shareFile);
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(shareFile));
        }
        intent.setType(MIME_TYPE_IMAGE);
        Intent chooser = Intent.createChooser(intent, title);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivityForResult(chooser,requestCode);
        }
        return true;
    }


}
