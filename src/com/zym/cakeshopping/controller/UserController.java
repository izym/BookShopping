package com.zym.cakeshopping.controller;

import com.google.gson.Gson;
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
    @At("doLogin")
    @POST
    public Object doLogin(@Param("userName")String userName, @Param("psw")String password){
        NutMap re = new NutMap();
        if(userName!=null&&password!=null) {
            UserEntity u = dao.fetch(UserEntity.class, Cnd.where("user_name", "=", userName).and("psw", "=", password));
            if (u!=null) {
                re.put("status", "1");
                re.put("msg", "OK");
                re.put("userId", u.getUserId());
                re.put("userName", u.getUserName());
            } else {
                re.put("status", "0");
                re.put("msg", "账号或密码错误");
            }
        }else{
            re.put("status", "0");
            re.put("msg", "账号或密码错误");
        }
        return new Gson().toJson(re);
    }


    @Ok("json")
    @Fail("http:500")
    @At("doRegiste")
    @POST
    public Object doRegiste(@Param("userName")String userName, @Param("psw")String password){
        NutMap re = new NutMap();
        if(userName!=null&&password!=null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(userName);
            userEntity.setPsw(password);
            userEntity.setMoney(0);
            dao.insert(userEntity);
            re.put("status", "1");
            re.put("msg", "注册成功");
        }else{
            re.put("status", "0");
            re.put("msg", "注册失败");
        }
        return new Gson().toJson(re);
    }

}
