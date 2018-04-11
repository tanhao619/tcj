/**
 * OA管理管理初始化
 */
var Workflow = {
    id: "FaqiTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
var rowIndex=[];
var rowId=[]
Workflow.initColumn = function () {
    return [
        /*{field: 'selectItem', checkbox: true},*/
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '审批编号', field: 'ordnum', visible: true, align: 'center', valign: 'middle'},
        {title: '审批类型', field: 'type', visible: true, align: 'center', valign: 'middle'},
        {title: '发起人角色', field: 'role', visible: true, align: 'center', valign: 'middle'},
        {title: '审批附件', field: 'attachments', visible: true, align: 'center', valign: 'middle',
            formatter:function (value, row, index) {
                rowIndex=index;
                rowId=row.id;
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
        {title: '发起时间', field: 'apprTime', visible: true, align: 'center', valign: 'middle'},
        {title: '审批状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
        {title: '审批完成时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
        {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: Workflow.formatOperate},
        ];
};




/**
 * 点击发起审批
 */
Workflow.openAddWorkflow = function () {
    var index = layer.open({
        type: 2,
        title: '发起审批',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Feng.ctxPath + '/workflow/workflow_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看OA管理详情,不验证是否选中
 */
Workflow.openWorkflowOneDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '部门信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/workflow/workflow_update/' + id
    });
    this.layerIndex = index;
};



/**
 * 查询OA管理列表
 */
Workflow.search = function () {
    var queryData = {};
    queryData['type'] = $("#type").val();
    queryData['roleId'] = $("#roleId").val();
    queryData['status'] = $("#status").val();
    queryData['apprTimeB'] = $("#apprTimeB").val();
    queryData['apprTimeE'] = $("#apprTimeE").val();
    queryData['endTimeB'] = $("#endTimeB").val();
    queryData['endTimeE'] = $("#endTimeE").val();
    Workflow.table.refresh({query: queryData});
};

Workflow.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn = "<button class='btn btn-primary'   onclick='Workflow.open()' id='shenpi'>审批记录</button>";
    if (row.isShowRevo == 1){//是否显示撤销按钮（是否能撤销）：1显示撤销，0不显示
        var btn_cancle =  "<button class='btn btn-primary'   onclick='Workflow.revert("+id+")'>撤销</button>";
    }

    <!-- 审批类型:1.请假审批;2.销假审批;3.出差审批;4.接待审批;5.公务用车审批;6.驻点人员请假审批;7.发文审批;8.收文审批-->
    if(row.type == '1'){//请假审批
        var btn_thin =  "<button class='btn btn-primary'   onclick=''>请假时间</button>";
    }

    if(row.type == '8'){//收文审批
        var btn_thin =  "<button class='btn btn-primary'   onclick=''>办结详情</button>";
    }

    if(btn_thin != undefined && btn_cancle != undefined){
        return btn_cancle+"|" + btn_thin+"|"+ btn + "&nbsp;&nbsp;&nbsp;";
    }
    if(btn_thin != undefined && btn_cancle == undefined){
        return  btn_thin+"|"+ btn + "&nbsp;&nbsp;&nbsp;";
    }
    if(btn_thin == undefined && btn_cancle != undefined){
        return  btn_cancle+"|"+ btn + "&nbsp;&nbsp;&nbsp;";
    }
    return btn + "&nbsp;&nbsp;&nbsp;";
};
// /**添加每行下拉框**/
// Workflow.formatOperateSelect = function(val, row, index){
//     // alert(123);
//     var divWrap = "<div style='width:300px;height:100px;border:1px solid red'></div>";
//     return divWrap;
// };
function open() {
    var divWrap = "<div style='width:300px;height:100px;border:1px solid red'></div>";
   return divWrap;
}
/**
 * 撤销发起的审批
 */
Workflow.revert = function (WorkflowId) {
    var ajax = new $ax(Feng.ctxPath + "/workflow/revokeShenpi?workflowId="+ WorkflowId, function(data){
        if(data.code == 200){
            Feng.success(data.message);
            //刷新
            Workflow.search();
        }
        if(data.code != 200){
            Feng.error(data.message);
        }
    },function(error){
        Feng.error("操作失败," + error.message + "!");
    });
    ajax.start();
};
/**
 * 重置查询条件
 */
Workflow.resetSearch = function () {
    $("#type").val("");
    $("#roleId").val("");
    $("#status").val("");
    $("#apprTimeB").val("");
    $("#apprTimeE").val("");
    $("#endTimeB").val("");
    $("#endTimeE").val("");
    Workflow.search();
};
function openAgree(id){
    // alert("成功");
    //当前行的id
    console.log(id);
}



/**
 * add by lgg 用于测试 别删亲
 */
Workflow.api = function () {
    var ajax = new $ax(Feng.ctxPath + "/workflow/apprRole?type=''", function(data){
        console.log(data);
    });
    ajax.start();
};

$(function () {
    var defaultColunms = Workflow.initColumn();
    var table = new BSTableCC(Workflow.id, "/workflow/list", defaultColunms);
    table.setPaginationType("server");
    Workflow.table = table.init();

    // table.setDetailFormatter(divWrap);
    $("#FaqiTable").on("expand-row.bs.table",function(index,row,$detail){
        alert(123);
    });
    $("#FaqiTable").on("post-body.bs.table",function () {
        changeType("#FaqiTable",["1","2","3","4","5","6","7","8"],["请假审批","销假审批","出差审批","接待审批","公务用车审批","驻点人员请假审批","发文审批","收文审批"],2);
        changeType("#FaqiTable",["1","2","3","4","5","6"],["审批中","审批未通过","审批通过","办结中","已办结","已撤销"],6);
    });
    // var ajax1 = new $ax(Feng.ctxPath + "/workflow/list", function(data){
    //     var attachemntsDataTemp = "";
    //     for(var i=0;i<data.rows.length;i++){
    //        if(data.rows[i].attachments.length>0){
    //            for(var j = 0;j<data.rows[i].attachments.length;j++){
    //                if (attachemntsDataTemp != "") {
    //                    attachemntsDataTemp += "、</a>";
    //                }
    //                var fujian = "<a href='"+Feng.fileSer+""+data.rows[i].attachments[j].url+"' target='_blank'>"+data.rows[i].attachments[j].name+"";
    //                attachemntsDataTemp += fujian;
    //                 console.log(attachemntsDataTemp.length);
    //                 var attachemntsData = attachemntsDataTemp;
    //            }
    //            attachemntsDataTemp += "</a>";
    //            console.log(attachemntsDataTemp);
    //            $("#FaqiTable").on('post-body.bs.table',function () {
    //            $("#FaqiTable tr").each(function(){
    //                    if($(this).find("td:eq(4)").html()!=undefined && $(this).find("td:eq(4)").html() != ""){
    //                        $(this).find("td:eq(4)").html(attachemntsData);
    //                    }else{
    //                        return;
    //                    }
    //            });
    //            });
    //        }
    //     }
    // },function(error){
    //     Feng.error("获取附件信息失败," + error.message + "!");
    // });
    // ajax1.start();

   //获取发起人角色
    var ajax = new $ax(Feng.ctxPath + "/workflow/faqiRole", function(data){
        for(var i=0;i<data.length;i++){
            var option="<option value="+data[i].id+">"+data[i].name+"</option>";
              $("#roleId").append(option);
        }
    },function(error){
        Feng.error("获取账号角色信息失败," + error.message + "!");
    });
    ajax.start();

});

