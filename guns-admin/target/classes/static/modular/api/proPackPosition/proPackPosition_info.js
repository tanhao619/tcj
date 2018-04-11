/**
 * 初始化策划包装定位详情对话框
 */
var ProPackPositionInfoDlg = {
    proPackPositionInfoData : {}
};

/**
 * 清除数据
 */
ProPackPositionInfoDlg.clearData = function() {
    this.proPackPositionInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProPackPositionInfoDlg.set = function(key, val) {
    this.proPackPositionInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProPackPositionInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProPackPositionInfoDlg.close = function() {
    parent.layer.close(window.parent.ProPackPosition.layerIndex);
}

/**
 * 收集数据
 */
ProPackPositionInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
ProPackPositionInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proPackPosition/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProPackPosition.table.refresh();
        ProPackPositionInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proPackPositionInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProPackPositionInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proPackPosition/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProPackPosition.table.refresh();
        ProPackPositionInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proPackPositionInfoData);
    ajax.start();
}

$(function() {

});
