package util;

import java.util.HashMap;
import java.util.Map;

import com.json.JSONObject;
import com.rt.core.util.BaseUtil;

import app.WebConst;


/**
 * 常量类
 */
public class AppConst extends WebConst {

    public final static String PLATFORM_CODE = "aPhone";
    public final static String progressDialogTitle = "消息";
    public final static String confirmTitle = "确认";
    public final static String cancelTitle = "取消";

    public final static int CONNECTION_TIMEOUT_30000 = 30000;
    public final static int CONNECTION_TIMEOUT_3000 = 3000;
    public final static String SERVER_URL_DEFAULT = "http://imr.jsjrong.com.cn";
    public final static JSONObject SERVER_URL_ARRAY = new JSONObject();

    static {
        SERVER_URL_ARRAY.put(SERVER_URL_DEFAULT, 0L);
    }

    public final static String OPEN_GOBACK_URL = "sys/goBack";
    public final static String OPEN_MESSAGE_URL = "sys/message";
    public final static String OPEN_MESSAGETOAST_URL = "sys/messageToast";
    // 打开本地相册
    public final static String OPEN_IMAGESELE_URL = "sys/openImageSelect";
    public final static String OPEN_ALBUM_URL = "sys/openAlbum";
    public final static String OPEN_CAMERA_URL = "sys/openCamera";
    public final static String JS_INIT_DATA = "initData";
    public final static int OPEN_CAMERA_REQUEST_CODE = 101;
    public final static int OPEN_IMAGESELECT_REQUEST_CODE = 102;

    // ------------------------------------ start 本地地址
    // 获取服务器地址
    public final static String local_url_sns_index_sns = "sns/indexSns.html";
    // ------------------------------------ end 本地地址

    // ------------------------------------ start 公共数据
    // 公共数据
    public final static String logic_indexPublic = "/m/indexPublic.do";
    // ------------------------------------ end 公共数据

    // ------------------------------------ star 信息获取
    // 首页数据
    public final static String logic_index = "/m/index.do";

    // ------------------------------------ star sns
    // 分类数据内容列表
    public final static String logic_indexSnsItemList = "/m/sns/indexSnsItemList.do";
    // sns完整内容
    public final static String logic_indexSnsItem = "/m/sns/indexSnsItem.do";
    // ------------------------------------ end sns

    private final static JSONObject logicUrlMap = new JSONObject();

    static {
        // start 公共信息
        logicUrlMap.put("index.public", logic_indexPublic);

        // start 首页
        logicUrlMap.put("index.index", logic_index);

        // start sns相关信息
        logicUrlMap.put("sns.indexSnsItemList", logic_indexSnsItemList);
        logicUrlMap.put("sns.indexSnsItem", logic_indexSnsItem);
    }

    public final static String logicIdToUrl(String logicId) {
        return logicUrlMap.getString(logicId);
    }

    // 本地路径
    public final static String LOCAL_URL_indexSnsItem = "sns/indexSnsItem.html";
    public final static String LOCAL_URL_indexSnsItemImageAtt = "sns/indexSnsItemImageAtt.html";
    public final static String LOCAL_URL_snsItemEditor = "sns/snsItemEditor.html";

    public static final String APP_URL_PROTOCOL = "app://";
    public static final String APP_URL_HTTP = "http";
    public static final String APP_URL_ROOT = "file:///android_asset/www";
    public static final String APP_URL_ROOT_ACL = "file:///android_asset/www/acl";
    public static final String APP_URL_INDEX = APP_URL_ROOT + "/index.html";
    public static final String APP_URL_404 = APP_URL_ROOT + "/404.html";

    public static final String LOGIC_PACKAGE = "com.ins.module.logic";

    public static final long LAUNCHER_WAIT_TIME_5s = 5000;

    /**
     * 订单状态 --已下单
     **/
    public static final String ORDER_INFO_STATE_SUBMIT = "submit";
    /**
     * 订单状态 --处理中
     **/
    public static final String ORDER_INFO_STATE_PROCESSING = "processing";
    /**
     * 订单状态 --发货
     **/
    public static final String ORDER_INFO_STATE_SEND = "send";
    /**
     * 订单状态 --签收
     **/
    public static final String ORDER_INFO_STATE_RECEIVED = "received";
    /**
     * 订单状态 --完成
     **/
    public static final String ORDER_INFO_STATE_FINISH = "finish";
    /**
     * 订单状态 --取消
     **/
    public static final String ORDER_INFO_STATE_CANCEL = "cancel";

    public final static Map<String, String> MIME_MAP = new HashMap<String, String>();

