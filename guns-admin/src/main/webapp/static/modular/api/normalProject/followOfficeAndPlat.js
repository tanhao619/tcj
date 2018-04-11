/**
 * 跟踪科室和平台
 */
var FollowOfficeAndPlat = {
    followData: {},
    projectId: parent.document.getElementById("id").value, // 项目主键
    proType: parent.document.getElementById("from").value, // 项目类型 1:常规;2:重大
    type: $("input[type='hidden'][name='type']").val(), // 分配来源 0:分配;1:更改分配
    folType: parent.document.getElementById("folType").value // 项目跟踪阶段
};

/**
 * 收集跟踪数据
 */
FollowOfficeAndPlat.collectData = function() {
    var data = {};
    data['from'] = FollowOfficeAndPlat.proType;
    data['proId'] = FollowOfficeAndPlat.projectId;
    data['folType'] = "2";
    // 科室数组
    var i = 0;
    $("input[name='office']:checked").each(function (index,element){
        data['followDeptId'+i] = element.value;
        data['deptId'+i] = element.parentNode.nextSibling.value;
        i ++ ;
    });
    data['deptCount'] = i;
    this.followData = data;
};

/**
 * 清除跟踪数据
 */
FollowOfficeAndPlat.clearData = function() {
    this.followData = {};
};

/**
 * 表单验证
 * 跟踪科室必须选择1个及以上（除开信息服务中心）
 *
 * @returns {boolean} 是否通过（true：不通过；false：通过）
 */
FollowOfficeAndPlat.validate = function () {
    var i = $("#officeFlagContent input[name='office']:checked").size();
    if (i < 2) {
        return true;
    } else {
        return false;
    }
};

/**
 * 提交跟踪表单，关闭子弹窗，刷新父页面
 */
FollowOfficeAndPlat.submit = function () {
    if(this.validate()) {
        Feng.error("跟踪科室必须选择1个及以上（不含信息服务中心）！");
        return;
    }
    this.clearData();
    this.collectData();
    //console.log(this.followData);
    //return;
    var ajax1 = new $ax(Feng.ctxPath + "/followProject/updateFollowProject", function (data) {
        Feng.success("更改跟踪科室成功！");
        parent.window.location.reload();
    }, function (data) {
        Feng.error("失败!" + data.responseJSON.message + "!");
    });
    ajax1.set(this.followData);
    ajax1.start();
};

/**
 * 关闭窗口
 */
FollowOfficeAndPlat.cancel = function () {
    switch (FollowOfficeAndPlat.proType) {
        case '1':
            parent.layer.close(window.parent.NormalProjectInfo.layerIndex);
            break;
        case '2':
            parent.layer.close(window.parent.BigProjectInfo.layerIndex);
            break;
    }
};


$(function () {
    // 加载跟踪投促局科室多选框（信息服务中心默认不可点）
    var ajax1 = new $axRest(Feng.ctxPath + "/normalProject/followsOffice", "get", function (data) {
        data.forEach(function (value, index, array) {
            var node = "<li><labe1><input type='checkbox' name='office' value='"+value.id+"'>"+value.simplename+"</labe1><input type='hidden' name='id' value=''></li>";
            if (value.simplename === "信息服务中心") {
                node = "<li class='li-disable'><labe1><input type='checkbox' name='office' value='"+value.id+"'>"+value.simplename+"</labe1><input type='hidden' name='id' value=''></li>";
            }
            $("#officeFlagContent ul").append(node);
        });
    }, function (data) {
        Feng.error("加载投促局科室失败!");
    });
    ajax1.start();

    // 加载跟踪平台科室多选框
    var ajax2 = new $axRest(Feng.ctxPath + "/normalProject/followsPlat", "get", function (data) {
        data.forEach(function (value, index, array) {
            var node = "<li><labe1 style='cursor: pointer'><input type='checkbox' name='office' value='" + value.id + "'>" + value.simplename + "</labe1><input type='hidden' name='id' value=''></li>";
            $("#platFlagContent ul").append(node);
        });
    }, function (data) {
        Feng.error("加载平台科室失败!");
    });
    ajax2.start();

    // 加载跟踪平台科室多选框
    var ajax3 = new $axRest(Feng.ctxPath + "/normalProject/followsStreet", "get", function (data) {
        data.forEach(function (value, index, array) {
            var node = "<li><labe1><input type='checkbox' name='office' value='" + value.id + "'>" + value.simplename + "</labe1><input type='hidden' name='id' value=''></li>";
            $("#streetFlagContent ul").append(node);
        });
    }, function (data) {
        Feng.error("加载街道办科室失败!");
    });
    ajax3.start();

    // 多选框加active
    $(".common ul li").click(function() {
        $(this).toggleClass("active");
        if($(this).hasClass("active")){
            $(this).children().children().prop("checked", "checked");
        }else{
            $(this).children().children().removeAttr("checked");
            $(this).children().css("color","#35a6d6");
        }
    });

    // 回显跟踪科室多选框
    var officeDeptId = parent.document.getElementsByName("officeDeptId");
    var checkbox = $("input[type='checkbox'][name='office']");
    for(var i=0;i<checkbox.size();i++){
        for(var j=0;j<officeDeptId.length;j++){
            if (checkbox[i].value === officeDeptId[j].value){
                checkbox[i].checked = true;
                $(checkbox[i]).parent().parent().addClass("active");
                checkbox[i].parentNode.nextElementSibling.value = officeDeptId[j].nextElementSibling.value;
                break;
            }
        }
    }

});
