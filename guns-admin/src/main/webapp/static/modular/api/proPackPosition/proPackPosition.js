/**
 * 策划包装定位管理初始化
 */
var ProPackPosition = {
    id: "ProPackPositionTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
ProPackPosition.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: ProPackPosition.formatOperate}
    ];
};

ProPackPosition.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProPackPosition.openProPackPositionDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProPackPosition.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}
/**
 * 检查是否选中
 */
ProPackPosition.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProPackPosition.seItem = selected[0];
        ProPackPosition.dealDelIds(selected);
        return true;
    }
};

/**
 * 点击添加策划包装定位
 */
ProPackPosition.openAddProPackPosition = function () {
    var index = layer.open({
        type: 2,
        title: '添加策划包装定位',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proPackPosition/proPackPosition_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看策划包装定位详情
 */
ProPackPosition.openProPackPositionDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '策划包装定位详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/proPackPosition/proPackPosition_update/' + ProPackPosition.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看策划包装定位详情,不验证是否选中
 */
ProPackPosition.openProPackPositionOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proPackPosition/proPackPosition_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除策划包装定位
 */
ProPackPosition.delete = function () {
    if (this.check()) {
         var operation = function(){
             var ajax = new $ax(Feng.ctxPath + "/proPackPosition/delete", function (data) {
                    Feng.success("删除成功!");
                    ProPackPosition.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("ids",ProPackPosition.delIds);
                ajax.start();
            };
            Feng.confirm("是否刪除选中策划包装定位?", operation);
         }
};

/**
 * 删除单个策划包装定位
 */
ProPackPosition.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/proPackPosition/delete", function (data) {
            Feng.success("删除成功!");
            ProPackPosition.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中策划包装定位?", operation);

};

/**
 * 查询策划包装定位列表
 */
ProPackPosition.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProPackPosition.table.refresh({query: queryData});
};

ProPackPosition.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProPackPosition.openProPackPositionOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProPackPosition.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
ProPackPosition.dealDelIds = function (selected) {
    ProPackPosition.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            ProPackPosition.delIds += selected[i].id+",";
        }else {
            ProPackPosition.delIds += selected[i].id;
        }
    }
};

$(function () {
    var defaultColunms = ProPackPosition.initColumn();
    var table = new BSTable(ProPackPosition.id, "/proPackPosition/list", defaultColunms);
    table.setPaginationType("client");
    ProPackPosition.table = table.init();
});
