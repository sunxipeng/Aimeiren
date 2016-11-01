/*
 * Created on 2008/08/21
 *
 */
package component;

import android.content.Intent;
import android.os.Bundle;

import com.json.JSONObject;
import com.rt.core.util.BaseUtil;

/**
 * Bundle 扩展
 */
public class AppBundle {
    private Bundle bundle;
    private JSONObject param;
    private JSONObject attr;

    public AppBundle() {
        this.bundle = new Bundle();
        param = new JSONObject();
        attr = new JSONObject();
    }

    public AppBundle(Bundle bundle) {
        if (bundle == null) {
            this.bundle = new Bundle();
            param = new JSONObject();
            attr = new JSONObject();
            return;
        }
        this.bundle = bundle;
        String urlParam = bundle.getString("urlParam");
        try {
            param = new JSONObject(urlParam);
        } catch (Exception e) {
            param = new JSONObject();
        }
        String urlAttr = bundle.getString("urlAttr");
        try {
            attr = new JSONObject(urlAttr);
        } catch (Exception e) {
            attr = new JSONObject();
        }
    }

    public Bundle getBundle() {
        this.bundle.putString("urlParam", param.toString());
        this.bundle.putString("urlAttr", attr.toString());
        return this.bundle;
    }

    public Intent getIntent() {
        this.bundle.putString("urlParam", param.toString());
        this.bundle.putString("urlAttr", attr.toString());
        Intent intent = new Intent();
        intent.putExtras(this.bundle);
        return intent;
    }

    public String getId() {
        return BaseUtil.nullToSpace(bundle.getString("id"));
    }

    public void setId(String id) {
        bundle.putString("id", id);
    }

    public String getName() {
        return BaseUtil.nullToSpace(bundle.getString("name"));
    }

    public void setName(String name) {
        bundle.putString("name", name);
    }

    public boolean isSupportZoom() {
        return bundle.getBoolean("supportZoom");
    }

    public void setSupportZoom(boolean supportZoom) {
        bundle.putBoolean("supportZoom", supportZoom);
    }

    public boolean isHiddenActionBar() {
        return bundle.getBoolean("hiddenActionBar");
    }

    public void setHiddenActionBar(boolean hiddenActionBar) {
        bundle.putBoolean("hiddenActionBar", hiddenActionBar);
    }

    public String getClassName() {
        return BaseUtil.nullToSpace(bundle.getString("className"));
    }

    public void setClassName(String className) {
        bundle.putString("className", className);
    }

    public boolean isKeepState() {
        return bundle.getBoolean("keepState");
    }

    public void setKeepState(boolean keepState) {
        bundle.putBoolean("keepState", keepState);
    }

    public String getUrl() {
        return BaseUtil.nullToSpace(bundle.getString("url"));
    }

    public void setUrl(String url) {
        bundle.putString("url", url);
    }

    public String getFun() {
        return BaseUtil.nullToSpace(bundle.getString("fun"));
    }

    public void setFun(String fun) {
        bundle.putString("fun", fun);
    }

    public String getFromUrl() {
        return BaseUtil.nullToSpace(bundle.getString("fromUrl"));
    }

    public void setFromUrl(String url) {
        bundle.putString("fromUrl", url);
    }

    public JSONObject getParam() {
        return this.param;
    }

    public String getParamVal() {
        return this.param.toString();
    }

    public void setParam(JSONObject param) {
        this.param = param;
    }

    public String getParam(String key) {
        return BaseUtil.nullToSpace(param.getString(key));
    }

    public void putParam(String key, boolean value) {
        getParam().put(key, value);
    }

    public void putParam(String key, String value) {
        getParam().put(key, value);
    }

    public JSONObject getAttr() {
        return this.attr;
    }

    public String getAttrVal() {
        return this.attr.toString();
    }

    public void setAttr(JSONObject attr) {
        this.attr = attr;
    }

    public String getAttr(String key) {
        return BaseUtil.nullToSpace(attr.getString(key));
    }

    public JSONObject getAttrObject(String key) {
        return attr.getJSONObject(key, false);
    }

    public void putAttr(String key, String value) {
        getAttr().put(key, value);
    }

    public void putAttr(String key, JSONObject object) {
        getAttr().put(key, object);
    }

}