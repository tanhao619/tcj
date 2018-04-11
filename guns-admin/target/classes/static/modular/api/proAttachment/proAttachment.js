/**
 * 附件信息管理初始化
 */
var ProAttachment = {
    id: "ProAttachmentTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
ProAttachment.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: ProAttachment.formatOperate}
    ];
};

ProAttachment.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProAttachment.openProAttachmentDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProAttachment.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}
/**
 * 检查是否选中
 */
ProAttachment.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProAttachment.seItem = selected[0];
        ProAttachment.dealDelIds(selected);
        return true;
    }
};

/**
 * 点击添加附件信息
 */
ProAttachment.openAddProAttachment = function () {
    var index = layer.open({
        type: 2,
        title: '添加附件信息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proAttachment/proAttachment_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看附件信息详情
 */
ProAttachment.openProAttachmentDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '附件信息详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/proAttachment/proAttachment_update/' + ProAttachment.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看附件信息详情,不验证是否选中
 */
ProAttachment.openProAttachmentOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proAttachment/proAttachment_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除附件信息
 */
ProAttachment.delete = function () {
    if (this.check()) {
         var operation = function(){
             var ajax = new $ax(Feng.ctxPath + "/proAttachment/delete", function (data) {
                    Feng.success("删除成功!");
                    ProAttachment.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("ids",ProAttachment.delIds);
                ajax.start();
            };
            Feng.confirm("是否刪除选中附件信息?", operation);
         }
};

/**
 * 删除单个附件信息
 */
ProAttachment.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/proAttachment/delete", function (data) {
            Feng.success("删除成功!");
            ProAttachment.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中附件信息?", operation);

};

/**
 * 查询附件信息列表
 */
ProAttachment.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProAttachment.table.refresh({query: queryData});
};

ProAttachment.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProAttachment.openProAttachmentOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProAttachment.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
ProAttachment.dealDelIds = function (selected) {
    ProAttachment.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            ProAttachment.delIds += selected[i].id+",";
        }else {
            ProAttachment.delIds += selected[i].id;
        }
    }
};

$(function () {
    var defaultColunms = ProAttachment.initColumn();
    var table = new BSTable(ProAttachment.id, "/proAttachment/list", defaultColunms);
    table.setPaginationType("client");
    ProAttachment.table = table.init();
});
