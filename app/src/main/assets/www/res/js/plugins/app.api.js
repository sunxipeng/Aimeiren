// 初始化参数对象
function initParam() {
    var urlParam;
    if (typeof(appJsParam) == "undefined") {
        urlParam = new Object();
        // just test
        var userInfo = new Object();
        userInfo.customerId = "b0ba2e8d-3836-4606-bf56-533b26e5ecd5";
        userInfo.customerCode = "kdm";
        userInfo.customerName = "王牌";
        userInfo.customerIconUrl = "http://storage.jsjrong.com.cn/accs/cus/o/2016/04/21/442bd79a-9ab3-4e89-a2b4-f0ca7065e98b.jpg";
        urlParam.userInfo = userInfo;
        return urlParam;
    }
    urlParam = JSON.parse(appJsParam);
    return urlParam;
}

// 初始化参数对象
function initAttr() {
    var urlAttr;
    if (typeof(appJsAttr) == "undefined") {
        urlAttr = new Object();
        return urlAttr;
    }
    urlAttr = JSON.parse(appJsAttr);
    return urlAttr;
}

// 初始化参数对象
function getUserInfo() {
    var userInfo = new Object();
    if (typeof(appJs) == "undefined") {
        userInfo.id = "b0ba2e8d-3836-4606-bf56-533b26e5ecd5";
        userInfo.code = "kdm";
        userInfo.name = "王牌";
        userInfo.iconUrl = "http://storage.jsjrong.com.cn/accs/cus/o/2016/04/21/442bd79a-9ab3-4e89-a2b4-f0ca7065e98b.jpg";
        return userInfo;
    }
    // 发送请求
    var rs = eval("appJs.getUserInfo()");
    // 构造返回值
    var obj = JSON.parse(rs);
    return obj;
}

// 初始化参数对象
function toJSON(value) {
    var json;
    if (isEmpty(value) == true) {
        return new Object();
    }
    json = (new Function("return " + value))();
    //json = eval('(' + value + ')');
    return json;
}

// ahref跳转
function appHref(url, param, attr) {
    var obj = new Object();
    if (typeof(appJs) == "undefined") {
        obj.success = false;
        obj.message = "业务不存在";
        // 仅供本地联通测试使用.
        window.location.href = buildURL(url, param);
        return obj;
    }
    if (appJs.hasOwnProperty("setParam") == false) {
        obj.success = false;
        obj.message = "业务项目不存在";
        return obj;
    }
    if (isEmpty(param)) {
        param = new Object();
    }
    if (isEmpty(attr)) {
        attr = new Object();
    }
    // 发送请求
    eval("appJs.setParam('" + JSON.stringify(param) + "', '" + JSON.stringify(attr) + "')");
    window.location.href = buildURL(url, param);
}

// 打开照相机
function openCamera(param, attr) {
    var obj = new Object();
    if (typeof(appJs) == "undefined") {
        obj.success = false;
        obj.message = "业务不存在";
        // 仅供本地联通测试使用.
        window.location.href = url;
        return obj;
    }
    if (appJs.hasOwnProperty("openCamera") == false) {
        obj.success = false;
        obj.message = "业务项目不存在";
        return obj;
    }
    if (typeof(param) == "undefined") {
        param = new Object();
    }
    if (typeof(att) == "undefined") {
        att = new Object();
    }
    // 发送请求
    eval("appJs.openCamera('" + JSON.stringify(param) + "', '" + JSON.stringify(att) + "')");
    window.location.href = "sys/openCamera";
}

// 打开本地相册
function openAlbum(param, att) {
    var obj = new Object();
    if (typeof(appJs) == "undefined") {
        obj.success = false;
        obj.message = "业务不存在";
        // 仅供本地联通测试使用.
        window.location.href = url;
        return obj;
    }
    if (appJs.hasOwnProperty("openAlbum") == false) {
        obj.success = false;
        obj.message = "业务项目不存在";
        return obj;
    }
    if (typeof(param) == "undefined") {
        param = new Object();
    }
    if (typeof(att) == "undefined") {
        att = new Object();
    }
    // 发送请求
    eval("appJs.openAlbum('" + JSON.stringify(param) + "', '" + JSON.stringify(att) + "')");
    window.location.href = "sys/openAlbum";
}

