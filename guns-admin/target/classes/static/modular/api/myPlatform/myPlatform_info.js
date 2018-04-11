/**
 * 初始化我的工作台详情对话框
 */
var MyPlatformInfoDlg = {
    myPlatformInfoData : {}
};

/**
 * 清除数据
 */
MyPlatformInfoDlg.clearData = function() {
    this.myPlatformInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MyPlatformInfoDlg.set = function(key, val) {
    this.myPlatformInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MyPlatformInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MyPlatformInfoDlg.close = function() {
    parent.layer.close(window.parent.MyPlatform.layerIndex);
}

/**
 * 收集数据
 */
MyPlatformInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
MyPlatformInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/myPlatform/add", function(data){
        Feng.success("添加成功!");
        window.parent.MyPlatform.table.refresh();
        MyPlatformInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.myPlatformInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MyPlatformInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/myPlatform/update", function(data){
        Feng.success("修改成功!");
        window.parent.MyPlatform.table.refresh();
        MyPlatformInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.myPlatformInfoData);
    ajax.start();
}

$(function() {

});
