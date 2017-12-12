/**
 * Created by zym on 2017/12/7.
 */
function doLogin() {
    var userName = $("#userName").val();
    var psw = $("#psw").val();
    $.post(
        "/do_login",
        {
            userName: userName,
            psw: psw
        },
        function (data) {
            var jsonData = JSON.parse(data);
            if(jsonData.status == "1"){
                console.log(userName);
                setCookie("userName", userName, 7);
                window.location.href = "/html/home.html";
            }else{
                alert(jsonData.status + jsonData.msg);
            }
        }
    );
}

function judgement() {
    username=getCookie('username');
    if (username!=null && username!="") {
        alert('Welcome again '+username+'!')
    }
    else{
        window.location.href = "/html/register.html";
    }
}

function setCookie(c_name,value,expiredays)
{
    var exdate=new Date()
    exdate.setDate(exdate.getDate()+expiredays)
    document.cookie=c_name+ "=" +escape(value)+
        ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}

function getCookie(c_name)
{
    if (document.cookie.length>0)
    {
        c_start=document.cookie.indexOf(c_name + "=")
        if (c_start!=-1)
        {
            c_start=c_start + c_name.length+1
            c_end=document.cookie.indexOf(";",c_start)
            if (c_end==-1) c_end=document.cookie.length
            return unescape(document.cookie.substring(c_start,c_end))
        }
    }
    return ""
}
