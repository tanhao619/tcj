/**
 * 初始化常规项目详情
 */
var NormalProjectInfo = {
    id: "NormalProjectOperationLogTable", //操作日志表格id
    table: null, //操作日志表格
    layerIndex: -1,
    normalProjectId: $("#id").val(), //项目id
    normalProjectFolType: $("#folType").val(), //项目状态
    normalProjectEditData: {}, //收集的全页面编辑数据
    tzf: null, //投资方公司表单
    zr: null, //责任单位及相关责任人信息表单
    xm: null, //项目地址及用地表单
    validateFields: {
        name: {
            container:'#nameTip',
            validators: {
                notEmpty: {
                    message: '投资项目名称不能为空'
                },
                remote: {
                    url: '/normalProject/checkNormalProject',
                    message: '该项目名称已经存在',
                    type: 'POST',
                    data: function(validator) {
                        return {
                            name: $('#name').val(),
                            proId: $('#id').val()
                        }
                    }
                }
            },
        },
        /*comCount: {
            trigger:"change",
            container:'#comTip',
            validators: {
                greaterThan: {
                    value: 1,
                    message: '至少要有1个投资方'
                },
                lessThan: {
                    value: 5,
                    message: '最多只能有5个投资方'
                }
            }
        },
        infoDesc: {
            container: '#infoDescTip',
            validators: {
                stringLength: {
                    max: 500,
                    message: '备注不能超过500字'
                }
            }
        },
        content: {
            container:'#contentTip',
            validators: {
                notEmpty: {
                    message: '投资建设内容不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 600,
                    message: '投资建设内容不能超过600字'
                }
            }
        },
        tel:{
            container:'#infoProviderTip',
            validators: {
                regexp:{
                    regexp:/(^1[34578]\d{9}$)$|(\d{3}-\d{8}|\d{4}-\d{7})/,
                    message:"信息提供者联系电话号码格式不正确"
                }
            }
        },
        enterOfferName: {
            container:'#entProviderTip',
            validators: {
                notEmpty: {
                    message: '企业提供者不能为空'
                }
            }
        },
        enterOfferTel:{
            container:'#entProviderTip',
            validators: {
                notEmpty: {
                    message: '企业提供者联系电话不能为空'
                },
                regexp:{
                    regexp:/(^1[34578]\d{9}$)$|(\d{3}-\d{8}|\d{4}-\d{7})/,
                    message:"企业提供者联系电话号码格式不正确"
                }
            }
        }*/
    }, //基本表单验证
    validateBaseFields: {

    }, //项目基本信息情况
    validateTzfFields: {
        comName: {
            container:'#comTip',
            validators: {
                notEmpty: {
                    message: '公司名称不能为空'
                }
            }
        },
        comAuthor: {
            container:'#comTip',
            validators: {
                notEmpty: {
                    message: '公司联系人不能为空'
                }
            }
        },
        comAddr: {
            container:'#comTip',
            validators: {
                notEmpty: {
                    message: '公司地址不能为空'
                }
            }
        },
        comTel: {
            container:'#comTip',
            validators: {
                notEmpty: {
                    message: '公司联系人电话不能为空'
                },
                regexp:{
                    regexp:/(^1[34578]\d{9}$)$|(\d{3}-\d{8}|\d{4}-\d{7})/,
                    message:"公司联系人电话号码格式不正确"
                }
            }
        },
        comContent: {
            container:'#comTip',
            validators: {
                notEmpty: {
                    message: '公司简介不能为空'
                },
                stringLength: {
                    max: 600,
                    message: '公司简介不能超过600字'
                }
            }
        }
    }, //投资方公司表单验证
    validateZrFields: {
        unitLiable: {
            container:'#zrTip',
            validators: {
                notEmpty: {
                    message: '责任单位不能为空'
                }
            }
        }
    }, //责任单位及相关责任人信息表单验证
    validateXmFields: {
        addr: {
            container:'#xmTip',
            validators: {
                notEmpty: {
                    message: '项目地址不能为空'
                }
            }
        }
    } //项目地址及用地表单验证
};

/**
 * 点击跟踪人员信息，出现人员信息table
 */
