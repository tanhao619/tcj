/**
 * 土地类载体资源管理初始化
 */
var ZateLand = {
    id: "ZateLandTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
ZateLand.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: ZateLand.formatOperate}
    ];
};

ZateLand.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ZateLand.openZateLandDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ZateLand.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}
/**
 * 检查是否选中
 */
ZateLand.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ZateLand.seItem = selected[0];
        ZateLand.dealDelIds(selected);
        return true;
    }
};

/**
 * 点击添加土地类载体资源
 */
ZateLand.openAddZateLand = function () {
    var index = layer.open({
        type: 2,
        title: '添加土地类载体资源',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/zateLand/zateLand_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看土地类载体资源详情
 */
ZateLand.openZateLandDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '土地类载体资源详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/zateLand/zateLand_update/' + ZateLand.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看土地类载体资源详情,不验证是否选中
 */
ZateLand.openZateLandOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/zateLand/zateLand_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除土地类载体资源
 */
ZateLand.delete = function () {
    if (this.check()) {
         var operation = function(){
             var ajax = new $ax(Feng.ctxPath + "/zateLand/delete", function (data) {
                    Feng.success("删除成功!");
                    ZateLand.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("ids",ZateLand.delIds);
                ajax.start();
            };
            Feng.confirm("是否刪除选中土地类载体资源?", operation);
         }
};

/**
 * 删除单个土地类载体资源
 */
ZateLand.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/zateLand/delete", function (data) {
            Feng.success("删除成功!");
            ZateLand.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中土地类载体资源?", operation);

};

/**
 * 查询土地类载体资源列表
 */
ZateLand.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ZateLand.table.refresh({query: queryData});
};

ZateLand.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ZateLand.openZateLandOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ZateLand.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
ZateLand.dealDelIds = function (selected) {
    ZateLand.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            ZateLand.delIds += selected[i].id+",";
        }else {
            ZateLand.delIds += selected[i].id;
        }
    }
};

$(function () {
    var defaultColunms = ZateLand.initColumn();
    var table = new BSTable(ZateLand.id, "/zateLand/list", defaultColunms);
    table.setPaginationType("client");
    ZateLand.table = table.init();
});
