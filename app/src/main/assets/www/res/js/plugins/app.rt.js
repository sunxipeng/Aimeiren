// 判断浏览器是否为IE6、7、8
(function ($) {
    $.extend({
        isIE: function (str, size) {
            return "\v" == "v";
        }
    });
})(jQuery);

// 扩展jquery方法
jQuery.fn.extend({
    isEmpty: function () {
        return $.type(this) == "undefined" || this == null;
    },
    isNotEmpty: function () {
        return this.isEmpty() == false;
    },
    checked: function (checked) {
        if ($.type(checked) == "undefined" || checked == true) {
            this.prop("checked", "checked");
        } else {
            this.removeAttr("checked");
        }
        return this;
    },
    selected: function (selected) {
        if ($.type(selected) == "undefined" || selected == true) {
            this.attr("selected", "selected");
        } else {
            this.removeAttr("selected");
        }
        return this;
    },
    disabled: function (disabled) {
        if ($.type(disabled) == "undefined" || disabled == true) {
            this.attr("disabled", "disabled");
        } else {
            this.removeAttr("disabled");
        }
        return this;
    },
    readonly: function (readonly) {
        if ($.type(readonly) == "undefined" || readonly == true) {
            this.attr("readonly", "readonly");
        } else {
            this.removeAttr("readonly");
        }
        return this;
    },
    hasAttr: function (attribute) {
        if (this.attr(attribute)) {
            return true;
        }
        return false;
    }
});

// 自动加载Form表单
jQuery.fn.extend({
    loadForm: function (obj) {
        var _obj = this;
        _obj.cleanForm();
        $.each(obj, function (key, value) {
            var _children = $("[name='" + key + "']", _obj);
            if (_children.isNotEmpty()) {
                _children.val(value);
            }
        });
    },
    // 清空form
    cleanForm: function () {
        return this.each(function () {
            $('input,select,textarea', this).each(function () {
                var t = this.type, tag = this.tagName.toLowerCase();
                if (t == 'text' || t == 'password' || t == 'file' || tag == 'textarea') {
                    this.value = '';
                } else if (t == 'checkbox' || t == 'radio') {
                    this.checked = false;
                } else if (tag == 'select') {
                    this.selectedIndex = 0;
                } else if (t == 'number') {
                    this.value = 0;
                } else if (t == 'hidden') {
                } else {
                    this.value = '';
                }
            });
        });
    }
});

// 扩展form.request方法异步求情扩展
jQuery.fn.extend({
    // AJAX 请求
    request: function (options) {
        if (typeof options == 'function') {
            options = {success: options};
        }
        var action = this.attr('action');
        var method = this.attr('method');
        var enctype = nullToSpace(this.attr('enctype'));
        options = $.extend({
            url: action || "",
            type: method || "post",
            enctype: enctype,
            dataType: "json",
            success: function () {
            }
        }, options);

        var t = "t=" + new Date().getTime();
        options.url += (options.url.indexOf("?") > 0 ? "&" : "?") + t + "&dt=" + options.dataType;

        // 设置回调函数
        var success = options.success;
        options.success = function (data) {
            $.response(data, options.dataType, function () {
                success(data);
            });
        };
        this.ajaxSubmit(options);
    }
});

// POST URL AJAX
(function ($) {
    $.getUrlParam = function (name, param) {
        //获取url中"?"符后的字串
        var url = window.location.search;
        var theRequest = new Object();
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            strs = str.split("&");
            for (var i = 0; i < strs.length; i++) {
                //就是这句的问题
                theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
                //之前用了unescape()
                //才会出现乱码
            }
            return theRequest[name];
        }
        if (isEmpty(param)) {
            return "";
        } else {
            return param[name];
        }
    };
    $.extend({
        // AJAX 请求
        request: function (options, param, callbackFun) {
            if (typeof options == 'string') {
                options = {url: options};
            }
            options.success = callbackFun;
            options.data = param;
            // 映射参数objcet
            settings = $.extend({
                type: "post",
                dataType: "json",
                async: true,
                cache: false,
                success: function () {
                }
            }, options);

            var t = "t=" + new Date().getTime();
            var url = settings.url + (settings.url.indexOf("?") > 0 ? "&" : "?") + t + "&dt=" + settings.dataType;

            return $.ajax({
                url: encodeURI(url),
                type: settings.type,
                enctype: settings.enctype,
                async: settings.async,
                cache: settings.cache,
                data: settings.data,
                dataType: settings.dataType,
                success: function (data) {
                    // 回调方法
                    $.response(data, settings.dataType, function () {
                        settings.success(data);
                    });
                }
            });
        },
        // AJAX 回调函数
        response: function (data, dataType, callback) {
            if (dataType == "json") {
                callback();
            } else {
                callback();
            }
        }
    });
})(jQuery);

