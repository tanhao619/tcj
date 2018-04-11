$(function () {
    // 禁止查看网页源代码
    window.onload = function() {
        document.onkeydown = function() {
            var e = window.event || arguments[0];
            //屏蔽F12
            if(e.keyCode == 123) {

                return false;
                //屏蔽Ctrl+Shift+I
            } else if((e.ctrlKey) && (e.shiftKey) && (e.keyCode == 73)) {

                return false;
                //屏蔽Shift+F10
            } else if((e.shiftKey) && (e.keyCode == 121)){

                return false;
            }
        };
        //屏蔽右键单击
        document.oncontextmenu = function() {

            return false;
        }
    }



    var submitBtn = document.getElementById("submit");
    // rsa加密、解密，效率太低！
    /*   submitBtn.onclick = function (event) {
           var thisPwd = document.getElementById("pwd").value;
           var module = $('#hid_module').val();
           var empoent = $('#hid_empoent').val();
           //setMaxDigits()貌似是生成密文的最大位数，如果不设置或者乱设置的话很可能导致死循环然后浏览器崩溃。
           //这个语句必须最先执行，1024位的密钥要传入130,2048的话要传入260
           setMaxDigits(130);
           //RSAKeyPair是密钥对的对象，用e和n可以生成公钥，第二个参数其实就是d，因为我们只需要公钥当然是传空的。
           var key = new RSAKeyPair(empoent,"",module);
           //加密方法，传入公钥和原文，加密成密文。
           //$("#pwd").val(encryptedString(key, encodeURIComponent(thisPwd)));
       };*/
    /*   var key;
       function bodyRSA(){
           var module = $('#hid_module').val();
           var empoent = $('#hid_empoent').val();
           setMaxDigits(130);
           key = new RSAKeyPair(empoent,"",module);
       }*/



    // AES加密
    submitBtn.onclick = function (event) {

        var thisPwd = document.getElementById("pwd").value;
        $("#pwd").val(encrypt($.trim(thisPwd)));


    };
    function encrypt(data) {
        var key  = CryptoJS.enc.Latin1.parse('lgg20180125ailul');
        var iv   = CryptoJS.enc.Latin1.parse('lul20180125ailgg');
        return CryptoJS.AES.encrypt(data, key, {iv:iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.ZeroPadding}).toString();
    }
});