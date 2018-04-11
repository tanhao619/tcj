/**
 * OA管理管理初始化
 */
var Workflow = {
    id: "FaqiTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    flowsList:{},
    flowslistLen:0,
    flowsDTO:null,
    qjTime:null,
    spStep:null,
    revokeStatus:null,
    lineStep:null,
    lineType:null
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
        {title: '审批类型', field: 'type', visible: true, align: 'center', valign: 'middle',formatter:Workflow.addqjBtn

        },
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
        {title: '审批状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
        {title: '审批完成时间', field: 'endTime', visible: true, align: 'center', valign: 'middle',sortable:true,width:'21%'},
        // {title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: Workflow.formatOperate},
        ];
};

//添加请假撤销按钮
Workflow.addqjBtn = function (value, row, index) {
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
            strBtn += '<button onclick="Workflow.cancelQj('+row.id+')" class="kyxj">销假</button>';
        }
       if(row.xiaojBtn == '1'){
           strBtn += '<button class="bkxj" disabled>销假</button>';
        }
        if(row.xiaojBtn == '2'){
            strBtn += '<button class="bkxj" disabled>已销假</button>';
        }
        return '请假审批 '+strBtn+'';
    }
}


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
    queryData['offset']=0;
    Workflow.table.refresh({query: queryData});
};

Workflow.formatOperate = function (val, row, index) {
    // var id = row.id;
    // var btn = "<button class='btn btn-primary'   onclick='Workflow.open()' id='shenpi'>审批记录</button>";
    // if (row.isShowRevo == 1){//是否显示撤销按钮（是否能撤销）：1显示撤销，0不显示
    //     var btn_cancle =  "<button class='btn btn-primary'   onclick='Workflow.revert("+id+")'>撤销</button>";
    // }
    //
    // <!-- 审批类型:1.请假审批;2.销假审批;3.出差审批;4.接待审批;5.公务用车审批;6.驻点人员请假审批;7.发文审批;8.收文审批-->
    // if(row.type == '1'){//请假审批
    //     var btn_thin =  "<button class='btn btn-primary'   onclick=''>请假时间</button>";
    // }
    //
    // if(row.type == '8'){//收文审批
    //     var btn_thin =  "<button class='btn btn-primary'   onclick=''>办结详情</button>";
    // }
    //
    // if(btn_thin != undefined && btn_cancle != undefined){
    //     return btn_cancle+"|" + btn_thin+"|"+ btn + "&nbsp;&nbsp;&nbsp;";
    // }
    // if(btn_thin != undefined && btn_cancle == undefined){
    //     return  btn_thin+"|"+ btn + "&nbsp;&nbsp;&nbsp;";
    // }
    // if(btn_thin == undefined && btn_cancle != undefined){
    //     return  btn_cancle+"|"+ btn + "&nbsp;&nbsp;&nbsp;";
    // }
    // return btn + "&nbsp;&nbsp;&nbsp;";
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
}



/**
 * add by lgg 用于测试 别删亲
 */
Workflow.api = function () {
    var ajax = new $ax(Feng.ctxPath + "/workflow/apprRole?type=''", function(data){
    });
    ajax.start();
};
function showFaqiList(step,item,divWrap,spAdvice,type,workflowId) {
    console.log(workflowId);
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
        str+= "<div class='listItemRow' style='margin-top: 75px;'><div class='spyjColor sureBtnWrap'><button style='width: 100px;' onclick='Workflow.bjDetail("+workflowId+")'>查看办结详情</button></div></div>";

    }

    divWrap.find(".listItem").eq(step-1).html(str);
}