NormalProjectInfo.openFollowUser = function() {
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
 * 新增投资方公司信息表单
 */
NormalProjectInfo.addTzfTable = function() {
    var tzfTable = $(NormalProjectInfo.tzf).clone(true);
    $(".investmentCompanyInfo").append(tzfTable);
    var comCount = Number($("#comCount").val());
    $("#comCount").val(comCount + 1).change();
};

/**
 * 删除投资方公司信息表单
 */
NormalProjectInfo.delTzfTable = function(this1){
    var parentElement = this1.closest(".addTzf");
    parentElement.remove();
    var comCount = Number($("#comCount").val());
    $("#comCount").val(comCount - 1).change();
};

/**
 * 修改常规投资项目信息，放开表单
 */
NormalProjectInfo.edit = function() {
    $("#edit").hide();
    $("#submit").show();
    var deptLevel = $("#deptLevel").val();
    if (deptLevel == '3') {
        $("#addQt,#addLy,#item7").find("textarea,input").attr("disabled",false);
        $("#addQt,#addLy,#item7").find(".radioItem").removeClass("li-disable");
        $("#addQt,#addLy").find(".formCtrl").show();
        return;
    }
    $("input").attr("disabled",false);
    $("textarea").attr("disabled",false);
    $("select").attr("disabled",false);
    $(".formCtrl").show();
    $(".radioItem").removeClass("li-disable");
    $(".checkboxItem").removeClass("li-disable");
    $("#picker").removeAttr("style");
};

/**
 * 点击更改平台与科室，出现平台和科室复选框
 *
 * @param type （0：分配；1：更改分配）
 */
NormalProjectInfo.openFollowOfficeAndPlat = function(type) {
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
 * 跟踪洽谈--打开合算弹框
 */
NormalProjectInfo.openCalculateInvest = function(){
    $(".cal_wrap").show();
};

/**
 * 跟踪洽谈--自动计算
 */
NormalProjectInfo.calculateInvest = function () {
    var investRmb = Number($("#investRmb").val());//人民币（亿元）
    var investDollar1 = Number($("#investDollar1").val());//美元（万元）
    var investRatio = Number($("#investRatio").val());//汇率
    var investRmb2 = investDollar1/10000*investRatio;
    $("#investRmb2").val(investRmb2);
    var invest = investRmb2+investRmb;
    $("#invest").val(invest);
};

/**
 * 跟踪洽谈--关闭合算弹框
 */
NormalProjectInfo.closeCalculateModel =function(){
    $(".cal_wrap").hide();
};

/**
 * 跟踪洽谈--是否重大项目，选择是否单选
 *
 * @param isBigPro （1：是；0：否）
 */
NormalProjectInfo.checkIsBigPro = function (isBigPro) {
    if (isBigPro == 1) {
        $("#addZd").show();
    } else {
        $("#addZd").hide();
    }
};

/**
 * 跟踪洽谈--删除一个项目备案表
 */
NormalProjectInfo.delAttachment = function (this1) {
    var parentElement = this1.parentNode;
    parentElement.remove();
};

/**
 * 跟踪洽谈--新增责任单位及相关责任人信息表单
 */
NormalProjectInfo.addZrTable = function () {
    var zrTable = $(NormalProjectInfo.zr).clone(true);
    $(".baseInfo").append(zrTable);
};

/**
 * 跟踪洽谈--删除责任单位及相关责任人信息表单
 */
NormalProjectInfo.delZrTable = function (this1) {
    var parentElement = this1.closest(".addZr");
    parentElement.remove();
};

/**
 * 跟踪洽谈--新增项目地址及用地表单
 */
NormalProjectInfo.addXmTable = function () {
    var xmTable = $(NormalProjectInfo.xm).clone(true);
    $(".projectAddress").append(xmTable);
};

/**
 * 跟踪洽谈--删除项目地址及用地表单
 */
NormalProjectInfo.delXmTable = function (this1) {
    var parentElement = this1.closest(".addXm");
    parentElement.remove();
};

/**
 * 跟踪洽谈--选择资源使用类型改变使用面积单位
 */
NormalProjectInfo.changeUserType = function (this1) {
    var type = this1.value;
    if(type == 1) {
        $(this1).closest(".secondLine").find("label:last").html("使用面积（亩）");
    } else {
        $(this1).closest(".secondLine").find("label:last").html("使用面积（平方米）");
    }
};

/**
 * 跟踪洽谈--项目洽谈更新信息，添加更新
 */
NormalProjectInfo.addTalkUpdInfo = function () {
    var node = $("#updateTime");
    node.closest(".updateTime").html('正在添加更新');
    var parentNode = $("#addQt");
    parentNode.find("textarea[name='progress']").val(null);
    parentNode.find("textarea[name='question']").val(null);
    parentNode.find("textarea[name='nextStep']").val(null);
    parentNode.find("input[name='isVisit'][type='radio']")[1].checked=true;
    parentNode.find("input[name='isVisit'][type='radio']")[0].parentNode.classList.remove("active");
    parentNode.find("input[name='isVisit'][type='radio']")[1].parentNode.classList.add("active");
    parentNode.find("#visitLv").val(null);
    parentNode.find("#isMerge").val("1");
};

/**
 * 跟踪洽谈--项目洽谈更新信息，查看及修改更新记录
 */
NormalProjectInfo.openTalkUpdate = function () {
    var index = layer.open({
        type: 2,
        title: '项目洽谈更新信息--查看及修改更新记录',
        area: ['80%', '70%'], //宽高
        fix: false, //不固定
        maxmin: true,
        resize:false,
        content: Feng.ctxPath + '/normalProject/talkLog',
        end: function () {
            location.reload();
        }
    });
    this.layerIndex = index;
};

/**
 * 跟踪洽谈--是否需要拜访，选择是否单选
 *
 * @param isVisit （1：是；0：否）
 */
NormalProjectInfo.checkIsVisit = function (isVisit) {
    if (isVisit == 1) {
        $("#visitLvDiv").show();
    } else {
        $("#visitLv").val("");
        $("#visitLvDiv").hide();
    }
};

/**
 * 履约情况--项目履约情况，选择是否单选
 *
 * @param conventionType （1：是；0：否）
 */
NormalProjectInfo.checkConventionType = function (conventionType) {
    if (conventionType == 1) {
        $("#addLy").show();
    } else {
        /*$("textarea[name='proConventionInfo']").val(null);
        $("textarea[name='nextAdvise']").val(null);*/
        $("#addLy").hide();
    }
};

/**
 * 履约情况--项目履约情况，添加更新
 */
NormalProjectInfo.addConventionUpdInfo = function () {
    var node = $("#proconventionUpdateTime");
    node.closest(".proconventionUpdateTime").html('正在添加更新');
    var parentNode = $("#addLy");
    parentNode.find("textarea[name='proConventionInfo']").val(null);
    parentNode.find("textarea[name='nextAdvise']").val(null);
    parentNode.find("#isMergeT").val("1");
};

/**
 * 履约情况--项目履约情况，查看及修改更新记录
 */
NormalProjectInfo.openConventionUpdate = function () {
    var index = layer.open({
        type: 2,
        title: '项目履约更新信息--查看及修改更新记录',
        area: ['85%', '70%'], //宽高
        fix: false, //不固定
        maxmin: true,
        resize:false,
        content: Feng.ctxPath + '/normalProject/conventionLog',
        end: function () {
            location.reload();
        }
    });
    this.layerIndex = index;
};

/**
 * 清除常规投资项目信息数据
 */
NormalProjectInfo.clearData = function() {
    this.normalProjectEditData = {};
};

/**
 * 收集常规投资项目信息数据
 */
NormalProjectInfo.collectData = function() {
    var editData = {};

    // 收集项目信息
    editData['id'] = $("#id").val();
    editData['name'] = $("#name").val();
    editData['status'] = $("#status").val();
    // 投资方公司数组
    var i = 0;
    $(".addTzf").each(function (index,element){
        editData['comId'+i] = $(element).find("input[name='comId']").val();
        editData['comName'+i] = $(element).find("input[name='comName']").val();
        editData['comAddr'+i] = $(element).find("input[name='comAddr']").val();
        editData['comAuthor'+i] = $(element).find("input[name='comAuthor']").val();
        editData['comTel'+i] = $(element).find("input[name='comTel']").val();
        editData['comType'+i] = $(element).find("#comType").val();
        editData['comContent'+i] = $(element).find("textarea[name='comContent']").val();
        i ++ ;
    });
    editData['comCount'] = i;
    editData['infoDesc'] = $("textarea[name='infoDesc']").val();

    // 收集项目基本信息（第一个tab）
    editData['content'] = $("#content").val();
    editData['fromArea'] = $("input[type='radio'][name='fromArea']:checked").val();
    editData['investType'] = $("input[type='radio'][name='investType']:checked").val();
    // 归属产业数组
    var o = 0;
    $("input[name='category'][type='checkbox']:checked").each(function (index,element){
        var index = $("input[name='category'][type='checkbox']").index(element);
        editData['cyId'+o] = $(element).val();
        editData['cyName'+o] = index+1;
        o ++ ;
    });
    editData['cyCount'] = o;
    editData['leadCategory'] = $("input[type='radio'][name='leadCategory']:checked").val();
    editData['author'] = $("#author").val();
    editData['tel'] = $("#tel").val();
    editData['enterOfferName'] = $("#enterOfferName").val();
    editData['enterOfferTel'] = $("#enterOfferTel").val();

    // 收集项目跟踪洽谈（第二个tab）
    editData['investRmb'] = $("#investRmb").val();
    editData['investDollar'] = $("#investDollar").val();
    editData['investRatio'] = $("#investRatio").val();
    editData['countRmb'] = $("#invest").val();
    editData['isBigPro'] = $("input[type='radio'][name='isBigPro']:checked").val();
    editData['bigProCom'] = $("#bigProCom").val();
    editData['bigProTime'] = $("#bigProTime").val();
    // 项目备案表数组
    var j = 0;
    $(".handleItem").each(function (index,element){
        if($(element).find("input[type='hidden'][name='attachId']").val() === undefined){
            editData['attachId'+j] = "";
        } else {
            editData['attachId'+j] = $(element).find("input[type='hidden'][name='attachId']").val();
        }
        editData['attachUrl'+j] = $(element).find("a").attr("url");
        editData['attachName'+j] = $(element).find("a").text();
        j ++ ;
    });
    editData['attachCount'] = j;
    editData['leader'] = $("#leader").val();
    // 责任单位及相关责任人信息数组
    var k = 0;
    $(".addZr").each(function (index,element){
        editData['unitId'+k] = $(element).find("input[type='hidden'][name='unitLiableId']").val();
        editData['unitLiable'+k] = $(element).find("#liable").val();
        editData['unitName'+k] = $(element).find("input[name='unitName']").val();
        editData['unitTel'+k] = $(element).find("input[name='unitTel']").val();
        k ++ ;
    });
    editData['unitCount'] = k;
    // 项目地址及用地数组
    var l = 0;
    $(".addXm").each(function (index,element){
        editData['areaId'+l] = $(element).find("input[type='hidden'][name='proAreaId']").val();
        editData['areaAddr'+l] = $(element).find("#addr").val();
        editData['areaUseType'+l] = $(element).find("#useType").val();
        editData['areaUseArea'+l] = $(element).find("#useArea").val();
        l ++ ;
    });
    editData['areaCount'] = l;
    // 建议实施方式数组
    var m = 0;
    $("input[name='adviseOpeType'][type='checkbox']:checked").each(function (index,element){
        var index = $("input[name='adviseOpeType'][type='checkbox']").index(element);
        editData['adviseId'+m] = $(element).val();
        editData['adviseName'+m] = index+1;
        m ++ ;
    });
    editData['adviseCount'] = m;
    editData['firstTalkTime'] = $("#firstTalkTime").val();
    editData['isMerge'] = $("#isMerge").val();
    editData['progress'] = $("textarea[name='progress']").val();
    editData['question'] = $("textarea[name='question']").val();
    editData['nextStep'] = $("textarea[name='nextStep']").val();
    editData['isVisit'] = $("input[type='radio'][name='isVisit']:checked").val();
    editData['visitLv'] = $("#visitLv").val();
    editData['proMeetTime'] = $("#proMeetTime").val();
    editData['proViewTime'] = $("#proViewTime").val();
    editData['contractTime'] = $("#contractTime").val();
    // 项目合同类型数组
    var n = 0;
    $("input[name='contractType'][type='checkbox']:checked").each(function (index,element){
        var index = $("input[name='contractType'][type='checkbox']").index(element);
        editData['htId'+n] = $(element).val();
        editData['htName'+n] = index+1;
        n ++ ;
    });
    editData['htCount'] = n;

    // 收集项目履约（第三个tab）
    editData['proConcatCom'] = $("#proConcatCom").val();
    editData['regComName'] = $("#regComName").val();
    editData['regComTime'] = $("#regComTime").val();
    editData['regInvest'] = $("#regInvest").val();
    editData['regNo'] = $("#regNo").val();
    editData['proContractTime'] = $("#proContractTime").val();
    editData['proRegTime'] = $("#proRegTime").val();
    editData['proType'] = $("input[type='radio'][name='proType']:checked").val();
    editData['regBigProTime'] = $("#regBigProTime").val();
    editData['regedBigProTime'] = $("#regedBigProTime").val();
    editData['controlCom'] = $("#controlCom").val();
    editData['proConventionType'] = $("input[type='radio'][name='proConventionType']:checked").val();
    editData['isMergeT'] = $("#isMergeT").val();
    editData['proConventionInfo'] = $("textarea[name='proConventionInfo']").val();
    editData['nextAdvise'] = $("textarea[name='nextAdvise']").val();

    this.normalProjectEditData = editData;
};

/**
 * 平台审核，提交成功，刷新页面
 */
NormalProjectInfo.audit = function (proId) {
    var data = {};
    data['from'] = "1";
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
NormalProjectInfo.close = function () {
    location.href = Feng.ctxPath + '/normalProject';
};

/**
 * 验证数据
 */
NormalProjectInfo.validate = function () {
    var bootstrapValidator = $("#normalProjectEditForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if(!bootstrapValidator.isValid()){
        return false;
    }

    /*var comCount = $(".addTzf").length;
    if (comCount >= 1) {
        Feng.initValidator("tzfCompanyInfo", NormalProjectInfo.validateTzfFields);
        var sss = $("#tzfCompanyInfo").data('bootstrapValidator');
        sss.validate();
        if (!sss.isValid()) {
            return false;
        }
    }
    var zrCount = $(".addZr").length;
    if (zrCount >= 1) {
        Feng.initValidator("peoject3", NormalProjectInfo.validateZrFields);
        var sss = $("#peoject3").data('bootstrapValidator');
        sss.validate();
        if (!sss.isValid()) {
            return false;
        }
    }
    var xmCount = $(".addXm").length;
    if (xmCount >= 1) {
        Feng.initValidator("projectAddress", NormalProjectInfo.validateXmFields);
        var sss = $("#projectAddress").data('bootstrapValidator');
        sss.validate();
        if (!sss.isValid()) {
            return false;
        }
    }*/
    return true;
};

/**
 * 验证数据，原生态，无污染
 */
NormalProjectInfo.validate4JS = function () {
    this.clearData();
    this.collectData();
    // 验证基本表单
    if(this.normalProjectEditData.name.trim() == ""){
        $("#nameTip").html("投资项目名称不能为空");
        return false;
    } else {
        var nameValid = true;
        var ajax = new $ax(Feng.ctxPath + "/normalProject/checkNormalProject", function(data){
            if(data.valid === false) {
                $("#nameTip").html("该项目名称已经存在");
                nameValid = false;
            }
        },function(data){
            Feng.error("无法验证项目名称重复!" + data.responseJSON.message + "!");
            nameValid = false;
        });
        ajax.set({proId:this.normalProjectEditData.id, name:this.normalProjectEditData.name});
        ajax.start();
        if(nameValid === false) {
            return false;
        }
    }

    if(this.normalProjectEditData.comCount < 1){
        $("#comTip").html("至少要有1个投资方");
        return false;
    } else if(this.normalProjectEditData.comCount > 5) {
        $("#comTip").html("最多只能有5个投资方");
        return false;
    } else {
        for (var i = 0; i < this.normalProjectEditData.comCount; i++){
            if(this.normalProjectEditData['comName' + i].trim() == ""){
                $("#comTip").html("公司名称不能为空");
                return false;
            }
            if(this.normalProjectEditData['comAuthor' + i].trim() == ""){
                $("#comTip").html("公司联系人不能为空");
                return false;
            }
            if(this.normalProjectEditData['comAddr' + i].trim() == ""){
                $("#comTip").html("公司地址不能为空");
                return false;
            }
            if(this.normalProjectEditData['comTel' + i].trim() == ""){
                $("#comTip").html("公司联系人电话不能为空");
                return false;
            } else if(!/(^1[34578]\d{9}$)$|(\d{3}-\d{8}|\d{4}-\d{7})/.test(this.normalProjectEditData['comTel' + i].trim())) {
                $("#comTip").html("公司联系人电话号码格式不正确");
                return false;
            }
            if(this.normalProjectEditData['comContent' + i].trim() == ""){
                $("#comTip").html("公司简介不能为空");
                return false;
            } else if(this.normalProjectEditData['comContent' + i].trim().length > 600) {
                $("#comTip").html("公司简介不能超过600字");
                return false;
            }
        }
    }
    if(this.normalProjectEditData.infoDesc.trim().length > 500){
        $("#infoDescTip").html("备注不能超过500字");
        return false;
    }
    if(this.normalProjectEditData.content.trim() == ""){
        $("#contentTip").html("投资建设内容不能为空");
        return false;
    } else if(this.normalProjectEditData.content.trim().length > 600){
        $("#contentTip").html("投资建设内容不能超过600字");
        return false;
    }
    if(this.normalProjectEditData.cyCount < 1){
        $("#categoryTip").html("请勾选至少1个归属产业");
        return false;
    }
    if(this.normalProjectEditData.tel.trim() == ""){

    } else if(!/(^1[34578]\d{9}$)$|(\d{3}-\d{8}|\d{4}-\d{7})/.test(this.normalProjectEditData.tel.trim())) {
        $("#infoProviderTip").html("信息提供者联系电话号码格式不正确");
        return false;
    }
    if(this.normalProjectEditData.enterOfferName.trim() == ""){
        $("#entProviderTip").html("企业联系者不能为空");
        return false;
    }
    if(this.normalProjectEditData.enterOfferTel.trim() == ""){
        $("#entProviderTip").html("企业提供者联系电话不能为空");
        return false;
    } else if(!/(^1[34578]\d{9}$)$|(\d{3}-\d{8}|\d{4}-\d{7})/.test(this.normalProjectEditData.enterOfferTel.trim())) {
        $("#entProviderTip").html("企业提供者联系电话号码格式不正确");
        return false;
    }
    for (var i = 0; i < this.normalProjectEditData.unitCount; i++){
        if(this.normalProjectEditData['unitLiable' + i].trim() == ""){
            $("#zrTip").html("责任单位不能为空");
            return false;
        }
        if(this.normalProjectEditData['unitTel' + i].trim() == ""){

        } else if(!/(^1[34578]\d{9}$)$|(\d{3}-\d{8}|\d{4}-\d{7})/.test(this.normalProjectEditData['unitTel' + i].trim())) {
            $("#zrTip").html("责任人电话号码格式不正确");
            return false;
        }
    }
    for (var i = 0; i < this.normalProjectEditData.areaCount; i++){
        if(this.normalProjectEditData['areaAddr' + i].trim() == ""){
            $("#xmTip").html("项目地址不能为空");
            return false;
        }
    }
    return true;
};

/**
 * 提交项目全部表单，刷新跟踪详情页
 */
NormalProjectInfo.editSubmit = function() {
    $(".tips").html("");
    $(".tips").hide();
    // if(!this.validate()){
    if(!this.validate4JS()){
        $(".tips").show();
        Feng.error("表单错误！请检查后提交");
        return;
    }
    // this.clearData();
    // this.collectData();
    //console.log(this.normalProjectEditData);
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/normalProject/add", function(data){
        Feng.success("修改成功!");
        location.reload();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.normalProjectEditData);
    ajax.start();
};

/**
 * 初始化操作日志表格的列
 */
NormalProjectInfo.initOperationLogColumn = function () {
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
NormalProjectInfo.openOperationLogDetail = function (row) {
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

/**
 * 鼠标移到单选框显示产业
 */
$(".helpImg").hover(function () {
    $(this).next().css("display","block");
},function () {
    $(this).next().css("display","none");
});

var uploadFile;

$(function() {
    // 给表单加校验
    //Feng.initValidator("normalProjectEditForm", NormalProjectInfo.validateFields);

    $("#uploadCreate").click(function () {
        if(uploadFile==undefined){
            uploadFile=new UploadFile("#picker","#thelist","/zateLand/upload");
        }
    });
    $("#uploadCreate").trigger("click");
    //锚点显示
    setTimeout(function(){
        $(".uploadCreate").click(function(){
            $("#anchorSkip3").show();
            $("#anchorSkip4").hide();
            $("#anchorSkip2").hide();
        });
    },50);
    setTimeout(function(){
        $("#projectBase1").click(function(){
            $("#anchorSkip3").hide();
            $("#anchorSkip4").hide();
            $("#anchorSkip2").show();
        });
    },50);
    setTimeout(function(){
        $("#projectBase2").click(function(){
            $("#anchorSkip3").hide();
            $("#anchorSkip4").show();
            $("#anchorSkip2").hide();
        });
    },50);

    // 监控投资额
    $("#investRmb").bind('input propertychange', function() {
        NormalProjectInfo.calculateInvest();
    });
    $("#investDollar1").bind('input propertychange', function() {
        $("#investDollar").val(Number($("#investDollar1").val()));
        NormalProjectInfo.calculateInvest();
    });
    $("#investRatio").bind('input propertychange', function() {
        NormalProjectInfo.calculateInvest();
    });

    // 金额面积不能输入复数
    $("input[type='number']").bind('input propertychange', function() {
        this.value = this.value.replace(/\-/g,"");
    });

    // 移除子表单模板（投资方公司、责任单位及相关责任人信息、项目地址及用地）
    var addTzf = $(".addTzf")[0];
    NormalProjectInfo.tzf = addTzf;
    addTzf.remove();
    var addZr = $(".addZr")[0];
    NormalProjectInfo.zr = addZr;
    addZr.remove();
    var addXm = $(".addXm")[0];
    NormalProjectInfo.xm = addXm;
    addXm.remove();

    // 控制按钮和表单
    $("#submit").hide();
    $("input").attr("disabled",true);
    $("textarea").attr("disabled",true);
    $("select").attr("disabled",true);
    $(".formCtrl").hide();
    $(".radioItem").addClass("li-disable");
    $(".checkboxItem").addClass("li-disable");

    //重大项目提示
    $(".weightProjectTips").hover(function(){
        $(".projectTips").show();
    },function(){
        $(".projectTips").hide();
    });
    $(".checkboxChecked li").click(function(){
        $(this).toggleClass("active");
        // $(this).children("input").trigger('click');
        if($(this).hasClass("active")){
            $(this).children("input").prop("checked", "checked");
        }else{
            $(this).children("input").removeAttr("checked");
            // $(this).children().css("color","#35a6d6");
        }
    });
    //  给选中的产业类别加样式
    $(".radioChecked").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
        $(this).children("input").prop("checked","checked");
        $(this).siblings().children("input").removeAttr("checked");
    });
    setTimeout(function(){

    },50);
    //给建议实施方式加选中样式
    $(".adviseOpeType li").click(function(){
        $(this).toggleClass("active");
        if($(this).hasClass("active")){
            $(this).children().prop("checked", "checked");
        }else{
            $(this).children().removeAttr("checked");
            // $(this).children().css("color","#35a6d6");
        }
    });
    //给项目合同类型加选中样式
    $(".contractTypeItem li").click(function(){
        $(this).toggleClass("active");
        if($(this).hasClass("active")){
            $(this).children().prop("checked", "checked");
        }else{
            $(this).children().removeAttr("checked");
            // $(this).children().css("color","#35a6d6");
        }
    });

    // 回显常规投资项目信息
    var status = $("input[name='status']").val();
    $("#status").val(status);
    var followName = $("input[name='followName']").val();
    var followTel = $("input[name='followTel']").val();
    $("#followUser").val(followName + " " + followTel);
    $(".addTzf").each(function (index,element){
        var comType = $(element).find("input[name='comType']").val();
        $(element).find("#comType").val(comType);
    });

    // 回显项目基本信息情况
    $("#comCount").val($(".addTzf").length);
    var fromArea = $("input[name='fromArea'][type='hidden']").val();
    $("input[name='fromArea'][type='radio']")[fromArea-1].checked=true;
    $("input[name='fromArea'][type='radio']")[fromArea-1].parentNode.classList.add("active");
    var investType = $("input[name='investType'][type='hidden']").val();
    $("input[name='investType'][type='radio']")[investType-1].checked=true;
    $("input[name='investType'][type='radio']")[investType-1].parentNode.classList.add("active");
    $("input[type='hidden'][name='cyId']").each(function (index,element) {
        var cyId = $(element).val();
        var cyName = $(element).next().val();
        $("input[name='category'][type='checkbox']")[cyName-1].checked=true;
        $("input[name='category'][type='checkbox']")[cyName-1].value= cyId;
        $("input[name='category'][type='checkbox']")[cyName-1].parentNode.classList.add("active");
    });
    var leadCategory = $("input[name='leadCategory'][type='hidden']").val();
    if (leadCategory == "1") {
        $("input[name='leadCategory'][type='radio']")[0].checked=true;
        $("input[name='leadCategory'][type='radio']")[0].parentNode.classList.add("active");
    } else if (leadCategory == "0") {
        $("input[name='leadCategory'][type='radio']")[1].checked=true;
        $("input[name='leadCategory'][type='radio']")[1].parentNode.classList.add("active");
    }

    // 回显项目跟踪洽谈情况
    var investRmb = Number($("#investRmb").val());//人民币（亿元）
    var investDollar = Number($("#investDollar").val());//美元（万元）
    var investRatio = Number($("#investRatio").val());//汇率
    var investRmb2 = investDollar/10000*investRatio;//美元换算人民币 人民币（亿元）
    var invest = investRmb2+investRmb;//总投资额 人民币（亿元）
    $("#invest").val(invest);
    $("#investRmb2").val(investRmb2);
    var isBigPro = $("input[name='isBigPro'][type='hidden']").val();
    if(isBigPro == '1') {
        $("input[name='isBigPro'][type='radio']")[0].checked=true;
        $("input[name='isBigPro'][type='radio']")[0].parentNode.classList.add("active");
    } else if(isBigPro == '0') {
        $("input[name='isBigPro'][type='radio']")[1].checked=true;
        $("input[name='isBigPro'][type='radio']")[1].parentNode.classList.add("active");
        $("#addZd").hide();
    }
    dateFormatYMD($("#bigProTime"));
    timeSingle($("#bigProTime"),"YYYY-MM-DD");
    $(".handleItem").each(function (index, element) {
        var url = $(element).find("a").attr("url");
        $(element).find("a").attr("href", Feng.fileSer + url);
    });
    $(".addXm").each(function (index,element) {
        var useType = $(element).find("input[type='hidden'][name='useType']").val();
        $(element).find("#useType").val(useType);
    });
    $("input[type='hidden'][name='opeTypeId']").each(function (index,element) {
        var opeTypeId = $(element).val();
        var opeTypeName = $(element).next().val();
        $("input[name='adviseOpeType'][type='checkbox']")[opeTypeName-1].checked=true;
        $("input[name='adviseOpeType'][type='checkbox']")[opeTypeName-1].value= opeTypeId;
        $("input[name='adviseOpeType'][type='checkbox']")[opeTypeName-1].parentNode.classList.add("active");
    });
    $("input[type='hidden'][name='contractTypeId']").each(function (index,element) {
        var contractTypeId = $(element).val();
        var contractTypeName = $(element).next().val();
        $("input[name='contractType'][type='checkbox']")[contractTypeName-1].checked=true;
        $("input[name='contractType'][type='checkbox']")[contractTypeName-1].value= contractTypeId;
        $("input[name='contractType'][type='checkbox']")[contractTypeName-1].parentNode.classList.add("active");
    });
    dateFormatYMD($("#firstTalkTime"));
    timeSingle($("#firstTalkTime"),"YYYY-MM-DD");
    dateFormatYMDHMS($("#updateTime"));
    var isVisit = $("input[name='isVisit'][type='hidden']").val();
    if(isVisit == '1') {
        $("input[name='isVisit'][type='radio']")[0].checked=true;
        $("input[name='isVisit'][type='radio']")[0].parentNode.classList.add("active");
    } else if(isVisit == '0') {
        $("input[name='isVisit'][type='radio']")[1].checked=true;
        $("input[name='isVisit'][type='radio']")[1].parentNode.classList.add("active");
        $("#visitLvDiv").hide();
    }
    dateFormatYMD($("#proMeetTime"));
    timeSingle($("#proMeetTime"),"YYYY-MM-DD");
    dateFormatYMD($("#proViewTime"));
    timeSingle($("#proViewTime"),"YYYY-MM-DD");
    dateFormatYMD($("#contractTime"));
    timeSingle($("#contractTime"),"YYYY-MM-DD");

    // 回显项目履约情况
    dateFormatYMD($("#regComTime"));
    timeSingle($("#regComTime"),"YYYY-MM-DD");
    dateFormatYMD($("#proContractTime"));
    timeSingle($("#proContractTime"),"YYYY-MM-DD");
    dateFormatYMD($("#proRegTime"));
    timeSingle($("#proRegTime"),"YYYY-MM-DD");
    var proType = $("input[name='proType'][type='hidden']").val();
    if(proType == '1') {
        $("input[name='proType'][type='radio']")[0].checked=true;
        $("input[name='proType'][type='radio']")[0].parentNode.classList.add("active");
    } else if(proType == '2') {
        $("input[name='proType'][type='radio']")[1].checked=true;
        $("input[name='proType'][type='radio']")[1].parentNode.classList.add("active");
    }
    dateFormatYMD($("#regBigProTime"));
    timeSingle($("#regBigProTime"),"YYYY-MM-DD");
    dateFormatYMD($("#regedBigProTime"));
    timeSingle($("#regedBigProTime"),"YYYY-MM-DD");
    var proConventionType = $("#addLyFlag").val();
    if(proConventionType === '2') {
        $("input[name='proConventionType'][type='radio']")[0].checked=true;
        $("input[name='proConventionType'][type='radio']")[0].parentNode.classList.add("active");
        $("#addLy").hide();
    } else if(proConventionType === '1') {
        $("input[name='proConventionType'][type='radio']")[1].checked=true;
        $("input[name='proConventionType'][type='radio']")[1].parentNode.classList.add("active");
    } else{
        $("#addLy").hide();
    }
    dateFormatYMDHMS($("#proconventionUpdateTime"));

    // 显示项目操作日志table
    var defaultColunms = NormalProjectInfo.initOperationLogColumn();
    var proId = $("#id").val();
    var table = new BSTable(NormalProjectInfo.id, "/proHistory/proHistorys?folType=1&proId="+proId, defaultColunms);
    table.setPaginationType("server");
    NormalProjectInfo.table = table.init();
    $('#NormalProjectOperationLogTable').on('click-row.bs.table', function (e, row, element) {
        NormalProjectInfo.openOperationLogDetail(row);
    });

});
