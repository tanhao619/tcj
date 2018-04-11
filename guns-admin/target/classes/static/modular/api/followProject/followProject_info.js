/**
 * 初始化项目跟踪详情对话框
 */
var FollowProjectInfoDlg = {
    followProjectInfoData : {}
};

/**
 * 清除数据
 */
FollowProjectInfoDlg.clearData = function() {
    this.followProjectInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FollowProjectInfoDlg.set = function(key, val) {
    this.followProjectInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FollowProjectInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
FollowProjectInfoDlg.close = function() {
    parent.layer.close(window.parent.FollowProject.layerIndex);
}

/**
 * 收集数据
 */
FollowProjectInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
FollowProjectInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/followProject/add", function(data){
        Feng.success("添加成功!");
        window.parent.FollowProject.table.refresh();
        FollowProjectInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.followProjectInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
FollowProjectInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/followProject/update", function(data){
        Feng.success("修改成功!");
        window.parent.FollowProject.table.refresh();
        FollowProjectInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.followProjectInfoData);
    ajax.start();
}

$(function() {

});