    static {
        MIME_MAP.put(".3gp", "video/3gpp");
        MIME_MAP.put(".apk", "application/vnd.android.package-archive");
        MIME_MAP.put(".asf", "video/x-ms-asf");
        MIME_MAP.put(".avi", "video/x-msvideo");
        MIME_MAP.put(".bin", "application/octet-stream");
        MIME_MAP.put(".bmp", "image/bmp");
        MIME_MAP.put(".c", "text/plain");
        MIME_MAP.put(".class", "application/octet-stream");
        MIME_MAP.put(".conf", "text/plain");
        MIME_MAP.put(".cpp", "text/plain");
        MIME_MAP.put(".doc", "application/msword");
        MIME_MAP.put(".exe", "application/octet-stream");
        MIME_MAP.put(".gif", "image/gif");
        MIME_MAP.put(".gtar", "application/x-gtar");
        MIME_MAP.put(".gz", "application/x-gzip");
        MIME_MAP.put(".h", "text/plain");
        MIME_MAP.put(".htm", "text/html");
        MIME_MAP.put(".html", "text/html");
        MIME_MAP.put(".jar", "application/java-archive");
        MIME_MAP.put(".java", "text/plain");
        MIME_MAP.put(".jpeg", "image/jpeg");
        MIME_MAP.put(".jpg", "image/jpeg");
        MIME_MAP.put(".js", "application/x-javascript");
        MIME_MAP.put(".log", "text/plain");
        MIME_MAP.put(".m3u", "audio/x-mpegurl");
        MIME_MAP.put(".m4a", "audio/mp4a-latm");
        MIME_MAP.put(".m4b", "audio/mp4a-latm");
        MIME_MAP.put(".m4p", "audio/mp4a-latm");
        MIME_MAP.put(".m4u", "video/vnd.mpegurl");
        MIME_MAP.put(".m4v", "video/x-m4v");
        MIME_MAP.put(".mov", "video/quicktime");
        MIME_MAP.put(".mp2", "audio/x-mpeg");
        MIME_MAP.put(".mp3", "audio/x-mpeg");
        MIME_MAP.put(".mp4", "video/mp4");
        MIME_MAP.put(".mpc", "application/vnd.mpohun.certificate");
        MIME_MAP.put(".mpe", "video/mpeg");
        MIME_MAP.put(".mpeg", "video/mpeg");
        MIME_MAP.put(".mpg", "video/mpeg");
        MIME_MAP.put(".mpg4", "video/mp4");
        MIME_MAP.put(".mpga", "audio/mpeg");
        MIME_MAP.put(".msg", "application/vnd.ms-outlook");
        MIME_MAP.put(".ogg", "audio/ogg");
        MIME_MAP.put(".pdf", "application/pdf");
        MIME_MAP.put(".png", "image/png");
        MIME_MAP.put(".pps", "application/vnd.ms-powerpoint");
        MIME_MAP.put(".ppt", "application/vnd.ms-powerpoint");
        MIME_MAP.put(".prop", "text/plain");
        MIME_MAP.put(".rar", "application/x-rar-compressed");
        MIME_MAP.put(".rc", "text/plain");
        MIME_MAP.put(".rmvb", "audio/x-pn-realaudio");
        MIME_MAP.put(".rtf", "application/rtf");
        MIME_MAP.put(".sh", "text/plain");
        MIME_MAP.put(".tar", "application/x-tar");
        MIME_MAP.put(".tgz", "application/x-compressed");
        MIME_MAP.put(".txt", "text/plain");
        MIME_MAP.put(".wav", "audio/x-wav");
        MIME_MAP.put(".wma", "audio/x-ms-wma");
        MIME_MAP.put(".wmv", "audio/x-ms-wmv");
        MIME_MAP.put(".wps", "application/vnd.ms-works");
        MIME_MAP.put(".xml", "text/xml");
        MIME_MAP.put(".xml", "text/plain");
        MIME_MAP.put(".z", "application/x-compress");
        MIME_MAP.put(".zip", "application/zip");
        MIME_MAP.put("", "");
    }

    /*********************** 基本验证正则 *******************/
    // 手机号正则表达式
    public static String MOBIE_REGEX = "^1[3458]{1}[0-9]{9}$";
    // 邮件正则表达式
    public static String EMAIL_REGEX = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    // 邮件正则表达式
    public static String IDCARD_REGEX = "/^\\d{14}X$||^\\d{15}$||^\\d{18}$||^\\d{17}X$/";

    // andriod手机相关常量
    public static String APP_NAME = "imr";

    public static long DEFAULT_ITEM_UPDATE_TAG_VALUE = 300000;
    public static long ONE_DAY_LONG_TIME = 86400000;
    public static long ONE_HOURS_LONG_TIME = 3600000;

    // ------------------------------ upload
    public static final String UPLOAD_ACTION = "action";
    public static final String UPLOAD_UPLOAD = "upload";
    public static final String UPLOAD_SUCCESS = "complate";
    public static final String UPLOAD_ERROR = "error";
    public static final String UPLOAD_EXCEPTION = "exception";

    // andriod手机相关常量
    public static final String LINE1_NUMBER = "line1Number";
    public static final String SIM_SERIAL_NUMBER = "simSerialNumber";
    public static final String SUB_SCRIBER_ID = "subscriberId";
    public static final String PRODUCT_MODEL = "productModel";
    public static final String DEVICE_ID = "deviceId";
    public static final String GMAIL = "gmail";

    public static final String CONTENT_TYPE_PNG = "image/png";
    public static final String CONTENT_TYPE_GIF = "image/gif";
    public static final String CONTENT_TYPE_JPG = "image/jpeg";
    public static final String CONTENT_TYPE_JPEG = "image/jpeg";

    // 险种相关常量
    /**
     * 变量字符串切割
     */
    public final static String CUT_REG = "(.*?)(\\d+)(.*)";

    public static String DATE_FORMAT_NORMAL = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMAT_DAY = "yyyy-MM-dd";
    public static String DATE_FORMAT_TIME = "HH:mm:ss";
    /**
     * 内容每页显示数据量
     */
    public static final int PAGE_SIZE_CONTENT = 20;
    /**
     * 预览每页显示数据量
     */
    public static final int PAGE_SIZE_PREVIEW = 10;

    /**
     * 1天等于 86400秒
     */
    public static int SECONDS_DAY = 86400;

    public static String[] OBJECT_KEYWORD_ARRAY = {"qname"};

    public static boolean isUpd(String value) {
        return BaseUtil.contains(AppConst.DB_OBJECT_TAG_UPD, value);
    }

    public static boolean isAdd(String value) {
        return BaseUtil.contains(AppConst.DB_OBJECT_TAG_ADD, value);
    }

    public static boolean isDel(String value) {
        return BaseUtil.contains(AppConst.DB_OBJECT_TAG_DEL, value);
    }
}
