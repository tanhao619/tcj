/**
 * 项目跟踪管理初始化
 */
var FollowProject = {
    id: "FollowProjectTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
FollowProject.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: FollowProject.formatOperate}
    ];
};

FollowProject.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='FollowProject.openFollowProjectDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='FollowProject.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}
/**
 * 检查是否选中
 */
FollowProject.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        FollowProject.seItem = selected[0];
        FollowProject.dealDelIds(selected);
        return true;
    }
};

/**
 * 点击添加项目跟踪
 */
FollowProject.openAddFollowProject = function () {
    var index = layer.open({
        type: 2,
        title: '添加项目跟踪',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/followProject/followProject_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看项目跟踪详情
 */
FollowProject.openFollowProjectDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '项目跟踪详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/followProject/followProject_update/' + FollowProject.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看项目跟踪详情,不验证是否选中
 */
FollowProject.openFollowProjectOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/followProject/followProject_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除项目跟踪
 */
FollowProject.delete = function () {
    if (this.check()) {
         var operation = function(){
             var ajax = new $ax(Feng.ctxPath + "/followProject/delete", function (data) {
                    Feng.success("删除成功!");
                    FollowProject.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("ids",FollowProject.delIds);
                ajax.start();
            };
            Feng.confirm("是否刪除选中项目跟踪?", operation);
         }
};

/**
 * 删除单个项目跟踪
 */
FollowProject.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/followProject/delete", function (data) {
            Feng.success("删除成功!");
            FollowProject.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中项目跟踪?", operation);

};

/**
 * 查询项目跟踪列表
 */
FollowProject.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    FollowProject.table.refresh({query: queryData});
};

FollowProject.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='FollowProject.openFollowProjectOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='FollowProject.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
FollowProject.dealDelIds = function (selected) {
    FollowProject.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            FollowProject.delIds += selected[i].id+",";
        }else {
            FollowProject.delIds += selected[i].id;
        }
    }
};

$(function () {
    var defaultColunms = FollowProject.initColumn();
    var table = new BSTable(FollowProject.id, "/followProject/list", defaultColunms);
    table.setPaginationType("client");
    FollowProject.table = table.init();
});
