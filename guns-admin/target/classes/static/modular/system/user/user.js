/**
 * 系统管理--用户管理的单例对象
 */
var MgrUser = {
    id: "managerTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptid:0
};

/**
 * 初始化表格的列
 */
MgrUser.initColumn = function () {
    var columns = [
         /*{field: 'selectItem', radio: true},*/
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '账号', field: 'account', align: 'center', valign: 'middle', sortable: false},
        {title: '姓名', field: 'name', align: 'center', valign: 'middle', sortable: false},
        /*{title: '性别', field: 'sexName', align: 'center', valign: 'middle', sortable: true},*/
        {title: '角色', field: 'roleName', align: 'center', valign: 'middle', sortable: false},
        /*{title: '部门', field: 'deptName', align: 'center', valign: 'middle', sortable: true},*/
        {title: '邮箱', field: 'email', align: 'center', valign: 'middle', sortable: false},
        {title: '联系电话', field: 'phone', align: 'center', valign: 'middle', sortable: false},
        {title: '创建时间', field: 'createtime', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'statusName', align: 'center', valign: 'middle', sortable: false},
        {title: '操作', field: 'opearte', align: 'center', valign: 'middle', sortable: false, formatter: MgrUser.formatOperate}
    ];
    return columns;
};
MgrUser.formatOperate = function (val, row, index) {
    var id = row.id;
    var account = row.account;
    var statusName = row.statusName;
    var account = row.account;
    if (account == 'swgd' || account == 'bgszr'){// 收文/归档科员 或 办公室主任 只能修改
        var btn_edit = "<div class='f_wrap'>" +
            "<span class='' onclick='show(this)'>...</span>" +
            "<div class='overInfo'  onmouseleave='hide(this)'>" +
            "<ul><li onclick='MgrUser.openChangeUser("+id+")'>修改</li>";
        btn_edit +=
            "<li onclick='MgrUser.resetPwd("+id+")'>重置密码</li>";
        return btn_edit;
    }
    var btn_edit = "<div class='f_wrap'>" +
        "<span class='' onclick='show(this)'>...</span>" +
        "<div class='overInfo'  onmouseleave='hide(this)'>" +
        "<ul><li onclick='MgrUser.openChangeUser("+id+")'>修改</li>" +
        "<li onclick='MgrUser.delMgrUser("+id+",this)'>删除</li>";
        if ("冻结" == statusName){
            btn_edit += "<li onclick='MgrUser.unfreeze("+id+")'>解除冻结</li>"
        }else if("启用"==statusName){
            btn_edit += "<li onclick='MgrUser.freezeAccount("+id+")'>冻结</li>"
        }
    btn_edit +=
        "<li onclick='MgrUser.resetPwd("+id+")'>重置密码</li>" +
        "<li onclick='MgrUser.roleAssign("+id+")'>角色分配</li></ul></div></div>";
    return btn_edit ;
}
function show(this1){
    $(this1).siblings().show();
}
function hide(this1){
    $(this1).hide();
}
/**
 * 检查是否选中
 */
MgrUser.check = function (row) {
    // var selected = $('#' + this.id).bootstrapTable('getSelections');
    // if (selected.length == 0) {
    //     Feng.info("请先选中表格中的某一记录！");
    //     return false;
    // } else {
    //     MgrUser.seItem = selected[0];
    //     return true;
    // }
   // var selected = $('#' + this.id).bootstrapTable('getSelections');
    // MgrUser.seItem = selected[0];
    return true;
};

/**
 * 点击添加管理员
 */
MgrUser.openAddMgr = function () {
    var index = layer.open({
        type: 2,
        title: '添加管理员',
        area: ['780px', '440px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/mgr/user_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改按钮时
 * @param userId 管理员id
 */
MgrUser.openChangeUser = function (id) {
    // if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑用户',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mgr/user_edit/' + id
        });
        this.layerIndex = index;
    // }
};

/**
 * 点击角色分配
 * @param
 */
MgrUser.roleAssign = function (id) {
    // if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '角色分配',
            area: ['300px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mgr/role_assign/' + id
        });
        this.layerIndex = index;
    // }
};

/**
 * 删除用户
 */
MgrUser.delMgrUser = function (id,this1) {
     var parent1 = $(this1).parents("td").siblings().first("td").next("td");
    var account = parent1.html();
        var operation = function(){
            var userId = id;
            var ajax = new $ax(Feng.ctxPath + "/mgr/delete", function () {
                Feng.success("删除成功!");
                MgrUser.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("userId", userId);
            ajax.start();
        };
      Feng.confirm("是否删除用户" + account + "?",operation);
    // }
};

/**
 * 冻结用户账户
 * @param userId
 */
MgrUser.freezeAccount = function (id) {
    // if (this.check()) {
        var userId = id;
        var ajax = new $ax(Feng.ctxPath + "/mgr/freeze", function (data) {
            Feng.success("冻结成功!");
            MgrUser.table.refresh();
        }, function (data) {
            Feng.error("冻结失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userId", userId);
        ajax.start();
    // }
};

/**
 * 解除冻结用户账户
 * @param userId
 */
MgrUser.unfreeze = function (id) {
    // if (this.check()) {
        var userId = id;
        var ajax = new $ax(Feng.ctxPath + "/mgr/unfreeze", function (data) {
            Feng.success("解除冻结成功!");
            MgrUser.table.refresh();
        }, function (data) {
            Feng.error("解除冻结失败!");
        });
        ajax.set("userId", userId);
        ajax.start();
    // }
}

/**
 * 重置密码
 */
MgrUser.resetPwd = function (id) {
    var index = layer.open({
        type: 2,
        title: '重置密码',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/mgr/user_resetPwd/' + id
    });
    this.layerIndex = index;
};
/*MgrUser.resetPwd = function (id) {
    // if (this.check()) {
        var userId = id;
        parent.layer.confirm('是否重置密码为123456？', {
            btn: ['确定', '取消'],
            shade: false //不显示遮罩
        }, function () {
            var ajax = new $ax(Feng.ctxPath + "/mgr/reset", function (data) {
                Feng.success("重置密码成功!");
            }, function (data) {
                Feng.error("重置密码失败!");
            });
            ajax.set("userId", userId);
            ajax.start();
        });
    // }
};*/

MgrUser.resetSearch = function () {
    $("#name").val("");
    $("#beginTime").val("");
    $("#endTime").val("");

    MgrUser.search();
}

MgrUser.search = function () {
    var queryData = {};

    queryData['deptid'] = MgrUser.deptid;
    queryData['name'] = $("#name").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();

    MgrUser.table.refresh({query: queryData});
}

MgrUser.onClickDept = function (e, treeId, treeNode) {
    MgrUser.deptid = treeNode.id;
    MgrUser.search();
};
//处理时间
timeBetween($("#beginTime"),$("#endTime"));
$(function () {
    inputFocus();
  $("#userMangner").click(function(){
      $(".userAdd").show();
      $(".roleAdd").hide();
      $(".deptAdd").hide();
  });
    $("#roleMangner").click(function(){
        $(".userAdd").hide();
        $(".roleAdd").show();
        $(".deptAdd").hide();
    });
    $("#deptMangner").click(function(){
        $(".userAdd").hide();
        $(".roleAdd").hide();
        $(".deptAdd").show();
    });

    var defaultColunms = MgrUser.initColumn();
    var table = new BSTable("managerTable", "/mgr/list", defaultColunms);
    table.setPaginationType("client");
    MgrUser.table = table.init();
    var ztree = new $ZTree("deptTree", "/dept/tree");
    ztree.bindOnClick(MgrUser.onClickDept);
    ztree.init();
});
