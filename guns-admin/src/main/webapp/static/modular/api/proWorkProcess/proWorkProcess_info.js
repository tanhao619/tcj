/**
 * 初始化工作进度详情对话框
 */
var ProWorkProcessInfoDlg = {
    proWorkProcessInfoData : {}
};

/**
 * 清除数据
 */
ProWorkProcessInfoDlg.clearData = function() {
    this.proWorkProcessInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProWorkProcessInfoDlg.set = function(key, val) {
    this.proWorkProcessInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProWorkProcessInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProWorkProcessInfoDlg.close = function() {
    parent.layer.close(window.parent.ProWorkProcess.layerIndex);
}

/**
 * 收集数据
 */
ProWorkProcessInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
ProWorkProcessInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proWorkProcess/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProWorkProcess.table.refresh();
        ProWorkProcessInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proWorkProcessInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProWorkProcessInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proWorkProcess/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProWorkProcess.table.refresh();
        ProWorkProcessInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proWorkProcessInfoData);
    ajax.start();
}

$(function() {

});
