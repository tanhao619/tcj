/**
 * 初始化常规项目详情对话框
 */
var NormalProjectAdd = {
    id : 0,
    normalProjectAddData : {},
    tzf : null, // 投资方表单
    validateFields: {
        name: {
            container:'#nameTip',
            validators: {
                notEmpty: {
                    message: '投资项目名称不能为空'
                },
                remote: {
                    //ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                    url: '/normalProject/checkNormalProject',//验证地址
                    message: '该项目名称已经存在',//提示消息
                    //delay :  2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                    type: 'POST',//请求方式
                    /**自定义提交数据，默认值提交当前input value */
                    data: function(validator) {
                        return {
                            name: $('#name').val(),
                            proId: null
                        }
                    }
                }
            },

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
        fromArea: {
            container:'#fromAreaTip',
            validators: {
                notEmpty: {
                    message: '请选择投资来源地归属'
                }
            }
        },
        investType: {
            container:'#investTypeTip',
            validators: {
                notEmpty: {
                    message: '请选择投资类型'
                }
            }
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
                    message: '企业联系者不能为空'
                }
            }
        },
        enterOfferTel:{
            container:'#entProviderTip',
            validators: {
                notEmpty: {
                    message: '企业联系者联系电话不能为空'
                },
                regexp:{
                    regexp:/(^1[34578]\d{9}$)$|(\d{3}-\d{8}|\d{4}-\d{7})/,
                    message:"企业联系者联系电话号码格式不正确"
                }
            }
        },
        comCount: {
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
        }
    }, //基本表单验证
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
    } //投资方公司表单验证
};

/**
 * 清除数据
 */
NormalProjectAdd.clearData = function() {
    this.normalProjectAddData = {};
};

/**
 * 收集数据
 */
NormalProjectAdd.collectData = function() {
    var addData = {};
    addData['name'] = $("#name").val();
    addData['content'] = $("#content").val();
    addData['fromArea'] = $("input[type='radio'][name='fromArea']:checked").val();
    addData['investType'] = $("input[type='radio'][name='investType']:checked").val();

    // 归属行业数组
    var j = 0;
    $("input[type='checkbox'][name='category']:checked").each(function(index, element){
        addData['cyId'+j] = '';
        addData['cyName'+j] = element.value;
        j ++ ;
    });
    addData['cyCount'] = j;

    addData['leadCategory'] = $("input[type='radio'][name='leadCategory']:checked").val();
    addData['author'] = $("#author").val();
    addData['tel'] = $("#tel").val();
    addData['enterOfferName'] = $("#enterOfferName").val();
    addData['enterOfferTel'] = $("#enterOfferTel").val();

    // 投资方公司数组
    var i = 0;
    $(".addTzf").each(function (index,element){
        addData['comName'+i] = $(element).find("input[name='comName']").val();
        addData['comAddr'+i] = $(element).find("input[name='comAddr']").val();
        addData['comAuthor'+i] = $(element).find("input[name='comAuthor']").val();
        addData['comTel'+i] = $(element).find("input[name='comTel']").val();
        addData['comType'+i] = $(element).find("#comType").val();
        addData['comContent'+i] = $(element).find("textarea[name='comContent']").val();
        i ++ ;
    });
    addData['comCount'] = i;

    addData['infoDesc'] = $("textarea[name='infoDesc']").val();
    addData['investRmb'] = 0;
    addData['investDollar'] = 0;
    addData['investRatio'] = 0;
    addData['countRmb'] = 0;
    this.normalProjectAddData = addData;
};

/**
 * 新增投资方公司信息表单
 */
NormalProjectAdd.addTzfTable = function(){
    var tzfTable = $(NormalProjectAdd.tzf).clone(true);
    $(".investmentCompanyInfo").append(tzfTable);
    var comCount = Number($("#comCount").val());
    $("#comCount").val(comCount + 1).change();
};

/**
 * 删除投资方公司信息表单
 */
NormalProjectAdd.deleteTzfTable = function(this1){
    var parentElement = this1.closest(".addTzf");
    parentElement.remove();
    var comCount = Number($("#comCount").val());
    $("#comCount").val(comCount - 1).change();
};

/**
 * 验证数据
 */
NormalProjectAdd.validate = function () {
    var bootstrapValidator = $("#normalProjectAddForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if(!bootstrapValidator.isValid()){
        return false;
    }
    var comCount = $(".addTzf").length;
    if(comCount >= 1 ){
        Feng.initValidator("investmentCompanyForm", NormalProjectAdd.validateTzfFields);
        var sss = $("#investmentCompanyForm").data('bootstrapValidator');
        sss.validate();
        if(!sss.isValid()){
            return false;
        }
    }
    return true;
};

/**
 * 新增常规投资项目表单提交，跳转到列表页
 */
NormalProjectAdd.addSubmit = function () {
    $(".tips").hide();
    if(!this.validate()){
        $(".tips").show();
        Feng.error("表单错误！请检查后提交");
        return;
    }
    NormalProjectAdd.clearData();
    NormalProjectAdd.collectData();
    /*console.log(this.normalProjectAddData);
    return;*/
    var ajax1 = new $ax(Feng.ctxPath + "/normalProject/add", function (data) {
        $("#submit").attr("disabled",true);
        Feng.success("成功!");
        location.href=Feng.ctxPath + '/normalProject';
    }, function (data) {
        Feng.error("失败!" + data.responseJSON.message + "!");
        return;
    });
    ajax1.set(NormalProjectAdd.normalProjectAddData);
    ajax1.start();
};

/**
 * 新增常规投资项目表单取消
 */
NormalProjectAdd.addCancel = function () {
    location.href=Feng.ctxPath + '/normalProject';
};

$(function() {
    // 给表单加校验
    Feng.initValidator("normalProjectAddForm", NormalProjectAdd.validateFields);

    /**
     * 鼠标移到单选框显示产业
     */
    $(".helpImg").hover(function () {
        $(this).next().css("display","block");
    },function () {
        $(this).next().css("display","none");
    });

    // 定义投资方空表单节点并移除
    var addTzf = $(".addTzf")[0];
    NormalProjectAdd.tzf = addTzf;
    addTzf.remove();

    // 给复选框加选中样式
    setTimeout(function(){
        $(".checkboxChoice li").click(function(){
            $(this).toggleClass("active");
            // $(this).children("input").trigger('click');
            if($(this).hasClass("active")){
                $(this).children().prop("checked", "checked");
            }else{
                $(this).children().removeAttr("checked");
            }
        });
    },50);

    // 给单选框选中样式
    $(".radioChecked").click(function(){
        $(this).addClass("active").siblings().removeClass("active");
        $(this).children("input").prop("checked","checked");
        $(this).siblings().children("input").removeAttr("checked");
        $(this).children("input").trigger('click');
    });

    // $("input[name='category'],input[name='investType'],input[name='fromArea'],input[name='leadCategory'] ").click(function(event){
    //     event.stopPropagation();
    // });

});
