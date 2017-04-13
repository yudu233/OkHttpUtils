package rain.coder.myohttp.utils;

import android.util.Log;

/**
 * Describe : Log助手类
 * Created by Rain on 17-3-10.
 */
public class LogUtils {

    // 开发调试状态
    public static boolean debug = true;

    public static final String TAG = "OkHttp : ";


    public static final void dLog(String msg) {
        if (debug)
            Log.d(TAG, msg);
    }

    public static final void dLog(String tag, String msg) {
        if (debug)
            Log.d(tag, msg);
    }

    public static final void eLog(String msg) {
        if (debug)
            Log.e(TAG, msg);
    }

    public static final void eLog(String tag, String msg) {
        if (debug)
            Log.e(tag, msg);
    }

    public static final void iLog(String msg) {
        if (debug)
            Log.i(TAG, msg);
    }

    public static final void iLog(String tag, String msg) {
        if (debug)
            Log.i(tag, msg);
    }

}
