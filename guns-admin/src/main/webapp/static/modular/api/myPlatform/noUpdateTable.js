/**
 * Created by cdyoue on 2017/11/8.
 */
/*生成项目一周未更新表格*/
var noUpdateTable = {
    id: "noUpdateTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};
/*初始化表格*/
noUpdateTable.initColumn = function () {
    return [
        {title: '编号', field: 'id',align: 'center', valign: 'middle',sortable: true,width:'10%'},
        {title: '项目名称', field: 'name',align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle'},
        {title: '责任单位', field: 'liable', align: 'center', valign: 'middle'},
        {title: '项目阶段', field: 'status', align: 'center', valign: 'middle'},
        {title: '项目类型', field: 'isBigPro', align: 'center', valign: 'middle'},
        {title: '更新时间', field: 'updateTime', align: 'center', valign: 'middle',sortable: true}
    ];
};
/**
 * 打开查看常规项目详情
 */
noUpdateTable.openNormalProjectOneDetail = function (id) {
    //id = id.substring(1);
    location.href = Feng.ctxPath + '/normalProject/normalProject_update/' + id;
};
/**
 * 打开查看重大项目详情
 */
noUpdateTable.openBigProjectOneDetail = function (id) {
    //id = id.substring(1);
    location.href = Feng.ctxPath + '/bigProject/bigProject_update/' + id;
};
$(function () {
    var defaultColunms = noUpdateTable.initColumn();
    var table = new BSTableTotalPG(noUpdateTable.id, "/myPlatform/noUpdateList", defaultColunms,5);
    // table.setPaginationType("client");
    table.setPaginationType("server");
    noUpdateTable.table = table.init();
    /*table加载成功后*/
    $("#noUpdateTable").on('load-success.bs.table',function () {
        $("#noUpdateTableTotalNum").text(table.total);
        noticeTotalNum();//计算提醒总数
    });
    $("#noUpdateTable").on("post-body.bs.table",function () {
        var resourcesChangeTabletr=$("#noUpdateTable").find("tbody tr");
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
        changeType("#noUpdateTable",["0","1","2","3","4","5"],["已取消","申报储备","跟踪在谈","拟签约","已签约","履约"],4);
        changeType("#noUpdateTable",["0","1"],["常规投资项目","重大包装项目"],5);
    });
    //点击查看详情
    $('#noUpdateTable').on('click-row.bs.table', function (e, row, element) {
        var id = row.id;
        var type=row.isBigPro;//0常规，1重大
        id = id.substring(1);
        if(type == 0){
            noUpdateTable.openNormalProjectOneDetail(id);
        }
        if(type == 1){
            noUpdateTable.openBigProjectOneDetail(id);
        }
    });
});