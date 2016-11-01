package component;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class AppWebChromeClient extends WebChromeClient {
	@Override
	public void onProgressChanged(WebView view, int progress) {
		// 载入进度改变而触发
		if (progress == 100) {
			// 如果全部载入,隐藏进度对话框
		}
		super.onProgressChanged(view, progress);
	}
}