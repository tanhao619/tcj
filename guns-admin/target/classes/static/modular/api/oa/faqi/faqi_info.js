/**
 * 初始化OA管理详情对话框
 */
var WorkflowInfoDlg = {
    workflowInfoData : {}
};
/**
 * 附件最多上传5个
 */
/*上传*/
function UploadFileFaQi(pick,list,url) {
    this.pick=pick; //选择文件按钮
    this.list=list; //存储上传成功文件的div
    this.url=url;  //地址
    this.listArry=[];
    this.init();
    this.webUploader;
}
UploadFileFaQi.prototype={
    init:function () {
        var uploader = this.create();
        this.bindEvent(uploader);
        return uploader;
    },
    create:function () {
        this.webUploader = WebUploader.create({
            auto:true,
            // swf文件路径
            swf: Feng.ctxPath
            + '/static/css/plugins/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: Feng.ctxPath + this.url,
            pick: this.pick,
            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false
        });
        return this.webUploader;
    },
    bindEvent:function (bindedObj) {
        var me =  this;
        bindedObj.on( 'fileQueued', function( file ) {
            var $list=$(me.list);
            $list.append( '<div id="' + file.id + '" class="item">' +
                '<h4 class="info">' + file.name + '</h4>' +
                '<p class="state">等待上传...</p>' +
                '</div>');
        });
        // 文件上传过程中创建进度条实时显示。
        bindedObj.on( 'uploadProgress', function( file, percentage ) {
            var $li = $( '#'+file.id ),
                $percent = $li.find('.progress .progress-bar');
            // 避免重复创建
            if ( !$percent.length ) {
                $percent = $('<div class="progress progress-striped active">' +
                    '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                    '</div>' +
                    '</div>').appendTo( $li ).find('.progress-bar');
            }
            $li.find('p.state').text('上传中');
            $percent.css( 'width', percentage * 100 + '%' );
        });
        bindedObj.on( 'uploadSuccess', function( file,data ) {
            $( '#'+file.id ).find('p.state').css("display","none");
            $( '#'+file.id ).find('h4.info').css("display","none");
            $( '#'+file.id ).append('<div id="#handle' + file.id + '" class="handleItem">' +
                '<a target="_blank"  id="fileInfo">' + file.name + '</a>' +
                /*'<span id="handleDel">删除</span>' +*/
                '<img src="/static/img/icon_delete_hover.png" class="formCtrl" id="handleDel">' +
                '</div>' );
            $($( '#'+file.id + " #fileInfo")).attr("href",Feng.fileSer + data.url);
            var uploadObj={id:file.id,name:data.originalName,url:data.url};
            me.listArry.push(uploadObj);
            if(me.listArry.length >=5){
                $("#picker").addClass("faqi-disable");
            }
        });
        bindedObj.on( 'uploadError', function( file ) {
            $( '#'+file.id ).find('p.state').text('上传出错');
        });
        /*不管上传成功还是失败都会触发uploadComplete事件*/
        bindedObj.on( 'uploadComplete', function( file ) {
            $( '#'+file.id ).find('.progress').fadeOut();
        });
        /*删除*/
        $("#thelist").on("click","#handleDel",function () {
            var delFile=$(this).parent().parent();
            $.each(me.listArry,function (index,item) {
                if(this.id==delFile.prop("id")){
                    me.listArry.splice(index,1);
                }
            });
            bindedObj.removeFile(delFile.prop("id"),true );
            delFile.remove(); //删除页面展示
            if(me.listArry.length < 5){
                $("#picker").removeClass("faqi-disable");
            }
        });
    }
};
// 封装返回上传文件信息数组
function escapFileArrFaQi(arr){
    var reArr = [];
    if (arr != undefined && arr.length > 0){
        for(var i =0;i < arr.length;i++){
            var reObj = {"name":arr[i].name,"url":arr[i].url};
            reArr.push(reObj);
        }
    }
    return reArr;
}
/**
 * 清除数据
 */
