/**
 * 履约信息管理初始化
 */
var ProConvention = {
    id: "ProConventionTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
ProConvention.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: ProConvention.formatOperate}
    ];
};

ProConvention.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProConvention.openProConventionDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProConvention.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}
/**
 * 检查是否选中
 */
ProConvention.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProConvention.seItem = selected[0];
        ProConvention.dealDelIds(selected);
        return true;
    }
};

/**
 * 点击添加履约信息
 */
ProConvention.openAddProConvention = function () {
    var index = layer.open({
        type: 2,
        title: '添加履约信息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proConvention/proConvention_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看履约信息详情
 */
ProConvention.openProConventionDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '履约信息详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/proConvention/proConvention_update/' + ProConvention.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看履约信息详情,不验证是否选中
 */
ProConvention.openProConventionOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proConvention/proConvention_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除履约信息
 */
ProConvention.delete = function () {
    if (this.check()) {
         var operation = function(){
             var ajax = new $ax(Feng.ctxPath + "/proConvention/delete", function (data) {
                    Feng.success("删除成功!");
                    ProConvention.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("ids",ProConvention.delIds);
                ajax.start();
            };
            Feng.confirm("是否刪除选中履约信息?", operation);
         }
};

/**
 * 删除单个履约信息
 */
ProConvention.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/proConvention/delete", function (data) {
            Feng.success("删除成功!");
            ProConvention.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中履约信息?", operation);

};

/**
 * 查询履约信息列表
 */
ProConvention.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProConvention.table.refresh({query: queryData});
};

ProConvention.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProConvention.openProConventionOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProConvention.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
ProConvention.dealDelIds = function (selected) {
    ProConvention.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            ProConvention.delIds += selected[i].id+",";
        }else {
            ProConvention.delIds += selected[i].id;
        }
    }
};

$(function () {
    var defaultColunms = ProConvention.initColumn();
    var table = new BSTable(ProConvention.id, "/proConvention/list", defaultColunms);
    table.setPaginationType("client");
    ProConvention.table = table.init();
});
