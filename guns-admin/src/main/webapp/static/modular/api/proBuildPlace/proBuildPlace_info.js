/**
 * 初始化拟建设地点详情对话框
 */
var ProBuildPlaceInfoDlg = {
    proBuildPlaceInfoData : {}
};

/**
 * 清除数据
 */
ProBuildPlaceInfoDlg.clearData = function() {
    this.proBuildPlaceInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProBuildPlaceInfoDlg.set = function(key, val) {
    this.proBuildPlaceInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProBuildPlaceInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProBuildPlaceInfoDlg.close = function() {
    parent.layer.close(window.parent.ProBuildPlace.layerIndex);
}

/**
 * 收集数据
 */
ProBuildPlaceInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
ProBuildPlaceInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proBuildPlace/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProBuildPlace.table.refresh();
        ProBuildPlaceInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proBuildPlaceInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProBuildPlaceInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proBuildPlace/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProBuildPlace.table.refresh();
        ProBuildPlaceInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proBuildPlaceInfoData);
    ajax.start();
}

$(function() {

});
