package component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.rt.core.log.Log;
import com.rt.core.util.BaseUtil;

import js.AppJS;
import logic.AppLogic;
import util.AppConst;
import util.AppUtil;


@SuppressLint("SetJavaScriptEnabled")
public class AppWebView extends WebView {

    protected Log log = Log.getLog(this.getClass());

    private AppBundle appBundle;

    public AppWebView(Context context) {
        super(context);
        initAttrs();
    }

    public AppWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs();
    }

    public AppWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs();
    }

    public AppBundle getAppBundle() {
        return appBundle;
    }

    public String getFromUrl() {
        if (appBundle == null) {
            return null;
        }
        return appBundle.getFromUrl();
    }

    @SuppressLint("JavascriptInterface")
    public void init(AppBundle appBundle, AppJS appjs) {
        // 加载对象
        if (appjs == null) {
            addJavascriptInterface(AppJS.getInstance(), AppJS.API_NAME);
        } else {
            addJavascriptInterface(appjs, AppJS.API_NAME);
        }
        // 加载对象
        this.appBundle = appBundle;
        String url = null;
        if (appBundle != null) {
            // 初始化加载url
            url = appBundle.getUrl();
            // 加入是否为设备的标记,供画面使用.
            appBundle.putParam("isDevice", true);
            // 加载到对象
            addJavascriptInterface(appBundle.getParamVal(),
                    AppJS.API_PARAM_NAME);
            // 加载到对象
            addJavascriptInterface(appBundle.getAttrVal(), AppJS.API_ATTR_NAME);
        }
        getSettings().setSupportZoom(appBundle.isSupportZoom());

        // 最后再加载url

        String url_web = AppLogic.getAppUrl(url);
        loadUrl(url_web);
    }

    /**
     * 初始化属性
     */
    private void initAttrs() {
        // 启用javascript
        getSettings().setJavaScriptEnabled(true);
        getSettings().setSavePassword(false);
        getSettings().setSupportZoom(false);
        getSettings().setAllowFileAccess(true);
        // 缓存设置
        getSettings().setAppCacheEnabled(true);
        // 缓存1G
        getSettings().setAppCacheMaxSize(1073741824 * 1);
        getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上;
        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // 防止屏幕闪烁?
        // setBackgroundColor(0);
    }

    /**
     * 调用html页面中的js方法
     *
     * @param funName
     */
    public void callJs(String funName) {
        callJs(funName, null);
    }

    /**
     * 调用html页面中的js方法
     *
     * @param funName
     * @param param
     */
    public void callJs(String funName, String param) {
        if (BaseUtil.isEmpty(funName)) {
            return;
        }
        if (BaseUtil.isEmpty(param)) {
            if (BaseUtil.contains(funName, "function")
                    || BaseUtil.contains(funName, "()")) {
                loadUrl("javascript:" + funName);
            } else {
                loadUrl("javascript:" + funName + "()");
            }
        } else {
            loadUrl("javascript:" + funName + "('" + param + "')");
        }
    }

}
