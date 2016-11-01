package util;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.view.View;

import com.json.JSONObject;
import com.rt.constant.RTConst;
import com.rt.core.log.Log;
import com.rt.core.util.BaseUtil;
import com.rt.core.util.IOUtil;
import com.rt.logic.util.LogicUtil;


import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import app.WebConst;
import component.BaseFragment;

/**
 * web帮助类
 */
public class WebUtil extends LogicUtil {

    private static Log log = Log.getLog(WebUtil.class);

    private static Context TOP_CONTEXT;
    private static Context MAIN_CONTEXT;
    private static BaseFragment TOP_FRAGMENT;

    /**
     * 获取Context
     *
     * @return Context
     */
    public final static Context getTopContext() {
        return TOP_CONTEXT;
    }

    /**
     * 获取Activity
     *
     * @return Activity
     */
    public final static Activity getTopActivity() {
        return (Activity) TOP_CONTEXT;
    }

    /**
     * 获取Activity
     *
     * @return Activity
     */
    public final static Activity getMainActivity() {
        return (Activity) MAIN_CONTEXT;
    }

    /**
     * 初始化context
     *
     * @param context
     */
    public final static void setTopContext(Context context) {
        TOP_CONTEXT = context;
    }

    public final static View getTopFragmentLayoutView() {
        if (TOP_FRAGMENT == null) {
            return null;
        }
        return TOP_FRAGMENT.getLayoutView();
    }

    public final static void setTopFragment(BaseFragment fragment) {
        if (fragment == null) {
            return;
        }
        TOP_FRAGMENT = fragment;
    }

    public final static BaseFragment getTopFragment() {
        return TOP_FRAGMENT;
    }

    /**
     * 初始化context
     *
     * @param context
     */
    public final static void setMainContext(Context context) {
        MAIN_CONTEXT = context;
    }

    private static String appHome;

    protected final static String sys_const = "sys_const";
    protected final static String sys_resource = "sys_resource";

    public final static String DOT_DO = ".do";
    public final static String REDIRECT = "redirect:";
    public final static String FORWARD = "forward:";

    public final static String SECURITY_CODE = "security.code";
    public final static String SECURITY_TOKEN = "security.token";
    public final static String SECURITY_ROLE = "security.role";
    public final static String SECURITY_ROLE_RES_TAG = "security.role.res.";

    public final static String CALLBACK_URL = "callbackUrl.";

    public final static String CONTENT_TYPE_TEXT_HTML = "text/html";
    public final static String CONTENT_TYPE_APPLICATION_JSON = "text/html";

    private static String VERSION;

    private static Properties props = new Properties();

    /**
     * 是否为开发模式
     *
     * @return boolean
     */
    public static boolean isDev() {
        return !BaseUtil.equalsIgnoreCase("production", getAppMode());
    }

    /**
     * 获取应用运行模式
     *
     * @return String
     */
    public static String getAppMode() {
        return getMsg("rt.mode");
    }

    /**
     * 获取角色资源
     *
     * @param roleId
     * @return Map
     */
    public static Map getRoleResourceArrayFromRoleId(String roleId) {
        Map data = getCacheItem(SECURITY_ROLE_RES_TAG + roleId);
        if (data == null) {
            data = new JSONObject();
        }
        return data;
    }

    /**
     * 设置角色资源
     *
     * @param roleId
     */
    public static void putRoleResourceArray(String roleId, Map userResArray) {
        putCacheItem(SECURITY_ROLE_RES_TAG + roleId, userResArray);
    }

    /**
     * 设置角色资源
     *
     * @param roleId
     * @param item
     */
    public static void setRoleResourceArrayFromRoleId(String roleId, Object item) {
        putCacheItem(SECURITY_ROLE_RES_TAG + roleId, item);
    }

    /**
     * 获取画面文字<br/>
     * 支持i18n
     *
     * @param name
     * @return String
     */
    public static String getMsg(String name) {
        String value;
        try {
            value = getProperties().getProperty(name);
        } catch (Exception e) {
            log.error(e);
            value = WebConst.EMPTY;
        }
        if (value == null) {
            value = WebConst.EMPTY;
        }
        return value;
    }

    /**
     * 加载配置文件
     *
     * @return Properties
     */
    public static Properties getProperties() {
        if (props == null) {
            props = new Properties();
        }
        try {
            InputStream in = getTopContext().getAssets().open("sys.properties");
            props.load(in);
        } catch (Exception e) {
            log.error(e);
        }
        return props;
    }

    /**
     * 异常转换为字符串.
     *
     * @param e <Exception>
     * @return String
     */
    public static String exceptionToString(Object e) {
        if (e == null) {
            return RTConst.EMPTY;
        }
        StringBuffer error = new StringBuffer();
        Exception exception = null;
        if (e instanceof Exception == false) {
            return RTConst.EMPTY;
        }
        exception = (Exception) e;
        error.append(exception.getClass());
        error.append("\n");
        error.append(exception.getMessage());
        error.append("\n");
        error.append(exception.getClass());
        StackTraceElement[] trace = exception.getStackTrace();
        for (StackTraceElement traceElement : trace) {
            error.append("\n");
            error.append(traceElement.toString());
        }
        String msg = error.toString();
        return msg;
    }

