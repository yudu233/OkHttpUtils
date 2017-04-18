package rain.coder.myokhttp.builder;

import java.util.Map;

import okhttp3.Request;
import rain.coder.myokhttp.OkHttpUtils;
import rain.coder.myokhttp.callback.CallBack;
import rain.coder.myokhttp.response.IResponseHandler;
import rain.coder.myokhttp.utils.LogUtils;

/**
 * Created by Administrator on 2017/3/9 0009.
 */
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> {

    public GetBuilder(OkHttpUtils okHttpUtils) {
        super(okHttpUtils);
    }

    @Override
    public void enqueue(IResponseHandler response) {
        try {
            if (url == null || url.length() == 0)
                throw new IllegalArgumentException("url can not be null !");

            if (params != null && params.size() > 0)
                url = appendParams(url, params);

            Request.Builder builder = new Request.Builder().url(url).get();
            appendHeaders(builder, headers);

            if (tag != null)
                builder.tag(tag);

            Request request = builder.build();

            myOkHttp.getOkHttpClient().
                    newCall(request).
                    enqueue(new CallBack(response, command,showLoading));
        } catch (Exception e) {
            LogUtils.eLog("Get enqueue error:" + e.getMessage());
        }
    }

    private String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
