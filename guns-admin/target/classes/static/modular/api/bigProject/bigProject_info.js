/**
 * 初始化重大项目详情页
 */
var BigProjectInfo = {
    id: "BigProjectOperationLogTable", //操作日志表格id
    table: null, //操作日志表格
    layerIndex: -1,
    bigProjectId: $("#id").val(), // 重大项目主键
    normalProjectFolType: $("#folType").val(), // 重大项目状态
    hyForm : null, // 具体行业分类子表单
    zrForm : null, // 责任单位及相关责任人信息子表单
    jsForm : null, // 拟建设地点子表单
    bigProjectInfoData : {} // 封装表单数据
};

/**
 * 初始化详情页
 */
BigProjectInfo.init = function() {
    // 初始化表单--定义子表单节点并移除
    var hyForm = $(".hyForm")[0];
    BigProjectInfo.hyForm = hyForm;
    hyForm.remove();
    var zrForm = $(".zrForm")[0];
    BigProjectInfo.zrForm = zrForm;
    zrForm.remove();
    var jsForm = $(".jsForm")[0];
    BigProjectInfo.jsForm = jsForm;
    jsForm.remove();

    // 初始化表单--禁用表单及按钮
    $("#submit").hide();
    $("input").attr("disabled",true);
    $("textarea").attr("disabled",true);
    $("select").attr("disabled",true);
    $(".formCtrl").hide();
     $(".radioItem").addClass("li-disable");
     $(".radioItem img").css("cursor","pointer");
    $(".checkboxItem").addClass("li-disable");

    // 初始化表单--回显表单数据
    var category = $("#category").val();
    $("input[type='radio'][name='category']")[category - 1].checked = true;
    $("input[type='radio'][name='category']")[category - 1].parentNode.classList.add("active");
    var status = $("input[type='hidden'][name='status']").val();
    $("#status").val(status);
    var followName = $("#followName").val();
    var followTel = $("#followTel").val();
    $("#followUser").val(followName + " " + followTel);
    $("input[type='hidden'][name='packName']").each(function(index, element){
        var packName = $(element).val();
        var packId = $(element).prev().val();
        $("input[type='checkbox'][name='packId']")[packName - 1].checked = true;
        $("input[type='checkbox'][name='packId']")[packName - 1].value = packId;
        $("input[type='checkbox'][name='packId']")[packName - 1].parentNode.classList.add("active");
    });
    $("input[type='hidden'][name='adviseName']").each(function(index, element){
        var adviseName = $(element).val();
        var adviseId = $(element).prev().val();
        $("input[type='checkbox'][name='adviseId']")[adviseName - 1].checked = true;
        $("input[type='checkbox'][name='adviseId']")[adviseName - 1].value = adviseId;
        $("input[type='checkbox'][name='adviseId']")[adviseName - 1].parentNode.classList.add("active");
    });
    dateFormatYMD($("#planStartTime"));
    timeSingle($("#planStartTime"),"YYYY-MM-DD");
    dateFormatYMD($("#planEndTime"));
    timeSingle($("#planEndTime"),"YYYY-MM-DD");
    var investRmb = Number($("#investRmb").val());
    var investMvRmb = Number($("#investMvRmb").val());
    var investDollar = Number($("#investDollar").val());
    var investRatio = Number($("#investRatio").val());
    var investRmb2 = (investDollar / 10000) * investRatio;
    $("#investRmb2").val(investRmb2);
    var invest = investRmb + investRmb2;
    $("#invest").val(invest);
    dateFormatYMDHMS($("#updateWorkTime"));

    // 初始化表单--加载项目操作日志表格
    var defaultColunms = BigProjectInfo.initOperationLogColumn();
    var table = new BSTable(BigProjectInfo.id, "/proHistory/proHistorys?folType=2&proId="+BigProjectInfo.bigProjectId, defaultColunms);
    table.setPaginationType("server");
    BigProjectInfo.table = table.init();
    $('#BigProjectOperationLogTable').on('click-row.bs.table', function (e, row, element) {
        BigProjectInfo.openOperationLogDetail(row);
    });

    // 初始化表单--定义监控美元汇率
    $("#investRmb").bind('input propertychange', function() {
        BigProjectInfo.calculateInvest();
    });
    $("#investDollar1").bind('input propertychange', function() {
        $("#investDollar").val(Number($("#investDollar1").val()));
        BigProjectInfo.calculateInvest();
    });
    $("#investRatio").bind('input propertychange', function() {
        BigProjectInfo.calculateInvest();
    });
};

/**
 * 点击跟踪人员信息，打开人员信息弹窗
 */
