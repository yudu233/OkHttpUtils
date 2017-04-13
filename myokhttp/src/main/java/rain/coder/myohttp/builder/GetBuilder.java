package rain.coder.myohttp.builder;

import java.util.Map;

import okhttp3.Request;
import rain.coder.myohttp.OkHttpUtils;
import rain.coder.myohttp.callback.CallBack;
import rain.coder.myohttp.response.IResponseHandler;

/**
 * Created by Administrator on 2017/3/9 0009.
 */
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> {

    public GetBuilder(OkHttpUtils okHttpUtils) {
        super(okHttpUtils);
    }


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
                    enqueue(new CallBack(response, command));
        } catch (Exception e) {
            // Log.e("Get enqueue error:" + e.getMessage());
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
