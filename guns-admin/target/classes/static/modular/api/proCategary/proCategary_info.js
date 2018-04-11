/**
 * 初始化行业分类详情对话框
 */
var ProCategaryInfoDlg = {
    proCategaryInfoData : {}
};

/**
 * 清除数据
 */
ProCategaryInfoDlg.clearData = function() {
    this.proCategaryInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProCategaryInfoDlg.set = function(key, val) {
    this.proCategaryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProCategaryInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProCategaryInfoDlg.close = function() {
    parent.layer.close(window.parent.ProCategary.layerIndex);
}

/**
 * 收集数据
 */
ProCategaryInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
ProCategaryInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proCategary/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProCategary.table.refresh();
        ProCategaryInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proCategaryInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProCategaryInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proCategary/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProCategary.table.refresh();
        ProCategaryInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proCategaryInfoData);
    ajax.start();
}

$(function() {

});