//---------------------------start原型-------------------------------
// 全部替换
String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
};
// 去掉字符两端的空白字符
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};
// 判断字符串是否以指定的字符串开始
String.prototype.startsWith = function (str) {
    return this.substr(0, str.length) == str;
};
// 判断字符串是否以指定的字符串结束
String.prototype.endsWith = function (str) {
    return this.substr(this.length - str.length) == str;
};
// 返回字符的长度，一个中文算2个
String.prototype.chineseLength = function () {
    return this.replace(/[^\x00-\xff]/g, "**").length;
};
// 字符串转换成四舍五入转化为整型
String.prototype.toInteger = function (mode) {
    return this.toDouble().toInteger(mode);
};
// 字符串转换成Double
String.prototype.toDouble = function () {
    return parseFloat(this.replaceAll(",", ""));
};
// 字符串转换布尔类型
String.prototype.toBoolean = function () {
    if (this.isNumber() && this.toInteger() > 0) {
        return true;
    } else if (this == "true") {
        return true;
    } else {
        return false;
    }
};
// 字符串  类型 转换成Date类型
String.prototype.toDate = function () {
    return new Date(this.replace(/-/g, "/"));
};
// 判断字符串是否为YYYY-MM-DD Date类型
String.prototype.isDate = function () {
    return !isNaN(this.toDate());
};
// 格式化输出数值
String.prototype.formatString = function (mode) {
    return this.toDouble().formatString(mode);
};
// 判断是字符串是否为空
String.prototype.isEmpty = function () {
    return this.trim().length == 0;
};
// 判断是字符串是否为数值
String.prototype.isNumber = function () {
    var s = this.trim();
    return (s.search(/^[+-]?[0-9.]*$/) >= 0);
}
// 判断是否包含汉字
String.prototype.inChinese = function () {
    return this.length != this.chineseLength();
}
// 根据年龄获得出生年月 Date
String.prototype.brithdayToAge = function () {
    return this.toInteger().getBrithdayToAge();
};
// 转换成整型up:四舍五入进位;down:不进位
Number.prototype.toInteger = function (mode) {
    if (mode == "down") {
        return Math.floor(this);
    } else {
        return Math.round(this);
    }
}

// 格式化输出数值
Number.prototype.formatString = function (mode) {
    if ($.type(mode) == "undefined") mode = 0;
    return $.formatNumber(this, mode, "", ",");
}
// 根据年龄获得出生年月 Date
Number.prototype.brithdayToAge = function () {
    var now = new Date();
    now.setFullYear(now.getFullYear() - Math.round(this));
    now.setDate(now.getDate() - 1);
    return now;
}

/*格式化时间
 formatStr: yyyy:年	MM:月 dd:日 hh:小时 mm:分钟 ss:秒
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
// 日期转换年龄，小于一年则为小数显示天数，最大365天(0.365)
Date.prototype.getAge = function () {
    var age = 0;
    var year = this.getFullYear();
    var month = this.getMonth();
    var day = this.getDate();

    var now = new Date();
    var nowYear = now.getFullYear();
    var nowMonth = now.getMonth();
    var nowDay = now.getDate();

    if (nowYear == year) {
        // 同年为0岁，如果年龄为0岁，则计算其天数
        var date = parseInt(Math.abs(now - this) / 1000 / 60 / 60 / 24) // 把相差的毫秒数转换为天数
        age = date / 1000;
    } else {
        var ageDiff = nowYear - year; // 年之差
        if (ageDiff > 0) {
            if (nowMonth == month) {
                var dayDiff = nowDay - day;// 日之差
                if (dayDiff < 0) {
                    age = ageDiff - 1;
                } else {
                    age = ageDiff;
                }
            } else {
                var monthDiff = nowMonth - month;// 月之差
                if (monthDiff < 0) {
                    age = ageDiff - 1;
                } else {
                    age = ageDiff;
                }
            }
        } else {
            age = -1;// 返回-1 表示出生日期输入错误 晚于今天
        }
    }
    return age;
}
// 判断当前中是否包含此项
Array.prototype.contains = function (value) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == value) {
            return true;
        }
    }
    return false;
}
//---------------------------end原型-------------------------------
// 是否有某个属性
function hasAttr(obj, name) {
    if (typeof(obj) == 'undefined' || object == null) {
        return false;
    }
    return obj.hasOwnProperty(name);
}

function nullToSpace(object) {
    return isEmpty(object) ? "" : object;
}

function val(val, def) {
    return isEmpty(val) ? def : val;
}

function isEmpty(object) {
    if (typeof(object) == "undefined" || object == null || object == '' || object == 'null') {
        return true;
    }
    return false;
}

function isNotEmpty(object) {
    return !isEmpty(object);
}

/*
 * 格式化long型时间
 */
function formatLongDate(longDate, format, formatZero) {
    if (typeof(formatZero) == "undefined" || formatZero == null) {
        formatZero = false;
    }
    if (longDate == 0 && formatZero == false) {
        return "";
    }
    if (longDate != null) {
        return new Date(longDate).format(format);
    } else {
        return "";
    }
}

/*
 * long型时间,转年龄
 */
function longDateToAge(longDate, formatZero) {
    if (formatZero != true) {
        return "";
    }
    if (longDate != null) {
        return new Date(longDate).getAge();
    } else {
        return "";
    }
}

/*
 * 获取uuid
 */
function getUUID() {
    return Math.uuid();
}

/*
 * 构造url,追加当前url参数
 */
function buildURL(url, paramArray) {
    var href = location.href;
    var param = {};
    var localParam;
    var urlParam;
    if (href.indexOf("?") > 0) {
        localParam = href.substring(href.indexOf('?') + 1, href.length).split("&");
        param = putParam(param, localParam);
    }
    var first = true;
    if (url.indexOf("?") > 0) {
        first = false;
        urlParam = url.substring(url.indexOf('?') + 1, url.length).split("&");
        param = putParam(param, urlParam);
    }
    if (isNotEmpty(paramArray)) {
        for (var item in paramArray) {
            param[item] = paramArray[item];
        }
    }
    for (var item in param) {
        if (first) {
            first = false;
            url += "?" + item + "=" + nullToSpace(param[item]).toString();
        } else {
            url += "&" + item + "=" + nullToSpace(param[item]).toString();
        }
    }
    return url;
}

function putParam(param, paramArray) {
    for (var i = 0; i < paramArray.length; i++) {
        var localParamArray = paramArray[i].split("=");
        param[localParamArray[0]] = nullToSpace(localParamArray[1]).toString();
    }
    return param;
}