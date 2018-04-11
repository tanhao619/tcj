/**
 * 角色管理的单例
 */
var Role = {
    id: "roleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    isSystem:''
};

/**
 * 初始化表格的列
 */
Role.initColumn = function () {
    var columns = [
        // {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '角色名称', field: 'name', align: 'center', valign: 'middle', sortable: false},
        {title: '角色类型', field: 'isSystem',  visible: true,align: 'center', valign: 'middle', sortable: false},
       /* {title: '上级角色', field: 'pName', align: 'center', valign: 'middle', sortable: true},*/
        {title: '所在科室', field: 'deptName', align: 'center', valign: 'middle', sortable: false},
        {title: '职位', field: 'duty', align: 'center', valign: 'middle', sortable: false},
        /*{title: '备注', field: 'tips', align: 'center', valign: 'middle', sortable: true}*/
        {title: '备注', field: 'remark', align: 'center', valign: 'middle', sortable: false},
        {title: '操作', field: 'opearte', align: 'center', valign: 'middle', sortable: false, formatter: Role.formatOperate}
        ];
    return columns;
};
Role.formatOperate = function (val, row, index) {
    var id = row.id;
    var account = row.account;
    var statusName = row.statusName;
    var btn_edit = "<div class='f_wrap wrap'  id='orit'>" +
        "<span class='' onclick='showRole(this,"+row.isSystem+")'>...</span>" +
        "<div class='overInfo roleInfo'  onmouseleave='hideRole(this)'>" +
        "<ul><li onclick='Role.openChangeRole("+id+")'>修改</li>" +
        "<li onclick='Role.delRole("+id+",this)'>删除</li></ul></div></div>";
    return btn_edit ;

}
function showRole(this1,isSystem){
     if(isSystem == 1){//当isSystem为1时，不能进行修改和删除
        return;
    }
     else{
        $(this1).siblings().show();
     }

}
function hideRole(this1){
    $(this1).hide();
}

/**
 * 检查是否选中
 */
Role.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Role.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加管理员
 */
Role.openAddRole = function () {
    var index = layer.open({
        type: 2,
        title: '添加角色',
        area: ['50%', '70%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/role/role_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改按钮时
 */
Role.openChangeRole = function (id) {
    // if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改角色',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/role/role_edit/' + id
        });
        this.layerIndex = index;
    // }
};

/**
 * 删除角色
 */
Role.delRole = function (id,roleName) {
    var parent1 = $(roleName).parents("td").siblings().first("td");
    var account = parent1.html();

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/role/remove", function () {
                Feng.success("删除成功!");
                Role.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("roleId", id);
            ajax.start();
        };

        Feng.confirm("是否删除角色 " + account + "?",operation);
    // }
};

/**
 * 权限配置
 */
Role.assign = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '权限配置',
            area: ['300px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/role/role_assign/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 搜索角色
 */
Role.search = function () {
    var queryData = {};
    queryData['roleName'] = $("#roleName").val();
    Role.table.refresh({query: queryData});
}

$(function () {
    var defaultColunms = Role.initColumn();
    var table = new BSTable(Role.id, "/role/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Role.table = table;
    $("#roleTable").on("load-success.bs.table",function () {
        $("#roleTable tr").each(function(){
           /* $(this).find("td:first").css('display','none');
            $(this).find("th:first").css('display','none');*/
            var firstValue= $(this).find("td:eq(1)").html();
            if(firstValue == '系统默认'){
                $(this).css({"background":"#f6f6f6","cursor":"not-allowed","color":"#333"});
                $(this).find("td").css({"background":"#f6f6f6","cursor":"not-allowed"});
              $(this).find("td:last").css("cursor",'not-allowed');
              $(this).find("td:last").children(".f_wrap").css("cursor",'not-allowed');
                $(this).find("td:last").children("span").off("click");
            }
        })
    });
    $("#roleTable").on("post-body.bs.table",function () {
        changeType("#roleTable",["1","2","3","4","-1"],["科员","科长","分管副局长","局长",""],3);
    });
    $("#roleTable").on("post-body.bs.table",function () {
        changeType("#roleTable",["1","0"],["系统默认","非系统默认"],1);
    });
});
