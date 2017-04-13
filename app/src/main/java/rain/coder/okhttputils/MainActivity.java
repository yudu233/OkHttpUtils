package rain.coder.okhttputils;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;

import rain.coder.myohttp.OkHttpUtils;
import rain.coder.myohttp.response.GsonResponseHandler;
import rain.coder.myohttp.response.IResponseHandler;

public class MainActivity extends BaseActivity implements IResponseHandler {

    private TextView mContent;

    public static final int HTTP_GET = 101;
    public static final int HTTP_POST = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContent = (TextView) findViewById(R.id.txvResult);

        loadData();

        findViewById(R.id.btnGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });

        findViewById(R.id.btnError).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils.get().url("http://rain-coder.oss-cn-shanghai.aliyuncs.com/user.json1")
                        .command(HTTP_GET).enqueue(new GsonResponseHandler(MainActivity.this, UserInfo.class));

            }
        });

    }

    private void loadData() {

        OkHttpUtils.get().url("http://rain-coder.oss-cn-shanghai.aliyuncs.com/user.json")
                .command(HTTP_GET).enqueue(new GsonResponseHandler(MainActivity.this, UserInfo.class));

    }

    @Override
    public void onErrorHttpResult(int ErrorCode) {
        showError();
    }

    @Override
    public void onSuccessHttpResult(int command, Object response) throws JSONException {
        showContent();
        if (command == HTTP_GET) {
            UserInfo userInfo = (UserInfo) response;
            mContent.setText(userInfo.getPerson().get(0).getName());

        } else if (command == HTTP_POST) {

        }
    }

    @Override
    protected void onRefresh() {
        loadData();
    }
}
