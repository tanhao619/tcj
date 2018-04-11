/**
 * 载体资源管理管理初始化
 */
var Zate = {
    id: "ZateTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};

/**
 * 初始化表格的列
 */
Zate.initColumn = function () {
    return [
       /* {field: 'selectItem', checkbox: true},*/
      /* {title: '编号', field: 'zid', visible: true, align: 'center', valign: 'middle'},*/
  /*      {
            title: '编号',//标题  可不加
            field: 'zid',
            formatter: function (value, row, index) {
                return row.type + "" + row.zid;
            },
            align: 'center',
            sortable: true
        },*/
        {title: '编号', field: 'ordId', visible: true, align: 'center', valign: 'middle',sortable: true,width:'10%'},
        {title: '载体资源名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '载体资源类型', field: 'type', visible: true, align: 'center', valign: 'middle'},
        {title: '载体资源地点', field: 'address', visible: true, align: 'center', valign: 'middle'},
        {title: '填报单位名称', field: 'unit', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
        {title: '联系人', field: 'fillContacter', visible: true, align: 'center', valign: 'middle'},
        {title: '联系电话', field: 'fillTel', visible: true, align: 'center', valign: 'middle'},
        {title: '填报时间', field: 'fillTime', visible: true, align: 'center', valign: 'middle',width:'15%',sortable: true,
            formatter: function (value, row, index) {
                if (value == null) {
                    return "";
                }
                var offlineTimeStr = row.fillTime.substr(0,10);
                return offlineTimeStr;
            }
        }
    ];

};

/**
 * 查询载体资源管理列表
 */
Zate.search = function () {
    var queryData = {};
    queryData['name'] = $.trim($("#name").val());
    queryData['address'] = $.trim($("#address").val());
    queryData['status'] = $("#status").val();
    queryData['type'] = $("#type").val();
    // queryData['inpstart'] =  $("#inpstart").val();
    // queryData['inpend'] =  $("#inpend").val();
    queryData['offset']=0;
    Zate.table.refresh({query: queryData});
};
/**
 * 点击添加载体资源管理
 */
Zate.openAddZate = function () {
    location.href=Feng.ctxPath + '/zate/zate_add';
};
/**
 * 打开查看载体资源管理详情,不验证是否选中
 */
Zate.openZateOneDetail = function (id,type) {
    if(type==1){
        location.href=Feng.ctxPath + '/zateLand/zateLand_update/' +id + "/"+ -1;
    }
    if(type==2){
        location.href=Feng.ctxPath + '/zateBuilding/zateBuilding_update/' +id + "/" + -1;
    }
};

/**
 * 查询导出的数量,打开弹框
 */
Zate.exportExcelNum = function (type){
    var title;
    if(type==""||type==null){
        return;
    }
    if (type == 1) {
        Zate.table.queryParams.zateType = "1";
        title="导出土地资源类列表"
    }
    if (type == 2) {
        Zate.table.queryParams.zateType = "2";
        title="导出楼宇或闲置厂房类列表"
    }
    var all=scapParms(Zate.table.queryParams);
    $.ajax({
        type: 'GET',
        url: Feng.ctxPath + "/zate/exportNum",
        data: Zate.table.queryParams,
        success: function(data){
                var index = layer.open({
                type: 2,
                title: title,
                area: ['335px', '265px'], //宽高
                fix: false, //不固定
                maxmin: false,
                content: Feng.ctxPath + '/zate/export/zate_excel/' +data+"?"+all
            });
            Zate.layerIndex = index;
        },
        error: function(){
            //请求失败处理函数
        }
    });
};
function scapParms(Obj){
    var str = "";
    for(var key in Obj){
        str  += key + "=" + Obj[key] + "&"
    }
    return str;
}

/**
 * 导出excel，回传回来流
 */
Zate.exportExcel = function (flag,zateType,order,sort,name,address,status,type){
    Zate.table.queryParams.all = flag;
    Zate.table.queryParams.zateType = zateType;
    Zate.table.queryParams.order=order;
    Zate.table.queryParams.sort=sort;
    Zate.table.queryParams.name=name;
    Zate.table.queryParams.address=address;
    Zate.table.queryParams.status=status;
    Zate.table.queryParams.type=type;
    var parmas = scapParms(Zate.table.queryParams);
    var url = "/zate/export?"+parmas;
    $("#excelExp").attr("href",url);
    //关闭弹窗,隐藏下拉框
    // Zate.close();
    $("#excelExp").click(function () {
        //隐藏弹框

    });
    // setTimeout(Zate.close,5000);
};


//关闭弹窗
Zate.close = function(){
    parent.layer.close(window.parent.Zate.layerIndex);
};

/**
 * 重置查询条件
 */
Zate.resetSearch = function () {
    $("#name").val("");
    $("#address").val("");
    $("#status").val("");
    $("#type").val("");
    Zate.search();
};
function addIcons() {
    var iconsSearch="<img src='/static/img/icon_search.png'/>";
     $("#search").append(iconsSearch);

}
$(function () {

    inputFocus();
    var defaultColunms = Zate.initColumn();
    var table = new BSTable(Zate.id, "/zate/list", defaultColunms);
    table.setPaginationType("server");
    Zate.table = table.init();
    $("#ZateTable").on("post-body.bs.table",function () {
        changeType("#ZateTable",["1","2"],["土地资源类","楼宇或闲置厂房"],2);
        changeType("#ZateTable",["1","2","3"],["未使用","已使用","已停用"],5);
    });
    $('#ZateTable').on('click-row.bs.table', function (e, row, element) {
        var id = row.zid;
        var type=row.type;
        Zate.openZateOneDetail(id,type);
    });
    //导出Excle
    $(".declar").click(function(){
        $(".declarProjects .content").toggle();
        $(this).children('.arrow').toggleClass('open')
    });
   /*选中后关闭下拉框*/
        $(".declarProjects .content li").click(function () {
            $(".declarProjects .content").toggle();
            $(".declar .arrow").toggleClass('open');
        })
    /*增加搜索和导出图标*/
    addIcons();

});

