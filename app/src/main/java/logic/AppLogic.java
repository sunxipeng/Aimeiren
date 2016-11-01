/*
 * Created on 2008/08/21
 *
 */
package logic;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;

import com.json.JSONObject;
import com.rt.beans.IOCallbackImpl;
import com.rt.constant.RTConst;
import com.rt.core.util.BaseUtil;
import com.rt.core.util.IOUtil;
import com.rt.core.util.NetUtil;
import com.rt.core.util.TimeUtil;
import com.rt.orm.Dao;
import com.rt.security.aes.Aes;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import app.AR;
import beans.WebBean;
import component.AppBundle;
import component.AppWebView;
import util.AppConst;
import util.AppUtil;
import view.ItemContentActivity;

/**
 * 业务规则逻辑层
 */
@SuppressWarnings("static-access")
public class AppLogic extends WebLogic {

    // 请求参数
    private static JSONObject APP_PARAM;
    // 传递参数
    private static JSONObject APP_ATTR;
    // 选中图片参数
    private static ArrayList imageSelectArray;
    // 服务器URL
    private static String SERVER_URL = null;
    private static int netBufferSize = 3072;

    public static AppLogic getInstance() {
        return getInstance(AppLogic.class, true);
    }

    /**
     * 菜单跳转
     *
     * @param item
     * @return boolean
     */
    public static boolean onOptionsItemSelected(Activity activity, MenuItem item, AppWebView appWebView) {
        // 返回首页
        if (item.getItemId() == android.R.id.home) {
            activity.finish();
            return true;
        } else if (item.getItemId() == AR.string.menu_sendSns_btn_name) {
            AppBundle appBundle = new AppBundle();
            appBundle.setUrl(AppConst.LOCAL_URL_snsItemEditor);
            AppLogic.startActivity(activity, appBundle);
            return true;
        } else {
            // 自定义菜单项,根据定义属性获取跳转地址等内容,如果获取不到,不操作.
            // item.getItemId();
            Intent intent = item.getIntent();
            if (intent == null) {
                return false;
            }
            String url = intent.getStringExtra("url");
            String fun = intent.getStringExtra("fun");
            // 两个都是空,直接返回,不执行.
            if (BaseUtil.isEmpty(url) && BaseUtil.isEmpty(fun)) {
                return false;
            }
            AppBundle appBundle = new AppBundle();
            appBundle.setUrl(url);
            appBundle.setFun(fun);
            if (appWebView == null) {
                AppLogic.startActivity(activity, appBundle);
            } else {
                if (BaseUtil.isNotEmpty(url)) {
                    AppLogic.startActivity(activity, appBundle, appWebView);
                    return true;
                }
                if (BaseUtil.isNotEmpty(fun)) {
                    appWebView.loadUrl("javascript:" + fun);
                    return true;
                }
            }
            return false;
        }


    }



    /**
     * 需要登录才能访问的方法
     *
     * @param logicId
     * @return boolean
     */
    public final static boolean needLoginFun(String logicId) {
        String functionName = BaseUtil.substringAfterLast(logicId, RTConst.DOT);
        // 在需要登录才能访问的资源列表内.
        if (BaseUtil.startsWith(functionName, "index")
                || BaseUtil.startsWith(functionName, "exe")
                || BaseUtil.startsWith(functionName, "check")
                || BaseUtil.startsWith(functionName, "login")
                || BaseUtil.startsWith(functionName, "logout")) {
            return false;
        }
        return true;
    }

    public static AppBundle getLoginBundle() {
        AppBundle loginBundle = new AppBundle();
        loginBundle.setUrl("account/indexLogin.html");
        return loginBundle;
    }

    /**
     * 跳转到登录画面
     *
     * @param activity
     * @param appBundle
     */
    public static void startLoginActivity(Activity activity, AppBundle appBundle) {
        Intent intent = new Intent(activity, ItemContentActivity.class);
        intent.putExtras(appBundle.getBundle());
        activity.startActivity(intent);
    }

    /**
     * 跳转
     *
     * @param activity
     * @param appBundle
     */
    public static void startActivity(Activity activity, AppBundle appBundle) {
        startActivity(activity, appBundle, null);
    }

