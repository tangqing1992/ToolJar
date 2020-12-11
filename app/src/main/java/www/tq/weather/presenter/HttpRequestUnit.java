package www.tq.weather.presenter;


import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.https.HttpsUtils;
import java.net.URLEncoder;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import www.tq.weather.model.DbUnit;


public class HttpRequestUnit {

    private static String tag = "HttpRequestUnit";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    public static final String content_type_applicationurlecoded = "application/x-www-form-urlencoded";
    public static final String content_type_multipart = "multipart/form-data";
    public static final String content_type_applicationjson = "application/json";
    public static final String content_type_applicationxml = "application/xml";
    public static  final String xtoken = "xtoken";
    /*超时时间*/
    public static int HTTP_TIME_OUT = 10000;

    /*content_Type 返回内容的MIME类型	Content-Type: application/x-www-form-urlencoded; charset=utf-8*/
    public static MediaType getMediaTypeJson(String content_Type,String charset){
        try {
            return  MediaType.parse(content_Type+"; charset="+charset);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    }

    /*ishttps : 访问请求是否是https*/
    public static void get(Activity activity, boolean ishttps, String url,String token, Map<String, String> params,  final Handler mHandler, int msg_success, int msg_timeout){

        String paramdata = "";
        try {
            //处理参数
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : params.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(params.get(key), "utf-8")));
                pos++;
            }
            paramdata = tempParams.toString();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        DbUnit.logd(tag,"-----url="+url);
        DbUnit.logd(tag,"-----paramdata="+paramdata);

        OkHttpClient mOkHttpClient = null;
        if (ishttps)
        {
            // 这就是信任所有证书
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
            mOkHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .build();
            OkHttpUtils.initClient(mOkHttpClient);

        }

        if (token==null||token.equals(""))
        {
            OkHttpUtils
                    .get()
                    .url(url)
                    .params(params)
                    .addHeader("Connection", "keep-alive")
                    .addHeader("platform", "2")
                    .addHeader("phoneModel", Build.MODEL)
                    .addHeader("systemVersion", Build.VERSION.RELEASE)
                    .addHeader("appVersion", "3.2.0")
                    .tag(activity)
                    .build()
                    .connTimeOut(HTTP_TIME_OUT)
                    .readTimeOut(HTTP_TIME_OUT)
                    .writeTimeOut(HTTP_TIME_OUT)
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            DbUnit.logd(tag,"-----onError---");

                            Message msg = Message.obtain();
                            msg.what = msg_timeout;
                            msg.obj = e.toString();
                            mHandler.sendMessage(msg);

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            DbUnit.logd(tag,"-----onResponse---response="+response);

                            Message message = Message.obtain();
                            message.what = msg_success;
                            message.obj = response;
                            mHandler.sendMessage(message);

                        }

                    });
        }
        else {
            OkHttpUtils
                    .get()
                    .url(url)
                    .params(params)
                    .addHeader(xtoken, token)
                    .addHeader("Connection", "keep-alive")
                    .addHeader("platform", "2")
                    .addHeader("phoneModel", Build.MODEL)
                    .addHeader("systemVersion", Build.VERSION.RELEASE)
                    .addHeader("appVersion", "3.2.0")
                    .tag(activity)
                    .build()
                    .connTimeOut(HTTP_TIME_OUT)
                    .readTimeOut(HTTP_TIME_OUT)
                    .writeTimeOut(HTTP_TIME_OUT)
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            DbUnit.logd(tag,"-----onError---");

                            Message msg = Message.obtain();
                            msg.what = msg_timeout;
                            msg.obj = e.toString();
                            mHandler.sendMessage(msg);

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            DbUnit.logd(tag,"-----onResponse---response="+response);

                            Message message = Message.obtain();
                            message.what = msg_success;
                            message.obj = response;
                            mHandler.sendMessage(message);

                        }

                    });
        }


    }


    public static void post(Activity activity,boolean ishttps,String url,String token,Map<String ,String> params,MediaType mediaType,Handler mHandler,int msg_success,int msg_timeout){

        String paramdata = "";
        try {
            //处理参数
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : params.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(params.get(key), "utf-8")));
                pos++;
            }
            paramdata = tempParams.toString();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        DbUnit.logd(tag,"---post--url="+url);
        DbUnit.logd(tag,"---post--paramdata="+paramdata);

        OkHttpClient mOkHttpClient = null;
        if (ishttps)
        {
            // 这就是信任所有证书
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
            mOkHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .build();
            OkHttpUtils.initClient(mOkHttpClient);
        }

        if (token==null||token.equals(""))
        {
            OkHttpUtils
                    .postString()
                    .url(url)
                    .content(paramdata)
                    .mediaType(mediaType)
                    .addHeader("Connection", "keep-alive")
                    .addHeader("platform", "2")
                    .addHeader("phoneModel", Build.MODEL)
                    .addHeader("systemVersion", Build.VERSION.RELEASE)
                    .addHeader("appVersion", "3.2.0")
                    .tag(activity)
                    .build()
                    .connTimeOut(HTTP_TIME_OUT)
                    .readTimeOut(HTTP_TIME_OUT)
                    .writeTimeOut(HTTP_TIME_OUT)
                    .execute(new StringCallback()
                    {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                            DbUnit.logd(tag,"---post-onError--e="+e.toString());
                            Message msg = Message.obtain();
                            msg.what = msg_timeout;
                            msg.obj = e.toString();
                            mHandler.sendMessage(msg);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            DbUnit.logd(tag,"---post---onResponse="+response);

                            Message message = Message.obtain();
                            message.what = msg_success;
                            message.obj = response;
                            mHandler.sendMessage(message);
                        }

                    });
        }
        else {
            OkHttpUtils
                    .postString()
                    .url(url)
                    .content(paramdata)
                    .mediaType(mediaType)
                    .addHeader("Connection", "keep-alive")
                    .addHeader("platform", "2")
                    .addHeader("phoneModel", Build.MODEL)
                    .addHeader("systemVersion", Build.VERSION.RELEASE)
                    .addHeader("appVersion", "3.2.0")
                    .addHeader(xtoken, token)
                    .tag(activity)
                    .build()
                    .connTimeOut(HTTP_TIME_OUT)
                    .readTimeOut(HTTP_TIME_OUT)
                    .writeTimeOut(HTTP_TIME_OUT)
                    .execute(new StringCallback()
                    {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                            DbUnit.logd(tag,"---post-onError--e="+e.toString());
                            Message msg = Message.obtain();
                            msg.what = msg_timeout;
                            msg.obj = e.toString();
                            mHandler.sendMessage(msg);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            DbUnit.logd(tag,"---post---onResponse="+response);

                            Message message = Message.obtain();
                            message.what = msg_success;
                            message.obj = response;
                            mHandler.sendMessage(message);
                        }

                    });
        }

    }


}
