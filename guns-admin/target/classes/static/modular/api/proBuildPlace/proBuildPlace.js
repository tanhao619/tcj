/**
 * 拟建设地点管理初始化
 */
var ProBuildPlace = {
    id: "ProBuildPlaceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
ProBuildPlace.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: ProBuildPlace.formatOperate}
    ];
};

ProBuildPlace.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProBuildPlace.openProBuildPlaceDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProBuildPlace.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}
/**
 * 检查是否选中
 */
ProBuildPlace.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProBuildPlace.seItem = selected[0];
        ProBuildPlace.dealDelIds(selected);
        return true;
    }
};

/**
 * 点击添加拟建设地点
 */
ProBuildPlace.openAddProBuildPlace = function () {
    var index = layer.open({
        type: 2,
        title: '添加拟建设地点',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proBuildPlace/proBuildPlace_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看拟建设地点详情
 */
ProBuildPlace.openProBuildPlaceDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '拟建设地点详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/proBuildPlace/proBuildPlace_update/' + ProBuildPlace.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看拟建设地点详情,不验证是否选中
 */
ProBuildPlace.openProBuildPlaceOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proBuildPlace/proBuildPlace_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除拟建设地点
 */
ProBuildPlace.delete = function () {
    if (this.check()) {
         var operation = function(){
             var ajax = new $ax(Feng.ctxPath + "/proBuildPlace/delete", function (data) {
                    Feng.success("删除成功!");
                    ProBuildPlace.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("ids",ProBuildPlace.delIds);
                ajax.start();
            };
            Feng.confirm("是否刪除选中拟建设地点?", operation);
         }
};

/**
 * 删除单个拟建设地点
 */
ProBuildPlace.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/proBuildPlace/delete", function (data) {
            Feng.success("删除成功!");
            ProBuildPlace.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中拟建设地点?", operation);

};

/**
 * 查询拟建设地点列表
 */
ProBuildPlace.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProBuildPlace.table.refresh({query: queryData});
};

ProBuildPlace.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProBuildPlace.openProBuildPlaceOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProBuildPlace.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
ProBuildPlace.dealDelIds = function (selected) {
    ProBuildPlace.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            ProBuildPlace.delIds += selected[i].id+",";
        }else {
            ProBuildPlace.delIds += selected[i].id;
        }
    }
};

$(function () {
    var defaultColunms = ProBuildPlace.initColumn();
    var table = new BSTable(ProBuildPlace.id, "/proBuildPlace/list", defaultColunms);
    table.setPaginationType("client");
    ProBuildPlace.table = table.init();
});
