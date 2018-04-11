/**
 * 初始化项目操作详情对话框
 */
var ProHistoryInfoDlg = {
    proHistoryInfoData : {}
};

/**
 * 清除数据
 */
ProHistoryInfoDlg.clearData = function() {
    this.proHistoryInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProHistoryInfoDlg.set = function(key, val) {
    this.proHistoryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProHistoryInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProHistoryInfoDlg.close = function() {
    parent.layer.close(window.parent.ProHistory.layerIndex);
}

/**
 * 收集数据
 */
ProHistoryInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
ProHistoryInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proHistory/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProHistory.table.refresh();
        ProHistoryInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proHistoryInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProHistoryInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proHistory/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProHistory.table.refresh();
        ProHistoryInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proHistoryInfoData);
    ajax.start();
}

$(function() {

});
