/**
 * OA管理管理初始化
 */
var BanjieStep = {
    id: "BanjieStepTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BanjieStep.initColumn = function () {
    return [
        {title: '办结编号', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '办结附件', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '办公室发起科员', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '发起时间', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '指派科室', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '指派科长', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '指派时间', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '办结状态', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '办结时间', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: BanjieStep.formatOperate}
    ];
};

BanjieStep.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn = "<button class='btn btn-primary'   onclick=''>审批记录</button>";
    return btn + "&nbsp;&nbsp;&nbsp;";
}

/**
 * 打开查看OA管理详情,不验证是否选中
 */
BanjieStep.openWorkflowStepOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/workflowStep/workflowStep_update/' + id
    });
    this.layerIndex = index;
};


/**
 * 查询OA管理列表
 */
BanjieStep.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BanjieStep.table.refresh({query: queryData});
};
/**
 * 重置查询条件
 */
BanjieStep.resetSearch = function () {
    BanjieStep.search();
};

$(function () {
    var defaultColunms = BanjieStep.initColumn();
    var table = new BSTable(BanjieStep.id, "", defaultColunms);
    table.setPaginationType("client");
    BanjieStep.table = table.init();
});
