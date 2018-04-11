/**
 * 楼宇或标准厂房载体资源管理初始化
 */
var ZateBuilding = {
    id: "ZateBuildingTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
ZateBuilding.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: ZateBuilding.formatOperate}
    ];
};

ZateBuilding.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ZateBuilding.openZateBuildingDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ZateBuilding.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}
/**
 * 检查是否选中
 */
ZateBuilding.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ZateBuilding.seItem = selected[0];
        ZateBuilding.dealDelIds(selected);
        return true;
    }
};

/**
 * 点击添加楼宇或闲置厂房载体资源
 */
ZateBuilding.openAddZateBuilding = function () {
    var index = layer.open({
        type: 2,
        title: '添加楼宇或标准厂房载体资源',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/zateBuilding/zateBuilding_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看楼宇或闲置厂房载体资源详情
 */
ZateBuilding.openZateBuildingDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '楼宇或标准房载体资源详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/zateBuilding/zateBuilding_update/' + ZateBuilding.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看楼宇或闲置厂房载体资源详情,不验证是否选中
 */
ZateBuilding.openZateBuildingOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/zateBuilding/zateBuilding_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除楼宇或闲置厂房载体资源
 */
ZateBuilding.delete = function () {
    if (this.check()) {
         var operation = function(){
             var ajax = new $ax(Feng.ctxPath + "/zateBuilding/delete", function (data) {
                    Feng.success("删除成功!");
                    ZateBuilding.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("ids",ZateBuilding.delIds);
                ajax.start();
            };
            Feng.confirm("是否刪除选中楼宇或标准厂房载体资源?", operation);
         }
};

/**
 * 删除单个楼宇或闲置厂房载体资源
 */
ZateBuilding.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/zateBuilding/delete", function (data) {
            Feng.success("删除成功!");
            ZateBuilding.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中楼宇或标准厂房载体资源?", operation);

};

/**
 * 查询楼宇或闲置厂房载体资源列表
 */
ZateBuilding.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ZateBuilding.table.refresh({query: queryData});
};

ZateBuilding.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ZateBuilding.openZateBuildingOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ZateBuilding.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
ZateBuilding.dealDelIds = function (selected) {
    ZateBuilding.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            ZateBuilding.delIds += selected[i].id+",";
        }else {
            ZateBuilding.delIds += selected[i].id;
        }
    }
};

$(function () {
    var defaultColunms = ZateBuilding.initColumn();
    var table = new BSTable(ZateBuilding.id, "/zateBuilding/list", defaultColunms);
    table.setPaginationType("client");
    ZateBuilding.table = table.init();
});
