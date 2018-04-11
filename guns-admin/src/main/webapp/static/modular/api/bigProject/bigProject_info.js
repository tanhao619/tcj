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
    bigProjectInfoData : {}, // 封装表单数据
    validateFields: {
        name: {
            container:'#nameTip',
            validators: {
                notEmpty: {
                    message: '投资项目名称不能为空'
                },
                remote: {
                    url: '/bigProject/checkBigProject',
                    message: '该项目名称已经存在',
                    type: 'POST',
                    data: function(validator) {
                        return {
                            name: $('#name').val(),
                            proId: $("#id").val()
                        }
                    }
                }
            },
        },
        /*hyCount: {
            trigger:"change",
            container:'#hyFormTip',
            validators: {
                greaterThan: {
                    value: 1,
                    message: '至少要有1个具体行业分类'
                }
            }
        },
        packId: {
            container:'#packTip',
            validators: {
                choice: {
                    min: 1,
                    max: 4,
                    message: '请选择策划包装定位'
                }
            }
        },
        zrCount: {
            trigger:"change",
            container:'#zrFormTip',
            validators: {
                greaterThan: {
                    value: 1,
                    message: '至少要有1个责任单位及相关责任人信息'
                }
            }
        },
        investRmb: {
            container:'#investTip',
            validators: {
                numeric: {
                    message: '请填写数字'
                }
            }
        },
        investMvRmb: {
            container:'#investTip',
            validators: {
                numeric: {
                    message: '请填写数字'
                },
                /!*lessThan: {
                 field: '#investRmb',
                 message: '拆迁费不能高于投资额（人民币）'
                 }*!/
            }
        },
        investDollar1: {
            container:'#investTip',
            validators: {
                numeric: {
                    message: '请填写数字'
                }
            }
        },
        investRatio: {
            container:'#investTip',
            validators: {
                numeric: {
                    message: '请填写数字'
                }
            }
        },
        invest: {
            trigger:"change",
            container:'#investTip',
            validators: {
                greaterThan: {
                    value: 0,
                    message: '请填写匡算投资额'
                }
            }
        },
        govArea: {
            container:'#njsFormTip',
            validators: {
                numeric: {
                    message: '请填写数字'
                }
            }
        },
        collectArea: {
            container:'#njsFormTip',
            validators: {
                numeric: {
                    message: '请填写数字'
                }
            }
        },
        farmArea: {
            container:'#njsFormTip',
            validators: {
                numeric: {
                    message: '请填写数字'
                }
            }
        },
        content: {
            container: '#njsFormTip',
            validators: {
                stringLength: {
                    max: 500,
                    message: '投资建设内容不能超过500字'
                }
            }
        },
        workProcess: {
            container: '#workProcessTip',
            validators: {
                stringLength: {
                    max: 500,
                    message: '目前工作进度不能超过500字'
                }
            }
        },
        infoDesc: {
            container: '#infoDescTip',
            validators: {
                stringLength: {
                    max: 100,
                    message: '备注不能超过100字'
                }
            }
        }*/
    }, //基本表单验证
    validateHyFields: {
        cateName: {
            container:'#hyFormTip',
            validators: {
                notEmpty: {
                    message: '行业分类名称不能为空'
                }
            }
        }
    }, //具体行业分类表单验证
    validateZrFields: {
        unitLiable: {
            container:'#zrFormTip',
            validators: {
                notEmpty: {
                    message: '责任单位名称不能为空'
                }
            }
        },
        unitName: {
            container:'#zrFormTip',
            validators: {
                notEmpty: {
                    message: '责任人姓名不能为空'
                }
            }
        },
        unitTel: {
            container:'#zrFormTip',
            validators: {
                notEmpty: {
                    message: '责任人电话不能为空'
                },
                regexp:{
                    regexp:/(^1[34578]\d{9}$)$|(\d{3}-\d{8}|\d{4}-\d{7})/,
                    message:"责任人电话号码格式不正确"
                }
            }
        }
    }, //责任单位及相关责任人信息表单验证
    validateJsFields: {
        buildPlaceName: {
            container:'#jsFormTip',
            validators: {
                notEmpty: {
                    message: '拟建设地点名称不能为空'
                }
            }
        }
    } //拟建设地点表单验证
};

