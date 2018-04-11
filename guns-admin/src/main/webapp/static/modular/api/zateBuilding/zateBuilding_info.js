/**
 * 初始化楼宇或闲置厂房载体资源详情对话框
 */
var ZateBuildingInfoDlg = {
    zateBuildingInfoData : {}
};

/**
 * 清除数据
 */
ZateBuildingInfoDlg.clearData = function() {
    this.zateBuildingInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ZateBuildingInfoDlg.set = function(key, val) {
    this.zateBuildingInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ZateBuildingInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ZateBuildingInfoDlg.close = function() {
    parent.layer.close(window.parent.ZateBuilding.layerIndex);
}

/**
 * 收集数据
 */
ZateBuildingInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
ZateBuildingInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/zateBuilding/add", function(data){
        Feng.success("添加成功!");
        window.parent.ZateBuilding.table.refresh();
        ZateBuildingInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.zateBuildingInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ZateBuildingInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/zateBuilding/update", function(data){
        Feng.success("修改成功!");
        window.parent.ZateBuilding.table.refresh();
        ZateBuildingInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.zateBuildingInfoData);
    ajax.start();
}

$(function() {

});
