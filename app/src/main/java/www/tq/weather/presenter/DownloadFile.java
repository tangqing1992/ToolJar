package www.tq.weather.presenter;

import android.os.Build;
import android.os.Environment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import www.tq.weather.unit.DbUnit;

public class DownloadFile {


    private String tag = "DownloadFile";
    public static final int requestCode = 10611;
    public static final String apkdir = "apkdownload", apkname = "mixswap.apk";
    /*超时时间*/
    public static int HTTP_TIME_OUT = 50000;


    private static OkHttpClient mOkHttpClient;
    /**
     * 初始化 OkHttpClient 带有Log拦截器
     */

    /**
     * @param url      下载连接
     * @param dirpath  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String dirpath, final String fileName, final OnDownloadListener listener) {

        try {


            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
            OkHttpClient okHttpClient = null;
            okHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    }).connectTimeout(60, TimeUnit.SECONDS)//设置超时时间
                    .readTimeout(60, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(60, TimeUnit.SECONDS)//设置写入超时时间
                    .build();

            OkHttpUtils.initClient(okHttpClient);
            DbUnit.logd(tag, "下载app：url= " + url);
            Request request = new Request.Builder().url(url)
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Charset", "UTF-8")
                    .addHeader("phoneModel", Build.MODEL)
                    .addHeader("systemVersion", Build.VERSION.RELEASE)
                    .addHeader("Accept-Encoding", "application/octet-stream")
                    .addHeader("Content-Type", "text/plain")
                    .build();
            DbUnit.logd(tag, "下载app：request= " + request);
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    DbUnit.logd(tag, "onFailure：e" + e.toString());
                    // 下载失败
                    listener.onDownloadFailed(url);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    // 储存下载文件的目录
                    DbUnit.logd(tag, "存储下载目录：" + dirpath);
                    try {
                        is = response.body().byteStream();
                        long total = response.body().contentLength();
                        File file = new File(dirpath, fileName);
                        if (file == null || !file.exists()) {
                            file.createNewFile();
                        }
                        DbUnit.logd(tag, "最终路径：" + file);
                        fos = new FileOutputStream(file);
                        long sum = 0;
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            sum += len;
                            int progress = (int) (sum * 1.0f / total * 100);
                            // 下载中
                            listener.onDownloading(progress);
                        }
                        fos.flush();
                        // 下载完成
                        listener.onDownloadSuccess(url, file.getAbsolutePath());
                        DbUnit.logd(tag, "--onDownloadSuccess---" );

                    } catch (Exception e) {
                        e.printStackTrace();
                        DbUnit.logd(tag, "Exception：e" + e.toString());
                        listener.onDownloadFailed(url);
                    } finally {
                        try {
                            if (is != null)
                                is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (fos != null)
                                fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            DbUnit.logd(tag, "Exception：e" + e.toString());
            listener.onDownloadFailed(url);
        }


    }


    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String url, String fileabsolutepath);

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed(String url);
    }
}
