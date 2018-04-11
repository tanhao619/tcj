/**
 * 初始化OA管理详情对话框
 */
var WorkflowStepInfoDlg = {
    workflowStepInfoData : {}
};

/**
 * 清除数据
 */
WorkflowStepInfoDlg.clearData = function() {
    this.workflowStepInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkflowStepInfoDlg.set = function(key, val) {
    this.workflowStepInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkflowStepInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WorkflowStepInfoDlg.close = function() {
    parent.layer.close(window.parent.ShenpiStep.layerIndex);
}

/**
 * 收集数据
 */
WorkflowStepInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('id');
}

/**
 * 提交添加
 */
WorkflowStepInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workflowStep/add", function(data){
        Feng.success("添加成功!");
        window.parent.WorkflowStep.table.refresh();
        WorkflowStepInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workflowStepInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
WorkflowStepInfoDlg.editSubmit = function() {
    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workflowStep/update", function(data){
        Feng.success("修改成功!");
        window.parent.WorkflowStep.table.refresh();
        WorkflowStepInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workflowStepInfoData);
    ajax.start();
}
//一般审批
WorkflowStepInfoDlg.commonSubmitSure = function(){

    var datacomList = [];
    var SWFPworkflowId = $("#SWFPworkflowId").val();
    var SWFPstep = $("#SWFPstep").val();
    var step2Type = "";
    $('input[type="checkbox"]:checked').each(function(){
        datacomList.push({"userId":$(this).val(),"deptId":$(this).siblings("#deptId").val(),"userName":$(this).siblings("span").text()});
    });
   if(datacomList.length <= 0){
       Feng.error("至少选择一个角色");
   }
   else{
       $.ajax({
           type: 'GET',
           url: Feng.ctxPath + "/workflowStep/shenpi/apprShouWenflow",
           data: {'userFPDTOs':JSON.stringify(datacomList),'workflowId': SWFPworkflowId, "step": SWFPstep,"step2Type":step2Type},
           async: false,//同步加载
           success: function (data) {
               if (data.code == "400"){
                   Feng.error(data.message);
                   return;
               }
               var btn = window.parent.shouwenThis;
               $(btn).parents(".detail-view").prev().children('td').eq(7).text('是');
           },
           error: function () {
               //请求失败处理函数
           }
       });
       window.parent.$('#ShenpiStepTable').trigger('click-row.bs.table');
       WorkflowStepInfoDlg.close();
   }

}
//局长审批提交
WorkflowStepInfoDlg.jzSubmitSure = function(){
    var datajzList = [];
    var SWFPworkflowId = $("#SWFPworkflowId").val();
    var SWFPstep = $("#SWFPstep").val();
    $('input[type="radio"]:checked').each(function(){
        datajzList.push({"userId":$(this).val(),"deptId":$(this).siblings("#deptId").val(),"userName":$(this).siblings("span").text()});
    });
    if(datajzList.length <= 0){
        Feng.error("至少选择一个角色");
    }
    else{
        $.ajax({
            type: 'GET',
            url: Feng.ctxPath + "/workflowStep/shenpi/apprShouWenflow",
            data: {'userFPDTOs':JSON.stringify(datajzList),'workflowId': SWFPworkflowId, "step": SWFPstep},
            async: false,//同步加载
            success: function (data) {
                var btn = window.parent.shouwenThis;
                $(btn).parents(".detail-view").prev().children('td').eq(7).text('是');
            },
            error: function () {
                //请求失败处理函数
            }
        });
        window.parent.$('#ShenpiStepTable').trigger('click-row.bs.table');
        WorkflowStepInfoDlg.close();
    }


};
//副局长审批提交
WorkflowStepInfoDlg.fjzSubmitSure = function(){
    var datafjzList = [];
    var SWFPworkflowId = $("#SWFPworkflowId").val();
    var SWFPstep = $("#SWFPstep").val();
    var step2Type = "FJZ";
    $('input[type="checkbox"]:checked').each(function(){
        datafjzList.push({"userId":$(this).val(),"deptId":$(this).siblings("#deptId").val(),"userName":$(this).siblings("span").text()});
    });
    if(datafjzList.length <= 0){
        Feng.error("至少选择一个角色");
    }
    else{
        $.ajax({
            type: 'GET',
            url: Feng.ctxPath + "/workflowStep/shenpi/apprShouWenflow",
            data: {'userFPDTOs':JSON.stringify(datafjzList),'workflowId': SWFPworkflowId, "step": SWFPstep,"step2Type":step2Type},
            async: false,//同步加载
            success: function (data) {
                var btn = window.parent.shouwenThis;
                $(btn).parents(".detail-view").prev().children('td').eq(7).text('是');
            },
            error: function () {
                //请求失败处理函数
            }
        });
        window.parent.$('#ShenpiStepTable').trigger('click-row.bs.table');
        WorkflowStepInfoDlg.close();
    }

};
$(function() {
    var workflowId = $("#SWFPworkflowId").val();
    var step =  $("#SWFPstep").val();
    $(".comfen").hide();
    $(".jzAnfjz").hide();
    var fenPeiPerson = "";
    $.ajax({
        type: 'GET',
        url: Feng.ctxPath + "/workflowStep/shenpi/SWfenpeiUsers",
        data: {'workflowId':workflowId,"step":step},
        async:false,//同步加载
        success: function(data){
            // $("#rolesUl").empty();
           var commUser = data.commSWFPUsers;
           var fjzSWFPUsers = data.fjzSWFPUsers;
           var jzSWFPUsers = data.jzSWFPUsers;
            if(data.nowStep != "2"){
                $(".comfen").show();
                for(var i=0;i<commUser.length;i++){
                    var SPMan="<li class='radioItems' onclick='checkboxClick(this)'><input type='hidden' id='deptId' value="+commUser[i].deptId+"><input type='checkbox' value="+commUser[i].userId+"><span>"+commUser[i].userName+"</span></li>";
                    // fenPeiPerson += SPMan;
                    $(".commonUser").append(SPMan);
                }
            }
            if(data.nowStep == "2"){
                $(".jzAnfjz").show();
                for(var i=0;i<fjzSWFPUsers.length;i++){
                    var SPMan="<li class='radioItems' onclick='checkboxClick(this)'><input type='hidden' id='deptId' value="+fjzSWFPUsers[i].deptId+"><input type='checkbox' value="+fjzSWFPUsers[i].userId+"><span>"+fjzSWFPUsers[i].userName+"</span></li>";
                    // fenPeiPerson += SPMan;
                    $(".fjzPerson").append(SPMan);
                }
                for(var i=0;i<jzSWFPUsers.length;i++){
                    var SPMan="<li class='radioItems' onclick='radioClick(this)'><input type='hidden' id='deptId' value="+jzSWFPUsers[i].deptId+"><input type='radio' value="+jzSWFPUsers[i].userId+" name='fpJz'><span>"+jzSWFPUsers[i].userName+"</span></li>";
                    // fenPeiPerson += SPMan;
                    $(".jzPerson").append(SPMan);
                }
            }
            // for(var i=0;i<data.length;i++){
            //     var SPMan="<li class='radioItems'><input type='checkbox' value="+data[i].id+">"+data[i].name+"</li>";
            //     // fenPeiPerson += SPMan;
            //     $(".fenPeiPerson").append(SPMan);
            // }

        },
        error: function(){
            //请求失败处理函数
        }
    });
    //局长或副局长切换的时候清空选中的值
    $(".juzhang").click(function(){
        $('input[type="checkbox"]').each(function(){
            $(this).removeAttr("checked");
            $(this).parents("li").removeClass("active");
        });
    })
    $(".fujuzhang").click(function(){
        $('input[type="radio"]').each(function(){
            $(this).removeAttr("checked");
            $(this).parents("li").removeClass("active");
        });
    })

});
//给审批确认人员加active
function checkboxClick(this1){
    $(this1).toggleClass("active");
    if($(this1).hasClass("active")){
        $(this1).children().prop("checked", "checked");
    }else{
        $(this1).children().removeAttr("checked");
        $(this1).children().css("color","#35a6d6");
    }
}
//给审批确认人员加active
function radioClick(this1){
    $(this1).addClass("active").siblings().removeClass("active");
    $(this1).children("input").prop("checked","checked");
    $(this1).siblings().children("input").removeAttr("checked");
}