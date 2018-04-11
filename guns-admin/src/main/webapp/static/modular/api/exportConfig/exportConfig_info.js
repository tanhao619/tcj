/**
 * 初始化导出配置详情对话框
 */
var ExportConfigInfoDlg = {
    exportConfigInfoData : {}
};

/**
 * 清除数据
 */
ExportConfigInfoDlg.clearData = function() {
    this.exportConfigInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExportConfigInfoDlg.set = function(key, val) {
    this.exportConfigInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ExportConfigInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ExportConfigInfoDlg.close = function() {
    parent.layer.close(window.parent.ExportConfig.layerIndex);
}

/**
 * 收集数据
 */
ExportConfigInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
ExportConfigInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/normalExportConfig/add", function(data){
        Feng.success("添加成功!");
        window.parent.ExportConfig.table.refresh();
        ExportConfigInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.exportConfigInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ExportConfigInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/normalExportConfig/update", function(data){
        Feng.success("修改成功!");
        window.parent.ExportConfig.table.refresh();
        ExportConfigInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.exportConfigInfoData);
    ajax.start();
}

$(function() {

});
