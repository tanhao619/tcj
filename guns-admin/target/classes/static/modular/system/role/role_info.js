/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var RolInfoDlg = {
    roleInfoData: {},
    deptZtree: null,
    pNameZtree: null,
    validateFields: {
        duty: {
            validators: {
                notEmpty: {
                    message: '职责不能为空'
                }
            }
        },
        deptName: {
            validators: {
                notEmpty: {
                    message: '所在科室不能为空'
                }
            }
        },
        pName: {
            validators: {
                notEmpty: {
                    message: '父级名称不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
RolInfoDlg.clearData = function () {
    this.roleInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RolInfoDlg.set = function (key, val) {
    this.roleInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RolInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
RolInfoDlg.close = function () {
    parent.layer.close(window.parent.Role.layerIndex);
};

/**
 * 点击部门input框时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
RolInfoDlg.onClickDept = function (e, treeId, treeNode) {
     var treeNodeName =  RolInfoDlg.deptZtree.getSelectedVal();
    //$("#deptName").attr("value", RolInfoDlg.deptZtree.getSelectedVal());
    $("#deptName").attr("value", treeNodeName);
    $("#deptid").attr("value", treeNode.id);

    scapRoleName(treeNodeName);
};
function matchDuty(dutyType) {
    if (dutyType == 1){return "-科员"}
    else if (dutyType == 2){return "-科长"}
    else if (dutyType == 3){return "-分管副局长"}
    else {return ""}

}
function matchFirstClickDuty(dutyType) {
    if (dutyType == 1){return "科员"}
    else if (dutyType == 2){return "科长"}
    else if (dutyType == 3){return "分管副局长"}
    else {return ""}

}
// 监控duty改变 start add by lgg
$("#duty").change(function(){
    //var treeNodeName =  RolInfoDlg.deptZtree.getSelectedVal();
    var treeNodeName =  $("#deptName").val();
    scapRoleName(treeNodeName);
});
// 封装角色名字
function scapRoleName(treeNodeName) {

    var duty = matchDuty($("#duty").val());
    var nodeName = RolInfoDlg.deptZtree.getSelectedRoleVal();
    if (nodeName == undefined){
        var dutyName = matchFirstClickDuty($("#duty").val());
        var oldName = $("#name").val();
        var oldNameList = oldName.split("-");
        var newName;
        if(oldNameList.length == 3){
            newName = oldNameList[0]+"-"+oldNameList[1] +"-"+dutyName;
        }
        if(oldNameList.length == 1){
            newName = dutyName;
        }

        $("#name").attr("value",newName);
        return;
    }
    var pNodeName = RolInfoDlg.deptZtree.getSelectedParentVal();
    if (treeNodeName != undefined && pNodeName != "" && pNodeName != undefined){
        $("#name").attr("value",pNodeName + "-" + nodeName + duty);
    }
}
// 监控duty改变 start add by lgg

RolInfoDlg.onDblClickDept = function (e, treeId, treeNode) {
    $("#deptName").attr("value", RolInfoDlg.deptZtree.getSelectedVal());
    $("#deptid").attr("value", treeNode.id);
    $("#deptContent").fadeOut("fast");
};

/**
 * 点击父级菜单input框时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
RolInfoDlg.onClickPName = function (e, treeId, treeNode) {
    $("#pName").attr("value", RolInfoDlg.pNameZtree.getSelectedVal());
    $("#pid").attr("value", treeNode.id);
};

/**
 * 显示部门选择的树
 *
 * @returns
 */
RolInfoDlg.showDeptSelectTree = function () {
    Feng.showInputTree("deptName", "deptContent");
};

/**
 * 显示父级菜单的树
 *
 * @returns
 */
RolInfoDlg.showPNameSelectTree = function () {
    Feng.showInputTree("pName", "pNameContent");
};

/**
 * 收集数据
 */
RolInfoDlg.collectData = function () {
    //this.set('id').set('name').set('pid').set('deptid').set('tips').set('num');
    this.set('id').set('name').set('pid').set('deptid').set('remark').set('duty');
};

/**
 * 验证数据是否为空
 */
RolInfoDlg.validate = function () {
    $('#roleInfoForm').data("bootstrapValidator").resetForm();
    $('#roleInfoForm').bootstrapValidator('validate');
    return $("#roleInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加用户
 */
RolInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/role/add", function (data) {
        if (data.code == 200){
            Feng.success("添加成功!");
            window.parent.Role.table.refresh();
            RolInfoDlg.close();
        }else {
            Feng.error(data.message);
        }

    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.roleInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
RolInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/role/edit", function (data) {
        if (data.code == 200){
            Feng.success("修改成功!");
            window.parent.Role.table.refresh();
            RolInfoDlg.close();
        }else {
            Feng.error(data.message);
        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.roleInfoData);
    ajax.start();
};

$(function () {
    Feng.initValidator("roleInfoForm", RolInfoDlg.validateFields);

    var deptTree = new $ZTree("deptTree", "/dept/tree");
    deptTree.bindOnClick(RolInfoDlg.onClickDept);
    deptTree.bindOnDblClick(RolInfoDlg.onDblClickDept)
    deptTree.init();
    RolInfoDlg.deptZtree = deptTree;

    var pNameTree = new $ZTree("pNameTree", "/role/roleTreeList");
    pNameTree.bindOnClick(RolInfoDlg.onClickPName);
    pNameTree.init();
    RolInfoDlg.pNameZtree = pNameTree;

    //初始化职责选项
    $("#duty").val($("#detyValue").val());
});
