package rain.coder.okhttputils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.json.JSONException;

import rain.coder.myokhttp.OkHttpUtils;

/**
 * Describe :Activity基类
 * Created by Rain on 17-4-10.
 */
public class BaseActivity extends AppCompatActivity implements OkHttpUtils.RequestListener {

    private LoadingDialog loadingDialog;
    private boolean isShowLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 泛型简化findViewById
     *
     * @param resId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int resId) {
        return (T) super.findViewById(resId);
    }

    /**
     * 加载失败后点击后的操作
     */
    protected void onRefresh() {

    }

    /**
     * 加载完成的状态
     */
    protected void showContentView() {
        if (isShowLoading) loadingDialog.dismiss();
    }

    /**
     * 加载失败点击重新加载的状态
     */
    protected void showError() {
        if (isShowLoading) loadingDialog.dismiss();
/*        SnackBarUtils.CustomizeSnackBar(backButton, "服务器连接异常",
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.colorTextBg),
                getResources().getColor(R.color.white))
                .setAction("检查网络",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //打开网络设置
                                UtilsHelper.setNetworkMethod(BaseActivity.this);
                            }
                        }
                ).show();*/
    }

    @Override
    public void onStart(boolean showLoading) {
        isShowLoading = showLoading;
        if (showLoading == true) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.showLoading();
        }
    }

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }

    @Override
    public void onErrorHttpResult(int command, int ErrorCode, Object response) {
        showError();
    }

    @Override
    public void onSuccessHttpResult(int command, Object response) throws JSONException {

    }
}
