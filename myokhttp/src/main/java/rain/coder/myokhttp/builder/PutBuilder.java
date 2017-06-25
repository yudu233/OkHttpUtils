package rain.coder.myokhttp.builder;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import rain.coder.myokhttp.OkHttpUtils;
import rain.coder.myokhttp.callback.CallBack;
import rain.coder.myokhttp.utils.LogUtils;

/**
 * 在服务器更新资源（客户端提供改变后的完整资源）。
 * Created by Rain on 17-4-18.
 */
public class PutBuilder extends OkHttpRequestBuilder<PutBuilder> {


    public PutBuilder(OkHttpUtils myOkHttp) {
        super(myOkHttp);
    }

    @Override
    void enqueue(OkHttpUtils.RequestListener responseHandler) {
        try {
            if (url == null || url.length() == 0) {
                throw new IllegalArgumentException("url can not be null !");
            }

            Request.Builder builder = new Request.Builder().url(url);
            appendHeaders(builder, headers);

            if (tag != null) {
                builder.tag(tag);
            }

            builder.put(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), ""));

            Request request = builder.build();

            myOkHttp.getOkHttpClient()
                    .newCall(request)
                    .enqueue(new CallBack(responseHandler, command,showLoading));
        } catch (Exception e) {
            LogUtils.eLog("Put enqueue error:" + e.getMessage());
        }
    }
}