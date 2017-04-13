package rain.coder.myohttp.builder;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import rain.coder.myohttp.OkHttpUtils;

/**
 * Describe : OkHttp请求构造基类
 * Created by Rain on 17-3-9.
 */
public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder> {

    protected String url;
    protected String tag = null;

    protected int command;

    protected Map<String, String> headers;
    protected Map<String, String> params;

    protected OkHttpUtils myOkHttp;


    public OkHttpRequestBuilder(OkHttpUtils myOkHttp) {
        this.myOkHttp = myOkHttp;
    }


    public T command(int command) {
        this.command = command;
        return (T) this;
    }

    public T url(String url) {
        this.url = url;
        return (T) this;
    }

    public T tag(String tag) {
        this.tag = tag;
        return (T) this;
    }

    public T params(Map<String, String> params) {
        this.params = params;
        return (T) this;
    }

    public T addParam(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return (T) this;
    }

    public T headers(Map<String, String> headers) {
        this.headers = headers;
        return (T) this;
    }

    public T addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return (T) this;
    }

    //append headers into builder
    protected void appendHeaders(Request.Builder builder, Map<String, String> headers) {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }
}
