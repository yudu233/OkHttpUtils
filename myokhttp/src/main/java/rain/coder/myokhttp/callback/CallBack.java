package rain.coder.myokhttp.callback;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rain.coder.myokhttp.OkHttpUtils;
import rain.coder.myokhttp.response.IResponseHandler;
import rain.coder.myokhttp.utils.LogUtils;

/**
 * Describe :网络请求返回
 * Created by Rain on 17-3-9.
 */
public class CallBack implements Callback {

    private int command;
    private IResponseHandler mResponseHandler;

    public CallBack(IResponseHandler response, int command, boolean showLoading) {
        this.mResponseHandler = response;
        this.command = command;
        mResponseHandler.onStart(showLoading);
    }


    @Override
    public void onFailure(Call call, IOException e) {
        //网络请求失败 或者错误 在这里做相应的处理
        LogUtils.dLog("onFailure", String.valueOf(e));
        OkHttpUtils.handler.post(new Runnable() {
            @Override
            public void run() {
                mResponseHandler.onErrorHttpResult(command, 0);
            }
        });
    }


    @Override
    public void onResponse(Call call, final Response response) throws IOException {

        //请求返回数据
        if (response.isSuccessful()) {
            OkHttpUtils.handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        mResponseHandler.onSuccessHttpResult(command, response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            LogUtils.dLog("onFailure", "ErrorCode : " + response.code());
            mResponseHandler.onErrorHttpResult(command, response.code());
        }

    }
}
