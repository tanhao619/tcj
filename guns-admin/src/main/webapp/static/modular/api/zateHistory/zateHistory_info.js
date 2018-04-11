/**
 * 初始化载体资源信息变更详情对话框
 */
var ZateHistoryInfoDlg = {
    zateHistoryInfoData : {}
};

/**
 * 清除数据
 */
ZateHistoryInfoDlg.clearData = function() {
    this.zateHistoryInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ZateHistoryInfoDlg.set = function(key, val) {
    this.zateHistoryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ZateHistoryInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ZateHistoryInfoDlg.close = function() {
    parent.layer.close(window.parent.ZateHistory.layerIndex);
}

/**
 * 收集数据
 */
ZateHistoryInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set("createTime");
}

/**
 * 提交添加
 */
ZateHistoryInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    console.log(this.zateHistoryInfoData);

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/zateHistory/add", function(data){
        Feng.success("添加成功!");
        window.parent.ZateHistory.table.refresh();
        ZateHistoryInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.zateHistoryInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ZateHistoryInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/zateHistory/update", function(data){
        Feng.success("修改成功!");
        window.parent.ZateHistory.table.refresh();
        ZateHistoryInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.zateHistoryInfoData);
    ajax.start();
}

$(function() {

});
