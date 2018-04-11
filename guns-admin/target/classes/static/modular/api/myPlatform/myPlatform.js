
/*计算总提醒条数*/
function noticeTotalNum() {
    var num1=parseInt($('#trackTableTotalNum').text());
    var num2=parseInt($('#noUpdateTableTotalNum').text());
    $("#noticeTotalNum").text(num1+num2);
}