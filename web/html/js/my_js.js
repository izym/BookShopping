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
            var jsonData = data;
            if (jsonData.status == "1") {
                console.log(userName);
                setCookie("userName", userName, 7);
                setCookie("userId", data.userId, 7);
                setCookie("money", data.money, 7);
                window.location.href = "/html/home.html";
            } else {
                alert(jsonData.status + jsonData.msg);
            }
        }
    );
}

function doregister() {
    var userName = $("#myName").val();
    var psw = $("#myPass").val();
    var rewritemypass = $("#rewritemypass").val();
    if (rewritemypass == psw) {
        $.post(
            "/do_registe",
            {
                userName: userName,
                psw: psw
            },
            function (data) {
                var jsonData = data;
                if (jsonData.status == "1") {
                    alert(jsonData.msg);
                    window.location.href = "/html/login.html";
                } else {
                    alert(jsonData.msg);
                }
            }
        );
    } else {
        alert("密码不一致");
    }
}

function setCookie(c_name, value, expiredays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + expiredays);
    document.cookie = c_name + "=" + escape(value) +
        ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
}

function getCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
}

function addcart(cake_id) {
    $.post(
        "/add_shop_cart",
        {
            cakeId: cake_id,
            userId: getCookie("userId")
        },
        function (data) {
            var jsonData = data;
            alert(data.msg);
        }
    );
}

function del_cart(cake_id) {
    $.post(
        "/del_shop_cart",
        {
            cakeId: cake_id,
            userId: getCookie("userId")
        },
        function (data) {
            alert(data.msg);
            if (data.status == "1") {
                window.location.href = "/html/shop_cart.html";
            }

        }
    );
}

function get_carts() {
    $.post(
        "/get_orders",
        {
            userId: getCookie("userId"),
            state: 0
        },
        function (data) {
            var a = '<div class="cart-header"> <div class="close1" onclick="javascript:del_cart(';
            var e = ')"> </div> <div class="cart-sec simpleCart_shelfItem"><div class="cart-item cyc"> <img src="';
            var b = '" class="img-responsive" alt=""> </div> <div class="cart-item-info"> <h3>';
            var c = '</h3> <ul class="qty"> </ul> <div class="delivery"> <p>价格 : ￥';
            var d = '</p> <span></span> <div class="clearfix"></div> </div> </div> <div class="clearfix"></div> </div> </div>';

            if (data.status == "1") {
                var cakes = data.cakes;
                for (var i in cakes) {
                    var img_path = cakes[i].img;
                    var cake_name = cakes[i].cakeName;
                    var price = cakes[i].price;
                    $("#cart").append(a + cakes[i].cakeId + e + img_path + b + cake_name + c + price + d);
                }
            }
        }
    );
}

function get_cakes() {

    $.get(
        "/get_cakes",
        function(data){
            if(data.status=="1") {
                var cakes = data.cakes;
                var a = '<div class="product-grid">' +
                    '<div class="product-img b-link-stripe b-animate-go  thickbox"> ' +
                    '<img src="';
                var b = '" class="img-responsive" alt=""> ' +
                    '</div> ' +
                    '<div class="product-info simpleCart_shelfItem"> ' +
                    '<div class="product-info-cust prt_name"> ' +
                    '<h4>';
                var c = '</h4><span class="item_price">￥';
                var d = '</span> ' + '<div class="ofr"> ' +
                    '<p class="pric1"><del>￥';
                var e = '</del> ' +
                    '</p><p class="disc">[七折]</p> </div> ' +
                    '<input type="button" class="item_add items" value="Add" onclick="javascript:addcart(';
                var f = ')"> ' +
                    '<div class="clearfix"></div></div> </div> </div>';
                for (var i in cakes) {
                    var img_path = cakes[i].img;
                    var cake_name = cakes[i].cakeName;
                    var cake_old_price = cakes[i].price;
                    var cake_price = cake_old_price * 0.7;
                    $("#list").append(a + img_path + b + cake_name + c + cake_price + d + cake_old_price + e + cakes[i].cakeId + f);
                }
            }
        }
    );

}

function get_orders() {
    $.post(
        "/get_orders",
        {
            userId: getCookie("userId"),
            state: 1
        },
        function (data) {
            var a = '<div class="cart-header"> ';
            var e = '<div class="cart-sec simpleCart_shelfItem"><div class="cart-item cyc"> <img src="';
            var b = '" class="img-responsive" alt=""> </div> <div class="cart-item-info"> <h3>';
            var c = '</h3> <ul class="qty"> </ul> <div class="delivery"> <p>价格 : ￥';
            var d = '</p> <span></span> <div class="clearfix"></div> </div> </div> <div class="clearfix"></div> </div> </div>';

            if (data.status == "1") {
                var cakes = data.cakes;
                for (var i in cakes) {
                    var img_path = cakes[i].img;
                    var cake_name = cakes[i].cakeName;
                    var price = cakes[i].price;
                    $("#order").append(a + e + img_path + b + cake_name + c + price + d);
                }
            }
        }
    );
}

function pay() {
    $.post(
        "/buy",
        {
            userId: getCookie("userId")
        },
        function (data) {
            alert(data.msg);
            window.location.href = "/html/shop_cart.html";
            setCookie("money",getCookie("money") - data.money, 7);
        });
}

