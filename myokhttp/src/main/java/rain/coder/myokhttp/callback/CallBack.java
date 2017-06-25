package rain.coder.myokhttp.callback;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rain.coder.myokhttp.OkHttpUtils;
import rain.coder.myokhttp.utils.LogUtils;

/**
 * Describe :网络请求返回
 * Created by Rain on 17-3-9.
 */
public class CallBack implements Callback {

    private int command;
    private OkHttpUtils.RequestListener requestListener;

    public CallBack(OkHttpUtils.RequestListener response, int command, boolean showLoading) {
        this.requestListener = response;
        this.command = command;
        requestListener.onStart(showLoading);
    }


    @Override
    public void onFailure(Call call, IOException e) {
        //网络请求失败 或者错误 在这里做相应的处理
        LogUtils.dLog("onFailure", String.valueOf(e));
        OkHttpUtils.handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    requestListener.onErrorHttpResult(command, 0, null);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {

        if (!response.isSuccessful()) {
            LogUtils.dLog("onFailure", " response is not successful ! ErrorCode : " + response.code());
        }
        //请求返回数据
        if (response.isSuccessful()) {
            OkHttpUtils.handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        requestListener.onSuccessHttpResult(command, response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            try {
                requestListener.onErrorHttpResult(command, response.code(), response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
