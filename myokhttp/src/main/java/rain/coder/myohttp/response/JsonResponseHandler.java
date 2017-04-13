package rain.coder.myohttp.response;

import org.json.JSONException;
import org.json.JSONTokener;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;
import rain.coder.myohttp.OkHttpUtils;
import rain.coder.myohttp.utils.LogUtils;

/**
 * Describe : JSON类型的回调接口
 * Created by Rain on 17-3-13.
 */
public class JsonResponseHandler implements IResponseHandler {

    private IResponseHandler jsonResponse;

    public JsonResponseHandler(IResponseHandler jsonResponse) {
        this.jsonResponse = jsonResponse;
    }


    @Override
    public void onErrorHttpResult(final int ErrorCode) {
        OkHttpUtils.handler.post(new Runnable() {
            @Override
            public void run() {
                jsonResponse.onErrorHttpResult(ErrorCode);
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
            LogUtils.eLog("onResponse fail read response body");
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

        try {
            JSONTokener jsonParser = new JSONTokener(responseBodyStr);
            final Object result = jsonParser.nextValue();

            OkHttpUtils.handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        jsonResponse.onSuccessHttpResult(command, result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            LogUtils.eLog("onResponse failed to parse json,body=" + finalResponseBodyStr);
            OkHttpUtils.handler.post(new Runnable() {
                @Override
                public void run() {
                    onErrorHttpResult(((Response) response).code());
                }
            });
        }
    }
}
