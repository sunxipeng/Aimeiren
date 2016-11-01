package beans;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.json.JSONArray;
import com.json.JSONObject;
import com.rt.core.util.BaseUtil;
import com.rt.logic.beans.LogicBean;
import com.rt.orm.entity.EntityFace;

import util.WebUtil;


/**
 * 业务层业务逻辑传递数据封装
 */
public class WebBean extends LogicBean {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认构造
	 */
	public WebBean() {
	}

	/**
	 * 通过json字符串构造
	 * 
	 * @param string
	 * @throws Exception
	 */
	public WebBean(String string) throws Exception {
		super(string);
	}

	/**
	 * 通过Map构造
	 * 
	 * @param map
	 * @throws Exception
	 */
	public WebBean(Map map) throws Exception {
		super(map);
	}

	/**
	 * sessionId
	 * 
	 * @return String
	 */
	public String getSessionId() {
		return getString("sessionId");
	}

	/**
	 * sessionId
	 * 
	 * @param sessionId
	 */
	public void setSessionId(String sessionId) {
		put("sessionId", sessionId);
	}
	
	public boolean showLoading() {
		return getParam().getBoolean("showLoading");
	}

	public boolean isConnectionSucceed() {
		return getBoolean("connectionSucceed");
	}
	
	public void setConnectionSucceed(boolean success) {
		put("connectionSucceed", success);
	}

	/**
	 * 远程请求地址
	 * 
	 * @return String
	 */
	public String getRemoteUrl() {
		return getParam("remoteUrl");
	}

	/**
	 * 远程请求地址
	 * 
	 * @param remoteUrl
	 */
	public void setRemoteUrl(String remoteUrl) {
		putParam("remoteUrl", remoteUrl);
	}


	/**
	 * 成功跳转页面
	 * 
	 * @return String
	 */
	public String getSuccessView() {
		return getParam("sv");
	}

	/**
	 * 成功跳转页面
	 * 
	 * @param successView
	 */
	public void setSuccessView(String successView) {
		putParam("sv", successView);
	}

	/**
	 * 是否有成功跳转页面
	 * 
	 * @return boolean
	 */
	public boolean hasSuccessView() {
		return BaseUtil.isNotEmpty(getSuccessView());
	}

	/**
	 * 失败跳转页面
	 * 
	 * @return String
	 */
	public String getFailView() {
		return getParam("fv");
	}

	/**
	 * 是否有失败跳转页面
	 * 
	 * @return boolean
	 */
	public boolean hasFailView() {
		return BaseUtil.isNotEmpty(getFailView());
	}

	/**
	 * 失败跳转页面
	 * 
	 * @param FailView
	 */
	public void setFailView(String FailView) {
		putParam("fv", FailView);
	}

	/**
	 * 消息
	 * 
	 * @return String
	 */
	public String getMessageCode() {
		return getString("messageCode");
	}

	/**
	 * 消息
	 * 
	 * @param messageCode
	 */
	public void setMessageCode(String messageCode) {
		put("messageCode", messageCode);
	}

	/**
	 * 文件对象
	 * 
	 * @return Object
	 *//*
	public GMultipartFile getFile() {
		Map map = getFileMap();
		for (Iterator iterator = map.values().iterator(); iterator.hasNext();) {
			List<GMultipartFile> list = (List<GMultipartFile>) iterator.next();
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		}
		return null;
	}*/

	/**
	 * 文件对象
	 * 
	 * @return Object
	 */
	/*public GMultipartFile getFile(String name) {
		List<GMultipartFile> list = getFiles(name);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}*/

	/**
	 * 获取文件列表
	 * 
	 * @param name
	 * @return List
	 */
	public List getFiles(String name) {
		return (List) getFileMap().get(name);
	}

	/**
	 * 获取所有文件
	 * 
	 * @return Map<String, List<GMultipartFile>>
	 */
	public Map getFileMap() {
		Map map = (Map) get("fileArray");
		if (map == null) {
			map = new JSONObject();
			put("fileArray", map);
		}
		return map;
	}

