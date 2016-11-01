package js;

import android.content.DialogInterface;

import com.rt.core.util.BaseUtil;

import app.AR;
import beans.WebBean;
import component.AppOnClickListener;
import logic.AppLogic;
import util.AppConst;
import util.AppUtil;


/**
 * js可调用对象 引用需要被调用的logic，单独编写业务方法。
 */
public class AppJS extends AppLogic {

    public static final String API_NAME = "appJs";
    public static final String API_PARAM_NAME = "appJsParam";
    public static final String API_ATTR_NAME = "appJsAttr";

    public static AppJS getInstance() {
        return getInstance(AppJS.class, true);
    }

    /**
     * 执行业务逻辑
     *
     * @param paramValue
     * @param attrValue
     * @return WebBean
     */
    @SuppressWarnings("static-access")
    public WebBean executeLogic(String paramValue, String attrValue) {
        final AppLogic logic = AppLogic.getInstance();
        final WebBean webBean = logic.paramToWebBean(paramValue, attrValue);
        // 登录检查
        if (AppLogic.needLoginFun(webBean.getLogicId())) {
            String msg = AppUtil.getRVal(AR.string.msg_login_info_name);
            AppUtil.messageDialog(msg, new AppOnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(AppUtil.getTopActivity(),
                            AppLogic.getLoginBundle());
                }
            }, new AppOnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            webBean.setSuccess(false);
            webBean.setMessage(msg);
            return webBean;
        }
        try {
            logic.executeLogic(webBean);
        } catch (Exception e) {
            webBean.setSuccess(false);
            webBean.setMessage(e.toString());
        }
        return webBean;
    }



    /**
     * url跳转 需要传送大对象时使用,否则可直接使用内部跳转方法
     *
     * @param paramValue 请求参数
     * @param attrValue  传递参数
     * @return boolean
     */
    public boolean setParam(String paramValue, String attrValue) {
        return AppLogic.setAppParam(paramValue, attrValue);
    }

}