package rain.coder.okhttputils;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import rain.coder.myokhttp.OkHttpUtils;
import rain.coder.myokhttp.https.HttpsUtils;
import rain.coder.myokhttp.log.LoggerInterceptor;

/**
 * Describe :
 * Created by Rain on 17-4-10.
 */
public class MyApplication extends Application {

    public static OkHttpUtils okHttpUtils;

    protected static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;

        HttpsUtils.SSLParams sslSocketFactory = HttpsUtils.createSSLSocketFactory();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor(true))        //log拦截器
                .connectTimeout(10, TimeUnit.SECONDS)      //链接超时时间
                .writeTimeout(10, TimeUnit.SECONDS)        //写入超时时间
                .readTimeout(10, TimeUnit.SECONDS)         //读取超时时间
                .sslSocketFactory(sslSocketFactory.sSLSocketFactory)
                .build();

        okHttpUtils = new OkHttpUtils(okHttpClient);


    }
}
