/**
 * Created by cdyoue on 2017/11/8.
 */
/*载体资源变更提醒表格*/
var resourcesChangeTable = {
    id: "resourcesChangeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};
/*初始化表格*/
resourcesChangeTable.initColumn = function () {
    return [
        {//field: 'Number',//可不加
            title: '编号',//标题  可不加
            formatter: function (value, row, index) {
                return row.type+row.zateId;
            },
           align: 'center',
            sortable: true,width:'10%'
        },
        {title: '载体资源名称', field: 'name',align: 'center', valign: 'middle'},
        {title: '载体资源类型', field: 'type',align: 'center', valign: 'middle'},
        {title: '地址', field: 'address',align: 'center', valign: 'middle'},
        {title: '填报单位', field: 'unit',align: 'center', valign: 'middle'},
        {title: '联系人', field: 'fillContacter',align: 'center', valign: 'middle'},
        {title: '联系电话', field: 'fillTel',align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime',align: 'center', valign: 'middle'},
        {title: '变更时间', field: 'updateTime',align: 'center', valign: 'middle'}
    ];
};
resourcesChangeTable.openZateOneDetail = function (id,type,historyId) {
    if(type==1){
        location.href=Feng.ctxPath + '/zateLand/zateLand_update/' +id+"/"+historyId;
    }
    if(type==2){
        location.href=Feng.ctxPath + '/zateBuilding/zateBuilding_update/' +id+"/"+historyId;
    }
};

$(function () {
    var defaultColunms = resourcesChangeTable.initColumn();
    var table = new BSTableTotal(resourcesChangeTable.id, "/zate/historys/list", defaultColunms);
    table.setPaginationType("server");
    resourcesChangeTable.table = table.init();
    $("#resourcesChangeTable").on("post-body.bs.table",function () {
        changeType("#resourcesChangeTable",["1","2"],["土地资源类","楼宇或闲置厂房"],2);
    });
    $('#resourcesChangeTable').on('click-row.bs.table', function (e, row, element) {
        var id = row.zateId;
        var type=row.type;
        var historyId=row.historyId;
        resourcesChangeTable.openZateOneDetail(id,type,historyId);
    });
    /*记录条数*/
    $("#resourcesChangeTable").on('load-success.bs.table',function () {
        $("#resTotalNum").text(table.total);
    });

});
