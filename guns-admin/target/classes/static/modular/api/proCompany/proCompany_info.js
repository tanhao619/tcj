/**
 * 初始化投资方公司详情对话框
 */
var ProCompanyInfoDlg = {
    proCompanyInfoData : {}
};

/**
 * 清除数据
 */
ProCompanyInfoDlg.clearData = function() {
    this.proCompanyInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProCompanyInfoDlg.set = function(key, val) {
    this.proCompanyInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProCompanyInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProCompanyInfoDlg.close = function() {
    parent.layer.close(window.parent.ProCompany.layerIndex);
}

/**
 * 收集数据
 */
ProCompanyInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
ProCompanyInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proCompany/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProCompany.table.refresh();
        ProCompanyInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proCompanyInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProCompanyInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/proCompany/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProCompany.table.refresh();
        ProCompanyInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.proCompanyInfoData);
    ajax.start();
}

$(function() {

});
