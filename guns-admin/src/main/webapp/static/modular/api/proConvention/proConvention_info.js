/**
 * 初始化履约信息详情对话框
 */
var ProConventionInfoDlg = {
    proConventionInfoData : {}
};

/**
 * 清除数据
 */
ProConventionInfoDlg.clearData = function() {
    this.proConventionInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProConventionInfoDlg.set = function(key, val) {
    this.proConventionInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProConventionInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProConventionInfoDlg.close = function() {
    parent.layer.close(window.parent.ProConvention.layerIndex);
}

/**
 * 收集数据
 */
ProConventionInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
ProConventionInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proConvention/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProConvention.table.refresh();
        ProConventionInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proConventionInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProConventionInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proConvention/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProConvention.table.refresh();
        ProConventionInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proConventionInfoData);
    ajax.start();
}

$(function() {

});
