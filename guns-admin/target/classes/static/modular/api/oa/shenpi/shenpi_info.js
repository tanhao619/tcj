/**
 * 初始化OA管理详情对话框
 */
var WorkflowStepInfoDlg = {
    workflowStepInfoData : {}
};

/**
 * 清除数据
 */
WorkflowStepInfoDlg.clearData = function() {
    this.workflowStepInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkflowStepInfoDlg.set = function(key, val) {
    this.workflowStepInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkflowStepInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WorkflowStepInfoDlg.close = function() {
    parent.layer.close(window.parent.WorkflowStep.layerIndex);
}

/**
 * 收集数据
 */
WorkflowStepInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
WorkflowStepInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workflowStep/add", function(data){
        Feng.success("添加成功!");
        window.parent.WorkflowStep.table.refresh();
        WorkflowStepInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workflowStepInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
WorkflowStepInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workflowStep/update", function(data){
        Feng.success("修改成功!");
        window.parent.WorkflowStep.table.refresh();
        WorkflowStepInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workflowStepInfoData);
    ajax.start();
}

$(function() {

});
