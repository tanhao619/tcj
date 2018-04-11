/**
 * OA管理管理初始化
 */
var GuiDangStep = {
    id: "GuidangStepTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
GuiDangStep.initColumn = function () {
    return [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '审批编号', field: 'ordnum', visible: true, align: 'center', valign: 'middle'},
        {title: '审批类型', field: 'type', visible: true, align: 'center', valign: 'middle'},
        {title: '发起人', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '发起人角色', field: 'role', visible: true, align: 'center', valign: 'middle'},
        {title: '审批附件', field: 'attachments', visible: true, align: 'center', valign: 'middle'},
        {title: '发起时间', field: 'apprTime', visible: true, align: 'center', valign: 'middle'},
        {title: '审批通过时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: GuiDangStep.formatOperate},
        {title: '备注', field: '', visible: true, align: 'center', valign: 'middle',formatter: GuiDangStep.formatOperate2}
    ];
};

GuiDangStep.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn = "<button class='btn btn-primary'   onclick=''>审批记录</button>";

    <!-- 审批类型:1.请假审批;2.销假审批;3.出差审批;4.接待审批;5.公务用车审批;6.驻点人员请假审批;7.发文审批;8.收文审批-->
    if(row.type == '1'){//请假审批
        var btn_thin =  "<button class='btn btn-primary'   onclick=''>请假时间</button>";
    }

    if(row.type == '8'){//收文审批
        var btn_thin =  "<button class='btn btn-primary'   onclick=''>办结详情</button>";
    }
    if(btn_thin != undefined){
        return  btn_thin+"|"+ btn + "&nbsp;&nbsp;&nbsp;";
    }
    return btn + "&nbsp;&nbsp;&nbsp;";
};
GuiDangStep.formatOperate2 = function (val, row, index) {
    var id = row.id;

    <!-- 审批类型:1.请假审批;2.销假审批;3.出差审批;4.接待审批;5.公务用车审批;6.驻点人员请假审批;7.发文审批;8.收文审批-->
    if(row.type == '1' ) {//请假审批是否销假，1显示未销假，0销假

    }
    return btn + "&nbsp;&nbsp;&nbsp;";
};




/**
 * 查询OA管理列表
 */
GuiDangStep.search = function () {
    var queryData = {};
    // queryData['condition'] = $("#condition").val();
    GuiDangStep.table.refresh({query: queryData});
};

GuiDangStep.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='WorkflowStep.openWorkflowStepOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='WorkflowStep.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
};

/**
 * 重置查询条件
 */
GuiDangStep.resetSearch = function () {
    $("#guidangtype").val("");
    $("#guidangapprMan").val("");
    $("#guidangTimeB").val("");
    $("#guidangTimeE").val("");
    $("#guidangendTimeB").val("");
    $("#guidangendTimeE").val("");
    GuiDangStep.search();
};
$(function () {
    var defaultColunms = GuiDangStep.initColumn();
    var table = new BSTable(GuiDangStep.id, "", defaultColunms);
    table.setPaginationType("client");
    GuiDangStep.table = table.init();
});
