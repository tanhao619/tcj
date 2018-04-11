/**
 * 跟踪人员列表
 */
var FollowUser = {
    id: "FollowUserTable",	//表格id
    folType: null,        // 项目跟踪阶段
    user: {}, //
    pageSize: 30,
    proId: parent.document.getElementById("id").value, // 项目主键
    proType: parent.document.getElementById("from").value // 项目类型 1：常规；2：重大
};

/**
 * 查询跟踪人员列表
 */
FollowUser.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val().trim();
    queryData['proId'] = FollowUser.proId;
    //加载跟踪人员
    var ajax2 = new $axRest(Feng.ctxPath + "/normalProject/followsPro", "get", function (data) {
        FollowUser.user = data;
        // var pageSize = 10;
        var total =  data.length;
        $(".total").html(total);
        var page = Math.ceil(total / FollowUser.pageSize);
        $(".pagination-outline").empty();
        $(".personInfo").empty();
        $(".pagination-outline").append('<li class="page-pre prev"><a onclick="FollowUser.goPage(1,this)" href="#">‹</a></li>');
        for (var pageNo = 0; pageNo < page; pageNo++) {
            $(".pagination-outline").append('<li class="page-number"><a onclick="FollowUser.goPage('+(pageNo+1)+  ',this)" href="#">'+(pageNo+1)+'</a></li>');
        }
        $(".pagination-outline").append('<li class="page-next next"><a onclick="FollowUser.goPage('+page+',this)" href="#">›</a></li>');
        data.forEach(function (value, index, array) {
            var pageNumber = 1;
            var start = (pageNumber-1)*FollowUser.pageSize;
            if (index>=start && index<start+FollowUser.pageSize){
                var node = "<li onclick='radioChecked(this)'><label><input type='radio' name='office' value='" + value.id + "'><span>" + value.name + "</span></label><input type='hidden' name='phone' value='"+value.phone+"'></li>";
                $(".personInfo").append(node);
            }
        });
    }, function (data) {
        Feng.error("没有!");
    });
    ajax2.set(queryData);
    ajax2.start();
};

/**
 * 确定选择人员，关闭子弹窗，刷新父页面
 */
FollowUser.submit = function () {
    var followData = {};
    followData['id'] = parent.document.getElementById("id").value;
    followData['from'] = FollowUser.proType;
    followData['followName'] = $("input[type='radio']:checked").siblings('span').html();
    followData['followTel'] = $("input[type='radio']:checked").parent().siblings("input").val();
    followData['followUserId'] = $("input[type='radio']:checked").val();
    /*console.log(followData);
    return;*/
    if(followData.followName == undefined){
        Feng.info("请先选中表格中的某一记录！");
        return;
    }else{
        var ajax1 = new $ax(Feng.ctxPath + "/normalProject/updateFollowUser", function (data) {
            Feng.success("更改跟踪人员成功！");
            parent.window.location.reload();
        }, function (data) {
            Feng.error("失败!" + data.responseJSON.message + "!");
        });
        ajax1.set(followData);
        ajax1.start();
    }
};

/**
 * 关闭窗口
 */
FollowUser.cancel = function () {
    switch (FollowUser.proType) {
        case '1':
            parent.layer.close(window.parent.NormalProjectInfo.layerIndex);
            break;
        case '2':
            parent.layer.close(window.parent.BigProjectInfo.layerIndex);
            break;
    }
};

/**
 *
 *
 * @param page
 */
FollowUser.goPage = function (page,this1) {
    var totalPage = Math.ceil(FollowUser.user.length / FollowUser.pageSize);
    $(this1).parent().addClass("active");
    $(this1).parent().siblings().removeClass("active");
    if(page == 1){
        $(".prev").attr("disabled","disabled");
    }
    else{
        $(".prev").removeAttr("disabled");
    }
    if(page == totalPage){
        $(".next").attr("disabled","disabled");
    }
    else{
        $(".next").removeAttr("disabled");
    }
    $(".personInfo").empty();
    FollowUser.user.forEach(function (value, index, array) {
        var pageNumber = page;
        var start = (pageNumber-1)*FollowUser.pageSize;
        if (index>=start && index<(start+FollowUser.pageSize)){
            var node = "";
            $(".personInfo").append("<li onclick='radioChecked(this)'><label><input type='radio' name='office' value='" + value.id + "'><span>" + value.name + "</span></label><input type='hidden' name='phone' value='"+value.phone+"'></li>");
        }
    });
};
function radioChecked(element){
    $(element).addClass("active").siblings().removeClass("active");
    $(element).find("input[type=radio]").attr("checked","checked");
}
$(function () {
    $("#name").bind('input propertychange', function() {
        FollowUser.search();
    });
    var queryData = {};
    var proId = parent.document.getElementById("id").value;
    queryData['proId'] = proId;
    //加载跟踪人员
    var ajax2 = new $axRest(Feng.ctxPath + "/normalProject/followsPro", "get", function (data) {
        FollowUser.user = data;
        // var pageSize = 30;
        var total =  data.length;
        $(".total").html(total);
        var page = Math.ceil(total / FollowUser.pageSize);
        $(".pagination-outline").append('<li class="page-pre prev"><a onclick="FollowUser.goPage(1,this)" href="#">‹</a></li>');
        for (var pageNo = 0; pageNo < page; pageNo++) {
            $(".pagination-outline").append('<li class="page-number"><a onclick="FollowUser.goPage('+(pageNo+1)+  ',this)" href="#">'+(pageNo+1)+'</a></li>');
        }
        $(".pagination-outline").append('<li class="page-next next"><a onclick="FollowUser.goPage('+page+',this)" href="#">›</a></li>');
        data.forEach(function (value, index, array) {
            var pageNumber = 1;
            var start = (pageNumber-1)*FollowUser.pageSize;
            if (index>=start && index<start+FollowUser.pageSize){
                var node = "<li onclick='radioChecked(this)'><label><input type='radio' name='office' value='" + value.id + "'><span>" + value.name + "</span></label><input type='hidden' name='phone' value='"+value.phone+"'></li>";
                $(".personInfo").append(node);
            }
        });
    }, function (data) {
        Feng.error("加载跟踪人员失败!" + data.responseJSON.message + "!");
    });
    ajax2.set(queryData);
    ajax2.start();
    //跟踪人员选中
    // setTimeout(function(){
    //     $(".personInfo li").click(function(){
    //         console.log(123);
    //         $(this).children("input[type=radio]").prop("checked","checked");
    //         $(this).addClass("active").siblings().removeClass("active");
    //
    //         // $(this).siblings().children("input").removeAttr("checked");
    //     });
    // },50);


});
