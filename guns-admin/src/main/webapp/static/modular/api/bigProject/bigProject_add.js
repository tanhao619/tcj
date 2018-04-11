/**
 * 初始化重大项目详情对话框
 */
var BigProjectAdd = {
    bigProjectAddData : {}, //提交的表单数据
    hyForm : null, // 具体行业分类子表单
    zrForm : null, // 责任单位及相关责任人信息子表单
    jsForm : null, // 拟建设地点子表单
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
                            proId: null
                        }
                    }
                }
            },
        },
        category: {
            container:'#categoryTip',
            validators: {
                choice: {
                    min: 1,
                    max: 3,
                    message: '请选择归属产业'
                }
            }
        },
        hyCount: {
            trigger:"change",
            container:'#hyFormTip',
            validators: {
                greaterThan: {
                    value: 1,
                    message: '至少要有1个具体行业分类'
                }
            }
        },
        packName: {
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
                /*lessThan: {
                    field: '#investRmb',
                    message: '拆迁费不能高于投资额（人民币）'
                }*/
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
                    message: '请填写正规的匡算投资额'
                }
            }
        },
        govArea: {
            container:'#njsFormTip',
            validators: {
                numeric: {
                    message: '请填写数字'
                },
                greaterThan: {
                    value: 0,
                    message: '面积必须非负'
                }
            }
        },
        collectArea: {
            container:'#njsFormTip',
            validators: {
                numeric: {
                    message: '请填写数字'
                },
                greaterThan: {
                    value: 0,
                    message: '面积必须非负'
                }
            }
        },
        farmArea: {
            container:'#njsFormTip',
            validators: {
                numeric: {
                    message: '请填写数字'
                },
                greaterThan: {
                    value: 0,
                    message: '面积必须非负'
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
        }
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
 * 初始化新增页
 */
BigProjectAdd.init = function () {
    // 初始化表单--添加表单验证
    Feng.initValidator("bigProjectAddForm", BigProjectAdd.validateFields);

    // 初始化表单--定义子表单节点并移除
    var hyForm = $(".hyForm")[0];
    BigProjectAdd.hyForm = hyForm;
    hyForm.remove();
    var zrForm = $(".zrForm")[0];
    BigProjectAdd.zrForm = zrForm;
    zrForm.remove();
    var jsForm = $(".jsForm")[0];
    BigProjectAdd.jsForm = jsForm;
    jsForm.remove();

    // 初始化表单--加上时间选择插件
    timeSingle($("#planStartTime"),"YYYY-MM-DD");
    timeSingle($("#planEndTime"),"YYYY-MM-DD");

    // 初始化表单--监控美元汇率
    $("#investRmb").bind('input propertychange', function() {
        BigProjectAdd.calculateInvest();
    });
    $("#investDollar1").bind('input propertychange', function() {
        $("#investDollar").val(Number($("#investDollar1").val()));
        BigProjectAdd.calculateInvest();
    });
    $("#investRatio").bind('input propertychange', function() {
        BigProjectAdd.calculateInvest();
    });
};

/**
 * 添加子表单
 *
 * @param childFormType 子表单类型（1：具体行业分类；2：责任单位及相关责任人信息；3：拟建设地点）
 */
BigProjectAdd.addChildForm = function(childFormType){
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
BigProjectAdd.delChildForm = function(this1, childFormType){
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
BigProjectAdd.openCalculateInvest = function(){
    $(".cal_wrap").show();
};

/**
 * 自动计算
 */
BigProjectAdd.calculateInvest = function () {
    var investRmb = Number($("#investRmb").val());//人民币（亿元）
    var investDollar1 = Number($("#investDollar1").val());//美元（万元）
    var investRatio = Number($("#investRatio").val());//汇率
    var investRmb2 = investDollar1/10000*investRatio;
    $("#investRmb2").val(investRmb2);
    var invest = investRmb2+investRmb;
    $("#invest").val(invest).change();
};

/**
 * 关闭合算弹框
 */
BigProjectAdd.closeCalculateModel =function(){
    $(".cal_wrap").hide();
};

/**
 * 清除表单数据
 */
BigProjectAdd.clearData = function() {
    this.bigProjectAddData = {};
};

/**
 * 收集表单数据
 */
BigProjectAdd.collectData = function() {
    var addData = {};
    addData['name'] = $("#name").val();

    // 归属行业数组
    var n = 0;
    $("input[type='checkbox'][name='category']:checked").each(function(index, element){
        addData['cyId'+n] = '';
        addData['cyName'+n] = element.value;
        n ++ ;
    });
    addData['cyCount'] = n;

    // 具体行业分类数组
    var i = 0;
    $(".hyForm").each(function (index,element){
        addData['cateName'+i] = $(element).find("input[name='cateName']").val();
        i ++ ;
    });
    addData['cateCount'] = i;

    // 策划包装定位数组
    var j = 0;
    $("input[type='checkbox'][name='packName']:checked").each(function(index, element){
        addData['packName'+j] = element.value;
        j ++ ;
    });
    addData['packCount'] = j;

    // 责任单位及相关责任人信息数组
    var k = 0;
    $(".zrForm").each(function (index,element){
        addData['unitLiable'+k] = $(element).find("input[name='unitLiable']").val();
        addData['unitName'+k] = $(element).find("input[name='unitName']").val();
        addData['unitTel'+k] = $(element).find("input[name='unitTel']").val();
        k ++ ;
    });
    addData['unitCount'] = k;

    // 建议实施方式数组
    var l = 0;
    $("input[type='checkbox'][name='adviseName']:checked").each(function(index, element){
        addData['adviseName'+l] = element.value;
        l ++ ;
    });
    addData['adviseCount'] = l;

    addData['planStartTime'] = $("#planStartTime").val();
    addData['planEndTime'] = $("#planEndTime").val();
    addData['planProcess'] = $("#planProcess").val();
    addData['leader'] = $("#leader").val();
    addData['investRmb'] = Number($("#investRmb").val());
    addData['investMvRmb'] = Number($("#investMvRmb").val());
    addData['investDollar'] = Number($("#investDollar").val());
    addData['investRatio'] = Number($("#investRatio").val());
    addData['countRmb'] = Number($("#invest").val());

    // 拟建设地点数组
    var m = 0;
    $(".jsForm").each(function (index,element){
        addData['buildPlaceName'+m] = $(element).find("input[name='buildPlaceName']").val();
        m ++ ;
    });
    addData['bpCount'] = m;

    addData['content'] = $("#content").val();
    addData['govArea'] = $("#govArea").val();
    addData['collectArea'] = $("#collectArea").val();
    addData['farmArea'] = $("#farmArea").val();
    addData['workProcess'] = $("#workProcess").val();
    addData['infoDesc'] = $("#infoDesc").val();

    this.bigProjectAddData = addData;
};

/**
 * 验证数据
 */
BigProjectAdd.validate = function () {
    var bootstrapValidator = $("#bigProjectAddForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if(!bootstrapValidator.isValid()){
        return false;
    }
    var hyCount = $(".hyForm").length;
    if(hyCount >= 1 ){
        //console.log(hyCount);
        Feng.initValidator("cateForm", BigProjectAdd.validateHyFields);
        var cateBootstrapValidator = $("#cateForm").data('bootstrapValidator');
        //console.log(cateBootstrapValidator.isValid());
        cateBootstrapValidator.validate();
        if(!cateBootstrapValidator.isValid()){
            return false;
        }
    }
    var zrCount = $(".zrForm").length;
    if(zrCount >= 1 ){
        Feng.initValidator("zrForms", BigProjectAdd.validateZrFields);
        var unitLiableBootstrapValidator = $("#zrForms").data('bootstrapValidator');
        unitLiableBootstrapValidator.validate();
        if(!unitLiableBootstrapValidator.isValid()){
            return false;
        }
    }
    var jsCount = $(".jsForm").length;
    if(jsCount >= 1 ){
        Feng.initValidator("setPlace", BigProjectAdd.validateJsFields);
        var buildPlaceBootstrapValidator = $("#setPlace").data('bootstrapValidator');
        buildPlaceBootstrapValidator.validate();
        if(!buildPlaceBootstrapValidator.isValid()){
            return false;
        }
    }
    return true;
};

/**
 * 新增重大包装项目表单提交，跳转到列表页
 */
BigProjectAdd.addSubmit = function () {
    if (!this.validate()) {
        Feng.error("表单错误！请检查后提交");
        return;
    }
    this.clearData();
    this.collectData();
    /*console.log(this.bigProjectAddData);
    return;*/
    var ajax1 = new $ax(Feng.ctxPath + "/bigProject/add", function (data) {
        $("#submit").attr("disabled",true);
        Feng.success("成功!");
        location.href = Feng.ctxPath + '/normalProject';
    }, function (data) {
        Feng.error("失败!" + data.responseJSON.message + "!");
        return;
    });
    ajax1.set(this.bigProjectAddData);
    ajax1.start();
};

/**
 * 新增重大包装项目表单取消
 */
BigProjectAdd.addCancel = function () {
    location.href=Feng.ctxPath + '/normalProject';
};

$(function() {

    BigProjectAdd.init();

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

    $("input[name='category'],input[name='adviseName'],input[name='packName']").click(function(event){
        event.stopPropagation();
    });

});
