package util;

import com.rt.beans.Property;
import com.rt.core.log.Log;

/**
 * 缓存
 */
public class WebCacheUtil {

	public static Log log = Log.getLog(WebCacheUtil.class);

	// 同步缓存
	private static Property syncCache = new Property();
	
	/**
	 * 获取缓存对象
	 * 
	 * @param key
	 * @return <T> T
	 */
	public static <T> T get(String key) {
		return (T) syncCache.get(key);
	}

	/**
	 * 是否有
	 * 
	 * @param key
	 * @return boolean
	 */
	public static boolean has(String key) {
		return get(key) == null;
	}

	/**
	 * 添加
	 * 
	 * @param key
	 * @param value
	 */
	public static void put(String key, Object value) {
		syncCache.put(key, value);
	}

	/**
	 * 删除
	 * 
	 * @param key
	 */
	public static void del(String key) {
		syncCache.remove(key);
	}

}
