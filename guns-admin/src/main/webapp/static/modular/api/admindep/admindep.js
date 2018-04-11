/**
 * 部门信息管理初始化
 */
var Admindep = {
    id: "AdmindepTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    // 批量删除
    delIds:[]
};

/**
 * 初始化表格的列
 */
Admindep.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '名字', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '描述', field: 'descript', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createdTime', visible: true, align: 'center', valign: 'middle'},
        {title: '修改时间', field: 'updateTime', visible: true, align: 'center', valign: 'middle'},
        {title: '操作', field: '11', visible: true,align: 'center', valign: 'middle', formatter: Admindep.formatOperate}

    ];
};

/**
 * 检查是否选中
 */
Admindep.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Admindep.seItem = selected[0];
        Admindep.dealDelIds(selected);
        return true;
    }
};

/**
 * 点击添加部门信息
 */
Admindep.openAddAdmindep = function () {
    var index = layer.open({
        type: 2,
        title: '添加部门信息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/admindep/admindep_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看部门信息详情
 */
Admindep.openAdmindepDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '部门信息详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/admindep/admindep_update/' + Admindep.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看部门信息详情,不验证是否选中
 */
Admindep.openAdmindepOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/admindep/admindep_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除部门信息
 */
Admindep.delete = function () {
    if (this.check()) {
        var operation = function(){
                var ajax = new $ax(Feng.ctxPath + "/admindep/delete", function (data) {
                    Feng.success("删除成功!");
                    Admindep.table.refresh();
                }, function (data) {
                    Feng.error(data.message + "!");
                });
                ajax.set("ids",Admindep.delIds);
                ajax.start();
            };
        Feng.confirm("是否刪除选中部门?", operation);
    }
};
/**
 * 删除单个部门
 */
Admindep.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/admindep/delete", function (data) {
            Feng.success("删除成功!");
            Admindep.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中部门?", operation);

};
/**
 * 重置查询条件
 */
Admindep.resetSearch = function () {
    $("#name").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    $("#admindepId").val("");
    $("#isDeleted").val("");
    Admindep.search();
}
/**
 * 查询部门信息列表
 */
Admindep.search = function () {
    var queryData = {};

    queryData['searchLikes'] = document.getElementById("name").id +":"+ $("#name").val() +","+
    document.getElementById("admindepId").id +":"+ $("#admindepId").val();

    queryData['descript'] = $("#descript").val();

    //queryData['beginTime'] = $("#beginTime").val();
    //queryData['endTime'] = $("#endTime").val();
    //queryData['searchTimes'] = "created_time,"+$("#beginTime").val()+","+$("#endTime").val();

    queryData['searchTimes'] = "createdTime,"+$("#beginTime").val()+","+$("#endTime").val()+
    "|update_time,"+$("#beginTime").val()+","+$("#endTime").val();

    queryData['isDeleted'] = $("#isDeleted").val();
    Admindep.table.refresh({query: queryData});
};


Admindep.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='Admindep.openAdmindepOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='Admindep.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
Admindep.dealDelIds = function (selected) {
    Admindep.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            Admindep.delIds += selected[i].id+",";
        }else {
            Admindep.delIds += selected[i].id;
        }
    }
};

$(function () {
    var defaultColunms = Admindep.initColumn();
    var table = new BSTable(Admindep.id, "/admindep/list", defaultColunms);
    //table.setPaginationType("client");//前台分页
    table.setPaginationType("server");// 后台分页
    console.log(Feng.fileSer);
    Admindep.table = table.init();
});