BigProjectInfo.openFollowUser = function () {
    var index = layer.open({
        type: 2,
        title: '跟踪人员',
        area: ['830px', '720px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/normalProject/followUser'
    });
    this.layerIndex = index;
};

/**
 * 修改重大包装项目信息，放开表单
 */
BigProjectInfo.edit = function() {
    $("#edit").hide();
    $("#submit").show();
    $("input").attr("disabled",false);
    $("textarea").attr("disabled",false);
    $("select").attr("disabled",false);
    $(".formCtrl").show();
    $(".radioItem").removeClass("li-disable");
    $(".checkboxItem").removeClass("li-disable");
};

/**
 * 清除表单数据
 */
BigProjectInfo.clearData = function() {
    this.bigProjectInfoData = {};
};

/**
 * 收集表单数据
 */
BigProjectInfo.collectData = function() {
    var editData = {};
    editData['id'] = $("#id").val();
    editData['name'] = $("#name").val();
    editData['status'] = $("#status").val();
    editData['category'] = $("input[type='radio'][name='category']:checked").val();

    // 具体行业分类数组
    var cateCount = 0;
    $(".hyForm").each(function(index, element){
        editData['cateId'+cateCount] = $(element).find("input[type='hidden'][name='cateId']").val();
        editData['cateName'+cateCount] = $(element).find("input[type='text'][name='cateName']").val();
        cateCount ++;
    });
    editData['cateCount'] = cateCount;

    // 策划包装定位数组
    var packCount = 0;
    $("input[type='checkbox'][name='packId']:checked").each(function(index, element){
        var name = $("input[type='checkbox'][name='packId']").index(element);
        editData['packId'+packCount] = element.value;
        editData['packName'+packCount] = name + 1;
        packCount ++;
    });
    editData['packCount'] = packCount;

    // 责任单位及相关责任人信息数组
    var unitCount = 0;
    $(".zrForm").each(function(index, element){
        editData['unitId'+unitCount] = $(element).find("input[type='hidden'][name='unitId']").val();
        editData['unitLiable'+unitCount] = $(element).find("input[name='unitLiable']").val();
        editData['unitName'+unitCount] = $(element).find("input[name='unitName']").val();
        editData['unitTel'+unitCount] = $(element).find("input[name='unitTel']").val();
        unitCount ++;
    });
    editData['unitCount'] = unitCount;

    // 建议实施方式数组
    var adviseCount = 0;
    $("input[type='checkbox'][name='adviseId']:checked").each(function(index, element){
        var name = $("input[type='checkbox'][name='adviseId']").index(element);
        editData['adviseId'+adviseCount] = element.value;
        editData['adviseName'+adviseCount] = name + 1;
        adviseCount ++;
    });
    editData['adviseCount'] = adviseCount;

    editData['planStartTime'] = $("#planStartTime").val();
    editData['planEndTime'] = $("#planEndTime").val();
    editData['planProcess'] = $("#planProcess").val();
    editData['leader'] = $("#leader").val();
    editData['investRmb'] = Number($("#investRmb").val());
    editData['investMvRmb'] = Number($("#investMvRmb").val());
    editData['investDollar'] = Number($("#investDollar").val());
    editData['investRatio'] = Number($("#investRatio").val());
    editData['countRmb'] = Number($("#invest").val());

    // 拟建设地点数组
    var bpCount = 0;
    $(".jsForm").each(function(index, element){
        editData['buildPlaceId'+bpCount] = $(element).find("input[type='hidden'][name='buildPlaceId']").val();
        editData['buildPlaceName'+bpCount] = $(element).find("input[type='text'][name='buildPlaceName']").val();
        bpCount ++;
    });
    editData['bpCount'] = bpCount;

    editData['content'] = $("#content").val();
    editData['govArea'] = $("#govArea").val();
    editData['collectArea'] = $("#collectArea").val();
    editData['farmArea'] = $("#farmArea").val();
    editData['isMerge'] = $("#isMerge").val();
    editData['workProcess'] = $("#workProcess").val();
    editData['infoDesc'] = $("#infoDesc").val();

    this.bigProjectInfoData = editData;
};

/**
 * 确定修改
 */
BigProjectInfo.editSubmit = function() {
    this.clearData();
    this.collectData();
    console.log(this.bigProjectInfoData);
    // return;
    var ajax = new $ax(Feng.ctxPath + "/bigProject/add", function(data){
        Feng.success("修改成功!");
        location.reload();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bigProjectInfoData);
    ajax.start();
};

/**
 * 点击更改平台与科室，出现平台和科室复选框
 *
 * @param type （0：分配；1：更改分配）
 */
BigProjectInfo.openFollowOfficeAndPlat = function(type) {
    var index = layer.open({
        type: 2,
        title: '跟踪分配',
        area: ['545px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/normalProject/followOfficeAndPlat/'+type
    });
    this.layerIndex = index;
};

/**
 * 添加子表单
 *
 * @param childFormType 子表单类型（1：具体行业分类；2：责任单位及相关责任人信息；3：拟建设地点）
 */
BigProjectInfo.addChildForm = function(childFormType){
    switch (childFormType) {
        case 1:
            $("#hyForms").append($(this.hyForm).clone(true));
            break;
        case 2:
            $("#zrForms").append($(this.zrForm).clone(true));
            break;
        case 3:
            $("#jsForms").append($(this.jsForm).clone(true));
            break;
    }
};

/**
 * 删除子表单
 *
 * @param this1 当前子表单
 * @param childFormType 子表单类型（1：具体行业分类；2：责任单位及相关责任人信息；3：拟建设地点）
 */
BigProjectInfo.delChildForm = function(this1, childFormType){
    switch (childFormType) {
        case 1:
            this1.closest(".hyForm").remove();
            break;
        case 2:
            this1.closest(".zrForm").remove();
            break;
        case 3:
            this1.closest(".jsForm").remove();
            break;
    }
};

/**
 * 打开合算弹框
 */
BigProjectInfo.openCalculateInvest = function(){
    $(".cal_wrap").show();
};

/**
 * 自动计算
 */
BigProjectInfo.calculateInvest = function () {
    var investRmb = Number($("#investRmb").val());//人民币（亿元）
    var investDollar1 = Number($("#investDollar1").val());//美元（万元）
    var investRatio = Number($("#investRatio").val());//汇率
    var investRmb2 = investDollar1/10000*investRatio;
    $("#investRmb2").val(investRmb2);
    var invest = investRmb2+investRmb;
    $("#invest").val(invest);
};

/**
 * 关闭合算弹框
 */
BigProjectInfo.closeCalculateModel = function(){
    $(".cal_wrap").hide();
};

/**
 * 目前工作进度更新信息，查看及修改更新记录
 */
BigProjectInfo.openProcessUpdate = function () {
    var index = layer.open({
        type: 2,
        title: '目前工作进度更新信息--查看及修改更新记录',
        area: ['85%', '70%'],
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/bigProject/processLog',
        end: function () {
            location.reload();
        }
    });
    this.layerIndex = index;
};

/**
 * 目前工作进度更新信息，添加更新
 */
BigProjectInfo.addProcessUpdInfo = function () {
    var node = $("#updateWorkTime");
    node.closest(".updateTime").html('正在添加更新');
    var parentNode = $("#addJd");
    parentNode.find("textarea[name='workProcess']").val(null);
    parentNode.find("#isMerge").val("1");
};

/**
 * 平台审核，提交成功，刷新页面
 */
BigProjectInfo.audit = function (proId) {
    var data = {};
    data['from'] = "2";
    data['folType'] = "2";
    data['proId'] = proId;
    var operation = function(){
        var ajax = new $ax(Feng.ctxPath + "/followProject/updateFollowProject", function(data){
            Feng.success("审核成功！");
            location.reload();
        },function(data){
            Feng.error("错误!" + data.responseJSON.message + "!");
        });
        ajax.set(data);
        ajax.start();
    };
    Feng.confirm("是否审核通过此项目： " + $("#name").val() + "?", operation);
};

/**
 * 取消，返回列表页
 */
BigProjectInfo.close = function () {
    location.href = Feng.ctxPath + '/bigProject';
};

/**
 * 初始化操作日志表格的列
 */
BigProjectInfo.initOperationLogColumn = function () {
    return [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '历史操作', field: 'content', visible: true, align: 'left', valign: 'middle'},
        {title: '操作人员', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '操作时间', field: 'createTime', visible: true, align: 'center', valign: 'middle',sortable: true}
    ];
};

/**
 * 弹出操作日志详情
 */
BigProjectInfo.openOperationLogDetail = function (row) {
    var index = layer.open({
        type: 2,
        title: '由'+row.name+'更新于'+row.createTime,
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/normalProject/operationLog/'+row.id
    });
    this.layerIndex = index;
};

$(function() {
    //初始化详情编辑跟踪页
    BigProjectInfo.init();

    // 鼠标移到单选框显示产业
    $(".helpImg").hover(function () {
        $(this).next().css("display","block");
    },function () {
        $(this).next().css("display","none");
    });

    //给策划包装定位加选种样式
    setTimeout(function(){
        $(".checkboxChoice li").click(function(){
            $(this).toggleClass("active");
            if($(this).hasClass("active")){
                $(this).children().prop("checked", "checked");
            }else{
                $(this).children().removeAttr("checked");
                // $(this).children().css("color","#35a6d6");
            }
        });
    },50);
    //  给选中的产业类别加样式
    $(".radioItem").click(function(){
        console.log($(this));
        $(this).addClass("active").siblings().removeClass("active");
        $(this).children("input").prop("checked","checked");
        $(this).siblings().children("input").removeAttr("checked");
    });
});
