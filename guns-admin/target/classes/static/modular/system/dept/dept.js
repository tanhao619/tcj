/**
 * 部门管理初始化
 */
var Dept = {
    id: "DeptTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Dept.initColumn = function () {
    return [
        // {field: 'selectItem', radio: true,visible: false},
        {title: '编号', field: 'id', align: 'center', valign: 'middle',width:'50px'},
        {title: '状态', field: 'isSystem', visible: false,align: 'center', valign: 'middle',width:'50px'},
        {title: '单位科室名称', field: 'simplename', align: 'center', valign: 'middle', sortable: true},
      /*  {title: '部门简称', field: 'simplename', align: 'center', valign: 'middle', sortable: true},
        {title: '部门全称', field: 'fullname', align: 'center', valign: 'middle', sortable: true},*/
        {title: '备注', field: 'remark', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: 'opearte', align: 'center', valign: 'middle', sortable: false, width:'100px',formatter: Dept.formatOperate}
    /*    {title: '排序', field: 'num', align: 'center', valign: 'middle', sortable: true},
        {title: '备注', field: 'tips', align: 'center', valign: 'middle', sortable: true}*/];
};
Dept.formatOperate = function (val, row, index) {
    var id = row.id;
    var account = row.account;
    var statusName = row.statusName;
    var btn_edit = "<div class='f_wrap' id='deptBtn'>" +
        "<span class='' onclick='showDept(this,"+row.isSystem+")'>...</span>" +
        "<div class='overInfo deptInfo'  onmouseleave='hideDept(this)'>" +
        "<ul><li onclick='Dept.openDeptDetail("+id+")'>修改</li>" +
        "<li onclick='Dept.delete("+id+")'>删除</li></ul></div></div>";
    return btn_edit ;
}
function showDept(this1,isSystem){
    if(isSystem == 1){
        $(this1).parent().css("cursor","not-allowed");
        return;
    }else{
        $(this1).siblings().show();
    }

}
function hideDept(this1){
    $(this1).hide();
}
/**
 * 检查是否选中
 */
Dept.check = function () {
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Dept.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加部门
 */
Dept.openAddDept = function () {
    var index = layer.open({
        type: 2,
        title: '添加单位科室',
        area: ['700px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dept/dept_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看部门详情
 */
Dept.openDeptDetail = function (id) {
    // if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '单位科室详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dept/dept_update/' + id
        });
        this.layerIndex = index;
    // }
};

/**
 * 删除部门
 */
Dept.delete = function (id) {
    // if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/dept/delete", function () {
                Feng.success("删除成功!");
                Dept.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("deptId",id);
            ajax.start();
        };

        Feng.confirm("是否刪除该部门?", operation);
    // }
};

/**
 * 查询部门列表
 */
Dept.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Dept.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Dept.initColumn();
    var table = new BSTreeTable(Dept.id, "/dept/list", defaultColunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandAll(true);
    table.setLoad =
    table.init();

        setTimeout(function(){
            $("#DeptTable tr").each(function(){
                $(this).find("td:eq(1)").css('display','none');
                $(this).find("th:eq(1)").css('display','none');
                var firstValue= $(this).find("td:eq(1)").html();
                if(firstValue == 1){
                    $(this).find("td").css({"background":"#f6f6f6","cursor":"not-allowed","color":"#333"});
                    $(this).find("td:last").css("cursor",'not-allowed');
                    $(this).find("td:last").children(".f_wrap").css("cursor",'not-allowed');
                    $(this).find("td:last").children("span").off("click");
                }
            })
        },100);
    Dept.table = table;

});
