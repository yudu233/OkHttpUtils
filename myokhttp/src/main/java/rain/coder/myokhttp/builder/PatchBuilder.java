package rain.coder.myokhttp.builder;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import rain.coder.myokhttp.OkHttpUtils;
import rain.coder.myokhttp.callback.CallBack;
import rain.coder.myokhttp.response.IResponseHandler;
import rain.coder.myokhttp.utils.LogUtils;

/**
 * Describe:在服务器更新资源（客户端提供改变的属性）。
 * Email:Bossrain99@163.com
 * Github:https://github.com/yudu233
 * Created by Rain on 2017/6/25 0025.
 */
public class PatchBuilder extends OkHttpRequestBuilder<PatchBuilder>{

    private static final String TAG = "PatchBuilder";

    public PatchBuilder(OkHttpUtils myOkHttp) {
        super(myOkHttp);
    }

    @Override
    public void enqueue(final IResponseHandler responseHandler) {
        try {
            if(url == null || url.length() == 0) {
                throw new IllegalArgumentException("url can not be null !");
            }

            Request.Builder builder = new Request.Builder().url(url);
            appendHeaders(builder, headers);

            if (tag != null) {
                builder.tag(tag);
            }

            builder.patch(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), ""));
            Request request = builder.build();

            myOkHttp.getOkHttpClient()
                    .newCall(request)
                    .enqueue(new CallBack(responseHandler,command,showLoading));
        } catch (Exception e) {
            LogUtils.eLog("Patch enqueue error:" + e.getMessage());
            responseHandler.onErrorHttpResult(command,0);
        }
    }
}
/*
 *   ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 *     ┃　　　┃
 *     ┃　　　┃
 *     ┃　　　┗━━━┓
 *     ┃　　　　　　　┣┓
 *     ┃　　　　　　　┏┛
 *     ┗┓┓┏━┳┓┏┛
 *       ┃┫┫　┃┫┫
 *       ┗┻┛　┗┻┛
 *        神兽保佑
 *        代码无BUG!
 */
