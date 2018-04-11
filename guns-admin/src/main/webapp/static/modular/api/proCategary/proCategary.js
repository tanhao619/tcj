/**
 * 行业分类管理初始化
 */
var ProCategary = {
    id: "ProCategaryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
ProCategary.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: ProCategary.formatOperate}
    ];
};

ProCategary.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProCategary.openProCategaryDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProCategary.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}
/**
 * 检查是否选中
 */
ProCategary.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProCategary.seItem = selected[0];
        ProCategary.dealDelIds(selected);
        return true;
    }
};

/**
 * 点击添加行业分类
 */
ProCategary.openAddProCategary = function () {
    var index = layer.open({
        type: 2,
        title: '添加行业分类',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proCategary/proCategary_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看行业分类详情
 */
ProCategary.openProCategaryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '行业分类详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/proCategary/proCategary_update/' + ProCategary.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看行业分类详情,不验证是否选中
 */
ProCategary.openProCategaryOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proCategary/proCategary_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除行业分类
 */
ProCategary.delete = function () {
    if (this.check()) {
         var operation = function(){
             var ajax = new $ax(Feng.ctxPath + "/proCategary/delete", function (data) {
                    Feng.success("删除成功!");
                    ProCategary.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("ids",ProCategary.delIds);
                ajax.start();
            };
            Feng.confirm("是否刪除选中行业分类?", operation);
         }
};

/**
 * 删除单个行业分类
 */
ProCategary.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/proCategary/delete", function (data) {
            Feng.success("删除成功!");
            ProCategary.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中行业分类?", operation);

};

/**
 * 查询行业分类列表
 */
ProCategary.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProCategary.table.refresh({query: queryData});
};

ProCategary.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProCategary.openProCategaryOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProCategary.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
ProCategary.dealDelIds = function (selected) {
    ProCategary.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            ProCategary.delIds += selected[i].id+",";
        }else {
            ProCategary.delIds += selected[i].id;
        }
    }
};

$(function () {
    var defaultColunms = ProCategary.initColumn();
    var table = new BSTable(ProCategary.id, "/proCategary/list", defaultColunms);
    table.setPaginationType("client");
    ProCategary.table = table.init();
});