WorkflowInfoDlg.clearData = function() {
    this.workflowInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkflowInfoDlg.set = function(key, val) {
    this.workflowInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkflowInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WorkflowInfoDlg.close = function() {
    parent.layer.close(window.parent.Workflow.layerIndex);
}

/**
 * 收集数据
 */
WorkflowInfoDlg.collectData = function() {
    // 添加自己需要新增的字段信息，与新增页面的input框的id相匹配,如 this.set('id').set('name').set()..
    this.set('ordnumSW').set('typeFQ').set('qjStart').set('qjEnd');
    this.workflowInfoData.roleIdFQ=$(".role:checked").val();//审批角色
};
/**
 * 点击提交验证各项是否为空
 */
var error1="<span style='color: red'>请选择审批类型！</span>";
var error2="<span style='color: red'>请选择审批角色！</span>";
var error3="<span style='color: red'>请上传附件！</span>";
var error4="<span style='color: red'>请填写请假时间！</span>";
var error5="<span style='color: red'>请填写收文编号！</span>";
WorkflowInfoDlg.validate=function(){
        $("#error1").empty();
        $("#error5").empty();
        $("#error4").empty();
        $("#error2").empty();
        $("#error3").empty();
        var bool=true;
        //审批类型
        if($("#typeFQ").val() == ""){
           $("#error1").html(error1);
            bool=false;
        }
        //收文，编号
        if($("#typeFQ").val() == "8" && $.trim($("#ordnumSW").val()) == ""){
            $("#error5").html(error5);
            bool=false;
        }
        //请假，时间
        if($("#typeFQ").val() == "1" && $("#qjStart").val() == "" && $("#qjEnd").val() == ""){
            $("#error4").html(error4);
            bool=false;
        }
        //审批角色
        if($(".role:checked").length == 0){
            $("#error2").html(error2);
            bool=false;
        }
        //附件
        if(escapFileArr(uploadFile.listArry) == undefined || escapFileArr(uploadFile.listArry).length <= 0){
            $("#error3").html(error3);
            bool=false;
        }
        return bool;
};

var uploadFile=new UploadFileFaQi("#picker","#thelist","/workflow/upload");
/**
 * 点击提交
 */
WorkflowInfoDlg.addSubmit=function () {
    this.clearData();
    this.collectData();
    //附件封装
    var attachments = escapFileArrFaQi(uploadFile.listArry);
    if (attachments != undefined && attachments.length > 0){
        //数组对象转成 jsonStr
        this.workflowInfoData.attachments = JSON.stringify(attachments);
    }
    //验证
    if(!this.validate()){
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workflow/add", function(data){
        if(data.code==200){
            Feng.success("添加成功!");
            window.parent.Workflow.table.refresh();
            WorkflowInfoDlg.close();
        }else{
            Feng.error("添加失败," + data.message + "!");
        }
    });
    ajax.set(this.workflowInfoData);
    ajax.start();
};

$(function() {
    $("#shouwen").css("display","none");
    $("#qjTime").css("display","none");
    $("#typeFQ").change(function () {
        //发文审批只能是科员
        //收文审批只能是办公室科员
        var typeValue=$(this).val();
        $.ajax({
            type: 'GET',
            url: Feng.ctxPath + "/workflow/apprRole",
            data: {type:typeValue},
            success: function(data){
                $("#roles").empty();
                for(var i=0;i<data.length;i++){
                    var SPMan="<input type='radio' value="+data[i].id+" name='shenpiRole' class='role'>"+data[i].name;
                    $("#roles").append(SPMan);
                }
            },
            error: function(){
                //请求失败处理函数
            }
        });
        if($(this).val() == '8'){
            //收文编号显示
            $("#shouwen").css("display","block");
        }else {
            $("#shouwen").css("display","none");
        }
        if($(this).val() == '1'){
            //显示请假时间
            $("#qjTime").css("display","block");
        }else {
            $("#qjTime").css("display","none");
        }
        //改变后重置以前填的数据
        $("#ordnumSW").val("");
        $(".role").attr("checked",false);
        $("#qjStart").val("");
        $("#qjEnd").val("");
        $("#thelist").empty();
        uploadFile.listArry=[];
    });
});
