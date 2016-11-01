package component;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.rt.core.util.BaseUtil;

import java.util.HashMap;
import java.util.Map;

import util.AppConst;
import util.AppUtil;

/**
 * 简单任务
 */
public abstract class LoadTask extends AsyncTask<String, String, String> {

    private static String TAG = LoadTask.class.getName();

    private boolean progressDialogCancelable;
    protected ProgressDialog progressDialog;

    private Context context;
    private Map param;
    private String name;

    public LoadTask() {
        context = AppUtil.getTopContext();
        progressDialogCancelable = false;
    }

    public LoadTask(boolean cancelable) {
        context = AppUtil.getTopContext();
        progressDialogCancelable = cancelable;
    }

    public LoadTask(Context context, boolean cancelable) {
        this.context = context;
        progressDialogCancelable = cancelable;
    }

    public Activity getActivity() {
        return (Activity) context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Map getParam() {
        if (param == null) {
            param = new HashMap();
        }
        return param;
    }

    private void clearParam() {
        param = null;
    }

    public void putParam(String name, Object value) {
        getParam().put(name, value);
    }

    public Object getParam(String name) {
        return getParam().get(name);
    }

    /**
     * 休眠
     *
     * @param time
     */
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 初始化对话框
     */
    protected void initProgressDialog(boolean cancelable) {
        if (context == null) {
            return;
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(cancelable);
        progressDialog.setCanceledOnTouchOutside(cancelable);
        if (cancelable) {
            progressDialog
                    .setOnCancelListener(new ProgressDialog.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            closeProgress();
                        }
                    });
        }
        if (BaseUtil.isNotEmpty(getButtonName())) {
            progressDialog.setButton(getButtonName(),
                    getButtonOnClickListener());
        }
        if (BaseUtil.isNotEmpty(getButton2Name())) {
            progressDialog.setButton2(getButton2Name(),
                    getButton2OnClickListener());
        }
    }

    /**
     * 获取按钮1名称
     *
     * @return String
     */
    abstract public String getButtonName();

    /**
     * 获取按钮1点击事件
     *
     * @return AppOnClickListener
     */
    abstract public AppOnClickListener getButtonOnClickListener();

    /**
     * 获取按钮2名称
     *
     * @return String
     */
    abstract public String getButton2Name();

    /**
     * 获取按钮2点击事件
     *
     * @return AppOnClickListener
     */
    abstract public AppOnClickListener getButton2OnClickListener();

    /**
     * 显示等待窗体
     *
     * @param message
     */
    public void showProgress(String message) {
        if (progressDialog == null) {
            initProgressDialog(progressDialogCancelable);
        }
        if (progressDialog == null) {
            return;
        }
        progressDialog.setTitle(AppConst.progressDialogTitle);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    /**
     * 关闭等待窗体
     */
    public void closeProgress() {
        if (progressDialog == null) {
            return;
        }
        cancel(true);
        clearParam();
        progressDialog.dismiss();
    }

    /**
     * onCancelled 取消,关闭等待对话框.
     */
    public void onCancelled() {
        closeProgress();
    }

    /**
     * onPreExecute 任务执行前,更新UI操作.
     */
    protected abstract void onPreExecute();

    /**
     * doInBackground 执行任务,注意:不可在此方法内更新UI.
     */
    protected abstract String doInBackground(String... params);

    /**
     * onProgressUpdate 任务执行中,更新UI,注意:此方法会被调用多次.
     */
    protected void onProgressUpdate(String... progresses) {
    }

    /**
     * onPostExecute 任务完成后,更新UI.
     */
    protected abstract void onPostExecute(String result);

    /**
     * 并行执行
     *
     * @return AsyncTask
     */
    public AsyncTask executeOnThreadPool() {
        return executeOnExecutor(THREAD_POOL_EXECUTOR);
    }

    /**
     * 串行执行
     *
     * @return AsyncTask
     */
    public AsyncTask executeOnSerialExecutor() {
        return executeOnExecutor(SERIAL_EXECUTOR);
    }

}