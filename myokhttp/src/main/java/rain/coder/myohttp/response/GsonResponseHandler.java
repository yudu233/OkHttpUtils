package rain.coder.myohttp.response;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;
import rain.coder.myohttp.OkHttpUtils;
import rain.coder.myohttp.utils.LogUtils;

/**
 * Describe :Gson类型的回调接口
 * Created by Rain on 17-3-13.
 */
public class GsonResponseHandler implements IResponseHandler {

    private Type mType;
    private Class<?> tClass;

    private IResponseHandler mGsonResponse;

    public GsonResponseHandler(IResponseHandler handler) {
        this.mGsonResponse = handler;

        //反射获取带泛型的class
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class)
            throw new RuntimeException("Missing type parameter.");
        //获取所有泛型
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        //将泛型转为type
        mType = $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    public GsonResponseHandler(IResponseHandler handler, Class<?> clazz) {
        this.mGsonResponse = handler;
        this.tClass = clazz;
    }

    private Type getType() {
        return mType;
    }

    @Override
    public void onErrorHttpResult(final int ErrorCode) {
        OkHttpUtils.handler.post(new Runnable() {
            @Override
            public void run() {
                mGsonResponse.onErrorHttpResult(ErrorCode);
            }
        });
    }

    @Override
    public void onSuccessHttpResult(final int command, final Object response) {
        ResponseBody responseBody = ((Response) response).body();
        String responseBodyStr = "";

        try {
            responseBodyStr = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.eLog("onResponse failed to read response body");
            OkHttpUtils.handler.post(new Runnable() {
                @Override
                public void run() {
                    onErrorHttpResult(((Response) response).code());
                }
            });
            return;
        } finally {
            responseBody.close();
        }

        final String finalResponseBodyStr = responseBodyStr;

        OkHttpUtils.handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (tClass == null)
                        mGsonResponse.onSuccessHttpResult(command, finalResponseBodyStr);
                    else {
                        Gson gson = new Gson();
                        mGsonResponse.onSuccessHttpResult(command, gson.fromJson(finalResponseBodyStr, tClass));
                    }
                    // Gson gson = new Gson();
                    // onSuccessResult(((Response) response).code(),(T)gson.fromJson(finalResponseBodyStr,getType()));

                } catch (JSONException e) {
                    e.printStackTrace();
                    LogUtils.eLog("onResponse failed to parse gson,body=" + finalResponseBodyStr);
                    OkHttpUtils.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onErrorHttpResult(((Response) response).code());
                        }
                    });
                }
            }
        });
    }

    //public abstract void onSuccessResult(int statusCode, T response);

}
