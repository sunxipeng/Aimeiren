package view;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;

import com.aimeiren.aimeiren.R;

import org.xwalk.core.XWalkView;

import app.AR;
import component.AppBundle;
import component.BaseFragmentActivity;
import logic.AppLogic;


@SuppressWarnings("static-access")
public class ItemContentActivity extends BaseFragmentActivity {
    //private AppWebView _appWebView;
    private Menu menu;
    private XWalkView xWalkView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_content_layout);
        // initViewData(savedInstanceState);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        AppBundle appBundle = new AppBundle(bundle);
        //_appWebView = (AppWebView) findViewById(R.id.appWebView);
        //initWebView(_appWebView, appBundle);

        xWalkView = (XWalkView) findViewById(R.id.webview);
        initXwebview(xWalkView, appBundle);
        ActionBar actionBar = getActionBar();
        if (appBundle.isHiddenActionBar()) {
            actionBar.hide();
        } else {
            actionBar.show();
        }
    }

    private void initXwebview(XWalkView xWalkView, AppBundle appBundle) {

        String url = appBundle.getUrl();
        appBundle.putParam("isDevice", true);
        String url_web = AppLogic.getAppUrl(url);
        xWalkView.load(url_web,"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*private void initWebView(AppWebView webView, AppBundle appBundle) {
        // 初始化内部属性
        webView.init(appBundle, null);

        AppWebViewClient appWebViewClient = new AppWebViewClient(appBundle) {
            public void shouldOverrideUrlLoadingAfter(AppBundle appBundle) {
                AppLogic.startActivity(getContext(), appBundle, _appWebView);
            }

            // 画面加载完成后操作
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        };
        webView.setWebViewClient(appWebViewClient);

        AppWebChromeClient appWebChromeClient = new AppWebChromeClient();
        webView.setWebChromeClient(appWebChromeClient);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        /*if (_appWebView == null) {
            return;
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getActionBar();
        // 应用logo是否显示
        actionBar.setDisplayShowHomeEnabled(false);
        // 返回按钮是否显示
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(AR.string.msg_goback_name);

        // Place an action bar item.
        // 改为通过画面html内容获取需要什么菜单.
        this.menu = menu;
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return AppLogic.onOptionsItemSelected(this, item, _appWebView);
    }*/

}
