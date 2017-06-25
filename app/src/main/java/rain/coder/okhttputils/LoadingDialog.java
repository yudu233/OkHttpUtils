package rain.coder.okhttputils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;


/**
 * Descriptions : Dialog---加载中
 * GitHub : https://github.com/Rain0413
 * Blog   : http://blog.csdn.net/sinat_33680954
 * Created by Rain on 17-2-15.
 */
public class LoadingDialog {


    private AnimationDrawable animationDrawable;
    private Context mContext;
    private int mResId;
    private Dialog mDialog;

    public LoadingDialog(Context context) {
        this.mContext = context;
        initView();
    }

    public LoadingDialog(Context context, int resId) {
        this.mContext = context;
        this.mResId = resId;
        initView();
    }

    public void initView() {
        mDialog = new Dialog(mContext, R.style.style_loadingDialog);

        //点击空白处Dialog不消失
        mDialog.setCanceledOnTouchOutside(false);
        //返回键监听拦截
        mDialog.setOnKeyListener(mListener);

        View mDialogContentView = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, null);
        mDialogContentView.findViewById(R.id.imv_loading).setBackgroundResource(mResId);
        animationDrawable = (AnimationDrawable) mDialogContentView.findViewById(R.id.imv_loading).getBackground();
        mDialog.setContentView(mDialogContentView);
    }

    Dialog.OnKeyListener mListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK)
                return true;
            else return false;
        }
    };

    public void showLoading() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}
