package rain.coder.myohttp;

import android.os.Handler;
import android.os.Looper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import rain.coder.myohttp.builder.GetBuilder;
import rain.coder.myohttp.builder.PostBuilder;
import rain.coder.myohttp.builder.UploadBuilder;

/**
 * okHttp网络请求助手类
 * Created by Administrator on 2017/3/9 0009.
 */
public class OkHttpUtils {
    public static final String TAG = "okHttp";

    //OkHttpUtils实例
    private volatile static OkHttpUtils mInstance;

    //OkHttpClient对象
    private static OkHttpClient mOkHttpClient;


    //json请求
    public static final MediaType JSON = MediaType
            .parse("application/json; charset=utf-8");

    public static Handler handler = new Handler(Looper.getMainLooper());

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (mOkHttpClient == null)
            synchronized (OkHttpUtils.class) {
                this.mOkHttpClient = okHttpClient;
            }
    }

    public static OkHttpUtils getInstance() {
        return mInstance;
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * okHttp GET
     */
    public static GetBuilder get() {
        return new GetBuilder(getInstance());
    }

    public static PostBuilder post() {
        return new PostBuilder(getInstance());
    }

    public static UploadBuilder upload() {
        return new UploadBuilder(getInstance());
    }

}
