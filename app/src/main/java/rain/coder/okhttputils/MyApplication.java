package rain.coder.okhttputils;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import rain.coder.myohttp.OkHttpUtils;
import rain.coder.myohttp.log.LoggerInterceptor;

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

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor(true))        //log拦截器
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)      //链接超时时间
                .readTimeout(10000L, TimeUnit.MILLISECONDS)         //读取超时时间
                .build();

        okHttpUtils = new OkHttpUtils(okHttpClient);


    }
}
