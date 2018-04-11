/**
 * OA管理管理初始化
 */
var GuiDangStep = {
    id: "GuidangStepTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    flowsList:{},
    flowslistLen:0,
    flowsDTO:null,
    qjTime:null
};

/**
 * 初始化表格的列
 */
GuiDangStep.initColumn = function () {
    return [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '审批编号', field: 'ordnum', visible: true, align: 'center', valign: 'middle',formatter:GuiDangStep.readStatus},
        {title: '审批类型', field: 'type', visible: true, align: 'center', valign: 'middle',formatter:GuiDangStep.addguidangqjBtn},
        {title: '发起人', field: 'apprUserName', visible: true, align: 'center', valign: 'middle'},
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
        {title: '发起时间', field: 'apprTime', visible: true, align: 'center', valign: 'middle',sortable:true,width:'16%'},
        {title: '审批通过时间', field: 'endTime', visible: true, align: 'center', valign: 'middle',sortable:true,width:'22%'}
    ];
};
//未读状态
GuiDangStep.readStatus = function(value, row, index){
     if(row.readStatus == "未读")
     {
         return '<p class="noRead"></p>'+row.ordnum;
     }
     else{
         return row.ordnum;
     }
}

//添加请假撤销按钮
GuiDangStep.addguidangqjBtn = function (value, row, index) {
    if(value == '4'){
        return '接待审批';
    }
    if(value == '2'){
        return '销假审批';
    }
    if(value == '3'){
        return '出差审批';
    }
    if(value == '5'){
        return '公务用车审批';
    }
    if(value == '6'){
        return '驻点人员请假审批';
    }
    if(value == '7'){
        return '发文审批';
    }
    if(value == '8'){
        return '收文审批';
    }
    if(value == '1'){
        var strBtn = ""
        if(row.xiaojBtn == '0'){
            strBtn += '<button class="bkxj" disabled>未销假</button>';
        }
        if(row.xiaojBtn == '1'){
            strBtn += '<button class="bkxj" disabled>未销假</button>';
        }
        if(row.xiaojBtn == '2'){
            strBtn += '<button class="bkxj" disabled>已销假</button>';
        }
        return '请假审批 '+strBtn+'';
    }
}




/**
 * 查询OA管理列表
 */
GuiDangStep.search = function () {
    var queryData = {};
    queryData['guidangType'] = $("#guidangType").val();
    queryData['guidangApprUser'] = $("#guidangApprUser").val();
    queryData['guidangApprTimeB'] = $("#guidangApprTimeB").val();
    queryData['guidangApprTimeE'] = $("#guidangApprTimeE").val();
    queryData['guidangEndTimeB'] = $("#guidangEndTimeB").val();
    queryData['guidangEndTimeE'] = $("#guidangEndTimeE").val();
    queryData['readStatus'] = $("#readStatus").val();
    queryData['offset']=0;
    GuiDangStep.table.refresh({query: queryData});
};

GuiDangStep.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn_edit = "<button class='btn btn-primary'   onclick='WorkflowStep.openWorkflowStepOneDetail(" + id + ")'><i class='fa fa-edit'></i> 编辑</button>";
    var btn_del = "<button class='btn btn-danger'   onclick='WorkflowStep.deleteOne(" + id + ")'><i class='fa fa-trash-o'></i> 删除</button>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
};

/**
 * 重置查询条件
 */
GuiDangStep.resetSearch = function () {
    $("#guidangType").val("");
    $("#guidangApprUser").val("");
    $("#guidangApprTimeB").val("");
    $("#guidangApprTimeE").val("");
    $("#guidangEndTimeB").val("");
    $("#guidangEndTimeE").val("");
    $("#readStatus").val("");
    GuiDangStep.search();
};