// 打开照片选择器
function openImageSelect(param, att) {
    var obj = new Object();
    if (typeof(appJs) == "undefined") {
        obj.success = false;
        obj.message = "业务不存在";
        // 仅供本地联通测试使用.
        window.location.href = url;
        return obj;
    }
    if (appJs.hasOwnProperty("openImageSelect") == false) {
        obj.success = false;
        obj.message = "业务项目不存在";
        return obj;
    }
    if (typeof(param) == "undefined") {
        param = new Object();
    }
    if (typeof(att) == "undefined") {
        att = new Object();
    }
    // 发送请求
    eval("appJs.openImageSelect('" + JSON.stringify(param) + "', '" + JSON.stringify(att) + "')");
    window.location.href = "sys/openImageSelect";
}

// 显示actionBar
function showActionBar(show) {
    var obj = new Object();
    if (typeof(appJs) == "undefined") {
        obj.success = false;
        obj.message = "业务不存在";
        return obj;
    }
    if (appJs.hasOwnProperty("showActionBar") == false) {
        obj.success = false;
        obj.message = "业务项目不存在";
        return obj;
    }
    // 发送请求
    eval("appJs.showActionBar('" + show + "')");
}

// 显示消息
function messageToast(message) {
    var obj = new Object();
    if (typeof(appJs) == "undefined") {
        obj.success = false;
        obj.message = "业务不存在";
        return obj;
    }
    if (appJs.hasOwnProperty("messageToast") == false) {
        obj.success = false;
        obj.message = "业务项目不存在";
        return obj;
    }
    var param = new Object();
    param.message = message;
    var att = new Object();
    // 发送请求
    eval("appJs.messageToast('" + JSON.stringify(param) + "', '" + JSON.stringify(att) + "')");
}

// 显示消息
function message(message, okFun, cancelFun) {
    var obj = new Object();
    if (typeof(appJs) == "undefined") {
        obj.success = false;
        obj.message = "业务不存在";
        return obj;
    }
    if (appJs.hasOwnProperty("setParam") == false) {
        obj.success = false;
        obj.message = "业务项目不存在";
        return obj;
    }
    var param = new Object();
    param.message = message;
    if (okFun) {
        param.okFun = okFun;
    }
    if (cancelFun) {
        param.cancelFun = cancelFun;
    }
    var att = new Object();
    // 发送请求
    eval("appJs.setParam('" + JSON.stringify(param) + "', '" + JSON.stringify(att) + "')");
    window.location.href = "sys/message";
}

// 返回上一个页面
function goBack(param, att) {
    var obj = new Object();
    if (typeof(appJs) == "undefined") {
        obj.success = false;
        obj.message = "业务不存在";
        // 仅供本地联通测试使用.
        window.location.href = url;
        return obj;
    }
    if (appJs.hasOwnProperty("setParam") == false) {
        obj.success = false;
        obj.message = "业务项目不存在";
        return obj;
    }
    if (typeof(param) == "undefined") {
        param = new Object();
    }
    if (typeof(att) == "undefined") {
        att = new Object();
    }
    // 发送请求
    eval("appJs.setParam('" + JSON.stringify(param) + "', '" + JSON.stringify(att) + "')");
    window.location.href = "sys/goBack";
}

// 收藏sns
function favoriteSns(itemId, name, subName, state) {
    var obj = new Object();
    if (typeof(appJs) == "undefined") {
        obj.success = false;
        obj.message = "业务不存在";
        return obj;
    }
    if (appJs.hasOwnProperty("executeLogic") == false) {
        obj.success = false;
        obj.message = "业务项目不存在";
        return obj;
    }
    var param = new Object();
    param.logicId = "account.saveAccountFavoriteSns";
    param.itemId = itemId;
    param.name = name;
    param.subName = subName;
    param.state = state;
    var att = new Object();
    // 发送请求
    var rs = eval("appJs.executeLogic('" + JSON.stringify(param) + "', '" + JSON.stringify(att) + "')");
    // 构造返回值
    obj = JSON.parse(rs);
    // 检查是否存在错误
    return obj;
}

