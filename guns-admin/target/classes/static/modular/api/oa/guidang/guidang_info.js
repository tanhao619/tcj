/**
 * 初始化OA管理详情对话框
 */
var GuiDangStepInfoDlg = {
    guiDangStepInfoData : {}
};

/**
 * 清除数据
 */
GuiDangStepInfoDlg.clearData = function() {
    this.guiDangStepInfoDlg = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
GuiDangStepInfoDlg.set = function(key, val) {
    this.guiDangStepInfoDlg[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
GuiDangStepInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
GuiDangStepInfoDlg.close = function() {
    parent.layer.close(window.parent.WorkflowStep.layerIndex);
}

/**
 * 收集数据
 */
GuiDangStepInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}



$(function() {

});
