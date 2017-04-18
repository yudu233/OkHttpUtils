package rain.coder.myokhttp.log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;
import rain.coder.myokhttp.utils.LogUtils;


/**
 * 应用拦截器 log打印
 * Created by zhy on 16/3/1.
 */
public class LoggerInterceptor implements Interceptor {


    public static String TAG = "OkHttp";
    private boolean showLog;
    private String header;

    public LoggerInterceptor(Boolean showLog) {
        this.showLog = showLog;
    }

    public LoggerInterceptor() {
        this(true);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        LogUtils.debug = showLog;

        Request request = chain.request();
        //请求耗时
        long startTime = System.nanoTime();
        Response response = chain.proceed(chain.request());
        long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        this.header = response.header("Set-Cookie");

        LogUtils.iLog(TAG, "| Method : " + clone.request().method());
        LogUtils.iLog(TAG, "| Url : " + clone.request().url());
        LogUtils.iLog(TAG, "| Code : " + clone.code());
        LogUtils.iLog(TAG, "| Protocol : " + clone.protocol());
        LogUtils.iLog(TAG, "| Set-Cookie: " + this.header);

        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        Buffer buffer = source.buffer();
        source.request(Long.MAX_VALUE);
        String responseMessage = " " + buffer.clone().readString(Util.UTF_8);
        LogUtils.iLog(TAG, "| Response:" + responseMessage);
        LogUtils.iLog(TAG, "----------End:" + duration + "毫秒----------");

        if ("POST".equals(clone.request().method())) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                if (sb.length() != 0)
                    sb.delete(sb.length() - 1, sb.length());
                LogUtils.iLog(TAG, "| RequestParams:{" + sb.toString() + "}");
            }
        }
        return response;
    }
}