    /**
     * 跳转
     *
     * @param activity
     * @param appBundle
     * @param appWebView
     */
    public static void startActivity(final Activity activity,
                                     AppBundle appBundle, final AppWebView appWebView) {
        if (activity == null) {
            return;
        }
        if (appBundle == null) {
            return;
        }


        // 需要跳转到的url
        String url = appBundle.getUrl();
        String fromUrl = appBundle.getFromUrl();
        String message = appBundle.getParam("message");
        final String okFun = appBundle.getParam("okFun");
        final String cancelFun = appBundle.getParam("cancelFun");

        // 结束当前页,返回上一页.
        if (BaseUtil.contains(url, AppConst.OPEN_GOBACK_URL)) {
            AppUtil.finishTopActivity();
            return;
        }

        // 消息提示
        if (BaseUtil.contains(url, AppConst.OPEN_MESSAGETOAST_URL)) {
            AppUtil.messageToast(message, false);
            return;
        }

        // 消息提示
        if (BaseUtil.contains(url, AppConst.OPEN_MESSAGE_URL)) {
            DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (appWebView != null) {
                        appWebView.callJs(okFun, null);
                    }
                }
            };
            if (BaseUtil.isEmpty(cancelFun)) {
                AppUtil.messageDialog(activity, message, ok);
            } else {
                DialogInterface.OnClickListener cancel = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 入股只是显示取消按钮,不进行回调.
                        if (BaseUtil.isTrue(cancelFun)) {
                        } else {
                            if (appWebView != null) {
                                appWebView.callJs(cancelFun, null);
                            }
                        }
                    }
                };
                AppUtil.messageDialog(activity, message, ok, cancel);
            }
            return;
        }

        // 图片选择器
        if (BaseUtil.contains(url, AppConst.OPEN_IMAGESELE_URL)) {
            return;
        } else if (BaseUtil.contains(url, AppConst.OPEN_CAMERA_URL)) {
            activity.startActivityForResult(AppUtil.getCameraIntent(),
                    AppConst.OPEN_CAMERA_REQUEST_CODE);
            return;
        }

        // 如果跳转位置是来源位置,直接结束当前内容,返回即可.
        // TODO 还需要根据完整业务优化,避免出现反复跳转到相同位置的情况.
        if (BaseUtil.equals(fromUrl, url)) {
            AppUtil.finishTopActivity();
            return;
        }
        Intent intent = new Intent(activity, ItemContentActivity.class);
        intent.putExtras(appBundle.getBundle());
        activity.startActivity(intent);
        return;
    }

    private final static byte[] seed(String val) {
        if (val == null || val.isEmpty()) {
            return null;
        }
        val = BaseUtil.getSHA1Value(val);
        List l = new ArrayList();
        for (int i = 0; i < val.length(); i++) {
            l.add(val.substring(i, i + 1));
        }
        Collections.sort(l, new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                return arg0.compareTo(arg1);
            }
        });
        StringBuffer s = new StringBuffer();
        for (Iterator iterator = l.iterator(); iterator.hasNext(); ) {
            String v = (String) iterator.next();
            s.append(v);
        }
        if (s.length() > 16) {
            return IOUtil.toByteArray(s.substring(0, 16));
        } else {
            return IOUtil.toByteArray(s.toString());
        }
    }

    /**
     * 获取业务逻辑请求地址
     *
     * @param url
     * @return String
     */
    public final static String getLogicUrl(String url) {
        if (BaseUtil.startsWith(url, AppConst.APP_URL_HTTP)) {
            return url;
        }
        return getServerUrl() + url;
    }

    /**
     * 获取服务器url
     *
     * @return String
     */
    public static String getServerUrl() {
        // 读取上一次的服务器url地址,如果没有,使用默认值.
        return BaseUtil.isEmpty(SERVER_URL) ? AppConst.SERVER_URL_DEFAULT
                : SERVER_URL;
    }

    /**
     * 设置主服务器地址
     *
     * @param url
     */
    protected static void setServerUrl(String url, long useTime, WebBean webBean) {
        if (BaseUtil.isEmpty(url)) {
            return;
        }
        // 检查是否为合法地址
        if (AppConst.SERVER_URL_ARRAY.containsKey(url) == false) {
            return;
        }
        // TODO 分析服务器返回结果,使用负载最小且最快的服务器.

        // 获取以前更新的时间值
        long oldUseTime = AppConst.SERVER_URL_ARRAY.getlong(url);
        // 检查使用时间是否比原来的快.
        if (oldUseTime == 0 || useTime < oldUseTime) {
            // 使用最快的服务器地址.
            AppConst.SERVER_URL_ARRAY.put(url, useTime);
            SERVER_URL = url;
        }
    }

    /**
     * 添加参数
     *
     * @return Map
     */
    public static Map getAppParam() {
        if (APP_PARAM == null) {
            APP_PARAM = new JSONObject();
        }
        return APP_PARAM;
    }

    /**
     * 添加参数
     *
     * @return Map
     */
    public static JSONObject getAppAttr() {
        if (APP_ATTR == null) {
            APP_ATTR = new JSONObject();
        }
        return APP_ATTR;
    }

    /**
     * url跳转 需要传送大对象时使用,否则可直接使用内部跳转方法
     *
     * @param paramValue
     * @param attrValue
     * @return boolean
     */
    public static boolean setAppParam(String paramValue, String attrValue) {
        APP_PARAM = null;
        APP_ATTR = null;
        if (BaseUtil.isNotEmpty(paramValue) && !paramValue.equals("null")) {
            try {
                APP_PARAM = new JSONObject(paramValue);
            } catch (Exception e) {
                return false;
            }
        }
        if (BaseUtil.isNotEmpty(attrValue) && !attrValue.equals("null")) {
            try {
                APP_ATTR = new JSONObject(attrValue);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    protected String getExecutLogicName(String logicId) {
        String logicClassName = BaseUtil.substringBeforeLast(logicId,
                RTConst.DOT);

        // 构造新逻辑名
        StringBuffer logicName = new StringBuffer();
        logicName.append(AppConst.LOGIC_PACKAGE);
        logicName.append(RTConst.DOT);
        logicName.append(BaseUtil.toFirstUpperCase(logicClassName));
        logicName.append("Logic");
        return logicName.toString();
    }

    /**
     * 初始化WebBean
     *
     * @param webBeanValue
     * @return WebBean
     */
    protected WebBean initWebBean(Object webBeanValue) {
        if (webBeanValue == null) {
            return initWebBean(null);
        }
        if (webBeanValue instanceof WebBean) {
            return (WebBean) webBeanValue;
        }
        if (webBeanValue instanceof String) {
            return stringToWebBean((String) webBeanValue);
        }
        return initWebBean(null);
    }

    /**
     * 请求参数构造为webBean
     *
     * @param paramValue
     * @param attrValue
     * @return WebBean
     */
    public static WebBean paramToWebBean(String paramValue, String attrValue) {
        WebBean webBean = initWebBean(null);

        // 初始化请求参数
        if (BaseUtil.isEmpty(paramValue)) {
            return webBean;
        }
        try {
            JSONObject param = new JSONObject(toJsonString(paramValue));
            webBean.setParam(param);
        } catch (Exception e) {
            webBean.setSuccess(false);
            webBean.setMessage("param format error.");
        }

        // 初始化传递属性
        if (BaseUtil.isEmpty(attrValue)) {
            return webBean;
        }
        try {
            JSONObject attr = new JSONObject(toJsonString(attrValue));
            webBean.setAttr(attr);
        } catch (Exception e) {
            webBean.setSuccess(false);
            webBean.setMessage("attr format error.");
        }
        return webBean;
    }

    private static String toJsonString(String s) {
        StringBuffer sb = new StringBuffer();
        if (s.indexOf("\r\n") != -1) {
            //将回车换行转换一下，因为JSON串中字符串不能出现显式的回车换行
            s = s.replaceAll("\r\n", "");
        }
        if (s.indexOf("\n") != -1) {
            //将换行转换一下，因为JSON串中字符串不能出现显式的换行
            s = s.replaceAll("\n", "");
        }
        return s;
    }

    /**
     * 字符串构造为webBean
     *
     * @param value
     * @return WebBean
     */
    protected WebBean stringToWebBean(String value) {
        if (BaseUtil.isEmpty(value)) {
            WebBean webBean = initWebBean(null);
            webBean.setSuccess(false);
            webBean.setMessage("服务器什么都没返回啊。");
            return webBean;
        }
        try {
            return new WebBean(value);
        } catch (Exception e) {
            WebBean webBean = initWebBean(null);
            webBean.setSuccess(false);
            webBean.setMessage("服务器返回了错误的数据格式。");
            return webBean;
        }
    }

    /**
     * 服务器返回内容转为本地数据结构
     *
     * @param server
     * @param localBean
     * @return WebBean
     */
    protected WebBean serverToLocal(String server, WebBean localBean) {
        localBean = initWebBean(localBean);
        if (server == null) {
            localBean.setSuccess(false);
            localBean.setMessage("服务器什么都没返回.");
        }
        try {
            WebBean serverBean = new WebBean(server);
            for (Iterator iterator = serverBean.keys(); iterator.hasNext(); ) {
                String key = (String) iterator.next();
                if (BaseUtil.equals(key, "param")) {
                    localBean.put("rsParam", serverBean.get(key));
                } else {
                    localBean.put(key, serverBean.get(key));
                }
            }
            if (serverBean.hasSuccess() == false) {
                localBean.setSuccess(true);
            }
        } catch (Exception e) {
            localBean.setSuccess(false);
            localBean.setMessage("服务器返回的数据格式不正确.");
            log.error("serverToLocal error: " + e.toString());
        }

        return localBean;
    }




    /**
     * 处理app WebView中的连接
     *
     * @param url
     */
    public static String getAppUrl(String url) {
        if (BaseUtil.isEmpty(url)) {
            // 跳转到无法定向的地址
            return AppConst.APP_URL_404;
        }
        String appUrl = null;
        if (BaseUtil.startsWith(url, AppConst.APP_URL_ROOT)) {
            // file:///android_asset 内部地址
            appUrl = BaseUtil.substringAfterLast(url, AppConst.APP_URL_ROOT);
            if (BaseUtil.isEmpty(appUrl)) {
                return AppConst.APP_URL_404;
            }
        } else {
            appUrl = url;
        }
        // 其余情况使用原始地址，在之前加入根路径。
        if (BaseUtil.startsWith(appUrl, AppConst.ROOT_PATH)) {
            appUrl = AppConst.APP_URL_ROOT + appUrl;
        } else {
            appUrl = AppConst.APP_URL_ROOT + AppConst.ROOT_PATH + appUrl;
        }
        return appUrl;
    }


}