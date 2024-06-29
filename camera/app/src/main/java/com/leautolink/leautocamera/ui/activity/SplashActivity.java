package com.leautolink.leautocamera.ui.activity;

import android.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.leautolink.leautocamera.R;
import com.leautolink.leautocamera.ui.base.BaseActivity;
import com.leautolink.leautocamera.ui.view.customview.StatementDialog;
import com.leautolink.leautocamera.utils.Logger;
import com.leautolink.leautocamera.utils.SpUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    @AfterViews
    void init() {
        checkStatementPermision();
//        goDelay();
    }

    @Background(delay = 500)
    void goDelay() {
        goHome();
    }

    @UiThread
    void goHome() {
        Logger.e("========>  goHome ");
        HomeActivity_.intent(this).start();
        this.finish();
    }

    private AlertDialog mDialog;
    private View mDialogView;

    /**
     * 免责声明检测
     */
    private void checkStatementPermision() {
        goDelay();
    }
}
