/**
 * Created by cdyoue on 2017/11/9.
 */
/*改变table的某列内容*/
function changeType(changeId,oldArry,newArry,num){
    /*
    * changeId:生成table的id
    * oldArry：被替换的内容，数组每一项必须是字符串
    * newArry：替换成的内容，数组每一项必须是字符串
    * num：替换第几列，从0开始数
    * */
    var resourcesChangeTabletr=$(changeId).find("tbody tr");
    resourcesChangeTabletr.each(function () {
        var resourcesChangeTabletrtd=$(this).children("td").eq(num);
        var returnValue=resourcesChangeTabletrtd.text();
        var address=oldArry.indexOf(returnValue);
        resourcesChangeTabletrtd.html(newArry[address]);
    });
}
/*改变table某列内容的使用，必须事件委托*/
/*$("#resourcesChange table").on("post-body.bs.table",function () {
 changeType("#resourcesChangeTable",["1","2"],["土地资源类","楼宇或闲置厂房"],1);
 });*/

//---- 单个时间 ----
function timeSingle(timeId,format) {
    if (format == undefined){
        format = 'YYYY-MM-DD';
    }
    var time = {
        format: format,
        //minDate: $.nowDate({DD: 0}), //设定最小日期为当前日期
        //festival:true,
        //日期 中文 语言设置
        language:{
            name  : "cn",
            month : ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
            weeks : [ "日", "一", "二", "三", "四", "五", "六" ],
            times : ["小时","分钟","秒数"],
            clear : "清空",
            today : "今天",
            yes   : "确定",
            close : "关闭"
        },
        maxDate: '2099-06-16 23:59:59', //最大日期
        choosefun: function (elem, datas) {
            // start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
            // logTable.draw();
        },
        okfun: function () {
            logTable.draw();
        },
        clearfun: function () {
            logTable.draw();
        }
    };

    timeId.jeDate(time);

}

//---- 单个时间 ----

// ----时间段 -----
var endTimeIdT;
var start = {
    format: 'YYYY-MM-DD',
    minDate: '2014-06-16 23:59:59', //设定最小日期为当前日期
    //festival:true,
    maxDate: $.nowDate({DD: 0}), //最大日期
    choosefun: function (elem, datas) {
        end.minDate = datas; //开始日选好后，重置结束日的最小日期
        endDates();
        logTable.draw();
    },
    okfun: function () {
        logTable.draw();
    },
    clearfun: function () {
        logTable.draw();
    }
};
var end = {
    format: 'YYYY-MM-DD',
    minDate: $.nowDate({DD: 0}), //设定最小日期为当前日期
    //festival:true,
    maxDate: '2099-06-16 23:59:59', //最大日期
    choosefun: function (elem, datas) {
        start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
        logTable.draw();
    },
    okfun: function () {
        logTable.draw();
    },
    clearfun: function () {
        start.maxDate =  $.nowDate({DD: 0});
        logTable.draw();

    }
};
function timeBetween(startTimeId,endTimeId) {
    endTimeIdT = endTimeId;
    startTimeId.jeDate(start);
    endTimeId.jeDate(end);

}
function endDates() {
    end.trigger = false;
    endTimeIdT.jeDate(end);
}
// ----时间段结束 -----
//多个时间转换
function dateFormatYMD() {
    for (var i = 0;i < arguments.length; i++ ) {
        if(arguments[i].val()=="" || arguments[i].val()==null){
            return;
        }
        var timeVal = Todate(arguments[i].val(),"YMD");
        if (timeVal != undefined){
            arguments[i].val(timeVal);
        }
    }
}
function dateFormatYMDHMS() {
    for (var i = 0;i < arguments.length; i++ ) {
        if(arguments[i].val()=="" || arguments[i].val()==null){
            return;
        }
        var timeVal = Todate(arguments[i].val(),"YMDHMS");
        if (timeVal != undefined){
            arguments[i].val(timeVal);
        }
    }
}
// ---- 日期格式转换 ----
function Todate(time,type)
{
    // alert(123);
    //Fri Oct 31 18:00:00 UTC+0800 2008
    //num=timeVal+"";
    var date="";
    var month=new Array();
    month["Jan"]=01;month["Feb"]=02;month["Mar"]=03;month["Apr"]=04;month["May"]=05;month["Jun"]=06;
    month["Jul"]=07;month["Aug"]=08;month["Sep"]=09;month["Oct"]=10;month["Nov"]=11;month["Dec"]=12;
    var week=new Array();
    week["Mon"]="一";week["Tue"]="二";week["Wed"]="三";week["Thu"]="四";week["Fri"]="五";week["Sat"]="六";week["Sun"]="日";
    str=time.split(" ");
    date=str[5]+"-";
    if (type === "YMD"){
        if(month[str[1]]<10){
            date=date+"0"+month[str[1]]+"-"+str[2];
        }else{
            date=date+month[str[1]]+"-"+str[2];
        }

    }
    if (type === "YMDHMS"){
        date=date+month[str[1]]+"-"+str[2]+" "+str[3];
    }
    //date=date+" 周"+week[str[0]];
    return date;
}

/*上传*/
function UploadFile(pick,list,url) {
    this.pick=pick; //选择文件按钮
    this.list=list; //存储上传成功文件的div
    this.url=url;  //地址
    this.listArry=[];
    this.init();
    this.webUploader;
}
UploadFile.prototype={
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
                '<a target="_blank"  id="fileInfo" url="' + data.url + '" title="'+file.name+'">' + file.name + '</a>' +
                /*'<span id="handleDel">删除</span>' +*/
                '<img src="/static/img/icon_delete_common1.png" class="formCtrl" id="handleDel">' +
                '</div>' );
            $($( '#'+file.id + " #fileInfo")).attr("href",Feng.fileSer + data.url);
            var uploadObj={id:file.id,name:data.originalName,url:data.url};
            me.listArry.push(uploadObj);
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
        });
    }
};

// 封装返回上传文件信息数组
function escapFileArr(arr){
    var reArr = [];
    if (arr != undefined && arr.length > 0){
        for(var i =0;i < arr.length;i++){
            var reObj = {"name":arr[i].name,"url":arr[i].url};
            reArr.push(reObj);
        }
    }
    return reArr;
}
/*input focus边框阴影*/
function inputFocus() {
    $(".myTable .input-group .form-control").focus(function () {
        $(this).css({
            "border-color":"#e5e6e7"
        });
        $(this).parent().css({
            "box-shadow":"5px 5px 26px #c2cbd5"
        });
    });
    $(".myTable .input-group .form-control").blur(function () {
        $(this).parent().css("box-shadow","none");
    });
    $(".myUser .input-group .form-control").focus(function () {
        $(this).css({
            "border-color":"#e5e6e7"
        });
        $(this).parent().css({
            "box-shadow":"5px 5px 26px #c2cbd5"
        });
    });
    $(".myUser .input-group .form-control").blur(function () {
        $(this).parent().css("box-shadow","none");
    });
    $(".myTable .input-group .form").focus(function () {
        $(this).css({
            "border-color":"none;"
        });
        $(this).parent().parent().css({
            "box-shadow":"5px 5px 26px #c2cbd5"
        });
    });
    $(".myTable .input-group .form").blur(function () {
        $(this).parent().parent().css("box-shadow","none");
    });
}
