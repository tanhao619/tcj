/**
 * OA管理管理初始化
 */
var BanjieStep = {
    id: "BanjieStepTable",	//表格id
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
BanjieStep.initColumn = function () {
    return [
        {title: 'id', field: 'workflowId', visible: false, align: 'center', valign: 'middle'},
        {title: '审批编号', field: 'ordnum', visible: true, align: 'center', valign: 'middle'},
        {title: '收文编号', field: 'ordnumSW', visible: true, align: 'center', valign: 'middle'},
        {title: '发起时间', field: 'faqiTime', visible: true, align: 'center', valign: 'middle'},
        {title: '指派科室', field: 'assignDept', visible: true, align: 'center', valign: 'middle'},
        {title: '指派科长', field: 'assignKZ', visible: true, align: 'center', valign: 'middle'},
        {title: '办结附件', field: 'attachments', visible: true, align: 'center', valign: 'middle',
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
            }
        },
        {title: '指派时间', field: 'assignTime', visible: true, align: 'center', valign: 'middle'},
        {title: '是否办结', field: 'isBanjie', visible: true, align: 'center', valign: 'middle'},
        {title: '办结时间', field: 'banjieTime', visible: true, align: 'center', valign: 'middle'}
        /*{title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: BanjieStep.formatOperate}*/
    ];
};

BanjieStep.formatOperate = function (val, row, index) {
    var id = row.id;
    var btn = "<button class='btn btn-primary'   onclick=''>审批记录</button>";
    return btn + "&nbsp;&nbsp;&nbsp;";
}

/**
 * 打开查看OA管理详情,不验证是否选中
 */
