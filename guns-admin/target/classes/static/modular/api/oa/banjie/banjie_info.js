/**
 * 初始化OA管理详情对话框
 */
var BanjieStepInfoDlg = {
    banjieStepStepInfoData : {}
};

/**
 * 清除数据
 */
BanjieStepInfoDlg.clearData = function() {
    this.banjieStepStepInfoData = {};
}


/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BanjieStepInfoDlg.get = function(key) {
    return $("#" + key).val();
}


/**
 * 收集数据
 */
BanjieStepInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}
$(function() {

});
