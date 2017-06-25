package rain.coder.myokhttp.builder;

import org.json.JSONException;

import okhttp3.Request;
import rain.coder.myokhttp.OkHttpUtils;
import rain.coder.myokhttp.callback.CallBack;
import rain.coder.myokhttp.utils.LogUtils;

/**
 * Describe:从服务器删除资源
 * Email:Bossrain99@163.com
 * Github:https://github.com/yudu233
 * Created by Rain on 2017/6/25 0025.
 */
public class DeleteBuilder extends OkHttpRequestBuilder<DeleteBuilder> {

    private static final String TAG = "DeleteBuilder";
    private Request request;

    public DeleteBuilder(OkHttpUtils myOkHttp) {
        super(myOkHttp);
    }

    @Override
    void enqueue(OkHttpUtils.RequestListener responseHandler) {
        try {
            if (url == null || url.length() == 0) {
                throw new IllegalArgumentException("url can not be null !");
            }

            Request.Builder builder = new Request.Builder().url(url).delete();
            appendHeaders(builder, headers);

            if (tag != null) {
                builder.tag(tag);
            }

            request = builder.build();

            myOkHttp.getOkHttpClient()
                    .newCall(request)
                    .enqueue(new CallBack(responseHandler, command, showLoading));
        } catch (Exception e) {
            LogUtils.eLog("Delete enqueue error:" + e.getMessage());
            try {
                responseHandler.onErrorHttpResult(command, 0,null);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
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
