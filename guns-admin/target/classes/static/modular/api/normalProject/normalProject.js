/**
 * 常规项目管理初始化
 */
var NormalProject = {
    id: "NormalProjectTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    queryData: {}
};

/**
 * 初始化表格的列
 */
NormalProject.initColumn = function () {
    return [
        {title: '序号', formatter: function (value, row, index) {return index+1;}, visible: false, align: 'center', valign: 'middle'},
        {title: '编号', field: 'id', visible: true, align: 'center', valign: 'middle', sortable: true,width:'10%'},
        {title: '投资项目名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '项目创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '投资方名称', field: 'investor', visible: true, align: 'center', valign: 'middle'},
        {title: '项目阶段', field: 'status', visible: true, align: 'center', valign: 'middle'},
        {title: '项目更新时间', field: 'updateTime', visible: true, align: 'center', valign: 'middle', sortable: true,width:'20%'}
    ];
};

/**
 * 打开查看常规项目详情
 */
NormalProject.openNormalProjectOneDetail = function (id) {
    location.href = Feng.ctxPath + '/normalProject/normalProject_update/' + id;
};

/**
 * 根据选择申报项目类型跳转到相应的新增页面
 *
 * @param type 1：常规投资项目；2：重大包装项目
 */
NormalProject.toAddNormalProject = function (type) {
    switch (type) {
        case '1':
            location.href = Feng.ctxPath + '/normalProject/normalProject_add';
            break;
        case '2':
            location.href = Feng.ctxPath + '/bigProject/bigProject_add';
            break;
    }
};
//打开导出弹框
NormalProject.openExcelExport = function(){
    var index = layer.open({
        type: 2,
        title: '导出列表',
        area: ['340px', '265px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/normalProject/excelExport/'
    });
     this.layerIndex = index;
}
/**
 * 清空查询数据
 */
NormalProject.clearData = function () {
    this.queryData = {};
};

/**
 * 收集查询数据
 */
NormalProject.collectData = function () {
    this.queryData['name'] = $("#name").val().trim();
    this.queryData['createTimeB'] = $("#createTimeB").val();
    this.queryData['createTimeE'] = $("#createTimeE").val();
    this.queryData['status'] = $("#status").val();
    this.queryData['updateTimeB'] = $("#updateTimeB").val();
    this.queryData['updateTimeE'] = $("#updateTimeE").val();
    this.queryData['fromArea'] = $("#fromArea").val();
    this.queryData['category'] = $("#category").val();
    this.queryData['useType'] = $("#useType").val();
    this.queryData['useAreaB'] = $("#useAreaB").val();
    this.queryData['useAreaE'] = $("#useAreaE").val();
    this.queryData['investRmbB'] = $("#investRmbB").val();
    this.queryData['investRmbE'] = $("#investRmbE").val();
    this.queryData['contractType'] = $("#contractType").val();
    this.queryData['isVisit'] = $("#isVisit").val();
    this.queryData['investType'] = $("#investType").val();
};

/**
 * 查询常规项目列表
 */
NormalProject.search = function () {
    this.clearData();
    this.collectData();
    this.queryData.offset = 0;
    NormalProject.table.refresh({query: NormalProject.queryData});
};

/**
 * 恢复查询条件默认值
 */
NormalProject.reset = function () {
    $("#name").val(null);
    $("#createTimeB").val(null);
    $("#createTimeE").val(null);
    $("#status").val(null);
    $("#updateTimeB").val(null);
    $("#updateTimeE").val(null);
    $("#fromArea").val(null);
    $("#category").val(null);
    $("#useType").val(null);
    $("#useAreaB").val(null);
    $("#useAreaE").val(null);
    $("#investRmbB").val(null);
    $("#investRmbE").val(null);
    $("#contractType").val(null);
    $("#isVisit").val(null);
    $("#investType").val(null);
    NormalProject.search();
};
//当载体使用类型及面积为全部类型的时侯置灰后面的input输入框
function allDisabled(value){
    if(value == ""){
       $("#useAreaB,#useAreaE").attr("disabled","disabled");
       $("#useAreaB,#useAreaE").css("background","#fff");
    }
    else{
        $("#useAreaB,#useAreaE").removeAttr("disabled","disabled");
    }
}
$(function () {
    inputFocus();
    allDisabled("");
    var defaultColunms = NormalProject.initColumn();
    var table = new BSTable(NormalProject.id, "/normalProject/list", defaultColunms);
    table.setPaginationType("server");
    NormalProject.table = table.init();
    $("#NormalProjectTable").on("post-body.bs.table",function () {
        changeType("#NormalProjectTable",["1","2","3","4","5","0"],["申报储备","跟踪在谈","拟签约","已签约","履约","已取消"],4);
    });
    $("#NormalProjectTable").on("post-body.bs.table",function () {
        var resourcesChangeTabletr=$("#NormalProjectTable").find("tbody tr");
        resourcesChangeTabletr.each(function () {
            var resourcesChangeTabletrtd=$(this).children("td").eq(3);
            var returnValue=resourcesChangeTabletrtd.text();
            var strArry = returnValue.split(',');
            var strArryLen = strArry.length;
            if (strArryLen === 2){
                resourcesChangeTabletrtd.html(strArry[0]+'<br>'+strArry[1]);
            } else if (strArryLen > 2){
                resourcesChangeTabletrtd.html(strArry[0]+'<br>'+strArry[1]+'...');
                resourcesChangeTabletrtd.attr("title",returnValue.replace(new RegExp(",","gm"), "\n"));
            }
        });
    });

    // 单击一条记录计入详情
    $('#NormalProjectTable').on('click-row.bs.table', function (e, row, element) {
        var id = row.id;
        NormalProject.openNormalProjectOneDetail(id);
    });
    //搜索条件显示隐藏
    $(".hideContent").click(function(){
        $(".needShow").hide();
        $(".showContent").show();
        $(this).hide();
    });
    $(".showContent").click(function(){
        $(".needShow").show();
        $(".showContent").hide();
        $(".hideContent").show();
    });

    // 打开申报项目
    $(".declar").click(function(){
        $(".declarProjects .content").toggle();
        $(this).children('.arrow').toggleClass('open')
    });

    // 创建时间
    // timeSingle($("#createTimeE"));
    // timeSingle($("#updateTimeE"));
    $("#createTimeB,#createTimeE").focus(function(){
        timeBetween($("#createTimeB"),$("#createTimeE"));
    });
    // 修改时间
    $("#updateTimeB,#updateTimeE").focus(function(){
        timeBetween($("#updateTimeB"),$("#updateTimeE"));
    });


});
