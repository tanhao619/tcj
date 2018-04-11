/**
 * 载体资源信息变更管理初始化
 */
var ZateHistory = {
    id: "ZateHistoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
ZateHistory.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        /*{title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},*/
        {title: '载体资源类型', field: 'type', visible: true, align: 'center', valign: 'middle'},
        {title: '地址', field: 'address', visible: true, align: 'center', valign: 'middle'},
        {title: '变更内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
        {title: '提交时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: ZateHistory.formatOperate}
    ];
};

ZateHistory.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ZateHistory.openZateHistoryDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ZateHistory.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}
/**
 * 检查是否选中
 */
ZateHistory.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ZateHistory.seItem = selected[0];
        ZateHistory.dealDelIds(selected);
        return true;
    }
};

/**
 * 点击添加载体资源信息变更
 */
ZateHistory.openAddZateHistory = function () {
    var index = layer.open({
        type: 2,
        title: '添加载体资源信息变更',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/zateHistory/zateHistory_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看载体资源信息变更详情
 */
ZateHistory.openZateHistoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '载体资源信息变更详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/zateHistory/zateHistory_update/' + ZateHistory.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看载体资源信息变更详情,不验证是否选中
 */
ZateHistory.openZateHistoryOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/zateHistory/zateHistory_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除载体资源信息变更
 */
ZateHistory.delete = function () {
    if (this.check()) {
         var operation = function(){
             var ajax = new $ax(Feng.ctxPath + "/zateHistory/delete", function (data) {
                    Feng.success("删除成功!");
                    ZateHistory.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("ids",ZateHistory.delIds);
                ajax.start();
            };
            Feng.confirm("是否刪除选中载体资源信息变更?", operation);
         }
};

/**
 * 删除单个载体资源信息变更
 */
ZateHistory.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/zateHistory/delete", function (data) {
            Feng.success("删除成功!");
            ZateHistory.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中载体资源信息变更?", operation);

};

/**
 * 查询载体资源信息变更列表
 */
ZateHistory.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ZateHistory.table.refresh({query: queryData});
};

ZateHistory.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ZateHistory.openZateHistoryOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ZateHistory.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
ZateHistory.dealDelIds = function (selected) {
    ZateHistory.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            ZateHistory.delIds += selected[i].id+",";
        }else {
            ZateHistory.delIds += selected[i].id;
        }
    }
};

$(function () {
    var defaultColunms = ZateHistory.initColumn();
    var table = new BSTable(ZateHistory.id, "/zateHistory/list", defaultColunms);
    table.setPaginationType("client");
    ZateHistory.table = table.init();
});