// 加载导航菜单
// 执行画面initOptionsMenu方法,获取菜单项
function onCreateOptionsMenu(menus, param, attr) {
    var obj = new Object();
    if (typeof(param) == "undefined") {
        param = new Object();
    }
    if (typeof(appJs) == "undefined") {
        obj.success = false;
        obj.message = "业务不存在";
        alert(obj.message);
        return obj;
    }

    if (appJs.hasOwnProperty("onCreateOptionsMenu") == false) {
        obj.success = false;
        obj.message = "业务项目不存在";
        alert(obj.message);
        return obj;
    }

    // 发送请求
    param.menus = menus;
    var rs = eval("appJs.onCreateOptionsMenu('" + JSON.stringify(param) + "', '" + JSON.stringify(attr) + "')");
    obj = JSON.parse(rs);
    return obj;
}

// 执行业务逻辑
function executeLogic(logicId, param, attr, callback) {
    var obj = new Object();
    if (typeof(param) == "undefined") {
        param = new Object();
    }
    // 添加
    param.logicId = logicId;
    if (typeof(attr) == "undefined") {
        attr = new Object();
    }

    if (typeof(appJs) == "undefined") {
        if (param.isDevice) {
            obj.success = false;
            obj.message = "业务不存在";
            if (isNotEmpty(callback)) {
                callback(obj);
            }
            return obj;
        }
        // just test
        httpRequest(logicId, param, attr, callback);
        return obj;
    }

    if (appJs.hasOwnProperty("executeLogic") == false) {
        obj.success = false;
        obj.message = "业务项目不存在";
        if (isNotEmpty(callback)) {
            callback(obj);
        }
        return obj;
    }

    // 发送请求
    var rs = eval("appJs.executeLogic('" + JSON.stringify(param) + "', '" + JSON.stringify(attr) + "')");
    // 构造返回值
    obj = JSON.parse(rs);
    if (isNotEmpty(callback)) {
        callback(obj);
    }

    return obj;
}

// 执行网络请求
function httpRequest(logicId, param, attr, callback) {
    var obj = new Object();
    if (typeof(param) == "undefined") {
        param = new Object();
    }
    // 添加
    param.logicId = logicId;
    if (typeof(attr) == "undefined") {
        attr = new Object();
    }

    if (typeof(appJs) == "undefined") {
        if (param.isDevice) {
            obj.success = false;
            obj.message = "业务不存在";
            if (isNotEmpty(callback)) {
                callback(obj);
            } else {
                console.warn("callback is null.");
            }
            return;
        }
        // 仅供测试使用
        param.debug = true;
        // 检查是否返回正确url
        var logicUrl = getLogicUrl(logicId, param);
        if (isEmpty(logicUrl)) {
            obj.success = false;
            obj.message = "业务地址不存在";
            if (isNotEmpty(callback)) {
                callback(obj);
            } else {
                console.warn("callback is null.");
            }
            return;
        }
        $.ajax({
            url: logicUrl,
            data: param,
            cache: false,
            async: true,
            type: "GET",
            jsonp: "jsonpCallback",
            jsonpCallback: "jsonpCallback",
            dataType: 'jsonp',
            success: function (result) {
                obj = result;
                if (isNotEmpty(callback)) {
                    callback(obj);
                } else {
                    console.warn("callback is null.");
                }
            }
        });
        return;
    }

    // 发送请求
    var rs = eval("appJs.httpRequest('" + JSON.stringify(param) + "', '" + JSON.stringify(attr) + "')");
    // 构造返回值
    obj = JSON.parse(rs);
    // 检查是否存在错误
    if (isNotEmpty(callback)) {
        callback(obj);
    } else {
        console.warn("callback is null.");
    }

    return;
}

function getLogicUrl(logicId, param) {
    var urlMap = {
        "index.index": "http://imr.jsjrong.com.cn/m/index.do",
        "sns.indexSnsItemList": "http://imr.jsjrong.com.cn/m/sns/indexSnsItemList.do",
        "sns.indexSnsItem": "http://imr.jsjrong.com.cn/m/sns/indexSnsItem.do",
        "account.getAccountFavoriteSns": "http://imr.jsjrong.com.cn/m/sns/exeSaveFavoriteSns.do",
        "sns.indexSnsItemComment": "http://imr.jsjrong.com.cn/m/sns/comment/indexSnsItemComment.do",
        "sns.saveSnsItemComment": "http://imr.jsjrong.com.cn/m/sns/comment/exeSaveSnsItemComment.do",
        "account.indexAccountInfo": "http://imr.jsjrong.com.cn/m/account/indexAccountInfo.do",
        "product.indexServiceProductList": "http://imr.jsjrong.com.cn/m/service/indexServiceProductList.do"
    };
    return urlMap[logicId];
}
