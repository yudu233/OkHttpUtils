package rain.coder.myohttp.response;

import org.json.JSONException;

/**
 * Describe :定义接口，在该方法中做数据的处理
 * Created by Rain on 17-3-9.
 */
public interface IResponseHandler {

    void onErrorHttpResult(int ErrorCode);

    void onSuccessHttpResult(int command, Object response) throws JSONException;
}
