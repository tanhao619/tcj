/**
 * 初始化部门详情对话框
 */
var DeptInfoDlg = {
    deptInfoData : {},
    zTreeInstance : null,
    validateFields: {
        simplenameUnit: {
            validators: {
                notEmpty: {
                    message: '单位名称不能为空'
                }
            }
        },
        simplenameOffice: {
            validators: {
                notEmpty: {
                    message: '科室不能为空'
                }
            }
        },
        fullname: {
            validators: {
                notEmpty: {
                    message: '部门全称不能为空'
                }
            }
        },
        pName: {
            validators: {
                notEmpty: {
                    message: '上级名单位称不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
DeptInfoDlg.clearData = function() {
    this.deptInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeptInfoDlg.set = function(key, val) {
    this.deptInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeptInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DeptInfoDlg.close = function() {
    parent.layer.close(window.parent.Dept.layerIndex);
}

/**
 * 点击部门ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
DeptInfoDlg.onClickDept = function(e, treeId, treeNode) {
    $("#pName").attr("value", DeptInfoDlg.zTreeInstance.getSelectedDeptVal());
    $("#pid").attr("value", treeNode.id);
}

/**
 * 显示部门选择的树
 *
 * @returns
 */
DeptInfoDlg.showDeptSelectTree = function() {
    var pName = $("#pName");
    var pNameOffset = $("#pName").offset();
    $("#parentDeptMenu").css({
        left : pNameOffset.left + "px",
        top : pNameOffset.top + pName.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

/**
 * 隐藏部门选择的树
 */
DeptInfoDlg.hideDeptSelectTree = function() {
    $("#parentDeptMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

/**
 * 收集数据
 */
DeptInfoDlg.collectData = function() {
    //this.set('id').set('simplename').set('fullname').set('tips').set('num').set('pid');
    if ($("#simplenameUnit").val() != undefined && $("#simplenameUnit").val() != ""){
        this.deptInfoData.simplename = $("#simplenameUnit").val();
        this.deptInfoData.remark = $("#remarkUnit").val();
        this.set('id').set('fullname').set('num').set('pid');
    }

    if ($("#simplenameOffice").val() != undefined && $("#simplenameOffice").val() != ""){
        this.deptInfoData.simplename = $("#simplenameOffice").val();
        this.deptInfoData.remark = $("#remarkOffice").val();
        this.set('id').set('fullname').set('num').set('pid');
    }
}

/**
 * 验证数据是否为空
 */
DeptInfoDlg.unitValidate = function () {
    $('#deptUnitInfoForm').data("bootstrapValidator").resetForm();
    $('#deptUnitInfoForm').bootstrapValidator('validate');
    return $("#deptUnitInfoForm").data('bootstrapValidator').isValid();
};
DeptInfoDlg.officeValidate = function () {
    $('#deptOfficeInfoForm').data("bootstrapValidator").resetForm();
    $('#deptOfficeInfoForm').bootstrapValidator('validate');
    return $("#deptOfficeInfoForm").data('bootstrapValidator').isValid();
};
/**
 * 提交添加部门
 */
DeptInfoDlg.addSubmit = function(type) {

    this.clearData();
    this.collectData();

    if (type == 'unit'){
        if (!this.unitValidate()) {
            return;
        }
    }
    if (type == 'office'){
        if (!this.officeValidate()) {
            return;
        }
    }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dept/add", function(data){
        if (data.code == 400){
            Feng.error(data.message);
        }else {
            Feng.success("添加成功!");
            window.parent.Dept.table.refresh();
            DeptInfoDlg.close();
        }
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deptInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
DeptInfoDlg.editSubmit = function(type) {

    this.clearData();
    this.collectData();

    if (type == 'unit'){
        if (!this.unitValidate()) {
            return;
        }
    }
    if (type == 'office'){
        if (!this.officeValidate()) {
            return;
        }
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dept/update", function(data){
        if (data.code == 400){
            Feng.error(data.message);
        }else {
            Feng.success("修改成功!");
            window.parent.Dept.table.refresh();
            DeptInfoDlg.close();
        }
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deptInfoData);
    ajax.start();
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentDeptMenu" || $(
            event.target).parents("#parentDeptMenu").length > 0)) {
        DeptInfoDlg.hideDeptSelectTree();
    }
}
function clearForm() {
    $("#simplenameUnit").val("");
    $("#remarkUnit").val("");

    $("#simplenameOffice").val("");
    $("#remarkOffice").val("");
    $("#pName").attr("value", "");
}
$(function() {
    Feng.initValidator("deptUnitInfoForm", DeptInfoDlg.validateFields);
    Feng.initValidator("deptOfficeInfoForm", DeptInfoDlg.validateFields);
    /*var ztree = new $ZTree("parentDeptMenuTree", "/dept/tree");*/
    var ztree = new $ZTree("parentDeptMenuTree", "/dept/getDeptTree");
    ztree.bindOnClick(DeptInfoDlg.onClickDept);
    ztree.init();
    DeptInfoDlg.zTreeInstance = ztree;
});
