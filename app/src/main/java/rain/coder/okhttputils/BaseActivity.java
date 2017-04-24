package rain.coder.okhttputils;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONException;

import rain.coder.myokhttp.OkHttpUtils;

/**
 * Describe :
 * Created by Rain on 17-4-10.
 */
public class BaseActivity extends AppCompatActivity implements OkHttpUtils.RequestListener {

    //加载中
    private LinearLayout mLoadingLayout;
    //加载失败
    private RelativeLayout mErrorLayout;
    //内容布局
    private View childView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setContentView(@LayoutRes int layoutResID) {
        View baseView = getLayoutInflater().inflate(R.layout.activity_base, null, false);
        childView = getLayoutInflater().inflate(layoutResID, null, false);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        childView.getRootView().setLayoutParams(params);
        RelativeLayout mContainer = (RelativeLayout) baseView.getRootView().findViewById(R.id.rllContainer);
        mContainer.addView(childView.getRootView());
        getWindow().setContentView(baseView.getRootView());

        childView.getRootView().setVisibility(View.GONE);

        ViewStub mLoading = (ViewStub) baseView.findViewById(R.id.vsLoading);
        ViewStub mError = (ViewStub) baseView.findViewById(R.id.vsError);

        View mErrorView = mError.inflate();
        View mLoadingView = mLoading.inflate();

        mErrorLayout = (RelativeLayout) mErrorView.findViewById(R.id.rlError);
        mLoadingLayout = (LinearLayout) mLoadingView.findViewById(R.id.rlLoading);
        //加载失败点击刷新
        mErrorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                onRefresh();
            }
        });

    }

    /**
     * 点击刷新
     */
    protected void onRefresh() {

    }

    /**
     * 加载中动画展示
     */
    private void showLoading() {

        if (mLoadingLayout.getVisibility() != View.VISIBLE)
            mLoadingLayout.setVisibility(View.VISIBLE);
        if (mErrorLayout.getVisibility() != View.GONE)
            mErrorLayout.setVisibility(View.GONE);
        if (childView.getVisibility() != View.GONE) {
            childView.setVisibility(View.GONE);
        }
    }

    /**
     * 加载完毕展示内容
     */
    protected void showContent() {
        if (mLoadingLayout.getVisibility() != View.GONE)
            mLoadingLayout.setVisibility(View.GONE);
        if (mErrorLayout.getVisibility() != View.GONE)
            mErrorLayout.setVisibility(View.GONE);
        if (childView.getVisibility() != View.VISIBLE) {
            childView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加载失败展示
     */
    protected void showError() {

        if (mLoadingLayout.getVisibility() != View.GONE)
            mLoadingLayout.setVisibility(View.GONE);

        if (mErrorLayout.getVisibility() != View.VISIBLE)
            mErrorLayout.setVisibility(View.VISIBLE);

        if (childView.getVisibility() != View.GONE) {
            childView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart(boolean showLoading) {
        showLoading();
    }

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }

    @Override
    public void onErrorHttpResult(int command, int ErrorCode) {
        showError();
    }

    @Override
    public void onSuccessHttpResult(int command, Object response) throws JSONException {

    }
}
