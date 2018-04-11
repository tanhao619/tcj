/**
 * OA管理管理初始化
 */
var ShenpiStep = {
    id: "ShenpiStepTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ShenpiStep.initColumn = function () {
    return [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '审批编号', field: 'ordnum', visible: true, align: 'center', valign: 'middle'},
        {title: '审批类型', field: 'type', visible: true, align: 'center', valign: 'middle'},
        {title: '发起人', field: 'apprUserName', visible: true, align: 'center', valign: 'middle'},
        {title: '发起人角色', field: 'apprRoleName', visible: true, align: 'center', valign: 'middle'},
        {title: '审批附件', field: 'attachments', visible: true, align: 'center', valign: 'middle',
            formatter:function (value, row, index) {
                var allFuJian="";

               if(value != null && value != "" && value.length>0){
                    for(var i=0;i<value.length;i++){
                        if(i == value.length-1){
                            var fujian = "<a href='"+Feng.fileSer+""+value[i].url+"' target='_blank'>"+value[i].name+"</a>";
                        }else {
                            var fujian = "<a href='"+Feng.fileSer+""+value[i].url+"' target='_blank'>"+value[i].name+"</a>、";
                        }
                        allFuJian +=fujian;
                    }
                    return allFuJian;
               }
            }},
        {title: '发起时间', field: 'faqiTime', visible: true, align: 'center', valign: 'middle'},
        {title: '是否审批', field: 'isAppr', visible: true, align: 'center', valign: 'middle'},
        {title: '审批时间', field: 'apprTime', visible: true, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: ShenpiStep.formatOperate}
    ];
};





/**
 * 打开查看OA管理详情,不验证是否选中
 */
ShenpiStep.openWorkflowStepOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/workflowStep/workflowStep_update/' + id
    });
    this.layerIndex = index;
};



/**
 * 查询OA管理列表
 */
ShenpiStep.search = function () {
    var queryData = {};
     queryData['shenpiType'] = $("#shenpiType").val();
     queryData['shenpiFaqiUser'] = $("#shenpiFaqiUser").val();
     queryData['isApprShenpi'] = $("#isApprShenpi").val();
     queryData['shenpiFaqiTimeB'] = $("#shenpiFaqiTimeB").val();
     queryData['shenpiFaqiTimeE'] = $("#shenpiFaqiTimeE").val();
     queryData['shenpiApprTimeB'] = $("#shenpiApprTimeB").val();
     queryData['shenpiApprTimeE'] = $("#shenpiApprTimeE").val();
    ShenpiStep.table.refresh({query: queryData});
};
/**
 * 重置查询条件
 */
ShenpiStep.resetSearch = function () {
    $("#shenpiType").val("");
    $("#shenpiFaqiUser").val("");
    $("#isApprShenpi").val("");
    $("#shenpiFaqiTimeB").val("");
    $("#shenpiFaqiTimeE").val("");
    $("#shenpiApprTimeB").val("");
    $("#shenpiApprTimeE").val("");
    ShenpiStep.search();
};

ShenpiStep.formatOperate = function (val, row, index) {
        var id = row.id;
        var btn = "<button class='btn btn-primary'   onclick=''>审批记录</button>";

        <!-- isAppr 是否审批:0 未审批，1已审批-->
        if(row.apprAdvice == '0'){//是否审批
            var btn_thin =  "<button class='btn btn-primary'   onclick=''>审批</button>";
        }

        if(row.type == '8'){//收文审批
            var btn_thin =  "<button class='btn btn-primary'   onclick=''>办结详情</button>";
        }

        if(btn_thin != undefined){
            return  btn+"|"+ btn_thin + "&nbsp;&nbsp;&nbsp;";
        }
        return btn + "&nbsp;&nbsp;&nbsp;";
};



$(function () {
    //显示我未审批的数量
    $.ajax({
        type: 'GET',
        url: Feng.ctxPath + "/workflowStep/shenpi/unApprNum",
        success: function(data){
            $("#noShenPiNum").html(data);
        },
        error: function(){
            //请求失败处理函数
        }
    });
    var defaultColunms = ShenpiStep.initColumn();
    var table = new BSTableCC(ShenpiStep.id, "/workflowStep/shenpi/list", defaultColunms);
    table.setPaginationType("server");
    ShenpiStep.table = table.init();
    $("#ShenpiStepTable").on("expand-row.bs.table",function(index,row,$detail,event){
        var divWrap = "<div style='width:300px;height:100px;border:1px solid red' id='$detail.workflowId'>"+$detail.workflowId+"" +
            "<input type='text'><button class='agree btn btn-primary' onclick='openAgree("+row['id']+")' >同意</button></div>";
        $(event).parent().children("td").html(divWrap);

    });


    $("#ShenpiStepTable").on("post-body.bs.table",function () {
        changeType("#ShenpiStepTable",["1","2","3","4","5","6","7","8"],["请假审批","销假审批","出差审批","接待审批","公务用车审批","驻点人员请假审批","发文审批","收文审批"],1);
        changeType("#ShenpiStepTable",["0","1"],["未审批","已审批"],6);
    });
});
