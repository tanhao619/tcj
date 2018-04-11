/**
 * 初始化附件信息详情对话框
 */
var ProAttachmentInfoDlg = {
    proAttachmentInfoData : {}
};

/**
 * 清除数据
 */
ProAttachmentInfoDlg.clearData = function() {
    this.proAttachmentInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProAttachmentInfoDlg.set = function(key, val) {
    this.proAttachmentInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProAttachmentInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProAttachmentInfoDlg.close = function() {
    parent.layer.close(window.parent.ProAttachment.layerIndex);
}

/**
 * 收集数据
 */
ProAttachmentInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
ProAttachmentInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proAttachment/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProAttachment.table.refresh();
        ProAttachmentInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proAttachmentInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProAttachmentInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proAttachment/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProAttachment.table.refresh();
        ProAttachmentInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proAttachmentInfoData);
    ajax.start();
}

$(function() {

});