/**
 * 初始化详情页
 */
BigProjectInfo.init = function() {
    // 给表单加校验
    // Feng.initValidator("bigProjectEditForm", BigProjectInfo.validateFields);

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
    $("#hyCount").val($(".hyForm").length);
    $("#zrCount").val($(".zrForm").length);
    var status = $("input[type='hidden'][name='status']").val();
    $("#status").val(status);
    var followName = $("#followName").val();
    var followTel = $("#followTel").val();
    $("#followUser").val(followName + " " + followTel);
    $("input[type='hidden'][name='cyId']").each(function (index,element) {
        var cyId = $(element).val();
        var cyName = $(element).next().val();
        $("input[name='category'][type='checkbox']")[cyName-1].checked=true;
        $("input[name='category'][type='checkbox']")[cyName-1].value= cyId;
        $("input[name='category'][type='checkbox']")[cyName-1].parentNode.classList.add("active");
    });
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
        this.value = this.value.replace(/\-/g,"");
        BigProjectInfo.calculateInvest();
    });
    $("#investDollar1").bind('input propertychange', function() {
        this.value = this.value.replace(/\-/g,"");
        $("#investDollar").val(Number($("#investDollar1").val()));
        BigProjectInfo.calculateInvest();
    });
    $("#investRatio").bind('input propertychange', function() {
        this.value = this.value.replace(/\-/g,"");
        BigProjectInfo.calculateInvest();
    });

    // 金额面积不能输入复数
    $("input[type='number']").bind('input propertychange', function() {
        this.value = this.value.replace(/\-/g,"");
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
    var deptLevel = $("#deptLevel").val();
    if (deptLevel == '3') {
        $("#addJd").find("textarea").attr("disabled",false);
        $("#addJd").find(".formCtrl").show();
        return;
    }
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

    // 归属产业数组
    var cyCount = 0;
    $("input[name='category'][type='checkbox']:checked").each(function (index,element){
        var index = $("input[name='category'][type='checkbox']").index(element);
        editData['cyId'+cyCount] = $(element).val();
        editData['cyName'+cyCount] = index+1;
        cyCount ++ ;
    });
    editData['cyCount'] = cyCount;

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
 * 验证数据
 */
BigProjectInfo.validate = function () {
    var bootstrapValidator = $("#bigProjectEditForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if(!bootstrapValidator.isValid()){
        return false;
    }
    /*var hyCount = $(".hyForm").length;
    if(hyCount >= 1 ){
        Feng.initValidator("detailIndustry", BigProjectInfo.validateHyFields);
        var cateBootstrapValidator = $("#detailIndustry").data('bootstrapValidator');
        cateBootstrapValidator.validate();
        if(!cateBootstrapValidator.isValid()){
            return false;
        }
    }
    var zrCount = $(".zrForm").length;
    if(zrCount >= 1 ){
        Feng.initValidator("zrForms", BigProjectInfo.validateZrFields);
        var unitLiableBootstrapValidator = $("#zrForms").data('bootstrapValidator');
        unitLiableBootstrapValidator.validate();
        if(!unitLiableBootstrapValidator.isValid()){
            return false;
        }
    }
    var jsCount = $(".jsForm").length;
    if(jsCount >= 1 ){
        Feng.initValidator("constructionSite", BigProjectInfo.validateJsFields);
        var buildPlaceBootstrapValidator = $("#constructionSite").data('bootstrapValidator');
        buildPlaceBootstrapValidator.validate();
        if(!buildPlaceBootstrapValidator.isValid()){
            return false;
        }
    }*/
    return true;
};

/**
 * 验证数据，原生态，无污染
 */
BigProjectInfo.validate4JS = function () {
    this.clearData();
    this.collectData();
    // 验证基本表单
    if(this.bigProjectInfoData.name.trim() == ""){
        $("#nameTip").html("投资项目名称不能为空");
        return false;
    } else {
        var nameValid = true;
        var ajax = new $ax(Feng.ctxPath + "/bigProject/checkBigProject", function(data){
            if(data.valid === false) {
                $("#nameTip").html("该项目名称已经存在");
                nameValid = false;
            }
        },function(data){
            Feng.error("无法验证项目名称重复!" + data.responseJSON.message + "!");
            nameValid = false;
        });
        ajax.set({proId:this.bigProjectInfoData.id, name:this.bigProjectInfoData.name});
        ajax.start();
        if(nameValid === false) {
            return false;
        }
    }
    if(this.bigProjectInfoData.cyCount < 1){
        $("#categoryTip").html("请勾选至少1个归属产业");
        return false;
    }
    if(this.bigProjectInfoData.cateCount < 1){
        $("#hyFormTip").html("至少要有1个具体行业分类");
        return false;
    } else {
        for (var i = 0; i < this.bigProjectInfoData.cateCount; i++){
            if(this.bigProjectInfoData['cateName' + i].trim() == ""){
                $("#hyFormTip").html("行业分类名称不能为空");
                return false;
            }
        }
    }
    if(this.bigProjectInfoData.packCount < 1){
        $("#packTip").html("请勾选至少1个策划包装定位");
        return false;
    }
    if(this.bigProjectInfoData.unitCount < 1){
        $("#zrFormTip").html("至少要有1个责任单位及相关责任人信息");
        return false;
    } else {
        for (var i = 0; i < this.bigProjectInfoData.unitCount; i++){
            if(this.bigProjectInfoData['unitLiable' + i].trim() == ""){
                $("#zrFormTip").html("责任单位名称不能为空");
                return false;
            }
            if(this.bigProjectInfoData['unitName' + i].trim() == ""){
                $("#zrFormTip").html("责任人姓名不能为空");
                return false;
            }
            if(this.bigProjectInfoData['unitTel' + i].trim() == ""){
                $("#zrFormTip").html("责任人电话不能为空");
                return false;
            } else if(!/(^1[34578]\d{9}$)$|(\d{3}-\d{8}|\d{4}-\d{7})/.test(this.bigProjectInfoData['unitTel' + i].trim())) {
                $("#zrFormTip").html("责任人电话号码格式不正确");
                return false;
            }
        }
    }
    for (var i = 0; i < this.bigProjectInfoData.bpCount; i++){
        if(this.bigProjectInfoData['buildPlaceName' + i].trim() == ""){
            $("#jsFormTip").html("拟建设地点名称不能为空");
            return false;
        }
    }
    if(this.bigProjectInfoData.content.trim().length > 500){
        $("#njsFormTip").html("投资建设内容不能超过500字");
        return false;
    }
    if(this.bigProjectInfoData.workProcess.trim().length > 500){
        $("#workProcessTip").html("目前工作进度不能超过500字");
        return false;
    }
    if(this.bigProjectInfoData.infoDesc.trim().length > 100){
        $("#infoDescTip").html("备注不能超过100字");
        return false;
    }
    return true;
};

/**
 * 确定修改
 */
BigProjectInfo.editSubmit = function() {
    $(".tips").html("");
    // if (!this.validate()) {
    if (!this.validate4JS()) {
        Feng.error("表单错误！请检查后提交");
        return;
    }
    // this.clearData();
    // this.collectData();
    //console.log(this.bigProjectInfoData);
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
            $("#hyCount").val(Number($("#hyCount").val()) + 1).change();
            break;
        case 2:
            $("#zrForms").append($(this.zrForm).clone(true));
            $("#zrCount").val(Number($("#zrCount").val()) + 1).change();
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
            $("#hyCount").val(Number($("#hyCount").val()) - 1).change();
            break;
        case 2:
            this1.closest(".zrForm").remove();
            $("#zrCount").val(Number($("#zrCount").val()) - 1).change();
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
        resize:false,
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
        {title: '操作时间', field: 'createTime', visible: true, align: 'center', valign: 'middle',sortable: true,width:'10%'}
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
            $(this).children("input").trigger('click');
            if($(this).hasClass("active")){
                $(this).children().prop("checked", "checked");
            }else{
                $(this).children().removeAttr("checked");
                // $(this).children().css("color","#35a6d6");
            }
        });
    },50);

    //  给选中的产业类别加样式
    // $(".radioItem").click(function(){
    //     $(this).addClass("active").siblings().removeClass("active");
    //     $(this).children("input").trigger('click');
    //     $(this).siblings().children("input").removeAttr("checked");
    // });

    $("input[name='packId'],input[name='adviseId'],input[name='category']").click(function(event){
        event.stopPropagation();
    });
});
