/**
 * 项目履约列表
 */
var ProConvention = {
    id: "ProConventionTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    normalProjectId: parent.document.getElementById("id").value
};

/**
 * 初始化表格的列
 */
ProConvention.initColumn = function () {
    return [
        {title: '序号', formatter: function (value, row, index) {return index+1;}, visible: true, align: 'center', valign: 'middle'},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '情况说明', field: 'proConventionInfo', visible: true, align: 'center', valign: 'middle',width:'25%'},
        {title: '下一步工作情况', field: 'nextAdvise', visible: true, align: 'center', valign: 'middle',width:'25%'},
        {title: '', field: 'name', visible: true, align: 'center', valign: 'middle', formatter: ProConvention.formatOperate},
        {title: '操作人员', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '更新时间', field: 'updateTime', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 格式化操作列
 */
ProConvention.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<div class='btn_wrap'><button class='btn opearteLog'   onclick='ProConvention.editOne(this,"+id+")'><img src='/static/img/icon-edit1.png'></button></div>";
    var btn_del = "<div class='delete_wrap'><button class='btn opearteLog'   onclick='ProConvention.delOne(" + id + ")'><img src='/static/img/icon-delete1.png'></button></div>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
};

/**
 * 修改一行，该行可编辑，出现√
 */
ProConvention.editOne = function (this1, row1) {
    var rowId = row1;
    var prevTd = $(this1).parent().parent().prevAll("td").not(":last");
    $(this1).replaceWith("<button class='btn opearteLog' onclick='ProConvention.updOne(this,"+rowId+")'><img src='/static/img/icon-gou.png'></button>");
    $.each(prevTd,function(index) {
        $(this).html('<input type="text" value="' + $(this).html() + '" class="inputColor">');
    });
};

/**
 * 删除一行
 */
ProConvention.delOne = function (id) {
    var operation = function () {
        var ajax1 = new $axRest(Feng.ctxPath + "/proConvention/delete/"+id, "post", function (data) {
            Feng.success("成功！");
            ProConvention.table.refresh();
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
    Feng.confirm("是否删除该条履约记录？", operation);
};

/**
 * 更新一行
 */
ProConvention.updOne = function (this12, row12) {
    var editData = {};
    var prevTd1 = $(this12).parent().parent().prevAll("td").not(":last");
    var str = [];
    $.each(prevTd1,function(index){
        var single = $(this).children(".inputColor").val();
        str.push(single);
    });
    editData.id = row12;
    editData.proId = ProConvention.normalProjectId;
    editData.proConventionInfo = str[1];
    editData.nextAdvise = str[0];
    var ajax1 = new $axRest(Feng.ctxPath + "/proConvention/update", "post", function (data) {
        Feng.success("成功！");
        ProConvention.table.refresh();
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
ProConvention.cancel = function () {
    parent.layer.close(window.parent.NormalProjectInfo.layerIndex);
};

$(function () {
    var id =parent.document.getElementById("id").value;
    var opearUpdateBtn = parent.document.getElementById("opearUpdateBtn").value;
    var defaultColunms = ProConvention.initColumn();
    var table = new BSTable(ProConvention.id, "/proConvention/proConventions?proId="+id, defaultColunms);
    table.setPaginationType("server");
    ProConvention.table = table.init();
    if(opearUpdateBtn == "true") {
        setTimeout(function () {
            $(".conventionLog table tr").hover(function () {
                $(this).find(".opearteLog").show();
            }, function () {
                $(this).find(".opearteLog").hide();
            })
        }, 50);
    }
});