BanjieStep.openWorkflowStepOneDetail = function (id) {
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
BanjieStep.search = function () {
    var queryData = {};
    queryData['isBanjie'] = $("#isBanjie").val();
    queryData['assignDept'] = $("#assignDept").val();
    queryData['assignKZ'] = $.trim($("#assignKZ").val());
    queryData['banjieFaqiTimeB'] = $("#banjieFaqiTimeB").val();
    queryData['banjieFaqiTimeE'] = $("#banjieFaqiTimeE").val();
    queryData['assignTimeB'] = $("#assignTimeB").val();
    queryData['assignTimeE'] = $("#assignTimeE").val();
    queryData['banjieTimeB'] = $("#banjieTimeB").val();
    queryData['banjieTimeE'] = $("#banjieTimeE").val();
    queryData['offset']=0;
    BanjieStep.table.refresh({query: queryData});
};
/**
 * 重置查询条件
 */
BanjieStep.resetSearch = function () {
    $("#isBanjie").val("");
    $("#assignDept").val("");
    $("#assignKZ").val("");
    $("#banjieFaqiTimeB").val("");
    $("#banjieFaqiTimeE").val("");
    $("#assignTimeB").val("");
    $("#assignTimeE").val("");
    $("#banjieTimeB").val("");
    $("#banjieTimeE").val("");
    BanjieStep.search();
};
var _row;//备份
var t_element;
BanjieStep.escapFlowsDetal =function(e, row, $element) {
    var flag = false;
    if (row == null) {
        row = _row;
        flag = true;
    }
    if ($element == null) {
        $element = t_element;
        flag = true;
    }
    var ajax = new $ax(Feng.ctxPath + "/workflow/banjie/detailflows?workflowId="+row.workflowId+"&flowType="+row.type+"&fenleiType=2", function(data){
        BanjieStep.flowsList = data.sWFlowDTOs;
        BanjieStep.flowslistLen = data.sWFlowDTOs.length;
        BanjieStep.flowsDTO = data;
        BanjieStep.lineStep = data.lineStep;
        BanjieStep.lineType = data.lineType;
    },function(data){
    });
    ajax.start();
    var list = BanjieStep.flowsList;
    var listLen = BanjieStep.flowslistLen;
    var type = BanjieStep.flowsDTO.type;
    var  index1= $element.data('index'); //获取当前行的id
    if (!flag) {
        if($element.next().is('tr.detail-view')){
            closebanjieRow();
        }
        else {
            closebanjieRow();
            //hide
            $("#BanjieStepTable").on("expand-row.bs.table",function(index,row,$detail,event){
                $(event).parent().children("td").html("");
                var divWrap = $("<div class='flyOpenTr clearfix' style='width:100%;' id="+$detail.workflowId+"><div id='qjsj'></div><div class='oaData'></div></div>");
                var qingjiaSJStr="";
                if(BanjieStep.flowsDTO.type == "1"){
                    var qjDTO = BanjieStep.qjTime;
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
                if(BanjieStep.flowsDTO.type != "1"){
                    divWrap.find("#qjsj").css({"margin-left":"0px","margin-right":"30px"});
                }
                var lineBar = $("<div class='lineBar'></div>");
                // .appendChild(lineBar);
                for(var i=1;i<=listLen;i++){
                    if(i==1){
                        lineBar.append("<span class='circle'></span>");
                        continue;
                    }
                    if (i > 1   && i < BanjieStep.lineStep){
                        lineBar.append("<span class='notShenpi flyLine'></span><span class='circle'></span>");
                        continue;
                    }
                    if (i == BanjieStep.lineStep){
                        if(BanjieStep.lineType == '同意'){
                            lineBar.append("<span class='notShenpi flyLine'></span><span class='circle'></span>");
                            continue;
                        }
                        if(BanjieStep.lineType == '不同意'){
                            lineBar.append("<span class='notShenpi yellow'></span><span class='circle yellow'></span>");
                            continue;
                        }
                        if(BanjieStep.lineType == '待审批'){
                            lineBar.append("<span class='notShenpi '></span><span class='circle  waitShenpi'></span>");
                            continue;
                        }
                    }
                    if (i > BanjieStep.lineStep && i <= listLen){
                        lineBar.append("<span class='notShenpi'></span><span class='circle  notShenpiCircle'></span>");
                    }

                }
                // $(".oaData").append(lineBar)
                divWrap.find(".oaData").append(lineBar);
                for(var i=0;i<listLen;i++){
                    divWrap.find(".oaData").append("<div class='listItem'></div>");
                }
                for(var i=0;i<listLen;i++){
                    showbanjieList(list[i].step,list[i],divWrap,list[i].spAdvice,type,$detail.workflowId,BanjieStep.lineStep,BanjieStep.lineType);
                }
                $(event).parent().children("td").html(divWrap);
                // $('#ShenpiStepTable').bootstrapTable('expandRow', index1);
            });
            $('#BanjieStepTable').bootstrapTable('expandRow', index1);
        }


    } else {
        closebanjieRow();
        $("#BanjieStepTable").on("expand-row.bs.table",function(index,row,$detail,event){
            $(event).parent().children("td").html("");
            var divWrap = $("<div class='flyOpenTr clearfix' style='width:100%;' id="+$detail.workflowId+"><div id='qjsj'></div><div class='oaData'></div></div>");
            var qingjiaSJStr="";
            if(BanjieStep.flowsDTO.type == "1"){
                var qjDTO = BanjieStep.qjTime;
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
            if(BanjieStep.flowsDTO.type != "1"){
                divWrap.find("#qjsj").css({"margin-left":"0px","margin-right":"30px"});
            }
            var lineBar = $("<div class='lineBar'></div>");
            // .appendChild(lineBar);
            for(var i=1;i<=listLen;i++){
                if(i==1){
                    lineBar.append("<span class='circle'></span>");
                    continue;
                }
                if (i > 1   && i < BanjieStep.lineStep){
                    lineBar.append("<span class='notShenpi flyLine'></span><span class='circle'></span>");
                    continue;
                }
                if (i == BanjieStep.lineStep){
                    if(BanjieStep.lineType == '同意'){
                        lineBar.append("<span class='notShenpi flyLine'></span><span class='circle'></span>");
                        continue;
                    }
                    if(BanjieStep.lineType == '不同意'){
                        lineBar.append("<span class='notShenpi yellow'></span><span class='circle yellow'></span>");
                        continue;
                    }
                    if(BanjieStep.lineType == '待审批'){
                        lineBar.append("<span class='notShenpi '></span><span class='circle  waitShenpi'></span>");
                        continue;
                    }
                }
                if (i > BanjieStep.lineStep && i <= listLen){
                    lineBar.append("<span class='notShenpi'></span><span class='circle  notShenpiCircle'></span>");
                }

            }
            divWrap.find(".oaData").append(lineBar);
            for(var i=0;i<listLen;i++){
                divWrap.find(".oaData").append("<div class='listItem'></div>");
            }
            for(var i=0;i<listLen;i++){
                showbanjieList(list[i].step,list[i],divWrap,list[i].spAdvice,type,$detail.workflowId,BanjieStep.lineStep,BanjieStep.lineType);
            }
            $(event).parent().children("td").html(divWrap);

        });
        $('#BanjieStepTable').bootstrapTable('expandRow', index1);
    }
}
function closebanjieRow() {
    for (var i=0; i < 20; i ++) {
        $('#BanjieStepTable').bootstrapTable('collapseRow', i);
    }
}
function showbanjieList(step,item,divWrap,spAdvice,type,workflowId,lineStep,lineType) {
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
    if(spAdvice && spAdvice.indexOf("##0##") > 0){
        var isCanSpFlow = BanjieStep.flowsDTO.isCanSpFlow;
        var parmsStr = "?workflowId=" + workflowId+"&step="+step+
            "&type=" +type;

        BanjieStep.spStep = step;
        str+= "<div class='listItemRow'><p class='spyjColor'>审批意见:</p><p class='spyjColor'>是否同意审批?</p></div>";
        if (type == "7" ){//发文只有同意按钮
            /*str+="<div class='sureBtnWrap'><button><a href='"+Feng.ctxPath+"/shenpi/apprNormalflow"+parmsStr+"&spAdvice=2"+"'>同意</a></button></div>"*/
            if (isCanSpFlow == "true"){
                str+="<div class='sureBtnWrap'><button onclick='BanjieStep.apprNormalflow("+workflowId+","+step+","+type+",2)'>同意</button></div>"
            }else{
                str+="<div class='sureBtnWrap'><button disabled='disabled'>同意</button></div>"
            }

        }else if (type == "8"){// 收文只有同意按钮
            /* str+="<div class='sureBtnWrap'><button><a href='www.baidu.com'>同意</a></button></div>"*/
            if (isCanSpFlow == "true"){
                str+="<div class='sureBtnWrap'><button onclick='BanjieStep.apprShowWenflow("+workflowId+","+step+","+type+",2)'>同意</button></div>"
            }else{
                str+="<div class='sureBtnWrap'><button disabled='disabled'>同意</button></div>"
            }

        }else {// 其他有两按钮
            /*str+="<div class='sureBtnWrap'><button><a href='"+Feng.ctxPath+"/shenpi/apprNormalflow"+parmsStr+"&spAdvice=1"+"'>不同意</button></a></div>"
             str+="<div class='sureBtnWrap'><button><a href='"+Feng.ctxPath+"/shenpi/apprNormalflow"+parmsStr+"&spAdvice=2"+"'>同意</a></button></div>"*/
            if (isCanSpFlow == "true"){
                str+="<div class='sureBtnWrap'><button onclick='BanjieStep.apprNormalflow("+workflowId+","+step+","+type+",1)'>不同意</button></div>"
                str+="<div class='sureBtnWrap'><button onclick='BanjieStep.apprNormalflow("+workflowId+","+step+","+type+",2)'>同意</button></div>"
            }else {
                str+="<div class='sureBtnWrap'><button disabled='disabled'>不同意</button></div>"
                str+="<div class='sureBtnWrap'><button disabled='disabled'>同意</button></div>"
            }

        }

    }
    if(step == "6"){
        if(lineType == "待审批"){
            str+= "<div class='listItemRow'><p>办结详情：</p><p class='lookBanjieDetail' onclick='BanjieStep.bjDetail("+workflowId+")'>查看办结详情</p></div><div class='listItemRow isNotBanjie'><p>是否办结：</p><p>是否予以办结?</p><button onclick='BanjieStep.sureBanjie("+workflowId+",this)'>办结</button></div>";
        }
        // else{
        //     str+= "<div class='listItemRow'><p>办结详情：</p><a>查看办结详情</a></div><div class='listItemRow'><p>是否办结：</p><p>已办结</p></div>";
        // }

    }
    divWrap.find(".listItem").eq(step-1).html(str);
}
//查看办结详情
BanjieStep.bjDetail = function(workflowId){
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
//确认办结
BanjieStep.sureBanjie = function(workflowId,agreeBtn){
    parent.layer.open({
        title: '办结确认',
        type: 0,
        //skin: 'layui-layer-rim', //加上边框
        area: ['250px', '205px'], //宽高,
        btn:['确定','取消'],
        content: '<div style="padding: 20px;">确定已完成办结，修改后不可撤销？ </div>',
        yes: function(index, workflowId1){
            //按钮【按钮一】的回调
            //     console.log('成功');
            var ajax = new $ax(Feng.ctxPath + "workflow/banjie/bJSWworkflow?workflowId="+workflowId+"", function(data){
                if(data.code == "200"){
                    Feng.success(data.message);
                    $(agreeBtn).parents(".detail-view").prev().children('td').eq(8).text('是');
                    $('#BanjieStepTable').trigger('click-row.bs.table');

                }else{
                    Feng.error(data.message);
                }
            },function(data){
                Feng.error("审批失败");
            });
            ajax.start();
            parent.layer.close(index);
            $.ajax({
                type: 'GET',
                url: Feng.ctxPath + "/workflow/banjie/unApprNum",
                success: function(data){
                    $("#noBanJieNum").html(data);
                },
                error: function(){
                    //请求失败处理函数
                }
            });

        }
    });
}
$(function () {
    //显示我未办结的数量
    $.ajax({
        type: 'GET',
        url: Feng.ctxPath + "/workflow/banjie/unApprNum",
        success: function(data){
            $("#noBanJieNum").html(data);
            if($("#noBanJieNum").html() != 0){//有未办结的，只显示未办结的，选中否
                $("#isBanjie").val("否");
                BanjieStep.search();
            }
        },
        error: function(){
            //请求失败处理函数
        }
    });
    //显示指派科室
    $.ajax({
        type: 'GET',
        url: Feng.ctxPath + "/workflow/banjie/assignDept",
        success: function(data){
            if(data.length >= 1){
               for(var i = 0;i < data.length;i++){
                   var option="<option value="+data[i]+">"+data[i]+"</option>";
                   $("#assignDept").append(option)
               }
            }
        },
        error: function(){
            //请求失败处理函数
        }
    });
    var defaultColunms = BanjieStep.initColumn();
    var table = new BSTableCC(BanjieStep.id, "/workflow/banjie/list", defaultColunms);
    table.setPaginationType("server");
    BanjieStep.table = table.init();
    //点击当前行展开详情
    $('#BanjieStepTable').on('click-row.bs.table', function (e, row, $element) {
        if (row != null && row != "" && row != "undefined") {
            _row = row;
        }
        if ($element != null && $element != "" && $element != "undefined") {
            t_element = $element;
        }
        BanjieStep.escapFlowsDetal(e, row, $element);

    });
    //办结发起时间
    $("#banjieFaqiTimeB,#banjieFaqiTimeE").focus(function(){
        timeBetween($("#banjieFaqiTimeB"),$("#banjieFaqiTimeE"));
    });
    //办结指派时间
    $("#assignTimeB,#assignTimeE").focus(function(){
        timeBetween($("#assignTimeB"),$("#assignTimeE"));
    });
    //办结时间
    $("#banjieTimeB,#banjieTimeE").focus(function(){
        timeBetween($("#banjieTimeB"),$("#banjieTimeE"));
    });
});
