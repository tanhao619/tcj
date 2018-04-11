/**
 * 责任单位管理初始化
 */
var UnitLiable = {
    id: "UnitLiableTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
UnitLiable.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: UnitLiable.formatOperate}
    ];
};

UnitLiable.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='UnitLiable.openUnitLiableDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='UnitLiable.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}
/**
 * 检查是否选中
 */
UnitLiable.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UnitLiable.seItem = selected[0];
        UnitLiable.dealDelIds(selected);
        return true;
    }
};

/**
 * 点击添加责任单位
 */
UnitLiable.openAddUnitLiable = function () {
    var index = layer.open({
        type: 2,
        title: '添加责任单位',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/unitLiable/unitLiable_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看责任单位详情
 */
UnitLiable.openUnitLiableDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '责任单位详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/unitLiable/unitLiable_update/' + UnitLiable.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看责任单位详情,不验证是否选中
 */
UnitLiable.openUnitLiableOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/unitLiable/unitLiable_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除责任单位
 */
UnitLiable.delete = function () {
    if (this.check()) {
         var operation = function(){
             var ajax = new $ax(Feng.ctxPath + "/unitLiable/delete", function (data) {
                    Feng.success("删除成功!");
                    UnitLiable.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("ids",UnitLiable.delIds);
                ajax.start();
            };
            Feng.confirm("是否刪除选中责任单位?", operation);
         }
};

/**
 * 删除单个责任单位
 */
UnitLiable.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/unitLiable/delete", function (data) {
            Feng.success("删除成功!");
            UnitLiable.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中责任单位?", operation);

};

/**
 * 查询责任单位列表
 */
UnitLiable.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    UnitLiable.table.refresh({query: queryData});
};

UnitLiable.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='UnitLiable.openUnitLiableOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='UnitLiable.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
UnitLiable.dealDelIds = function (selected) {
    UnitLiable.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            UnitLiable.delIds += selected[i].id+",";
        }else {
            UnitLiable.delIds += selected[i].id;
        }
    }
};

$(function () {
    var defaultColunms = UnitLiable.initColumn();
    var table = new BSTable(UnitLiable.id, "/unitLiable/list", defaultColunms);
    table.setPaginationType("client");
    UnitLiable.table = table.init();
});
