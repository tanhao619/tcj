/**
 * 项目操作管理初始化
 */
var ProHistory = {
    id: "ProHistoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
ProHistory.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: ProHistory.formatOperate}
    ];
};

ProHistory.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProHistory.openProHistoryDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProHistory.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}
/**
 * 检查是否选中
 */
ProHistory.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProHistory.seItem = selected[0];
        ProHistory.dealDelIds(selected);
        return true;
    }
};

/**
 * 点击添加项目操作
 */
ProHistory.openAddProHistory = function () {
    var index = layer.open({
        type: 2,
        title: '添加项目操作',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proHistory/proHistory_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看项目操作详情
 */
ProHistory.openProHistoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '项目操作详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/proHistory/proHistory_update/' + ProHistory.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看项目操作详情,不验证是否选中
 */
ProHistory.openProHistoryOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proHistory/proHistory_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除项目操作
 */
ProHistory.delete = function () {
    if (this.check()) {
         var operation = function(){
             var ajax = new $ax(Feng.ctxPath + "/proHistory/delete", function (data) {
                    Feng.success("删除成功!");
                    ProHistory.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("ids",ProHistory.delIds);
                ajax.start();
            };
            Feng.confirm("是否刪除选中项目操作?", operation);
         }
};

/**
 * 删除单个项目操作
 */
ProHistory.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/proHistory/delete", function (data) {
            Feng.success("删除成功!");
            ProHistory.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中项目操作?", operation);

};

/**
 * 查询项目操作列表
 */
ProHistory.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProHistory.table.refresh({query: queryData});
};

ProHistory.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProHistory.openProHistoryOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProHistory.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
ProHistory.dealDelIds = function (selected) {
    ProHistory.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            ProHistory.delIds += selected[i].id+",";
        }else {
            ProHistory.delIds += selected[i].id;
        }
    }
};

$(function () {
    var defaultColunms = ProHistory.initColumn();
    var table = new BSTable(ProHistory.id, "/proHistory/list", defaultColunms);
    table.setPaginationType("client");
    ProHistory.table = table.init();
});
