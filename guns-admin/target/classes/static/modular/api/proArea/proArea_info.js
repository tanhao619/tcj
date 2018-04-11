/**
 * 初始化项目地址及用地详情对话框
 */
var ProAreaInfoDlg = {
    proAreaInfoData : {}
};

/**
 * 清除数据
 */
ProAreaInfoDlg.clearData = function() {
    this.proAreaInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProAreaInfoDlg.set = function(key, val) {
    this.proAreaInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProAreaInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProAreaInfoDlg.close = function() {
    parent.layer.close(window.parent.ProArea.layerIndex);
}

/**
 * 收集数据
 */
ProAreaInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
ProAreaInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proArea/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProArea.table.refresh();
        ProAreaInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proAreaInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProAreaInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proArea/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProArea.table.refresh();
        ProAreaInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proAreaInfoData);
    ajax.start();
}

$(function() {

});
