package rain.coder.myohttp.builder;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import rain.coder.myohttp.OkHttpUtils;
import rain.coder.myohttp.callback.CallBack;
import rain.coder.myohttp.response.IResponseHandler;

/**
 * okHttp PostBuilder
 * Created by Administrator on 2017/3/9 0009.
 */
public class PostBuilder extends OkHttpRequestBuilder<PostBuilder> {

    private String mJsonParams = "";

    public PostBuilder(OkHttpUtils myOkHttp) {
        super(myOkHttp);
    }

    public PostBuilder jsonParams(String json) {
        this.mJsonParams = json;
        return this;
    }

    public void enqueue(IResponseHandler responseHandler) {
        if (url == null || url.length() == 0)
            throw new IllegalArgumentException("url can not be null !");

        Request.Builder builder = new Request.Builder().url(url);
        appendHeaders(builder, headers);

        if (tag != null)
            builder.tag(tag);
        if (mJsonParams.length() > 0) {  //上传json格式的参数
            RequestBody requestBody = RequestBody.create(myOkHttp.getInstance().JSON, mJsonParams);
            builder.post(requestBody);
        } else {     //上传普通key-value参数
            FormBody.Builder encodingBuilder = new FormBody.Builder();
            appendParams(encodingBuilder, params);
            builder.post(encodingBuilder.build());
        }

        Request request = builder.build();
        myOkHttp.getOkHttpClient().newCall(request).enqueue(new CallBack(responseHandler, command));
    }

    //append params to form builder
    private void appendParams(FormBody.Builder builder, Map<String, String> params) {

        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }

    }
}
