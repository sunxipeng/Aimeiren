package view;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.aimeiren.aimeiren.R;
import com.rt.core.util.BaseUtil;

import component.AppTask;
import component.BaseActivity;
import util.AppConst;
import util.AppUtil;

/**
 * 启动程序
 */
@SuppressWarnings("static-access")
public class LauncherActivity extends BaseActivity {

    // private AppWebView _appWebView;
    private TextView skipView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_layout);
        initViewData(savedInstanceState);
        initView(savedInstanceState);
    }

    /**
     * 初始化页面数据
     */
    private void initViewData(Bundle savedInstanceState) {
    }

    /**
     * 初始化组件
     */
    private void initView(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        // _appWebView = (AppWebView) findViewById(AR.id.appWebView);
        // initWebView(_appWebView, new AppBundle(bundle));

        // 执行任务
        runTask();
    } // end initView

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 开屏页禁止用户对返回按钮的控制
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 原有webView启动画面,暂时不适用.
     * 目前已经改为gdt插件.
     *
     * @param webView
     * @param appBundle
     */
    /*private void initWebView(AppWebView webView, AppBundle appBundle) {
        // appBundle.setUrl("launcher.html");
        // 初始化内部属性
        // webView.init(appBundle, initAppJS());

        AppWebViewClient appWebViewClient = new AppWebViewClient(appBundle) {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                initLauncherImage();
            }
        };
        webView.setWebViewClient(appWebViewClient);

        AppWebChromeClient appWebChromeClient = new AppWebChromeClient();
        webView.setWebChromeClient(appWebChromeClient);
    }*/

    public void initLauncherImage() {
        // _appWebView.callJs("initLauncherImage",
        //        LauncherLogic.getLauncherImagePath());
    }

    /**
     * 启动主程序
     */
    private void startActivity() {
        startActivity(new Intent(getApplication(), MainActivity.class));
        finish();
    }

    /**
     * 启动任务
     */
    private void runTask() {
        // 进入强制等待状态.
        final long sleepTime = BaseUtil.tolong(
                AppUtil.getMsg("launcher_wait_time"),
                AppConst.LAUNCHER_WAIT_TIME_5s);
        // 后台计时器，够X秒后才启动主程序
        AppTask launcherHolder = new AppTask() {
            protected String doInBackground(String... params) {
                sleep(sleepTime);
                return null;
            }

            protected void onPostExecute(String result) {
                startActivity();
            }
        };
        launcherHolder.executeOnThreadPool();

    } // end task

}
