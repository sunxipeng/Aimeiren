/*
 * Created on 2008/08/21
 *
 */
package logic;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;

import com.rt.constant.RTConst;
import com.rt.core.util.BaseUtil;
import com.rt.core.util.TimeUtil;

import com.rt.orm.entity.IDEntity;

import beans.WebBean;


/**
 * 业务规则逻辑层<br/>
 * 提供基础操作方法,尽量不要直接调用dao
 */
@SuppressLint("DefaultLocale")
public abstract class WebLogic extends BaseLogic {

    /**
     * 初始化WebBean
     *
     * @return WebBean
     */
    protected WebBean initWebBean() {
        return initWebBean(null);
    }

    /**
     * 初始化WebBean
     *
     * @param webBean
     * @return WebBean
     */
    protected final static WebBean initWebBean(WebBean webBean) {
        if (webBean == null) {
            webBean = new WebBean();
            webBean.setSuccess(true);
        }
        return webBean;
    }

    /**
     * 执行默认逻辑<br/>
     * 没有放到logic层是因为事务处理<br/>
     * 必须根据logic层的第一个方法名称决定做查询还是更新.
     *
     * @param webBean
     * @return WebBean
     */
    public WebBean executeLogic(WebBean webBean) {
        if (webBean == null) {
            return null;
        }
        long start = TimeUtil.getDateToLong();
        // 获取请求逻辑名称
        String logicId = webBean.getLogicId();
        if (BaseUtil.isEmpty(logicId)) {
            log.error("execute logic error: logic id is null.");
            webBean.setSuccess(false);
            webBean.setMessage("Logic id is null.");
            return webBean;
        }

        // 获取执行方法名
        String functionName = BaseUtil.substringAfterLast(logicId, RTConst.DOT);

        // 构造新逻辑名
        String logicName = getExecutLogicName(logicId);

        // 获取需要执行的类
        Object logic = getInstance(logicName, false);
        // 没有指定类
        if (logic == null) {
            webBean.setSuccess(false);
            log.error("execute logic error, logic not found: "
                    + logicName.toString());
            return webBean;
        }

        // run logic
        log.info("execute logic start: " + logicName + "." + functionName);
        Object obj = null;
        try {
            Class[] paramTypes = {WebBean.class};
            Object[] param = {webBean};
            obj = BaseUtil.doMethod(logic, functionName, paramTypes, param);
        } catch (Exception e) {
            webBean.setSuccess(false);
            log.error("execute logic error: " + e.toString());
            return webBean;
        }
        // 处理返回值,如果需要处理.
        if (obj == null) {
        }

        // 业务逻辑里没设置执行是否成功时,才设为成功.
        // 否则使用业务逻辑内部执行是否成功标记.
        if (webBean.hasSuccess() == false) {
            webBean.setSuccess(true);
        }

        // 计算执行时间
        long useTime = TimeUtil.getDateToLong() - start;
        webBean.put("logicUseTime", useTime);
        log.info("execute logic success: " + logicName + "." + functionName
                + ", UseTime: " + useTime);
        return webBean;
    }

    /**
     * 执行业务逻辑类名
     *
     * @param name
     * @return String
     */
    abstract protected String getExecutLogicName(String name);

    /**
     * 执行脚本
     *
     * @param webBean
     * @return WebBean
     */
    private final WebBean runScript(WebBean webBean) {
        return webBean;
    }

    /**
     * 执行查询脚本
     *
     * @param webBean
     * @return WebBean
     */
    public WebBean searchScript(WebBean webBean) {
        return runScript(webBean);
    }









    /**
     * 根据query创建countQuery
     *
     * @param query
     * @return String
     */
    public static String buildCountQuery(String query) {
        if (BaseUtil.isEmpty(query)) {
            return BaseUtil.EMPTY;
        }
        // 查找FROM位置
        String t = query.toUpperCase();
        int s = t.indexOf("FROM");
        int e = t.indexOf("ORDER BY");
        StringBuffer countQuery = new StringBuffer();
        countQuery.append(" SELECT COUNT(*) ");
        if (s == -1) {
            // 错误,直接返回countQuery
            return countQuery.toString();
        }
        if (e == -1) {
            countQuery.append(query.substring(s));
        } else {
            countQuery.append(query.substring(s, e));

        }
        return countQuery.toString();
    }

    /**
     * 根据query创建sumQuery
     *
     * @param query
     * @return String
     */
    public static String buildQuery(String startQuery, String query) {
        if (BaseUtil.isEmpty(query)) {
            return BaseUtil.EMPTY;
        }
        // 查找FROM位置
        String t = query.toUpperCase();
        int s = t.indexOf("FROM");
        int e = t.indexOf("ORDER BY");
        StringBuffer countQuery = new StringBuffer();
        countQuery.append(startQuery);
        if (s == -1) {
            // 错误,直接返回countQuery
            return countQuery.toString();
        }
        if (e == -1) {
            countQuery.append(query.substring(s));
        } else {
            countQuery.append(query.substring(s, e));
        }
        return countQuery.toString();
    }

}