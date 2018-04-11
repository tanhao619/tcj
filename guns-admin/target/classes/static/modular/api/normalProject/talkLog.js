/**
 * 项目洽谈列表
 */
var ProTalk = {
    id: "ProTalkTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    normalProjectId: parent.document.getElementById("id").value
};

//$('#ProTalkTable').bootstrapTable({
   // editable: false,//开启编辑模式
    // onEditableHidden: function(field, row, $el, reason) { // 当编辑状态被隐藏时触发
    //     if(reason === 'save') {
    //         var $td = $el.closest('tr').children();
    //         $td.eq(-1).html((row.price*row.number).toFixed(2));
    //         $el.closest('tr').next().find('.editable').editable('show'); //编辑状态向下一行移动
    //     } else if(reason === 'nochange') {
    //         $el.closest('tr').next().find('.editable').editable('show');
    //     }
    // },
    // onClickRow: function (row, tr) {
    //     // memberID = row.id;
    // }
//});

/**
 * 初始化表格的列
 */
ProTalk.initColumn = function () {
    return [
        {title: '序号', formatter: function (value, row, index) {return index+1;}, visible: true, align: 'center', valign: 'middle'},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '进展情况', field: 'progress',visible: true, align: 'center', valign: 'middle'},
        {title: '存在问题', field: 'question', visible: true, align: 'center', valign: 'middle'},
        {title: '下一步举措', field: 'nextStep', visible: true, align: 'center', valign: 'middle'},
        {title: '是否需要拜访', field: 'isVisit', visible: true, align: 'center', valign: 'middle'},
        {title: '拜访所需领导层级', field: 'visitLv', visible: true, align: 'center', valign: 'middle'},
        {title: '', field: 'id', visible: true, align: 'center', valign: 'middle', formatter: ProTalk.formatOperate},
        {title: '操作人员', field: 'userName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '更新时间', field: 'updateTime', visible: true, align: 'center', valign: 'middle', sortable: true}
    ]

};

/**
 * 格式化操作列
 */
ProTalk.formatOperate = function (val, row, index) {
    var progress = row.progress;
    var id = row.id;
    var btn_edit = "<div class='btn_wrap'><button class='btn opearteLog'   onclick='ProTalk.editOne(this,"+id+")'><img src='/static/img/icon-edit1.png'></button></div>";
    var btn_del = "<div class='delete_wrap'><button class='btn opearteLog'   onclick='ProTalk.delOne(" + id + ")'><img src='/static/img/icon-delete1.png'></button></div>";
    return btn_edit + "&nbsp;&nbsp;&nbsp;" + btn_del;
};

/**
 * 修改一行，该行可编辑，出现√
 */
ProTalk.editOne = function (this1,row1) {
    //console.log(row1);
    var rowId = row1;
    var prevTd = $(this1).parent().parent().prevAll("td").not(":last");
    var parentClass = prevTd.parent();
    $(this1).replaceWith("<button class='btn opearteLog' onclick='ProTalk.updOne(this,"+rowId+")'><img src='/static/img/icon-gou.png'></button>");
    $.each(prevTd,function(index){
        if(index == 1){
            var isVisitStr = $(this).html();
            if (isVisitStr === "是"){
                $(this).html('<select class="inputColor"><option value="1" selected>是</option><option value="0">否</option></select>');
            } else {
                $(this).html('<select class="inputColor"><option value="1">是</option><option value="0" selected>否</option></select>');
            }
        }
        else{
            $(this).html('<input type="text" value="'+$(this).html()+'" class="inputColor">');
        }
 //    console.log( $(this).html($(this).find('input[type=text]').val()));

   });
};
function print(value){
    console.log($(value).val());
    return $(value).val();
}

/**
 * 删除一行
 */
ProTalk.delOne = function (id) {
    var operation = function () {
        var ajax1 = new $axRest(Feng.ctxPath + "/proTalk/delete/"+id, "post", function (data) {
            Feng.success("成功！");
            ProTalk.table.refresh();
            setTimeout(function(){
                $(".conventionLog table tr").hover(function(){
                    $(this).find(".opearteLog").show();
                },function(){
                    $(this).find(".opearteLog").hide();
                })
            },50);
        }, function (data) {
            Feng.error("失败!" + data.responseJSON.message + "!");
            return;
        });
        ajax1.start();
    };
    Feng.confirm("是否删除该条洽谈记录？", operation);
};

/**
 * 更新一行
 */
ProTalk.updOne = function (this12, row12) {
    var editData = {};
    // console.log("当前id："+row12);
    var prevTd1 = $(this12).parent().parent().prevAll("td").not(":last");
    var str = [];
    $.each(prevTd1,function(index){
        //console.log($(this).children(".inputColor").val());
        var single = $(this).children(".inputColor").val();
        str.push(single);
        //$(this).html(single);
    });
    editData.id = row12;
    editData.proId = ProTalk.normalProjectId;
    editData.progress = str[4];
    editData.question = str[3];
    editData.nextStep = str[2];
    editData.isVisit = str[1];
    editData.visitLv = str[0];
    var ajax1 = new $axRest(Feng.ctxPath + "/proTalk/update", "post", function (data) {
        Feng.success("成功！");
        $(this12).replaceWith("<button class='btn opearteLog'   onclick='ProTalk.editOne(this)'><img src='/static/img/icon-edit1.png'></button>");
        ProTalk.table.refresh();
        setTimeout(function(){
            $(".conventionLog table tr").hover(function(){
                $(this).find(".opearteLog").show();
            },function(){
                $(this).find(".opearteLog").hide();
            })
        },50);

    }, function (data) {
        Feng.error("失败!" + data.responseJSON.message + "!");
        return;
    });
    ajax1.set(editData);
    ajax1.start();

    var selected = $('#' + this.id).bootstrapTable('getSelections');
};

/**
 * 关闭窗口
 */
ProTalk.cancel = function () {
    parent.layer.close(window.parent.NormalProjectInfo.layerIndex);
};

$(function () {
    var id =parent.document.getElementById("id").value;
    var opearUpdateBtn = parent.document.getElementById("opearUpdateBtn").value;
    var defaultColunms = ProTalk.initColumn();
    var table = new BSTable(ProTalk.id, "/proTalk/proTalks?proId="+id, defaultColunms);
    table.setPaginationType("server");
    ProTalk.table = table.init();
    $("#ProTalkTable").on("post-body.bs.table",function () {
        changeType("#ProTalkTable",["1","0"],["是","否"],4);
    });
    if(opearUpdateBtn == "true"){
        setTimeout(function(){
            $(".conventionLog table tr").hover(function(){
                $(this).find(".opearteLog").show();
            },function(){
                $(this).find(".opearteLog").hide();
            })
        },100);
    }

});

