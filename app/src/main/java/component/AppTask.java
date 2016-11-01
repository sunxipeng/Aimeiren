package component;

import android.content.Context;

/**
 * 简单任务
 */
public class AppTask extends LoadTask {

	public AppTask() {
		super();
	}

	public AppTask(boolean cancelable) {
		super(cancelable);
	}

	public AppTask(Context context, boolean cancelable) {
		super(context, cancelable);
	}

	protected void onPreExecute() {
	}

	protected String doInBackground(String... params) {
		return null;
	}

	protected void onPostExecute(String result) {
	}

	@Override
	public String getButtonName() {
		return null;
	}

	@Override
	public AppOnClickListener getButtonOnClickListener() {
		return null;
	}

	@Override
	public String getButton2Name() {
		return null;
	}

	@Override
	public AppOnClickListener getButton2OnClickListener() {
		return null;
	}

}