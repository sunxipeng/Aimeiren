package component;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rt.beans.Property;
import com.rt.core.util.BaseUtil;

import logic.AppLogic;
import util.AppConst;

public class AppWebViewClient extends WebViewClient {


	public AppWebViewClient(AppBundle appBundle) {

	}

	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
	}
	
	/**
	 * 每次发生点击事件时
	 */
	public boolean shouldOverrideUrlLoading(WebView view, String url) {

			return super.shouldOverrideUrlLoading(view, url);
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
	}

}