package logic;

import com.rt.core.log.Log;
import com.rt.core.util.BaseUtil;
import com.rt.logic.Logic;
import com.rt.orm.Dao;

import util.WebCacheUtil;

/**
 * 所有Logic的基类 提供数据库访问和从获得自身实例的通用方式
 */
public abstract class BaseLogic implements Logic {

    protected Log log = Log.getLog(this.getClass());

    /**
     * Dao interface
     */
    private static Dao _dao = null;

    public static <T> T getInstance(String className, boolean useCache) {
        // 不要使用缓存,每次都适用新的实力.
        return (T) BaseUtil.getInstance(className);
    }

    public static <T> T getInstance(Class clazz) {
        return (T) getInstance(clazz.getName(), false);
    }

    public static <T> T getInstance(Class clazz, boolean useCache) {
        return (T) getInstance(clazz.getName(), useCache);
    }

    @Deprecated
    protected final Dao getDaoFace() {
        return _dao;
    }

    @Deprecated
    public final void setDao(Dao dao) {
        _dao = dao;
    }

    @Override
    public Object execute(Object context) throws Exception {
        return context;
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
        return (T) WebCacheUtil.get(key);
    }

    /**
     * 获取所有缓存
     *
     * @return Map public static Map getCacheNames() { return
     *         WebCacheUtil.getCacheNames(); }
     */
}