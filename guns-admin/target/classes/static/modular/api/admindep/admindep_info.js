/**
 * 初始化部门信息详情对话框
 */
var AdmindepInfoDlg = {
    admindepInfoData : {}
};

/**
 * 清除数据
 */
AdmindepInfoDlg.clearData = function() {
    this.admindepInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AdmindepInfoDlg.set = function(key, val) {
    this.admindepInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AdmindepInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AdmindepInfoDlg.close = function() {
    parent.layer.close(window.parent.Admindep.layerIndex);
}

/**
 * 收集数据
 */
AdmindepInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配
    this.set('id').set("name").set("admindepId").set("descript").set("imgurl").set("createdTime");
}

/**
 * 提交添加
 */
AdmindepInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/admindep/add", function(data){
        Feng.success("添加成功!");
        window.parent.Admindep.table.refresh();
        AdmindepInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.admindepInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AdmindepInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/admindep/update", function(data){
        Feng.success("修改成功!");
        window.parent.Admindep.table.refresh();
        AdmindepInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.admindepInfoData);
    ajax.start();
}

$(function() {

});
