package rain.coder.myokhttp.log;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Describe : 接收请求中返回并保存Cookie
 * Created by Rain on 17-3-14.
 */
public class CookiesInterceptor implements Interceptor {

    private Context context;

    public CookiesInterceptor(Context context) {
        super();
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());

        String cookies = response.header("Set-Cookie");
        SharedPreferences preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        if (preferences.getString("header", "").isEmpty())
            preferences.edit().putString("header", cookies).commit();

        return response;
    }
}
