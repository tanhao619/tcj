/**
 * Created by cdyoue on 2017/11/8.
 */
/**
 * Created by cdyoue on 2017/11/8.
 */
/*待处理项目*/
var toBeAllocatedTable = {
    id: "toBeAllocatedTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};
/*初始化表格*/
toBeAllocatedTable.initColumn = function () {
    return [
        {title: '编号', field: 'id', align: 'center', valign: 'middle',sortable: true},
        {title: '投资项目名称', field: 'name', align: 'center', valign: 'middle'},
        {title: '所属产业', field: 'category', align: 'center', valign: 'middle'},
        {title: '责任单位', field: 'liable', align: 'center', valign: 'middle'},
        {title: '项目类型', field: 'isBigPro', align: 'center', valign: 'middle'},
        {title: '项目创建时间', field: 'createTime',align: 'center', valign: 'middle',sortable: true}
    ];
};
/*搜索*/
function selectValue() {
    $("#toBeHandleSelect").on('change',function () {
        var queryData = {};
        queryData['type'] = $("#toBeHandleSelect").val();
        toBeAllocatedTable.table.refresh({query: queryData});
    });
}
/**
 * 打开查看常规项目详情
 */
toBeAllocatedTable.openNormalProjectOneDetail = function (id,type) {
    if(type == 0){
        location.href = Feng.ctxPath + '/normalProject/normalProject_update/' + id;
    }else if(type == 1){
        location.href = Feng.ctxPath + '/bigProject/bigProject_update/' + id;
    }

};
$(function () {
    var defaultColunms = toBeAllocatedTable.initColumn();
    var table = new BSTableTotal(toBeAllocatedTable.id, "myPlatform/PendingList", defaultColunms);
    table.setPaginationType("server");
    toBeAllocatedTable.table = table.init();
    selectValue();//选择状态
    $("#toBeAllocated").on('load-success.bs.table',function () {
        $("#toBeHandleTotalNum").text(table.total);
    });
    $("#toBeAllocated").on("post-body.bs.table",function () {
        changeType("#toBeAllocated",["1","2","3"],["第一产业","第二产业","第三产业"],2);
        changeType("#toBeAllocated",["0","1"],["常规投资项目","重大包装项目"],4);
    });

    //点击查看详情
    $('#toBeAllocated').on('click-row.bs.table', function (e, row, element) {
        var id = row.id;
        var type = row.isBigPro;
        toBeAllocatedTable.openNormalProjectOneDetail(id,type);
    });

});