package rain.coder.myokhttp.response;

import java.io.File;

/**
 * Describe:下载回调
 * Email:Bossrain99@163.com
 * Github:https://github.com/yudu233
 * Created by Rain on 2017/6/25 0025.
 */
public abstract class DownloadResponseHandler {

    private static final String TAG = "DownloadResponseHandler";

    public void onStart(long totalBytes) {

    }

    public void onCancel() {

    }

    public abstract void onFinish(File downloadFile);
    public abstract void onProgress(long currentBytes, long totalBytes);
    public abstract void onFailure(String error_msg);

}
/*
 *   ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 *     ┃　　　┃
 *     ┃　　　┃
 *     ┃　　　┗━━━┓
 *     ┃　　　　　　　┣┓
 *     ┃　　　　　　　┏┛
 *     ┗┓┓┏━┳┓┏┛
 *       ┃┫┫　┃┫┫
 *       ┗┻┛　┗┻┛
 *        神兽保佑
 *        代码无BUG!
 */
