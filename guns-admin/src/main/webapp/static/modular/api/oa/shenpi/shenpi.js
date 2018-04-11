/**
 * OA管理管理初始化
 */
var ShenpiStep = {
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
        {title: '发起时间', field: 'faqiTime', visible: true, align: 'center', valign: 'middle',sortable:true,width:'16%'},
        {title: '是否审批', field: 'isAppr', visible: true, align: 'center', valign: 'middle'},
        {title: '审批时间', field: 'apprTime', visible: true, align: 'center', valign: 'middle',sortable:true,width:'16%'}
        /*{title: '操作', field: 'id', visible: true,align: 'center', valign: 'middle', formatter: ShenpiStep.formatOperate}*/
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
    queryData['offset']=0;
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

// 审批一般审批
ShenpiStep.apprNormalflow = function (workflowId,step,type,advice,agreeBtn) {
    // Integer workflowId,String step,String type,String spAdvice
    var parms = "?workflowId=" +workflowId+"&step="+step+
        "&type=" +type + "&spAdvice="+advice;
    // alert("审批一般审批");
    //打开弹框
    if(advice == 1){
        parent.layer.open({
            title: '审批确认',
            type: 0,
            //skin: 'layui-layer-rim', //加上边框
            area: ['250px', '205px'], //宽高,
            btn:['确定','取消'],
            content: '<div style="padding: 20px;">确认不同意该审批? </div>',
            yes: function(index, workflowId){
                //按钮【按钮一】的回调
                //     console.log('成功');
                var ajax = new $ax(Feng.ctxPath + "/workflowStep/shenpi/apprNormalflow"+parms, function(data){
                    if(data.code == "200"){
                        Feng.success(data.message);
                        $('#ShenpiStepTable').trigger('click-row.bs.table');

                    }else{
                        Feng.error(data.message);
                    }
                },function(data){
                    Feng.error("审批失败");
                });
                ajax.start();
                parent.layer.close(index);
            }

        });
    }
    if(advice == 2){
        parent.layer.open({
            title: '审批确认',
            type: 0,
            //skin: 'layui-layer-rim', //加上边框
            area: ['250px', '205px'], //宽高,
            btn:['确定','取消'],
            content: '<div style="padding: 20px;">是否同意该审批? </div>',
            yes: function(index, workflowId){
                //按钮【按钮一】的回调
                //     console.log('成功');
                var ajax = new $ax(Feng.ctxPath + "/workflowStep/shenpi/apprNormalflow"+parms, function(data){
                    if(data.code == "200"){
                        Feng.success(data.message);
                        $(agreeBtn).parents(".detail-view").prev().children('td').eq(7).text('是');
                        $('#ShenpiStepTable').trigger('click-row.bs.table');

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
                    url: Feng.ctxPath + "/workflowStep/shenpi/unApprNum",
                    success: function(data){
                        $("#noShenPiNum").html(data);
                    },
                    error: function(){
                        //请求失败处理函数
                    }
                });

            }
        });


    }



};
var shouwenThis;
// 审批收文审批
ShenpiStep.apprShowWenflow = function (workflowId,step,type,advice,agreeBtn) {
    shouwenThis = agreeBtn;
    // alert("审批收文审批");
    var index = layer.open({
        type: 2,
        title: '审批确认',
        area: ['30%', '60%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + "/workflowStep/sw_fenpei_user?workflowId="+workflowId+"&step="+step+""
    });
    this.layerIndex = index;
    //$(shouwenThis).parents(".detail-view").prev().children('td').eq(7).text('是');
    /*parent.layer.open({
        title: '审批确认',
        type: 0,
        //skin: 'layui-layer-rim', //加上边框
        area: ['30%', '40%'], //宽高,
        btn:['确定','取消'],
        content: ShenpiStep.fenPeiPerson,
        yes: function(index, workflowId){
            //按钮【按钮一】的回调
            //     console.log('成功');
            var ajax = new $ax(Feng.ctxPath + "/workflowStep/shenpi/apprNormalflow"+parms, function(data){
                if(data.code == "200"){
                    Feng.success(data.message);
                    $(agreeBtn).parents(".detail-view").prev().children('td').eq(7).text('是');
                    $('#ShenpiStepTable').trigger('click-row.bs.table');

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
                url: Feng.ctxPath + "/workflowStep/shenpi/unApprNum",
                success: function(data){
                    $("#noShenPiNum").html(data);
                },
                error: function(){
                    //请求失败处理函数
                }
            });

        }
    });*/


    // console.log(fenPeiPerson);

};


function showList(step,item,divWrap,spAdvice,type,workflowId) {
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
    if(spAdvice && spAdvice.indexOf("##不同意##") > 0){

    }
    if(spAdvice && spAdvice.indexOf("##0##") > 0){
        var isCanSpFlow = ShenpiStep.flowsDTO.isCanSpFlow;
        var parmsStr = "?workflowId=" + workflowId+"&step="+step+
            "&type=" +type;

        ShenpiStep.spStep = step;
        str+= "<div class='listItemRow'><p class='spyjColor'>审批意见:</p><p class='spyjColor'>是否同意审批?</p></div>";
        if (type == "7" ){//发文只有同意按钮
            /*str+="<div class='sureBtnWrap'><button><a href='"+Feng.ctxPath+"/shenpi/apprNormalflow"+parmsStr+"&spAdvice=2"+"'>同意</a></button></div>"*/
            if (isCanSpFlow == "true"){
                str+="<div class='sureBtnWrap'><button onclick='ShenpiStep.apprNormalflow("+workflowId+","+step+","+type+",2,this)'>同意</button></div>"
            }
        }else if (type == "8"){// 收文只有同意按钮
            str+="<div class='sureBtnWrap'><button onclick='ShenpiStep.apprShowWenflow("+workflowId+","+step+","+type+",2,this)'>同意</button></div>"
        }else {// 其他有两按钮
            /*str+="<div class='sureBtnWrap'><button><a href='"+Feng.ctxPath+"/shenpi/apprNormalflow"+parmsStr+"&spAdvice=1"+"'>不同意</button></a></div>"
             str+="<div class='sureBtnWrap'><button><a href='"+Feng.ctxPath+"/shenpi/apprNormalflow"+parmsStr+"&spAdvice=2"+"'>同意</a></button></div>"*/
            if (isCanSpFlow == "true"){
                str+="<div class='sureBtnWrap'><button onclick='ShenpiStep.apprNormalflow("+workflowId+","+step+","+type+",1)'>不同意</button></div>"
                str+="<div class='sureBtnWrap'><button onclick='ShenpiStep.apprNormalflow("+workflowId+","+step+","+type+",2,this)'>同意</button></div>"
            }
        }
        //type为8的时候添加办结详情


    }
    if(type == "8" && step == "6"){
        str+= "<div class='listItemRow' style='margin-top: 75px;'><div class='spyjColor sureBtnWrap'><button style='width: 100px;' onclick='ShenpiStep.bjDetail("+workflowId+")'>查看办结详情</button></div></div>";

    }

    divWrap.find(".listItem").eq(step-1).html(str);
}

var _row;//备份
var t_element;
function escapFlowsDetal(e, row, $element) {
    var flag = false;
    if (row == null) {
        row = _row;
        flag = true;
    }
    if ($element == null) {
        $element = t_element;
        flag = true;
    }
    var ajax = new $ax(Feng.ctxPath + "/workflowStep/shenpi/detailflows?workflowId="+row.workflowId+"&flowType="+row.type+"&fenleiType=2", function(data){
        if (data.type==8){
            ShenpiStep.flowsList = data.sWFlowDTOs;
            ShenpiStep.flowslistLen = data.sWFlowDTOs.length;
            ShenpiStep.flowsDTO = data;
            ShenpiStep.lineStep = data.lineStep;
            ShenpiStep.lineType = data.lineType;
        }else{
            ShenpiStep.flowsList = data.normalFlowDTOs;
            ShenpiStep.flowslistLen = data.normalFlowDTOs.length;
            ShenpiStep.flowsDTO = data;
            ShenpiStep.qjTime = data.qJTimePropDTO;
            ShenpiStep.lineStep = data.lineStep;
            ShenpiStep.lineType = data.lineType;
        }

    },function(data){
    });
    ajax.start();
    var list = ShenpiStep.flowsList;
    var listLen = ShenpiStep.flowslistLen;
    var type = ShenpiStep.flowsDTO.type;
    var  index1= $element.data('index'); //获取当前行的id
    if (!flag) {
        if($element.next().is('tr.detail-view')){
            closeRow();
        }
        else {
            closeRow();
        //hide
        $("#ShenpiStepTable").on("expand-row.bs.table",function(index,row,$detail,event){
            // alert("点击2");
            $(event).parent().children("td").html("");
            var divWrap = $("<div class='flyOpenTr clearfix' style='width:100%;' id="+$detail.workflowId+"><div id='qjsj'></div><div class='oaData'></div></div>");
            var qingjiaSJStr="";
            if(ShenpiStep.flowsDTO.type == "1"){
                var qjDTO = ShenpiStep.qjTime;
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
            if(ShenpiStep.flowsDTO.type != "1"){
                divWrap.find("#qjsj").css({"margin-left":"0px","margin-right":"30px"});
            }
            var lineBar = $("<div class='lineBar'></div>");
            // .appendChild(lineBar);
            for(var i=1;i<=listLen;i++){
                if(i==1){
                    lineBar.append("<span class='circle'></span>");
                    continue;
                }
                if (i > 1   && i < ShenpiStep.lineStep){
                    lineBar.append("<span class='notShenpi flyLine'></span><span class='circle'></span>");
                    continue;
                }
                if (i == ShenpiStep.lineStep){
                    if(ShenpiStep.lineType == '同意'){
                        lineBar.append("<span class='notShenpi flyLine'></span><span class='circle'></span>");
                        continue;
                    }
                     if(ShenpiStep.lineType == '不同意'){
                        lineBar.append("<span class='notShenpi yellow'></span><span class='circle yellow'></span>");
                         continue;
                    }
                     if(ShenpiStep.lineType == '待审批'){
                        lineBar.append("<span class='notShenpi '></span><span class='circle  waitShenpi'></span>");
                         continue;
                    }
                }
                if (i > ShenpiStep.lineStep && i <= listLen){
                        lineBar.append("<span class='notShenpi '></span><span class='circle  notShenpiCircle'></span>");
                }


            }
            // $(".oaData").append(lineBar)
            divWrap.find(".oaData").append(lineBar);
            for(var i=0;i<listLen;i++){
                divWrap.find(".oaData").append("<div class='listItem'></div>");
            }
            for(var i=0;i<listLen;i++){
                showList(list[i].step,list[i],divWrap,list[i].spAdvice,type,$detail.workflowId);
            }
            $(event).parent().children("td").html(divWrap);
            // $('#ShenpiStepTable').bootstrapTable('expandRow', index1);
        });
        $('#ShenpiStepTable').bootstrapTable('expandRow', index1);
        }


    } else {
        closeRow();
        $("#ShenpiStepTable").on("expand-row.bs.table",function(index,row,$detail,event){
            $(event).parent().children("td").html("");
            var divWrap = $("<div class='flyOpenTr clearfix' style='width:100%;' id="+$detail.workflowId+"><div id='qjsj'></div><div class='oaData'></div></div>");
            var qingjiaSJStr="";
            if(ShenpiStep.flowsDTO.type == "1"){
                var qjDTO = ShenpiStep.qjTime;
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
            if(ShenpiStep.flowsDTO.type != "1"){
                divWrap.find("#qjsj").css({"margin-left":"0px","margin-right":"30px"});
            }
            var lineBar = $("<div class='lineBar'></div>");
            // .appendChild(lineBar);
            for(var i=1;i<=listLen;i++){
                if(i==1){
                    lineBar.append("<span class='circle'></span>");
                    continue;
                }
                if (i > 1   && i < ShenpiStep.lineStep){
                    lineBar.append("<span class='notShenpi flyLine'></span><span class='circle'></span>");
                    continue;
                }
                if (i == ShenpiStep.lineStep){
                    if(ShenpiStep.lineType == '同意'){
                        lineBar.append("<span class='notShenpi flyLine'></span><span class='circle'></span>");
                        continue;
                    }
                    if(ShenpiStep.lineType == '不同意'){
                        lineBar.append("<span class='notShenpi yellow'></span><span class='circle yellow'></span>");
                        continue;
                    }
                    if(ShenpiStep.lineType == '待审批'){
                        lineBar.append("<span class='notShenpi '></span><span class='circle  waitShenpi'></span>");
                        continue;
                    }
                }
                if (i > ShenpiStep.lineStep && i <= listLen){
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
         $('#ShenpiStepTable').bootstrapTable('expandRow', index1);
    }
}

function closeRow() {
    for (var i=0; i < 20; i ++) {
        $('#ShenpiStepTable').bootstrapTable('collapseRow', i);
    }
}
//查看办结详情
ShenpiStep.bjDetail = function(workflowId){
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
    //显示我未审批的数量
    $.ajax({
        type: 'GET',
        url: Feng.ctxPath + "/workflowStep/shenpi/unApprNum",
        success: function(data){
            $("#noShenPiNum").html(data);
            if($("#noShenPiNum").html() != 0){//有未审批的，只显示未审批的，选中否
                $("#isApprShenpi").val(0);
                ShenpiStep.search();
            }
        },
        error: function(){
            //请求失败处理函数
        }
    });
    var defaultColunms = ShenpiStep.initColumn();
    var table = new BSTableCC(ShenpiStep.id, "/workflowStep/shenpi/list", defaultColunms);
    table.setPaginationType("server");
    ShenpiStep.table = table.init();
/*    var list = [
        {
            step: 2,
            fqks: "发起科室##二科##2",
            fquser: "审批科长##王小二##1",
            spyj: "审批意见##同意##3",
            fqsj: "发起时间##2017/08/10 14:23:45##4",
        },
        {
            step: 1,
            fqks: "发起科室##一科##3",
            fquser: "发起科员##王小一##1",
            fqsj: "发起时间##2017/08/10 14:23:45##4",
        }
        ,
        {
            step: 3,
            fqks: "发起科室##三科##2",
            fquser: "发起科员##王小三##1",
            fqsj: "发起时间##2017/08/20 14:23:15##3",
        },
        {
            step: 4,
            fqks: "发起科室##——##2",
            fquser: "发起科员##王小四##1",
            fqsj: "",
            spAdvice:"审批意见##0##3"
        },
        {
            step: 5,
            fqks: "发起科室##——##2",
            fquser: "发起科员##王小四##1",
            fqsj: "发起时间##2017/08/20 14:23:15##3",
        },
        {
            step: 6,
            fqks: "发起科室##——##2",
            fquser: "发起科员##王小00000##1",
            fqsj: "发起时间##——##3",
        }
    ];
    var listLen = list.length;
*/

//11111
    $('#ShenpiStepTable').on('click-row.bs.table', function (e, row, $element) {
        if (row != null && row != "" && row != "undefined") {
            _row = row;
        }
        if ($element != null && $element != "" && $element != "undefined") {
            t_element = $element;
        }
        escapFlowsDetal(e, row, $element);

    });

    $("#shenpiFaqiTimeB,#shenpiFaqiTimeE").focus(function(){
        timeBetween($("#shenpiFaqiTimeB"),$("#shenpiFaqiTimeE"));
    });
    // 审批时间
    $("#shenpiApprTimeB,#shenpiApprTimeE").focus(function(){
        timeBetween($("#shenpiApprTimeB"),$("#shenpiApprTimeE"));
    });

    $("#ShenpiStepTable").on("post-body.bs.table",function () {
        changeType("#ShenpiStepTable",["1","2","3","4","5","6","7","8"],["请假审批","销假审批","出差审批","接待审批","公务用车审批","驻点人员请假审批","发文审批","收文审批"],2);
        changeType("#ShenpiStepTable",["0","1"],["否","是"],7);
    });
});
