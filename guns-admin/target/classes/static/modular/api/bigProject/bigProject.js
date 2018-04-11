/**
 * 重大项目管理初始化
 */
var BigProject = {
    id: "BigProjectTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    queryData: {} //查询数据
};

/**
 * 初始化表格的列
 */
BigProject.initColumn = function () {
    return [
        {title: '序号', formatter: function (value, row, index) {return index+1;}, visible: false, align: 'center', valign: 'middle'},
        {title: '编号', field: 'id', visible: true, align: 'center', valign: 'middle', sortable: true,width:'10%'},
        {title: '项目名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '项目创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '策划包装定位', field: 'packPosition', visible: true, align: 'center', valign: 'middle'},
        {title: '具体行业分类', field: 'categary', visible: true, align: 'center', valign: 'middle'},
        {title: '建议实施方式', field: 'adviseOpeType', visible: true, align: 'center', valign: 'middle'},
        {title: '项目更新时间', field: 'updateTime', visible: true, align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 清空查询数据
 */
BigProject.clearData = function () {
    this.queryData = {};
};

/**
 * 收集查询数据
 */
BigProject.collectData = function () {
    var data = {};
    data['name'] = $("#bigName").val();
    data['createTimeB'] = $("#bigCreateTimeB").val();
    data['createTimeE'] = $("#bigCreateTimeE").val();
    data['category'] = $("#bigCategory").val();
    data['status'] = $("#bigStatus").val();
    data['updateTimeB'] = $("#bigUpdateTimeB").val();
    data['updateTimeE'] = $("#bigUpdateTimeE").val();
    data['planStartTimeB'] = $("#planStartTimeB").val();
    data['planStartTimeE'] = $("#planStartTimeE").val();
    data['planEndTimeB'] = $("#planEndTimeB").val();
    data['planEndTimeE'] = $("#planEndTimeE").val();
    data['investRmbB'] = $("#bigInvestRmbB").val();
    data['investRmbE'] = $("#bigInvestRmbE").val();
    var packPositionStr = "";
    $("input[type='checkbox'][name='packPosition']:checked").each(function(index, element){
        packPositionStr = packPositionStr + element.value + ",";
    });
    packPositionStr = packPositionStr.substring(0, packPositionStr.length - 1);
    data['packPosition'] = packPositionStr;
    this.queryData = data;
};

/**
 * 查询重大项目列表
 */
BigProject.search = function () {
    this.clearData();
    this.collectData();
    /*console.log(this.queryData);
    return;*/
    BigProject.table.refresh({query: BigProject.queryData});
};

/**
 * 恢复查询条件默认值
 */
BigProject.reset = function () {
    $("#bigName").val(null);
    $("#bigCreateTimeB").val(null);
    $("#bigCreateTimeE").val(null);
    $("#bigCategory").val(null);
    $("#bigStatus").val(null);
    $("#bigUpdateTimeB").val(null);
    $("#bigUpdateTimeE").val(null);
    $("#planStartTimeB").val(null);
    $("#planStartTimeE").val(null);
    $("#planEndTimeB").val(null);
    $("#planEndTimeE").val(null);
    $("#bigInvestRmbB").val(null);
    $("#bigInvestRmbE").val(null);
    $("input[type='checkbox'][name='packPosition']:checked").each(function(index, element){
        element.checked = false;
    });
    BigProject.search();
};

/**
 * 查询重大项目编辑详情页
 *
 * @param id 重大包装项目主键
 */
BigProject.openBigProjectOneDetail = function (id) {
    location.href = Feng.ctxPath + '/bigProject/bigProject_update/' + id;
};

$(function () {

    // 初始化搜索框--加上时间段选择插件 创建时间 修改时间 开工时间 竣工时间
    $("#bigCreateTimeB").focus(function(){
        timeBetween($("#bigCreateTimeB"),$("#bigCreateTimeE"));
    });
    $("#bigUpdateTimeB").focus(function(){
        timeBetween($("#bigUpdateTimeB"),$("#bigUpdateTimeE"));
    });
    $("#planStartTimeB").focus(function(){
        timeBetween($("#planStartTimeB"),$("#planStartTimeE"));
    });
    $("#planEndTimeB").focus(function(){
        timeBetween($("#planEndTimeB"),$("#planEndTimeE"));
    });

    // 加载表格
    var defaultColunms = BigProject.initColumn();
    var table = new BSTable(BigProject.id, "/bigProject/list", defaultColunms);
    table.setPaginationType("server");
    BigProject.table = table.init();

    // 格式化第4列--策划包装定位
    $("#BigProjectTable").on("post-body.bs.table",function () {
        var resourcesChangeTabletr=$("#BigProjectTable").find("tbody tr");
        resourcesChangeTabletr.each(function () {
            var resourcesChangeTabletrtd=$(this).children("td").eq(3);
            var returnValue=resourcesChangeTabletrtd.text();
            returnValue = returnValue.replace(/\d/g, function(rs){
                switch (rs){
                    case '1':return '“五中心一枢纽”功能';
                    case '2':return '“十字方针”战略';
                    case '3':return '产业生态圈培养';
                    case '4':return '其他';
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
    });

    // 格式化第5列--具体行业分类
    $("#BigProjectTable").on("post-body.bs.table",function () {
        var resourcesChangeTabletr=$("#BigProjectTable").find("tbody tr");
        resourcesChangeTabletr.each(function () {
            var resourcesChangeTabletrtd=$(this).children("td").eq(4);
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

    // 格式化第6列--建议实施方式
    $("#BigProjectTable").on("post-body.bs.table",function () {
        var resourcesChangeTabletr=$("#BigProjectTable").find("tbody tr");
        resourcesChangeTabletr.each(function () {
            var resourcesChangeTabletrtd=$(this).children("td").eq(5);
            var returnValue=resourcesChangeTabletrtd.text();
            returnValue = returnValue.replace(/\d/g, function(rs){
                switch (rs){
                    case '1':return '招商引资';
                    case '2':return '基本建设';
                    case '3':return '融资';
                    case '4':return 'PPP';
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
    });

    // 单击一条记录计入详情
    $('#BigProjectTable').on('click-row.bs.table', function (e, row, element) {
        var id = row.id;
        BigProject.openBigProjectOneDetail(id);
    });
});
