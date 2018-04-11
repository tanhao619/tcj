/**
 * 初始化土地类载体资源详情对话框
 */
var ZateLandInfoDlg = {
    zateLandInfoData : {}
};

/**
 * 清除数据
 */
ZateLandInfoDlg.clearData = function() {
    this.zateLandInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ZateLandInfoDlg.set = function(key, val) {
    this.zateLandInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ZateLandInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ZateLandInfoDlg.close = function() {
    parent.layer.close(window.parent.ZateLand.layerIndex);
}

/**
 * 收集数据
 */
ZateLandInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
ZateLandInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/zateLand/add", function(data){
        Feng.success("添加成功!");
        window.parent.ZateLand.table.refresh();
        ZateLandInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.zateLandInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ZateLandInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/zateLand/update", function(data){
        Feng.success("修改成功!");
        window.parent.ZateLand.table.refresh();
        ZateLandInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.zateLandInfoData);
    ajax.start();
}

$(function() {

});
