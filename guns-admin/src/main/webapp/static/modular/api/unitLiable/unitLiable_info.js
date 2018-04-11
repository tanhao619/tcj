/**
 * 初始化责任单位详情对话框
 */
var UnitLiableInfoDlg = {
    unitLiableInfoData : {}
};

/**
 * 清除数据
 */
UnitLiableInfoDlg.clearData = function() {
    this.unitLiableInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UnitLiableInfoDlg.set = function(key, val) {
    this.unitLiableInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UnitLiableInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UnitLiableInfoDlg.close = function() {
    parent.layer.close(window.parent.UnitLiable.layerIndex);
}

/**
 * 收集数据
 */
UnitLiableInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
UnitLiableInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/unitLiable/add", function(data){
        Feng.success("添加成功!");
        window.parent.UnitLiable.table.refresh();
        UnitLiableInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.unitLiableInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UnitLiableInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/unitLiable/update", function(data){
        Feng.success("修改成功!");
        window.parent.UnitLiable.table.refresh();
        UnitLiableInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.unitLiableInfoData);
    ajax.start();
}

$(function() {

});