    // end 主路径/URL

    /**
     * 登录消息
     */
    public static String getLoginMessage() {
        String key = getMsg("security.loginMessage");
        return getMsg(key);
    }

    /**
     * 没有权限消息
     */
    public static String getPrivilegeMessage() {
        String key = getMsg("security.privilegeMessage");
        return getMsg(key);
    }

    /**
     * 是否允许匿名访问
     */
    public static boolean isAnonymous() {
        return BaseUtil.toboolean(getMsg("security.anonymous"));
    }

   /* *//**
     * 是否为root用户
     *
     * @param securityUser
     * @return boolean
     *//*
    public static boolean isRoot(UserInfo securityUser) {
        if (securityUser == null) {
            return false;
        }
        return isRoot(securityUser.getCode(), securityUser.getPassword());
    }*/

    /**
     * 是否为root用户
     *
     * @param code
     * @param password
     * @return boolean
     */
    public static boolean isRoot(String code, String password) {
        if (BaseUtil.equals(code, getMsg(SECURITY_CODE))
                && (BaseUtil.equals(BaseUtil.getSHA1Value(password),
                getMsg(SECURITY_TOKEN)) || BaseUtil.equals(password,
                getMsg(SECURITY_TOKEN)))) {
            return true;
        }
        return false;
    }

    /**
     * 获取公司名称
     *
     * @return String
     */
    public static String getCopName() {
        return getMsg("rt.copName");
    }

    /**
     * 获取系统版本号
     *
     * @return the version
     */
    public static String getVersion() {
        // 生产,返回配置文件值
        if (BaseUtil.isEmpty(VERSION)) {
            VERSION = getMsg("rt.version");
        }
        return VERSION;
    }

    /**
     * 判断存储位置是否存在
     *
     * @return boolean
     */
    public static boolean hasStorage() {
        // 判断存储卡是否存在
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取存储位置根路径
     *
     * @return String RealPath
     */
    public static String getExtDir() {
        // 获取跟目录
        if (hasStorage() == false) {
            return null;
        }
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取应用存储主路径
     *
     * @return String
     */
    public static String getAppHomePath() {
        if (appHome != null) {
            return appHome;
        }
        // 获取跟目录
        if (hasStorage() == false) {
            return null;
        }
        String path = getExtDir();
        path += WebConst.ROOT_PATH + getTopContext().getPackageName();
        IOUtil.createPath(path);
        appHome = path;
        return appHome;
    }

    /**
     * 构造请求路径
     *
     * @param url
     * @return String
     */
    public static String buildUrl(String url) {
        if (BaseUtil.isEmpty(url)) {
            return BaseUtil.EMPTY;
        }
        // 检查是否redirect开头
        if (BaseUtil.startsWith(url, REDIRECT)) {
            url = buildRedirectUrl(url);
            // 检查是否forward开头
        } else if (BaseUtil.startsWith(url, FORWARD)) {
            url = buildForwardUrl(url);
        } else {
            // 检查第一个字符是否为/,如果不是,添加.
            if (BaseUtil.startsWith(url, RTConst.ROOT_PATH) == false) {
                url = RTConst.ROOT_PATH + url;
            }
        }
        // 不要.do结尾内容,避免死循环.
        url = BaseUtil.substringBefore(url, RTConst.DOT);
        return url;
    }

    /**
     * 处理重定向url
     *
     * @param url
     * @return String
     */
    public static String buildRedirectUrl(String url) {
        url = BaseUtil.replaceOnce(url, REDIRECT, RTConst.EMPTY);
        if (BaseUtil.startsWith(url, RTConst.ROOT_PATH) == false) {
            url = RTConst.ROOT_PATH + url;
        }
        return url;
    }

    /**
     * 处理转向url
     *
     * @param url
     * @return String
     */
    public static String buildForwardUrl(String url) {
        url = BaseUtil.replaceOnce(url, FORWARD, RTConst.EMPTY);
        if (BaseUtil.startsWith(url, RTConst.ROOT_PATH) == false) {
            url = RTConst.ROOT_PATH + url;
        }
        return url;
    }

    /**
     * 构造访问资源,去除无用信息.
     *
     * @param value
     * @return Resource
     */
    public static String buildResourceByURL(String value) {
        if (BaseUtil.isEmpty(value)) {
            return BaseUtil.EMPTY;
        }
        // 读取最后一个.之前的内容
        return BaseUtil.substringBeforeLast(value, RTConst.DOT);
    }

    /**
     * 添加缓存对象<br/>
     * 只有生产环境,且启用缓存的情况下才有效.
     *
     * @param key
     * @param value
     */
    public static void putCacheItem(String key, Object value) {
        WebCacheUtil.put(key, value);
    }

    /**
     * 删除缓存对象
     *
     * @param key
     */
    public static void delCacheItem(String key) {
        WebCacheUtil.del(key);
    }

    /**
     * 获取缓存对象
     *
     * @param key
     * @return <T> T
     */
    public static <T> T getCacheItem(String key) {
        return WebCacheUtil.get(key);
    }

}
