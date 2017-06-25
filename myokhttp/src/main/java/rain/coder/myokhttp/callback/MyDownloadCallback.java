package rain.coder.myokhttp.callback;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rain.coder.myokhttp.response.DownloadResponseHandler;
import rain.coder.myokhttp.utils.LogUtils;

/**
 * Describe:
 * Email:Bossrain99@163.com
 * Github:https://github.com/yudu233
 * Created by Rain on 2017/6/25 0025.
 */
public class MyDownloadCallback implements Callback {

    private static final String TAG = "MyDownloadCallback";

    private DownloadResponseHandler mDownloadResponseHandler;
    private String mFilePath;
    private Long mCompleteBytes;

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public MyDownloadCallback(DownloadResponseHandler downloadResponseHandler, String filePath, Long completeBytes) {
        mDownloadResponseHandler = downloadResponseHandler;
        mFilePath = filePath;
        mCompleteBytes = completeBytes;
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        LogUtils.eLog("onFailure", e.toString());

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(mDownloadResponseHandler != null) {
                    mDownloadResponseHandler.onFailure(e.toString());
                }
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        ResponseBody body = response.body();

        try {
            if (response.isSuccessful()) {
                //开始
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mDownloadResponseHandler != null) {
                            mDownloadResponseHandler.onStart(response.body().contentLength());
                        }
                    }
                });

                try {
                    if(response.header("Content-Range") == null || response.header("Content-Range").length() == 0){
                        //返回的没有Content-Range 不支持断点下载 需要重新下载
                        mCompleteBytes = 0L;
                    }

                    saveFile(response, mFilePath, mCompleteBytes);

                    final File file = new File(mFilePath);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(mDownloadResponseHandler != null) {
                                mDownloadResponseHandler.onFinish(file);
                            }
                        }
                    });
                } catch (final Exception e) {
                    if(call.isCanceled()) {     //判断是主动取消还是别动出错
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(mDownloadResponseHandler != null) {
                                    mDownloadResponseHandler.onCancel();
                                }
                            }
                        });
                    } else {
                        LogUtils.eLog("onResponse saveFile fail", e.toString());
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(mDownloadResponseHandler != null) {
                                    mDownloadResponseHandler.onFailure("onResponse saveFile fail." + e.toString());
                                }
                            }
                        });
                    }
                }
            } else {
                LogUtils.eLog("onResponse fail status=" + response.code());

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mDownloadResponseHandler != null) {
                            mDownloadResponseHandler.onFailure("fail status=" + response.code());
                        }
                    }
                });
            }
        } finally {
            if(body != null) {
                body.close();
            }
        }
    }

    //保存文件
    private void saveFile(Response response, String filePath, Long completeBytes) throws Exception {
        InputStream is = null;
        byte[] buf = new byte[4 * 1024];           //每次读取4kb
        int len;
        RandomAccessFile file = null;

        try {
            is = response.body().byteStream();

            file = new RandomAccessFile(filePath, "rwd");
            if(completeBytes > 0L) {
                file.seek(completeBytes);
            }

            long complete_len = 0;
            final long total_len = response.body().contentLength();
            while ((len = is.read(buf)) != -1) {
                file.write(buf, 0, len);
                complete_len += len;

                //已经下载完成写入文件的进度
                final long final_complete_len = complete_len;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mDownloadResponseHandler != null) {
                            mDownloadResponseHandler.onProgress(final_complete_len, total_len);
                        }
                    }
                });
            }
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
            }
            try {
                if (file != null) file.close();
            } catch (IOException e) {
            }
        }
    }
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
