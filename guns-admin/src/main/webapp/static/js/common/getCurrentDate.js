/**
 * Created by Administrator on 2017/11/15 0015.
 */
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
    return currentdate;
}
$('.calendar img').hover(function(){
    var that = this;
    var timeDate = getNowFormatDate();//得到当前时间日期
    layer.tips(timeDate, that,{ tips: 3}); //在元素的事件回调体中，follow直接赋予this即可,tips为3代表弹框的位置在下
});

//左侧导航添加active

$(".J_menuItem").click(function(){
    $(this).addClass("active").parent().parent().siblings().children().children().removeClass("active");
});
