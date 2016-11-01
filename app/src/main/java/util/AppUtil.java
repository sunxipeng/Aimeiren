package util;


import com.aimeiren.aimeiren.MainActivity;
import com.rt.core.log.Log;
import com.rt.core.util.BaseUtil;

import java.util.HashMap;
import java.util.Map;

import app.AR;
import component.AppTabHost;
import component.AppTask;

/**
 * 业务逻辑函数库 环境或设备相关内容写到父类中
 */
public class AppUtil extends DeviceUtil {

    protected static Log log = Log.getLog(AppUtil.class);

    private static Map taskMap;

    private static Map getTaskMap() {
        if (taskMap == null) {
            taskMap = new HashMap();
        }
        return taskMap;
    }

    public static AppTask getTask(String name) {
        return (AppTask) getTaskMap().get(name);
    }

    public static void removeTask(String name) {
        getTaskMap().remove(name);
    }

    /**
     * 添加任务
     *
     * @param name
     * @param task
     */
    public static void putTask(String name, AppTask task) {
        getTaskMap().put(name, task);
    }

   /* *//**
     * 获取当前应用在SD卡中的用户路径
     *
     * @return String
     *//*
    public static String getAppUserPath() {
        String appUserPath = getAppHomePath() + "/c/";
        IOUtil.createPath(appUserPath);
        return appUserPath;
    }*/

    /**
     * 结束最顶部的Activity,主内容除外.
     */
    public static void finishTopActivity() {
        if (MainActivity.class.getName().equals(
                getTopContext().getClass().getName())) {
        } else {
            getTopActivity().finish();
        }
    }

    /**
     * 激活主画面某个卡片
     *
     * @param index
     */
    public static void setMainCurrentTab(int index) {
        AppTabHost appTabHost = (AppTabHost) getMainActivity().findViewById(
                android.R.id.tabhost);
        if (appTabHost != null) {
            // 设置主画面激活卡片
            appTabHost.setCurrentTab(index);
        }
    }

    /**
     * 获取当前的AppWebView组件
     *
     * @return AppWebView
     */
    @SuppressWarnings("static-access")
   /* public static AppWebView getAppWebView() {
        if (MainActivity.class.getName().equals(
                getTopContext().getClass().getName())) {
            return (AppWebView) getTopFragmentLayoutView().findViewById(
                    R.id.appWebView);
        } else {
            return (AppWebView) getTopActivity().findViewById(AR.id.appWebView);
        }
    }*/

    /**
     * 获取应用名
     *
     * @return String
     */
    public static String getAppName() {
        return getRVal(AR.string.app_name);
    }

    /**
     * 获取资源中的字符
     *
     * @param id
     * @return String
     */
    public static String getRVal(int id) {
        return BaseUtil.nullToSpace(getTopContext().getString(id));
    }

    /**
     * 获取以最后一个.结尾的名字
     *
     * @param name
     * @return String
     */
    public static String getExtName(String name) {
        return BaseUtil.substringAfterLast(name, AppConst.DOT);
    }


}
