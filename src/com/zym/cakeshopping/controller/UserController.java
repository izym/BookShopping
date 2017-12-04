package com.zym.cakeshopping.controller;

import com.zym.cakeshopping.dao.UserEntity;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

/**
 * Created by 12390 on 2017/12/4.
 */
@IocBean
public class UserController {
    @Inject
    Dao dao;

    @Ok("json")
    @Fail("http:500")
    @At("create_game")
    @POST
    private Object doLogin(@Param("userName")String userName, @Param("psw")String password){
        NutMap re = new NutMap();
        if(userName!=null&&password!=null) {
            UserEntity u = dao.fetch(UserEntity.class, Cnd.where("user_name", "=", userName).and("psw", "=", password));
            if (u!=null) {

                re.put("status", 1);
                re.put("msg", "OK");
            } else {
                re.put("status", 0);
                re.put("msg", "账号或密码错误");
            }
        }else{
            re.put("status", 0);
            re.put("msg", "账号或密码错误");
        }
        return re;
    }
}