	/**
	 * 添加文件
	 * 
	 * @param name
	 * @param file
	 */
	/*public void putFile(String name, GMultipartFile file) {
		List list = getFiles(name);
		if (list == null) {
			list = new JSONArray();
			getFileMap().put(name, list);
		}
		list.add(file);
	}*/

	/**
	 * 添加文件列表
	 * 
	 * @param name
	 * @param files
	 */
	/*public void putFiles(String name, List<GMultipartFile> files) {
		getFileMap().put(name, files);
	}*/

	/**
	 * 获取额外返回参数
	 * 
	 * @return rsParam
	 */
	public JSONObject getRsParam() {
		JSONObject rsParam = getJSONObject("rsParam");
		if (rsParam == null) {
			rsParam = new JSONObject();
			put("rsParam", rsParam);
		}
		return rsParam;
	}

	/**
	 * 获取属性参数
	 * 
	 * @return rsParam
	 */
	public JSONObject getAttr() {
		JSONObject attr = getJSONObject("attr");
		if (attr == null) {
			attr = new JSONObject();
			put("attr", attr);
		}
		return attr;
	}

	/**
	 * 设置属性参数
	 * 
	 * @return rsParam
	 */
	public void setAttr(JSONObject attr) {
		put("attr", attr);
	}

	/**
	 * 获取请求参数对象值
	 * 
	 * @param name
	 * @return Object
	 */
	public JSONArray getParamArray(String name) {
		JSONArray array = getParam().getJSONArray(name, false);
		if (array.isEmpty()) {
			String value = getParam(name);
			if (BaseUtil.isEmpty(value)) {
				getParam().put(name, BaseUtil.EMPTY);
			} else {
				array.add(value);
			}
		}
		return array;
	}
	
	/**
	 * 添加参数
	 * 
	 * @param name
	 * @param value
	 */
	public void putParam(String name, boolean value) {
		getParam().put(name, value);
	}

	/**
	 * 添加参数
	 * 
	 * @param name
	 * @param value
	 */
	public void putParam(String name, Long value) {
		getParam().put(name, value);
	}

	/**
	 * 添加参数
	 * 
	 * @param name
	 * @param value
	 */
	public void putParam(String name, List value) {
		getParam().put(name, value);
	}
	
	/**
	 * 添加参数
	 * 
	 * @param name
	 * @param value
	 */
	public void putParam(String name, Map value) {
		getParam().put(name, value);
	}

	/**
	 * 查询实体
	 * 
	 * @return the queryEntity
	 */
	public Map<String, Class> getQueryEntity() {
		JSONObject param = getJSONObject("queryEntity");
		if (param == null) {
			param = new JSONObject();
			setQueryEntity(param);
		}
		return param;
	}

	/**
	 * 查询实体
	 * 
	 * @param queryEntity
	 *            the queryEntity to set
	 */
	public void setQueryEntity(Map<String, Class> queryEntity) {
		put("queryEntity", queryEntity);
	}

	/**
	 * 添加查询实体
	 * 
	 * @param name
	 * @param clazz
	 */
	public void putQueryEntity(String name, Class clazz) {
		getQueryEntity().put(name, clazz);
	}

	/**
	 * 查询返回结构构造对象
	 * 
	 * @param entityFace
	 */
	public void setEntityFace(EntityFace entityFace) {
		put("entityFace", entityFace);
	}

	/**
	 * 查询返回结构构造对象
	 * 
	 * @return entityFace
	 */
	public EntityFace getEntityFace() {
		return (EntityFace) get("entityFace");
	}

	/**
	 * 获取指定数据
	 * 
	 * @return <T> T
	 */
	public <T> T getData(String key) {
		return (T) get(key);
	}

	/**
	 * 获取List数据
	 * 
	 * @return List
	 */
	public List getDataList() {
		return getData();
	}

