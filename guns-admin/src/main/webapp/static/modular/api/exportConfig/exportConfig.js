/**
 * 导出配置管理初始化
 */
var ExportConfig = {
    id: "ExportConfigTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
ExportConfig.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: ExportConfig.formatOperate}
    ];
};

ExportConfig.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ExportConfig.openExportConfigDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ExportConfig.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}
/**
 * 检查是否选中
 */
ExportConfig.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ExportConfig.seItem = selected[0];
        ExportConfig.dealDelIds(selected);
        return true;
    }
};
//时间触发
ExportConfig.changeDate1 = function(val){
    if(val == 4){
        $(".selfDefine1").hide();
        $(".filter1").show();
    }
}
ExportConfig.changeDate2 = function(val){
    if(val == 4){
        $(".selfDefine2").hide();
        $(".filter2").show();
    }
}
/**
 * 点击添加导出配置
 */
ExportConfig.openAddExportConfig = function () {
    var index = layer.open({
        type: 2,
        title: '添加导出配置',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/normalExportConfig/exportConfig_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看导出配置详情
 */
ExportConfig.openExportConfigDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '导出配置详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/normalExportConfig/exportConfig_update/' + ExportConfig.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 打开查看导出配置详情,不验证是否选中
 */
ExportConfig.openExportConfigOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/normalExportConfig/exportConfig_update/' + id
    });
    this.layerIndex = index;
};
/**
 * 批量删除导出配置
 */
ExportConfig.delete = function () {
    if (this.check()) {
         var operation = function(){
             var ajax = new $ax(Feng.ctxPath + "/normalExportConfig/delete", function (data) {
                    Feng.success("删除成功!");
                    ExportConfig.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("ids",ExportConfig.delIds);
                ajax.start();
            };
            Feng.confirm("是否刪除选中导出配置?", operation);
         }
};

/**
 * 删除单个导出配置
 */
ExportConfig.deleteOne = function (id) {

    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/normalExportConfig/delete", function (data) {
            Feng.success("删除成功!");
            ExportConfig.table.refresh();
        }, function (data) {
            Feng.error(data.message + "!");
        });
        ajax.set("ids",id);
        ajax.start();
    };
    Feng.confirm("是否刪除选中导出配置?", operation);

};

/**
 * 查询导出配置列表
 */
ExportConfig.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ExportConfig.table.refresh({query: queryData});
};

ExportConfig.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='ExportConfig.openExportConfigOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='ExportConfig.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
}

// 封装批量删除的ids
ExportConfig.dealDelIds = function (selected) {
    ExportConfig.delIds = "";
    for(var i=0;i<selected.length;i++){
        if (selected[i] != null && i != selected.length -1){
            ExportConfig.delIds += selected[i].id+",";
        }else {
            ExportConfig.delIds += selected[i].id;
        }
    }
};
//确认导出打开弹框
ExportConfig.sureExport = function () {
    var name = [];
    $("#moneyy").attr("name",$("#sel option:selected").attr("name"));
    $('input[type="checkbox"]:checked').each(function(){
            name.push($(this).prop("name"));
    });
    var conStart = $("#exportStart").val();
    var conEnd = $("#exportEnd").val();
    var talkStartTime = $("#inpstart1").val();
    var talkEndTime = $("#inpend1").val();
    var talkTime = $("#talkTime option:selected").val();
    var conTime = $("#conTime option:selected").val();
    var url = "/normalProject/exportExcel?name="+name+"&conStart="+conStart+"&conEnd="+conEnd+"&talkStartTime="+talkStartTime+
        "&talkEndTime="+talkEndTime+"&talkTime="+talkTime+"&conTime="+conTime;
    if (name.length < 1) {
        Feng.alert ("请勾选需要导出的内容!");
    }else {
        $("#excelExp").attr("href",url);
    }
    // Feng.confirm("确认导出勾选?");
};
$(function () {
     $("input[type='checkbox']").prop("checked","checked");
    var defaultColunms = ExportConfig.initColumn();
    var table = new BSTable(ExportConfig.id, "/normalExportConfig/list", defaultColunms);
    table.setPaginationType("client");
    ExportConfig.table = table.init();
    $(".selcted").click(function(){
        $("input[name='chk_list']").prop("checked",$(this).prop("checked"));
    });
    $(".chkAll").click(function(){
        $(this).parent().parent().siblings().find("input").prop("checked",$(this).prop("checked"));
    })
    //所有复选框全部选中
    $(".allCheckbox").click(function(){
        $(this).parent().parent().siblings().find("input").prop("checked",$(this).prop("checked"));
    })
});
// laydate.render({
//     elem: '#test1' //指定元素
// });
//时间段
$("#inpstart1").focus(function(){
    timeBetween($("#inpstart1"),$("#inpend1"));
});
$("#exportStart").focus(function(){
    timeBetween($("#exportStart"),$("#exportEnd"));
});
