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
        {title: '编号', field: 'id', align: 'center', valign: 'middle',sortable: true,width:'10%'},
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
    id = id.substring(1);
    if(type == 1){
        location.href = Feng.ctxPath + '/normalProject/normalProject_update/' + id;
    }else if(type == 2){
        location.href = Feng.ctxPath + '/bigProject/bigProject_update/' + id;
    }

};
$(function () {
    var defaultColunms = toBeAllocatedTable.initColumn();
    var table = new BSTableTotalPG(toBeAllocatedTable.id, "myPlatform/PendingList", defaultColunms,5);
    table.setPaginationType("server");
    toBeAllocatedTable.table = table.init();
    selectValue();//选择状态
    $("#toBeAllocated").on('load-success.bs.table',function () {
        $("#toBeHandleTotalNum").text(table.total);
    });
    // 格式化列
    $("#toBeAllocated").on("post-body.bs.table",function () {
        var resourcesChangeTabletr=$("#toBeAllocated").find("tbody tr");
        // 归属产业
        resourcesChangeTabletr.each(function () {
            var resourcesChangeTabletrtd=$(this).children("td").eq(2);
            var returnValue=resourcesChangeTabletrtd.text();
            returnValue = returnValue.replace(/\d/g, function(rs){
                switch (rs){
                    case '1':return '第一产业';
                    case '2':return '第二产业';
                    case '3':return '第三产业';
                }
            });
            var strArry = returnValue.split(',');
            var strArryLen = strArry.length;
            if (strArryLen === 1){
                resourcesChangeTabletrtd.html(strArry);
            } else if (strArryLen === 2){
                resourcesChangeTabletrtd.html(strArry[0]+'<br>'+strArry[1]);
            } else if (strArryLen > 2){
                resourcesChangeTabletrtd.html(strArry[0]+'<br>'+strArry[1]+'...');
                resourcesChangeTabletrtd.attr("title",returnValue.replace(new RegExp(",","gm"), "\n"));
            }
        });
        // 责任单位
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
        // 项目类型
        changeType("#toBeAllocated",["1","2"],["常规投资项目","重大包装项目"],4);
    });
    $("#toBeAllocated").on("post-body.bs.table",function () {

    });
    //点击查看详情
    $('#toBeAllocated').on('click-row.bs.table', function (e, row, element) {
        var id = row.id;
        var type = row.isBigPro;
        toBeAllocatedTable.openNormalProjectOneDetail(id,type);
    });

});