	/**
	 * 获取Map数据
	 * 
	 * @return Map
	 */
	public Map getDataMap() {
		return getData();
	}

	/**
	 * 成功跳转页面
	 * 
	 * @param successView
	 */
	public void setSuccessViewPath(String successView) {
		setSuccessView(WebUtil.buildUrl(successView));
	}

	/**
	 * 失败跳转页面
	 * 
	 * @param failView
	 */
	public void setFailViewPath(String failView) {
		setFailView(WebUtil.buildUrl(failView));
	}

	/**
	 * json格式返回
	 * 
	 * @param json
	 */
	public void setReturnJSON(boolean json) {
		if (json) {
			putParam("dt", "json");
		}
	}

	/**
	 * json格式返回
	 * 
	 * @return boolean
	 */
	public boolean isReturnJSON() {
		return BaseUtil.contains(getParam("dt"), "json");
	}

	/**
	 * 文件格式返回
	 * 
	 * @return boolean
	 */
	public boolean isReturnFile() {
		return BaseUtil.contains(getParam("dt"), "file");
	}

	/**
	 * 获取数据size
	 * 
	 * @return int
	 */
	public int getDataSize() {
		Object data = getData();
		if (data instanceof List) {
			List list = (List) data;
			return list.size();
		} else if (data instanceof Map) {
			Map map = (Map) data;
			return map.size();
		}
		return 0;
	}

	/**
	 * 添加数据对象到data
	 * 
	 * @param baseEntity
	 */
	public void addDataItem(JSONObject baseEntity) {
		if (baseEntity == null) {
			return;
		}
		((List) getData()).add(baseEntity);
	}

	/**
	 * 获取Data列表中获取第一个数据对象
	 * 
	 * @return <T> T
	 */
	public <T> T getDataItem() {
		if (getData() == null) {
			return null;
		}
		List data = getDataList();
		if (data.size() > 0) {
			return (T) data.get(0);
		}
		return null;
	}

	/**
	 * 业务逻辑名称 EL
	 * 
	 * @return String
	 */
	public String getLogicId() {
		return getParam("logicId");
	}

	/**
	 * 业务逻辑名称 EL
	 * 
	 * @param logicId
	 */
	public void setLogicId(String logicId) {
		putParam("logicId", logicId);
	}

	/**
	 * 消息
	 * 
	 * @return String
	 */
	public String getRequestBody() {
		return getString("requestBody");
	}

	/**
	 * 消息
	 * 
	 * @param requestBody
	 */
	public void setRequestBody(String requestBody) {
		put("requestBody", requestBody);
	}

	public void clearLogicId() {
		getParam().remove("logicId");
	}

	/**
	 * 清理文件数据
	 */
	public void clearFile() {
		remove("fileArray");
	}

	/**
	 * 清理数据
	 */
	public void clearData() {
		remove("data");
	}

	/**
	 * 清理无用数据
	 */
	public void clearMaxRow() {
		remove("maxRow");
	}

	/**
	 * 清理用户数据
	 */
	public void clearUserInfo() {
		remove("userInfo");
	}

	public void clearQuery() {
		remove("query");
	}

	/**
	 * 清理所有请求参数
	 */
	public void clearParam() {
		remove("param");
		remove("queryEntity");
		remove("entityFace");
		clearQuery();
		clearData();
		clearFile();
		clearMaxRow();
	}

	/**
	 * 清理无用数据
	 */
	public void clearAttribute() {
		Map param = getParam();
		param.remove("sv");
		param.remove("fv");
		param.remove("logicId");
		param.remove("functionName");
		param.remove("file");
		param.remove("password");
		param.remove("passwordConfirm");
		remove("captcha");
		remove("requestBody");
		remove("logicUseTime");
		remove("password");
		remove("fileArray");
		remove("checkcode");
		remove("query");
		remove("countQuery");
		remove("queryEntity");
		remove("entityFace");
	}



}