package util;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.rt.core.log.Log;
import com.rt.core.util.BaseUtil;

import java.io.File;
import java.util.Locale;

class DeviceUtil extends WebUtil {

	protected static Log log = Log.getLog(DeviceUtil.class);

	private static String appCachePath;
	private static String appTmpPath;

	/**
	 * 提示消息
	 * 
	 * @param message
	 * @param lengthLong
	 */
	public static void messageToast(String message, boolean lengthLong) {
		messageToast(getTopContext(), message, lengthLong);
	}

	/**
	 * 消息提示
	 * 
	 * @param context
	 * @param message
	 * @param longMessage
	 */
	private static void messageToast(Context context, String message,
			boolean longMessage) {
		if (longMessage) {
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 消息对话框
	 * 
	 * @param message
	 */
	public static void messageDialog(String message) {
		messageDialog(getTopActivity(), message, null, null);
	}

	/**
	 * 消息对话框
	 * 
	 * @param message
	 */
	public static void messageDialog(Context context, String message) {
		messageDialog(context, message, null, null);
	}

	/**
	 * 消息对话框
	 * 
	 * @param message
	 */
	public static void messageDialog(String message,
			android.content.DialogInterface.OnClickListener okListener) {
		messageDialog(getTopActivity(), message, okListener, null);
	}

	/**
	 * 消息对话框
	 * 
	 * @param message
	 */
	public static void messageDialog(Context context, String message,
			android.content.DialogInterface.OnClickListener okListener) {
		messageDialog(context, message, okListener, null);
	}

	/**
	 * 消息对话框
	 * 
	 * @param message
	 */
	public static void messageDialog(String message,
			android.content.DialogInterface.OnClickListener okListener,
			android.content.DialogInterface.OnClickListener cancelListener) {
		Builder builder = new Builder(getTopActivity());
		builder.setCancelable(false);
		builder.setTitle(AppConst.progressDialogTitle);
		builder.setMessage(message);
		if (okListener == null) {
			builder.setPositiveButton(AppConst.confirmTitle, null);
		} else {
			builder.setPositiveButton(AppConst.confirmTitle, okListener);
		}
		if (cancelListener != null) {
			builder.setNegativeButton(AppConst.cancelTitle, cancelListener);
		}
		try {
			builder.create().show();
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 消息对话框
	 * 
	 * @param message
	 */
	public static void messageDialog(Context context, String message,
			android.content.DialogInterface.OnClickListener okListener,
			android.content.DialogInterface.OnClickListener cancelListener) {
		Builder builder = new Builder(context);
		builder.setCancelable(false);
		builder.setTitle(AppConst.progressDialogTitle);
		builder.setMessage(message);
		if (okListener == null) {
			builder.setPositiveButton(AppConst.confirmTitle, null);
		} else {
			builder.setPositiveButton(AppConst.confirmTitle, okListener);
		}
		if (cancelListener != null) {
			builder.setNegativeButton(AppConst.cancelTitle, cancelListener);
		}
		try {
			builder.create().show();
		} catch (Exception e) {
			log.error(e);
		}
	}

	// android获取一个用于打开HTML文件的intent
	public static Intent getHtmlFileIntent(String param) {
		Uri uri = Uri.parse(param).buildUpon()
				.encodedAuthority("com.android.htmlfileprovider")
				.scheme("content").encodedPath(param).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	/**
	 * 打开照相机
	 * 
	 * @return Intent
	 */
	public static Intent getCameraIntent() {
		Intent intent = new Intent();
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri imageUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), "image/*"));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		return intent;
	}

	/**
	 * 打开相册
	 * 
	 * @return Intent
	 */
	public static Intent getAlbumIntent() {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.SEND_MULTIPLE");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setType("image/*");
		// intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		return intent;
	}

	// android获取一个用于打开PDF文件的intent
	public static Intent getPdfFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}

	// android获取一个用于打开文本文件的intent
	public static Intent getTextFileIntent(String param, boolean paramBoolean) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (paramBoolean) {
			Uri uri1 = Uri.parse(param);
			intent.setDataAndType(uri1, "text/plain");
		} else {
			Uri uri2 = Uri.fromFile(new File(param));
			intent.setDataAndType(uri2, "text/plain");
		}
		return intent;
	}

	// android获取一个用于打开音频文件的intent
	public static Intent getAudioFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	// android获取一个用于打开视频文件的intent
	public static Intent getVideoFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	// android获取一个用于打开CHM文件的intent
	public static Intent getChmFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/x-chm");
		return intent;
	}

	// android获取一个用于打开Word文件的intent
	public static Intent getWordFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	// android获取一个用于打开Excel文件的intent
	public static Intent getExcelFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	// android获取一个用于打开PPT文件的intent
	public static Intent getPptFileIntent(String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	/**
	 * 是否为模拟器
	 * 
	 * @return boolean
	 */
	public static boolean isEmulator() {
		return BaseUtil.isEmpty(android.os.Build.SERIAL);
	}

	/**
	 * 设备id
	 * 
	 * @return String
	 */
	public static String getDeviceId() {
		return getTelephonyManager().getDeviceId();
	}

	/**
	 * 获取应用根路径<br/>
	 * 
	 * @deprecated 不建议做存储使用,应用路径权限限制较多,建议存储到设备根路径.
	 * 
	 * @return String RealPath
	 */
	public static String getAppInstallPath() {
		return getTopContext().getFilesDir().getPath();
	}

	/**
	 * 获取存储位置根路径
	 * 
	 * @return String RealPath
	 */
	public static String getExtPublicDir(String type) {
		// 获取跟目录
		if (hasStorage() == false) {
			return null;
		}
		return Environment.getExternalStoragePublicDirectory(type).getPath();
	}



	/**
	 * 是否第一次使用
	 * 
	 * @return boolean
	 */
	public static boolean isFirstUse() {
		SharedPreferences sp = getTopContext().getSharedPreferences(
				getTopContext().getPackageName(), Context.MODE_PRIVATE);
		return sp.getBoolean(getTopContext().getPackageName(), true);
	}

	/**
	 * 设置应用为已使用
	 */
	public static void setUsed(boolean used) {
		SharedPreferences sp = getTopContext().getSharedPreferences(
				getTopContext().getPackageName(), Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(getTopContext().getPackageName(), used);
		editor.commit();
	}

	/**
	 * 获取版本代码
	 * 
	 * @return int
	 */
	public static int getVersionCode() {
		try {
			PackageManager pm = getTopContext().getPackageManager();
			PackageInfo pi = pm.getPackageInfo(
					getTopContext().getPackageName(), 0);
			return pi.versionCode;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 需要更新
	 * 
	 * @param lastVersionCode
	 * @return boolean
	 */
	public static boolean needUpdate(int lastVersionCode) {
		return lastVersionCode > getVersionCode();
	}

	/**
	 * 取得设备SimSerialNumber信息
	 * 
	 * @return String 设备ID
	 */
	public static String getSimSerialNumber() {
		return getTelephonyManager().getSimSerialNumber();
	}

	/**
	 * 取得系统语言
	 * 
	 * @return String 系统语言
	 */
	public static String getLocalLanguage() {
		return Locale.getDefault().getLanguage();
	}

	/**
	 * 是否有网络可用
	 * 
	 * @return boolean
	 */
	public static boolean hasNet() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getTopContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.isAvailable()) {
			return true;
		}
		return false;
	}

	/**
	 * 获取本机号码<br />
	 * 注意! 不是所有环境都能取得到号码
	 * 
	 * @return String
	 */
	public static String getPhoneNumber1() {
		return getTelephonyManager().getLine1Number();
	}

	/**
	 * 获取设备状态
	 * 
	 * @return TelephonyManager
	 */
	public static TelephonyManager getTelephonyManager() {
		return (TelephonyManager) getTopContext().getSystemService(
				Context.TELEPHONY_SERVICE);
	}

	/**
	 * 获取ip
	 * 
	 * @return ip
	 */
	public static String getIP() {
		WifiManager wifiManager = (WifiManager) getTopContext()
				.getSystemService(Context.WIFI_SERVICE);
		// 判断wifi是否开启
		if (!wifiManager.isWifiEnabled() == true) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		return intToIp(ipAddress);
	}

	private final static String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}

}