var _row;//备份
var t_element;
function escapFaqiFlowsDetal(e, row, $element) {
    var flag = false;
    if (row == null) {
        row = _row;
        flag = true;
    }
    if ($element == null) {
        $element = t_element;
        flag = true;
    }
    var ajax = new $ax(Feng.ctxPath + "/workflowStep/faqi/detailflows?workflowId="+row.id+"&flowType="+row.type+"&fenleiType=1", function(data){
        if (data.type==8){
            Workflow.flowsList = data.sWFlowDTOs;
            Workflow.flowslistLen = data.sWFlowDTOs.length;
            Workflow.flowsDTO = data;
            Workflow.revokeStatus = data.revokeStatus;
            Workflow.lineStep = data.lineStep;
            Workflow.lineType = data.lineType;
        }else{
            Workflow.flowsList = data.normalFlowDTOs;
            Workflow.flowslistLen = data.normalFlowDTOs.length;
            Workflow.flowsDTO = data;
            Workflow.qjTime = data.qJTimePropDTO;
            Workflow.revokeStatus = data.revokeStatus;
            Workflow.lineStep = data.lineStep;
            Workflow.lineType = data.lineType;
        }
    },function(data){

    });
    ajax.start();
    var list = Workflow.flowsList;
    var listLen = Workflow.flowslistLen;
    var type = Workflow.flowsDTO.type;
    var  index1= $element.data('index'); //获取当前行的id
    if (!flag) {
        //hide
        if($element.next().is('tr.detail-view')){
          //  $('#FaqiTable').bootstrapTable('collapseRow', index1);
            closeFaqiRow();
        }
        else{
            closeFaqiRow();
            $("#FaqiTable").on("expand-row.bs.table",function(index,row,$detail,event){

                $(event).parent().children("td").html("");
                var divWrap = $("<div class='flyOpenTr clearfix' style='width:100%;' id="+$detail.workflowId+"><div id='qjsj'></div><div class='oaData'></div><div class='cx'></div></div>");
                var qingjiaSJStr="";
                if(Workflow.flowsDTO.type == "1"){
                    var qjDTO = Workflow.qjTime;
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
                if(Workflow.flowsDTO.type != "1"){
                    divWrap.find("#qjsj").css({"margin-left":"0px","margin-right":"30px"});
                }

                var lineBar = $("<div class='lineBar'></div>");
                // .appendChild(lineBar);
                for(var i=1;i<=listLen;i++){
                    if(i==1){
                        lineBar.append("<span class='circle'></span>");
                        continue;
                    }
                    if (i > 1   && i < Workflow.lineStep){
                        lineBar.append("<span class='notShenpi flyLine'></span><span class='circle'></span>");
                        continue;
                    }
                    if (i == Workflow.lineStep){
                        if(Workflow.lineType == '同意'){
                            lineBar.append("<span class='notShenpi flyLine'></span><span class='circle'></span>");
                            continue;
                        }
                        if(Workflow.lineType == '不同意'){
                            lineBar.append("<span class='notShenpi yellow'></span><span class='circle yellow'></span>");
                            continue;
                        }
                        if(Workflow.lineType == '待审批'){
                            lineBar.append("<span class='notShenpi '></span><span class='circle  waitShenpi'></span>");
                            continue;
                        }
                    }
                    if (i > Workflow.lineStep && i <= listLen){
                        lineBar.append("<span class='notShenpi'></span><span class='circle  notShenpiCircle'></span>");
                    }

                }
                divWrap.find(".oaData").append(lineBar);
                for(var i=0;i<listLen;i++){
                    divWrap.find(".oaData").append("<div class='listItem'></div>");
                }
                for(var i=0;i<listLen;i++){
                    showFaqiList(list[i].step,list[i],divWrap,list[i].spAdvice,type,$detail.id);
                }
                $(event).parent().children("td").html(divWrap);
                //判断撤销
                var revokeStatus = "";
                if(Workflow.revokeStatus == '0'){
                    revokeStatus += '<button class="faqiCx kyCx" onclick="Workflow.revokeShenpi('+$detail.id+')">撤销</button>'
                }
                if(Workflow.revokeStatus == '1'){
                    revokeStatus += '<button class="faqiCx bkCx">撤销</button>'
                }
                if(Workflow.revokeStatus == '2'){
                    revokeStatus += '<button class="faqiCx bkCx">已撤销</button>'
                }
                divWrap.find(".oaData").children(".listItem:first").children(".listItemRow:last").after(revokeStatus);
            });
            $('#FaqiTable').bootstrapTable('expandRow', index1);

        }} else {
        closeFaqiRow();
       // $('#FaqiTable').bootstrapTable('collapseRow', index1);
        $("#FaqiTable").on("expand-row.bs.table",function(index,row,$detail,event){
            $(event).parent().children("td").html("");
            var divWrap = $("<div class='flyOpenTr clearfix' style='width:100%;' id="+$detail.workflowId+"><div id='qjsj'></div><div class='oaData'></div></div>");
            var qingjiaSJStr="";
            if(Workflow.flowsDTO.type == "1"){
                var qjDTO = Workflow.qjTime;
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
            if(Workflow.flowsDTO.type != "1"){
                divWrap.find("#qjsj").css({"margin-left":"0px","margin-right":"30px"});
            }
            var lineBar = $("<div class='lineBar'></div>");
            for(var i=1;i<=listLen;i++){
                if(i==1){
                    lineBar.append("<span class='circle'></span>");
                    continue;
                }
                if (i > 1   && i < Workflow.lineStep){
                    lineBar.append("<span class='notShenpi flyLine'></span><span class='circle'></span>");
                    continue;
                }
                if (i == Workflow.lineStep){
                    if(Workflow.lineType == '同意'){
                        lineBar.append("<span class='notShenpi flyLine'></span><span class='circle'></span>");
                        continue;
                    }
                    if(Workflow.lineType == '不同意'){
                        lineBar.append("<span class='notShenpi yellow'></span><span class='circle yellow'></span>");
                        continue;
                    }
                    if(Workflow.lineType == '待审批'){
                        lineBar.append("<span class='notShenpi '></span><span class='circle  waitShenpi'></span>");
                        continue;
                    }
                }
                if (i > Workflow.lineStep && i <= listLen){
                    lineBar.append("<span class='notShenpi '></span><span class='circle  notShenpiCircle'></span>");
                }


            }
            divWrap.find(".oaData").append(lineBar);
            for(var i=0;i<listLen;i++){
                divWrap.find(".oaData").append("<div class='listItem'></div>");
            }
            for(var i=0;i<listLen;i++){
                showList(list[i].step,list[i],divWrap,list[i].spAdvice,type,$detail.workflowId);
            }
            $(event).parent().children("td").html(divWrap);

        });
        $('#FaqiTable').bootstrapTable('expandRow', index1);
    }
}
//撤销审批
Workflow.revokeShenpi = function(workflowId){
    parent.layer.open({
        title: '撤销确认',
        type: 0,
        //skin: 'layui-layer-rim', //加上边框
        area: ['250px', '205px'], //宽高,
        btn:['确定','取消'],
        content: '<div style="padding: 20px;">确定要撤销? </div>',
        yes: function(index, workflowId1){
            //按钮【按钮一】的回调
            //     console.log('成功');
            var ajaxQxqj = new $ax(Feng.ctxPath + "/workflow/revokeShenpi?workflowId="+workflowId, function(data){
                $(".kyCx").html("已撤销");
                $(".kyCx").css({'background':'#b0cadd','cursor':'not-allowed'});
            },function(){

            })
            ajaxQxqj.start();
            parent.layer.close(index);
        }

    });

}

//撤销请假

Workflow.cancelQj = function(workflowId){
    var sureInfo = '<div class="detailInfo"><p class="qjTitle">确定要销假?</p></div>'
     var workId = workflowId;
    //Feng.infoDetail('销假确认',sureInfo);
    // sureCancel(workflowId);
    parent.layer.open({
        title: '销假确认',
        type: 0,
        //skin: 'layui-layer-rim', //加上边框
        area: ['250px', '205px'], //宽高,
        btn:['确定','取消'],
        content: '<div style="padding: 20px;">' + sureInfo + '</div>',
        yes: function(index, workflowId){
        //按钮【按钮一】的回调
        //     console.log('成功');
            var ajaxQxqj = new $ax(Feng.ctxPath + "/workflow/faqi/xiaojia?workflowId="+workId, function(data){
                Workflow.table.refresh();
            },function(){

            })
            ajaxQxqj.start();
            parent.layer.close(index);
        }

    });

};
//查看办结详情
Workflow.bjDetail = function(workflowId){
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

//收起行
function closeFaqiRow() {
    for (var i=0; i < 20; i ++) {
        $('#FaqiTable').bootstrapTable('collapseRow', i);
    }
}
$(function () {
    inputFocus();
    var defaultColunms = Workflow.initColumn();
    var table = new BSTableCC(Workflow.id, "/workflow/list", defaultColunms);
    table.setPaginationType("server");
    Workflow.table = table.init();

    $('#FaqiTable').on('click-row.bs.table', function (e, row, $element) {
        if (row != null && row != "" && row != "undefined") {
            _row = row;
        }
        if ($element != null && $element != "" && $element != "undefined") {
            t_element = $element;
        }
        escapFaqiFlowsDetal(e, row, $element);

    });
    $("#FaqiTable").on("post-body.bs.table",function () {
        changeType("#FaqiTable",["1","2","3","4","5","6","7","8"],["请假审批","销假审批","出差审批","接待审批","公务用车审批","驻点人员请假审批","发文审批","收文审批"],2);
        changeType("#FaqiTable",["1","2","3","4","5","6"],["审批中","审批未通过","审批通过","办结中","已办结","已撤销"],6);
    });
    // timeSingle($("#createTimeE"));
    // timeSingle($("#updateTimeE"));
    $("#apprTimeB,#apprTimeE").focus(function(){
        timeBetween($("#apprTimeB"),$("#apprTimeE"));
    });
    // 修改时间
    $("#endTimeB,#endTimeE").focus(function(){
        timeBetween($("#endTimeB"),$("#endTimeE"));
    });

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

