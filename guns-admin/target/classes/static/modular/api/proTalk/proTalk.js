/**
 * 洽谈信息管理初始化
 */
var ProTalk = {
    id: "ProTalkTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
ProTalk.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: ProTalk.formatOperate}
    ];
};

ProTalk.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProTalk.openProTalkDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProTalk.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}
/**
 * 检查是否选中
 */
ProTalk.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProTalk.seItem = selected[0];
        ProTalk.dealDelIds(selected);
        return true;
    }
};

/**
 * 点击添加洽谈信息
 */
ProTalk.openAddProTalk = function () {
    var index = layer.open({
        type: 2,
        title: '添加洽谈信息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proTalk/proTalk_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看洽谈信息详情
 */
ProTalk.openProTalkDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '洽谈信息详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/proTalk/proTalk_update/' + ProTalk.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看洽谈信息详情,不验证是否选中
 */
ProTalk.openProTalkOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/proTalk/proTalk_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除洽谈信息
 */
ProTalk.delete = function () {
    if (this.check()) {
         var operation = function(){
             var ajax = new $ax(Feng.ctxPath + "/proTalk/delete", function (data) {
                    Feng.success("删除成功!");
                    ProTalk.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("ids",ProTalk.delIds);
                ajax.start();
            };
            Feng.confirm("是否刪除选中洽谈信息?", operation);
         }
};

/**
 * 删除单个洽谈信息
 */
ProTalk.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/proTalk/delete", function (data) {
            Feng.success("删除成功!");
            ProTalk.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中洽谈信息?", operation);

};

/**
 * 查询洽谈信息列表
 */
ProTalk.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProTalk.table.refresh({query: queryData});
};

ProTalk.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ProTalk.openProTalkOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ProTalk.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
ProTalk.dealDelIds = function (selected) {
    ProTalk.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            ProTalk.delIds += selected[i].id+",";
        }else {
            ProTalk.delIds += selected[i].id;
        }
    }
};

$(function () {
    var defaultColunms = ProTalk.initColumn();
    var table = new BSTable(ProTalk.id, "/proTalk/list", defaultColunms);
    table.setPaginationType("client");
    ProTalk.table = table.init();
});
