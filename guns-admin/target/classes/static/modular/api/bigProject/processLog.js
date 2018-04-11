/**
 * 项目目前工作进度列表
 */
var ProProcess = {
    id: "ProProcessTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    bigProjectId: parent.document.getElementById("id").value
};

/**
 * 初始化表格的列
 */
ProProcess.initColumn = function () {
    return [
        {title: '序号', formatter: function (value, row, index) {return index+1;}, visible: true, align: 'center', valign: 'middle'},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '目前工作进度', field: 'workProcess', visible: true, align: 'center', valign: 'middle',width:'50%'},
        {title: '', field: 'name', visible: true, align: 'center', valign: 'middle', formatter: ProProcess.formatOperate},
        {title: '操作人员', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '更新时间', field: 'updateTime', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 格式化操作列
 */
ProProcess.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<div class='btn_wrap'><button class='btn opearteLog'   onclick='ProProcess.editOne(this,"+id+")'><img src='/static/img/icon-edit1.png'></button></div>";
    var btn_del = "<div class='delete_wrap'><button class='btn opearteLog'   onclick='ProProcess.delOne(" + id + ")'><img src='/static/img/icon-delete1.png'></button></div>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
};

/**
 * 修改一行，该行可编辑，出现√
 */
ProProcess.editOne = function (this1, row1) {
    var rowId = row1;
    var prevTd = $(this1).parent().parent().prevAll("td").not(":last");
    $(this1).replaceWith("<button class='btn opearteLog' onclick='ProProcess.updOne(this,"+rowId+")'><img src='/static/img/icon-gou.png'></button>");
    $.each(prevTd,function(index) {
        $(this).html('<input type="text" value="' + $(this).html() + '" class="inputColor">');
    });
};

/**
 * 删除一行
 */
ProProcess.delOne = function (id) {
    var operation = function () {
        var ajax1 = new $axRest(Feng.ctxPath + "/proWorkProcess/delete/"+id, "post", function (data) {
            Feng.success("成功！");
            ProProcess.table.refresh();
            setTimeout(function(){
                $(".conventionLog table tr").hover(function(){
                    $(this).find(".opearteLog").show();
                },function(){
                    $(this).find(".opearteLog").hide();
                })
            },100);
        }, function (data) {
            Feng.error("失败!" + data.responseJSON.message + "!");
            return;
        });
        ajax1.start();
    };
    Feng.confirm("是否删除该条工作进度记录？", operation);
};

/**
 * 更新一行
 */
ProProcess.updOne = function (this12, row12) {
    var editData = {};
    var prevTd1 = $(this12).parent().parent().prevAll("td").not(":last");
    var str = [];
    $.each(prevTd1,function(index){
        var single = $(this).children(".inputColor").val();
        str.push(single);
    });
    editData.id = row12;
    editData.proId = ProProcess.bigProjectId;
    editData.workProcess = str[0];
    var ajax1 = new $axRest(Feng.ctxPath + "/proWorkProcess/update", "post", function (data) {
        Feng.success("成功！");
        ProProcess.table.refresh();
        setTimeout(function(){
            $(".conventionLog table tr").hover(function(){
                $(this).find(".opearteLog").show();
            },function(){
                $(this).find(".opearteLog").hide();
            })
        },100);
    }, function (data) {
        Feng.error("失败!" + data.responseJSON.message + "!");
        return;
    });
    ajax1.set(editData);
    ajax1.start();
};

/**
 * 关闭窗口
 */
ProProcess.cancel = function () {
    parent.layer.close(window.parent.NormalProjectInfo.layerIndex);
};

$(function () {
    var id = parent.document.getElementById("id").value;
    var opearUpdateBtn = parent.document.getElementById("opearUpdateBtn").value;
    var defaultColunms = ProProcess.initColumn();
    var table = new BSTable(ProProcess.id, "/proWorkProcess/list?proId="+id, defaultColunms);
    table.setPaginationType("server");
    ProProcess.table = table.init();
    if(opearUpdateBtn == "true"){
        setTimeout(function(){
            $(".conventionLog table tr").hover(function(){
                $(this).find(".opearteLog").show();
            },function(){
                $(this).find(".opearteLog").hide();
            })
        },50);
    }
});
