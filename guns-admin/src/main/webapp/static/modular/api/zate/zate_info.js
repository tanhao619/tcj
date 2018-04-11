/**
 * 初始化载体资源管理详情对话框
 */
var ZateInfoDlg = {
    zateInfoData : {},
    zateInfoData2:{},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '载体资源名称不能为空'
                }
            }
        },
        name2: {
            validators: {
                notEmpty: {
                    message: '载体资源名称不能为空'
                }
            }
        },
        address: {
            validators: {
                notEmpty: {
                    message: '资源地点不能为空'
                }
            }
        },
        address2: {
            validators: {
                notEmpty: {
                    message: '资源地点不能为空'
                }
            }
        },
        // fillTel:{
        //     validators: {
        //         regexp:{
        //             regexp:/(^1[34578]\d{9}$)$|(\d{3}-\d{8}|\d{4}-\d{7})/,
        //             message:"手机号码格式不正确"
        //         }
        //     }
        // },
        area:{
            validators: {
                numeric: {
                    message: '请输入有效的数值，允许小数'
                },
            }
        },
        area2:{
            validators: {
                numeric: {
                    message: '请输入有效的数值，允许小数'
                },
            }
        },
        induPosition:{
            container:"#induPosition_error",
            validators:{
                stringLength: {
                    max:100,
                    message: '最多只能输入100个字'
                }
            }
        },situation:{
            container:"#situation_error",
            validators:{
                stringLength: {
                    max:200,
                    message: '最多只能输入200个字'
                }
            }
        },condition:{
            container:"#condition_error",
            validators:{
                stringLength: {
                    max:200,
                    message: '最多只能输入200个字'
                }
            }
        },condition2:{
            container:"#condition2_error",
            validators:{
                stringLength: {
                    max:500,
                    message: '最多只能输入500个字'
                }
            }
        },remark:{
            container:"#remark_error",
            validators:{
                stringLength: {
                    max:100,
                    message: '最多只能输入100个字'
                }
            }
        },remark2:{
            container:"#remark2_error",
            validators:{
                stringLength: {
                    max:100,
                    message: '最多只能输入100个字'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
ZateInfoDlg.clearData = function() {
    this.zateInfoData = {};
    this.zateInfoData2={};
}
/**
 * 鼠标移到单选框显示产业
 */
$(".helpImg").hover(function () {
    $(this).next().css("display","block");
},function () {
    $(this).next().css("display","none");
});
/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ZateInfoDlg.set = function(key, val) {
    this.zateInfoData[key] = (typeof val == "undefined") ? $.trim($("#" + key).val()) : $.trim(val);
    return this;
}
ZateInfoDlg.set2 = function(key, val) {
    this.zateInfoData2[key] = (typeof val == "undefined") ? $.trim($("#" + key+"2").val()) : $.trim(val);
    return this;
}

timeSingle($("#fillTime"),"YYYY-MM-DD");
timeSingle($("#fillTime2"),"YYYY-MM-DD");

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ZateInfoDlg.get = function(key) {
    return $("#" + key).val();
}
ZateInfoDlg.get2 = function(key) {
    return $("#" + key).val();
}
/**
 * 关闭此对话框
 */
ZateInfoDlg.close = function() {
    // parent.layer.close(window.parent.Zate.layerIndex);
    location.href=Feng.ctxPath + '/zate';

}

/**
 * 收集数据
 */
ZateInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('name').set('address').set('area').set('property').set('status').set('unit').set('fillTime').set('fillContacter').set('fillTel');
    this.zateInfoData.induPosition = $("#induPosition").val() != null? $("#induPosition").val() :"";
    this.zateInfoData.situation = $("#situation").val() != null? $("#situation").val() :"";
    this.zateInfoData.condition = $("#condition").val() != null? $("#condition").val() :"";
    this.zateInfoData.remark = $("#remark").val() != null? $("#remark").val() :"";
    var landPropertys="";
    if ($(".landProperty:checked").val() != null && $(".landProperty:checked").val() != "") {
        $(".landProperty:checked").each(function (index, element) {
            landPropertys+=$(this).val()+",";
        })
    }
    this.zateInfoData.landPropertys=landPropertys;

};
ZateInfoDlg.collectData2 = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set2('name').set2('address').set2('area').set2('status').set2('unit').set2('fillTime').set2('fillContacter').set2('fillTel')
        .set2('owne').set2('owneContacter').set2('owneTel');
    this.zateInfoData2.condition = $("#condition2").val() != null ? $("#condition2").val():"";
    this.zateInfoData2.remark = $("#remark2").val() != null ? $("#remark2").val():"";
    // this.zateInfoData2.induType="";
    var induTypList="";
    if ($(".induType:checked").val() != null && $(".induType:checked").val() != "") {
        $(".induType:checked").each(function (index, element) {
            induTypList+=$(this).val()+",";
        })
    }
    this.zateInfoData2.induType=induTypList;
};

/**
 * 验证数据是否为空
 */
ZateInfoDlg.validateLand = function () {
    $('#zateLandInfoForm').data("bootstrapValidator").resetForm();
    $('#zateLandInfoForm').bootstrapValidator('validate');
    return $("#zateLandInfoForm").data('bootstrapValidator').isValid();
};
ZateInfoDlg.validateBuilding = function () {
    $('#zateBuildingInfoForm').data("bootstrapValidator").resetForm();
    $('#zateBuildingInfoForm').bootstrapValidator('validate');
    return $("#zateBuildingInfoForm").data('bootstrapValidator').isValid();
};
ZateInfoDlg.validate = function () {
    $('#zateInfoForm').data("bootstrapValidator").resetForm();
    $('#zateInfoForm').bootstrapValidator('validate');
    return $("#zateInfoForm").data('bootstrapValidator').isValid();
};
/**
 * 提交添加土地
 */
var uploadFile=new UploadFile("#picker","#thelist","/zateLand/upload");
var error="<span style='color: red;font-weight: 100;' id='err'>填报时间不能为空</span>";
ZateInfoDlg.addLandSubmit = function() {
    this.clearData();
    this.collectData();
    $("#err").remove();
    //验证
    if (!this.validateLand()) {
        if(this.zateInfoData.fillTime==""||this.zateInfoData.fillTime==null){
            $("#err").remove();
            $("#fillTime").after(error);
        }
        return;
    }
        if(this.validateLand()){
            if(this.zateInfoData.fillTime==""||this.zateInfoData.fillTime==null){
                $("#err").remove();
                $("#fillTime").after(error);
                return;
            }
        }
        //图片封装
        var landImgs = escapFileArr(uploadFile.listArry);
        if (landImgs != undefined && landImgs.length > 0){
            //数组对象转成 jsonStr
            this.zateInfoData.zateImgs = JSON.stringify(landImgs);
        }
    //提交信息
        var ajax = new $ax(Feng.ctxPath + "/zateLand/add", function(data){
            if(data.code==200){
                Feng.success("添加成功!");
                ZateInfoDlg.close();
            }else{
                Feng.error("添加失败," + data.message + "!");
            }

        });
        ajax.set(this.zateInfoData);
    ajax.start();
};
/**
 * 提交添加楼宇
 */
ZateInfoDlg.addBuildingSubmit = function() {
    this.clearData();
    this.collectData2();
    console.log(this.zateInfoData2)
    $("#err").remove();
    //验证
    if (!this.validateBuilding()) {
        if(this.zateInfoData2.fillTime==""||this.zateInfoData2.fillTime==null){
            $("#err").remove();
            $("#fillTime2").after(error);
        }
        return;
    }
        if(this.validateBuilding()){
            if(this.zateInfoData2.fillTime==""||this.zateInfoData2.fillTime==null){
                $("#err").remove();
                $("#fillTime2").after(error);
                return;
            }
        }
    //提交信息
        var ajax = new $ax(Feng.ctxPath + "/zateBuilding/add", function(data){
            if(data.code==200){
                Feng.success("添加成功!");
                ZateInfoDlg.close();
            }else{
                Feng.error("添加失败," + data.message + "!");
            }
        });
        ajax.set(this.zateInfoData2);
    ajax.start();
};

/**
 * 点击修改
 */
/*切换修改与确认*/
$("#change").on("click",function () {
    $("input").removeAttr("disabled");
    $("textarea").removeAttr("disabled");
    $("select").removeAttr("disabled");
    $("#change").css("display","none");$("#ensure").css("display","block");
//给图片添加删除
    $(".J_menuItem").each(function () {
        var del="<img src='/static/img/icon_delete_common1.png' class='formCtrl' id='handleDel' onclick='del(this)'>"
        $(this).after(del);
    });
    $(".radioItem").removeClass("li-disable");
    $("#picker").removeClass("div-disable");
    /*显示撤回按钮*/
   /* var delIcon="<img src='/static/img/icon_undo_common.png'/>";
    $(".inputWidth").append(delIcon);
    $("textarea").append(delIcon);*/
});
//修改土地页面删除图片
function del(a) {
    $(a).parent().remove();
}

/**
 * 提交修改
 */
ZateInfoDlg.editSubmit = function() {
    this.clearData();
    this.collectData();
    this.collectData2();
    //验证
    $("#err").remove();
    if (!this.validate()) {
        if(this.zateInfoData.fillTime==""||this.zateInfoData.fillTime==null){
            $("#err").remove();
            $("#fillTime").after(error);
        }
        if(this.zateInfoData2.fillTime==""||this.zateInfoData2.fillTime==null){
            $("#err").remove();
            $("#fillTime2").after(error);
        }
        return;
    }
    //提交信息
    if(this.zateInfoData.name!=null&&this.zateInfoData.name!=""){
        if(this.validate()){
            if(this.zateInfoData.fillTime==""||this.zateInfoData.fillTime==null){
                $("#err").remove();
                $("#fillTime").after(error);
                return;
            }
        }
        this.zateInfoData.id=$("#id").val();
        var landImgs = escapFileArr(uploadFile.listArry);
        $(".J_menuItem").each(function () {
            olduploadObj={id:$(this).find("input").val(),name:$(this).find("span").html(),url:$(this).attr("url")};
            landImgs.push(olduploadObj);
        });
        if (landImgs != undefined && landImgs.length > 0){
            //数组对象转成 jsonStr
            this.zateInfoData.zateImgs = JSON.stringify(landImgs);
        }

        var ajax = new $ax(Feng.ctxPath + "/zateLand/update", function(data){
            if(data.code == 200){
                Feng.success("修改成功!");
                //刷新详情页面
                location.href=Feng.ctxPath + '/zateLand/zateLand_update/' +$("#id").val() + "/"+ -1;
            }else{
                Feng.error("修改失败," + data.message + "!");
            }
        });
        ajax.set(this.zateInfoData);
    }else if(this.zateInfoData2.name!=null&&this.zateInfoData2.name!=""){
        if(this.validate()){
            if(this.zateInfoData2.fillTime==""||this.zateInfoData2.fillTime==null){
                $("#err").remove();
                $("#fillTime2").after(error);
                return;
            }
        }
        this.zateInfoData2.id=$("#id").val();
        var ajax = new $ax(Feng.ctxPath + "/zateBuilding/update", function(data){
            if(data.code == 200){
                Feng.success("修改成功!");
                //刷新详情页面
                location.href=Feng.ctxPath + '/zateBuilding/zateBuilding_update/' +$("#id").val() + "/" + -1;
            }else{
                Feng.error("修改失败," + data.message + "!");
            }
        });
        ajax.set(this.zateInfoData2);
    }
    ajax.start();
};
function initForm() {
    $("#name").val("");
    $("#address").val("");
    $("#area").val("");
    $("#unit").val("");
    $("#fillTime").val("");
    $("#fillContacter").val("");
    $("#fillTel").val("");
    $("#induPosition").val("");
    $("#situation").val("");
    $("#condition").val("");
    $("#remark").val("");

    $("#name2").val("");
    $("#address2").val("");
    $("#area2").val("");
    $("#unit2").val("");
    $("#fillTime2").val("");
    $("#fillContacter2").val("");
    $("#fillTel2").val("");
    $("#owne").val("");
    $("#owneContacter").val("");
    $("#owneTel").val("");
    $("#condition2").val("");
    $("#remark2").val("");
    $(".induType").prop("checked",false);
    $(".landProperty").prop("checked",false);
}
var ZateLog = {
    id: "ZateLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    delIds:[]           // 批量删除
};
//初始化日志信息列表
ZateLog.initColumn = function () {
    return [
        {title: '历史操作', field: 'content', visible: true, align: 'center', valign: 'middle'},
        {title: '操作人员', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '操作时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];

};
//打开日志详情弹框
ZateLog.openZateLog = function (pid) {
        var index = layer.open({
            type: 2,
            title: '载体资源变更详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/zate/history/detail/' + pid
});
        this.layerIndex = index;
};
/*添加必填图标*/
function MustIcon() {
var icons="<img src='/static/img/icon_must1.png'/>";
    $("#name").parents(".form-group").find("label").prepend(icons);
    $("#name2").parents(".form-group").find("label").prepend(icons);
    $("#address").parents(".form-group").find("label").prepend(icons);
    $("#address2").parents(".form-group").find("label").prepend(icons);
    $(".fillTime span").prepend(icons);
}

$(function() {
    //增加必填图标
    MustIcon();
    dateFormatYMD($("#fillTime")); //可以同时传入多个id
    dateFormatYMD($("#fillTime2"));
    Feng.initValidator("zateLandInfoForm", ZateInfoDlg.validateFields);
    Feng.initValidator("zateBuildingInfoForm", ZateInfoDlg.validateFields);
    Feng.initValidator("zateInfoForm", ZateInfoDlg.validateFields);

    $(".J_menuItem").each(function () {
        $(this).attr("href",Feng.fileSer +$(this).attr("url"))
    });

    //详情页默认不能选
    $(".radioItem").addClass("li-disable");
    $("#picker").addClass("div-disable");
//  给选中的产业类别加样式
    $(".radioItem").click(function(){


        if($(this).children("input").prop("checked") == true){
            $(this).children("input").prop("checked",false);
            $(this).removeClass("active");
        }else{
            $(this).children("input").prop("checked","checked");
            $(this).addClass("active");
        }
        // $(this).siblings().children("input").removeAttr("checked");
    });
    $(".induType").click(function(){
        if($(this).prop("checked") == true){
            $(this).prop("checked",false);
            $(this).parent.removeClass("active");
        }else{
            $(this).prop("checked","checked");
            $(this).parent.addClass("active");
        }
    });
    $(".landProperty").click(function(){
        if($(this).prop("checked") == true){
            $(this).prop("checked",false);
            $(this).parent.removeClass("active");
        }else{
            $(this).prop("checked","checked");
            $(this).parent.addClass("active");
        }
    });
    //加载日志信息列表
    var defaultColunms = ZateLog.initColumn();
    var type=$("#type").val();
    if(type == 1){
        var table = new BSTablePG(ZateLog.id, '/zateLand/hisSpeciList/'+$("#id").val(), defaultColunms,5);
    }
    if(type == 2){
        var table = new BSTablePG(ZateLog.id, '/zateBuilding/hisSpeciList/'+$("#id").val(), defaultColunms,5);
    }
    table.setPaginationType("server");
    ZateLog.table = table.init();

   //点击查看日志详情，弹窗
    $('#ZateLogTable').on('click-row.bs.table', function (e, row, element) {
        var pid = row.id;
        ZateLog.openZateLog(pid);
    });

});



