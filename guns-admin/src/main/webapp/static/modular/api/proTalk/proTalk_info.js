/**
 * 初始化洽谈信息详情对话框
 */
var ProTalkInfoDlg = {
    proTalkInfoData : {}
};

/**
 * 清除数据
 */
ProTalkInfoDlg.clearData = function() {
    this.proTalkInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProTalkInfoDlg.set = function(key, val) {
    this.proTalkInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProTalkInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProTalkInfoDlg.close = function() {
    parent.layer.close(window.parent.ProTalk.layerIndex);
}

/**
 * 收集数据
 */
ProTalkInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
ProTalkInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proTalk/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProTalk.table.refresh();
        ProTalkInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proTalkInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProTalkInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proTalk/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProTalk.table.refresh();
        ProTalkInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proTalkInfoData);
    ajax.start();
}

$(function() {

});
