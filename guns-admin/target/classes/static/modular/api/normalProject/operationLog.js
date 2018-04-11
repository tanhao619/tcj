/**
 * 常规项目操作日志
 */
var OperationLog = {
    layerIndex: -1,
    operationLogId: 0
};

/**
 * 关闭日志窗口
 */
OperationLog.cancel = function () {
    parent.layer.close(window.parent.NormalProjectInfo.layerIndex);
};

$(function () {
    this.operationLogId = $("#id").val();
    var ajax1 = new $axRest(Feng.ctxPath + "/proHistory/proHistorys", "get", function (data) {
        data.rows.forEach(function (value, index, array) {
            var value1 = value.content;
            var title = value1.substring(0,value1.indexOf(" "));
            var content = value1.substring(value1.indexOf(" ")+1);
            var node = "<label style='font-weight: 100;'><span style='color:#333;font-size: 14px;margin-right: 20px;'>"+title+"</span><span style='color:#666;'>"+content+"</span></label><br>";

            console.log(content);
            console.log(title);
            $(".operationLogDetail").append(node);
        });
    }, function (data) {
        Feng.error("失败!" + data.responseJSON.message + "!");
        return;
    });
    ajax1.set("id",this.operationLogId);
    ajax1.start();
});
