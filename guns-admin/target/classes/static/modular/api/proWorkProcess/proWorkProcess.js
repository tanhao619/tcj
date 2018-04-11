/**
 * 工作进度管理初始化
 */
var ProWorkProcess = {
    id: "ProWorkProcessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
ProWorkProcess.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: ProWorkProcess.formatOperate}
    ];
};

ProWorkProcess.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProWorkProcess.openProWorkProcessDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProWorkProcess.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}
/**
 * 检查是否选中
 */
ProWorkProcess.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProWorkProcess.seItem = selected[0];
        ProWorkProcess.dealDelIds(selected);
        return true;
    }
};

/**
 * 点击添加工作进度
 */
ProWorkProcess.openAddProWorkProcess = function () {
    var index = layer.open({
        type: 2,
        title: '添加工作进度',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proWorkProcess/proWorkProcess_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看工作进度详情
 */
ProWorkProcess.openProWorkProcessDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '工作进度详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/proWorkProcess/proWorkProcess_update/' + ProWorkProcess.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看工作进度详情,不验证是否选中
 */
ProWorkProcess.openProWorkProcessOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proWorkProcess/proWorkProcess_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除工作进度
 */
ProWorkProcess.delete = function () {
    if (this.check()) {
         var operation = function(){
             var ajax = new $ax(Feng.ctxPath + "/proWorkProcess/delete", function (data) {
                    Feng.success("删除成功!");
                    ProWorkProcess.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("ids",ProWorkProcess.delIds);
                ajax.start();
            };
            Feng.confirm("是否刪除选中工作进度?", operation);
         }
};

/**
 * 删除单个工作进度
 */
ProWorkProcess.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/proWorkProcess/delete", function (data) {
            Feng.success("删除成功!");
            ProWorkProcess.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中工作进度?", operation);

};

/**
 * 查询工作进度列表
 */
ProWorkProcess.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProWorkProcess.table.refresh({query: queryData});
};

ProWorkProcess.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProWorkProcess.openProWorkProcessOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProWorkProcess.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
ProWorkProcess.dealDelIds = function (selected) {
    ProWorkProcess.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            ProWorkProcess.delIds += selected[i].id+",";
        }else {
            ProWorkProcess.delIds += selected[i].id;
        }
    }
};

$(function () {
    var defaultColunms = ProWorkProcess.initColumn();
    var table = new BSTable(ProWorkProcess.id, "/proWorkProcess/list", defaultColunms);
    table.setPaginationType("client");
    ProWorkProcess.table = table.init();
});