function escapGuidangFlowsDetal(e, row, $element){
    // 设置改归档已读
    $.ajax({
        type: 'GET',
        url: Feng.ctxPath + "/workflow/guidang/read?workflowId="+row.id,
        success: function(data){
            //重置归档未读数量
            $.ajax({
                type: 'GET',
                url: Feng.ctxPath + "/workflow/guidang/unReadNum",
                success: function(data){
                    $("#unReadNum").html(data);
                },
                error: function(){
                    //请求失败处理函数
                }
            })
        },
        error: function(){
        }
    });

    var ajax = new $ax(Feng.ctxPath + "/workflowStep/guidang/detailflows?workflowId="+row.id+"&flowType="+row.type+"&fenleiType=4", function(data){
        if (data.type==8){
            GuiDangStep.flowsList = data.sWFlowDTOs;
            GuiDangStep.flowslistLen = data.sWFlowDTOs.length;
            GuiDangStep.flowsDTO = data;
            GuiDangStep.lineStep = data.lineStep;
            GuiDangStep.lineType = data.lineType;
        }else{
            GuiDangStep.flowsList = data.normalFlowDTOs;
            GuiDangStep.flowslistLen = data.normalFlowDTOs.length;
            GuiDangStep.flowsDTO = data;
            GuiDangStep.qjTime = data.qJTimePropDTO;
            GuiDangStep.lineStep = data.lineStep;
            GuiDangStep.lineType = data.lineType;
        }
    },function(data){
    });
    ajax.start();
    var list = GuiDangStep.flowsList;
    var listLen = GuiDangStep.flowslistLen;
    var type = GuiDangStep.flowsDTO.type;
    var  index1= $element.data('index'); //获取当前行的id

        if($element.next().is('tr.detail-view')){
            closeguidangRow();
        }
        else {
            closeguidangRow();
            //hide
            $("#GuidangStepTable").on("expand-row.bs.table",function(index,row,$detail,event){
                // alert('点击1');
                $(event).parent().children("td").html("");
                var divWrap = $("<div class='flyOpenTr clearfix' style='width:100%;' id="+$detail.id+"><div id='qjsj'></div><div class='oaData'></div></div>");
                var qingjiaSJStr="";
                if(GuiDangStep.flowsDTO.type == "1"){
                    var qjDTO = GuiDangStep.qjTime;
                    qingjiaSJStr = "<div class='qjWrap'><p class='qjTitle'>请假时段：</p></div>";
                    if(qjDTO.isSame == "true"){
                        qingjiaSJStr+="<p><span>"+qjDTO.qjTimeB+"</span><span>"+qjDTO.qjAMPMB+"</span></p>";
                        // $(".qjTitle").after(qingjiaSJStr);
                    }else{
                        qingjiaSJStr+="<p><span>"+qjDTO.qjTimeB+"</span><span>"+qjDTO.qjAMPMB+"</span></p><p><span>"+qjDTO.qjTimeE+"</span><span>"+qjDTO.qjAMPME+"</span></p>"
                    }
                    qingjiaSJStr+="<p>共计"+qjDTO.qjTotalDays+"天</p>";
                    divWrap.find("#qjsj").html(qingjiaSJStr);

                }
                if(GuiDangStep.flowsDTO.type != "1"){
                    divWrap.find("#qjsj").css({"margin-left":"0px","margin-right":"30px"});
                }
                var lineBar = $("<div class='lineBar'></div>");
                // .appendChild(lineBar);
                for(var i=0;i<listLen;i++){
                    if(i==listLen-1){
                        lineBar.append("<span class='circle'></span>");
                    }else{
                        lineBar.append("<span class='circle'></span><span class='notShenpi flyLine'></span>");
                    }

                }
                // $(".oaData").append(lineBar)
                divWrap.find(".oaData").append(lineBar);
                for(var i=0;i<listLen;i++){
                    divWrap.find(".oaData").append("<div class='listItem'></div>");
                }
                for(var i=0;i<listLen;i++){
                    showguidangList(list[i].step,list[i],divWrap,list[i].spAdvice,type,$detail.id);
                }
                $(event).parent().children("td").html(divWrap);
                // $('#GuiDangStepTable').bootstrapTable('expandRow', index1);
            });
            $('#GuidangStepTable').bootstrapTable('expandRow', index1);

        }


}
function closeguidangRow() {
    for (var i=0; i < 20; i ++) {
        $('#GuidangStepTable').bootstrapTable('collapseRow', i);
    }
}
function showguidangList(step,item,divWrap,spAdvice,type,workflowId) {
    var newList = [];
    for(var key in item){
        var value = item[key];
        if(key!="step" && value != "" && value.indexOf("##0##") == -1){
            var valueSplite = value.split("##");
            newList.push({
                num:parseInt(valueSplite[2]),
                label:valueSplite[0],
                name:valueSplite[1]
            })
        }
    }
    for(var k=0;k<newList.length;k++){
        for(var j=0;j<newList.length;j++){
            if(newList[k].num<newList[j].num){
                var tem = newList[k];
                newList[k] = newList[j];
                newList[j] = tem
            }
        }
    }
    var str = "";
    for(var i=0;i<newList.length;i++){
        str+= "<div class='listItemRow'><p>"+newList[i].label+":</p><p>"+newList[i].name+"</p></div>";
    }
    if(type == "8" && step == "6"){
        str+= "<div class='listItemRow' style='margin-top: 75px;'><div class='spyjColor sureBtnWrap'><button style='width: 100px;' onclick='GuiDangStep.bjDetail("+workflowId+")'>查看办结详情</button></div></div>";

    }
    divWrap.find(".listItem").eq(step-1).html(str);
}
//查看办结详情
GuiDangStep.bjDetail = function(workflowId){
    $.ajax({
        type: 'GET',
        url: Feng.ctxPath + "/workflowStep/banjieDetail",
        data: {'workflowId':workflowId},
        async:false,//同步加载
        success: function(data){
            if(data.length > 0){
                var index = layer.open({
                    type: 2,
                    title: '办结详情',
                    area: ['30%', '60%'], //宽高
                    fix: false, //不固定
                    maxmin: true,
                    content: Feng.ctxPath + "/workflowStep/obtain_banjie_detail?workflowId="+workflowId+""
                });
                this.layerIndex = index;
            }
            else{
                parent.layer.open({
                    title: '办结详情',
                    type: 0,
                    //skin: 'layui-layer-rim', //加上边框
                    area: ['250px', '205px'], //宽高,
                    btn:['确定','取消'],
                    content: '<div style="padding: 20px;">还未分配科员办结 </div>',
                    yes: function(index, workflowId){
                        //按钮【按钮一】的回调
                        parent.layer.close(index);
                    }

                });
            }
        },
        error: function(){
            //请求失败处理函数
        }
    });

}
$(function () {

    //显示归档未读数量
    $.ajax({
        type: 'GET',
        url: Feng.ctxPath + "/workflow/guidang/unReadNum",
        success: function(data){
            $("#unReadNum").html(data);
            if($("#unReadNum").html() != 0){//有未办结的，只显示未办结的，选中否
                $("#readStatus").val("未读");
                GuiDangStep.search();
            }
        },
        error: function(){
            //请求失败处理函数
        }
    });

    var defaultColunms = GuiDangStep.initColumn();
    var table = new BSTableCC(GuiDangStep.id, "/workflow/guidang/list", defaultColunms);
    table.setPaginationType("server");
    GuiDangStep.table = table.init();
    //点击当前行展开详情
    $('#GuidangStepTable').on('click-row.bs.table', function (e, row, $element) {
        $($element).find(".noRead").hide();
        escapGuidangFlowsDetal(e, row, $element);

    });
    $("#guidangApprTimeB,#guidangApprTimeE").focus(function(){
        timeBetween($("#guidangApprTimeB"),$("#guidangApprTimeE"));
    });
    // 审批时间
    $("#guidangEndTimeB,#guidangEndTimeE").focus(function(){
        timeBetween($("#guidangEndTimeB"),$("#guidangEndTimeE"));
    });
});
