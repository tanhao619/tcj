/**
 * OA管理管理初始化
 */
var banjieDetail = {
    id: "ShenpiStepTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    flowsList:{},
    flowslistLen:0,
    flowsDTO:null,
    qjTime:null,
    spStep:null,
    lineStep:null,
    lineType:null,
    fenPeiPerson:null,

};

/**
 * 初始化表格的列
 */













$(function () {
    //显示我未审批的数量
    var BanJieDetailworkflowId = $("#BanJieDetailworkflowId").val();
    $.ajax({
        type: 'GET',
        url: Feng.ctxPath + "/workflowStep/banjieDetail",
        data: {'workflowId':BanJieDetailworkflowId},
        async: false,//同步加载
        success: function (data) {
            var str = "";
            for(var i = 0; i < data.length; i++){
              str += "<li class='layui-timeline-item'><i class='layui-icon layui-timeline-axis'>&#xe60e;</i><div class='layui-timeline-content layui-text timeText'><p><span>办结科员：</span><span>"+data[i].bjUser+"</span></p><p><span>办结科室：</span><span>"+data[i].bjDept+"</span></p>" +
                  "<p><span>是否办结：</span><span>"+data[i].bjResult+"</span></p><p><span>办结时间：</span><span>"+data[i].bjTime+"</span></p></div></li>";
          }
            $(".layui-timeline").append(str);

        },
        error: function () {
            //请求失败处理函数
        }
    });





});
